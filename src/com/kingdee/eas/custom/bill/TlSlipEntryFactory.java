package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TlSlipEntryFactory
{
    private TlSlipEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITlSlipEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlipEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5BBEF1E") ,com.kingdee.eas.custom.bill.ITlSlipEntry.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITlSlipEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlipEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5BBEF1E") ,com.kingdee.eas.custom.bill.ITlSlipEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITlSlipEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlipEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5BBEF1E"));
    }
    public static com.kingdee.eas.custom.bill.ITlSlipEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITlSlipEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5BBEF1E"));
    }
}