package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.st.common.client.STBillBaseFilterUI;
import com.kingdee.eas.st.common.util.STUtils;

public class DefaultCustomerCUF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	private STBillBaseFilterUI ui;
	private String displayFormat = "$number$";

	public DefaultCustomerCUF7Processor(KDBizPromptBox f7, STBillBaseFilterUI ui) {
		super();
		this.f7 = f7;
		this.ui = ui;
	}

	public DefaultCustomerCUF7Processor(KDBizPromptBox f7,
			STBillBaseFilterUI ui, String displayFormat) {
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
			CSMF7Utils.setBizCustomerCUF7(f7, ui, newOrg);
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}
}
