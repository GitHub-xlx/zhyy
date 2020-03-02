package com.zhyy.entity;


public class Drugprice {

  private long dpid;
  private String drugcode;
  private Double currentprice;
  private Double previousprice;
  private Double governmentpricing;
  private String priceadjustmentdate;
  private String operator;
  private String pharmacycode;


  public long getDpid() {
    return dpid;
  }

  public void setDpid(long dpid) {
    this.dpid = dpid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public Double getCurrentprice()
  {
    return currentprice;
  }

  public void setCurrentprice(Double currentprice)
  {
    this.currentprice = currentprice;
  }

  public Double getPreviousprice()
  {
    return previousprice;
  }

  public void setPreviousprice(Double previousprice)
  {
    this.previousprice = previousprice;
  }

  public Double getGovernmentpricing()
  {
    return governmentpricing;
  }

  public void setGovernmentpricing(Double governmentpricing)
  {
    this.governmentpricing = governmentpricing;
  }

  public String getPriceadjustmentdate() {
    return priceadjustmentdate;
  }

  public void setPriceadjustmentdate(String priceadjustmentdate) {
    this.priceadjustmentdate = priceadjustmentdate;
  }


  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
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
    return "Drugprice{" + "dpid=" + dpid + ", drugcode='" + drugcode + '\'' + ", currentprice='" + currentprice + '\'' + ", previousprice='" + previousprice + '\'' + ", governmentpricing='" + governmentpricing + '\'' + ", priceadjustmentdate='" + priceadjustmentdate + '\'' + ", operator='" + operator + '\'' + ", pharmacycode='" + pharmacycode + '\'' + '}';
  }
}
