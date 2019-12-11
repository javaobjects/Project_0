package com.kingdee.eas.custom.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.app.STDataBaseController;
import com.kingdee.eas.custom.basedata.ChitCostInfo;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.basedata.ChitCostCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChitCostController extends STDataBaseController
{
    public ChitCostInfo getChitCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChitCostInfo getChitCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChitCostInfo getChitCostInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChitCostCollection getChitCostCollection(Context ctx) throws BOSException, RemoteException;
    public ChitCostCollection getChitCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChitCostCollection getChitCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
}