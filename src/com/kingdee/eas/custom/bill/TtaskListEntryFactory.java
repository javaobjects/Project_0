package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TtaskListEntryFactory
{
    private TtaskListEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITtaskListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FBC6D457") ,com.kingdee.eas.custom.bill.ITtaskListEntry.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITtaskListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FBC6D457") ,com.kingdee.eas.custom.bill.ITtaskListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITtaskListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FBC6D457"));
    }
    public static com.kingdee.eas.custom.bill.ITtaskListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITtaskListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FBC6D457"));
    }
}