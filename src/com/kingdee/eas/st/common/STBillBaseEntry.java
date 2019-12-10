package com.kingdee.eas.st.common;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public abstract class STBillBaseEntry extends CoreBillEntryBase implements ISTBillBaseEntry
{
    public STBillBaseEntry()
    {
        super();
        registerInterface(ISTBillBaseEntry.class, this);
    }
    public STBillBaseEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBillBaseEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("05B845BF");
    }
    private STBillBaseEntryController getController() throws BOSException
    {
        return (STBillBaseEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseEntryInfo(getContext(), pk);
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
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseEntryInfo(getContext(), pk, selector);
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
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public STBillBaseEntryCollection getSTBillBaseEntryCollection() throws BOSException
    {
        try {
            return getController().getSTBillBaseEntryCollection(getContext());
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
    public STBillBaseEntryCollection getSTBillBaseEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTBillBaseEntryCollection(getContext(), view);
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
    public STBillBaseEntryCollection getSTBillBaseEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTBillBaseEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}