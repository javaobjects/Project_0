package com.kingdee.eas.st.common.template;

import java.io.Serializable;

public class STBaseDataTemplateInfo extends AbstractSTBaseDataTemplateInfo
		implements Serializable {
	public STBaseDataTemplateInfo() {
		super();
	}

	protected STBaseDataTemplateInfo(String pkField) {
		super(pkField);
	}
}