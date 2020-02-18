package com.zhyy.sqlifclass;

import java.util.HashMap;

public class DrugPriceIfClass
{
	/**
	 * 变更表多条件模糊查询
	 *
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @param nowpage  分页从第几页开始
	 * @param size  分页，每页多少条
	 * @return
	 */
	public String selectByChangeIF(String pharmacycode,String drugcode,String commoname,String start,String end, int nowpage, int size) {
		StringBuffer sql = new StringBuffer("SELECT  * FROM  (SELECT * FROM drugprice where pharmacycode = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (drugcode != null && !("".equals(drugcode))) {
			sql.append(" AND B.drugcode LIKE '%" + drugcode + "%'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND B.priceadjustmentdate >=" + start + "");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND B.priceadjustmentdate <=" + end + "");
		}
		//分页语句
		sql.append(" LIMIT " + (nowpage-1)*size + "," + size);
		return sql.toString();
	}

	/**
	 * 变更表配合模糊查询返回数量
	 *
	 * @param pharmacycode 药房编号
	 * @param drugcode 药品编号
	 * @param commoname 常用名称
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	public String selectByChangeIFCount(String pharmacycode,String drugcode,String commoname,String start,String end) {
		StringBuffer sql = new StringBuffer("SELECT  COUNT(*) FROM  (SELECT * FROM drugprice where pharmacycode = "+pharmacycode+")B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (drugcode != null && !("".equals(drugcode))) {
			sql.append(" AND B.drugcode LIKE '%" + drugcode + "%'");
		}
		if (commoname != null && !("".equals(commoname))) {
			sql.append(" AND A.commoname LIKE '%" + commoname + "%'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND B.priceadjustmentdate >=" + start + "");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND B.priceadjustmentdate <=" + end + "");
		}
		return sql.toString();
	}
}

