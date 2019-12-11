package com.kingdee.eas.custom.bill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.custom.bill.TtaskListCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.custom.bill.TtaskListInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.app.STBillBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TtaskListController extends STBillBaseController
{
    public TtaskListInfo getTtaskListInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TtaskListInfo getTtaskListInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TtaskListInfo getTtaskListInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TtaskListCollection getTtaskListCollection(Context ctx) throws BOSException, RemoteException;
    public TtaskListCollection getTtaskListCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TtaskListCollection getTtaskListCollection(Context ctx, String oql) throws BOSException, RemoteException;
}