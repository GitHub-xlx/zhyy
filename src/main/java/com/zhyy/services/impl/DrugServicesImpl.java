package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.Drugprice;
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
	public List<Drugprice> queryDrugprice(String pharmacycode, String currentprice, String previousprice, String start, String end, int nowpage, int size)
	{
		return drugMapper.queryDrugprice(pharmacycode,currentprice,previousprice,start,end,nowpage,size);
	}

	@Override
	public int countDrugprice(String pharmacycode, String currentprice, String previousprice, String start, String end)
	{
		return drugMapper.countDrugprice(pharmacycode,currentprice,previousprice,start,end);
	}
}
