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


	public List<User> queryUserList(int pageInt,int limitInt );
	public int countUserList();
    public boolean resetPassword(String account);
	public boolean enableUser(String account);
	public boolean disableUser(String account);
	public boolean adjustmentPrice(int price);


	public User queryUserByAccount(String account);
}
