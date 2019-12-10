package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.STTreeBaseDataDCollection;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.framework.app.DataBaseDController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.STTreeBaseDataDInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STTreeBaseDataDController extends DataBaseDController
{
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataDInfo getSTTreeBaseDataDInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection(Context ctx) throws BOSException, RemoteException;
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STTreeBaseDataDCollection getSTTreeBaseDataDCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public String queryState(Context ctx, IObjectPK[] pks, int queryState) throws BOSException, EASBizException, RemoteException;
    public boolean checkHasItems(Context ctx, String treeid) throws BOSException, EASBizException, RemoteException;
    public String getTreeId(Context ctx, String id) throws BOSException, RemoteException;
}