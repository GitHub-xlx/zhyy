package com.zhyy.entity;


public class Drugcompatibilitycontraindications {

  private long dccid;
  private String drugcodeA;
  private String drugcodeB;
  private String contraindications;


  public long getDccid() {
    return dccid;
  }

  public void setDccid(long dccid) {
    this.dccid = dccid;
  }


  public String getDrugcodeA() {
    return drugcodeA;
  }

  public void setDrugcodeA(String drugcodeA) {
    this.drugcodeA = drugcodeA;
  }


  public String getDrugcodeB() {
    return drugcodeB;
  }

  public void setDrugcodeB(String drugcodeB) {
    this.drugcodeB = drugcodeB;
  }


  public String getContraindications() {
    return contraindications;
  }

  public void setContraindications(String contraindications) {
    this.contraindications = contraindications;
  }

}
