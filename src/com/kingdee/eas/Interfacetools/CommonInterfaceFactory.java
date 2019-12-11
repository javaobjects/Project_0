package com.kingdee.eas.Interfacetools;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CommonInterfaceFactory
{
    private CommonInterfaceFactory()
    {
    }
    public static com.kingdee.eas.Interfacetools.ICommonInterface getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.Interfacetools.ICommonInterface)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F5B901A") ,com.kingdee.eas.Interfacetools.ICommonInterface.class);
    }
    
    public static com.kingdee.eas.Interfacetools.ICommonInterface getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.Interfacetools.ICommonInterface)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F5B901A") ,com.kingdee.eas.Interfacetools.ICommonInterface.class, objectCtx);
    }
    public static com.kingdee.eas.Interfacetools.ICommonInterface getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.Interfacetools.ICommonInterface)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F5B901A"));
    }
    public static com.kingdee.eas.Interfacetools.ICommonInterface getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.Interfacetools.ICommonInterface)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F5B901A"));
    }
}