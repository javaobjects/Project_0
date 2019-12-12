package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class KsfcsBillFactory
{
    private KsfcsBillFactory()
    {
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B0FCEB9") ,com.kingdee.eas.custom.bill.IKsfcsBill.class);
    }
    
    public static com.kingdee.eas.custom.bill.IKsfcsBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B0FCEB9") ,com.kingdee.eas.custom.bill.IKsfcsBill.class, objectCtx);
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B0FCEB9"));
    }
    public static com.kingdee.eas.custom.bill.IKsfcsBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.bill.IKsfcsBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B0FCEB9"));
    }
}