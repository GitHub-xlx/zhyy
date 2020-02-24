package com.zhyy.entity;

import java.util.List;

public class TreeData
{
	private String title;
	private int id;
	private List<TreeData> children;
	private boolean checked = false ;
	private String rolecode;

	public String getRolecode()
	{
		return rolecode;
	}

	public void setRolecode(String rolecode)
	{
		this.rolecode = rolecode;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<TreeData> getChildren()
	{
		return children;
	}

	public void setChildren(List<TreeData> children)
	{
		this.children = children;
	}
}
