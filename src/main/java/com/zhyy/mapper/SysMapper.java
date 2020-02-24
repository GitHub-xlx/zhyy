package com.zhyy.mapper;

import com.zhyy.entity.Menu;
import com.zhyy.entity.Role;
import com.zhyy.entity.TableMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

	@Select("select D.*,E.menu parentname from menu E,(select B.menu,C.* from menu B,(select A.menucode,A.parentcode,A.state from rmrelation A where rolecode = #{rolecode})C where B.menucode = C.menucode)D where E.menucode = D.parentcode and D.menucode in(select menucode from menu) or D.parentcode = 0 group by D.menucode\n")
	public List<Menu>queryPermissionMenu(String rolecode);

	@Update("update rmrelation set state = '1' where rolecode = #{rolecode} and menucode = #{menucode}")
	public int savePermission(String menucode,String rolecode);

	@Update("update rmrelation set state = '0' where rolecode = #{rolecode}")
	public int openPermissionByRoleCode(String rolecode);

	@Select("select * from role")
	public List<Role> queryRole();

	@Select("select A.rolecode,A.rolename,B.departmentname departmentcode from role A,department B where ${where} ${limits}")
	public List<Role>queryAllRole(String where,String limits);

}
