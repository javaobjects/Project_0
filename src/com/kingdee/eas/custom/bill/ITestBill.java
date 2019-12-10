package com.kingdee.eas.custom.bill;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ITestBill extends ISTBillBase
{
    public TestBillInfo getTestBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TestBillInfo getTestBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TestBillInfo getTestBillInfo(String oql) throws BOSException, EASBizException;
    public TestBillCollection getTestBillCollection() throws BOSException;
    public TestBillCollection getTestBillCollection(EntityViewInfo view) throws BOSException;
    public TestBillCollection getTestBillCollection(String oql) throws BOSException;
}