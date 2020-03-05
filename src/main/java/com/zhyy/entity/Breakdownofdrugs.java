package com.zhyy.entity;


import com.google.gson.Gson;
/**
 * @Description  药品报损的实体类
 * @author xlx
 * @Date 下午 22:22 2020/3/3 0003
 * @Param
 * @return
 **/
public class Breakdownofdrugs
{

  private long bodid;
  private String drugcode;
  private String commoname;
  private String damagedtype;
  private String lossreporter;
  private String lossreport;
  private String losstime;
  private String losscount;
  private String lossamount;
  private String lotnumber;
  private String drugstate;
  private String auditor;
  private String reviewtime;
  private String provepath;
  private String pharmacycode;

  public String getCommoname()
  {
    return commoname;
  }

  public void setCommoname(String commoname)
  {
    this.commoname = commoname;
  }

  public long getBodid() {
    return bodid;
  }

  public void setBodid(long bodid) {
    this.bodid = bodid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public String getDamagedtype() {
    return damagedtype;
  }

  public void setDamagedtype(String damagedtype) {
    this.damagedtype = damagedtype;
  }


  public String getLossreporter() {
    return lossreporter;
  }

  public void setLossreporter(String lossreporter) {
    this.lossreporter = lossreporter;
  }


  public String getLossreport() {
    return lossreport;
  }

  public void setLossreport(String lossreport) {
    this.lossreport = lossreport;
  }


  public String getLosstime() {
    return losstime;
  }

  public void setLosstime(String losstime) {
    this.losstime = losstime;
  }


  public String getLosscount() {
    return losscount;
  }

  public void setLosscount(String losscount) {
    this.losscount = losscount;
  }


  public String getLossamount() {
    return lossamount;
  }

  public void setLossamount(String lossamount) {
    this.lossamount = lossamount;
  }


  public String getLotnumber() {
    return lotnumber;
  }

  public void setLotnumber(String lotnumber) {
    this.lotnumber = lotnumber;
  }


  public String getDrugstate() {
    return drugstate;
  }

  public void setDrugstate(String drugstate) {
    this.drugstate = drugstate;
  }


  public String getAuditor() {
    return auditor;
  }

  public void setAuditor(String auditor) {
    this.auditor = auditor;
  }


  public String getReviewtime() {
    return reviewtime;
  }

  public void setReviewtime(String reviewtime) {
    this.reviewtime = reviewtime;
  }


  public String getProvepath() {
    return provepath;
  }

  public void setProvepath(String provepath) {
    this.provepath = provepath;
  }


  public String getPharmacycode() {
    return pharmacycode;
  }

  public void setPharmacycode(String pharmacycode) {
    this.pharmacycode = pharmacycode;
  }

  @Override
  public String toString()
  {
    return  new Gson().toJson(this);
  }
}
