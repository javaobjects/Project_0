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

public interface ITtaskListEntry extends ISTBillBaseEntry
{
    public TtaskListEntryInfo getTtaskListEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TtaskListEntryInfo getTtaskListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TtaskListEntryInfo getTtaskListEntryInfo(String oql) throws BOSException, EASBizException;
    public TtaskListEntryCollection getTtaskListEntryCollection() throws BOSException;
    public TtaskListEntryCollection getTtaskListEntryCollection(EntityViewInfo view) throws BOSException;
    public TtaskListEntryCollection getTtaskListEntryCollection(String oql) throws BOSException;
}