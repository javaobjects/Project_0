package com.kingdee.eas.st.common;

import java.io.Serializable;

public class STTreeBaseDataInfo extends AbstractSTTreeBaseDataInfo implements
		Serializable {
	public STTreeBaseDataInfo() {
		super();
	}

	protected STTreeBaseDataInfo(String pkField) {
		super(pkField);
	}
}