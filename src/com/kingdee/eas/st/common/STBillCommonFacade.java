package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.sql.Timestamp;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class STBillCommonFacade extends AbstractBizCtrl implements ISTBillCommonFacade
{
    public STBillCommonFacade()
    {
        super();
        registerInterface(ISTBillCommonFacade.class, this);
    }
    public STBillCommonFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBillCommonFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9FFEE155");
    }
    private STBillCommonFacadeController getController() throws BOSException
    {
        return (STBillCommonFacadeController)getBizController();
    }
    /**
     *��ȡ�������˵�����-User defined method
     *@return
     */
    public Timestamp getServerDate() throws BOSException
    {
        try {
            return getController().getServerDate(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ѯ-User defined method
     *@param sql sql
     *@return
     */
    public IRowSet executeQuery(String sql) throws BOSException
    {
        try {
            return getController().executeQuery(getContext(), sql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ѯ-User defined method
     *@param sql sql
     *@param params ����
     *@return
     */
    public IRowSet executeQuery(String sql, Object[] params) throws BOSException
    {
        try {
            return getController().executeQuery(getContext(), sql, params);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���õ��ݱ������-User defined method
     *@param model ʵ��info
     *@param orgID ��֯����
     *@return
     */
    public String getBillCodingRuleNumber(STBillBaseInfo model, String orgID) throws BOSException, EASBizException
    {
        try {
            return getController().getBillCodingRuleNumber(getContext(), model, orgID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}