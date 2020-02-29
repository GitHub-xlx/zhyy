package com.zhyy.entity;


public class Druginventorytable
{

	private long ditid;
	private String drugcode;
	private double druginventorynumber;
	private double drugminimums;
	private String drugunit;
	private String lotnumber;
	private String specialmedicine;
	private String productiondate;
	private String drugstatus;
	private String pharmacynumber;
	private String commoname;//药品名称
    private String shelflife;//保质期
    private String receivetime;//入库时间

	public long getDitid()
	{
		return ditid;
	}

	public void setDitid(long ditid)
	{
		this.ditid = ditid;
	}


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

	public double getDruginventorynumber()
	{
		return druginventorynumber;
	}

	public void setDruginventorynumber(double druginventorynumber)
	{
		this.druginventorynumber = druginventorynumber;
	}


	public double getDrugminimums()
	{
		return drugminimums;
	}

	public void setDrugminimums(double drugminimums)
	{
		this.drugminimums = drugminimums;
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


	public String getSpecialmedicine()
	{
		return specialmedicine;
	}

	public void setSpecialmedicine(String specialmedicine)
	{
		this.specialmedicine = specialmedicine;
	}


	public String getProductiondate()
	{
		return productiondate;
	}

	public void setProductiondate(String productiondate)
	{
		this.productiondate = productiondate;
	}


	public String getDrugstatus()
	{
		return drugstatus;
	}

	public void setDrugstatus(String drugstatus)
	{
		this.drugstatus = drugstatus;
	}


	public String getPharmacynumber()
	{
		return pharmacynumber;
	}

	public void setPharmacynumber(String pharmacynumber)
	{
		this.pharmacynumber = pharmacynumber;
	}
	public String getShelflife()
	{
		return shelflife;
	}

	public void setShelflife(String shelflife)
	{
		this.shelflife = shelflife;
	}

	public String getReceivetime()
	{
		return receivetime;
	}

	public void setReceivetime(String receivetime)
	{
		this.receivetime = receivetime;
	}
}
