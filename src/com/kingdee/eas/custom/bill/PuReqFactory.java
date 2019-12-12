package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PuReqFactory
{
    private PuReqFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.IPuReq getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReq)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6CED62FD") ,com.kingdee.eas.custom.bill.IPuReq.class);
    }
    
    public static com.kingdee.eas.custom.bill.IPuReq getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReq)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6CED62FD") ,com.kingdee.eas.custom.bill.IPuReq.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.IPuReq getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReq)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6CED62FD"));
    }
    public static com.kingdee.eas.custom.bill.IPuReq getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IPuReq)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6CED62FD"));
    }
}