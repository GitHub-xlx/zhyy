package com.zhyy.controller;


import com.google.gson.Gson;
import com.zhyy.entity.Drugprice;
import com.zhyy.entity.ResultInfo;
import com.zhyy.entity.TableMsg;
import com.zhyy.entity.User;
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
	TableMsg selectprice(String currentprice,String previousprice, String start,String end,String page,String limit, HttpServletRequest request){

		int pageInt=Integer.valueOf(page);
		int limitInt=Integer.valueOf(limit);
		User user=(User)request.getSession().getAttribute("user");
		List<Drugprice> drugprices=null;
		int count=0;
		drugprices =drugServices.queryDrugprice(user.getPharmacycode(),currentprice,previousprice,start,end,pageInt,limitInt);

		count=drugServices.countDrugprice(user.getPharmacycode(),currentprice,previousprice,start,end);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(drugprices);
		return tableMsg;
	}
}
