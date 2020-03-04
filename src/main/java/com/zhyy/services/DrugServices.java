package com.zhyy.services;

import com.zhyy.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

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
	 * 药品发药查询
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @param nowpage   当前页数
	 * @param size  当前页数的大小
	 * @return
	 */
	public List<DruginventoryDruginformation> querydruginventorylist(String pharmacycode,String classcode,String commoname, int nowpage, int size);

	/**
	 * 药品发药总数查询
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @return
	 */
	public int countdruginventorylist(String pharmacycode,String classcode,String commoname);

	/**
	 * 药房药品发药出库
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
	int insertDruginventoryOutbound(String drugcode,String time,String number,String lotnumber,String specialmedicine,String asktime,String receivetime,String operatingtime,String pharmacynumber,String asker,String price);

	/**
	 * 药房药品发药出库库存减少
	 * @param drugcode
	 * @param number
	 * @param lotnumber
	 * @return
	 */
	int updateDruginventoryNumber(String drugcode,String number,String lotnumber);

	/**
	 * 配伍禁忌
	 * @param drugcode1
	 * @param drugcode2
	 * @return
	 */
	int selectDrugcompatibilitycontraindications(String drugcode1,String drugcode2);

	/**
	 * 配伍禁忌列表
	 * @return
	 */
	List<Drugcompatibilitycontraindications> selectcompatibilityList(String drugcode, int nowpage, int size);

	/**
	 * 配伍禁忌列表总数
	 * @return
	 */
	int selectcountcompatibilityList(String drugcode);

	List<Druginformation> selectclasscode();

	/**
	 * 查找药品信息表中的drugcode
	 * @param drugcode
	 * @return
	 */
	List<Druginformation> queryDrugcode(String drugcode);

	/**
	 * 新增配伍禁忌
	 */
	int insertcompatibility(String drugcodeA,String drugcodeB,String contraindications);

	/**
	 * 根据drugcode和commoname查询药品信息
	 * @return
	 */
	List<Druginformation> selectdrugstore(String drugcode,String commoname);

	/**
	 * 更改药品的医保状态
	 * @return
	 */
	int updateDruginformationhealthinsurance(String healthinsurance,String drugcode,String commoname);


	/**
	 * @Description  通过药品常用名称和拼音码查找药品信息表
	 * @author xlx
	 * @Date 下午 22:28 2020/2/20 0020
	 * @Param commonname 常用名称
	 * @Param pincode 拼音码
	 * @return
	 **/
	List<Druginformation> selectDruginformation(String commoname, String pincode);

	/**
	 * @Description  药库药品出库
	 * @author xlx
	 * @Date 下午 17:26 2020/2/26 0026
	 * @Param
	 * @return
	 **/
	int insertOutbound(Vacation vac);

	/**
	 * 查询分类信息列表list
	 * @author cbd
	 * @return 返回查询的分类信息list
	 */
	List<DrugClass> selectDrugClass();

	/**
	 * 根据大类编号查询子类编号信息
	 * @author cbd
	 * @param parentCode 大类编号
	 * @return 返回查询的大类编号的最大子类编号加1
	 */
	public String selectDrugClassCode(String parentCode);

	/**
	 * 药品分类设置新增方法：根据新增的信息插入到分类表
	 * @author cbd
	 * @param drugClass 药品分类信息对象
	 * @return 返回整型值判断结果状态成功与否
	 */

	public int saveDrugClassSetInfo(DrugClass drugClass);

	/**
	 * 查询药品信息表的所有药品信息
	 * @author cbd
	 * @return 返回药品信息list
	 */
	public List<Druginformation> selectDrugInfo();

	/**
	 * 根据新增药品信息对象将其保存至药品信息表
	 * @param drugInformation 药品信息对象
	 * @return 返回int值判断保存是否成功
	 */
	public int saveDrugInfo(Druginformation drugInformation);

	/**
	 * @Description  药房入库，插入出入库表并更新库存
	 * @author xlx
	 * @Date 上午 7:21 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	int insertAndUpdate(Vacation vac);

	/**
	 * @return
	 * @Description 查询药房出入库明细
	 * @author xlx
	 * @Date 下午 16:12 2020/2/28 0028
	 * @Param drugcode 药品编号
	 * @Param lotnumber 批号
	 * @Param asker 申请人
	 * @Param outbound 出入库
	 * @Param start，end 操作时间的开始和结束
	 **/
	 List<Pharmacydrugschedule> selectPharmacyd(String drugcode, String lotnumber, String asker, String outbound, String start, String end);




	//查询药房--库存列表
	List<Druginventorytable> queryDrugInventoryList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	int countDrugInventoryList();

	//药品停用查询
	List<Druginventorytable> querydrugDiscontinuation(int pageInt,int limitInt,String where);
	//药品停用查询总数
	int countdrugDiscontinuation(String where);

	///低限报警的-------------------
	//查询药房--库存列表
	List<Druginventorytable> queryPharmacyLowLimitDrugsList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	int countPharmacyLowLimitDrugsList();


	///低限报警查询-------------------
	//查询药房--库存列表
	List<Druginventorytable> querypharmacyDrugsQuery(int pageInt,int limitInt,String where);
	//统计药房--库存列表数量
	int countpharmacyDrugsQuery(String where);

	///过期的-------------------
	//查询药房--库存列表
	List<Druginventorytable> queryDrugInventoryExpiredList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	int countDrugInventoryExpiredList();

	///过期查询-------------------
	//查询药房--库存列表
	List<Druginventorytable> queryexpiredQuery(int pageInt,int limitInt,String where);
	//统计药房--库存列表数量
	int countexpiredQuery(String where);

	///滞销的-------------------
	//查询药房--库存列表
	List<Druginventorytable> queryDrugInventoryUnsalableList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	int countDrugInventoryUnsalableList();

	///滞销查询-------------------
	//查询药房--库存列表
	List<Druginventorytable> queryunSaleQuery(int pageInt,int limitInt,String where);
	//统计药房--库存列表数量
	int countunSaleQuery(String where);



	//药品低限设置
	List<Drugstoredruginventory> queryDrugStoreInventoryList(int pageInt,int limitInt);
	//药品低限设置统计
	int countDrugStoreInventoryList();

	//药品低限设置查询
	List<Drugstoredruginventory> querylowLimitQuery(int pageInt,int limitInt,String where);
	int countlowLimitQuery(String where);

	//药品低限设置
	boolean lowestSetting(String drugCode,String setData);

	//盘点列表
	List<Druginventorytable> queryInventoryTableList(int pageInt,int limitInt);
	int countInventoryTableList();

	//盘点查询
	List<Druginventorytable> queryInventoryQuery(int pageInt,int limitInt,String where);
	int countInventoryQuery(String where);


	boolean deleteAfterInventory();
    //盘点之后
	List <AfterInventory>queryAfterInventoryList(int pageInt,int limitInt);
	int countAfterInventoryList();

	//盘点之后药房库存数量变更
	boolean updateDruginventoryCount(String drugcode,int finishedquantity);

	//录入盘点盈亏表
	boolean insertInventory2(String drugcode,String inventoryresults,String pharmacycode,String inventorytime);

	List<Inventorycheck> selectInventorycheck(String commonname,String specialmedicine);
	//List<Inventorycheck> selectInventorycheck(String commoname,String specialmedicine);

	boolean insertInventory(String drugcode,String specification,String drugunit,String lotnumber,int druginventorynumber,int relativequantity,int finishedquantity,double wholesaleprice,double relativeamount);

	/**
	 * 查询药库药品库存表信息
	 * @author cbd
	 * @return 返回查询结果集list
	 */
	public  List<Drugstoredruginventory> selectDrugStoreInventory();

	/**
	 * 根据药库入库信息对象作为参数保存入库信息
	 * @author cbd
	 * @param drugStoreDrugInventory 药库药品入库信息对象参数
	 * @return 返回保存结果状态int值 作为判断成功
	 */
	public int saveDrugStoreInventory(Drugstoredruginventory drugStoreDrugInventory);

	/**
	 * 药品采购登记
	 * @author cbd
	 * @param purchaseStatistics 药品采购信息对象
	 * @return 返回int型 判断
	 */
	public  int  savePurchaseStatistics(Purchasestatistics purchaseStatistics);

	/**
	 * 查询采购登记表的信息
	 * @author cbd
	 * @return 返回采购登记表的所有数据list
	 */
	public List<Purchasestatistics> selectPurchaseStatistics();




}
