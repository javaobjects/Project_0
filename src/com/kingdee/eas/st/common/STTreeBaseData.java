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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class STTreeBaseData extends DataBase implements ISTTreeBaseData
{
    public STTreeBaseData()
    {
        super();
        registerInterface(ISTTreeBaseData.class, this);
    }
    public STTreeBaseData(Context ctx)
    {
        super(ctx);
        registerInterface(ISTTreeBaseData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9CB482C0");
    }
    private STTreeBaseDataController getController() throws BOSException
    {
        return (STTreeBaseDataController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataInfo(getContext(), pk);
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
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataInfo(getContext(), pk, selector);
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
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public STTreeBaseDataCollection getSTTreeBaseDataCollection() throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataCollection(getContext());
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
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataCollection(getContext(), view);
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
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��׼-User defined method
     *@param pks ����һ�����ID
     */
    public void audit(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����׼-User defined method
     *@param pks һ�����ID
     */
    public void unaudit(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *queryState-User defined method
     *@param pks pks
     *@param state 0=�Ǻ�׼��1=��׼
     *@return
     */
    public String queryState(IObjectPK[] pks, int state) throws BOSException, EASBizException
    {
        try {
            return getController().queryState(getContext(), pks, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ж������Ƿ���й���-User defined method
     *@param treeid treeid
     *@return
     */
    public boolean checkHasItems(String treeid) throws BOSException
    {
        try {
            return getController().checkHasItems(getContext(), treeid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������б���󣬻����������-User defined method
     *@param id id
     *@return
     */
    public String getTreeId(String id) throws BOSException
    {
        try {
            return getController().getTreeId(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}