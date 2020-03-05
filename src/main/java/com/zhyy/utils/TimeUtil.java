package com.zhyy.utils;

import org.activiti.engine.history.HistoricVariableInstance;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     *  pharmacystorage 药房入库
     */
    public final static String KEY_1 = "pharmacystorage";

    /**
     *  drugclaim 药品请领
     */
    public final static String KEY_2 = "drugclaim";
    /**
     *  pharmacywithdrawal 药房退库
     */
    public final static String KEY_3 = "pharmacywithdrawal";
    /**
     *  depotwithdrawal 药库退库
     */
    public final static String KEY_4 = "depotwithdrawal";
    /**
     *  drugdamaged 药品报损
     */
    public final static String KEY_5 = "drugdamaged";
    /**
     * 获取yyyy-MM-dd HH:mm:ss格式的是时间
     */
    public static String getTime(Date date) {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
    /**
     * 获取本周，本月，本季度的时间段
     */
    public static String[] getTime(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String[] arr=null;
        switch (time){
            case "1":
                //获取本周一的日期
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                arr[0]=df.format(cal.getTime());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                //增加一个星期，才是我们中国人理解的本周日的日期
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                arr[1]=df.format(cal.getTime());
                break;
            case "2":
                //1:本月第一天
                cal.set(Calendar.DAY_OF_MONTH,1);
                arr[0]= df.format(cal.getTime());
                //获取当前月最后一天
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                arr[1]=df.format(cal.getTime());
                break;
            case "3":
                int currentMonth =cal.get(Calendar.MONTH) + 1;
                try {
                    if (currentMonth >= 1 && currentMonth <= 3){
                        cal.set(Calendar.MONTH, 1);
                    } else if (currentMonth >= 4 && currentMonth <= 6){
                        cal.set(Calendar.MONTH, 3);
                    }else if (currentMonth >= 7 && currentMonth <= 9){
                        cal.set(Calendar.MONTH, 4);
                    }else if (currentMonth >= 10 && currentMonth <= 12){
                        cal.set(Calendar.MONTH, 9);
                    }
                    cal.set(Calendar.DATE, 1);
                    arr[0]= df.format(cal.getTime());
                    cal.add(Calendar.MONTH, 3);
                    arr[1]=df.format(cal.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
        }
        return arr;
    }
}
