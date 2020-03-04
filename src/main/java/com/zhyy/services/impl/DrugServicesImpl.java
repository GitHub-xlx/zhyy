package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.*;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.services.DrugServices;
import com.zhyy.services.UserServices;
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
	@Autowired
	private UserServices userServices;

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
	public List<DruginventoryDruginformation> querydruginventorylist(String pharmacycode, String classcode, String commoname, int nowpage, int size)
	{
		return drugMapper.querydruginventorylist(pharmacycode,classcode,commoname,nowpage,size);
	}

	@Override
	public int countdruginventorylist(String pharmacycode, String classcode, String commoname)
	{
		return drugMapper.countdruginventorylist(pharmacycode, classcode, commoname);
	}

	@Override
	public int insertDruginventoryOutbound(String drugcode,String time,String number,String lotnumber,String specialmedicine,String asktime,String receivetime,String operatingtime,String pharmacynumber,String asker,String price)
	{
		return drugMapper.insertDruginventoryOutbound(drugcode, time, number, lotnumber, specialmedicine, asktime, receivetime, operatingtime, pharmacynumber, asker,price);
	}

	@Override
	public int updateDruginventoryNumber(String drugcode, String number, String lotnumber)
	{
		return drugMapper.updateDruginventoryNumber(drugcode, number, lotnumber);
	}

	@Override
	public int selectDrugcompatibilitycontraindications(String drugcode1,String drugcode2)
	{
		return drugMapper.selectDrugcompatibilitycontraindications(drugcode1, drugcode2);
	}

	@Override
	public List<Drugcompatibilitycontraindications> selectcompatibilityList(String drugcode,int nowpage, int size)
	{
		return drugMapper.selectcompatibilityList(drugcode, nowpage, size);
	}

	@Override
	public int selectcountcompatibilityList(String drugcode)
	{
		return drugMapper.selectcountcompatibilityList(drugcode);
	}

	@Override
	public List<Druginformation> queryDrugcode(String drugcode)
	{
		String where="1=1 ";
		if(drugcode!=null){
			where = drugcode.length()>0 ? where+" and drugcode <> '"+drugcode+"'" : where;
		}
		return drugMapper.queryDrugcode(where);
	}

	@Override
	public int insertcompatibility(String drugcodeA, String drugcodeB, String contraindications)
	{
		return drugMapper.insertcompatibility(drugcodeA, drugcodeB, contraindications);
	}

	@Override
	public List<Druginformation> selectclasscode()
	{
		return drugMapper.selectclasscode();
	}

	@Override
	public List<Druginformation> selectdrugstore(String drugcode, String commoname)
	{
		String where="1=1 ";
		if(drugcode!=null){
			where = drugcode.length()>0 ? where+" and drugcode like '%"+drugcode+"%'" : where;
		}
		if (commoname!=null){
			where = commoname.length()>0 ? where+" and commoname like '%"+commoname+"%'" : where;
		}
		return drugMapper.selectdrugstore(where);
	}

	@Override
	public int updateDruginformationhealthinsurance(String healthinsurance, String drugcode, String commoname)
	{
		return drugMapper.updateDruginformationhealthinsurance(healthinsurance,drugcode,commoname);
	}

	@Override
	public List<InventoryDruginformation> selectinventorylist(String pharmacycode, String drugcode, String inventoryresults, String commoname ,String start,String end)
	{
		return drugMapper.selectinventorylist(pharmacycode, drugcode, inventoryresults, commoname, start, end);
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
		User user = userServices.queryUserByAccount(vac.getApplyUser());
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertOutbound(vac, user.getPharmacycode(), time);
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
	public int insertAndUpdate(Vacation vac)
	{
		User user = userServices.queryUserByAccount(vac.getApplyUser());
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertPharmacyDrug(vac,user.getPharmacycode(),time);
		return drugMapper.updatePharmacy(vac.getList(),user.getPharmacycode());
	}

	@Override
	public List<Pharmacydrugschedule> selectPharmacyd(String drugcode, String lotnumber, String asker, String outbound, String start, String end)
	{
		String where="1=1 ";
		if(drugcode!=null){
			where = drugcode.length()>0 ? where+" and drugcode like '%"+drugcode+"%'" : where;
		}
		if (lotnumber!=null){
			where = lotnumber.length()>0 ? where+" and lotnumber like '%"+lotnumber+"%'" : where;
		}
		if (asker!=null){
			where = asker.length()>0 ? where+" and asker like '%"+asker+"%'" : where;
		}
		if (outbound!=null){
			where = outbound.length()>0 ? where+" and outbound = '"+outbound+"'" : where;
		}
		if (start!=null){
			where = start.length()>0 ? where+" and operatingtime > '"+start+"'" : where;
		}
		if (end!=null){
			where = end.length()>0 ? where+" and operatingtime < '"+end+"'" : where;
		}
		return drugMapper.selectPharmacyd(where);
	}

	@Override
	public List<Inventorycheck> selectInventorycheck(String commoname,String specialmedicine)
	{
		String where="1=1 ";
		if(commoname!=null){
			where = commoname.length()>0 ? where+" and commoname like '%"+commoname+"%'" : where+" and commoname like '%%'";
		}
		if (specialmedicine!=null){
			where = specialmedicine.length()>0 ? where+" and specialmedicine = '"+specialmedicine+"'" : where;
		}
		return drugMapper.selectInventorycheck(where);

	}

	@Override
	public boolean insertInventory(String drugcode, String specification, String drugunit, String lotnumber, int druginventorynumber, int relativequantity, int finishedquantity, double wholesaleprice, double relativeamount)
	{
		return drugMapper.insertInventory(drugcode,specification,drugunit,lotnumber,druginventorynumber,relativequantity,finishedquantity,wholesaleprice,relativeamount);
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

	@Override
	public List<Druginventorytable> queryDrugInventoryList(int pageInt, int limitInt)
	{
		return drugMapper.queryDrugInventoryList(pageInt,limitInt);
	}

	@Override
	public int countDrugInventoryList()
	{
		return drugMapper.countDrugInventoryList();
	}

	@Override
	public List<Druginventorytable> querydrugDiscontinuation(int pageInt, int limitInt, String where)
	{
		return drugMapper.querydrugDiscontinuation(pageInt,limitInt,where);
	}

	@Override
	public int countdrugDiscontinuation(String where)
	{
		return drugMapper.countdrugDiscontinuation(where);
	}

	@Override
	public List<Druginventorytable> queryPharmacyLowLimitDrugsList(int pageInt, int limitInt)
	{
		return drugMapper.queryPharmacyLowLimitDrugsList(pageInt,limitInt);
	}

	@Override
	public int countPharmacyLowLimitDrugsList()
	{
		return drugMapper.countPharmacyLowLimitDrugsList();
	}

	@Override
	public List<Druginventorytable> querypharmacyDrugsQuery(int pageInt, int limitInt, String where)
	{
		return drugMapper.querypharmacyDrugsQuery(pageInt,limitInt,where);
	}

	@Override
	public int countpharmacyDrugsQuery(String where)
	{
		return drugMapper.countpharmacyDrugsQuery(where);
	}

	@Override
	public List<Druginventorytable> queryDrugInventoryExpiredList(int pageInt, int limitInt)
	{
		return drugMapper.queryDrugInventoryExpiredList(pageInt,limitInt);
	}

	@Override
	public int countDrugInventoryExpiredList()
	{
		return drugMapper.countDrugInventoryExpiredList();
	}

	@Override
	public List<Druginventorytable> queryexpiredQuery(int pageInt, int limitInt, String where)
	{
		return drugMapper.queryexpiredQuery(pageInt,limitInt,where);
	}

	@Override
	public int countexpiredQuery(String where)
	{
		return drugMapper.countexpiredQuery(where);
	}

	@Override
	public List<Druginventorytable> queryDrugInventoryUnsalableList(int pageInt, int limitInt)
	{
		return drugMapper.queryDrugInventoryUnsalableList(pageInt,limitInt);
	}

	@Override
	public int countDrugInventoryUnsalableList()
	{
		return drugMapper.countDrugInventoryUnsalableList();
	}

	@Override
	public List<Druginventorytable> queryunSaleQuery(int pageInt, int limitInt, String where)
	{
		return drugMapper.queryunSaleQuery(pageInt,limitInt,where);
	}

	@Override
	public int countunSaleQuery(String where)
	{
		return drugMapper.countunSaleQuery(where);
	}


	@Override
	public List<Drugstoredruginventory> queryDrugStoreInventoryList(int pageInt, int limitInt)
	{
		return drugMapper.queryDrugStoreInventoryList(pageInt,limitInt);
	}

	@Override
	public int countDrugStoreInventoryList()
	{
		return drugMapper.countDrugStoreInventoryList();
	}

	@Override
	public List<Drugstoredruginventory> querylowLimitQuery(int pageInt, int limitInt, String where)
	{
		return drugMapper.querylowLimitQuery(pageInt,limitInt,where);
	}

	@Override
	public int countlowLimitQuery(String where)
	{
		return drugMapper.countlowLimitQuery(where);
	}

	@Override
	public boolean lowestSetting(String drugCode, String setData)
	{
		return drugMapper.lowestSetting(drugCode,setData);
	}

	@Override
	public List<Druginventorytable> queryInventoryTableList(int pageInt, int limitInt)
	{
		return drugMapper.queryInventoryTableList(pageInt,limitInt);
	}

	@Override
	public int countInventoryTableList()
	{
		return drugMapper.countInventoryTableList();
	}

	@Override
	public List<Druginventorytable> queryInventoryQuery(int pageInt, int limitInt, String where)
	{
		return drugMapper.queryInventoryQuery(pageInt,limitInt,where);
	}

	@Override
	public int countInventoryQuery(String where)
	{
		return drugMapper.countInventoryQuery(where);
	}

	@Override
	public boolean deleteAfterInventory()
	{
		return drugMapper.deleteAfterInventory();
	}

	@Override
	public List<AfterInventory> queryAfterInventoryList(int pageInt, int limitInt)
	{
		return drugMapper.queryAfterInventoryList(pageInt,limitInt);
	}

	@Override
	public int countAfterInventoryList()
	{
		return drugMapper.countAfterInventoryList();
	}

	@Override
	public boolean updateDruginventoryCount(String drugcode, int finishedquantity)
	{
		return drugMapper.updateDruginventoryCount(drugcode,finishedquantity);
	}

	@Override
	public boolean insertInventory2(String drugcode, String inventoryresults, String pharmacycode, String inventorytime)
	{
		return drugMapper.insertInventory2(drugcode,inventoryresults,pharmacycode,inventorytime);
	}

	@Override
	public int savePurchaseStatistics(Purchasestatistics purchaseStatistics)
	{
		return drugMapper.savePurchaseStatistics(purchaseStatistics);
	}

	@Override
	public List<Purchasestatistics> selectPurchaseStatistics()
	{
		return drugMapper.selectPurchaseStatistics();
	}
}
