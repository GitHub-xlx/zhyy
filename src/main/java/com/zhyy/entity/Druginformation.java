package com.zhyy.entity;


import com.google.gson.Gson;

public class Druginformation {

  private long diid;
  private String drugcode;
  private String barcode;
  private String classcode;
  private String productname;
  private String commoname;
  private String specification;
  private String dosageform;
  private String drugdepotunit;
  private String pharmacyunit;
  private String prescribeunit;
  private String reductionformula;
  private String dosage;
  private String pincode;
  private String supplier;
  private String antibiotic;
  private String specialmedicine;
  private String approvalnumber;
  private String healthinsurance;
  private String price;
  private String wholesaleprice;
  private String additionrate;
  private String precautions;
  private String shelflife;


  public long getDiid() {
    return diid;
  }

  public void setDiid(long diid) {
    this.diid = diid;
  }


  public String getDrugcode() {
    return drugcode;
  }

  public void setDrugcode(String drugcode) {
    this.drugcode = drugcode;
  }


  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }


  public String getClasscode() {
    return classcode;
  }

  public void setClasscode(String classcode) {
    this.classcode = classcode;
  }


  public String getProductname() {
    return productname;
  }

  public void setProductname(String productname) {
    this.productname = productname;
  }


  public String getCommoname() {
    return commoname;
  }

  public void setCommoname(String commoname) {
    this.commoname = commoname;
  }


  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }


  public String getDosageform() {
    return dosageform;
  }

  public void setDosageform(String dosageform) {
    this.dosageform = dosageform;
  }


  public String getDrugdepotunit() {
    return drugdepotunit;
  }

  public void setDrugdepotunit(String drugdepotunit) {
    this.drugdepotunit = drugdepotunit;
  }


  public String getPharmacyunit() {
    return pharmacyunit;
  }

  public void setPharmacyunit(String pharmacyunit) {
    this.pharmacyunit = pharmacyunit;
  }


  public String getPrescribeunit() {
    return prescribeunit;
  }

  public void setPrescribeunit(String prescribeunit) {
    this.prescribeunit = prescribeunit;
  }


  public String getReductionformula() {
    return reductionformula;
  }

  public void setReductionformula(String reductionformula) {
    this.reductionformula = reductionformula;
  }


  public String getDosage() {
    return dosage;
  }

  public void setDosage(String dosage) {
    this.dosage = dosage;
  }


  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }


  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }


  public String getAntibiotic() {
    return antibiotic;
  }

  public void setAntibiotic(String antibiotic) {
    this.antibiotic = antibiotic;
  }


  public String getSpecialmedicine() {
    return specialmedicine;
  }

  public void setSpecialmedicine(String specialmedicine) {
    this.specialmedicine = specialmedicine;
  }


  public String getApprovalnumber() {
    return approvalnumber;
  }

  public void setApprovalnumber(String approvalnumber) {
    this.approvalnumber = approvalnumber;
  }


  public String getHealthinsurance() {
    return healthinsurance;
  }

  public void setHealthinsurance(String healthinsurance) {
    this.healthinsurance = healthinsurance;
  }


  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }


  public String getWholesaleprice() {
    return wholesaleprice;
  }

  public void setWholesaleprice(String wholesaleprice) {
    this.wholesaleprice = wholesaleprice;
  }


  public String getAdditionrate() {
    return additionrate;
  }

  public void setAdditionrate(String additionrate) {
    this.additionrate = additionrate;
  }


  public String getPrecautions() {
    return precautions;
  }

  public void setPrecautions(String precautions) {
    this.precautions = precautions;
  }


  public String getShelflife() {
    return shelflife;
  }

  public void setShelflife(String shelflife) {
    this.shelflife = shelflife;
  }

  @Override
  public String toString()
  {
    return  new Gson().toJson(this);
  }
}
