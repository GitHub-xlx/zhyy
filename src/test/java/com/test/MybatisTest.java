package com.test;

import com.zhyy.MySpringbootApplication;
import com.zhyy.entity.User;
import com.zhyy.mapper.UserMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Mybatis测试类
 *@author Administrator
 *Date 2020/2/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringbootApplication.class)
public class MybatisTest
{
	@Autowired
	private UserMapper userMapper;

	public void test()
	{
		List<User> list = userMapper.queryUserList();
		System.out.println(list);
	}
}
