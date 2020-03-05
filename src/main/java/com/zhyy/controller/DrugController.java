package com.zhyy.controller;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhyy.aspect.IgnoreLog;
import com.zhyy.entity.*;
import com.zhyy.services.DrugServices;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/drugController")
public class DrugController
{
	@Autowired
	private DrugServices drugServices;

	/**
	 * 药房价格查询
	 * @return
	 */
	@RequestMapping("/selectprice")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectprice(String drugcode, String commoname, String start, String end, String page, String limit, HttpServletRequest request)
	{

		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		User user = (User) request.getSession().getAttribute("user");
		List<DrugpriceDruginformation> drugprices = null;
		int count = 0;
		drugprices = drugServices.queryDrugprice(user.getPharmacycode(), drugcode, commoname, start, end, pageInt, limitInt);

		count = drugServices.countDrugprice(user.getPharmacycode(), drugcode, commoname, start, end);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(drugprices);
		return tableMsg;
	}

	/**
	 * 药房销售登记表查询
	 * @return
	 */
	@RequestMapping("/selectsale")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectsale(String drugcode, String commoname, String specialmedicine, String idcard, String consumername, String salesperson, String start, String end, String page, String limit, HttpServletRequest request)
	{

		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		User user = (User) request.getSession().getAttribute("user");
		List<Drugsale> drugsales = null;
		int count = 0;
		drugsales = drugServices.queryDrugSaleList(user.getPharmacycode(), drugcode, commoname, specialmedicine, idcard, consumername, salesperson, start, end, pageInt, limitInt);

		count = drugServices.countDrugSaleList(user.getPharmacycode(), drugcode, commoname, specialmedicine, idcard, consumername, salesperson, start, end);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(drugsales);
		return tableMsg;
	}

	/**
	 * 药房药品发药信息查询
	 *
	 * @param classcode
	 * @param commoname
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectdruginventory")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectdruginventory(String classcode, String commoname, String page, String limit, HttpServletRequest request)
	{

		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		User user = (User) request.getSession().getAttribute("user");
		List<DruginventoryDruginformation> druginventory = null;
		int count = 0;
		druginventory = drugServices.querydruginventorylist(user.getPharmacycode(), classcode, commoname, pageInt, limitInt);

		count=drugServices.countdruginventorylist(user.getPharmacycode(),classcode,commoname);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventory);
		return tableMsg;
	}

	/**
	 * 药房发药的分类
	 *
	 * @return
	 */
	@RequestMapping("/selectclasscode")
	public @ResponseBody
	List<Druginformation> selectclasscode(HttpServletRequest request)
	{
		return drugServices.selectclasscode();
	}

	/**
	 * 药房确认发药
	 * @param list
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmsendmedicine")
	public @ResponseBody
	String confirmsendmedicine(@RequestParam(name = "list") String list, HttpServletRequest request)
	{
		Gson gson = new Gson();
		List<DruginventoryDruginformation> list1 = gson.fromJson(list, new TypeToken<ArrayList<DruginventoryDruginformation>>() {}.getType());
		User user = (User) request.getSession().getAttribute("user");
		int j = 0;
		int k = 0;
		String res = "";
		int count = 0;
		int size = list1.size();
		int[] countnum = new int[size];
		int[] countnum2 = new int[size];
		for (int i = 0; i < countnum2.length; i++)
		{
			countnum2[i]=0;
		}

		for (int i = 0; i < size ; i++)
		{
			if(size==1){
				break;
			}else if(size>1){
				if(i<size-1){
					count=drugServices.selectDrugcompatibilitycontraindications(list1.get(i).getDrugcode(),list1.get(i+1).getDrugcode());
					countnum[i]=count;
				}else if(i==size-1){
					for (int l = size; l > 0 ; l--)
					{
						if(l>1){
							count=drugServices.selectDrugcompatibilitycontraindications(list1.get(l-1).getDrugcode(),list1.get(l-2).getDrugcode());
							countnum2[l-1]=count;
						}else{
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < countnum.length; i++)
		{
			count = countnum[i]+count;
		}
		for (int i = 0; i < countnum2.length; i++)
		{
			count = countnum2[i]+count;
		}
		if(count==0){
			for (int i = 0; i < list1.size(); i++)
			{
				String asktime="";
				String receivetime="";
				String operatingtime="";
				String datearr = (new Date().toLocaleString().split(" ")[0]);
				try
				{
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(datearr);
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH) + 1;
					int day = c.get(Calendar.DATE);
					String month2 = "";
					String day2 = "";
					if (month < 10)
					{
						month2 = "0" + month;
					} else
					{
						month2 = String.valueOf(month);
					}
					if (day < 10)
					{
						day2 = "0" + day;
					} else
					{
						day2 = String.valueOf(day);
					}

					receivetime=operatingtime=asktime= year + "-" + month2 + "-" + day2;
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
				j = drugServices.insertDruginventoryOutbound(list1.get(i).getDrugcode(),list1.get(i).getProductiondate(),list1.get(i).getNumber(),list1.get(i).getLotnumber(),list1.get(i).getSpecialmedicine(),asktime,receivetime,operatingtime,user.getPharmacycode(),user.getUsername(),list1.get(i).getPrice());
				k = drugServices.updateDruginventoryNumber(list1.get(i).getDrugcode(),list1.get(i).getNumber(),list1.get(i).getLotnumber());
			}
			if(j>0&&k>0){
				res="success";
			}else{
				res="failed";
			}
		}else{
			res = "conflict";
		}
		return res;
	}

	/**
	 * 配伍禁忌查询
	 * @return
	 */
	@RequestMapping("/selectcompatibility")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectcompatibility(String drugcode,String page,String limit, HttpServletRequest request){

		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		List<Drugcompatibilitycontraindications> list = null;
		int count = 0;
		list = drugServices.selectcompatibilityList(drugcode, pageInt, limitInt);

		count=drugServices.selectcountcompatibilityList(drugcode);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(list);
		return tableMsg;
	}

