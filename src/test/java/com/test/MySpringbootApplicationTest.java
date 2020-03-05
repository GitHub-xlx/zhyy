package com.test;

import com.zhyy.MySpringbootApplication;
import com.zhyy.entity.Druginformation;
import com.zhyy.entity.Drugstoredruginventory;
import com.zhyy.entity.User;
import com.zhyy.entity.*;
import com.zhyy.mapper.DrugMapper;
import com.zhyy.mapper.UserMapper;
import org.activiti.engine.RepositoryService;
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
//	@Resource
//	private RepositoryService repositoryService;
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

//		Purchasestatistics test = new Purchasestatistics();
//		test.setDrugcode("001国药准字H20070077");
//		test.setOperator("药库管理员");
//		test.setDate("2020-02-09");
//		int i = drugMapper.savePurchaseStatistics(test);
		List<Purchasestatistics> list = drugMapper.selectPurchaseStatistics();
		System.out.println("测试查询结果的：===="+list.size());



	}
}
