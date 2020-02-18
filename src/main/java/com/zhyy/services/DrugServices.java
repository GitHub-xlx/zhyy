package com.zhyy.services;

import com.zhyy.entity.Drugprice;
import com.zhyy.entity.DrugpriceDruginformation;

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
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @param nowpage  分页从第几页开始
	 * @param size  分页，每页多少条
	 * @return
	 */
	public List<DrugpriceDruginformation> queryDrugprice(String pharmacycode, String drugcode, String commoname, String start, String end, int nowpage, int size);

	/**
	 *
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	public int countDrugprice(String pharmacycode,String drugcode,String commoname,String start,String end);

}
