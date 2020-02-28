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

	//药房药品库存列表
	@RequestMapping("/doDrugInventory")
	public @ResponseBody
	TableMsg doDrugInventory(String page, String limit, HttpServletRequest request){
		System.out.println("药房药品库存列表");
		System.out.println("page:"+page+", limit:"+limit);
		int pageInt=Integer.valueOf(page)-1;
		int limitInt=Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList=null;
		int count=0;
		druginventorytableList =drugServices.queryDrugInventoryList(pageInt,limitInt);
		count=drugServices.countDrugInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;

	}


	/**
	 * 查询药品分类信息
	 * @author cbd
	 * @param page 当前页
	 * @param limit 每页显示数量
	 * @return 返回table信息对象
	 */
	@RequestMapping("/selectDrugClass")
	@ResponseBody
	TableMsg selectDrugClass (int page,int limit)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<DrugClass> drugClassList = drugServices.selectDrugClass();
		PageInfo pageInfo = new PageInfo(drugClassList);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据父级编码查询其对象的子级编码的最大值
	 * @author cbd
	 * @param parentCode 大类编码
	 * @return 返回子级编码最大值用于新增的子级编码
	 */
	@RequestMapping("/selectClassCode")
	@ResponseBody
	public String selectClassCode(String parentCode)
	{
		return drugServices.selectDrugClassCode(parentCode);
	}

	/**
	 * 药品分类设置新增方法：根据新增的药品分类插入到药品分类表
	 * @author cbd
	 * @param drugClass 药品分类信息对象
	 * @return 返回整型值判断结果状态成功与否
	 */
	@RequestMapping("/saveDrugClassSetInfo")
	@ResponseBody
	public String saveDrugClassSetInfo(DrugClass drugClass)
	{
		System.out.println("提交的信息=="+drugClass);
		String res = null;
		int i = drugServices.saveDrugClassSetInfo(drugClass);
		if(i>0){
			res = "success";
		}else {
			res = "fail";
		}
		return res;
	}

	/**
	 * 查询药品信息表
	 * @author cbd
	 * @param page 起始页
	 * @param limit 每页显示数量
	 * @return 返回药品信息表的list
	 */
	@RequestMapping("/selectDrugInfo")
	@ResponseBody
	public TableMsg selectDrugInfo(int page,int limit)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Druginformation> list = drugServices.selectDrugInfo();
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据药品信息对象将对应信息增加至药品信息表
	 * @author cbd
	 * @param drugInformation 药品信息表对象
	 * @return 返回状态字符串
	 */
	@RequestMapping("/saveDrugInfo")
	@ResponseBody
	public String saveDrugInfo(Druginformation drugInformation)
	{
		String res = null;
		int i = drugServices.saveDrugInfo(drugInformation);
		if(i>0){
			res = "success";
		}else {
			res = "fail";
		}

		return null;
	}
	@RequestMapping("/selectPharmacyd")
	@ResponseBody
	TableMsg selectPharmacyd (int page,int limit,String drugcode, String lotnumber, String asker, String outbound, String start, String end)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Pharmacydrugschedule> list = drugServices.selectPharmacyd(drugcode, lotnumber, asker, outbound, start, end);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}











}
