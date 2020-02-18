package com.zhyy.mapper;


import com.zhyy.entity.Drugprice;
import com.zhyy.entity.DrugpriceDruginformation;
import com.zhyy.sqlifclass.DrugPriceIfClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 测试用户查询接口
 * @author Administrator
 */
@Mapper
@Component("drugMapper")
public interface DrugMapper
{
	/**
	 * 多条件模糊查询
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @param nowpage  分页从第几页开始
	 * @param size  分页，每页多少条
	 * @return
	 * 注意  type 为创建的类名  method 为方法名
	 */
	@SelectProvider(type = DrugPriceIfClass.class,method = "selectByChangeIF")
	public List<DrugpriceDruginformation> queryDrugprice(String pharmacycode, String drugcode, String commoname, String start, String end, int nowpage, int size);

	/**
	 * 多条件模糊查询,返回数量
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	@SelectProvider(type= DrugPriceIfClass.class,method="selectByChangeIFCount")
	public int countDrugprice(String pharmacycode,String drugcode,String commoname,String start,String end);

}
