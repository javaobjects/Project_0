package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ICoreBillEntryBase;
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

public interface ISTBillBaseEntry extends ICoreBillEntryBase
{
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBillBaseEntryInfo getSTBillBaseEntryInfo(String oql) throws BOSException, EASBizException;
    public STBillBaseEntryCollection getSTBillBaseEntryCollection() throws BOSException;
    public STBillBaseEntryCollection getSTBillBaseEntryCollection(EntityViewInfo view) throws BOSException;
    public STBillBaseEntryCollection getSTBillBaseEntryCollection(String oql) throws BOSException;
}