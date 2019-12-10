package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STTreeBaseDataTreeFactory {
	private STTreeBaseDataTreeFactory() {
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataTree getRemoteInstance()
			throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataTree) BOSObjectFactory
				.createRemoteBOSObject(new BOSObjectType("555D0A7E"),
						com.kingdee.eas.st.common.ISTTreeBaseDataTree.class);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataTree getRemoteInstanceWithObjectContext(
			Context objectCtx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataTree) BOSObjectFactory
				.createRemoteBOSObjectWithObjectContext(new BOSObjectType(
						"555D0A7E"),
						com.kingdee.eas.st.common.ISTTreeBaseDataTree.class,
						objectCtx);
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataTree getLocalInstance(
			Context ctx) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataTree) BOSObjectFactory
				.createBOSObject(ctx, new BOSObjectType("555D0A7E"));
	}

	public static com.kingdee.eas.st.common.ISTTreeBaseDataTree getLocalInstance(
			String sessionID) throws BOSException {
		return (com.kingdee.eas.st.common.ISTTreeBaseDataTree) BOSObjectFactory
				.createBOSObject(sessionID, new BOSObjectType("555D0A7E"));
	}
}