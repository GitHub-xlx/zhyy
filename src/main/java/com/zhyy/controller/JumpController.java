package com.zhyy.controller;

import com.zhyy.aspect.IgnoreLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/jump")
public class JumpController
{

	@RequestMapping({"/front/{id}"})
	@IgnoreLog
	public String jumpFront(@PathVariable("id")String id){
		return "front/html/"+id;
	}
	@RequestMapping({"/back/{id}"})
	@IgnoreLog
	public String jumpBack(@PathVariable("id")String id){
		System.out.println("跳转网页至"+id);
		return "back/html/"+id;
	}
	@ResponseBody
	@RequestMapping({"/ce"})
	@IgnoreLog
	public String jumpCe(String username){
		return "hello "+username;
	}

}