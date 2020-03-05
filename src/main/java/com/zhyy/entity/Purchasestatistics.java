package com.zhyy.entity;/**
 * className 药品采购入库实体类
 */

/**
 *@author Administrator
 *Date 2020/3/1
 */
public class Purchasestatistics
{
	private Integer psid;
	private String drugcode;
	private String purchasenumber;
	private String totalpurchasemount;
	private String purchasetime;
	private String buyer;
	private String operator;
	private String state;
	//批号
	private String lotnumber;
	//有效期
	private String date;
	private String price;
	public Purchasestatistics(Integer psid, String drugcode, String purchasenumber, String totalpurchasemount, String purchasetime, String buyer, String operator, String state, String lotnumber, String date)
	private String productname;
	private String productiondate;


	public Purchasestatistics(Integer psid, String drugcode, String purchasenumber, String totalpurchasemount, String purchasetime, String buyer, String operator, int state, String lotnumber, String date, String productname, String productiondate)
	{
		this.psid = psid;
		this.drugcode = drugcode;
		this.purchasenumber = purchasenumber;
		this.totalpurchasemount = totalpurchasemount;
		this.purchasetime = purchasetime;
		this.buyer = buyer;
		this.operator = operator;
		this.state = state;
		this.lotnumber = lotnumber;
		this.date = date;
	}

	public Purchasestatistics()
	{

	}

	public Integer getPsid()
	{
		return psid;
	}

	public void setPsid(Integer psid)
	{
		this.psid = psid;
	}

	public String getDrugcode()
	{
		return drugcode;
	}

	public void setDrugcode(String drugcode)
	{
		this.drugcode = drugcode;
	}

	public String getPurchasenumber()
	{
		return purchasenumber;
	}

	public void setPurchasenumber(String purchasenumber)
	{
		this.purchasenumber = purchasenumber;
	}

	public String getTotalpurchasemount()
	{
		return totalpurchasemount;
	}

	public void setTotalpurchasemount(String totalpurchasemount)
	{
		this.totalpurchasemount = totalpurchasemount;
	}

	public String getPurchasetime()
	{
		return purchasetime;
	}

	public void setPurchasetime(String purchasetime)
	{
		this.purchasetime = purchasetime;
	}

	public String getBuyer()
	{
		return buyer;
	}

	public void setBuyer(String buyer)
	{
		this.buyer = buyer;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getLotnumber()
	{
		return lotnumber;
	}

	public void setLotnumber(String lotnumber)
	{
		this.lotnumber = lotnumber;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getProductname()
	{
		return productname;
	}

	public void setProductname(String productname)
	{
		this.productname = productname;
	}

	@Override
	public String toString()
	{
		return "Purchasestatistics{" + "psid=" + psid + ", drugcode='" + drugcode + '\'' + ", purchasenumber='" + purchasenumber + '\'' + ", totalpurchasemount='" + totalpurchasemount + '\'' + ", purchasetime='" + purchasetime + '\'' + ", buyer='" + buyer + '\'' + ", operator='" + operator + '\'' + ", state='" + state + '\'' + ", lotnumber='" + lotnumber + '\'' + ", date='" + date + '\'' + '}';
	}
}
