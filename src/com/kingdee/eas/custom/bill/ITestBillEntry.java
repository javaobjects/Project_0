package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.ISTBillBaseEntry;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ITestBillEntry extends ISTBillBaseEntry
{
    public TestBillEntryInfo getTestBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TestBillEntryInfo getTestBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TestBillEntryInfo getTestBillEntryInfo(String oql) throws BOSException, EASBizException;
    public TestBillEntryCollection getTestBillEntryCollection() throws BOSException;
    public TestBillEntryCollection getTestBillEntryCollection(EntityViewInfo view) throws BOSException;
    public TestBillEntryCollection getTestBillEntryCollection(String oql) throws BOSException;
}