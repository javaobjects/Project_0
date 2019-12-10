package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestBillEntryE2Factory
{
    private TestBillEntryE2Factory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntryE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntryE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5758CC2A") ,com.kingdee.eas.custom.bill.ITestBillEntryE2.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITestBillEntryE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntryE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5758CC2A") ,com.kingdee.eas.custom.bill.ITestBillEntryE2.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntryE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntryE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5758CC2A"));
    }
    public static com.kingdee.eas.custom.bill.ITestBillEntryE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBillEntryE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5758CC2A"));
    }
}