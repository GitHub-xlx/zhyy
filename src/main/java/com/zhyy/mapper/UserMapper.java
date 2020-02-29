package com.zhyy.mapper;


import com.zhyy.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 测试用户查询接口
 * @author Administrator
 */
@Mapper
@Component("userMapper")
public interface UserMapper
{
	/**
	 * 查询用户信息
	 * @return list
	 */

    @Insert("insert into userinfo (account,password,username,phone,sex,age,department,position,rolecode,pharmacycode,state) values (#{account},#{password},#{username},#{phone},#{sex},#{age},#{role},#{title},#{rolecode},#{pharmacycode},#{state})")
	public boolean regStaff(String account,String password,String username,String phone,String sex,String age,String role,String title,String rolecode,String pharmacycode,String state);
	@Select("select * from userinfo limit #{pageInt},#{limitInt}")
	public List<User> queryUserList(int pageInt, int limitInt);
	@Select("select count(*) from userinfo")
	public int countUserList();
	@Update("update userinfo password = 123456 where account = #{account}")
	public boolean resetPassword(String account);
	@Update("update userinfo set state ='已启用' where account = #{account}")
	public boolean enableUser(String account);
	@Update("update userinfo set state ='已禁用' where account = #{account}")
	public boolean disableUser(String account);
	@Update("update druginformation set price = #{price} where drugcode = #{drugcode}")
	public boolean adjustmentPrice(double price,String drugcode);
    @Update("update drugstoredruginventory set drugstatus ='停用' where drugcode = #{drugcode}")
	public boolean drugDiscontinuation(String drugcode);
	@Update("update druginventorytable set drugstatus ='停用' where drugcode = #{drugcode}")
	public boolean drug2Discontinuation(String drugcode);


	//查询药库库存数量不足的药品
	@Select("select * from drugstoredruginventory where drugminimums > druginventory")
	List<Drugstoredruginventory> checkInventoryCount();
	//根据药品编码查询药品名称
	@Select("select commoname from druginformation where drugcode = #{drugcode}")
	public String findDrugNameByDrugCode(String drugcode);
    //药房 药品过期检测
	@Select("select A.drugcode,B.commoname,A.productiondate,B.shelflife from druginventorytable A,druginformation B where A.drugcode=B.drugcode")
	public List<Druginventorytable> expiredCheck();
	//药房 药品滞销检测
	@Select("select A.drugcode,C.commoname,B.receivetime from druginventorytable A,pharmacydrugschedule B,druginformation C where A.drugcode=B.drugcode and B.drugcode=C.drugcode")
	public List<Druginventorytable> unsalableCheck();




	@Select("select D.menu parentcode,C.menu,C.url from menu D join (select A.* from menu A,(select menucode from rmrelation where rolecode = #{rolecode} and state = '0')B where A.menucode = B.menucode)C on D.menucode = C.parentcode and D.state = '0';")
	public List<Menu> queryMenuList(String rolecode);
	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);



}
