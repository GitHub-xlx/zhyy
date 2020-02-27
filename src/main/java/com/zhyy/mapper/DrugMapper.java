package com.zhyy.mapper;


import com.zhyy.entity.*;
import com.zhyy.sqlifclass.DrugPriceIfClass;
import com.zhyy.sqlifclass.DrugSaleIfClass;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
	 * 药品价格表列表查询
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
	 * 药品价格表总数查询
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	@SelectProvider(type= DrugPriceIfClass.class,method="selectByChangeIFCount")
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
	@SelectProvider(type = DrugSaleIfClass.class,method = "selectsalelist")
	public List<Drugsale> queryDrugSaleList(String pharmacycode, String drugcode, String commoname, String specialmedicine, String idcard, String consumername,String salesperson, String start, String end, int nowpage, int size);

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
	@SelectProvider(type = DrugSaleIfClass.class,method = "countsalelist")
	public int countDrugSaleList(String pharmacycode,String drugcode,String commoname,String specialmedicine,String idcard,String consumername,String salesperson,String start,String end);

	/**
	 * @Description  查找药品信息表以及药品库存
	 * @author xlx
	 * @Date 下午 22:59 2020/2/20 0020
	 * @Param
	 * @return
	 **/
	@Select("select a.*,b.druginventory,b.lotnumber,b.productiondate,b.drugstatus from (select * from druginformation where ${where}) a" +
			" left join drugstoredruginventory b" +
			" on a.drugcode=b.drugcode  ")
	List<Druginformation> selectDruginformation(String where);

	/**
	 * @Description  批量插入药库出库
	 * @author xlx
	 * @Date 下午 21:03 2020/2/26 0026
	 * @Param
	 * @return
	 **/
	@Insert({
			"<script>",
			"insert into inboundoutboundschedule(drugcode, number, outbound,lotnumber,auditor,asker,pharmacycode,asktime,reviewtime,receivetime,operatingtime,treasury) values ",
			"<foreach collection='vac.list' item='item' index='index' separator=','>",
			"(#{item.drugcode}, #{item.number},'出库',#{item.lotnumber},#{vac.auditor},#{vac.applyUser},#{pharmacycode},#{vac.applyTime},#{vac.auditTime},#{vac.medicineTime},#{time},#{vac.dispenser})",
			"</foreach>",
			"</script>"
	})
	int insertOutbound(Vacation vac,String pharmacycode,String time);

	/**
	 * @Description  修改药库库存
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 **/
	@Update({
			"<script>" +
			"<foreach collection = 'list' item ='item' open='' close='' separator=';'>" +
			"update drugstoredruginventory set druginventory =(select druginventory from drugstoredruginventory where drugcode=#{item.drugcode})- #{item.number} " +
			"where  drugcode =#{item.drugcode} and lotnumber=#{item.lotnumber}" +
			"</foreach></script>"

	})
	int updatePharmacyInventory(List<Druginformation> list);

	/**
	 * @Description  批量插入药房入库
	 * @author xlx
	 * @Date 下午 21:03 2020/2/26 0026
	 * @Param
	 * @return
	 **/
	@Insert({
			"<script>",
			"insert into inboundoutboundschedule(drugcode, number, outbound,lotnumber,auditor,asker,pharmacycode,asktime,reviewtime,receivetime,operatingtime,treasury) values ",
			"<foreach collection='vac.list' item='item' index='index' separator=','>",
			"(#{item.drugcode}, #{item.number},'出库',#{item.lotnumber},#{vac.auditor},#{vac.applyUser},#{pharmacycode},#{vac.applyTime},#{vac.auditTime},#{vac.medicineTime},#{time},#{vac.dispenser})",
			"</foreach>",
			"</script>"
	})
	int insertPharmacyDrug(Vacation vac,String pharmacycode,String time);

	/**
	 * @Description  修改药房库存
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 **/
	@Update({
			"<script>" +
			"<foreach collection = 'list' item ='item' open='' close='' separator=';'>" +
			"update drugstoredruginventory set druginventory =(select druginventory from drugstoredruginventory where drugcode=#{item.drugcode})- #{item.number} " +
			"where  drugcode =#{item.drugcode} and lotnumber=#{item.lotnumber}" +
			"</foreach></script>"

	})
	int updatePharmacy(List<Druginformation> list);
}
