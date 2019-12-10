package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.STDataBaseDInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.framework.app.DataBaseDController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.st.common.STDataBaseDCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STDataBaseDController extends DataBaseDController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STDataBaseDInfo getSTDataBaseDInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STDataBaseDInfo getSTDataBaseDInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STDataBaseDInfo getSTDataBaseDInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, STDataBaseDInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, STDataBaseDInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public STDataBaseDCollection getSTDataBaseDCollection(Context ctx) throws BOSException, RemoteException;
    public STDataBaseDCollection getSTDataBaseDCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STDataBaseDCollection getSTDataBaseDCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public boolean enabled(Context ctx, IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean disEnabled(Context ctx, IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean audit(Context ctx, IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean disAudit(Context ctx, IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException, RemoteException;
}