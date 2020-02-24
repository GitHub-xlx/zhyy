package com.zhyy.mapper;


import com.zhyy.entity.Druginformation;
import com.zhyy.entity.Drugstoredruginventory;
import com.zhyy.entity.Menu;
import com.zhyy.entity.User;
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
	List<Druginformation> findDrugNameByDrugCode(String drugcode);


	@Select("select D.menu parentcode,C.menu,C.url from menu D join (select A.* from menu A,(select menucode from rmrelation where rolecode = #{rolecode} and state = '0')B where A.menucode = B.menucode)C on D.menucode = C.parentcode;")
	public List<Menu> queryMenuList(String rolecode);
	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);



}
