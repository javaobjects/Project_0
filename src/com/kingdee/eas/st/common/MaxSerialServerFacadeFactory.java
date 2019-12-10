package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaxSerialServerFacadeFactory
{
    private MaxSerialServerFacadeFactory()
    {
    }
    public static com.kingdee.eas.st.common.IMaxSerialServerFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.IMaxSerialServerFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8533B86D") ,com.kingdee.eas.st.common.IMaxSerialServerFacade.class);
    }
    
    public static com.kingdee.eas.st.common.IMaxSerialServerFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.IMaxSerialServerFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8533B86D") ,com.kingdee.eas.st.common.IMaxSerialServerFacade.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.IMaxSerialServerFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.IMaxSerialServerFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8533B86D"));
    }
    public static com.kingdee.eas.st.common.IMaxSerialServerFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.IMaxSerialServerFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8533B86D"));
    }
}