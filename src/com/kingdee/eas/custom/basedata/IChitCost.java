package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.ISTDataBase;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IChitCost extends ISTDataBase
{
    public ChitCostInfo getChitCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ChitCostInfo getChitCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ChitCostInfo getChitCostInfo(String oql) throws BOSException, EASBizException;
    public ChitCostCollection getChitCostCollection() throws BOSException;
    public ChitCostCollection getChitCostCollection(EntityViewInfo view) throws BOSException;
    public ChitCostCollection getChitCostCollection(String oql) throws BOSException;
}