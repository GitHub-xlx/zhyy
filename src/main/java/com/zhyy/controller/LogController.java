package com.zhyy.controller;

import com.zhyy.aspect.IgnoreLog;
import com.zhyy.entity.Log;
import com.zhyy.entity.TableMsg;
import com.zhyy.services.LogServices;
import com.zhyy.services.SysServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/logController")
public class LogController
{
	@Autowired
	private LogServices logServices;

	@Autowired
	private SysServices sysServices;

	@RequestMapping("/queryAll")
	@IgnoreLog
	public @ResponseBody TableMsg queryAll(String username, String url, String start, String end, int page, int limit){
		String where = "1=1";

		String lim = "limit "+((page-1)*limit)+","+limit;
		if(username!=null){
			where = username.length()>0 ? where+" and username like '%"+username+"%'" : where;
		}
		if (url!=null){
			where = url.length()>0 ? where+" and url like '%"+url+"%'" : where;
		}
		if (start!=null){
			where = start.length()>0 ? where+" and operationdate >= '"+start+"'" : where;
		}
		if (end!=null){
			where = end.length()>0 ? where+" and operationdate <= '"+end+"'" : where;
		}
		int countPage = sysServices.count("log",where);
		List<Log> list = logServices.queryAll(where,lim);

		return new TableMsg(0,"",countPage,list);
	}
}
