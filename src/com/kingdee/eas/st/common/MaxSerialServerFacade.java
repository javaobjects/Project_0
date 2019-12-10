package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.bos.util.*;

public class MaxSerialServerFacade extends AbstractBizCtrl implements IMaxSerialServerFacade
{
    public MaxSerialServerFacade()
    {
        super();
        registerInterface(IMaxSerialServerFacade.class, this);
    }
    public MaxSerialServerFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IMaxSerialServerFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8533B86D");
    }
    private MaxSerialServerFacadeController getController() throws BOSException
    {
        return (MaxSerialServerFacadeController)getBizController();
    }
    /**
     *������ȡ������-User defined method
     *@param keyItem �ؼ���
     *@param keyItemValue �ؼ���ֵ��
     *@return
     */
    public long getMaxSerial(String keyItem, String keyItemValue) throws BOSException
    {
        try {
            return getController().getMaxSerial(getContext(), keyItem, keyItemValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ȡ������-User defined method
     *@param keyItem �ؼ���
     *@param keyItemValue �ؼ���ֵ��
     *@param step ����
     *@param initValue ��ʼֵ
     *@return
     */
    public long getMaxSerial(String keyItem, String keyItemValue, int step, long initValue) throws BOSException
    {
        try {
            return getController().getMaxSerial(getContext(), keyItem, keyItemValue, step, initValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ȡ������-User defined method
     *@param keyItem �ؼ���
     *@param keyItemValue �ؼ���ֵ��
     *@return
     */
    public long[] getMaxSerial(String keyItem, String[] keyItemValue) throws BOSException
    {
        try {
            return getController().getMaxSerial(getContext(), keyItem, keyItemValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������ȡ������-User defined method
     *@param keyItem �ؼ���
     *@param keyItemValue �ؼ���ֵ��
     *@param step ����
     *@param initValue ��ʼֵ
     *@return
     */
    public long[] getMaxSerial(String keyItem, String[] keyItemValue, int step, long initValue) throws BOSException
    {
        try {
            return getController().getMaxSerial(getContext(), keyItem, keyItemValue, step, initValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�ؼ��������ż�¼�Ƿ����-User defined method
     *@param keyItem �ؼ���
     *@param keyItemValue �ؼ���ֵ
     *@return
     */
    public boolean isExists(String keyItem, String keyItemValue) throws BOSException
    {
        try {
            return getController().isExists(getContext(), keyItem, keyItemValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}