package com.kingdee.eas.custom.basedata;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.basedata.app.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class NewMaterial extends DataBase implements INewMaterial
{
    public NewMaterial()
    {
        super();
        registerInterface(INewMaterial.class, this);
    }
    public NewMaterial(Context ctx)
    {
        super(ctx);
        registerInterface(INewMaterial.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B522D07F");
    }
    private NewMaterialController getController() throws BOSException
    {
        return (NewMaterialController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public NewMaterialInfo getNewMaterialInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialInfo(getContext(), pk);
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
    public NewMaterialInfo getNewMaterialInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialInfo(getContext(), pk, selector);
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
    public NewMaterialInfo getNewMaterialInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewMaterialInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public NewMaterialCollection getNewMaterialCollection() throws BOSException
    {
        try {
            return getController().getNewMaterialCollection(getContext());
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
    public NewMaterialCollection getNewMaterialCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewMaterialCollection(getContext(), view);
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
    public NewMaterialCollection getNewMaterialCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewMaterialCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}