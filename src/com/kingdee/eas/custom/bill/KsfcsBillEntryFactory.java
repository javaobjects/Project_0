package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class KsfcsBillEntryFactory
{
    private KsfcsBillEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C519539") ,com.kingdee.eas.custom.bill.IKsfcsBillEntry.class);
    }
    
    public static com.kingdee.eas.custom.bill.IKsfcsBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C519539") ,com.kingdee.eas.custom.bill.IKsfcsBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C519539"));
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C519539"));
    }
}