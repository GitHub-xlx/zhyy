package com.zhyy.mapper;

import com.zhyy.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("logMapper")
public interface LogMapper
{

	@Select("select * from log where ${where} ${limits}")
	List<Log> queryAll(String where,String limits);

	@Insert("insert into log (operationdate,account,username,ip,url,duration,method) values(#{operationdate},#{account},#{username},#{ip},#{url},#{duration},#{method})")
	int save (Log log);
}
