package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STTreeBaseDataDTreeFactory {
	private STTreeBaseDataDTreeFactory() {
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataDTree getRemoteInstance()
			throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataDTree) BOSObjectFactory
				.createRemoteBOSObject(new BOSObjectType("2A6C09A0"),
						com.kingdee.eas.st.common.ISTTreeBaseDataDTree.class);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataDTree getRemoteInstanceWithObjectContext(
			Context objectCtx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataDTree) BOSObjectFactory
				.createRemoteBOSObjectWithObjectContext(new BOSObjectType(
						"2A6C09A0"),
						com.kingdee.eas.st.common.ISTTreeBaseDataDTree.class,
						objectCtx);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataDTree getLocalInstance(
			Context ctx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataDTree) BOSObjectFactory
				.createBOSObject(ctx, new BOSObjectType("2A6C09A0"));
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataDTree getLocalInstance(
			String sessionID) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataDTree) BOSObjectFactory
				.createBOSObject(sessionID, new BOSObjectType("2A6C09A0"));
	}
}