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
	 * @param reviewtime
	 * @param operatingtime
	 * @param pharmacynumber
	 * @param asker
	 * @return
	 */
	int insertDruginventoryOutbound(String drugcode,String time,String number,String lotnumber,String specialmedicine,String asktime,String receivetime,String operatingtime,String pharmacynumber,String asker);

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
	 * @param drugcode
	 * @return
	 */
	int selectDrugcompatibilitycontraindications(String drugcode);



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


	/**
	 * @Description  药房库存查询（通过常用名称和是否为特殊药品）
	 * @author xlx
	 * @Date 下午 18:13 2020/2/28 0028
	 * @Param
	 * @return
	 **/
//	List<Druginformation> selectDruginformation(String commonname, String pincode);

	//查询药房--库存列表
	List<Druginventorytable> queryDrugInventoryList(int pageInt,int limitInt);
	//统计药房--库存列表数量
	int countDrugInventoryList();

	//查询药库--库存列表
	List<Drugstoredruginventory> queryDrugStoreInventoryList(int pageInt,int limitInt);
	//统计药库--库存列表数量
	int countDrugStoreInventoryList();

	//药品低限设置
	boolean lowestSetting(String drugCode,String setData);


	//盘点列表
	List<Druginventorytable> queryInventoryTableList(int pageInt,int limitInt);
	int countInventoryTableList();

	List<Inventorycheck> selectInventorycheck(String commonname,String specialmedicine);

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

}
