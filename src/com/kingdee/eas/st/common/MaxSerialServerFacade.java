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
     *批量获取最大序号-User defined method
     *@param keyItem 关键字
     *@param keyItemValue 关键字值组
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
     *批量获取最大序号-User defined method
     *@param keyItem 关键字
     *@param keyItemValue 关键字值组
     *@param step 步进
     *@param initValue 初始值
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
     *批量获取最大序号-User defined method
     *@param keyItem 关键字
     *@param keyItemValue 关键字值组
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
     *批量获取最大序号-User defined method
     *@param keyItem 关键字
     *@param keyItemValue 关键字值组
     *@param step 步进
     *@param initValue 初始值
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
     *关键字最大序号记录是否存在-User defined method
     *@param keyItem 关键字
     *@param keyItemValue 关键字值
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