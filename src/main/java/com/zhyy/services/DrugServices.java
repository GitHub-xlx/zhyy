package com.zhyy.services;

import com.zhyy.entity.Drugprice;

import java.util.HashMap;
import java.util.List;

/**
 * 测试药品信息业务相关类
 * className
 * @author Administrator
 */
public interface DrugServices
{
	/**
	 *
	 * @param pharmacycode
	 * @param currentprice
	 * @param previousprice
	 * @param start
	 * @param end
	 * @param nowpage
	 * @param size
	 * @return
	 */
	public List<Drugprice> queryDrugprice(String pharmacycode,String currentprice,String previousprice,String start,String end,int nowpage,int size);
//String pharmacycode,String currentprice,String previousprice,String start,String end

	/**
	 *
	 * @param pharmacycode
	 * @param currentprice
	 * @param previousprice
	 * @param start
	 * @param end
	 * @return
	 */
	public int countDrugprice(String pharmacycode,String currentprice,String previousprice,String start,String end);

}
