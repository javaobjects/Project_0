package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.basedata.framework.DataBaseD;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.framework.IDataBaseD;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class STTreeBaseDataD extends DataBaseD implements ISTTreeBaseDataD
{
    public STTreeBaseDataD()
    {
        super();
        registerInterface(ISTTreeBaseDataD.class, this);
    }
    public STTreeBaseDataD(Context ctx)
    {
        super(ctx);
        registerInterface(ISTTreeBaseDataD.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("42D462E2");
    }
    private STTreeBaseDataDController getController() throws BOSException
    {
        return (STTreeBaseDataDController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDInfo(getContext(), pk);
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
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDInfo(getContext(), pk, selector);
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
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTTreeBaseDataDInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection() throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDCollection(getContext());
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
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDCollection(getContext(), view);
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
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTTreeBaseDataDCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��׼-User defined method
     *@param pks pks
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
     *�Ǻ�׼-User defined method
     *@param pks pks
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
     *@param queryState 0=�Ǻ�׼��1����׼
     *@return
     */
    public String queryState(IObjectPK[] pks, int queryState) throws BOSException, EASBizException
    {
        try {
            return getController().queryState(getContext(), pks, queryState);
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
    public boolean checkHasItems(String treeid) throws BOSException, EASBizException
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