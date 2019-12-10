package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STTreeBaseDataDFactory {
	private STTreeBaseDataDFactory() {
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataD getRemoteInstance()
			throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataD) BOSObjectFactory
				.createRemoteBOSObject(new BOSObjectType("42D462E2"),
						com.kingdee.eas.st.common.ISTTreeBaseDataD.class);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataD getRemoteInstanceWithObjectContext(
			Context objectCtx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataD) BOSObjectFactory
				.createRemoteBOSObjectWithObjectContext(new BOSObjectType(
						"42D462E2"),
						com.kingdee.eas.st.common.ISTTreeBaseDataD.class,
						objectCtx);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataD getLocalInstance(
			Context ctx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataD) BOSObjectFactory
				.createBOSObject(ctx, new BOSObjectType("42D462E2"));
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataD getLocalInstance(
			String sessionID) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataD) BOSObjectFactory
				.createBOSObject(sessionID, new BOSObjectType("42D462E2"));
	}
}