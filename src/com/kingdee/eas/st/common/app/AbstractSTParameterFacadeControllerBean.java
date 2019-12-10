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

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.util.param.STParamReader;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractSTParameterFacadeControllerBean extends AbstractBizControllerBean implements STParameterFacadeController
{
    protected AbstractSTParameterFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("30158F3A");
    }

    public STParamReader getParameter(Context ctx, String paramNumber, EntityViewInfo viewInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3137f36f-010f-1000-e000-0001c0a81243"), new Object[]{ctx, paramNumber, viewInfo});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            STParamReader retValue = (STParamReader)_getParameter(ctx, paramNumber, viewInfo);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (STParamReader)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract STParamReader _getParameter(Context ctx, String paramNumber, EntityViewInfo viewInfo) throws BOSException, EASBizException;

}