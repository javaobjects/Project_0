package com.kingdee.eas.st.common.template;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.ISTDataBase;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.STDataBase;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.template.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class STBaseDataTemplate extends STDataBase implements ISTBaseDataTemplate
{
    public STBaseDataTemplate()
    {
        super();
        registerInterface(ISTBaseDataTemplate.class, this);
    }
    public STBaseDataTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBaseDataTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("892850FE");
    }
    private STBaseDataTemplateController getController() throws BOSException
    {
        return (STBaseDataTemplateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBaseDataTemplateInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBaseDataTemplateInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBaseDataTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection() throws BOSException
    {
        try {
            return getController().getSTBaseDataTemplateCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTBaseDataTemplateCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTBaseDataTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}