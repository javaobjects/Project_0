package com.kingdee.eas.st.common.template;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STBaseDataTemplateFactory
{
    private STBaseDataTemplateFactory()
    {
    }
    public static com.kingdee.eas.st.common.template.ISTBaseDataTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBaseDataTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("892850FE") ,com.kingdee.eas.st.common.template.ISTBaseDataTemplate.class);
    }
    
    public static com.kingdee.eas.st.common.template.ISTBaseDataTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBaseDataTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("892850FE") ,com.kingdee.eas.st.common.template.ISTBaseDataTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.template.ISTBaseDataTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBaseDataTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("892850FE"));
    }
    public static com.kingdee.eas.st.common.template.ISTBaseDataTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBaseDataTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("892850FE"));
    }
}