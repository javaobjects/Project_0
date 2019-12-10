package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.TreeBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class STTreeBaseDataDTree extends TreeBase implements ISTTreeBaseDataDTree
{
    public STTreeBaseDataDTree()
    {
        super();
        registerInterface(ISTTreeBaseDataDTree.class, this);
    }
    public STTreeBaseDataDTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISTTreeBaseDataDTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2A6C09A0");
    }
    private STTreeBaseDataDTreeController getController() throws BOSException
    {
        return (STTreeBaseDataDTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDTreeInfo(getContext(), pk);
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
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDTreeInfo(getContext(), pk, selector);
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
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection() throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDTreeCollection(getContext());
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
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDTreeCollection(getContext(), view);
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
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}