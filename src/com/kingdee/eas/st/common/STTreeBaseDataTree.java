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

public class STTreeBaseDataTree extends TreeBase implements ISTTreeBaseDataTree
{
    public STTreeBaseDataTree()
    {
        super();
        registerInterface(ISTTreeBaseDataTree.class, this);
    }
    public STTreeBaseDataTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISTTreeBaseDataTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("555D0A7E");
    }
    private STTreeBaseDataTreeController getController() throws BOSException
    {
        return (STTreeBaseDataTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataTreeInfo(getContext(), pk);
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
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataTreeInfo(getContext(), pk, selector);
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
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection() throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataTreeCollection(getContext());
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
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataTreeCollection(getContext(), view);
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
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}