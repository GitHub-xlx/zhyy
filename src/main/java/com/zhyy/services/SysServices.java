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

	/**
	 * 查询菜单编号是否存在
	 * @param menucode
	 * @return
	 */
	public int checkMenu(String menucode);

	/**
	 * 添加菜单
	 * @param menucode
	 * @param parentcode
	 * @param menu
	 * @param url
	 * @return
	 */
	public int addMenu(String menucode,String parentcode,String menu,String url);

	/**
	 * 启禁用菜单
	 * @param menucode
	 * @param state
	 * @return
	 */
	public int ableMenu(String menucode,String state);

	/**
	 * 根据父级菜单启禁用
	 * @param parentcode
	 * @param state
	 * @return
	 */
	public int ableMenuByParentCode(String parentcode,String state);

	/**
	 * 修改菜单
	 * @param menucode
	 * @param menuname
	 * @param url
	 * @return
	 */
	public int editMenu(String menucode,String menuname,String url);


}
