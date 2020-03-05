package com.zhyy.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhyy.entity.*;
import com.zhyy.services.DrugServices;
import com.zhyy.services.impl.VacationServiceImpl;
import com.zhyy.utils.TimeUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/vacationController")
public class VacationController
{
	@Autowired
	private VacationServiceImpl vacationServices;
	@Autowired
	private DrugServices drugServices;
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	/**
	 * @Description  开启请领流程
	 * @author xlx
	 * @Date 下午 17:01 2020/2/24 0024
	 * @Param
	 * @return @RequestBody
	 **/
	@RequestMapping("/startProcess")
	public @ResponseBody
	boolean startProcess( String gsonList, String processkey,  Vacation vac, HttpSession session){
		boolean flag=false;
		List<Druginformation> list = new Gson().fromJson(gsonList,new TypeToken< List<Druginformation>>(){}.getType());
		User user = (User)session.getAttribute("user");
		if (user!=null){
			flag=vacationServices.startVac(user.getAccount(),list,processkey,vac);
		}
		return flag;
	}
	/**
	 * @Description  开启退库流程
	 * @author xlx
	 * @Date 下午 17:01 2020/2/24 0024
	 * @Param
	 * @return @RequestBody
	 **/
	@RequestMapping("/startWithdrawal")
	public @ResponseBody
	boolean startWithdrawal( String gsonList, String processkey,  Vacation vac, HttpSession session){
		boolean flag=false;
		List<DrugWithdrawal> list = new Gson().fromJson(gsonList,new TypeToken< List<DrugWithdrawal>>(){}.getType());
		User user = (User)session.getAttribute("user");
		if (user!=null){
			flag=vacationServices.startVac(user.getAccount(),list,processkey,vac);
		}
		return flag;
	}
	/**
	 * @Description  开启报损流程
	 * @author xlx
	 * @Date 下午 17:01 2020/2/24 0024
	 * @Param
	 * @return @RequestBody
	 **/
	@RequestMapping("/startDamaged")
	public @ResponseBody
	boolean startDamaged( String gsonList,  Vacation vac,HttpSession session){
		boolean flag=false;
		List<Inventorycheck> list = new Gson().fromJson(gsonList,new TypeToken< List<Inventorycheck>>(){}.getType());
		User user = (User)session.getAttribute("user");
		if (user!=null){
			flag=vacationServices.startVac(user.getAccount(),list,"drugdamaged",vac);
		}
		return flag;
	}
	/** 
	 * @Description  查询用户申请信息
	 * @author xlx
	 * @Date 上午 7:02 2020/2/25 0025
	 * @Param 
	 * @return 
	 **/
	@RequestMapping("/myVac")
	public @ResponseBody
	TableMsg myVac(int limit,int page, HttpSession session){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		PageHelper.startPage(page,limit);
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myVac(user.getAccount());
		}
		PageInfo p = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}
	/** 
	 * @Description  查询用户申请结束的审核信息
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param 
	 * @return  ,String processkey
	 **/
	@RequestMapping("/myVacRecord")
	public @ResponseBody
	TableMsg myVacRecord(int limit,int page, HttpSession session){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		PageHelper.startPage(page,limit);
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myVacRecord(user.getAccount());
		}
		PageInfo p = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
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
	TableMsg myAudit(int limit,int page,HttpSession session){
		List<VacTask> list=null;
		User user = (User)session.getAttribute("user");
		PageHelper.startPage(page,limit);
		if (user!=null){
			list = vacationServices.myAudit(user.getAccount());
		}
		PageInfo p = new PageInfo(list);

		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
	}
	/**
	 * @Description  查询用户的审核记录
	 * @author xlx
	 * @Date 上午 7:12 2020/2/25 0025
	 * @Param
	 * @return ,String processkey
	 **/
	@RequestMapping("/myAuditRecord")
	public @ResponseBody
	TableMsg myAuditRecord(int limit,int page, HttpSession session){
		List<Vacation> list=null;
		User user = (User)session.getAttribute("user");
		PageHelper.startPage(page,limit);
		if (user!=null){
			list = ( List<Vacation>)vacationServices.myAuditRecord(user.getAccount());
		}
		PageInfo p = new PageInfo(list);
		TableMsg tableMsg = new TableMsg();
		tableMsg.setCode(0);
		tableMsg.setMsg("");
		tableMsg.setCount((int)p.getTotal());
		tableMsg.setData(p.getList());
		return tableMsg;
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
	boolean passAudit( HttpSession session,VacTask vacTask,String gsonList){
		boolean flag=false;
		List list = new Gson().fromJson(gsonList,new TypeToken< List>(){}.getType());
		vacTask.getVac().setList(list);
		User user = (User)session.getAttribute("user");
		if (user!=null){
			Task task=taskService.createTaskQuery() // 创建任务查询
					.taskId(vacTask.getId()) // 根据任务id查询
					.singleResult();
			ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(vacTask.getVac().getInstanceId()).singleResult();
			flag = (boolean)vacationServices.passAudit(user.getAccount(),vacTask);
			ProcessInstance process1 = runtimeService.createProcessInstanceQuery().processInstanceId(vacTask.getVac().getInstanceId()).singleResult();
			String key = process.getProcessDefinitionKey();
			if (process1==null){
				Vacation vacation = vacationServices.queryHistoryProcess(task.getProcessInstanceId());
				switch(key){
					case TimeUtil.KEY_1:
						//判断药房入库，同意则进入药房药品入库
						drugServices.insertAndUpdate(vacation);
						break;
					case TimeUtil.KEY_2:
						//判断是否是同意发药，同意则进入药品出库
						drugServices.insertOutbound(vacation);
						//开启药房入库的审核流程
						Vacation v = new Vacation();
						v.setReason("药房入库");
						vacationServices.startVac(vacation.getApplyUser(),vacation.getList(),TimeUtil.KEY_1,v);
						break;
					case TimeUtil.KEY_3:
						//药房退库
						drugServices.pharmacyWithdrawal(vacation);
						break;
					case TimeUtil.KEY_4:
						//药库退库
						drugServices.depotwithdrawal(vacation);
						break;
					case TimeUtil.KEY_5:
						//药品报损
						drugServices.insertBreakdownOfDrugs(vacation);
						break;
					default:
						break;
				}
			}
//			if (key.equals("pharmacywithdrawal")||key.equals("depotwithdrawal")){
//
//			}else{
//				if (user.getAccount().equals(TimeUtil.ROLE_ISSUER)&&vacTask.getVac().getNowResult().equals("同意")){
//
//				}else if (user.getAccount().equals(TimeUtil.ROLE_PHMANAGER)&&vacTask.getVac().getNowResult().equals("同意")){
//
//				}
//			}

		}
		return flag;
	}
	/**
	 * @Description  获取流程实时进度
	 * @author xlx
	 * @Date 下午 15:15 2020/3/1 0001
	 * @Param
	 * @return
	 **/
	@RequestMapping("/getFlowChart")
	public void getFlowChart (String id, HttpServletRequest request, HttpServletResponse response){
		response.setContentType("image/png");//设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		try
		{
			vacationServices.getFlowImgByInstanceId(id,response.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
		}


	}
}
