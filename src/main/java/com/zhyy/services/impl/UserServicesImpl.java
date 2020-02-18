package com.zhyy.services.impl;/**
 * className
 */

import com.zhyy.entity.User;
import com.zhyy.mapper.UserMapper;
import com.zhyy.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public User queryUserByAccount(String account)
	{
		System.out.println("进入实现类");

		return userMapper.queryUserByAccount(account);
	}

}
