/*
 * @DefaultLocationF7Processor.java
 * 
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.st.common.util.STUtils;

/**
 * 描述: <描述> <br>
 * 
 * @author: angus_yang date: 2008-5-21
 * 
 *          修改人：<修改人> <br>
 *          修改时间：<修改时间> <br>
 *          修改描述：<修改描述> <br>
 * @version EAS 6.0
 */
public final class DefaultLocationF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	private KDTable table = null;
	private String locationColumnName = null;

	public DefaultLocationF7Processor(KDBizPromptBox f7) {
		super();
		this.f7 = f7;
	}

	public DefaultLocationF7Processor(KDBizPromptBox f7, KDTable table,
			String locationColName) {
		super();
		this.f7 = f7;
		this.table = table;
		this.locationColumnName = locationColName;
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType, boolean isClearValue) {
		if (STUtils.isNotNull(newOrg)) {
			f7
					.setQueryInfo("com.kingdee.eas.basedata.scm.im.inv.app.F7LocationQuery");

			if (mainOrgType.equals(OrgType.Storage)) {
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("storageOrgUnit.id", newOrg.getId()
								.toString(), CompareType.EQUALS));
				viewInfo.setFilter(filter);
				f7.setEntityViewInfo(viewInfo);
			}
			if (isClearValue) {
				f7.setValue(null);
				if (table != null
						&& table.getColumn(locationColumnName) != null) {
					for (int i = 0, count = table.getRowCount(); i < count; i++) {
						table.getRow(i).getCell(locationColumnName).setValue(
								null);
					}
				}
			}
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}
}
