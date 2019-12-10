package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.org.OrgUnitInfo;

/**
 * 在单据中，需要额外处理的动作，在这里完成
 * 
 * @author zhiwei_wang
 * 
 */
public interface IUserChangeProcessor {
	// 更新前检查，如果返回false，则后续所有操作都将撤销，正常情况下返回true
	public boolean beforeMainOrgUnitChanged(KDBizPromptBox mainOrgF7,
			OrgUnitInfo oldOrg, OrgUnitInfo newOrg);

	public void afterMainOrgUnitChanged(KDBizPromptBox mainOrgF7,
			OrgUnitInfo oldOrg, OrgUnitInfo newOrg);
}
