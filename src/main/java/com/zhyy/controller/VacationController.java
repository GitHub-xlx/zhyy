package com.zhyy.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhyy.entity.*;
import com.zhyy.services.DrugServices;
import com.zhyy.services.impl.VacationServiceImpl;
import com.zhyy.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/vacationController")
public class VacationController
{
	@Autowired
	private VacationServiceImpl vacationServices;
	@Autowired
	private DrugServices drugServices;
	/**
	 * @Description  开启流程
	 * @author xlx
	 * @Date 下午 17:01 2020/2/24 0024
	 * @Param
	 * @return @RequestBody
	 **/
	@RequestMapping("/startProcess")
	public @ResponseBody
	boolean startProcess( String gsonList, String processkey, HttpSession session){
		boolean flag=false;
		List<Druginformation> list = new Gson().fromJson(gsonList,new TypeToken< List<Druginformation>>(){}.getType());
		User user = (User)session.getAttribute("user");
		System.out.println(list);
		System.out.println(1111);
		System.out.println(new Gson().toJson(list));
		if (user!=null){
			flag=vacationServices.startVac(user.getAccount(),list,processkey);
		}
		return flag;
	}
	/** 
	 * @Description  查询用户审核信息
	 * @author xlx
	 * @Date 上午 7:02 2020/2/25 0025
	 * @Param 
	 * @return 
	 **/
	@RequestMapping("/myVac")
	public @ResponseBody
	List<Vacation> myVac( HttpSession session){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myVac(user.getAccount());
		}
		return list;
	}
	/** 
	 * @Description  查询用户申请结束的审核信息
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param 
	 * @return 
	 **/
	@RequestMapping("/myVacRecord")
	public @ResponseBody
	List<Vacation> myVacRecord(String processkey, HttpSession session){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myVacRecord(user.getAccount(),processkey);
		}
		return list;
	}
	/**
	 * @Description  查询等待用户的审核信息
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param
	 * @return
	 **/
	@RequestMapping("/myAudit")
	public @ResponseBody
	List<VacTask> myAudit(HttpSession session){
		List<VacTask> list=null;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			list = vacationServices.myAudit(user.getAccount());
		}
		return list;
	}
	/**
	 * @Description  查询用户的审核记录
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param
	 * @return
	 **/
	@RequestMapping("/myAuditRecord")
	public @ResponseBody
	List<Vacation> myAuditRecord( HttpSession session,String processkey){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myAuditRecord(user.getAccount(),processkey);
		}
		return list;
	}
	/**
	 * @Description  审核通过
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param
	 * @return
	 **/
	@RequestMapping("/passAudit")
	public @ResponseBody
	boolean passAudit( HttpSession session,VacTask vacTask){
		boolean flag=false;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			flag = (boolean)vacationServices.passAudit(user.getAccount(),vacTask);
			if (user.getAccount().equals(TimeUtil.ROLE_ISSUER)&&vacTask.getVac().getNowResult().equals("同意")){
				//判断是否是同意发药，同意则进入药品出库
				Vacation vacation = vacationServices.queryHistoryProcess(vacTask.getId());
				drugServices.insertOutbound(vacation);
				//开启药房入库的审核流程
				vacationServices.startVac(vacation.getApplyUser(),vacation.getList(),"pharmacystorage");
			}else if (user.getAccount().equals(TimeUtil.ROLE_PHMANAGER)&&vacTask.getVac().getNowResult().equals("同意")){
				//判断药房是否是同意入库，同意则进入药房药品入库
				Vacation vacation = vacationServices.queryHistoryProcess(vacTask.getId());
				drugServices.insertAndUpdate(vacation);
			}
		}
		return flag;
	}
}
