package com.zhyy.utils;

import org.activiti.engine.history.HistoricVariableInstance;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 获取现在时间格式的工具方法
 * @author Created by yawn on 2018-01-10 16:32
 */
public class TimeUtil
{
    /**
     *  申请人
     */
    public final static String ROLE_APPLICANT = "applicant";
    /**
     *  药库审核人
     */
    public final static String ROLE_REVIEWER = "reviewer";
    /**
     *  药库仓管
     */
    public final static String ROLE_ISSUER = "issuer";
    /**
     *  药房审核人
     */
    public final static String ROLE_PHMANAGER = "phmanager";
    /**
     * 获取yyyy-MM-dd HH:mm:ss格式的是时间
     */
    public static String getTime(Date date) {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}
