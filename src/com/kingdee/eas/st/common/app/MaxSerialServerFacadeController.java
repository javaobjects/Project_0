package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MaxSerialServerFacadeController extends BizController
{
    public long getMaxSerial(Context ctx, String keyItem, String keyItemValue) throws BOSException, RemoteException;
    public long getMaxSerial(Context ctx, String keyItem, String keyItemValue, int step, long initValue) throws BOSException, RemoteException;
    public long[] getMaxSerial(Context ctx, String keyItem, String[] keyItemValue) throws BOSException, RemoteException;
    public long[] getMaxSerial(Context ctx, String keyItem, String[] keyItemValue, int step, long initValue) throws BOSException, RemoteException;
    public boolean isExists(Context ctx, String keyItem, String keyItemValue) throws BOSException, RemoteException;
}