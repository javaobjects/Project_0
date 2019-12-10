package com.kingdee.eas.st.common;

import java.io.Serializable;

public abstract class STBillBaseEntryInfo extends AbstractSTBillBaseEntryInfo
		implements Serializable {
	public STBillBaseEntryInfo() {
		super();
	}

	protected STBillBaseEntryInfo(String pkField) {
		super(pkField);
	}
}