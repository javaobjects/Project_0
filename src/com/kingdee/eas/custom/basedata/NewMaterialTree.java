package com.kingdee.eas.custom.basedata;

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
import com.kingdee.eas.custom.basedata.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class NewMaterialTree extends TreeBase implements INewMaterialTree
{
    public NewMaterialTree()
    {
        super();
        registerInterface(INewMaterialTree.class, this);
    }
    public NewMaterialTree(Context ctx)
    {
        super(ctx);
        registerInterface(INewMaterialTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CE6060BD");
    }
    private NewMaterialTreeController getController() throws BOSException
    {
        return (NewMaterialTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewMaterialTreeInfo getNewMaterialTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialTreeInfo(getContext(), pk);
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
    public NewMaterialTreeInfo getNewMaterialTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialTreeInfo(getContext(), pk, selector);
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
    public NewMaterialTreeInfo getNewMaterialTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewMaterialTreeCollection getNewMaterialTreeCollection() throws BOSException
    {
        try {
            return getController().getNewMaterialTreeCollection(getContext());
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
    public NewMaterialTreeCollection getNewMaterialTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewMaterialTreeCollection(getContext(), view);
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
    public NewMaterialTreeCollection getNewMaterialTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewMaterialTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}