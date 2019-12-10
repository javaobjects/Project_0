package com.kingdee.eas.st.common;

import java.io.Serializable;

public class STTreeBaseDataTreeInfo extends AbstractSTTreeBaseDataTreeInfo
		implements Serializable {
	public STTreeBaseDataTreeInfo() {
		super();
	}

	protected STTreeBaseDataTreeInfo(String pkField) {
		super(pkField);
	}
}