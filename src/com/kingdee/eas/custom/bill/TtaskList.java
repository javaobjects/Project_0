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
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.STBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class TtaskList extends STBillBase implements ITtaskList
{
    public TtaskList()
    {
        super();
        registerInterface(ITtaskList.class, this);
    }
    public TtaskList(Context ctx)
    {
        super(ctx);
        registerInterface(ITtaskList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BBBB14DB");
    }
    private TtaskListController getController() throws BOSException
    {
        return (TtaskListController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TtaskListInfo getTtaskListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListInfo(getContext(), pk);
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
    public TtaskListInfo getTtaskListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListInfo(getContext(), pk, selector);
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
    public TtaskListInfo getTtaskListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTtaskListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TtaskListCollection getTtaskListCollection() throws BOSException
    {
        try {
            return getController().getTtaskListCollection(getContext());
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
    public TtaskListCollection getTtaskListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTtaskListCollection(getContext(), view);
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
    public TtaskListCollection getTtaskListCollection(String oql) throws BOSException
    {
        try {
            return getController().getTtaskListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}