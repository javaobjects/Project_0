package com.kingdee.eas.Interfacetools.app;

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



public abstract class AbstractCommonInterfaceControllerBean extends AbstractBizControllerBean implements CommonInterfaceController
{
    protected AbstractCommonInterfaceControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("2F5B901A");
    }

    public String[][] onloadData(Context ctx, String easTemplateNumber, String[] jsonDatas, boolean isUpdate) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("43354026-e4c0-4691-9341-b1488468d649"), new Object[]{ctx, easTemplateNumber, jsonDatas, new Boolean(isUpdate)});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            String[][] retValue = (String[][])_onloadData(ctx, easTemplateNumber, jsonDatas, isUpdate);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (String[][])svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected String[][] _onloadData(Context ctx, String easTemplateNumber, String[] jsonDatas, boolean isUpdate) throws BOSException
    {    	
        return null;
    }

}