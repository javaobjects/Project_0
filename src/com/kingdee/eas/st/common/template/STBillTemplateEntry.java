package com.kingdee.eas.st.common.template;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.ISTBillBaseEntry;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.template.app.*;
import com.kingdee.eas.st.common.STBillBaseEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public abstract class STBillTemplateEntry extends STBillBaseEntry implements ISTBillTemplateEntry
{
    public STBillTemplateEntry()
    {
        super();
        registerInterface(ISTBillTemplateEntry.class, this);
    }
    public STBillTemplateEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBillTemplateEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E424204A");
    }
    private STBillTemplateEntryController getController() throws BOSException
    {
        return (STBillTemplateEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillTemplateEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection() throws BOSException
    {
        try {
            return getController().getSTBillTemplateEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTBillTemplateEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTBillTemplateEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}