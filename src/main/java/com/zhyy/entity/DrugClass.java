package com.zhyy.entity;/**
 * className 药品分类实体类
 */

/**
 *@author Administrator
 *Date 2020/2/24
 */
public class DrugClass
{
	private  Integer dcid;
	private  String parentcode;
	private String parentname;
	private String classname;
	private String classcode;

	public DrugClass(Integer dcid, String parentcode, String parentname, String classname, String classcode)
	{
		this.dcid = dcid;
		this.parentcode = parentcode;
		this.parentname = parentname;
		this.classname = classname;
		this.classcode = classcode;
	}

	public DrugClass()
	{

	}

	public Integer getDcid()
	{
		return dcid;
	}

	public void setDcid(Integer dcid)
	{
		this.dcid = dcid;
	}

	public String getParentcode()
	{
		return parentcode;
	}

	public void setParentcode(String parentcode)
	{
		this.parentcode = parentcode;
	}

	public String getParentname()
	{
		return parentname;
	}

	public void setParentname(String parentname)
	{
		this.parentname = parentname;
	}

	public String getClassname()
	{
		return classname;
	}

	public void setClassname(String classname)
	{
		this.classname = classname;
	}

	public String getClasscode()
	{
		return classcode;
	}

	public void setClasscode(String classcode)
	{
		this.classcode = classcode;
	}

	@Override
	public String toString()
	{
		return "DrugClass{" + "dcid=" + dcid + ", parentcode='" + parentcode + '\'' + ", parentname='" + parentname + '\'' + ", classname='" + classname + '\'' + ", classcode='" + classcode + '\'' + '}';
	}
}
