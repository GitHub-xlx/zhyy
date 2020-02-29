package com.zhyy.services.impl;


import com.zhyy.entity.Druginformation;
import com.zhyy.entity.User;
import com.zhyy.entity.VacTask;
import com.zhyy.entity.Vacation;
import com.zhyy.utils.ActivitiUtil;
import com.zhyy.utils.TimeUtil;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Created by yawn on 2018-01-08 13:44
 */
@Service
public class VacationServiceImpl
{

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private IdentityService identityService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ProcessEngine processEngine;


//    private static final String PROCESS_DEFINE_KEY = "vacationProcess";

    public boolean startVac(String userName, List list,String processkey) {
	    Deployment deployment = processEngine.getRepositoryService().createDeployment().name(processkey)
			    .addClasspathResource("processes/"+processkey+".bpmn")
			    .addClasspathResource("processes/"+processkey+".png")
			    .deploy();
        identityService.setAuthenticatedUserId(userName);
        // 开始流程
        ProcessInstance vacationInstance = runtimeService.startProcessInstanceByKey(processkey);
        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(vacationInstance.getId()).singleResult();
        // 申明任务
        taskService.claim(currentTask.getId(), userName);

        Map<String, Object> vars = new HashMap<>(5);
        vars.put("applyUser", userName);
        vars.put("applyTime", TimeUtil.getTime(new Date()));
//        vars.put("reason", vac.getReason());
        vars.put("list", list);

        taskService.complete(currentTask.getId(), vars);

        return true;
    }

