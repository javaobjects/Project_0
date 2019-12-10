package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.st.common.client.AsstActClientUtils;
import com.kingdee.eas.st.common.client.OrgUnitClientUtils;
import com.kingdee.eas.st.common.client.STBillBaseFilterUI;
import com.kingdee.eas.st.common.util.STUtils;

/**
 * 往来户
 * 
 * @author zhiwei_wang
 * 
 */
public class DefaultAsstActF7Processor implements IF7Processor {
	private KDBizPromptBox f7 = null;
	// 主业务组织F7所在的UI，通常为EditUI和FilterUI
	private CoreUIObject uiObject = null;
	// 决定往来户类型的F7所在的UI，通常和uiObject相同
	private IAsstActUI asstActUI = null;

	public DefaultAsstActF7Processor(CoreUIObject uiObject, KDBizPromptBox f7,
			IAsstActUI asstActUI) {
		super();
		this.f7 = f7;
		this.uiObject = uiObject;
		this.asstActUI = asstActUI;
		if (asstActUI != null) {
			asstActUI.addAsstActChangeListener(new AsstActChangeListener() {

				public void asstActChanged(String oldType, String newType) {
					// 在往来户类型发生变化后，只需调用一次主业务组织变化事件
					callMainOrgChange();
				}

			});
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType, boolean isClearValue) {
		// TODO 自动生成方法存根
		if (STUtils.isNotNull(newOrg) && STUtils.isNotNull(f7)) {
			if (isClearValue) {
				f7.setValue(null);
			}
			try {
				AsstActClientUtils.setAsstActF7(uiObject, f7, asstActUI
						.getAsstActType());
			} catch (Exception e) {

			}

			// OrgUnitClientUtils.setBizOrgF7ByDelegation(f7, OrgType.Admin,
			// mainOrgType, newOrg, true);
		}
	}

	public void mainOrgChanged(OrgUnitInfo oldOrg, OrgUnitInfo newOrg,
			OrgType mainOrgType) {
		mainOrgChanged(oldOrg, newOrg, mainOrgType, true);
	}

	private void callMainOrgChange() {
		if (uiObject instanceof STBillBaseFilterUI) {
			STBillBaseFilterUI ui = (STBillBaseFilterUI) uiObject;
			mainOrgChanged(null, (OrgUnitInfo) ui.getMainBizOrgF7().getValue(),
					ui.getMainBizOrgType(), true);
		} else if (uiObject instanceof CoreUI) {
			CoreUI ui = (CoreUI) uiObject;
			mainOrgChanged(null, ui.getMainOrgInfo(), ui.getMainType(), true);
		}

	}
}
