package com.zhyy.services.impl;/**
 * className
 */

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
	//调价
	@Override
	public boolean adjustmentPrice(int price){
		return userMapper.adjustmentPrice(price);
	}


	@Override
	public User queryUserByAccount(String account)
	{
		System.out.println("进入实现类");

		return userMapper.queryUserByAccount(account);
	}

}
