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

public class TtaskListEntry extends STBillBaseEntry implements ITtaskListEntry
{
    public TtaskListEntry()
    {
        super();
        registerInterface(ITtaskListEntry.class, this);
    }
    public TtaskListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITtaskListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FBC6D457");
    }
    private TtaskListEntryController getController() throws BOSException
    {
        return (TtaskListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TtaskListEntryInfo getTtaskListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListEntryInfo(getContext(), pk);
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
    public TtaskListEntryInfo getTtaskListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListEntryInfo(getContext(), pk, selector);
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
    public TtaskListEntryInfo getTtaskListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TtaskListEntryCollection getTtaskListEntryCollection() throws BOSException
    {
        try {
            return getController().getTtaskListEntryCollection(getContext());
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
    public TtaskListEntryCollection getTtaskListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTtaskListEntryCollection(getContext(), view);
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
    public TtaskListEntryCollection getTtaskListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTtaskListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}