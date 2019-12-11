package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChitCostPropertyCostFactory
{
    private ChitCostPropertyCostFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.IChitCostPropertyCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCostPropertyCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("07E9CD87") ,com.kingdee.eas.custom.basedata.IChitCostPropertyCost.class);
    }
    
    public static com.kingdee.eas.custom.basedata.IChitCostPropertyCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCostPropertyCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("07E9CD87") ,com.kingdee.eas.custom.basedata.IChitCostPropertyCost.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.IChitCostPropertyCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCostPropertyCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("07E9CD87"));
    }
    public static com.kingdee.eas.custom.basedata.IChitCostPropertyCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.IChitCostPropertyCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("07E9CD87"));
    }
}