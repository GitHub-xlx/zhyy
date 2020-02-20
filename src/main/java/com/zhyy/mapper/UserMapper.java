package com.zhyy.mapper;


import com.zhyy.entity.Menu;
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
	@Select("select D.menu parentcode,C.menu,C.url,C.state from menu D join (select A.* from menu A,(select menucode from rmrelation where rolecode = #{rolecode} and state = '0')B where A.menucode = B.menucode )C on D.menucode = C.parentcode and C.state = '0';")
	public List<Menu> queryMenuList(String rolecode);
	@Select("select * from userinfo where account = #{account}")
	public User queryUserByAccount(String account);
}
