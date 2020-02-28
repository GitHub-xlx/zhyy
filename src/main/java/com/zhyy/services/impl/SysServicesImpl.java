package com.zhyy.services.impl;

import com.zhyy.entity.Department;
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
	/**
	 * 带条件分页查询菜单
	 * @param wheres
	 * @param limits
	 * @return
	 */
	@Override
	public List<Menu> queryAllMenu(String wheres,String limits)
	{
		return sysMapper.queryAllMenu(wheres,limits);
	}

	/**
	 * 单表统计数据量
	 * @param tableName
	 * @param where
	 * @return
	 */
	@Override
	public int count(String tableName, String where)
	{
		return sysMapper.count(tableName,where);
	}
	/**
	 * 根据角色查询权限菜单
	 * @param rolecode
	 * @return
	 */
	@Override
	public List<Menu> queryPermissionMenu(String rolecode)
	{
		return sysMapper.queryPermissionMenu(rolecode);
	}
	/**
	 * 保存权限配置
	 * @param menucode
	 * @param rolecode
	 * @return
	 */
	@Override
	public int savePermission(String menucode, String rolecode)
	{
		return sysMapper.savePermission(menucode,rolecode);
	}

	/**
	 * 根据角色打开所有菜单权限
	 * @param rolecode
	 * @return
	 */
	@Override
	public int openPermissionByRoleCode(String rolecode)
	{
		return sysMapper.openPermissionByRoleCode(rolecode);
	}

	/**
	 * 查询角色表
	 * @return
	 */
	@Override
	public List<Role> queryRole()
	{
		return sysMapper.queryRole();
	}
	/**
	 * 带条件限制查询角色
	 * @param where
	 * @param limits
	 * @return
	 */
	@Override
	public List<Role> queryAllRole(String where, String limits)
	{
		return sysMapper.queryAllRole(where,limits);
	}

	/**
	 * 根据菜单编号查重
	 * @param menucode
	 * @return
	 */
	@Override
	public int checkMenu(String menucode)
	{
		return sysMapper.checkMenu(menucode);
	}



	/**
	 * 添加菜单
	 * @param menucode
	 * @param parentcode
	 * @param menu
	 * @param url
	 * @return
	 */
	@Override
	public int addMenu(String menucode, String parentcode, String menu, String url)
	{
		return sysMapper.addMenu(menucode,parentcode,menu,url);
	}


	/**
	 * 启禁用菜单
	 * @param menucode
	 * @param state
	 * @return
	 */
	@Override
	public int ableMenu(String menucode, String state)
	{
		return sysMapper.ableMenu(menucode,state);
	}



	/**
	 * 根据父级菜单启禁用菜单
	 * @param parentcode
	 * @param state
	 * @return
	 */
	@Override
	public int ableMenuByParentCode(String parentcode, String state)
	{
		return sysMapper.ableMenuByParentCode(parentcode,state);
	}
	/**
	 * 修改菜单信息
	 * @param menucode
	 * @param menuname
	 * @param url
	 * @return
	 */
	@Override
	public int editMenu(String menucode, String menuname, String url)
	{
		return sysMapper.editMenu(menucode,menuname,url);
	}
	/**
	 * 查询部门
	 * @return
	 */
	@Override
	public List<Department> queryDepartment()
	{
		return sysMapper.queryDepartment();
	}

	/**
	 * 根据角色编号查重
	 * @param rolecode
	 * @return
	 */
	@Override
	public int checkRole(String rolecode)
	{
		return sysMapper.checkRole(rolecode);
	}

	@Override
	public int addRole(String departmentcode, String rolecode, String rolename)
	{
		return sysMapper.addRole(departmentcode,rolecode,rolename);
	}

	@Override
	public int editRole(String rolecode, String rolename)
	{
		return sysMapper.editRole(rolecode,rolename);
	}

	@Override
	public String checkPassword(String account)
	{
		return sysMapper.checkPassword(account);
	}

	@Override
	public int editPassword(String account, String password)
	{
		return sysMapper.editPassword(account,password);
	}


}
