package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.st.common.util.STUtils;

public class DefaultMaterialF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	private CoreBillEditUI ui;
	private String displayFormat = "$number$";

	public DefaultMaterialF7Processor(KDBizPromptBox f7, CoreBillEditUI ui) {
		super();
		this.f7 = f7;
		this.ui = ui;
	}

	public DefaultMaterialF7Processor(KDBizPromptBox f7, CoreBillEditUI ui,
			String displayFormat) {
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
			CSMF7Utils.setBizMaterialTreeF7(f7, ui, mainOrgType, newOrg,
					displayFormat);
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}
}
