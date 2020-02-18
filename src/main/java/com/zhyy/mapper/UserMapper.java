package com.zhyy.mapper;


import com.zhyy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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


	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);
}
