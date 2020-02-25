package com.zhyy.entity;


public class Drugstoredruginventory {

  private long dsdiid;
  private String drugcode;
  private double druginventory;
  private double drugminimums;
  private String lotnumber;
  private String productiondate;
  private String drugstatus;


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

}
