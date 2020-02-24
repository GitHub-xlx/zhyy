package com.zhyy.services;

import com.zhyy.entity.Menu;
import com.zhyy.entity.Role;

import java.util.List;

public interface SysServices
{
	/**
	 * 菜单管理分页
	 * @param wheres
	 * @param limits
	 * @return
	 */
	public List<Menu> queryAllMenu(String wheres,String limits);

	/**
	 * 单表带条件数据量统计
	 * @param tableName
	 * @param where
	 * @return
	 */
	public int count(String tableName,String where);

	/**
	 * 根据角色编号查询权限菜单
	 * @param rolecode
	 * @return
	 */
	public List<Menu> queryPermissionMenu(String rolecode);

	/**
	 * 保存权限配置
	 * @param menucode
	 * @param rolecode
	 * @return
	 */
	public int savePermission(String menucode,String rolecode);

	/**
	 * 根据角色ID开放所有权限
	 * @param rolecode
	 * @return
	 */
	public int openPermissionByRoleCode(String rolecode);

	/**
	 * 查询角色表
	 * @return
	 */
	public List<Role> queryRole();

	/**
	 * 角色表带条件分页查询
	 * @param where
	 * @param limits
	 * @return
	 */
	public List<Role>queryAllRole(String where,String limits);

}
