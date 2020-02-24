package com.zhyy.services;

import com.zhyy.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  药品相关操作
 * @author
 */
public interface DrugServices
{
	/**
	 * 药品价格表列表查询
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
	 * 药品价格表总数查询
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	public int countDrugprice(String pharmacycode,String drugcode,String commoname,String start,String end);

	/**
	 * 药品销售登记表列表查询
	 * @param pharmacycode 药房编号
	 * @param drugcode  药品编号
	 * @param commoname 药品常用名称
	 * @param specialmedicine   是否是特殊药品
	 * @param idcard    购买人员身份证
	 * @param consumername  购买人员姓名
	 * @param salesperson 营业员
	 * @param start 销售开始时间
	 * @param end   销售结束时间
	 * @param nowpage   当前页数
	 * @param size  当前页数的大小
	 * @return
	 */
	public List<Drugsale> queryDrugSaleList(String pharmacycode,String drugcode,String commoname,String specialmedicine,String idcard,String consumername,String salesperson,String start,String end, int nowpage, int size);

	/**
	 * 药品销售登记表总数查询
	 * @param pharmacycode 药房编号
	 * @param drugcode  药品编号
	 * @param commoname 药品常用名称
	 * @param specialmedicine   是否是特殊药品
	 * @param idcard    购买人员身份证
	 * @param consumername  购买人员姓名
	 * @param salesperson 营业员
	 * @param start 销售开始时间
	 * @param end   销售结束时间
	 * @return
	 */
	public int countDrugSaleList(String pharmacycode,String drugcode,String commoname,String specialmedicine,String idcard,String consumername,String salesperson,String start,String end);

	/**
	 * @Description  通过药品常用名称和拼音码查找药品信息表
	 * @author xlx
	 * @Date 下午 22:28 2020/2/20 0020
	 * @Param commonname 常用名称
	 * @Param pincode 拼音码
	 * @Param page 拼音码
	 * @Param limit 拼音码
	 * @return
	 **/
	List<Druginformation> selectDruginformation(String commonname, String pincode);
}
