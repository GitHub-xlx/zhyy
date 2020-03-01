package com.zhyy.controller;


import com.google.gson.JsonObject;
import com.zhyy.aspect.IgnoreLog;
import com.zhyy.entity.*;
import com.zhyy.services.UserServices;
import com.zhyy.test.MyJobDetail;
import com.zhyy.test.QuartzTest;
import com.zhyy.test.TestSend;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *@author Administrator
 *Date 2020/2/14
 */
@Controller
@RequestMapping("/userController")
public class UserController
{
	@Autowired
	private UserServices userServices;
	@RequestMapping("/welcome")
	@IgnoreLog
	public String welcome(){
		return "back/html/login";
	}
	@RequestMapping("/query")
	public String queryUserList()
	{
		return "back/html/login";
	}

	@RequestMapping("/doLogin")
	public @ResponseBody
	ResultInfo doLogin(String account, String password, HttpServletRequest request){


		if (account!=null&&password!=null){
			System.out.println("开始查询用户");
			User user = userServices.queryUserByAccount(account);
			if (user!=null){
				System.out.println("比对密码");
				if (user.getPassword().equals(password)){

					System.out.println("比对用户的状态");
					if(user.getState().equals("已启用")){
						request.getSession().setAttribute("user",user);

                        //登录成功检测库存
						checkInventory();
						//药房过期药品检测
						expiredCheck();
						//药房滞销药品检测
						unsalableCheck();

						return new ResultInfo(200,"登录成功");
					}else{
						return new ResultInfo(406,"该账号已被禁用,有问题联系管理员");
					}

				}else{
					return new ResultInfo(404,"密码错误");
				}
			}else{
				return new ResultInfo(405,"账号不存在");
			}
		}
		return new ResultInfo(403,"数据丢失");
	}

	@RequestMapping("/showMain")
	public String showMain(HttpServletRequest request, Model model){
		User user = (User)request.getSession().getAttribute("user");
		ArrayList<Menu> list = (ArrayList<Menu>) userServices.queryMenuList(user.getRolecode());
		ArrayList<Menu>list2 = new ArrayList<>();
		HashMap map = new HashMap<String,ArrayList<Menu>>();

		for (int i = 0; i <list.size() ; i++)
		{
			if (map.containsKey(list.get(i).getParentcode())){
				list2.add(list.get(i));
			}else{
				list2 = new ArrayList<>();
				list2.add(list.get(i));
				map.put(list.get(i).getParentcode(),list2);
			}
		}
		model.addAttribute("menu",map);
		System.out.println(list);
		return "back/html/manage";
	}

	//检差库存的方法（不足则发送邮件警告）
	public void checkInventory(){
		ArrayList<String>arrayList = new ArrayList<>();
		//查询药库库存的药品的当前数量和最低数量
		List<Drugstoredruginventory>list=userServices.checkInventoryCount();
		if(list.size()>0){//遍历药库的药品数量是否小于最低限量
			System.out.println(list.size()+"样药品库存不足!");
			for (int i = 0; i <list.size(); i++)
			{
				//根据药品编码查询药品名称
				arrayList.add(userServices.findDrugNameByDrugCode(list.get(i).getDrugcode()));
			}
			QuartzTest.sendMail(String.valueOf(arrayList));
		}else{
			System.out.println("库存足够!");
		}
	}
	//检查药房药品是否过期的方法（如过期则发送邮件警告）
	public void expiredCheck(){
	List<Druginventorytable>list=userServices.expiredCheck();
	int year=0;
	String str;
	Calendar cal= Calendar.getInstance();
	int yearNow=cal.get(Calendar.YEAR);
		System.out.println("当前的年份为："+yearNow);
	for (int i = 0; i <list.size() ; i++)
	{
		str=list.get(i).getProductiondate().substring(0,4);
		System.out.println("查询出的药品年份为："+str);
		if(list.get(i).getShelflife().equals("12个月")){
			year=Integer.valueOf(str)+1;
		}else if(list.get(i).getShelflife().equals("24个月")){
			year=Integer.valueOf(str)+2;
		}else {
			year=Integer.valueOf(str)+3;
		}

		if(yearNow==year){
			System.out.println(list.get(i).getCommoname()+"等药品过期了！");
			//接下来走药品停用的方法
			drugDiscontinuation(list.get(i).getDrugcode());
		}
	}
	}
	//检查药房药品是否滞销方法（如滞销则发送邮件警告）
	public void unsalableCheck(){
		List<Druginventorytable>list=userServices.unsalableCheck();
		int month=0;
		Calendar cal= Calendar.getInstance();
		int monthNow = cal.get(Calendar.MONTH) + 1;
		System.out.println("当前月份为："+monthNow);
		String str;
		for (int i = 0; i <list.size() ; i++)
		{
			str=list.get(i).getReceivetime().substring(5,7);
			System.out.println("查询出的药品月份为："+str);
            month=Integer.valueOf(str);

            if((month+3)>12){
	            month=month-9;
	            if (month == monthNow)
	            {
		            System.out.println(list.get(i).getCommoname() + "等药品已超过90天了！");
	            }
            }else
            {
	            if ((month + 3) == monthNow)
	            {
		            System.out.println(list.get(i).getCommoname() + "等药品已超过90天了！");
	            }
            }
		}
	}

