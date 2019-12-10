package com.kingdee.eas.st.common.template;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STBillTemplateFactory
{
    private STBillTemplateFactory()
    {
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0F04DD08") ,com.kingdee.eas.st.common.template.ISTBillTemplate.class);
    }
    
    public static com.kingdee.eas.st.common.template.ISTBillTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0F04DD08") ,com.kingdee.eas.st.common.template.ISTBillTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0F04DD08"));
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0F04DD08"));
    }
}