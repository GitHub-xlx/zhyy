package com.zhyy.controller;


import com.google.gson.JsonObject;
import com.zhyy.entity.ResultInfo;
import com.zhyy.entity.User;
import com.zhyy.services.UserServices;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	public String showMain(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		System.out.println(user);

		return "back/html/manage";
	}

}
