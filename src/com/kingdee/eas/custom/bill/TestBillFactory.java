package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TestBillFactory
{
    private TestBillFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.ITestBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8722E695") ,com.kingdee.eas.custom.bill.ITestBill.class);
    }
    
    public static com.kingdee.eas.custom.bill.ITestBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8722E695") ,com.kingdee.eas.custom.bill.ITestBill.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.ITestBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8722E695"));
    }
    public static com.kingdee.eas.custom.bill.ITestBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.ITestBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8722E695"));
    }
}