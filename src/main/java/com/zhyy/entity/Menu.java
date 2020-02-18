package com.zhyy.entity;

public class Menu
{

	private int mid;
	private String menucode;
	private String parentcode;
	private String menu;
	private String url;
	private String rolecode;
	private String state;

	public int getMid()
	{
		return mid;
	}

	public void setMid(int mid)
	{
		this.mid = mid;
	}

	public String getMenucode()
	{
		return menucode;
	}

	public void setMenucode(String menucode)
	{
		this.menucode = menucode;
	}

	public String getParentcode()
	{
		return parentcode;
	}

	public void setParentcode(String parentcode)
	{
		this.parentcode = parentcode;
	}

	public String getMenu()
	{
		return menu;
	}

	public void setMenu(String menu)
	{
		this.menu = menu;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getRolecode()
	{
		return rolecode;
	}

	public void setRolecode(String rolecode)
	{
		this.rolecode = rolecode;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
}
