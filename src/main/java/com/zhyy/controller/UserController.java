package com.zhyy.controller;


import com.zhyy.entity.User;
import com.zhyy.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 *@author Administrator
 *Date 2020/2/14
 */
@Controller
public class UserController
{
	@Autowired
	private UserServices userServices;
	@RequestMapping("/query")

	public String queryUserList()
	{


		return "back/html/login";

	}

}