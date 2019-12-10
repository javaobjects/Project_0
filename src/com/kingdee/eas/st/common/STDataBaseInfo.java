package com.kingdee.eas.st.common;

import com.kingdee.bos.util.BOSObjectType;

public class STDataBaseInfo extends AbstractSTDataBaseInfo {
	public STDataBaseInfo() {
		super();
	}

	public STDataBaseInfo(String pkField) {
		super(pkField);
	}

	public BOSObjectType getBOSType() {
		return new BOSObjectType("E099AFE4");
	}
}
