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
	@Override
	public List<User> queryUserList()
	{
		System.out.println("services **************** ");
		return userMapper.queryUserList();
	}

	@Override
	public User queryUserByAccount(String account)
	{
		System.out.println("进入实现类");

		return userMapper.queryUserByAccount(account);
	}

}
