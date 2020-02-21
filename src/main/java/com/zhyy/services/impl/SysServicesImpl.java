package com.zhyy.services.impl;

import com.zhyy.entity.Menu;
import com.zhyy.mapper.SysMapper;
import com.zhyy.services.SysServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysServicesImpl implements SysServices
{
	@Autowired
	private SysMapper sysMapper;

	@Override
	public List<Menu> queryAllMenu(String wheres,String limits)
	{
		return sysMapper.queryAllMenu(wheres,limits);
	}

	@Override
	public int count(String tableName, String where)
	{
		return sysMapper.count(tableName,where);
	}
}
