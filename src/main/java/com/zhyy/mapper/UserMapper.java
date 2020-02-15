package com.zhyy.mapper;


import com.zhyy.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 测试用户查询接口
 * @author Administrator
 */
@Mapper
public interface UserMapper
{
	/**
	 * 查询用户信息
	 * @return list
	 */
	public List<User> queryUserList();
}
