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
	 *
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
	 *
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

		count = drugServices.countdruginventorylist(user.getPharmacycode(), classcode, commoname);

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
	 *
	 * @param list
	 * @param request
	 * @return
	 */
	@RequestMapping("/confirmsendmedicine")
	public @ResponseBody
	String confirmsendmedicine(@RequestParam(name = "list") String list,String idcard,String consumername, HttpServletRequest request)
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
			countnum2[i] = 0;
		}

		for (int i = 0; i < size; i++)
		{
			if (size == 1)
			{
				break;
			} else if (size > 1)
			{
				if (i < size - 1)
				{
					count = drugServices.selectDrugcompatibilitycontraindications(list1.get(i).getDrugcode(), list1.get(i + 1).getDrugcode());
					countnum[i] = count;
				} else if (i == size - 1)
				{
					for (int l = size; l > 0; l--)
					{
						if (l > 1)
						{
							count = drugServices.selectDrugcompatibilitycontraindications(list1.get(l - 1).getDrugcode(), list1.get(l - 2).getDrugcode());
							countnum2[l - 1] = count;
						} else
						{
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < countnum.length; i++)
		{
			count = countnum[i] + count;
		}
		for (int i = 0; i < countnum2.length; i++)
		{
			count = countnum2[i] + count;
		}
		if (count == 0)
		{
			for (int i = 0; i < list1.size(); i++)
			{
				String asktime = "";
				String receivetime = "";
				String operatingtime = "";
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

					receivetime = operatingtime = asktime = year + "-" + month2 + "-" + day2;
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
				j = drugServices.insertDruginventoryOutbound(list1.get(i).getDrugcode(), list1.get(i).getProductiondate(), list1.get(i).getNumber(), list1.get(i).getLotnumber(), list1.get(i).getSpecialmedicine(), asktime, receivetime, operatingtime, user.getPharmacycode(), user.getUsername(),list1.get(i).getPrice());
				k = drugServices.updateDruginventoryNumber(list1.get(i).getDrugcode(), list1.get(i).getNumber(), list1.get(i).getLotnumber());
				double totalprice=list1.get(i).getPrice()*Integer.valueOf(list1.get(i).getNumber());
				if("是".equals(list1.get(i).getSpecialmedicine())){
					drugServices.insertDrugsale(list1.get(i).getDrugcode(),String.valueOf(list1.get(i).getPrice()),list1.get(i).getNumber(),String.valueOf(totalprice),asktime,list1.get(i).getSpecialmedicine(),idcard,user.getUsername(),user.getPharmacycode(),consumername);
				}else{
					drugServices.insertDrugsale(list1.get(i).getDrugcode(),String.valueOf(list1.get(i).getPrice()),list1.get(i).getNumber(),String.valueOf(totalprice),asktime,list1.get(i).getSpecialmedicine(),null,user.getUsername(),user.getPharmacycode(),null);
				}

			}
			if (j > 0 && k > 0)
			{
				res = "success";
			} else
			{
				res = "failed";
			}
		} else
		{
			res = "conflict";
		}
		return res;
	}

	/**
	 * 配伍禁忌查询
	 *
	 * @return
	 */
	@RequestMapping("/selectcompatibility")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectcompatibility(String drugcode, String page, String limit, HttpServletRequest request)
	{

		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);
		List<Drugcompatibilitycontraindications> list = null;
		int count = 0;
		list = drugServices.selectcompatibilityList(drugcode, pageInt, limitInt);

		count = drugServices.selectcountcompatibilityList(drugcode);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(list);
		return tableMsg;
	}

	@RequestMapping("/queryDrugcode")
	@IgnoreLog
	public @ResponseBody
	List<Druginformation> queryDrugcode()
	{
		return drugServices.queryDrugcode(null);
	}

	@RequestMapping("/queryDrugcodeIf")
	@IgnoreLog
	public @ResponseBody
	List<Druginformation> queryDrugcodeIf(HttpServletRequest request)
	{
		String choosedrugcodeA = request.getParameter("choosedrugcodeA");
		return drugServices.queryDrugcode(choosedrugcodeA);
	}

	@RequestMapping("/insertcompatibility")
	public @ResponseBody
	String insertcompatibility(HttpServletRequest request)
	{
		String choosedrugcodeA = request.getParameter("choosedrugcodeA");
		String choosedrugcodeB = request.getParameter("choosedrugcodeB");
		String msg = request.getParameter("msg");

		String res = "";
		int i = drugServices.selectDrugcompatibilitycontraindications(choosedrugcodeA, choosedrugcodeB);

		if (i == 0)
		{
			drugServices.insertcompatibility(choosedrugcodeA, choosedrugcodeB, msg);
			drugServices.insertcompatibility(choosedrugcodeB, choosedrugcodeA, msg);
			res = "success";
		} else
		{
			res = "exist";
		}

		return res;
	}

	/**
	 * 医保药品核对的药品信息查询
	 *
	 * @return
	 */
	@RequestMapping("/selectdrugstore")
	public @ResponseBody
	TableMsg selectdrugstore(String drugcode, String commoname, String page, String limit, HttpServletRequest request)
	{
		int pageInt = Integer.valueOf(page);
		int limitInt = Integer.valueOf(limit);

		PageHelper.startPage(pageInt, limitInt);
		List all = drugServices.selectdrugstore(drugcode, commoname);
		PageInfo p = new PageInfo(all);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}

	/**
	 * 更改药品医保状态
	 *
	 * @return
	 */
	@RequestMapping("/updatehealthinsurance")
	public @ResponseBody
	String updatehealthinsurance(HttpServletRequest request)
	{
		String status = request.getParameter("status");
		String drugcode = request.getParameter("drugcode");
		String commoname = request.getParameter("commoname");
		String res = "";

		int i = drugServices.updateDruginformationhealthinsurance(status, drugcode, commoname);

		if (i > 0)
		{
			res = "success";
		} else
		{
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
	 * @return
	 * @Description 药品请领查询
	 * @author xlx
	 * @Date 上午 11:31 2020/2/29 0029
	 * @Param
	 **/
	@RequestMapping("/selectclaim")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectclaim(int limit, int page, String commoname, String pincode, HttpServletRequest request)
	{

		//开启分页
		PageHelper.startPage(page, limit);
		List all = drugServices.selectDruginformation(commoname, pincode);
		PageInfo p = new PageInfo(all);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}


	//--药房--药品库存列表
	@RequestMapping("/doDrugInventory")
	@IgnoreLog
	public @ResponseBody
	TableMsg doDrugInventory(String page, String limit, HttpServletRequest request)
	{
		System.out.println("药房药--药房--药品库存列表品库存列表");
		System.out.println("page:" + page + ", limit:" + limit);
		int pageInt = Integer.valueOf(page) - 1;
		int limitInt = Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryDrugInventoryList(pageInt, limitInt);
		count = drugServices.countDrugInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;
	}

	//--药库--药品库存列表
	@RequestMapping("/doDrugStoreDrugInventory")
	@IgnoreLog
	public @ResponseBody
	TableMsg doDrugStoreDrugInventory(String page, String limit, HttpServletRequest request)
	{
		System.out.println("--药库--药品库存列表");
		System.out.println("page:" + page + ", limit:" + limit);
		int pageInt = Integer.valueOf(page) - 1;
		int limitInt = Integer.valueOf(limit);

		List<Drugstoredruginventory> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryDrugStoreInventoryList(pageInt, limitInt);
		count = drugServices.countDrugStoreInventoryList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;

	}

	//药品低限设置查询
	@RequestMapping("/lowLimitQuery")
	public @ResponseBody
	TableMsg lowLimitQuery(String drugcode, String commoname, int page, int limit, HttpServletRequest request)
	{

		System.out.println("药品低限设置查询");
		String where = "1=1";
		System.out.println("drugcode:" + drugcode + ",commoname:" + commoname);

		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
		if (drugcode != null)
		{
			where = drugcode.length() > 0 ? where + " and A.drugcode like '%" + drugcode + "%'" : where;
		}
		if (commoname != null)
		{
			where = commoname.length() > 0 ? where + " and B.commoname like '%" + commoname + "%'" : where;
		}

		int countPage = drugServices.countlowLimitQuery(where);
		List<Drugstoredruginventory> list = drugServices.querylowLimitQuery(pageInt, limitInt, where);

		return new TableMsg(0, "", countPage, list);
	}

	//药品低限设置
	@ResponseBody
	@RequestMapping("/lowestSetting")
	public String lowestSetting(String drugCode, String setData)
	{
		System.out.println("执行到药品低限设置");
		boolean b = drugServices.lowestSetting(drugCode, setData);
		System.out.println("b:" + b);
		String msg = "";
		if (b)
		{
			msg = "1";
		} else
		{
			msg = "2";
		}
		return msg;
	}

	/**
	 * 查询药品分类信息
	 *
	 * @param page  当前页
	 * @param limit 每页显示数量
	 * @return 返回table信息对象
	 * @author cbd
	 */
	@RequestMapping("/selectDrugClass")
	@ResponseBody
	TableMsg selectDrugClass(int page, int limit)
	{
		//开启分页
		PageHelper.startPage(page, limit);
		List<DrugClass> drugClassList = drugServices.selectDrugClass();
		PageInfo pageInfo = new PageInfo(drugClassList);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据父级编码查询其对象的子级编码的最大值
	 *
	 * @param parentCode 大类编码
	 * @return 返回子级编码最大值用于新增的子级编码
	 * @author cbd
	 */
	@RequestMapping("/selectClassCode")
	@IgnoreLog
	@ResponseBody
	public String selectClassCode(String parentCode)
	{
		return drugServices.selectDrugClassCode(parentCode);
	}

	/**
	 * 药品分类设置新增方法：根据新增的药品分类插入到药品分类表
	 *
	 * @param drugClass 药品分类信息对象
	 * @return 返回整型值判断结果状态成功与否
	 * @author cbd
	 */
	@RequestMapping("/saveDrugClassSetInfo")
	@ResponseBody
	public String saveDrugClassSetInfo(DrugClass drugClass)
	{
		System.out.println("提交的信息==" + drugClass);
		String res = null;
		int i = drugServices.saveDrugClassSetInfo(drugClass);
		if (i > 0)
		{
			res = "success";
		} else
		{
			res = "fail";
		}
		return res;
	}

	/**
	 * 查询药品信息表
	 *
	 * @param page  起始页
	 * @param limit 每页显示数量
	 * @return 返回药品信息表的list
	 * @author cbd
	 */
	@RequestMapping("/selectDrugInfo")
	@IgnoreLog
	@ResponseBody
	public TableMsg selectDrugInfo(int page, int limit)
	{
		//开启分页
		PageHelper.startPage(page, limit);
		List<Druginformation> list = drugServices.selectDrugInfo();
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据药品信息对象将对应信息增加至药品信息表
	 *
	 * @param drugInformation 药品信息表对象
	 * @return 返回状态字符串
	 * @author cbd
	 */
	@RequestMapping("/saveDrugInfo")
	@ResponseBody
	public String saveDrugInfo(Druginformation drugInformation)
	{
		String res = null;
		int i = drugServices.saveDrugInfo(drugInformation);
		if (i > 0)
		{
			res = "success";
		} else
		{
			res = "fail";
		}

		return res;
	}

	/**
	 * 查询药库药品库存表信息
	 *
	 * @return 返回查询结果集list
	 * @author cbd
	 */
	@RequestMapping("/selectDrugStoreInventory")
	@IgnoreLog
	@ResponseBody
	public TableMsg selectDrugStoreInventory(int page, int limit)
	{

		//开启分页
		PageHelper.startPage(page, limit);
		List<Drugstoredruginventory> list = drugServices.selectDrugStoreInventory();
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * 根据药库入库信息对象作为参数保存入库信息
	 *
	 * @param drugStoreDrugInventory 药库药品入库信息对象参数
	 * @return 返回保存结果状态int值 作为判断成功
	 * @author cbd
	 */
	@RequestMapping("/saveDrugStoreInventory")
	@ResponseBody
	public String saveDrugStoreInventory(Drugstoredruginventory drugStoreDrugInventory)
	{
		String state = null;
		int i = drugServices.saveDrugStoreInventory(drugStoreDrugInventory);
		if (i > 0)
		{
			state = "success";
		} else
		{
			state = "fail";
		}

		return state;
	}

	@RequestMapping("/selectInventorycheck")
	@IgnoreLog
	public @ResponseBody
	TableMsg selectInventorycheck(int page, int limit, String commoname, String specialmedicine)
	{
		//开启分页
		PageHelper.startPage(page, limit);
		List<Inventorycheck> list = drugServices.selectInventorycheck(commoname, specialmedicine);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}

	/**
	 * @return
	 * @Description 查询药房出入库明细
	 * @author xlx
	 * @Date 下午 21:52 2020/3/1 0001
	 * @Param
	 **/
	@RequestMapping("/selectPharmacyd")
	@ResponseBody
	public TableMsg selectPharmacyd(int page, int limit, String drugcode, String lotnumber, String asker, String outbound, String start, String end)
	{
		//开启分页
		PageHelper.startPage(page, limit);
		List<Pharmacydrugschedule> list = drugServices.selectPharmacyd(drugcode, lotnumber, asker, outbound, start, end);
		PageInfo pageInfo = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int) pageInfo.getTotal());
		tableMsg.setData(pageInfo.getList());
		return tableMsg;
	}


	//低限报警列表
	@RequestMapping("/pharmacyLowLimitDrugs")
	public @ResponseBody
	TableMsg pharmacyLowLimitDrugs(String page, String limit, HttpServletRequest request)
	{
		System.out.println("低限报警列表");
		System.out.println("page:" + page + ", limit:" + limit);
		int pageInt = Integer.valueOf(page) - 1;
		int limitInt = Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryPharmacyLowLimitDrugsList(pageInt, limitInt);
		count = drugServices.countPharmacyLowLimitDrugsList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;
	}

	//低限报警查询
	@RequestMapping("/pharmacyDrugsQuery")
	public @ResponseBody
	TableMsg pharmacyDrugsQuery(String drugcode, String commoname, int page, int limit, HttpServletRequest request)
	{

		System.out.println("低限报警查询");
		String where = "1=1";
		System.out.println("drugcode:" + drugcode + ",commoname:" + commoname);

		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
		if (drugcode != null)
		{
			where = drugcode.length() > 0 ? where + " and A.drugcode like '%" + drugcode + "%'" : where;
		}
		if (commoname != null)
		{
			where = commoname.length() > 0 ? where + " and B.commoname like '%" + commoname + "%'" : where;
		}

		int countPage = drugServices.countpharmacyDrugsQuery(where);
		List<Druginventorytable> list = drugServices.querypharmacyDrugsQuery(pageInt, limitInt, where);

		return new TableMsg(0, "", countPage, list);
	}

	//过期报警列表
	@RequestMapping("/doDrugInventoryExpired")
	@IgnoreLog
	public @ResponseBody
	TableMsg doDrugInventoryExpired(String page, String limit, HttpServletRequest request)
	{
		System.out.println("已过期列表");
		System.out.println("page:" + page + ", limit:" + limit);
		int pageInt = Integer.valueOf(page) - 1;
		int limitInt = Integer.valueOf(limit);

		List<Druginventorytable> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryDrugInventoryExpiredList(pageInt, limitInt);
		count = drugServices.countDrugInventoryExpiredList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;
	}

	//过期查询
	@RequestMapping("/expiredQuery")
	public @ResponseBody
	TableMsg expiredQuery(String drugcode, String commoname, String start, String end, int page, int limit, HttpServletRequest request)
	{

		System.out.println("过期查询");
		String where = "1=1";
		System.out.println("drugcode:" + drugcode + ",commoname:" + commoname);

		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
		if (drugcode != null)
		{
			where = drugcode.length() > 0 ? where + " and A.drugcode like '%" + drugcode + "%'" : where;
		}
		if (commoname != null)
		{
			where = commoname.length() > 0 ? where + " and B.commoname like '%" + commoname + "%'" : where;
		}
		if (start != null)
		{
			where = start.length() > 0 ? where + " and A.productiondate >= '" + start + "'" : where;
		}
		if (end != null)
		{
			where = end.length() > 0 ? where + " and A.productiondate <= '" + end + "'" : where;
		}

		int countPage = drugServices.countexpiredQuery(where);
		List<Druginventorytable> list = drugServices.queryexpiredQuery(pageInt, limitInt, where);

		return new TableMsg(0, "", countPage, list);
	}

	//已滞销列表
	@RequestMapping("/doDrugInventoryUnsalable")
	@IgnoreLog
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

	//滞销查询
	@RequestMapping("/unSaleQuery")
	public @ResponseBody
	TableMsg unSaleQuery(String drugcode, String commoname, String start, String end, int page, int limit, HttpServletRequest request)
	{

		System.out.println("滞销查询");
		String where = "1=1";
		System.out.println("drugcode:" + drugcode + ",commoname:" + commoname);

		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
		if (drugcode != null)
		{
			where = drugcode.length() > 0 ? where + " and A.drugcode like '%" + drugcode + "%'" : where;
		}
		if (commoname != null)
		{
			where = commoname.length() > 0 ? where + " and B.commoname like '%" + commoname + "%'" : where;
		}
		if (start != null)
		{
			where = start.length() > 0 ? where + " and C.receivetime >= '" + start + "'" : where;
		}
		if (end != null)
		{
			where = end.length() > 0 ? where + " and C.receivetime <= '" + end + "'" : where;
		}

		int countPage = drugServices.countunSaleQuery(where);
		List<Druginventorytable> list = drugServices.queryunSaleQuery(pageInt, limitInt, where);
		return new TableMsg(0, "", countPage, list);
	};



	//盘点
	@RequestMapping("/doInventory")
	public @ResponseBody
	TableMsg doInventory(String page, String limit, HttpServletRequest request)
	{
		System.out.println("执行到盘点");
		System.out.println("page:" + page + ", limit:" + limit);
		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;

		List<Druginventorytable> druginventorytableList = null;
		int count = 0;
		druginventorytableList = drugServices.queryInventoryTableList(pageInt, limitInt);
		count = drugServices.countInventoryTableList();

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount(count);
		tableMsg.setData(druginventorytableList);
		return tableMsg;

	}

	//盘点查询
	@RequestMapping("/inventoryQuery")
	public @ResponseBody
	TableMsg inventoryQuery(String drugcode, String commoname, int page, int limit, HttpServletRequest request)
	{

		System.out.println("盘点查询");
		String where = "1=1";
		System.out.println("drugcode:" + drugcode + ",commoname:" + commoname);

		int limitInt = Integer.valueOf(limit);
		int pageInt = (Integer.valueOf(page) - 1) * limitInt;
		if (drugcode != null)
		{
			where = drugcode.length() > 0 ? where + " and A.drugcode like '%" + drugcode + "%'" : where;
		}
		if (commoname != null)
		{
			where = commoname.length() > 0 ? where + " and B.commoname like '%" + commoname + "%'" : where;
		}

		int countPage = drugServices.countInventoryQuery(where);
		List<Druginventorytable> list = drugServices.queryInventoryQuery(pageInt, limitInt, where);

		return new TableMsg(0, "", countPage, list);
	}

	//盘点后调整库存数量/盘点结果
	@RequestMapping("/adjustmentQuantity")
	@ResponseBody
	public String adjustmentQuantity(String data)
	{
		System.out.println("执行到盘点后调整库存数量/盘点结果");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateFormat.format(date));

		boolean b = drugServices.deleteAfterInventory();
		System.out.println("清除原先数据库数据" + b);
		List<AfterInventory> afterInventories = JSON.parseArray(data, AfterInventory.class);
		String result = "";
		for (int i = 0; i < afterInventories.size(); i++)
		{
			System.out.println(afterInventories.get(i));

			//盘点前后数量比较
			if (afterInventories.get(i).getDruginventorynumber() == afterInventories.get(i).getFinishedquantity())
			{
				//如果相等
				afterInventories.get(i).setRelativequantity(0);
				afterInventories.get(i).setRelativeamount(0);
			} else
			{
				//如果不相等
				//盘点前数量大于盘点后数量(盘亏)
				if (afterInventories.get(i).getDruginventorynumber() > afterInventories.get(i).getFinishedquantity())
				{
					afterInventories.get(i).setRelativequantity(afterInventories.get(i).getDruginventorynumber() - afterInventories.get(i).getFinishedquantity());
					double z = mul(afterInventories.get(i).getRelativequantity(), afterInventories.get(i).getWholesaleprice());
					afterInventories.get(i).setRelativeamount(z);
					result = "盘亏";
				} else
				{
					afterInventories.get(i).setRelativequantity(afterInventories.get(i).getFinishedquantity() - afterInventories.get(i).getDruginventorynumber());
					double z = mul(afterInventories.get(i).getRelativequantity(), afterInventories.get(i).getWholesaleprice());
					afterInventories.get(i).setRelativeamount(z);
					result = "盘盈";
				}
				//盘点结果录入盘点结果表
				drugServices.insertInventory(afterInventories.get(i).getDrugcode(), afterInventories.get(i).getSpecification(), afterInventories.get(i).getDrugunit(), afterInventories.get(i).getLotnumber(), afterInventories.get(i).getDruginventorynumber(), afterInventories.get(i).getRelativequantity(), afterInventories.get(i).getFinishedquantity(), afterInventories.get(i).getWholesaleprice(), afterInventories.get(i).getRelativeamount());

				//录入盘点盈亏表
				drugServices.insertInventory2(afterInventories.get(i).getDrugcode(), result, "001", dateFormat.format(date));

				//药房药品库存数量自动调整
				drugServices.updateDruginventoryCount(afterInventories.get(i).getDrugcode(), afterInventories.get(i).getFinishedquantity());

			}
		}

		return "1";
	}

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
	@IgnoreLog
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

	@RequestMapping("/gainAndLoss")
	@IgnoreLog
	public @ResponseBody List<GainAndLoss> gainAndLoss(){

		List<GainAndLoss> list = drugServices.gainAndLoss();
		return list;
	};
}
