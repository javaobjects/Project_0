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

public interface ITlSlipEntry extends ISTBillBaseEntry
{
    public TlSlipEntryInfo getTlSlipEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TlSlipEntryInfo getTlSlipEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TlSlipEntryInfo getTlSlipEntryInfo(String oql) throws BOSException, EASBizException;
    public TlSlipEntryCollection getTlSlipEntryCollection() throws BOSException;
    public TlSlipEntryCollection getTlSlipEntryCollection(EntityViewInfo view) throws BOSException;
    public TlSlipEntryCollection getTlSlipEntryCollection(String oql) throws BOSException;
}