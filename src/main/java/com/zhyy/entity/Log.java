package com.zhyy.entity;

import java.util.Date;

public class Log
{

	private int lid;
	private Date operationdate;
	private String operationdateStr;
	private String account;
	private String username;
	private String ip;
	private String url;
	private long duration;
	private String method;

	public int getLid()
	{
		return lid;
	}

	public void setLid(int lid)
	{
		this.lid = lid;
	}

	public Date getOperationdate()
	{
		return operationdate;
	}

	public void setOperationdate(Date operationdate)
	{
		this.operationdate = operationdate;
	}

	public String getOperationdateStr()
	{
		return operationdateStr;
	}

	public void setOperationdateStr(String operationdateStr)
	{
		this.operationdateStr = operationdateStr;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}
}
