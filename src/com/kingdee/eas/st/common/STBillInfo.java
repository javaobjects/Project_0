package com.kingdee.eas.st.common;

import java.io.Serializable;

public abstract class STBillInfo extends AbstractSTBillInfo implements
		Serializable {
	public STBillInfo() {
		super();
	}

	protected STBillInfo(String pkField) {
		super(pkField);
	}
}