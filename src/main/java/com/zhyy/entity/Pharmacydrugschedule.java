package com.zhyy.entity;

import com.google.gson.Gson;

/**
 * @Description  药房出入库明细表实体类
 * @author xlx
 * @Date 下午 15:39 2020/2/28 0028
 * @Param
 * @return
 **/
public class Pharmacydrugschedule {

  private long pdeesid;
  private String drugcode;
  private String time;
  private String number;
  private String outbound;
  private String lotnumber;
  private String specialmedicine;
  private String outboundtype;
  private String auditor;
  private String asktime;
  private String reviewtime;
  private String receivetime;
  private String operatingtime;
  private String pharmacynumber;
  private String asker;

  public String getAsker()
  {
    return asker;
  }

  public void setAsker(String asker)
  {
    this.asker = asker;
  }

  public long getPdeesid() {
    return pdeesid;
  }

  public void setPdeesid(long pdeesid) {
    this.pdeesid = pdeesid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }


  public String getOutbound() {
    return outbound;
  }

  public void setOutbound(String outbound) {
    this.outbound = outbound;
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


  public String getOutboundtype() {
    return outboundtype;
  }

  public void setOutboundtype(String outboundtype) {
    this.outboundtype = outboundtype;
  }


  public String getAuditor() {
    return auditor;
  }

  public void setAuditor(String auditor) {
    this.auditor = auditor;
  }


  public String getAsktime() {
    return asktime;
  }

  public void setAsktime(String asktime) {
    this.asktime = asktime;
  }


  public String getReviewtime() {
    return reviewtime;
  }

  public void setReviewtime(String reviewtime) {
    this.reviewtime = reviewtime;
  }


  public String getReceivetime() {
    return receivetime;
  }

  public void setReceivetime(String receivetime) {
    this.receivetime = receivetime;
  }


  public String getOperatingtime() {
    return operatingtime;
  }

  public void setOperatingtime(String operatingtime) {
    this.operatingtime = operatingtime;
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