	@RequestMapping("/queryDrugcode")
	public @ResponseBody List<Druginformation> queryDrugcode(){
		return drugServices.queryDrugcode(null);
	}

	@RequestMapping("/queryDrugcodeIf")
	public @ResponseBody List<Druginformation> queryDrugcodeIf(HttpServletRequest request){
		String choosedrugcodeA=request.getParameter("choosedrugcodeA");
		return drugServices.queryDrugcode(choosedrugcodeA);
	}

	@RequestMapping("/insertcompatibility")
	public @ResponseBody String insertcompatibility(HttpServletRequest request){
		String choosedrugcodeA=request.getParameter("choosedrugcodeA");
		String choosedrugcodeB=request.getParameter("choosedrugcodeB");
		String msg = request.getParameter("msg");

		String res="";
		int i = drugServices.selectDrugcompatibilitycontraindications(choosedrugcodeA,choosedrugcodeB);

		if(i==0){
			drugServices.insertcompatibility(choosedrugcodeA,choosedrugcodeB,msg);
			drugServices.insertcompatibility(choosedrugcodeB,choosedrugcodeA,msg);
			res="success";
		}else{
			res = "exist";
		}

		return res;
	}

	@RequestMapping("/selectinventorylist")
	public @ResponseBody
	TableMsg selectinventorylist(String page, String limit,HttpServletRequest request)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);

		PageHelper.startPage(pageInt, limitInt);
		String drugcode = request.getParameter("drugcode");
		String inventoryresults = request.getParameter("inventoryresults");
		String commoname = request.getParameter("commoname");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		User user = (User) request.getSession().getAttribute("user");
		List all = null;
		if (("请选择").equals(inventoryresults))
		{
			all = drugServices.selectinventorylist(user.getPharmacycode(), drugcode, null, commoname,start,end);
		} else
		{
			all = drugServices.selectinventorylist(user.getPharmacycode(), drugcode, inventoryresults, commoname,start,end);
		}

		PageInfo p = new PageInfo(all);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) p.getTotal());
		tableMsg.setData(p.getList());

		return tableMsg;
	}

	/**
	 * @Description  药品请领查询
	 * @author xlx
	 * @Date 上午 11:31 2020/2/29 0029
	 * @Param
	 * @return
	 **/
	@RequestMapping("/selectclaim")
	public @ResponseBody
	TableMsg selectclaim(int limit,int page,String commoname,String pincode, HttpServletRequest request){

		//开启分页
		PageHelper.startPage(page,limit);
		List all = drugServices.selectDruginformation(commoname,pincode);
		PageInfo p = new PageInfo(all);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}

	//--药房--药品库存列表
	@RequestMapping("/doDrugInventory")
	public @ResponseBody
	TableMsg doDrugInventory(String page, String limit, HttpServletRequest request){
		System.out.println("药房药--药房--药品库存列表品库存列表");
		System.out.println("page:"+page+", limit:"+limit);
		int pageInt=Integer.valueOf(page)-1;
		int limitInt=Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList=null;
		int count=0;
		druginventorytableList =drugServices.queryDrugInventoryList(pageInt,limitInt);
		count=drugServices.countDrugInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;

	}
	//--药库--药品库存列表
	@RequestMapping("/doDrugStoreDrugInventory")
	public @ResponseBody
	TableMsg doDrugStoreDrugInventory(String page, String limit, HttpServletRequest request){
		System.out.println("--药库--药品库存列表");
		System.out.println("page:"+page+", limit:"+limit);
		int pageInt=Integer.valueOf(page)-1;
		int limitInt=Integer.valueOf(limit);

		List<Drugstoredruginventory> druginventorytableList=null;
		int count=0;
		druginventorytableList =drugServices.queryDrugStoreInventoryList(pageInt,limitInt);
		count=drugServices.countDrugStoreInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;

	}

	//药品低限设置
	@ResponseBody
	@RequestMapping("/lowestSetting")
	public String lowestSetting(String drugCode,String setData){
		System.out.println("执行到药品低限设置");
		boolean b=drugServices.lowestSetting(drugCode,setData);
		System.out.println("b:"+b);
		String msg="";
		if(b){
			msg="1";
		}else{
			msg="2";
		}
		return msg;
	}

	/**
	 * 查询药品分类信息
	 * @author cbd
	 * @param page 当前页
	 * @param limit 每页显示数量
	 * @return 返回table信息对象
	 */
	@RequestMapping("/selectDrugClass")
	@ResponseBody
	TableMsg selectDrugClass (int page,int limit)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<DrugClass> drugClassList = drugServices.selectDrugClass();
		PageInfo pageInfo = new PageInfo(drugClassList);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据父级编码查询其对象的子级编码的最大值
	 * @author cbd
	 * @param parentCode 大类编码
	 * @return 返回子级编码最大值用于新增的子级编码
	 */
	@RequestMapping("/selectClassCode")
	@ResponseBody
	public String selectClassCode(String parentCode)
	{
		return drugServices.selectDrugClassCode(parentCode);
	}

	/**
	 * 药品分类设置新增方法：根据新增的药品分类插入到药品分类表
	 * @author cbd
	 * @param drugClass 药品分类信息对象
	 * @return 返回整型值判断结果状态成功与否
	 */
	@RequestMapping("/saveDrugClassSetInfo")
	@ResponseBody
	public String saveDrugClassSetInfo(DrugClass drugClass)
	{
		System.out.println("提交的信息=="+drugClass);
		String res = null;
		int i = drugServices.saveDrugClassSetInfo(drugClass);
		if(i>0){
			res = "success";
		}else {
			res = "fail";
		}
		return res;
	}

	/**
	 * 查询药品信息表
	 * @author cbd
	 * @param page 起始页
	 * @param limit 每页显示数量
	 * @return 返回药品信息表的list
	 */
	@RequestMapping("/selectDrugInfo")
	@ResponseBody
	public TableMsg selectDrugInfo(int page,int limit)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Druginformation> list = drugServices.selectDrugInfo();
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据药品信息对象将对应信息增加至药品信息表
	 * @author cbd
	 * @param drugInformation 药品信息表对象
	 * @return 返回状态字符串
	 */
	@RequestMapping("/saveDrugInfo")
	@ResponseBody
	public String saveDrugInfo(Druginformation drugInformation)
	{
		String res = null;
		int i = drugServices.saveDrugInfo(drugInformation);
		if(i>0){
			res = "success";
		}else {
			res = "fail";
		}

		return res;
	}

	/**
	 * 查询药库药品库存表信息
	 * @author cbd
	 * @return 返回查询结果集list
	 */
	@RequestMapping("/selectDrugStoreInventory")
	@ResponseBody
	public  TableMsg  selectDrugStoreInventory(int page,int limit)
	{

		//开启分页
		PageHelper.startPage(page,limit);
		List<Drugstoredruginventory> list = drugServices.selectDrugStoreInventory();
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}
	/**
	 * 根据药库入库信息对象作为参数保存入库信息
	 * @author cbd
	 * @param drugStoreDrugInventory 药库药品入库信息对象参数
	 * @return 返回保存结果状态int值 作为判断成功
	 */
	@RequestMapping("/saveDrugStoreInventory")
	@ResponseBody
	public String saveDrugStoreInventory(Drugstoredruginventory drugStoreDrugInventory)
	{
		String state = null;
		int i = drugServices.saveDrugStoreInventory(drugStoreDrugInventory);
		if(i>0){
			state = "success";
		}else {
			state= "fail";
		}

		return state;
	}

	@RequestMapping("/selectInventorycheck")
	@ResponseBody
	public TableMsg selectInventorycheck (int page,int limit,String commoname,String specialmedicine)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Inventorycheck> list = drugServices.selectInventorycheck(commoname,specialmedicine);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}
	/**
	 * @Description  查询药房出入库明细
	 * @author xlx
	 * @Date 下午 21:52 2020/3/1 0001
	 * @Param
	 * @return
	 **/
	@RequestMapping("/selectPharmacyd")
	@ResponseBody
	public TableMsg selectPharmacyd (int page,int limit,String drugcode, String lotnumber, String asker, String outbound, String start, String end)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Pharmacydrugschedule> list = drugServices.selectPharmacyd(drugcode, lotnumber,  asker,  outbound, start, end);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}



	//盘点
	@RequestMapping("/doInventory")
	public @ResponseBody
	TableMsg doInventory(String page, String limit, HttpServletRequest request){
		System.out.println("执行到盘点");
		System.out.println("page:"+page+", limit:"+limit);
		int limitInt=Integer.valueOf(limit);
		int pageInt=(Integer.valueOf(page)-1)* limitInt;


		List<Druginventorytable> inventorytables=null;
		int count=0;
		inventorytables =drugServices.queryInventoryTableList(pageInt,limitInt);
		count=drugServices.countInventoryTableList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(inventorytables);
		return tableMsg;

	}

	//已滞销列表
	@RequestMapping("/doDrugInventoryUnsalable")
	public @ResponseBody
	TableMsg doDrugInventoryUnsalable(String page, String limit, HttpServletRequest request)
	{
		System.out.println("已滞销列表");
		System.out.println("page:" + page + ", limit:" + limit);
		int pageInt = Integer.valueOf(page) - 1;
		int limitInt = Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryDrugInventoryUnsalableList(pageInt, limitInt);
		count = drugServices.countDrugInventoryUnsalableList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;
	}


	//	//盘点
	//	@RequestMapping("/doInventory")
	//	public @ResponseBody
	//	TableMsg doInventory(String page, String limit, HttpServletRequest request)
	//	{
	//		System.out.println("执行到盘点");
	//		System.out.println("page:" + page + ", limit:" + limit);
	//		int limitInt = Integer.valueOf(limit);
	//		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
	//
	//	}
	//	//盘点后调整库存数量
	//	@ResponseBody
	//	@RequestMapping("/adjustmentQuantity")
	//	public String adjustmentQuantity(Druginventorytable druginventorytable){
	//		System.out.println("执行到盘点后调整库存数量");
	//
	//
	//
	//
	//		return msg;
	//	}


	//乘法计算
	public double mul(double x, double y)
	{
		System.out.println("两个数相乘为：" + x + "*" + y + "=" + x * y);
		double z = x * y;
		return z;
	}


	//盘点之后
	@RequestMapping("/afterInventory")
	public @ResponseBody
	TableMsg afterInventory(String page, String limit, HttpServletRequest request)
	{
		System.out.println("执行到盘点结果后");
		System.out.println("page:" + page + ", limit:" + limit);
		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;

		List<AfterInventory> afterInventories = null;
		int count = 0;
		afterInventories = drugServices.queryAfterInventoryList(pageInt, limitInt);
		count = drugServices.countAfterInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(afterInventories);
		return tableMsg;

	}

	@RequestMapping("/showInventory")
	@IgnoreLog
	public @ResponseBody List<Inventorycheck> showInventory(){

		List<Inventorycheck> list = drugServices.selectInventorycheck(null,null);
		return list;
	};
	/**
	 * @Description  查询药库库存通过特殊分类和名称
	 * @author xlx
	 * @Date 下午 18:57 2020/3/3 0003
	 * @Param
	 * @return
	 **/
	@RequestMapping("/selectStoreInventorycheck")
	@ResponseBody
	public TableMsg selectStoreInventorycheck (int page,int limit,String commoname,String specialmedicine)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Inventorycheck> list = drugServices.selectStoreInventorycheck(commoname,specialmedicine);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * @Description  查询药房报损明细
	 * @author xlx
	 * @Date 下午 21:52 2020/3/1 0001
	 * @Param
	 * @return
	 **/
	@RequestMapping("/selectBreakdownOfDrugs")
	@ResponseBody
	public TableMsg selectBreakdownOfDrugs(int page,int limit,String damagedtype, String commoname, String start, String end)
	{
		//开启分页
		PageHelper.startPage(page,limit);
		List<Breakdownofdrugs> list = drugServices.selectBreakdownOfDrugs(damagedtype, commoname,  start, end);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}
}
