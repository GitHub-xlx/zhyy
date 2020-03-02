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
	public List<User> checkUser();
	public boolean regStaff(String account,String password,String username,String phone,String sex,
	                        String age,String role,String title,String rolecode,String pharmacycode,String state);
	public boolean updateStaff(String password,String username,String phone,String sex,String age,String role,String title,String rolecode);
	public boolean resetPassword(String account);
	public boolean enableUser(String account);
	public boolean disableUser(String account);
	public boolean adjustmentPrice(double price,String drugcode);
	public boolean adjustmentPrice2(double priceNow,double beforeprice,String drugCode,String dataTime);
	public boolean drugDiscontinuation(String drugcode);
	public boolean drug2Discontinuation(String drugcode);
	public boolean drugEnable(String drugcode);
	public boolean drugEnable2(String drugcode);
	public List<Drugstoredruginventory> checkInventoryCount();
	public List<Druginventorytable> checkPharmacyInventoryCount();
	public String findDrugNameByDrugCode(String drugcode);
	public List<User> queryUserList(int pageInt,int limitInt );
	public int countUserList();
	public List<Menu> queryMenuList(String rolecode);
	public List<Druginventorytable> expiredCheck();
	public List<Druginventorytable> unsalableCheck();
    public boolean expiredStatus(String drugcode);
	public boolean expiredStatus2(String drugcode);
	public boolean unsalableStatus(String drugcode);
	public String findDrugPriceByDrugCode(String drugCode);
	public User queryUserByAccount(String account);
}
