package com.kingdee.eas.st.common.client.mainorgf7;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.org.OrgUnitInfo;

/**
 * �ڵ����У���Ҫ���⴦��Ķ��������������
 * 
 * @author zhiwei_wang
 * 
 */
public interface IUserChangeProcessor {
	// ����ǰ��飬�������false����������в���������������������·���true
	public boolean beforeMainOrgUnitChanged(KDBizPromptBox mainOrgF7,
			OrgUnitInfo oldOrg, OrgUnitInfo newOrg);

	public void afterMainOrgUnitChanged(KDBizPromptBox mainOrgF7,
			OrgUnitInfo oldOrg, OrgUnitInfo newOrg);
}
