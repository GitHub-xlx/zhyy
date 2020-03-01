package com.zhyy.sqlifclass;

public class DrugDistributionIfClass
{
	/**
	 *
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @param nowpage   当前页数
	 * @param size  当前页数的大小
	 * @return
	 */
	public String selectdruginventorylist(String pharmacycode,String classcode,String commoname, int nowpage, int size) {
		StringBuffer sql = new StringBuffer("SELECT  * FROM  (SELECT * FROM druginventorytable where pharmacynumber = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (classcode != null && !("".equals(classcode))) {
			sql.append(" AND A.classcode = '" + classcode + "'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		//分页语句
		sql.append(" LIMIT " + (nowpage-1)*size + "," + size);
		return sql.toString();
	}

	/**
	 *
	 * @param pharmacycode 药房编号
	 * @param classcode  药品种类
	 * @param commoname 药品常用名称
	 * @return
	 */
	public String countdruginventorylist(String pharmacycode,String classcode,String commoname) {
		StringBuffer sql = new StringBuffer("SELECT  COUNT(*) FROM  (SELECT * FROM druginventorytable where pharmacynumber = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (classcode != null && !("".equals(classcode))) {
			sql.append(" AND A.classcode = '" + classcode + "'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		return sql.toString();
	}
}

