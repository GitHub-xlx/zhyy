package com.zhyy.services;

import com.zhyy.entity.User;

import java.util.List;

/**
 * 测试用户信息业务相关类
 * className
 * @author Administrator
 */
public interface UserServices
{
	/**
	 * 测试 查询用户信息服务层
	 * @return
	 */
	public List<User> queryUserList();
}
