package com.zhyy.services;

import com.zhyy.entity.Menu;

import java.util.List;

public interface SysServices
{
	public List<Menu> queryAllMenu(String wheres,String limits);

	public int count(String tableName,String where);
}
