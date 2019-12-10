package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestTreeFactory
{
    private TestTreeFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.ITestTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITestTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F2613CB8") ,com.kingdee.eas.custom.basedata.ITestTree.class);
    }
    
    public static com.kingdee.eas.custom.basedata.ITestTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITestTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F2613CB8") ,com.kingdee.eas.custom.basedata.ITestTree.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.ITestTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITestTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F2613CB8"));
    }
    public static com.kingdee.eas.custom.basedata.ITestTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.ITestTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F2613CB8"));
    }
}