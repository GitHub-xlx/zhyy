package com.zhyy.mapper;


import com.zhyy.entity.*;
import com.zhyy.sqlifclass.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;
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
	 * 药品分药查询
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @param nowpage   当前页数
	 * @param size  当前页数的大小
	 * @return
	 */
	@SelectProvider(type = DrugDistributionIfClass.class,method = "selectdruginventorylist")
	public List<DruginventoryDruginformation> querydruginventorylist(String pharmacycode,String classcode,String commoname, int nowpage, int size);

	/**
	 *  药品分药总数查询
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @return
	 */
	@SelectProvider(type = DrugDistributionIfClass.class,method = "countdruginventorylist")
	public int countdruginventorylist(String pharmacycode,String classcode,String commoname);

	/**
	 * 批量插入药房发药出库
	 * @param drugcode
	 * @param time
	 * @param number
	 * @param lotnumber
	 * @param specialmedicine
	 * @param asktime
	 * @param receivetime
	 * @param operatingtime
	 * @param pharmacynumber
	 * @param asker
	 * @return
	 */
	@Insert({
			"<script>",
			"insert into pharmacydrugschedule(drugcode,time,number,outbound,lotnumber,specialmedicine,outboundtype,auditor,asktime,reviewtime,receivetime,operatingtime,pharmacynumber,asker,price) values ",
			"(#{drugcode}, #{time},#{number},'出库',#{lotnumber},#{specialmedicine},'出库','无',#{asktime},null,#{receivetime},#{operatingtime},#{pharmacynumber},#{asker},#{price})",
			"</script>"
	})
	int insertDruginventoryOutbound(String drugcode,String time,String number,String lotnumber,String specialmedicine,String asktime,String receivetime,String operatingtime,String pharmacynumber,String asker,String price);

	/**
	 * 更改药房药品库存数量
	 * @param drugcode
	 * @param number
	 * @param lotnumber
	 * @return
	 */
	@Update({
			"<script>",
			"update druginventorytable set druginventorynumber = druginventorynumber-#{number} where drugcode = #{drugcode} and lotnumber = #{lotnumber}",
			"</script>"
	})
	int updateDruginventoryNumber(String drugcode,String number,String lotnumber);

	/**
	 * 查找药品配伍禁忌
	 * @param drugcode1
	 * @param drugcode2
	 * @return
	 */
	@Select("select count(*) from drugcompatibilitycontraindications  where drugcodeA= '${drugcode1}' and drugcodeB= '${drugcode2}'")
	int selectDrugcompatibilitycontraindications(String drugcode1,String drugcode2);

	/**
	 * 药品配伍禁忌列表
	 */
	@SelectProvider(type = CompatibilityIfClass.class,method = "selectcompatibilityList")
	List<Drugcompatibilitycontraindications> selectcompatibilityList(String drugcode, int nowpage, int size);

	/**
	 * 药品配伍禁忌列表总数
	 */
	@SelectProvider(type = CompatibilityIfClass.class,method = "selectcountcompatibilityList")
	int selectcountcompatibilityList(String drugcode);

	/**
	 * 查找药品信息表中所有的drugcode
	 */
	@Select("select * from druginformation where ${where}")
	List<Druginformation> queryDrugcode(String where);

	/**
	 * 新增配伍禁忌
	 */
	@Insert({
			"<script>",
			"insert into drugcompatibilitycontraindications(drugcodeA,drugcodeB,contraindications) values ",
			"(#{drugcodeA}, #{drugcodeB},#{contraindications})",
			"</script>"
	})
	int insertcompatibility(String drugcodeA,String drugcodeB,String contraindications);

	@Select("select distinct classcode from druginformation")
	List<Druginformation> selectclasscode();

	@Select("select * from druginformation where ${where}")
	List<Druginformation> selectdrugstore(String where);

	/**
	 * 更改药品的医保状态
	 * @return
	 */
	@Update({
			"<script>",
			"update druginformation set healthinsurance = #{healthinsurance} where drugcode = #{drugcode} and commoname = #{commoname}",
			"</script>"
	})
	int updateDruginformationhealthinsurance(String healthinsurance,String drugcode,String commoname);

	/**
	 * 药房盘盈盘亏表
	 * @return
	 */
	@SelectProvider(type = InventoryDruginformationIfClass.class,method = "selectinventorylist")
	List<InventoryDruginformation> selectinventorylist(String pharmacycode, String drugcode, String inventoryresults, String commoname ,String start,String end);

	/**
	 * @Description  查找药品信息表
	 * @author xlx
	 * @Date 下午 22:59 2020/2/20 0020
	 * @Param
	 * @return
	 **/
	@Select("select a.*,b.druginventory,b.lotnumber,b.productiondate,b.drugstatus  from (select * from druginformation where ${where}) a," +
			" drugstoredruginventory b where a.drugcode=b.drugcode")
	List<Druginformation> selectDruginformation(String where);

	//查询药房--库存列表
	@Select("select A.*,B.commoname from druginventorytable A,druginformation B where A.drugcode=B.drugcode  limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryDrugInventoryList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable")
	int countDrugInventoryList();

	//药品停用查询
	@Select("select A.*,B.commoname from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode  limit #{pageInt},#{limitInt}")
	List<Druginventorytable> querydrugDiscontinuation(int pageInt,int limitInt,String where);
	//药品停用查询总数
	@Select("select count(*) from druginventorytable A ,druginformation B  where ${where} and A.drugcode=B.drugcode")
	public int countdrugDiscontinuation(String where);


	///低限报警---------------------------
	//查询药房--库存列表
	@Select("select A.*,B.commoname from druginventorytable A,druginformation B where A.drugcode=B.drugcode and A.druginventorynumber<A.drugminimums  limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryPharmacyLowLimitDrugsList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable")
	int countPharmacyLowLimitDrugsList();

	///低限报警查询-------------------
	//查询药房--库存列表
	@Select("select A.*,B.commoname from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode and A.druginventorynumber<A.drugminimums  limit #{pageInt},#{limitInt}")
	List<Druginventorytable> querypharmacyDrugsQuery(int pageInt,int limitInt,String where);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode and A.druginventorynumber<A.drugminimums")
	int countpharmacyDrugsQuery(String where);


	///过期的---------------------------
	//查询药房--库存列表
	@Select("select A.*,B.commoname,B.shelflife from druginventorytable A,druginformation B where A.drugcode=B.drugcode and A.drugstatus= '已过期' limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryDrugInventoryExpiredList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable")
	int countDrugInventoryExpiredList();

	///过期查询-------------------
	//查询药房--库存列表
	@Select("select A.*,B.commoname,B.shelflife from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode and A.drugstatus= '已过期' limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryexpiredQuery(int pageInt,int limitInt,String where);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode and A.drugstatus= '已过期'")
	int countexpiredQuery(String where);


	///滞销的-------------------
	//查询药房--库存列表
	@Select("select A.*,B.commoname,C.receivetime from druginventorytable A,druginformation B,pharmacydrugschedule C where A.drugcode=B.drugcode and B.drugcode=C.drugcode and A.drugstatus= '已滞销' and C.outbound = '入库' limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryDrugInventoryUnsalableList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	@Select("select count(*) from druginventorytable")
	int countDrugInventoryUnsalableList();

	///滞销查询-------------------
	@Select("select A.*,B.commoname,C.receivetime from druginventorytable A,druginformation B,pharmacydrugschedule C where ${where} and A.drugcode=B.drugcode and B.drugcode=C.drugcode and A.drugstatus= '已滞销' and C.outbound = '入库' limit #{pageInt},#{limitInt}")
	List<Druginventorytable> queryunSaleQuery(int pageInt,int limitInt,String where);
	@Select("select count(*) from druginventorytable A,druginformation B,pharmacydrugschedule C where ${where} and A.drugcode=B.drugcode and B.drugcode=C.drugcode and A.drugstatus= '已滞销' and C.outbound = '入库' ")
	int countunSaleQuery(String where);



	//药品低限设置
	@Select("select A.*,B.commoname from drugstoredruginventory A,druginformation B where A.drugcode=B.drugcode  limit #{pageInt},#{limitInt}")
	List<Drugstoredruginventory> queryDrugStoreInventoryList(int pageInt,int limitInt);
	//药品低限设置统计
	@Select("select count(*) from drugstoredruginventory")
	int countDrugStoreInventoryList();

	//药品低限设置查询
	@Select("select A.*,B.commoname from drugstoredruginventory A,druginformation B where ${where} and A.drugcode=B.drugcode  limit #{pageInt},#{limitInt}")
	List<Drugstoredruginventory> querylowLimitQuery(int pageInt,int limitInt,String where);
	@Select("select count(*) from drugstoredruginventory A,druginformation B where ${where} and A.drugcode=B.drugcode")
	int countlowLimitQuery(String where);


	//药品低限设置
	@Update("update drugstoredruginventory set drugminimums = #{setData} where drugcode = #{drugCode}")
	boolean lowestSetting(String drugCode,String setData);

	//盘点列表
	@Select("select A.drugcode,B.commoname,B.specification,A.drugunit,A.lotnumber,A.druginventorynumber,B.wholesaleprice from druginventorytable A,druginformation B where A.drugcode=B.drugcode")
	List<Druginventorytable> queryInventoryTableList(int pageInt,int limitInt);
	@Select("select count(*) from druginventorytable")
	int countInventoryTableList();

	//盘点查询
	@Select("select A.drugcode,B.commoname,B.specification,A.drugunit,A.lotnumber,A.druginventorynumber,B.wholesaleprice from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode")
	List<Druginventorytable> queryInventoryQuery(int pageInt,int limitInt,String where);
	@Select("select count(*) from druginventorytable A,druginformation B where ${where} and A.drugcode=B.drugcode")
	int countInventoryQuery(String where);

	//清理盘点数据
	@Delete("delete from afterinventory")
	boolean deleteAfterInventory();

	//盘点之后查询
	@Select("select A.*,B.commoname,B.specification,B.wholesaleprice from afterinventory A,druginformation B where A.drugcode=B.drugcode")
	List <AfterInventory>queryAfterInventoryList(int pageInt,int limitInt);
	@Select("select count(*) from afterinventory")
	int countAfterInventoryList();


	//盘点之后录入盘点结果
    @Insert("insert into afterinventory (drugcode,specification,drugunit,lotnumber,druginventorynumber,relativequantity,finishedquantity,wholesaleprice,relativeamount)" +
		    " values (#{drugcode},#{specification},#{drugunit},#{lotnumber},#{druginventorynumber},#{relativequantity},#{finishedquantity},#{wholesaleprice},#{relativeamount})")
    boolean insertInventory(String drugcode,String specification,String drugunit,String lotnumber,int druginventorynumber,int relativequantity,int finishedquantity,double wholesaleprice,double relativeamount);

	//盘点之后药房库存数量调整
	@Update("update druginventorytable set druginventorynumber = #{finishedquantity} where drugcode = #{drugcode}")
	boolean updateDruginventoryCount(String drugcode,int finishedquantity);

	//盘点之后录入盘点盈亏表
	@Insert("insert into inventory (drugcode,inventoryresults,pharmacycode,inventorytime)" +
			" values (#{drugcode},#{inventoryresults},#{pharmacycode},#{inventorytime})")
	boolean insertInventory2(String drugcode, String inventoryresults, String pharmacycode, String inventorytime);





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
			"update drugstoredruginventory set druginventory =druginventory- #{item.number} " +
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
	 * 	"<script>" +
	 * 			"<foreach collection = 'list' item ='item' open='' close='' separator=';'>" +
	 * 			"update drugstoredruginventory set druginventory =(select druginventory from drugstoredruginventory where drugcode=#{item.drugcode})+ #{item.number} " +
	 * 			"where  drugcode =#{item.drugcode} and lotnumber=#{item.lotnumber}" +
	 * 			"</foreach></script>"
	 *
	 **/
	@Update({
			"<script>" +
					"<foreach collection='list' separator=';' item='i' >" +
					"INSERT INTO drugstoredruginventory" +
					"(drugcode,druginventorynumber,drugminimums,drugunit,lotnumber,specialmedicine,productiondate,drugstatus,pharmacynumber) " +
					"VALUE(#{i.drugcode},#{i.number},'0',#{i.pharmacyunit},#{i.lotnumber},#{i.specialmedicine},#{i.productiondate},#{i.drugstatus},#{pharmacycode}) " +
					"ON DUPLICATE KEY UPDATE " +
					"druginventorynumber=druginventorynumber+ #{i.number}" +
					"</foreach>" +
					"</script>"
	})
	int updatePharmacy(List<Druginformation> list,String pharmacycode);


	/**
	 * 查询分类信息列表list
	 * @author cbd
	 * @return 返回查询的分类信息list
	 */
	@Select("SELECT b.dcid,a.classcode as parentcode,a.classname as parentname,b.classcode,b.classname\n" + "FROM `drugclassification` a,`drugclassification` b where a.classcode=b.parentcode")
	List<DrugClass> selectDrugClass();

	/**
	 * 根据大类编号查询子类编号信息
	 * @author cbd
	 * @param parentCode 大类编号
	 * @return 返回查询的大类编号的最大子类编号加1
	 */
	@Select("SELECT classcode+1 AS classcode " + " FROM drugclassification  T " + " WHERE T.parentcode=#{parentCode} ORDER BY classcode desc LIMIT 1")
	public String selectDrugClassCode(String parentCode);

	/**
	 * 药品分类设置新增方法：根据新增的信息插入到分类表
	 * @author cbd
	 * @param drugClass 药品分类信息对象
	 * @return 返回整型值判断结果状态成功与否
	 */

	@Insert("INSERT INTO `drugclassification`(`classcode`, `classname`, `parentcode`) VALUES ( #{classcode}, #{classname}, #{parentcode})")
	public int saveDrugClassSetInfo(DrugClass drugClass);

	/**
	 * 查询药品信息表的所有药品信息
	 * @author cbd
	 * @return 返回药品信息list
	 */
	@Select("SELECT * FROM druginformation")
	public List<Druginformation> selectDrugInfo();

	/**
	 * 根据新增药品信息对象将其保存至药品信息表
	 * @param drugInformation 药品信息对象
	 * @return 返回int值判断保存是否成功
	 */
	@Insert("INSERT INTO druginformation (`diid`, `drugcode`, `barcode`, `classcode`, `productname`, `commoname`, `specification`, `dosageform`, `drugdepotunit`, `pharmacyunit`, `prescribeunit`, `reductionformula`, `dosage`, `pincode`, `supplier`, `antibiotic`, `specialmedicine`, `approvalnumber`, `healthinsurance`, `price`, `wholesaleprice`,`additionrate`, `precautions`, `shelflife`)  values (#{diid}, #{drugcode}, #{barcode}, #{classcode}, #{productname}, #{commoname}, #{specification}, #{dosageform}, #{drugdepotunit}, #{pharmacyunit}, #{prescribeunit}, #{reductionformula}, #{dosage}, #{pincode}, #{supplier}, #{antibiotic}, #{specialmedicine}, #{approvalnumber}, #{healthinsurance}, #{price}, #{wholesaleprice}, #{additionrate}, #{precautions}, #{shelflife})")
	public int saveDrugInfo(Druginformation drugInformation);

	/**
	 * @Description  查询药房出入库明细表
	 * @author xlx
	 * @Date 下午 16:08 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	@Select("SELECT * FROM pharmacydrugschedule where ${where}")
	List<Pharmacydrugschedule> selectPharmacyd(String where);
	/**
	 * 查询药库药品库存表信息
	 * @author cbd
	 * @return 返回查询结果集list
	 */
	@Select("SELECT * FROM  drugstoredruginventory")
	public  List<Drugstoredruginventory> selectDrugStoreInventory();

	/**
	 * 根据药库入库信息对象作为参数保存入库信息
	 * @author cbd
	 * @param drugStoreDrugInventory 药库药品入库信息对象参数
	 * @return 返回保存结果状态int值 作为判断成功
	 */
	@Insert("INSERT INTO `drugstoredruginventory`(`dsdiid`, `drugcode`, `druginventory`, `drugminimums`, `lotnumber`, `productiondate`, `drugstatus`)  VALUES (#{dsdiid},#{drugcode},#{druginventory},#{drugminimums},#{lotnumber},#{productiondate},#{drugstatus})")
	public int saveDrugStoreInventory(Drugstoredruginventory drugStoreDrugInventory);


	/**
	 * @Description  药房库存查询（通过常用名称）
	 * @author xlx
	 * @Date 下午 17:52 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	@Select("select a.commoname,b.* from (SELECT drugcode,commoname FROM druginformation where ${where}) a,"
			+ " druginventorytable b where a.drugcode=b.drugcode")
	List<Inventorycheck> selectInventorycheck(String where);

	/**
	 * 药品采购登记,即新增采购药品信息
	 * @author cbd
	 * @param purchaseStatistics 药品采购信息对象
	 * @return 返回int型 判断
	 */
	@Insert("INSERT INTO `purchasestatistics`( `drugcode`, `purchasenumber`, `totalpurchasemount`, `purchasetime`, `buyer`, `operator`, `state`, `lotnumber`, `date`)  VALUES  ( #{drugcode},#{ purchasenumber},#{ totalpurchasemount},#{ purchasetime},#{ buyer},#{ operator},#{ state},#{ lotnumber},#{ date})")
	int  savePurchaseStatistics(Purchasestatistics purchaseStatistics);

	/**
	 * 查询采购登记表的信息
	 * @author cbd
	 * @return 返回采购登记表的所有数据list
	 */
	@Select("select * from purchasestatistics ")
	List<Purchasestatistics> selectPurchaseStatistics();

	/**
	 * @Description  批量插入药库出入库
	 * @author xlx
	 * @Date 下午 21:03 2020/2/26 0026
	 * @Param
	 * @return
	 **/
	@Insert({
			"<script>",
			"insert into inboundoutboundschedule(drugcode, number, outbound,lotnumber,auditor,asker,pharmacycode,asktime,reviewtime,receivetime,operatingtime,treasury) values ",
			"<foreach collection='vac.list' item='item' index='index' separator=','>",
			"(#{item.drugcode}, #{item.number},#{outbound},#{item.lotnumber},#{vac.auditor},#{vac.applyUser},#{pharmacycode},#{vac.applyTime},#{vac.auditTime},#{vac.medicineTime},#{time},#{vac.dispenser})",
			"</foreach>",
			"</script>"
	})
	int insertDrugstore(Vacation vac,String pharmacycode,String time,String outbound);

	/**
	 * @Description  修改药库库存（退库）
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 **/
	@Update({
			"<script>" +
					"<foreach collection = 'list' item ='item' open='' close='' separator=';'>" +
					"update drugstoredruginventory set druginventory =druginventory- #{item.number} " +
					"where  drugcode =#{item.drugcode} and lotnumber=#{item.lotnumber}" +
					"</foreach></script>"

	})
	int updateDepotwithdrawal(List list);
	/**
	 * @Description  修改药库库存（药房退库）
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 **/
	@Update({
			"<script>" +
					"<foreach collection = 'list' item ='item' open='' close='' separator=';'>" +
					"update drugstoredruginventory set druginventory =druginventory+ #{item.number} " +
					"where  drugcode =#{item.drugcode} and lotnumber=#{item.lotnumber}" +
					"</foreach></script>"

	})
	int updateDepot(List list);
	/**
	 * @Description  批量插入药房出入库
	 * @author xlx
	 * @Date 下午 21:03 2020/2/26 0026
	 * @Param
	 * @return
	 **/
	@Insert({
			"<script>",
			"insert into pharmacydrugschedule(drugcode, number, outbound,lotnumber,specialmedicine,outboundtype,auditor,asker,pharmacynumber,asktime,reviewtime,operatingtime) values ",
			"<foreach collection='vac.list' item='item' index='index' separator=','>",
			"(#{item.drugcode}, #{item.number},#{outbound},#{item.lotnumber},#{item.specialmedicine},#{outboundtype},#{vac.auditor},#{vac.applyUser},#{pharmacycode},#{vac.applyTime},#{vac.auditTime},#{time})",
			"</foreach>",
			"</script>"
	})
	int insertPharmacyOutbound(Vacation vac,String pharmacycode,String time,String outbound,String outboundtype);
	/**
	 * 查询盘盈盘亏结果
	 * @return
	 */
	@Select("select A.*,B.commoname from inventory A,druginformation B where A.drugcode = B.drugcode")
	List<GainAndLoss> gainAndLoss();

	/**
	 * @Description  修改药房库存(退库)
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 * 	"<script>" +
	 * 					"<foreach collection='list' separator=';' item='i' >" +
	 * 					"INSERT INTO druginventorytable" +
	 * 					"(drugcode,druginventorynumber,drugminimums,drugunit,lotnumber,specialmedicine,productiondate,drugstatus,pharmacynumber) " +
	 * 					"VALUE(#{i.drugcode},#{i.number},'0',#{i.pharmacyunit},#{i.lotnumber},#{i.specialmedicine},#{i.productiondate},#{i.drugstatus},#{pharmacycode}) " +
	 * 					"ON DUPLICATE KEY UPDATE " +
	 * 					"druginventorynumber=druginventorynumber+ #{i.number}" +
	 * 					"</foreach>" +
	 * 					"</script>"
	 **/
	@Update({
			"<script>" +
					"<foreach collection='list' separator=';' item='i' >" +
					"update druginventorytable set druginventorynumber =druginventorynumber- #{i.number} " +
					"where  drugcode =#{i.drugcode} and lotnumber=#{i.lotnumber}" +
					"</foreach>" +
					"</script>"
	})
	int updatePharmacywithdrawal(List list);

	/**
	 * @Description  药库库存查询（通过常用名称）
	 * @author xlx
	 * @Date 下午 17:52 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	@Select("select a.commoname,b.* from (SELECT drugcode,commoname FROM druginformation where ${where}) a,"
			+ " druginventorytable b where a.drugcode=b.drugcode")
	List<Inventorycheck> selectStoreInventorycheck(String where);

	/**
	 * @Description  插入药品报损表
	 * @author xlx
	 * @Date 上午 9:58 2020/3/4 0004
	 * @Param
	 * @return
	 **/
	@Insert({
			"<script>",
			"insert into breakdownofdrugs(drugcode, damagedtype, lossreporter,lossreport,losstime,losscount,lossamount,lotnumber,drugstate,auditor,reviewtime,provepath,pharmacycode) values ",
			"<foreach collection='vac.list' item='item' index='index' separator=','>",
			"(#{item.drugcode}, #{vac.damagedtype}, #{vac.applyUser},#{vac.reason},#{vac.applyTime},#{item.losscount},#{item.lossamount},#{item.lotnumber},#{item.drugstatus},#{vac.auditor},#{vac.auditTime},#{vac.provepath},#{pharmacycode})",
			"</foreach>",
			"</script>"
	})
	int insertBreakdownOfDrugs(Vacation vac,String pharmacycode);

	/**
	 * @Description  查询药品报损表
	 * @author xlx
	 * @Date 上午 10:16 2020/3/4 0004
	 * @Param
	 * @return
	 **/
	@Select("select a.commoname,b.* from (SELECT drugcode,commoname FROM druginformation where ${where1}) a,"
			+ " (SELECT * FROM breakdownofdrugs where ${where2}) b where a.drugcode=b.drugcode")
	List<Breakdownofdrugs> selectBreakdownOfDrugs(String where1,String where2);

	/**
	 * @Description  修改药房库存(报损)
	 * @author xlx
	 * @Date 上午 7:08 2020/2/27 0027
	 * @Param
	 * @return
	 **/
	@Update({
			"<script>" +
					"<foreach collection='list' separator=';' item='i' >" +
					"update druginventorytable set druginventorynumber =druginventorynumber- #{i.losscount} " +
					"where  drugcode =#{i.drugcode} and lotnumber=#{i.lotnumber}" +
					"</foreach>" +
					"</script>"
	})
	int updatePharmacydamaged(List list);
}
