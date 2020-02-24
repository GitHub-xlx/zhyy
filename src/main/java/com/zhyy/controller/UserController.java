package com.zhyy.controller;


import com.google.gson.JsonObject;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

					request.getSession().setAttribute("user",user);


					List<Druginformation> list1=null;
					//查询药库库存的药品的当前数量和最低数量
					List<Drugstoredruginventory>list=userServices.checkInventoryCount();
					if(list.size()>0){//遍历药库的药品数量是否小于最低限量
						System.out.println("库存不足!");
						for (int i = 0; i <list.size(); i++)
						{
							//根据药品编码查询药品名称
							list1=userServices.findDrugNameByDrugCode(list.get(i).getDrugcode());
						}
						System.out.println("list1->查出的药品名称列表:"+list1);
						for (int i = 0; i <list1.size(); i++)
						{
							QuartzTest.sendMail(list1.get(i).getCommoname());
						}
					}else{
						System.out.println("库存足够!");
					}


					return new ResultInfo(200,"登录成功");
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

    //管理用户列表
	@RequestMapping("/manageUsers")
	public @ResponseBody
	TableMsg manageUsers(String page, String limit, HttpServletRequest request){
		System.out.println("执行到管理用户列表");
		System.out.println("page:"+page+", limit:"+limit);
		int pageInt=Integer.valueOf(page)-1;
		int limitInt=Integer.valueOf(limit);


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
