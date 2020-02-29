package com.zhyy.entity;


public class Drugstoredruginventory {

  private long dsdiid;
  private String drugcode;
  private double druginventory;
  private double drugminimums;
  private String lotnumber;
  private String productiondate;
  private String drugstatus;
  private String commoname;

  public long getDsdiid() {
    return dsdiid;
  }

  public void setDsdiid(long dsdiid) {
    this.dsdiid = dsdiid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public double getDruginventory() {
    return druginventory;
  }

  public void setDruginventory(double druginventory) {
    this.druginventory = druginventory;
  }


  public double getDrugminimums() {
    return drugminimums;
  }

  public void setDrugminimums(double drugminimums) {
    this.drugminimums = drugminimums;
  }


  public String getLotnumber() {
    return lotnumber;
  }

  public void setLotnumber(String lotnumber) {
    this.lotnumber = lotnumber;
  }


  public String getProductiondate() {
    return productiondate;
  }

  public void setProductiondate(String productiondate) {
    this.productiondate = productiondate;
  }


  public String getDrugstatus() {
    return drugstatus;
  }

  public void setDrugstatus(String drugstatus) {
    this.drugstatus = drugstatus;
  }

  public String getCommoname()
  {
    return commoname;
  }

  public void setCommoname(String commoname)
  {
    this.commoname = commoname;
  }


  @Override
  public String toString()
  {
    return "Drugstoredruginventory{" + "dsdiid=" + dsdiid + ", drugcode='" + drugcode + '\'' + ", druginventory=" + druginventory + ", drugminimums=" + drugminimums + ", lotnumber='" + lotnumber + '\'' + ", productiondate='" + productiondate + '\'' + ", drugstatus='" + drugstatus + '\'' + '}';
  }
}
