package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.Druginformation;
import com.zhyy.entity.Drugprice;
import com.zhyy.entity.DrugpriceDruginformation;
import com.zhyy.entity.Drugsale;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.services.DrugServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 *@author Administrator
 *Date 2020/2/14
 */
@Service
@Transactional
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

	@Override
	public List<Druginformation> selectDruginformation(String commonname, String pincode)
	{
		String where="1=1 ";
		if(commonname!=null){
			where = commonname.length()>0 ? where+" and commonname = '"+commonname+"'" : where;
		}
		if (pincode!=null){
			where = pincode.length()>0 ? where+" and pincode = '"+pincode+"'" : where;
		}
		return drugMapper.selectDruginformation(where);

	}
}
