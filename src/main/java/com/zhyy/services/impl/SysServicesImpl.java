package com.zhyy.services.impl;

import com.zhyy.entity.Menu;
import com.zhyy.entity.Role;
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

	@Override
	public List<Menu> queryPermissionMenu(String rolecode)
	{
		return sysMapper.queryPermissionMenu(rolecode);
	}

	@Override
	public int savePermission(String menucode, String rolecode)
	{
		return sysMapper.savePermission(menucode,rolecode);
	}

	@Override
	public int openPermissionByRoleCode(String rolecode)
	{
		return sysMapper.openPermissionByRoleCode(rolecode);
	}

	@Override
	public List<Role> queryRole()
	{
		return sysMapper.queryRole();
	}

	@Override
	public List<Role> queryAllRole(String where, String limits)
	{
		return sysMapper.queryAllRole(where,limits);
	}


}
