package com.kingdee.eas.st.common.template.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.st.common.template.STBillTemplateCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.app.STBillBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.st.common.template.STBillTemplateInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STBillTemplateController extends STBillBaseController
{
    public STBillTemplateInfo getSTBillTemplateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STBillTemplateInfo getSTBillTemplateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STBillTemplateInfo getSTBillTemplateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STBillTemplateCollection getSTBillTemplateCollection(Context ctx) throws BOSException, RemoteException;
    public STBillTemplateCollection getSTBillTemplateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STBillTemplateCollection getSTBillTemplateCollection(Context ctx, String oql) throws BOSException, RemoteException;
}