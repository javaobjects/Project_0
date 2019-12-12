package com.kingdee.eas.custom.bill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.custom.bill.KsfcsBillInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.custom.bill.KsfcsBillCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.app.STBillBaseController;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface KsfcsBillController extends STBillBaseController
{
    public KsfcsBillInfo getKsfcsBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public KsfcsBillInfo getKsfcsBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public KsfcsBillInfo getKsfcsBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public KsfcsBillCollection getKsfcsBillCollection(Context ctx) throws BOSException, RemoteException;
    public KsfcsBillCollection getKsfcsBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public KsfcsBillCollection getKsfcsBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}