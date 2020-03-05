package com.zhyy.entity;

import com.google.gson.Gson;

/**
 * @program: SmartMedicine
 * @ClassName ImageResponse
 * @description:图片上传结果回写
 * @author: xlx
 * @create: 2020-03-04 16:48
 * @Version 1.0
 **/
public class ImageResponse
{
	private int code;
	private String msg;
	private String data;

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
	@Override
	public String toString()
	{
		return  new Gson().toJson(this);
	}
}