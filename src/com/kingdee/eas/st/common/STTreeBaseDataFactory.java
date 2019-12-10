package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STTreeBaseDataFactory {
	private STTreeBaseDataFactory() {
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseData getRemoteInstance()
			throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseData) BOSObjectFactory
				.createRemoteBOSObject(new BOSObjectType("9CB482C0"),
						com.kingdee.eas.st.common.ISTTreeBaseData.class);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseData getRemoteInstanceWithObjectContext(
			Context objectCtx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseData) BOSObjectFactory
				.createRemoteBOSObjectWithObjectContext(new BOSObjectType(
						"9CB482C0"),
						com.kingdee.eas.st.common.ISTTreeBaseData.class,
						objectCtx);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseData getLocalInstance(
			Context ctx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseData) BOSObjectFactory
				.createBOSObject(ctx, new BOSObjectType("9CB482C0"));
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseData getLocalInstance(
			String sessionID) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseData) BOSObjectFactory
				.createBOSObject(sessionID, new BOSObjectType("9CB482C0"));
	}
}