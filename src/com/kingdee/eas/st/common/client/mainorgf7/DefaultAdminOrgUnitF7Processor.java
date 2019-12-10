package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.st.common.client.OrgUnitClientUtils;
import com.kingdee.eas.st.common.util.STUtils;

public class DefaultAdminOrgUnitF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;

	public DefaultAdminOrgUnitF7Processor(KDBizPromptBox f7) {
		super();
		this.f7 = f7;
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType, boolean isClearValue) {
		// TODO 自动生成方法存根
		if (STUtils.isNotNull(newOrg) && STUtils.isNotNull(f7)) {
			if (isClearValue) {
				f7.setValue(null);
			}
			OrgUnitClientUtils.setBizOrgF7ByDelegation(f7, OrgType.Admin,
					mainOrgType, newOrg, true);
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}
}
