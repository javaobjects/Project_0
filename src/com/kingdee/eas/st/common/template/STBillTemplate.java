package com.kingdee.eas.st.common.template;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.eas.st.common.template.app.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.STBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public abstract class STBillTemplate extends STBillBase implements ISTBillTemplate
{
    public STBillTemplate()
    {
        super();
        registerInterface(ISTBillTemplate.class, this);
    }
    public STBillTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBillTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0F04DD08");
    }
    private STBillTemplateController getController() throws BOSException
    {
        return (STBillTemplateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public STBillTemplateInfo getSTBillTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateInfo(getContext(), pk);
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
    public STBillTemplateInfo getSTBillTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateInfo(getContext(), pk, selector);
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
    public STBillTemplateInfo getSTBillTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public STBillTemplateCollection getSTBillTemplateCollection() throws BOSException
    {
        try {
            return getController().getSTBillTemplateCollection(getContext());
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
    public STBillTemplateCollection getSTBillTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTBillTemplateCollection(getContext(), view);
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
    public STBillTemplateCollection getSTBillTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTBillTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}