package com.kingdee.eas.st.common.template;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STBillTemplateEntryFactory
{
    private STBillTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E424204A") ,com.kingdee.eas.st.common.template.ISTBillTemplateEntry.class);
    }
    
    public static com.kingdee.eas.st.common.template.ISTBillTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E424204A") ,com.kingdee.eas.st.common.template.ISTBillTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E424204A"));
    }
    public static com.kingdee.eas.st.common.template.ISTBillTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.template.ISTBillTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E424204A"));
    }
}