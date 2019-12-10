package com.kingdee.eas.custom.basedata.app;

import org.apache.log4j.Logger;
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

import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.basedata.TestInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.custom.basedata.TestCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
/**
 * Java类描述：
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * @author DZ_yanb
 * @date 2019-12-10    
 * @version 1.0
 */
public class TestControllerBean extends AbstractTestControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.custom.basedata.app.TestControllerBean");
    
    //com.kingdee.eas.custom.basedata.Test    用于搜索更新

    //禁用
    public void cancel(Context arg0, IObjectPK arg1, CoreBaseInfo arg2)throws BOSException, EASBizException {
    	com.kingdee.eas.custom.basedata.TestInfo info = (com.kingdee.eas.custom.basedata.TestInfo)arg2;
    	info.setCancelUser(com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(arg0));
    	info.setCancelDate(new java.util.Date());
    	info.setIsLoaded(false);
    	super.cancel(arg0, arg1, arg2);
    }
    
    //启用
    public void cancelCancel(Context arg0, IObjectPK arg1, CoreBaseInfo arg2)throws BOSException, EASBizException {
    	com.kingdee.eas.custom.basedata.TestInfo info = (com.kingdee.eas.custom.basedata.TestInfo)arg2;
    	info.setCancelCancelUser(com.kingdee.eas.util.app.ContextUtil.getCurrentUserInfo(arg0));
    	info.setCancelCancelDate(new java.util.Date());
     	info.setIsLoaded(false);
    	super.cancelCancel(arg0, arg1, arg2);
    }
    
    //增加校验名称不能重复
    protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
    	com.kingdee.eas.custom.basedata.TestInfo info = (com.kingdee.eas.custom.basedata.TestInfo)model;
    	
    	checkNumberBlank(ctx, info);
    	checkNumberDup(ctx, info);
    	checkNameBlank(ctx, info);
    	checkNameDup(ctx, info);
    	return super._submit(ctx, model);
    }
}