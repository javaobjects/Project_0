package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TlSlipFactory
{
    private TlSlipFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITlSlip getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlip)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("371419B4") ,com.kingdee.eas.custom.bill.ITlSlip.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITlSlip getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlip)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("371419B4") ,com.kingdee.eas.custom.bill.ITlSlip.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITlSlip getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlip)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("371419B4"));
    }
    public static com.kingdee.eas.custom.bill.ITlSlip getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlip)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("371419B4"));
    }
}