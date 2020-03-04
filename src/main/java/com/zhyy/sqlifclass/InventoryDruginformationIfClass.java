package com.zhyy.sqlifclass;

public class InventoryDruginformationIfClass
{
	public String selectinventorylist(String pharmacycode, String drugcode, String inventoryresults, String commoname ,String start,String end) {
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT * FROM inventory where pharmacycode = '"+pharmacycode+"' )B left join druginformation A on A.drugcode=B.drugcode where 1=1 ");
		if (drugcode != null && drugcode.length()>0) {
			sql.append(" AND B.drugcode like '%"+drugcode+"%'");
		}
		if (commoname != null && commoname.length()>0) {
			sql.append(" AND A.commoname like '%"+commoname+"%'");
		}
		if (inventoryresults != null && inventoryresults.length()>0) {
			sql.append(" AND B.inventoryresults = '"+inventoryresults+"'");
		}
		if (start != null && start.length()>0) {
			sql.append(" AND B.inventorytime >= '"+start+"'");
		}
		if (end != null && end.length()>0) {
			sql.append(" AND B.inventorytime <= '"+end+"'");
		}

		return sql.toString();
	}
}
