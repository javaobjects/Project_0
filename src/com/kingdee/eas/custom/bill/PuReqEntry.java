package com.kingdee.eas.custom.bill;

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
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class PuReqEntry extends CoreBillEntryBase implements IPuReqEntry
{
    public PuReqEntry()
    {
        super();
        registerInterface(IPuReqEntry.class, this);
    }
    public PuReqEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPuReqEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8DDA0B75");
    }
    private PuReqEntryController getController() throws BOSException
    {
        return (PuReqEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PuReqEntryInfo getPuReqEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqEntryInfo(getContext(), pk);
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
    public PuReqEntryInfo getPuReqEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqEntryInfo(getContext(), pk, selector);
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
    public PuReqEntryInfo getPuReqEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PuReqEntryCollection getPuReqEntryCollection() throws BOSException
    {
        try {
            return getController().getPuReqEntryCollection(getContext());
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
    public PuReqEntryCollection getPuReqEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPuReqEntryCollection(getContext(), view);
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
    public PuReqEntryCollection getPuReqEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPuReqEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}