package com.kingdee.eas.custom.bill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.st.common.app.STBillBaseEntryController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.bill.TlSlipEntryInfo;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.bill.TlSlipEntryCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TlSlipEntryController extends STBillBaseEntryController
{
    public TlSlipEntryInfo getTlSlipEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TlSlipEntryInfo getTlSlipEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TlSlipEntryInfo getTlSlipEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TlSlipEntryCollection getTlSlipEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TlSlipEntryCollection getTlSlipEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TlSlipEntryCollection getTlSlipEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}