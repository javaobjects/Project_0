package com.kingdee.eas.st.common;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public interface ISTBillCommonFacade extends IBizCtrl
{
    public Timestamp getServerDate() throws BOSException;
    public IRowSet executeQuery(String sql) throws BOSException;
    public IRowSet executeQuery(String sql, Object[] params) throws BOSException;
    public String getBillCodingRuleNumber(STBillBaseInfo model, String orgID) throws BOSException, EASBizException;
}