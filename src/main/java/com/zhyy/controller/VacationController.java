package com.zhyy.controller;

import com.zhyy.entity.*;
import com.zhyy.services.DrugServices;
import com.zhyy.services.impl.VacationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	 * @return
	 **/
	@RequestMapping("/startProcess")
	public @ResponseBody
	boolean startProcess(Vacation vac, String processkey, HttpSession session){
		boolean flag=false;
		User user = (User)session.getAttribute("user");
		if (user!=null){
			flag=vacationServices.startVac(user.getAccount(),vac,processkey);
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
			if (user.getAccount().equals("issuer")&&vacTask.getVac().getNowResult().equals("同意")){
				//判断是否是同意发药，同意则进入药品出库
				Vacation vacation = vacationServices.queryHistoryProcess(vacTask.getId());
				int i = drugServices.insertOutbound(vacation);
			}
		}
		return flag;
	}
}
