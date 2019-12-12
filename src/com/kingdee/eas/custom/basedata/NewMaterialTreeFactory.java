package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class NewMaterialTreeFactory
{
    private NewMaterialTreeFactory()
    {
    }
    public static com.kingdee.eas.custom.basedata.INewMaterialTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterialTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CE6060BD") ,com.kingdee.eas.custom.basedata.INewMaterialTree.class);
    }
    
    public static com.kingdee.eas.custom.basedata.INewMaterialTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterialTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CE6060BD") ,com.kingdee.eas.custom.basedata.INewMaterialTree.class, objectCtx);
    }
    public static com.kingdee.eas.custom.basedata.INewMaterialTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterialTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CE6060BD"));
    }
    public static com.kingdee.eas.custom.basedata.INewMaterialTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.basedata.INewMaterialTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CE6060BD"));
    }
}