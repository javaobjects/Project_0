package com.kingdee.eas.st.common.listenerutils.listener.F7Relation.impl;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7MTRlDtChgListener;

public class PurOrgUnit2Supplier extends F7MTRlDtChgListener {
	public FilterItemCollection getFilterItemCollection(Object v) {
		FilterItemCollection fic = new FilterItemCollection();
		if (null != v && v instanceof PurchaseOrgUnitInfo) {
			PurchaseOrgUnitInfo info = (PurchaseOrgUnitInfo) v;
			String oql = "Where id in (select s.fid from T_BD_SupplierPurchaseInfo"
					+ " sp left join T_BD_Supplier s on s.fid = sp.FSupplierID"
					+ " where sp.FPurchaseOrgID = '"
					+ info.getId().toString()
					+ "' and sp.FUsingStatus=0)";
			EntityViewInfo evi;
			try {
				evi = new EntityViewInfo(oql);

				FilterItemInfo fi = null;
				if (evi.getFilter() != null
						&& evi.getFilter().getFilterItems() != null
						&& evi.getFilter().getFilterItems().get(0) != null) {
					fi = evi.getFilter().getFilterItems().get(0);
					fic.add(fi);
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		} else {
			fic.add(new FilterItemInfo("id", "", CompareType.EQUALS));
		}
		return fic;
	}
}
