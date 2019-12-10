package com.kingdee.eas.st.common.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;



public abstract class AbstractMaxSerialServerFacadeControllerBean extends AbstractBizControllerBean implements MaxSerialServerFacadeController
{
    protected AbstractMaxSerialServerFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("8533B86D");
    }

    public long getMaxSerial(Context ctx, String keyItem, String keyItemValue) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a7e8e167-0115-1000-e000-0007c0a81298"), new Object[]{ctx, keyItem, keyItemValue});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            long retValue = (long)_getMaxSerial(ctx, keyItem, keyItemValue);
            svcCtx.setMethodReturnValue(new Long(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Long)svcCtx.getMethodReturnValue()).longValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract long _getMaxSerial(Context ctx, String keyItem, String keyItemValue) throws BOSException;

    public long getMaxSerial(Context ctx, String keyItem, String keyItemValue, int step, long initValue) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a7e8e167-0115-1000-e000-0008c0a81298"), new Object[]{ctx, keyItem, keyItemValue, new Integer(step), new Long(initValue)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            long retValue = (long)_getMaxSerial(ctx, keyItem, keyItemValue, step, initValue);
            svcCtx.setMethodReturnValue(new Long(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Long)svcCtx.getMethodReturnValue()).longValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract long _getMaxSerial(Context ctx, String keyItem, String keyItemValue, int step, long initValue) throws BOSException;

    public long[] getMaxSerial(Context ctx, String keyItem, String[] keyItemValue) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a7e8e167-0115-1000-e000-0009c0a81298"), new Object[]{ctx, keyItem, keyItemValue});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            long[] retValue = (long[])_getMaxSerial(ctx, keyItem, keyItemValue);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (long[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract long[] _getMaxSerial(Context ctx, String keyItem, String[] keyItemValue) throws BOSException;

    public long[] getMaxSerial(Context ctx, String keyItem, String[] keyItemValue, int step, long initValue) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a7e8e167-0115-1000-e000-000ac0a81298"), new Object[]{ctx, keyItem, keyItemValue, new Integer(step), new Long(initValue)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            long[] retValue = (long[])_getMaxSerial(ctx, keyItem, keyItemValue, step, initValue);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (long[])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract long[] _getMaxSerial(Context ctx, String keyItem, String[] keyItemValue, int step, long initValue) throws BOSException;

    public boolean isExists(Context ctx, String keyItem, String keyItemValue) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a7e8e167-0115-1000-e000-000bc0a81298"), new Object[]{ctx, keyItem, keyItemValue});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            boolean retValue = (boolean)_isExists(ctx, keyItem, keyItemValue);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            }
            invokeServiceAfter(svcCtx);
            return ((Boolean)svcCtx.getMethodReturnValue()).booleanValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _isExists(Context ctx, String keyItem, String keyItemValue) throws BOSException;

}