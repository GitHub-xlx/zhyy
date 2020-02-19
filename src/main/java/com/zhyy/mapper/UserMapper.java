package com.zhyy.mapper;


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
	//查询用户总数
	@Select("select count(*) from userinfo")
	public int countUserList();

    //重置密码
	@Update("update userinfo set password =123456 where account = #{account}")
	public boolean resetPassword(String account);

    //启用
    @Update("update userinfo set state ='已启用' where account = #{account}")
    public boolean enableUser(String account);

	//禁用
	@Update("update userinfo set state ='已禁用' where account = #{account}")
	public boolean disableUser(String account);

	//调价
	@Update("update druginformation set price = #{price}")
    public boolean adjustmentPrice(int price);

	

	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);
}
