package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PuReqEntryFactory
{
    private PuReqEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.IPuReqEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReqEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8DDA0B75") ,com.kingdee.eas.custom.bill.IPuReqEntry.class);
    }
    
    public static com.kingdee.eas.custom.bill.IPuReqEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReqEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8DDA0B75") ,com.kingdee.eas.custom.bill.IPuReqEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.IPuReqEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReqEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8DDA0B75"));
    }
    public static com.kingdee.eas.custom.bill.IPuReqEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReqEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8DDA0B75"));
    }
}