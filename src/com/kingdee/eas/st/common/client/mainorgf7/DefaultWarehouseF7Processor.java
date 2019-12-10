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

public class DefaultWarehouseF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	private KDTable table = null;
	private String warehouseColumnName = null;

	public DefaultWarehouseF7Processor(KDBizPromptBox f7) {
		super();
		this.f7 = f7;
	}

	public DefaultWarehouseF7Processor(KDBizPromptBox f7, KDTable table,
			String warehouseColumnName) {
		super();
		this.f7 = f7;
		this.table = table;
		this.warehouseColumnName = warehouseColumnName;
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType, boolean isClearValue) {
		// TODO 自动生成方法存根
		if (STUtils.isNotNull(newOrg)) {
			f7
					.setQueryInfo("com.kingdee.eas.basedata.scm.im.inv.app.F7WarehouseQuery");

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
						&& table.getColumn(warehouseColumnName) != null) {
					for (int i = 0, count = table.getRowCount(); i < count; i++) {
						table.getRow(i).getCell(warehouseColumnName).setValue(
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
