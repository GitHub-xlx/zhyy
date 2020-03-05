package com.zhyy.entity;/**
 * className 药库出入库实体类
 */

/**
 *@author Administrator
 *Date 2020/3/4
 */
public class Inboundoutboundschedule
{
	private int iosid;
	private String drugcode;
	private String time;
	private String number;
	private String outbound;
	private String lotnumber;
	private String auditor;
	private String asker;
	private String pharmacycode;
	private String asktime;
	private String reviewtime;
	private String receivetime;
	private String operatingtime;
	private String treasury;
	private String applicant;

	public Inboundoutboundschedule(int iosid, String drugcode, String time, String number, String outbound, String lotnumber, String auditor, String asker, String pharmacycode, String asktime, String reviewtime, String receivetime, String operatingtime, String treasury, String applicant)
	{
		this.iosid = iosid;
		this.drugcode = drugcode;
		this.time = time;
		this.number = number;
		this.outbound = outbound;
		this.lotnumber = lotnumber;
		this.auditor = auditor;
		this.asker = asker;
		this.pharmacycode = pharmacycode;
		this.asktime = asktime;
		this.reviewtime = reviewtime;
		this.receivetime = receivetime;
		this.operatingtime = operatingtime;
		this.treasury = treasury;
		this.applicant = applicant;
	}

	public Inboundoutboundschedule()
	{

	}

	public int getIosid()
	{
		return iosid;
	}

	public void setIosid(int iosid)
	{
		this.iosid = iosid;
	}

	public String getDrugcode()
	{
		return drugcode;
	}

	public void setDrugcode(String drugcode)
	{
		this.drugcode = drugcode;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getOutbound()
	{
		return outbound;
	}

	public void setOutbound(String outbound)
	{
		this.outbound = outbound;
	}

	public String getLotnumber()
	{
		return lotnumber;
	}

	public void setLotnumber(String lotnumber)
	{
		this.lotnumber = lotnumber;
	}

	public String getAuditor()
	{
		return auditor;
	}

	public void setAuditor(String auditor)
	{
		this.auditor = auditor;
	}

	public String getAsker()
	{
		return asker;
	}

	public void setAsker(String asker)
	{
		this.asker = asker;
	}

	public String getPharmacycode()
	{
		return pharmacycode;
	}

	public void setPharmacycode(String pharmacycode)
	{
		this.pharmacycode = pharmacycode;
	}

	public String getAsktime()
	{
		return asktime;
	}

	public void setAsktime(String asktime)
	{
		this.asktime = asktime;
	}

	public String getReviewtime()
	{
		return reviewtime;
	}

	public void setReviewtime(String reviewtime)
	{
		this.reviewtime = reviewtime;
	}

	public String getReceivetime()
	{
		return receivetime;
	}

	public void setReceivetime(String receivetime)
	{
		this.receivetime = receivetime;
	}

	public String getOperatingtime()
	{
		return operatingtime;
	}

	public void setOperatingtime(String operatingtime)
	{
		this.operatingtime = operatingtime;
	}

	public String getTreasury()
	{
		return treasury;
	}

	public void setTreasury(String treasury)
	{
		this.treasury = treasury;
	}

	public String getApplicant()
	{
		return applicant;
	}

	public void setApplicant(String applicant)
	{
		this.applicant = applicant;
	}

	@Override
	public String toString()
	{
		return "Inboundoutboundschedule{" + "iosid=" + iosid + ", drugcode='" + drugcode + '\'' + ", time='" + time + '\'' + ", number='" + number + '\'' + ", outbound='" + outbound + '\'' + ", lotnumber='" + lotnumber + '\'' + ", auditor='" + auditor + '\'' + ", asker='" + asker + '\'' + ", pharmacycode='" + pharmacycode + '\'' + ", asktime='" + asktime + '\'' + ", reviewtime='" + reviewtime + '\'' + ", receivetime='" + receivetime + '\'' + ", operatingtime='" + operatingtime + '\'' + ", treasury='" + treasury + '\'' + ", applicant='" + applicant + '\'' + '}';
	}
}
