package com.zhyy.mapper;


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
    @Update("update userinfo set password=123456 where account = #{account}")
	public boolean resetPassword(String account);
    @Update("update userinfo set state ='已启用' where account = #{account}")
	public boolean enableUser(String account);
	@Update("update userinfo set state ='已禁用' where account = #{account}")
	public boolean disableUser(String account);
	@Update("update druginformation set price = #{price} where drugcode =#{drugcode}")
	public boolean adjustmentPrice(double price,String drugcode);
	@Update("update drugstoredruginventory set drugstatus ='停用' where drugcode =#{drugcode}")
	public boolean drugDiscontinuation(String drugcode);
	@Update("update druginventorytable set drugstatus ='停用' where drugcode =#{drugcode}")
	public boolean drug2Discontinuation(String drugcode);






	public List<Menu> queryMenuList(String rolecode);

	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);
}
