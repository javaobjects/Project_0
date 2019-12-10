package com.kingdee.eas.st.common.listenerutils.listener.F7Relation.impl;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7MTRlDtChgListener;

public class SaleOrgUnit2Customer extends F7MTRlDtChgListener {

	public FilterItemCollection getFilterItemCollection(Object v) {
		FilterItemCollection fic = new FilterItemCollection();
		if (null != v && v instanceof SaleOrgUnitInfo) {
			SaleOrgUnitInfo info = (SaleOrgUnitInfo) v;
			String oql = "Where id in (select c.fid from T_BD_CustomerSaleInfo"
					+ " cs left join T_BD_Customer c on c.fid = cs.FCustomerID"
					+ " where cs.FSaleOrgID = '" + info.getId().toString()
					+ "' and cs.FUsingStatus=0)";
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
