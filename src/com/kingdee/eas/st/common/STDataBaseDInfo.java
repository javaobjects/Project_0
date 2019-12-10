package com.kingdee.eas.st.common;

import java.io.Serializable;

public class STDataBaseDInfo extends AbstractSTDataBaseDInfo implements
		Serializable {
	public STDataBaseDInfo() {
		super();
	}

	protected STDataBaseDInfo(String pkField) {
		super(pkField);
	}
}