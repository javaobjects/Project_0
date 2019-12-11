package com.kingdee.eas.custom.bill;

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
import com.kingdee.eas.st.common.STBillBaseEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class TlSlipEntry extends STBillBaseEntry implements ITlSlipEntry
{
    public TlSlipEntry()
    {
        super();
        registerInterface(ITlSlipEntry.class, this);
    }
    public TlSlipEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITlSlipEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5BBEF1E");
    }
    private TlSlipEntryController getController() throws BOSException
    {
        return (TlSlipEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TlSlipEntryInfo getTlSlipEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTlSlipEntryInfo(getContext(), pk);
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
    public TlSlipEntryInfo getTlSlipEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTlSlipEntryInfo(getContext(), pk, selector);
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
    public TlSlipEntryInfo getTlSlipEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTlSlipEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TlSlipEntryCollection getTlSlipEntryCollection() throws BOSException
    {
        try {
            return getController().getTlSlipEntryCollection(getContext());
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
    public TlSlipEntryCollection getTlSlipEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTlSlipEntryCollection(getContext(), view);
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
    public TlSlipEntryCollection getTlSlipEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTlSlipEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}