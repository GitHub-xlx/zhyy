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
import java.text.SimpleDateFormat;
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

                        //登录成功检测药库库存低限检测
						checkInventory();
						//药房过期药品检测
						expiredCheck();
						//药房滞销药品检测
						unsalableCheck();
						//药房库存低限检测
						checkPharmacyInventory();

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


	//检差药库库存的方法（不足则发送邮件警告）
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
			QuartzTest.sendMail(arrayList+"等这些药库库存数量");
		}else{
			System.out.println("库存足够!");
		}
	}

	//检差药房库存的方法（不足则发送邮件警告）
	public void checkPharmacyInventory(){
		ArrayList<String>arrayList = new ArrayList<>();
		//查询药房库存的药品的当前数量和最低数量
		List<Druginventorytable>list=userServices.checkPharmacyInventoryCount();
		if(list.size()>0){//遍历药库的药品数量是否小于最低限量
			System.out.println(list.size()+"样药品库存不足!");
			for (int i = 0; i <list.size(); i++)
			{
				//根据药品编码查询药品名称
				arrayList.add(userServices.findDrugNameByDrugCode(list.get(i).getDrugcode()));
			}
			QuartzTest.sendMail(arrayList+"等这些药房库存数量");
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
			expiredStatus(list.get(i).getDrugcode());
		}
	}
	}
	//设置药房药品状态为过期状态
	public void expiredStatus(String drugcode){

		System.out.println("执行到设置药房药品状态为过期状态");

		boolean b=userServices.expiredStatus(drugcode);
		boolean b2=userServices.expiredStatus2(drugcode);
		if(b){
			if(b2){
				System.out.println("修改成功");
			}else{
				System.out.println("修改失败2");
			}
		}else{
			System.out.println("修改失败");
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
		            unsalableStatus(list.get(i).getDrugcode());
	            }
            }else
            {
	            if ((month + 3) == monthNow)
	            {
		            System.out.println(list.get(i).getCommoname() + "等药品已超过90天了！");
		            unsalableStatus(list.get(i).getDrugcode());
	            }
            }
		}
	}

	//设置药房药品状态为滞销状态
	public void unsalableStatus(String drugcode){

		System.out.println("执行到设置药房药品状态为过期状态");

		boolean b=userServices.unsalableStatus(drugcode);
		if(b){
			System.out.println("修改成功");
		}else{
			System.out.println("修改失败");
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

	//检测用户是否存在
	@ResponseBody
	@RequestMapping("/checkUser")
	public String checkUser(String account){
		System.out.println("执行到检测用户是否存在");
		System.out.println("account:"+account);
		List<User>list= userServices.checkUser();
		String msg="";
		for (int i = 0; i <list.size() ; i++)
		{
			if(list.get(i).getAccount().equals(account)){
				msg="1";
				break;
			}else{
				msg="2";
			}
		}
		System.out.println("msg:"+msg);
		return msg;
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
		System.out.println("注册为:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}

//	//赋值
//	@ResponseBody
//	@RequestMapping("/assignment")
//	public void assignment(String account,String username, String phone,
//	                       String sex,String age,String department,String position,String rolecode,HttpServletRequest request){
//		System.out.println("执行到赋值");
//		request.setAttribute("account",account);
//		request.setAttribute("username",username);
//		request.setAttribute("phone",phone);
//		request.setAttribute("sex",sex);
//		request.setAttribute("age",age);
//		request.setAttribute("department",department);
//		request.setAttribute("position",position);
//		request.setAttribute("rolecode",rolecode);
//
//	}


//	//用户修改
//	@ResponseBody
//	@RequestMapping("/updateStaff")
//	public String updateStaff(String password2,String username2, String phone2,
//	                       String sex2,String age2,String roles,String titles){
//		System.out.println("执行到用户修改");
//
//		String wheres="";
//
//		String role="";
//		String title="";
//		if(roles.equals("01")){
//			role="药库";
//			if(titles.equals("001")){
//				title="库管人员";
//
//			}else if(titles.equals("002")){
//				title="仓库经理";
//			}else{
//				title="采购人员";
//			}
//
//		}else{
//			role="药房";
//			if(titles.equals("003"))
//			{
//				title = "药房销售";
//			}else{
//				title="药房经理";
//			}
//		}
//
//
//		boolean b=userServices.updateStaff(password2,username2,phone2,sex2,age2,role,title,titles);
//		System.out.println("b:"+b);
//		String msg="";
//		if(b){
//			msg="1";
//		}else{
//			msg="2";
//		}
//		return msg;
//	}

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
	public String adjustmentPrice(String price,String beforePrice,String drugCode){
		System.out.println("调价");
		String msg="";
		double priceNow =Double.valueOf(price);
		double beforeprice =Double.valueOf(beforePrice);
		String str=userServices.findDrugPriceByDrugCode(drugCode);

		//得到long类型当前时间
		long l = System.currentTimeMillis();
		//new日期对象
		Date date = new Date(l);
		//转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String dataTime = dateFormat.format(date);

        if(Double.valueOf(str)>priceNow){
	        msg="3";
        }else{
	        boolean b=userServices.adjustmentPrice(priceNow,drugCode);
	        boolean b2=userServices.adjustmentPrice2(priceNow,beforeprice,drugCode,dataTime);
	        System.out.println("b:"+b+",b2:"+b2);

	        if(b){
		        if(b2){
			        msg="1";
		        }else{
			        msg="2";
		        }
	        }else{
		        msg="2";
	        }
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

	//药品启用
	@ResponseBody
	@RequestMapping("/drugEnable")
	public String drugEnable(String drugcode){
		System.out.println("药品启用");
		boolean b=userServices.drugEnable(drugcode);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			boolean b2=userServices.drugEnable2(drugcode);
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
