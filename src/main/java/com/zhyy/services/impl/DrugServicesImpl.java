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
	public int insertDruginventoryOutbound(String drugcode,String time,String number,String lotnumber,String specialmedicine,String asktime,String receivetime,String operatingtime,String pharmacynumber,String asker)
	{
		return drugMapper.insertDruginventoryOutbound(drugcode, time, number, lotnumber, specialmedicine, asktime, receivetime, operatingtime, pharmacynumber, asker);
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
	public List<Druginformation> selectDruginformation(String commonname, String pincode)
	{
		String where="1=1 ";
		if(commonname!=null){
			where = commonname.length()>0 ? where+" and commonname = '"+commonname+"'" : where;
		}
		if (pincode!=null){
			where = pincode.length()>0 ? where+" and pincode = '"+pincode+"'" : where;
		}
		return drugMapper.selectDruginformation(where);

	}

	@Override
	public int insertOutbound(Vacation vac)
	{
		String[] split = vac.getDurgResult().split(",");
		String pharmacycode = split[1];
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertOutbound(vac, pharmacycode, time);
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
		return drugMapper.updatePharmacy(vac.getList());
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
	public List<Inventorycheck> selectInventorycheck(String commonname,String specialmedicine)
	{
		String where="1=1 ";
		if(commonname!=null){
			where = commonname.length()>0 ? where+" and commonname like '%"+commonname+"%'" : where+" and commonname like '%%'";
		}
		if (specialmedicine!=null){
			where = specialmedicine.length()>0 ? where+" and specialmedicine = '"+specialmedicine+"'" : where;
		}
		return drugMapper.selectInventorycheck(where);

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
	public int savePurchaseStatistics(Purchasestatistics purchaseStatistics)
	{
		return drugMapper.savePurchaseStatistics(purchaseStatistics);
	}

	@Override
	public List<Purchasestatistics> selectPurchaseStatistics()
	{
		return drugMapper.selectPurchaseStatistics();
	}

	/**
	 * @Description  药房退库，插入出入库表并更新库存（药房药库都要）
	 * @author xlx
	 * @Date 上午 7:21 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	@Override
	public int pharmacyWithdrawal(Vacation vac)
	{
		User user = userServices.queryUserByAccount(vac.getApplyUser());
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertPharmacyOutbound(vac,user.getPharmacycode(),time,"出库","退库");
		drugMapper.updatePharmacywithdrawal(vac.getList());
		drugMapper.insertDrugstore(vac,user.getPharmacycode(),time,"入库");
		return drugMapper.updateDepot(vac.getList());
	}
	/**
	 * @Description  药库退库，插入出入库表并更新库存（药库）
	 * @author xlx
	 * @Date 上午 7:21 2020/2/28 0028
	 * @Param
	 * @return
	 **/
	@Override
	public int depotwithdrawal(Vacation vac)
	{
		User user = userServices.queryUserByAccount(vac.getApplyUser());
		String time = TimeUtil.getTime(new Date());
		drugMapper.insertDrugstore(vac,user.getPharmacycode(),time,"退库");
		return drugMapper.updateDepotwithdrawal(vac.getList());
	}
	/**
	 * @Description  通过常用名称和特殊分类查询药库库存表
	 * @author xlx
	 * @Date 下午 17:47 2020/3/3 0003
	 * @Param
	 * @return
	 **/
	@Override
	public List<Inventorycheck> selectStoreInventorycheck(String commoname, String specialmedicine)
	{
		String where="1=1 ";
		if(commoname!=null){
			where = commoname.length()>0 ? where+" and commoname like '%"+commoname+"%'" : where+" and commoname like '%%'";
		}
		if (specialmedicine!=null){
			where = specialmedicine.length()>0 ? where+" and specialmedicine = '"+specialmedicine+"'" : where;
		}
		return drugMapper.selectStoreInventorycheck(where);
	}

	/**
	 * @Description  药品报损
	 * @author xlx
	 * @Date 下午 23:08 2020/3/3 0003
	 * @Param
	 * @return
	 **/
	@Override
	public int insertBreakdownOfDrugs(Vacation vac)
	{
		User user = userServices.queryUserByAccount(vac.getApplyUser());
		 //插入报损明细表
		drugMapper.insertBreakdownOfDrugs(vac,user.getPharmacycode());
//		 修改药房库存
		drugMapper.updatePharmacydamaged(vac.getList());
		return 0;
	}

	@Override
	public List<Breakdownofdrugs> selectBreakdownOfDrugs(String damagedtype, String commoname, String start, String end)
	{
		String where1="1=1 ";
		String where2="1=1 ";
		if(commoname!=null){
			where1 = commoname.length()>0 ? where1+" and commoname like '%"+commoname+"%'" : where1;
		}
		if (damagedtype!=null){
			where2 = damagedtype.length()>0 ? where2+" and damagedtype = '"+damagedtype+"'" : where2;
		}
		if (start!=null){
			where2 = start.length()>0 ? where2+" and losstime > '"+start+"'" : where2;
		}
		if (end!=null){
			where2 = end.length()>0 ? where2+" and losstime < '"+end+"'" : where2;
		}
		return drugMapper.selectBreakdownOfDrugs(where1,where2);
	}

	@Override
	public List<GainAndLoss> gainAndLoss()
	{
		return drugMapper.gainAndLoss();
	}

	@Override
	public int updateStorageState(String drugCode, String lotNumber)
	{
		return drugMapper.updateStorageState(drugCode,lotNumber);
	}

	@Override
	public List<Inboundoutboundschedule> selectInAndOutBoundDrug(String drugcode)
	{
		String where="1=1 ";
		if(drugcode!=null){
			where = drugcode.length()>0 ? where+" and commonname like '%"+drugcode+"%'" : where+" and drugcode like '%%'";
		}

		return drugMapper.selectInAndOutBoundDrug(where);
	}
}
