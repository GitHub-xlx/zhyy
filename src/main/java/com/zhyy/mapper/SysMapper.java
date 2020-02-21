package com.zhyy.mapper;

import com.zhyy.entity.Menu;
import com.zhyy.entity.TableMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Mapper
@Component("sysMapper")
public interface SysMapper
{
	@Select("select * from menu where ${where} ${limits}")
	public List<Menu>queryAllMenu(String where,String limits);

	@Select("select count(*) from ${tableName} where ${where}")
	public int count(String tableName,String where);


}
