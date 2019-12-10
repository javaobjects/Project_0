package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.st.common.STTreeBaseDataCollection;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.STTreeBaseDataInfo;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STTreeBaseDataController extends DataBaseController
{
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(Context ctx) throws BOSException, RemoteException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public String queryState(Context ctx, IObjectPK[] pks, int state) throws BOSException, EASBizException, RemoteException;
    public boolean checkHasItems(Context ctx, String treeid) throws BOSException, RemoteException;
    public String getTreeId(Context ctx, String id) throws BOSException, RemoteException;
}