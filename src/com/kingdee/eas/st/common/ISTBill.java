package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
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

public interface ISTBill extends ISTBillBase
{
    public STBillInfo getSTBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBillInfo getSTBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBillInfo getSTBillInfo(String oql) throws BOSException, EASBizException;
    public STBillCollection getSTBillCollection() throws BOSException;
    public STBillCollection getSTBillCollection(EntityViewInfo view) throws BOSException;
    public STBillCollection getSTBillCollection(String oql) throws BOSException;
}