package com.zhyy.sqlifclass;

public class CompatibilityIfClass
{
	public String selectcompatibilityList(String drugcode, int nowpage, int size) {
		StringBuffer sql = new StringBuffer("SELECT  * FROM drugcompatibilitycontraindications where 1=1 ");
		if (drugcode != null   && !("".equals(drugcode))) {
			sql.append(" AND drugcodeA like '%" + drugcode + "%'");
		}

		//分页语句
		sql.append(" LIMIT " + (nowpage-1)*size + "," + size);
		return sql.toString();
	}

	public String selectcountcompatibilityList(String drugcode) {
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM drugcompatibilitycontraindications where 1=1 ");
		if (drugcode != null   && !("".equals(drugcode))) {
			sql.append(" AND drugcodeA like '%" + drugcode + "%'");
		}
		return sql.toString();
	}
}

