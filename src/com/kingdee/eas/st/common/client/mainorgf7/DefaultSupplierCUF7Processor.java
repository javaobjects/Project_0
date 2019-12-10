package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.st.common.util.STUtils;

public class DefaultSupplierCUF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	private CustomerQueryPanel ui;
	private String displayFormat = null;

	public DefaultSupplierCUF7Processor(KDBizPromptBox f7, CustomerQueryPanel ui) {
		super();
		this.f7 = f7;
		this.ui = ui;
	}

	public DefaultSupplierCUF7Processor(KDBizPromptBox f7,
			CustomerQueryPanel ui, String displayFormat) {
		super();
		this.f7 = f7;
		this.ui = ui;
		this.displayFormat = displayFormat;
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType, boolean isClearValue) {
		// TODO 自动生成方法存根
		if (STUtils.isNotNull(newOrg)) {
			if (isClearValue) {
				f7.setValue(null);
			}

			if (displayFormat != null) {
				CSMF7Utils.setBizSupplierCUF7(f7, displayFormat, ui, newOrg);
			} else {
				CSMF7Utils.setBizSupplierCUF7(f7, ui, newOrg);
			}
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}
}
