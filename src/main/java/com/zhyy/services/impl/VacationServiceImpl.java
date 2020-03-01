package com.zhyy.services.impl;


import com.zhyy.entity.Druginformation;
import com.zhyy.entity.User;
import com.zhyy.entity.VacTask;
import com.zhyy.entity.Vacation;
import com.zhyy.utils.ActivitiUtil;
import com.zhyy.utils.CustomProcessDiagramGenerator;
import com.zhyy.utils.TimeUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;
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
	@Resource
	private ProcessEngineConfiguration processEngineConfiguration;


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
        vars.put("reason", processkey);
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
        String result = runtimeService.getVariable(instance.getId(), "result", String.class);
        String auditor = runtimeService.getVariable(instance.getId(), "auditor", String.class);
        String auditTime = runtimeService.getVariable(instance.getId(), "auditTime", String.class);
        String durgResult = runtimeService.getVariable(instance.getId(), "durgResult", String.class);
        String dispenser = runtimeService.getVariable(instance.getId(), "dispenser", String.class);
        String medicineTime = runtimeService.getVariable(instance.getId(), "medicineTime", String.class);
	    List<Druginformation> list = runtimeService.getVariable(instance.getId(), "list", List.class);
	    Vacation vac = new Vacation();
        vac.setApplyUser(instance.getStartUserId());
	    vac.setId(instance.getId());
        vac.setReason(reason);
        vac.setList(list);
        vac.setResult(result);
        vac.setAuditor(auditor);
        vac.setAuditTime(auditTime);
        vac.setDurgResult(durgResult);
        vac.setDispenser(dispenser);
        vac.setMedicineTime(medicineTime);
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
            if (userName.equals(vac.getApplyUser())){
	            vacTask.setStart(1);
            }
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
	    String result = vac.getResult();
        Map<String, Object> vars = new HashMap<>();
	    vars.put("message", vac.getNowResult());
        if (userName.equals("issuer")){
	        vars.put("durgResult", result);
	        vars.put("dispenser", userName);
	        vars.put("medicineTime",TimeUtil.getTime(new Date()));
        }else if (userName.equals(vac.getApplyUser())){
	        vars.put("applyUser", userName);
	        vars.put("applyTime", TimeUtil.getTime(new Date()));
	        vars.put("reason", vac.getReason());
	        vars.put("list", vac.getList());
        } else{
	        vars.put("result", result);
	        vars.put("auditor", userName);
	        vars.put("auditTime", TimeUtil.getTime(new Date()));

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

	/**
	 * 根据流程实例Id,获取实时流程图片
	 *
	 * @param processInstanceId
	 * @param outputStream
	 * @return
	 */
	public void getFlowImgByInstanceId(String processInstanceId, OutputStream outputStream) {
		try {
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			// 获取流程中已经执行的节点，按照执行先后顺序排序
			List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
					.orderByHistoricActivityInstanceId().asc().list();
			// 高亮已经执行流程节点ID集合
			List<String> highLightedActivitiIds = new ArrayList<>();
			for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
				highLightedActivitiIds.add(historicActivityInstance.getActivityId());
			}

			List<HistoricProcessInstance> historicFinishedProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).finished()
					.list();
			ProcessDiagramGenerator processDiagramGenerator = null;
			// 如果还没完成，流程图高亮颜色为绿色，如果已经完成为红色
			if (!CollectionUtils.isEmpty(historicFinishedProcessInstances)) {
				// 如果不为空，说明已经完成
				processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
			} else {
				processDiagramGenerator = new CustomProcessDiagramGenerator();
			}

			BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
			// 高亮流程已发生流转的线id集合
			List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);

			// 使用默认配置获得流程图表生成器，并生成追踪图片字符流
			InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds, "宋体", "微软雅黑", "黑体", null, 2.0);

			// 输出图片内容
			byte[] b = new byte[1024];
			int len;
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
				outputStream.write(b, 0, len);
			}
		} catch (Exception e) {
//			logger.error("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage(), e);
			System.out.println("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage());
		}
	}
	/**
	 * 获取已经流转的线
	 *
	 * @param bpmnModel
	 * @param historicActivityInstances
	 * @return
	 */
	private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
		// 高亮流程已发生流转的线id集合
		List<String> highLightedFlowIds = new ArrayList<>();
		// 全部活动节点
		List<FlowNode> historicActivityNodes = new ArrayList<>();
		// 已完成的历史活动节点
		List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

		for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
			FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId(), true);
			historicActivityNodes.add(flowNode);
			if (historicActivityInstance.getEndTime() != null) {
				finishedActivityInstances.add(historicActivityInstance);
			}
		}

		FlowNode currentFlowNode = null;
		FlowNode targetFlowNode = null;
		// 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
		for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
			// 获得当前活动对应的节点信息及outgoingFlows信息
			currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId(), true);
			List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

			/**
			 * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
			 */
			if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
				// 遍历历史活动节点，找到匹配流程目标节点的
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef(), true);
					if (historicActivityNodes.contains(targetFlowNode)) {
						highLightedFlowIds.add(targetFlowNode.getId());
					}
				}
			} else {
				List<Map<String, Object>> tempMapList = new ArrayList<>();
				for (SequenceFlow sequenceFlow : sequenceFlows) {
					for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
						if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
							Map<String, Object> map = new HashMap<>();
							map.put("highLightedFlowId", sequenceFlow.getId());
							map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
							tempMapList.add(map);
						}
					}
				}

				if (!CollectionUtils.isEmpty(tempMapList)) {
					// 遍历匹配的集合，取得开始时间最早的一个
					long earliestStamp = 0L;
					String highLightedFlowId = null;
					for (Map<String, Object> map : tempMapList) {
						long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
						if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
							highLightedFlowId = map.get("highLightedFlowId").toString();
							earliestStamp = highLightedFlowStartTime;
						}
					}

					highLightedFlowIds.add(highLightedFlowId);
				}

			}

		}
		return highLightedFlowIds;
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
