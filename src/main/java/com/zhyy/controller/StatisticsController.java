package com.zhyy.controller;

import com.zhyy.aspect.IgnoreLog;
import com.zhyy.entity.Log;
import com.zhyy.entity.People;
import com.zhyy.entity.TableMsg;
import com.zhyy.services.DrugServices;
import com.zhyy.services.LogServices;
import com.zhyy.services.SysServices;
import com.zhyy.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/statisticsController")
public class StatisticsController
{
	@Autowired
	private DrugServices drugServices;

	/**
	 * @Description 药品请领统计
	 * @author xlx
	 * @Date 下午 17:27 2020/3/2 0002
	 * @Param [time]
	 * @return java.lang.String
	 **/
	@RequestMapping("/claim")
	public @ResponseBody List<People> queryAll(String time){
		String[] time1 = TimeUtil.getTime(time);
		return null;
	}
}
