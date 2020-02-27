package com.zhyy.mapper;

import com.zhyy.entity.Department;
import com.zhyy.entity.Menu;
import com.zhyy.entity.Role;
import com.zhyy.entity.TableMsg;
import org.apache.ibatis.annotations.Insert;
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
	/**
	 * 带条件分页查询菜单
	 * @param where
	 * @param limits
	 * @return
	 */
	@Select("select * from menu where ${where} ${limits}")
	public List<Menu>queryAllMenu(String where,String limits);


	/**
	 * 单表统计数据量
	 * @param tableName
	 * @param where
	 * @return
	 */
	@Select("select count(*) from ${tableName} where ${where}")
	public int count(String tableName,String where);

	/**
	 * 根据角色查询权限菜单
	 * @param rolecode
	 * @return
	 */
	@Select("select D.*,E.menu parentname from menu E,(select B.menu,C.* from menu B,(select A.menucode,A.parentcode,A.state from rmrelation A where rolecode = #{rolecode})C where B.menucode = C.menucode)D where E.menucode = D.parentcode and D.menucode in(select menucode from menu) or D.parentcode = 0 group by D.menucode")
	public List<Menu>queryPermissionMenu(String rolecode);


	/**
	 * 保存权限配置
	 * @param menucode
	 * @param rolecode
	 * @return
	 */
	@Update("update rmrelation set state = '1' where rolecode = #{rolecode} and menucode = #{menucode}")
	public int savePermission(String menucode,String rolecode);


	/**
	 * 根据角色打开所有菜单权限
	 * @param rolecode
	 * @return
	 */
	@Update("update rmrelation set state = '0' where rolecode = #{rolecode}")
	public int openPermissionByRoleCode(String rolecode);


	/**
	 * 查询角色表
	 * @return
	 */
	@Select("select * from role")
	public List<Role> queryRole();


	/**
	 * 带条件限制查询角色
	 * @param where
	 * @param limits
	 * @return
	 */
	@Select("select A.rolecode,A.rolename,B.departmentname departmentcode from role A,department B where ${where} ${limits}")
	public List<Role>queryAllRole(String where,String limits);


	/**
	 * 根据菜单编号查重
	 * @param menucode
	 * @return
	 */
	@Select("select count(*) from menu where menucode = #{menucode}")
	public int checkMenu(String menucode);


	/**
	 * 添加菜单
	 * @param menucode
	 * @param parentcode
	 * @param menu
	 * @param url
	 * @return
	 */
	@Insert("insert into menu (menucode,parentcode,menu,url)values(#{menucode},#{parentcode},#{menu},#{url}")
	public int addMenu(String menucode,String parentcode,String menu,String url);


	/**
	 * 启禁用菜单
	 * @param menucode
	 * @param state
	 * @return
	 */
	@Update("update menu set state = #{state} where menucode = #{menucode}")
	public int ableMenu(String menucode,String state);


	/**
	 * 根据父级菜单启禁用菜单
	 * @param parentcode
	 * @param state
	 * @return
	 */
	@Update("update menu set state = #{state} where parentcode = #{parentcode}")
	public int ableMenuByParentCode(String parentcode,String state);


	/**
	 * 修改菜单信息
	 * @param menucode
	 * @param menuname
	 * @param url
	 * @return
	 */
	@Update("update menu set menu = #{menuname},url = #{url} where menucode = #{menucode}")
	public int editMenu(String menucode,String menuname,String url);


	/**
	 * 查询部门
	 * @return
	 */
	@Select("select * from department")
	public List<Department> queryDepartment();

	/**
	 * 根据角色编号查重
	 * @param rolecode
	 * @return
	 */
	@Select("select count(*) from role where rolecode = #{rolecode}")
	public int checkRole(String rolecode);


	/**
	 * 添加角色
	 * @param departmentcode
	 * @param rolecode
	 * @param rolename
	 * @return
	 */
	@Insert("insert into role (departmentcode,rolecode,rolename)values(#{departmentcode},#{rolecode},#{rolename}")
	public int addRole(String departmentcode,String rolecode,String rolename);


	/**
	 * 修改角色名称
	 * @param rolecode
	 * @param rolename
	 * @return
	 */
	@Update("update role set rolename = #{rolename} where rolecode = #{rolecode}")
	public int editRole(String rolecode,String rolename);


	/**
	 * 验证密码
	 * @param account
	 * @return
	 */
	@Select("select password from userinfo where account = #{account}")
	public String checkPassword(String account);


	/**
	 * 修改密码
	 * @param account
	 * @param password
	 * @return
	 */
	@Update("update userinfo set password = #{password} where account = #{account}")
	public int editPassword(String account,String password);

}
