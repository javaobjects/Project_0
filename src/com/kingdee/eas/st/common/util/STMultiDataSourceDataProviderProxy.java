package com.kingdee.eas.st.common.util;

import java.util.HashMap;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.jdbc.rowset.IRowSet;

public final class STMultiDataSourceDataProviderProxy implements
		BOSQueryDelegate {

	/**
	 * <String,BOSQueryDelegate>
	 */
	private HashMap hm = new HashMap();

	/**
	 * 委托数据源
	 */
	public IRowSet execute(BOSQueryDataSource ds) {
		String dsId = ds.getID();
		if (hm.keySet().size() == 0) {
			return null;
		} else {
			BOSQueryDelegate bosQueryDelegate = (BOSQueryDelegate) hm.get(dsId);
			if (bosQueryDelegate == null) {
				return null;
			}
			return bosQueryDelegate.execute(ds);
		}
	}

	/**
	 * @param id
	 * @param ds
	 */
	public void put(String id, BOSQueryDelegate ds) {
		hm.put(id, ds);
	}

	/**
	 * @param id
	 * @return
	 */
	public Object get(String id) {
		return hm.get(id);
	}
}
