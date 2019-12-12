package com.kingdee.eas.custom.bill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class PuReq extends CoreBillBase implements IPuReq
{
    public PuReq()
    {
        super();
        registerInterface(IPuReq.class, this);
    }
    public PuReq(Context ctx)
    {
        super(ctx);
        registerInterface(IPuReq.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6CED62FD");
    }
    private PuReqController getController() throws BOSException
    {
        return (PuReqController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PuReqCollection getPuReqCollection() throws BOSException
    {
        try {
            return getController().getPuReqCollection(getContext());
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
    public PuReqCollection getPuReqCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPuReqCollection(getContext(), view);
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
    public PuReqCollection getPuReqCollection(String oql) throws BOSException
    {
        try {
            return getController().getPuReqCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PuReqInfo getPuReqInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqInfo(getContext(), pk);
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
    public PuReqInfo getPuReqInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqInfo(getContext(), pk, selector);
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
    public PuReqInfo getPuReqInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPuReqInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}