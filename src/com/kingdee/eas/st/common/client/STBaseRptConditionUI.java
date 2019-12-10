/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgType;

/**
 * output class name
 */
public abstract class STBaseRptConditionUI extends AbstractSTBaseRptConditionUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STBaseRptConditionUI.class);

	/**
	 * output class constructor
	 */
	public STBaseRptConditionUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	abstract public KDBizPromptBox getMainBizOrgF7();

	abstract public OrgType getMainBizOrgType();

}