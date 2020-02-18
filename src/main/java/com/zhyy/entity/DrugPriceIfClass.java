package com.zhyy.entity;

import java.util.HashMap;

public class DrugPriceIfClass
{
	/**
	 * 变更表多条件模糊查询
	 *
	 * @param pharmacycode 药房编号
	 * @param currentprice 当前价格
	 * @param previousprice 上次价格
	 * @param start 调价日期
	 * @param end 调价日期
	 * @param nowpage  分页从第几页开始
	 * @param size  分页，每页多少条
	 * @return
	 */
	public String selectByChangeIF(String pharmacycode,String currentprice,String previousprice,String start,String end, int nowpage, int size) {
		StringBuffer sql = new StringBuffer("select * from drugprice where 1=1 ");
		if (pharmacycode != null && !("".equals(pharmacycode))) {
			sql.append(" AND pharmacycode =" + pharmacycode +"");
		}
		if (currentprice != null && !("".equals(currentprice))) {
			sql.append(" AND currentprice = '" + currentprice + "'");
		}

		if (previousprice != null && !("".equals(previousprice))) {
			sql.append(" AND previousprice='" + previousprice + "'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND priceadjustmentdate >=" + start + "");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND priceadjustmentdate <=" + end + "");
		}
		//分页语句
		sql.append(" LIMIT " + (nowpage-1)*size + "," + size);
		return sql.toString();
	}

	/**
	 * 变更表配合模糊查询返回数量
	 *
	 * @param pharmacycode 药房编号
	 * @param currentprice 当前价格
	 * @param previousprice 上次价格
	 * @param start 调价日期
	 * @param end 调价日期
	 * @return
	 */
	public String selectByChangeIFCount(String pharmacycode,String currentprice,String previousprice,String start,String end) {
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) from  drugprice where 1=1 ");
		if (pharmacycode != null && !("".equals(pharmacycode))) {
			sql.append(" AND pharmacycode =" + pharmacycode +"");
		}
		if (currentprice != null && !("".equals(currentprice))) {
			sql.append(" AND currentprice = '" + currentprice + "'");
		}

		if (previousprice != null && !("".equals(previousprice))) {
			sql.append(" AND previousprice='" + previousprice + "'");
		}
		if (start != null && !("".equals(start))) {
			sql.append(" AND priceadjustmentdate >=" + start + "");
		}
		if (end != null && !("".equals(end))) {
			sql.append(" AND priceadjustmentdate <=" + end + "");
		}
		return sql.toString();
	}
}

