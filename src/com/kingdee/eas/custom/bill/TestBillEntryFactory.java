package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestBillEntryFactory
{
    private TestBillEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1AFF06DD") ,com.kingdee.eas.custom.bill.ITestBillEntry.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITestBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1AFF06DD") ,com.kingdee.eas.custom.bill.ITestBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1AFF06DD"));
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1AFF06DD"));
    }
}