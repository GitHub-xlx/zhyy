package com.zhyy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/jump")
public class JumpController
{

	@RequestMapping({"/front/{id}"})
	public String jumpFront(@PathVariable("id")String id){
		return "front/html/"+id;
	}
	@RequestMapping({"/back/{id}"})
	public String jumpBack(@PathVariable("id")String id){
		return "back/html/"+id;
	}

}