package com.kingdee.eas.st.common.template;

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

public interface ISTBillTemplate extends ISTBillBase
{
    public STBillTemplateInfo getSTBillTemplateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBillTemplateInfo getSTBillTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBillTemplateInfo getSTBillTemplateInfo(String oql) throws BOSException, EASBizException;
    public STBillTemplateCollection getSTBillTemplateCollection() throws BOSException;
    public STBillTemplateCollection getSTBillTemplateCollection(EntityViewInfo view) throws BOSException;
    public STBillTemplateCollection getSTBillTemplateCollection(String oql) throws BOSException;
}