package com.zhyy.entity;


public class User
{

  private long uid;
  private String account;
  private String password;
  private String username;
  private String phone;
  private String sex;
  private String age;
  private String department;
  private String position;
  private String rolecode;
  private String pharmacycode;


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }


  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }


  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }


  public String getRolecode() {
    return rolecode;
  }

  public void setRolecode(String rolecode) {
    this.rolecode = rolecode;
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
    return "Userinfo{" + "uid=" + uid + ", account='" + account + '\'' + ", password='" + password + '\'' + ", username='" + username + '\'' + ", phone='" + phone + '\'' + ", sex='" + sex + '\'' + ", age='" + age + '\'' + ", department='" + department + '\'' + ", position='" + position + '\'' + ", rolecode='" + rolecode + '\'' + ", pharmacycode='" + pharmacycode + '\'' + '}';
  }
}
