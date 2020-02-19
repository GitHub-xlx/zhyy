package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.Drugprice;
import com.zhyy.entity.DrugpriceDruginformation;
import com.zhyy.entity.Drugsale;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.services.DrugServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 *@author Administrator
 *Date 2020/2/14
 */
@Service
public class DrugServicesImpl implements DrugServices
{
	@Autowired
	private DrugMapper drugMapper;

	@Override
	public List<DrugpriceDruginformation> queryDrugprice(String pharmacycode, String drugcode, String commoname, String start, String end, int nowpage, int size)
	{
		return drugMapper.queryDrugprice(pharmacycode,drugcode,commoname,start,end,nowpage,size);
	}

	@Override
	public int countDrugprice(String pharmacycode, String drugcode,String commoname, String start, String end)
	{
		return drugMapper.countDrugprice(pharmacycode,drugcode,commoname,start,end);
	}

	@Override
	public List<Drugsale> queryDrugSaleList(String pharmacycode, String drugcode, String commoname, String specialmedicine, String idcard, String consumername, String salesperson,String start, String end, int nowpage, int size)
	{
		return drugMapper.queryDrugSaleList(pharmacycode,drugcode,commoname,specialmedicine,idcard,consumername,salesperson,start,end,nowpage,size);
	}

	@Override
	public int countDrugSaleList(String pharmacycode, String drugcode, String commoname, String specialmedicine, String idcard, String consumername,String salesperson, String start, String end)
	{
		return drugMapper.countDrugSaleList(pharmacycode,drugcode,commoname,specialmedicine,idcard,consumername,salesperson,start,end);
	}
}
