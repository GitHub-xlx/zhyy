package com.zhyy.controller;

import com.zhyy.aspect.IgnoreLog;
import com.zhyy.entity.*;
import com.zhyy.services.SysServices;
import com.zhyy.utils.List2TreeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sysController")
public class SysController
{
	@Autowired
	private SysServices sysServices;

	@RequestMapping("/menuManage")
	@IgnoreLog
	public @ResponseBody
	TableMsg menuManage(String menucode,String menu,int page,int limit, HttpServletRequest request){

		System.out.println("进入controller");
		String where = "1=1";

		String lim = "limit "+((page-1)*limit)+","+limit;
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
	@IgnoreLog
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
	@IgnoreLog
	public @ResponseBody List<Role> queryRole(){

		return sysServices.queryRole();
	}
	@RequestMapping("/roleManage")
	@IgnoreLog
	public @ResponseBody
	TableMsg menuManage(String rolecode,String rolename,String department,int page,int limit, HttpServletRequest request){

		System.out.println("进入controller");
		String where = "A.departmentcode = B.departmentcode";

		String lim = "limit "+((page-1)*limit)+","+limit;
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
	@IgnoreLog
	public @ResponseBody List<Menu> queryParentMenu(){

		return sysServices.queryAllMenu("parentcode = '0'",null);
	}

	@RequestMapping("/checkMenu")
	@IgnoreLog
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
	public @ResponseBody ResultInfo editMenu(String menucode,String menuname,String url){

		ResultInfo resultInfo;

		if (sysServices.editMenu(menucode,menuname,url)>0){
			resultInfo = new ResultInfo(200,"添加成功");
		}else{
			return new ResultInfo(305,"新增失败，请重试");
		}
		return resultInfo;
	}


	@RequestMapping("/queryDepartment")
	@IgnoreLog
	public @ResponseBody List<Department> queryDepartment(){

		return sysServices.queryDepartment();
	}

	@RequestMapping("/checkRole")
	@IgnoreLog
	public @ResponseBody ResultInfo checkRole(String rolecode)
	{
		ResultInfo resultInfo;
		if (sysServices.checkRole(rolecode)>0){
			resultInfo =  new ResultInfo(300,"编号已存在");
		}else{
			resultInfo =  new ResultInfo(200,"编号可用");
		}

		return resultInfo;
	}

	@RequestMapping("/addRole")
	public @ResponseBody ResultInfo addRole(String department,String rolecode,String rolename){

		ResultInfo resultInfo;

		if (sysServices.addRole(department,rolecode,rolename)>0){
			resultInfo = new ResultInfo(200,"添加成功");
		}else{
			return new ResultInfo(305,"新增失败，请重试");
		}
		return resultInfo;
	}
	@RequestMapping("/editRole")
	public @ResponseBody ResultInfo editRole(String rolecode,String rolename){

		ResultInfo resultInfo;

		if (sysServices.editRole(rolecode,rolename)>0){
			resultInfo = new ResultInfo(200,"添加成功");
		}else{
			return new ResultInfo(305,"新增失败，请重试");
		}
		return resultInfo;
	}

	@RequestMapping("/checkPassword")
	@IgnoreLog
	public @ResponseBody ResultInfo checkPassword(String account,String password)
	{
		ResultInfo resultInfo;
		if (!sysServices.checkPassword(account).equals(password)){
			resultInfo =  new ResultInfo(300,"密码输入错误");
		}else{
			resultInfo =  new ResultInfo(200,"密码输入正确");
		}

		return resultInfo;
	}

	@RequestMapping("/editPassword")
	public @ResponseBody ResultInfo editPassword(String account,String newPassword){

		ResultInfo resultInfo;

		if (sysServices.editPassword(account,newPassword)>0){
			resultInfo = new ResultInfo(200,"密码修改成功");
		}else{
			return new ResultInfo(305,"密码修改失败");
		}
		return resultInfo;
	}

	@RequestMapping("/exit")
	@IgnoreLog
	public String exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getSession().invalidate();
		request.getRequestDispatcher("/userController/welcome").forward(request,response);
		return null;
	}

}

