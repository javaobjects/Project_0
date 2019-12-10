package com.kingdee.eas.st.common.rpt;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class STCommonReportFacadeFactory
{
    private STCommonReportFacadeFactory()
    {
    }
    public static com.kingdee.eas.st.common.rpt.ISTCommonReportFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.st.common.rpt.ISTCommonReportFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4F2EAFA") ,com.kingdee.eas.st.common.rpt.ISTCommonReportFacade.class);
    }
    
    public static com.kingdee.eas.st.common.rpt.ISTCommonReportFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.st.common.rpt.ISTCommonReportFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4F2EAFA") ,com.kingdee.eas.st.common.rpt.ISTCommonReportFacade.class, objectCtx);
    }
    public static com.kingdee.eas.st.common.rpt.ISTCommonReportFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.st.common.rpt.ISTCommonReportFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4F2EAFA"));
    }
    public static com.kingdee.eas.st.common.rpt.ISTCommonReportFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.st.common.rpt.ISTCommonReportFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4F2EAFA"));
    }
}