	//药品低限设置后检测库存
	@ResponseBody
	@RequestMapping("/checkInventoryBySet")
	public String checkInventoryBySet(){
		System.out.println("执行到药品低限设置后检测库存");
		checkInventory();
		return "1";
	}

    //用户管理列表
	@RequestMapping("/manageUsers")
	public @ResponseBody
	TableMsg manageUsers(String page, String limit, HttpServletRequest request){
		System.out.println("执行到管理用户列表");
		System.out.println("page:"+page+", limit:"+limit);
		int limitInt=Integer.valueOf(limit);
		int pageInt=(Integer.valueOf(page)-1)* limitInt;


		List<User> User=null;
		int count=0;
		User =userServices.queryUserList(pageInt,limitInt);
		count=userServices.countUserList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(User);
		return tableMsg;

	}


	//新增人员
	@ResponseBody
	@RequestMapping("/regStaff")
	public String regStaff(String account2,String password2,String username2, String phone2,
	                       String sex2,String age2,String roles,String titles){
		System.out.println("执行到新增人员");
		String role="";
		String title="";
		if(roles.equals("01")){
			 role="药库";
				if(titles.equals("001")){
					title="库管人员";

				}else if(titles.equals("002")){
					title="仓库经理";
				}else{
					title="采购人员";
				}

		}else{
			role="药房";
			if(titles.equals("003"))
			{
				title = "药房销售";
			}else{
				title="药房经理";
			}
		}
		boolean b=userServices.regStaff(account2,password2,username2,phone2,sex2,age2,role,title,titles,"001","已启用");
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}
	//重置密码
	@ResponseBody
	@RequestMapping("/resetPassword")
	public String resetPassword(String uaccount){
		System.out.println("执行到重置密码");
		boolean b=userServices.resetPassword(uaccount);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
         return msg;
	}

	//启用
	@ResponseBody
	@RequestMapping("/enable")
	public String enableUser(String account){
		System.out.println("启用");
		boolean b=userServices.enableUser(account);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}
	//禁用
	@ResponseBody
	@RequestMapping("/disable")
	public String disableUser(String account){
		System.out.println("禁用");
		boolean b=userServices.disableUser(account);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}

	//药品调价
	@ResponseBody
	@RequestMapping("/adjustmentPrice")
	public String adjustmentPrice(String price,String drugcode){
		System.out.println("调价");
		double priceNow =Double.valueOf(price);
		boolean b=userServices.adjustmentPrice(priceNow,drugcode);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}
	//药品停用
	@ResponseBody
	@RequestMapping("/drugDiscontinuation")
	public String drugDiscontinuation(String drugcode){
		System.out.println("药品停用");
		boolean b=userServices.drugDiscontinuation(drugcode);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			boolean b2=userServices.drug2Discontinuation(drugcode);
			if(b2){
				msg="1";
			}else{
				msg="2";
			}
		}else{
			msg="2";
		}
		return msg;
	}


}
