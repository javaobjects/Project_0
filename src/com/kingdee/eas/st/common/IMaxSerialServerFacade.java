package com.kingdee.eas.st.common;

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

public interface IMaxSerialServerFacade extends IBizCtrl
{
    public long getMaxSerial(String keyItem, String keyItemValue) throws BOSException;
    public long getMaxSerial(String keyItem, String keyItemValue, int step, long initValue) throws BOSException;
    public long[] getMaxSerial(String keyItem, String[] keyItemValue) throws BOSException;
    public long[] getMaxSerial(String keyItem, String[] keyItemValue, int step, long initValue) throws BOSException;
    public boolean isExists(String keyItem, String keyItemValue) throws BOSException;
}