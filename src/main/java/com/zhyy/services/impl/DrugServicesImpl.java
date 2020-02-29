package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.*;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.services.DrugServices;
import com.zhyy.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
	public List<Druginformation> selectDruginformation(String commoname, String pincode)
	{
		String where="1=1 ";
		if(commoname!=null){
			where = commoname.length()>0 ? where+" and commoname like '%"+commoname+"%'" : where;
		}
		if (pincode!=null){
			where = pincode.length()>0 ? where+" and pincode like '%"+pincode+"%'" : where;
		}
		return drugMapper.selectDruginformation(where);

	}

	@Override
	public int insertOutbound(Vacation vac)
	{
		String[] split = vac.getDurgResult().split(",");
		String pharmacycode = split[1];
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertOutbound(vac, pharmacycode, time);
		return drugMapper.updatePharmacyInventory(vac.getList());
	}

	@Override
	public List<DrugClass> selectDrugClass()
	{
		return drugMapper.selectDrugClass();
	}

	@Override
	public String selectDrugClassCode(String parentCode)
	{
		return drugMapper.selectDrugClassCode(parentCode);
	}

	@Override
	public int saveDrugClassSetInfo(DrugClass drugClass)
	{
		return drugMapper.saveDrugClassSetInfo(drugClass);
	}

	@Override
	public List<Druginformation> selectDrugInfo()
	{
		return drugMapper.selectDrugInfo();
	}

	@Override
	public int saveDrugInfo(Druginformation drugInformation)
	{
		return drugMapper.saveDrugInfo(drugInformation);
	}

	@Override
	public List<Drugstoredruginventory> selectDrugStoreInventory()
	{
		return drugMapper.selectDrugStoreInventory();
	}

	@Override
	public int saveDrugStoreInventory(Drugstoredruginventory drugStoreDrugInventory)
	{
		return drugMapper.saveDrugStoreInventory(drugStoreDrugInventory);
	}
}
