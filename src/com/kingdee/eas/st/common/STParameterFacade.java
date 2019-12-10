package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.util.param.STParamReader;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class STParameterFacade extends AbstractBizCtrl implements ISTParameterFacade
{
    public STParameterFacade()
    {
        super();
        registerInterface(ISTParameterFacade.class, this);
    }
    public STParameterFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISTParameterFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("30158F3A");
    }
    private STParameterFacadeController getController() throws BOSException
    {
        return (STParameterFacadeController)getBizController();
    }
    /**
     *获取系统参数-User defined method
     *@param paramNumber 参数编码
     *@param viewInfo 取树视图
     *@return
     */
    public STParamReader getParameter(String paramNumber, EntityViewInfo viewInfo) throws BOSException, EASBizException
    {
        try {
            return getController().getParameter(getContext(), paramNumber, viewInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}