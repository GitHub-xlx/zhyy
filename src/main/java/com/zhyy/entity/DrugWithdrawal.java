package com.zhyy.entity;


import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @Description  退库实体类
 * @author xlx
 * @Date 上午 8:48 2020/3/3 0003
 * @Param
 * @return
 **/
public class DrugWithdrawal implements Serializable
{

  private long ditid;
  private String drugcode;
  private long druginventorynumber;
  private long druginventory;
  private long drugminimums;
  private String drugunit;
  private String lotnumber;
  private String specialmedicine;
  private String productiondate;
  private String drugstatus;
  private String pharmacynumber;
  private int number;

  public int getNumber()
  {
    return number;
  }

  public void setNumber(int number)
  {
    this.number = number;
  }

  public long getDruginventory()
  {
    return druginventory;
  }

  public void setDruginventory(long druginventory)
  {
    this.druginventory = druginventory;
  }

  public long getDitid() {
    return ditid;
  }

  public void setDitid(long ditid) {
    this.ditid = ditid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public long getDruginventorynumber() {
    return druginventorynumber;
  }

  public void setDruginventorynumber(long druginventorynumber) {
    this.druginventorynumber = druginventorynumber;
  }


  public long getDrugminimums() {
    return drugminimums;
  }

  public void setDrugminimums(long drugminimums) {
    this.drugminimums = drugminimums;
  }


  public String getDrugunit() {
    return drugunit;
  }

  public void setDrugunit(String drugunit) {
    this.drugunit = drugunit;
  }


  public String getLotnumber() {
    return lotnumber;
  }

  public void setLotnumber(String lotnumber) {
    this.lotnumber = lotnumber;
  }


  public String getSpecialmedicine() {
    return specialmedicine;
  }

  public void setSpecialmedicine(String specialmedicine) {
    this.specialmedicine = specialmedicine;
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


  public String getPharmacynumber() {
    return pharmacynumber;
  }

  public void setPharmacynumber(String pharmacynumber) {
    this.pharmacynumber = pharmacynumber;
  }
  @Override
  public String toString()
  {
    return  new Gson().toJson(this);
  }
}
