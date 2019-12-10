package com.kingdee.eas.st.common.template;

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

public interface ISTBillTemplateEntry extends ISTBillBaseEntry
{
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBillTemplateEntryInfo getSTBillTemplateEntryInfo(String oql) throws BOSException, EASBizException;
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection() throws BOSException;
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection(EntityViewInfo view) throws BOSException;
    public STBillTemplateEntryCollection getSTBillTemplateEntryCollection(String oql) throws BOSException;
}