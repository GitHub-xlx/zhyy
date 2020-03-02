package com.zhyy.entity;

import com.google.gson.Gson;

/**
 * @Description  统计插件的实体类
 * @author xlx
 * @Date 下午 17:23 2020/3/2 0002
 * @Param
 * @return
 **/
public class People
{
	private String name;
	private int record;
	

	public People(String name, int record)
	{
		this.name = name;
		this.record = record;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRecord()
	{
		return record;
	}

	public void setRecord(int record)
	{
		this.record = record;
	}

	@Override
	public String toString()
	{
		return  new Gson().toJson(this);
	}
	
	
}
