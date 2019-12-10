package com.kingdee.eas.custom.bill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.app.STBillBaseController;
import com.kingdee.eas.custom.bill.TestBillInfo;
import com.kingdee.eas.custom.bill.TestBillCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TestBillController extends STBillBaseController
{
    public TestBillInfo getTestBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TestBillInfo getTestBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TestBillInfo getTestBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TestBillCollection getTestBillCollection(Context ctx) throws BOSException, RemoteException;
    public TestBillCollection getTestBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TestBillCollection getTestBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}