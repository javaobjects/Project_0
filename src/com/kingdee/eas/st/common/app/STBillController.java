package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.st.common.STBillCollection;
import com.kingdee.eas.st.common.STBillInfo;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STBillController extends STBillBaseController
{
    public STBillInfo getSTBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STBillInfo getSTBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STBillInfo getSTBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STBillCollection getSTBillCollection(Context ctx) throws BOSException, RemoteException;
    public STBillCollection getSTBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STBillCollection getSTBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}