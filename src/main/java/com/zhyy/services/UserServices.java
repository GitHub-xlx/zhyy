package com.zhyy.services;

import com.zhyy.entity.*;

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
	public boolean regStaff(String account,String password,String username,String phone,String sex,
	                        String age,String role,String title,String rolecode,String pharmacycode,String state);
	public boolean resetPassword(String account);
	public boolean enableUser(String account);
	public boolean disableUser(String account);
	public boolean adjustmentPrice(double price,String drugcode);
	public boolean drugDiscontinuation(String drugcode);
	public boolean drug2Discontinuation(String drugcode);
	public List<Drugstoredruginventory> checkInventoryCount();
	public String findDrugNameByDrugCode(String drugcode);
	public List<User> queryUserList(int pageInt,int limitInt );
	public int countUserList();
	public List<Menu> queryMenuList(String rolecode);
	public List<Druginventorytable> expiredCheck();
	public List<Druginventorytable> unsalableCheck();


	public User queryUserByAccount(String account);
}
