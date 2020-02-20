package com.zhyy.sqlifclass;

public class DrugSaleIfClass
{
	/**
	 *
	 * @param pharmacycode 药房编号
	 * @param drugcode  药品编号
	 * @param commoname 药品常用名称
	 * @param specialmedicine   是否是特殊药品
	 * @param idcard    购买人员身份证
	 * @param consumername  购买人员姓名
	 * @param salesperson 营业员
	 * @param start 销售开始时间
	 * @param end   销售结束时间
	 * @param nowpage   当前页数
	 * @param size  当前页数的大小
	 * @return
	 */
	public String selectsalelist(String pharmacycode,String drugcode,String commoname,String specialmedicine,String idcard,String consumername,String salesperson,String start,String end, int nowpage, int size) {
		StringBuffer sql = new StringBuffer("SELECT  * FROM  (SELECT * FROM drugsalesregistrationform where pharmacycode = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (drugcode != null && !("".equals(drugcode))) {
			sql.append(" AND B.drugcode LIKE '%" + drugcode + "%'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		if (specialmedicine != null && !("-1".equals(specialmedicine))) {
			sql.append(" AND B.specialmedicine = '" + specialmedicine + "'");
		}
		if (idcard != null && !("".equals(idcard))) {
			sql.append(" AND B.idcard LIKE '%" + idcard + "%'");
		}
		if (consumername != null && !("".equals(consumername))) {
			sql.append(" AND B.consumername LIKE '%" + consumername + "%'");
		}
		if (salesperson != null && !("".equals(salesperson))) {
			sql.append(" AND B.salesperson LIKE '%" + salesperson + "%'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND B.salestime >= '" + start + "'");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND B.salestime <= '" + end + "'");
		}
		//分页语句
		sql.append(" LIMIT " + (nowpage-1)*size + "," + size);
		return sql.toString();
	}

	/**
	 *
	 * @param pharmacycode 药房编号
	 * @param drugcode  药品编号
	 * @param commoname 药品常用名称
	 * @param specialmedicine   是否是特殊药品
	 * @param idcard    购买人员身份证
	 * @param consumername  购买人员姓名
	 * @param salesperson 营业员
	 * @param start 销售开始时间
	 * @param end   销售结束时间
	 * @return
	 */
	public String countsalelist(String pharmacycode,String drugcode,String commoname,String specialmedicine,String idcard,String consumername,String salesperson,String start,String end) {
		StringBuffer sql = new StringBuffer("SELECT  COUNT(*) FROM  (SELECT * FROM drugsalesregistrationform where pharmacycode = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (drugcode != null && !("".equals(drugcode))) {
			sql.append(" AND B.drugcode LIKE '%" + drugcode + "%'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		if (specialmedicine != null && !("-1".equals(specialmedicine))) {
			sql.append(" AND B.specialmedicine = '" + specialmedicine + "'");
		}
		if (idcard != null && !("".equals(idcard))) {
			sql.append(" AND B.idcard LIKE '%" + idcard + "%'");
		}
		if (consumername != null && !("".equals(consumername))) {
			sql.append(" AND B.consumername LIKE '%" + consumername + "%'");
		}
		if (salesperson != null && !("".equals(salesperson))) {
			sql.append(" AND B.salesperson LIKE '%" + salesperson + "%'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND B.salestime >= '" + start + "'");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND B.salestime <= '" + end + "'");
		}
		return sql.toString();
	}
}

