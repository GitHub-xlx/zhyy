package com.zhyy.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @Description  药房库存查询实体类
 * @author xlx
 * @Date 下午 17:41 2020/2/28 0028
 * @Param
 * @return
 **/
public class Inventorycheck implements Serializable
{

  private long ditid;
  private String drugcode;
  private String commoname;
  private long druginventorynumber;
  private long druginventory;//药库库存数量
  private long drugminimums;
  private String drugunit;
  private String lotnumber;
  private String specialmedicine;
  private String productiondate;
  private String drugstatus;
  private String pharmacynumber;
  private String price;
  private String losscount;//报损数量
  private String lossamount="0";//报损总金额

  public String getLosscount()
  {
    return losscount;
  }

  public void setLosscount(String losscount)
  {
    this.losscount = losscount;
  }

  public String getLossamount()
  {
    return lossamount;
  }

  public void setLossamount(String lossamount)
  {
    this.lossamount = lossamount;
  }

  public String getPrice()
  {
    return price;
  }

  public void setPrice(String price)
  {
    this.price = price;
  }

  public long getDruginventory()
  {
    return druginventory;
  }

  public void setDruginventory(long druginventory)
  {
    this.druginventory = druginventory;
  }

  public String getCommoname()
  {
    return commoname;
  }

  public void setCommoname(String commoname)
  {
    this.commoname = commoname;
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
