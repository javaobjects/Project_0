package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewMaterialFactory
{
    private NewMaterialFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.INewMaterial getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterial)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B522D07F") ,com.kingdee.eas.custom.basedata.INewMaterial.class);
    }
    
    public static com.kingdee.eas.custom.basedata.INewMaterial getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterial)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B522D07F") ,com.kingdee.eas.custom.basedata.INewMaterial.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.INewMaterial getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterial)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B522D07F"));
    }
    public static com.kingdee.eas.custom.basedata.INewMaterial getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterial)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B522D07F"));
    }
}