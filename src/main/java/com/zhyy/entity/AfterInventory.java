package com.zhyy.entity;

public class AfterInventory
{

	private String  drugcode;
	private String  commoname;
	private String	specification;
	private String	drugunit;
	private String	lotnumber;
	private int	druginventorynumber;//盘点前数量
	private int	relativequantity;//盘点相对数量
	private int	finishedquantity;//盘点后数量
	private double	wholesaleprice;//单价
	private double	relativeamount;//相对金额


	public String getDrugcode()
	{
		return drugcode;
	}

	public void setDrugcode(String drugcode)
	{
		this.drugcode = drugcode;
	}

	public String getCommoname()
	{
		return commoname;
	}

	public void setCommoname(String commoname)
	{
		this.commoname = commoname;
	}

	public String getSpecification()
	{
		return specification;
	}

	public void setSpecification(String specification)
	{
		this.specification = specification;
	}

	public String getDrugunit()
	{
		return drugunit;
	}

	public void setDrugunit(String drugunit)
	{
		this.drugunit = drugunit;
	}

	public String getLotnumber()
	{
		return lotnumber;
	}

	public void setLotnumber(String lotnumber)
	{
		this.lotnumber = lotnumber;
	}

	public int getDruginventorynumber()
	{
		return druginventorynumber;
	}

	public void setDruginventorynumber(int druginventorynumber)
	{
		this.druginventorynumber = druginventorynumber;
	}

	public int getRelativequantity()
	{
		return relativequantity;
	}

	public void setRelativequantity(int relativequantity)
	{
		this.relativequantity = relativequantity;
	}

	public int getFinishedquantity()
	{
		return finishedquantity;
	}

	public void setFinishedquantity(int finishedquantity)
	{
		this.finishedquantity = finishedquantity;
	}

	public double getWholesaleprice()
	{
		return wholesaleprice;
	}

	public void setWholesaleprice(double wholesaleprice)
	{
		this.wholesaleprice = wholesaleprice;
	}

	public double getRelativeamount()
	{
		return relativeamount;
	}

	public void setRelativeamount(double relativeamount)
	{
		this.relativeamount = relativeamount;
	}




}
