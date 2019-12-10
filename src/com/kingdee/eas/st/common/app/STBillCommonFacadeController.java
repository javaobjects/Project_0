package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.sql.Timestamp;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STBillCommonFacadeController extends BizController
{
    public Timestamp getServerDate(Context ctx) throws BOSException, RemoteException;
    public IRowSet executeQuery(Context ctx, String sql) throws BOSException, RemoteException;
    public IRowSet executeQuery(Context ctx, String sql, Object[] params) throws BOSException, RemoteException;
    public String getBillCodingRuleNumber(Context ctx, STBillBaseInfo model, String orgID) throws BOSException, EASBizException, RemoteException;
}