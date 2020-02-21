package com.zhyy.controller;

import com.zhyy.entity.Menu;
import com.zhyy.entity.TableMsg;
import com.zhyy.services.SysServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
		int pages = Integer.valueOf(page);
		int limits = Integer.valueOf(limit);
		int countPage = 0;
		String lim = "limit "+(pages-1)*limits+","+limits;
		if(menucode!=null){
			where = menucode.length()>0 ? where+"and menucode = '"+menucode+"'" : where;
		}
		if (menu!=null){
			where = menu.length()>0 ? where+"and menu = '"+menu+"'" : where;
		}

		countPage = sysServices.count("menu",where);
		List<Menu> list = sysServices.queryAllMenu(where,lim);

		return new TableMsg(0,"",countPage,list);
	}
}
