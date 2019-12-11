package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChitCostFactory
{
    private ChitCostFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.IChitCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9A07B9A5") ,com.kingdee.eas.custom.basedata.IChitCost.class);
    }
    
    public static com.kingdee.eas.custom.basedata.IChitCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9A07B9A5") ,com.kingdee.eas.custom.basedata.IChitCost.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.IChitCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9A07B9A5"));
    }
    public static com.kingdee.eas.custom.basedata.IChitCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9A07B9A5"));
    }
}