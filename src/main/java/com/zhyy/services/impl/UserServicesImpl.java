package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.Druginformation;
import com.zhyy.entity.Drugstoredruginventory;
import com.zhyy.entity.Menu;
import com.zhyy.entity.User;
import com.zhyy.mapper.UserMapper;
import com.zhyy.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@author Administrator
 *Date 2020/2/14
 */
@Service
@Transactional
public class UserServicesImpl implements UserServices
{
	@Autowired
	private UserMapper userMapper;


    //查询用户信息
	@Override
	public List<User> queryUserList(int pageInt, int limitInt)
	{
		return userMapper.queryUserList(pageInt,limitInt);
	}

	//查询用户总数
	@Override
	public int countUserList()
	{
		return userMapper.countUserList();
	}

	//新增人员
	@Override
	public boolean regStaff(String account, String password, String username, String phone, String sex, String age, String role, String title, String rolecode, String pharmacycode, String state)
	{
		return userMapper.regStaff(account,password,username,phone,sex,age,role,title,rolecode,pharmacycode,state);
	}

	//重置密码
	@Override
	public boolean resetPassword(String account)
	{
		return userMapper.resetPassword(account);
	}
	//启用
	@Override
	public boolean enableUser(String account)
	{
		return userMapper.enableUser(account);
	}
	//禁用
	@Override
	public boolean disableUser(String account)
	{
		return userMapper.disableUser(account);
	}
	//药品调价
	@Override
	public boolean adjustmentPrice(double price,String drugcode){
		return userMapper.adjustmentPrice(price,drugcode);
	}
    //药品停用(药库的药品状态)
	@Override
	public boolean drugDiscontinuation( String drugcode)
	{
		return userMapper.drugDiscontinuation(drugcode);
	}
	//药品停用(药房的药品状态)
	@Override
	public boolean drug2Discontinuation(String drugcode)
	{
		return userMapper.drug2Discontinuation(drugcode);
	}

	//查询药库库存数量不足的药品
	@Override
	public List<Drugstoredruginventory> checkInventoryCount()
	{
		return userMapper.checkInventoryCount();
	}
	//根据药品编码查询药品名称
	@Override
	public List<Druginformation> findDrugNameByDrugCode(String drugcode)
	{
		return userMapper.findDrugNameByDrugCode(drugcode);
	}

	@Override
	public List<Menu> queryMenuList(String rolecode)
	{
		return userMapper.queryMenuList(rolecode);
	}

	@Override
	public User queryUserByAccount(String account)
	{
		System.out.println("进入实现类");

		return userMapper.queryUserByAccount(account);
	}

}
