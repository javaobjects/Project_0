package com.kingdee.eas.custom.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.custom.basedata.TestInfo;
import java.lang.String;
import com.kingdee.eas.custom.basedata.TestCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TestController extends DataBaseController
{
    public TestInfo getTestInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TestInfo getTestInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TestInfo getTestInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TestCollection getTestCollection(Context ctx) throws BOSException, RemoteException;
    public TestCollection getTestCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TestCollection getTestCollection(Context ctx, String oql) throws BOSException, RemoteException;
}