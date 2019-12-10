package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STBillCommonFacadeFactory
{
    private STBillCommonFacadeFactory()
    {
    }
    public static com.kingdee.eas.st.common.ISTBillCommonFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTBillCommonFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9FFEE155") ,com.kingdee.eas.st.common.ISTBillCommonFacade.class);
    }
    
    public static com.kingdee.eas.st.common.ISTBillCommonFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTBillCommonFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9FFEE155") ,com.kingdee.eas.st.common.ISTBillCommonFacade.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.ISTBillCommonFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTBillCommonFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9FFEE155"));
    }
    public static com.kingdee.eas.st.common.ISTBillCommonFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.ISTBillCommonFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9FFEE155"));
    }
}