	/**
	 * 查询用户申请的审核
	 * @param userName
	 * @return
	 */
	public Object myVac(String userName) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().startedBy(userName).list();
        List<Vacation> vacList = new ArrayList<>();
        for (ProcessInstance instance : instanceList) {
            Vacation vac = getVac(instance);
            vacList.add(vac);
        }
        return vacList;
    }

	/**
	 * 获取流程状态
	 * @param instance
	 * @return
	 */
    private Vacation getVac(ProcessInstance instance) {
//        Integer days = runtimeService.getVariable(instance.getId(), "days", Integer.class);
        String reason = runtimeService.getVariable(instance.getId(), "reason", String.class);
	    List<Druginformation> list = runtimeService.getVariable(instance.getId(), "list", List.class);
	    Vacation vac = new Vacation();
        vac.setApplyUser(instance.getStartUserId());
	    vac.setId(instance.getId());
        vac.setReason(reason);
        vac.setList(list);
        Date startTime = instance.getStartTime(); // activiti 6 才有
        vac.setApplyTime(TimeUtil.getTime(startTime));
        vac.setApplyStatus(instance.isEnded() ? "申请结束" : "等待审批");
        return vac;
    }

	/**
	 * 获取等待用户的任务列表
	 * @param userName
	 * @return 		 .processDefinitionKey("holiday")
	 */
    public List<VacTask> myAudit(String userName) {
        List<Task> taskList = taskService.createTaskQuery()
		        .taskAssignee(userName)
		        .list();
//        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(userName)
//                .orderByTaskCreateTime().desc().list();
//        / 多此一举 taskList中包含了以下内容(用户的任务中包含了所在用户组的任务)
//        Group group = identityService.createGroupQuery().groupMember(userName).singleResult();
//        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(group.getId()).list();
//        taskList.addAll(list);
        List<VacTask> vacTaskList = new ArrayList<>();
        for (Task task : taskList) {
            VacTask vacTask = new VacTask();
            vacTask.setId(task.getId());
            vacTask.setName(task.getName());
            vacTask.setCreateTime(task.getCreateTime());
            String instanceId = task.getProcessInstanceId();
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            Vacation vac = getVac(instance);
            vacTask.setVac(vac);
            vacTaskList.add(vacTask);
        }
        return vacTaskList;
    }

	/**
	 * 完成任务
	 * @param userName
	 * @param vacTask
	 * @return
	 */
    public Object passAudit(String userName, VacTask vacTask) {
        String taskId = vacTask.getId();
	    Vacation vac = vacTask.getVac();
	    String result =vac.getNowResult()+","+ vac.getResult();
        Map<String, Object> vars = new HashMap<>();
	    vars.put("message", vac.getNowResult());
        if (userName.equals("reviewer")){
        	vars.put("result", result);
	        vars.put("auditor", userName);
	        vars.put("auditTime", TimeUtil.getTime(new Date()));
        }else{
	        vars.put("durgResult", result);
	        vars.put("dispenser", userName);
	        vars.put("medicineTime",TimeUtil.getTime(new Date()));
        }
        taskService.claim(taskId, userName);
        taskService.complete(taskId, vars);
        return true;
    }

	/**
	 * 获取个人历史申请结果
	 * @param userName
	 * @return
	 */
    public Object myVacRecord(String userName) {
        List<HistoricProcessInstance> hisProInstance = historyService.createHistoricProcessInstanceQuery()
		        .startedBy(userName).finished()
                .orderByProcessInstanceEndTime().desc().list();

        List<Vacation> vacList = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : hisProInstance) {
            Vacation vacation = new Vacation();
            vacation.setApplyUser(hisInstance.getStartUserId());
            vacation.setApplyTime(TimeUtil.getTime(hisInstance.getStartTime()));
            vacation.setApplyStatus("申请结束");
            List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(hisInstance.getId()).list();
            ActivitiUtil.setVars(vacation, varInstanceList);
            vacList.add(vacation);
        }
        return vacList;
    }

	/**
	 * 获取用户过往审核记录
	 * @param userName
//	 * @param processkey
	 * @return ,String processkey .processDefinitionKey(processkey)
	 */
    public Object myAuditRecord(String userName) {
        List<HistoricProcessInstance> hisProInstance = historyService.createHistoricProcessInstanceQuery()
		        .involvedUser(userName).finished()
                .orderByProcessInstanceEndTime().desc().list();

//        List<String> auditTaskNameList = new ArrayList<>();
//        auditTaskNameList.add("经理审批");
//        auditTaskNameList.add("总监审批");

        List<Vacation> vacList = new ArrayList<>();
        for (HistoricProcessInstance hisInstance : hisProInstance) {
            List<HistoricTaskInstance> hisTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(hisInstance.getId()).processFinished()
                    .taskAssignee(userName)
//                    .taskNameIn(auditTaskNameList)
                    .orderByHistoricTaskInstanceEndTime().desc().list();
            boolean isMyAudit = false;
            for (HistoricTaskInstance taskInstance : hisTaskInstanceList) {
                if (taskInstance.getAssignee().equals(userName)) {
                    isMyAudit = true;
                }
            }
            if (!isMyAudit) {
                continue;
            }
            Vacation vacation = new Vacation();
            vacation.setApplyUser(hisInstance.getStartUserId());
            vacation.setApplyStatus("申请结束");
	        vacation.setApplyTime(TimeUtil.getTime(hisInstance.getStartTime()));
            List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(hisInstance.getId()).list();
            ActivitiUtil.setVars(vacation, varInstanceList);
            vacList.add(vacation);
        }
        return vacList;
    }
	/**
	 * 删除流程定义
	 * @param depolyId 流程定义id
	 */
	public void deleteProcessDefinition(String depolyId)
	{
//		HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(currTask.getProcessDefinitionId()).singleResult();
//		String depolyId = processDefinition.getDeploymentId();
		repositoryService.deleteDeployment(depolyId,true);
		System.out.println("删除成功");
	}
	/**
	 * 查询历史流程
	 * @param taskId 流程实例id
	 */
	public Vacation queryHistoryProcess(String taskId)
	{
		HistoricProcessInstance hisInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(taskId).singleResult();
		Vacation vacation = new Vacation();
		vacation.setApplyTime(TimeUtil.getTime(hisInstance.getStartTime()));
		vacation.setApplyUser(hisInstance.getStartUserId());

		vacation.setApplyStatus("申请结束");
		List<HistoricVariableInstance> varInstanceList = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(hisInstance.getId()).list();
		ActivitiUtil.setVars(vacation, varInstanceList);
		return vacation;
	}
	//---------回退
	//destTaskId驳回的节点，页面可以选择，默认是上级节点

	/**
	 *
	 * @param flowId
	 * @param processInstanceId 流程实例id
	 * @param user 用户实例
	 * @param destTaskId 回退的目标节点
	 * @param rejectMsg 驳回意见
	 * @return
	 */
	@Transactional
	public String rejectTask(String flowId, String processInstanceId, User user, String destTaskId, String rejectMsg){
//		if(StringUtils.isEmpty(destTaskId)){
//			return "回退目标节点不能为空！";
//		}
//
//		if(isProcessFinishedByProcessInstId(processInstanceId)){
//			return "流程已结束，不能驳回！";
//		}
//
//		Task currTask = processEngine.getTaskService()
//				.createTaskQuery()
//				.processInstanceId(processInstanceId)
//				.taskAssignee(user.getAccount())
//				.singleResult();
//
//		List<HistoricTaskInstance>  hisTaskList  = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
//				.processInstanceId(processInstanceId)
//				.taskDefinitionKey(destTaskId)
//				//.orderByTaskCreateTime()
//				.list();
//
//		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)
//				repositoryService.getProcessDefinition(currTask.getProcessDefinitionId());
//
//		ActivityImpl destActivityImpl = definitionEntity.findActivity(destTaskId);
//		if(destActivityImpl == null){
//			return "要驳回的节点不存在！";
//		}
//
//		ActivityImpl currActivityImpl = definitionEntity.findActivity(currTask.getTaskDefinitionKey());
//
//		//保存当前活动节点的流程想参数
//		List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(0);
//		for(PvmTransition pvmTransition : currActivityImpl.getOutgoingTransitions()){
//			hisPvmTransitionList.add(pvmTransition);
//		}
//
//		//清空当前活动几点的所有流出项
//		currActivityImpl.getOutgoingTransitions().clear();
//		//为当前节点动态创建新的流出项
//		TransitionImpl newTransitionImpl = currActivityImpl.createOutgoingTransition();
//		//为当前活动节点新的流出目标指定流程目标
//		newTransitionImpl.setDestination(destActivityImpl);
//		//保存驳回意见
//		currTask.setDescription(rejectMsg);//设置驳回意见
//		processEngine.getTaskService().saveTask(currTask);
//		//设定驳回标志
//		Map<String, Object> variables = new HashMap<String, Object>(0);
//		variables.put("REJECTED", "驳回");
//		variables.put("assignee", hisTaskList.get(0).getAssignee());
//		//执行当前任务驳回到目标任务draft
//
//		processEngine.getTaskService().complete(currTask.getId(), variables);
//
//
//		//清除目标节点的新流入项
//
//		destActivityImpl.getIncomingTransitions().remove(newTransitionImpl);
//		//清除原活动节点的临时流程项
//
//		currActivityImpl.getOutgoingTransitions().clear();
//		//还原原活动节点流出项参数
//
//		currActivityImpl.getOutgoingTransitions().addAll(hisPvmTransitionList);


		return null;
	}
}
