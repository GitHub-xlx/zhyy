package com.test;

import com.zhyy.MySpringbootApplication;
import com.zhyy.entity.Druginformation;
import com.zhyy.entity.Drugstoredruginventory;
import com.zhyy.entity.User;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.mapper.UserMapper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mybatis测试类
 *@author Administrator
 *Date 2020/2/14
 */

//@SpringBootTest(classes = MySpringbootApplication.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringbootApplication.class)
public class MySpringbootApplicationTest
{
	@Autowired
//	private UserMapper userMapper;
	private DrugMapper drugMapper;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private RuntimeService runtimeService;
	@Test
	public void test()
	{

//		System.out.println(111);
//		repositoryService.deleteDeployment("drugclaim",true);
//		System.out.println(1121);
//		List<User> list = userMapper.queryUserList();
//		System.out.println(list);
//
//		Druginformation druginformation = new Druginformation();
//		druginformation.setClasscode("001");
//		druginformation.setCommoname("测试");
//		int i = drugMapper.saveDrugInfo(druginformation);
//		System.out.println("DrugInformation的结果="+i);
//		Druginformation druginformation = new Druginformation();
//		druginformation.setClasscode("001");
//		druginformation.setCommoname("测试");
//		int i = drugMapper.saveDrugInfo(druginformation);
//		System.out.println("DrugInformation的结果="+i);

//		Druginformation druginformation = new Druginformation();
//		druginformation.setClasscode("001");
//		druginformation.setCommoname("测试");
//		int i = drugMapper.saveDrugInfo(druginformation);
//		List<Drugstoredruginventory> list = drugMapper.selectDrugStoreInventory();
		////		System.out.println("DrugInformation的结果="+list.toString());
//
//		Drugstoredruginventory test = new Drugstoredruginventory();
//		test.setDrugcode("测试国字号");
//		test.setDrugstatus("启用");
//		test.setDruginventory(1);
//		int i = drugMapper.saveDrugStoreInventory(test);
//		System.out.println("test测试结果="+i);

//		Drugstoredruginventory test = new Drugstoredruginventory();
//		test.setDrugcode("测试国字号");
//		test.setDrugstatus("启用");
//		test.setDruginventory(1);
//		int i = drugMapper.saveDrugStoreInventory(test);
//		System.out.println("test测试结果="+i);
//		Purchasestatistics test = new Purchasestatistics();
//		test.setDrugcode("001国药准字H20070077");
//		test.setOperator("药库管理员");
//		test.setDate("2020-02-09");
//		int i = drugMapper.savePurchaseStatistics(test);
//		List<Purchasestatistics> list = drugMapper.selectPurchaseStatistics();
//		System.out.println("测试查询结果的：===="+list.size());


	}

	/**
	 * @Description  流程部署的方法
	 * @author xlx
	 * @Date 上午 8:08 2020/3/2 0002
	 * @Param
	 * @return
	 **/
	@Test
	public void start()
	{
		//pharmacystorage 药房入库 drugclaim 药品请领 pharmacywithdrawal 药房退库 depotwithdrawal 药库退库 drugdamaged 报损
		String processkey = "drugdamaged";
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name(processkey)
				.addClasspathResource("processes/"+processkey+".bpmn")
				.addClasspathResource("processes/"+processkey+".png")
				.deploy();
	}
	/**
	 * @Description  删除流程定义的方法
	 * @author xlx
	 * @Date 上午 8:10 2020/3/2 0002
	 * @Param
	 * @return
	 **/
	@Test
	public void delete()
	{
		List<ProcessDefinition> pdList=processEngine.getRepositoryService() // 获取service类
				.createProcessDefinitionQuery() // 创建流程定义查询
				.processDefinitionKey("pharmacystorag") // 通过key查询
				.list(); // 返回一个集合
		for(ProcessDefinition pd:pdList){
			System.out.println("ID_："+pd.getId());
			repositoryService.deleteDeployment(pd.getDeploymentId(),true);
			System.out.println("NAME_："+pd.getName());
			System.out.println("KEY_："+pd.getKey());
			System.out.println("VERSION_："+pd.getVersion());
			System.out.println("===================");
		}
	}
}
