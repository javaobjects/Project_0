package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STParameterFacadeFactory
{
    private STParameterFacadeFactory()
    {
    }
    public static com.kingdee.eas.st.common.ISTParameterFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTParameterFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("30158F3A") ,com.kingdee.eas.st.common.ISTParameterFacade.class);
    }
    
    public static com.kingdee.eas.st.common.ISTParameterFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTParameterFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("30158F3A") ,com.kingdee.eas.st.common.ISTParameterFacade.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.ISTParameterFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTParameterFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("30158F3A"));
    }
    public static com.kingdee.eas.st.common.ISTParameterFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTParameterFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("30158F3A"));
    }
}