package com.zhyy.utils;

import com.zhyy.entity.Menu;
import com.zhyy.entity.TreeData;

import java.util.ArrayList;
import java.util.List;

/**
 * 将数据库查出来的list数据抓成树状结构
 *
 * @author zgd
 */
public class List2TreeUtil
{

	/**
	 * 根级父ID
	 */
	private static final String ROOT_PARENT_ID = "0";

	/**
	 * 将普通的entity的集合转成存在树状结构的集合
	 *
	 * @param list
	 * @return
	 */
	public static List<TreeData> getDtoTreeList(List<Menu> list,String rolecode)
	{
		List<TreeData> dtos = new ArrayList<>();
		if (list == null || list.isEmpty())
		{
			return dtos;
		}
		for (Menu menu : list)
		{
			if (ROOT_PARENT_ID.equals(menu.getParentcode()))
			{
				//找到顶层目录
				TreeData menuLayui = new TreeData();
				//将实体类的数据转成dto
				menuLayui.setId(Integer.valueOf(menu.getMenucode()));
				menuLayui.setTitle(menu.getMenu());
				//				BeanUtils.copyProperties(menu,menuLayui);
				//获取该实体类下的所有子级
				dtos.add(findChildren(menuLayui, list,rolecode));
			}
		}
		return dtos;
	}

	/**
	 * 获取dto类下面的所有子级,存到dto中的children中
	 *
	 * @return
	 */
	private static TreeData findChildren(TreeData treeData, List<Menu> list,String rolecode)
	{
		for (Menu menu : list)
		{
			if (treeData.getId() == (Integer.valueOf(menu.getParentcode())))
			{
				//此时menu是menuDto下的子级
				if (treeData.getChildren() == null)
				{
					treeData.setChildren(new ArrayList<TreeData>());
				}
				TreeData mlayui = new TreeData();
				mlayui.setId(Integer.valueOf(menu.getMenucode()));
				mlayui.setTitle(menu.getMenu());
				mlayui.setRolecode(rolecode);
				if (menu.getState().equals("1")){
					mlayui.setChecked(true);
				}
				//				BeanUtils.copyProperties(menu,menuLayui);
				//递归
				treeData.getChildren().add(findChildren(mlayui, list,rolecode));
			}
		}
		return treeData;
	}

}

