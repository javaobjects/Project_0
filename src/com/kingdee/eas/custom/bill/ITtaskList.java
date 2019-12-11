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

public interface ITtaskList extends ISTBillBase
{
    public TtaskListInfo getTtaskListInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TtaskListInfo getTtaskListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TtaskListInfo getTtaskListInfo(String oql) throws BOSException, EASBizException;
    public TtaskListCollection getTtaskListCollection() throws BOSException;
    public TtaskListCollection getTtaskListCollection(EntityViewInfo view) throws BOSException;
    public TtaskListCollection getTtaskListCollection(String oql) throws BOSException;
}