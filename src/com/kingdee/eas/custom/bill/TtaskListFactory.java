package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TtaskListFactory
{
    private TtaskListFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITtaskList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BBBB14DB") ,com.kingdee.eas.custom.bill.ITtaskList.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITtaskList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BBBB14DB") ,com.kingdee.eas.custom.bill.ITtaskList.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITtaskList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BBBB14DB"));
    }
    public static com.kingdee.eas.custom.bill.ITtaskList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BBBB14DB"));
    }
}