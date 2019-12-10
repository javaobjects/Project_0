package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestFactory
{
    private TestFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.ITest getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITest)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("98CEE1FA") ,com.kingdee.eas.custom.basedata.ITest.class);
    }
    
    public static com.kingdee.eas.custom.basedata.ITest getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITest)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("98CEE1FA") ,com.kingdee.eas.custom.basedata.ITest.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.ITest getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITest)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("98CEE1FA"));
    }
    public static com.kingdee.eas.custom.basedata.ITest getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITest)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("98CEE1FA"));
    }
}