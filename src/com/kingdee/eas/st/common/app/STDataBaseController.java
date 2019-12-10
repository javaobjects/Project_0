package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.st.common.STDataBaseCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.STDataBaseInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STDataBaseController extends DataBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STDataBaseInfo getSTDataBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STDataBaseInfo getSTDataBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STDataBaseInfo getSTDataBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, STDataBaseInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, STDataBaseInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public STDataBaseCollection getSTDataBaseCollection(Context ctx) throws BOSException, RemoteException;
    public STDataBaseCollection getSTDataBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STDataBaseCollection getSTDataBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public boolean enabled(Context ctx, IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean disEnabled(Context ctx, IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean audit(Context ctx, IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean disAudit(Context ctx, IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException, RemoteException;
    public boolean checkBaseDataDup(Context ctx, String tablename, String billID, String fieldName, String value) throws BOSException, RemoteException;
}