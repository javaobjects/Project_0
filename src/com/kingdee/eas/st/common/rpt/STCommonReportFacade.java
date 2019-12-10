package com.kingdee.eas.st.common.rpt;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.st.common.rpt.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

public class STCommonReportFacade extends CommRptBase implements ISTCommonReportFacade
{
    public STCommonReportFacade()
    {
        super();
        registerInterface(ISTCommonReportFacade.class, this);
    }
    public STCommonReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISTCommonReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4F2EAFA");
    }
    private STCommonReportFacadeController getController() throws BOSException
    {
        return (STCommonReportFacadeController)getBizController();
    }
}