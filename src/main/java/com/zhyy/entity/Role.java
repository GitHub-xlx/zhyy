package com.zhyy.entity;

public class Role
{

	private String rid;
	private String rolecode;
	private String rolename;
	private String departmentcode;

	public String getDepartmentcode()
	{
		return departmentcode;
	}

	public void setDepartmentcode(String departmentcode)
	{
		this.departmentcode = departmentcode;
	}

	public String getRid()
	{
		return rid;
	}

	public void setRid(String rid)
	{
		this.rid = rid;
	}

	public String getRolecode()
	{
		return rolecode;
	}

	public void setRolecode(String rolecode)
	{
		this.rolecode = rolecode;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}
}
