package com.zhyy.controller;

import com.zhyy.entity.*;
import com.zhyy.services.SysServices;
import com.zhyy.utils.List2TreeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sysController")
public class SysController
{
	@Autowired
	private SysServices sysServices;

	@RequestMapping("/menuManage")
	public @ResponseBody
	TableMsg menuManage(String menucode,String menu,String page,String limit, HttpServletRequest request){

		System.out.println("进入controller");
		String where = "1=1";

		String lim = "limit "+(Integer.valueOf(page)-1)*Integer.valueOf(limit)+","+Integer.valueOf(limit);
		if(menucode!=null){
			where = menucode.length()>0 ? where+" and menucode like '%"+menucode+"%'" : where;
		}
		if (menu!=null){
			where = menu.length()>0 ? where+" and menu like '%"+menu+"%'" : where;
		}

		int countPage = sysServices.count("menu",where);
		List<Menu> list = sysServices.queryAllMenu(where,lim);

		return new TableMsg(0,"",countPage,list);
	}

	@RequestMapping("/permissionManage")
	public @ResponseBody
	Object menumanger(String rolecode)
	{


		List<Menu> treeList = sysServices.queryPermissionMenu(rolecode);
		List<TreeData> treeData = List2TreeUtil.getDtoTreeList(treeList,rolecode);
		for (int i = 0; i < treeData.size(); i++)
		{
			if (treeData.get(i).getChildren() == null)
			{
				treeData.remove(treeData.get(i));
				i--;
			}
		}
		return treeData;
	}

	@RequestMapping("/savePermission")
	public @ResponseBody ResultInfo savePermission(@RequestBody List<TreeData> list){

		boolean flag = false;
		ResultInfo resultInfo;
		boolean once = true;
			for (int i = 0; i <list.size() ; i++)
			{
				for (TreeData treeData :list.get(i).getChildren()){
					if (once){
						sysServices.openPermissionByRoleCode(treeData.getRolecode());
						once = false;
					}
					int n =sysServices.savePermission(treeData.getId()+"",treeData.getRolecode());
					flag = n > 0;
				}
			}


		if (flag){
			resultInfo = new ResultInfo(200,"修改成功");
		}else{
			resultInfo = new ResultInfo(500,"修改失败");

		}

		return resultInfo;
	}

	@RequestMapping("/openPermissionByRoleCode")
	public @ResponseBody ResultInfo openPermissionByRoleCode(String rolecode){

		boolean flag = false;
		ResultInfo resultInfo;

			int n = sysServices.openPermissionByRoleCode(rolecode);
			flag = n > 0;


		if (flag){
			resultInfo = new ResultInfo(200,"修改成功");
		}else{
			resultInfo = new ResultInfo(500,"修改失败");

		}

		return resultInfo;
	}


	@RequestMapping("/queryRole")
	public @ResponseBody List<Role> queryRole(){

		return sysServices.queryRole();
	}
	@RequestMapping("/roleManage")
	public @ResponseBody
	TableMsg menuManage(String rolecode,String rolename,String department,String page,String limit, HttpServletRequest request){

		System.out.println("进入controller");
		String where = "A.departmentcode = B.departmentcode";

		String lim = "limit "+(Integer.valueOf(page)-1)*Integer.valueOf(limit)+","+Integer.valueOf(limit);
		if(rolecode!=null){
			where = rolecode.length()>0 ? where+" and rolecode like '%"+rolecode+"%'" : where;
		}
		if (rolename!=null){
			where = rolename.length()>0 ? where+" and rolename like '%"+rolename+"%'" : where;
		}
		if (department!=null){
			where = department.length()>0 ? where+" and B.departmentname like '%"+department+"%'" : where;
		}
		int countPage = sysServices.count("role A,department B",where);
		List<Role> list = sysServices.queryAllRole(where,lim);

		return new TableMsg(0,"",countPage,list);
	}

	@RequestMapping("/queryParentMenu")
	public @ResponseBody List<Menu> queryParentMenu(){

		return sysServices.queryAllMenu("parentcode = '0'",null);
	}

	@RequestMapping("/checkMenu")
	public @ResponseBody ResultInfo checkMenu(String menucode)
	{
		ResultInfo resultInfo;
		if (sysServices.checkMenu(menucode)>0){
			resultInfo =  new ResultInfo(300,"编号已存在");
		}else{
			resultInfo =  new ResultInfo(200,"编号可用");
		}

		return resultInfo;
	}

	@RequestMapping("/addMenu")
	public @ResponseBody ResultInfo addMenu(String parentcode,String menucode,String menuname,String url){

		ResultInfo resultInfo;

		if (sysServices.addMenu(menucode,parentcode,menuname,url)>0){
			resultInfo = new ResultInfo(200,"添加成功");
		}else{
			return new ResultInfo(305,"新增失败，请重试");
		}
		return resultInfo;
	}

	@RequestMapping("/ableMenu")
	public @ResponseBody ResultInfo ableMenu(String menucode,int state,int parentcode){
		ResultInfo resultInfo;
		int n;

		if (parentcode==0){
			if (state==0){
				n = sysServices.ableMenuByParentCode(menucode,"1");
				n = sysServices.ableMenu(menucode,"1");
			}else{
				n = sysServices.ableMenuByParentCode(menucode,"0");
				n = sysServices.ableMenu(menucode,"0");
			}
		}else {
			if (state==0){
				n = sysServices.ableMenu(menucode,"1");
			}else{
				n = sysServices.ableMenu(parentcode+"","0");
				n = sysServices.ableMenu(menucode,"0");
			}
		}

		if (n>0){
			resultInfo = new ResultInfo(200,"修改成功");
		}else{
			resultInfo = new ResultInfo(500,"修改失败");
		}
		return resultInfo;
	}

	@RequestMapping("/editMenu")
	public @ResponseBody ResultInfo addMenu(String menucode,String menuname,String url){

		ResultInfo resultInfo;

		if (sysServices.editMenu(menucode,menuname,url)>0){
			resultInfo = new ResultInfo(200,"添加成功");
		}else{
			return new ResultInfo(305,"新增失败，请重试");
		}
		return resultInfo;
	}

}

