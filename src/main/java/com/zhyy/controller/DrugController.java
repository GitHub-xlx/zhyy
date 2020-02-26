package com.zhyy.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zhyy.entity.*;
import com.zhyy.services.DrugServices;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/drugController")
public class DrugController
{
	@Autowired
	private DrugServices drugServices;

	@RequestMapping("/selectprice")
	public @ResponseBody
	TableMsg selectprice(String drugcode,String commoname, String start,String end,String page,String limit, HttpServletRequest request){

		int pageInt=Integer.valueOf(page);
		int limitInt=Integer.valueOf(limit);
		User user=(User)request.getSession().getAttribute("user");
		List<DrugpriceDruginformation> drugprices=null;
		int count=0;
		drugprices =drugServices.queryDrugprice(user.getPharmacycode(),drugcode,commoname,start,end,pageInt,limitInt);

		count=drugServices.countDrugprice(user.getPharmacycode(),drugcode,commoname,start,end);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(drugprices);
		return tableMsg;
	}
	@RequestMapping("/selectsale")
	public @ResponseBody
	TableMsg selectsale(String drugcode, String commoname, String specialmedicine, String idcard, String consumername,String salesperson, String start, String end,String page,String limit, HttpServletRequest request){

		int pageInt=Integer.valueOf(page);
		int limitInt=Integer.valueOf(limit);
		User user=(User)request.getSession().getAttribute("user");
		List<Drugsale> drugsales=null;
		int count=0;
		drugsales =drugServices.queryDrugSaleList(user.getPharmacycode(),drugcode,commoname,specialmedicine,idcard,consumername,salesperson,start,end,pageInt,limitInt);

		count=drugServices.countDrugSaleList(user.getPharmacycode(),drugcode,commoname,specialmedicine,idcard,consumername,salesperson,start,end);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(drugsales);
		return tableMsg;
	}
	@RequestMapping("/selectclaim")
	public @ResponseBody
	TableMsg selectclaim(int limit,int page,String commoname,String pincode, HttpServletRequest request){

		//开启分页
		PageHelper.startPage(page,limit);
		List all = drugServices.selectDruginformation(commoname,pincode);
		PageInfo p = new PageInfo(all);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}


}
