package com.kingdee.eas.custom.bill;

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
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IPuReq extends ICoreBillBase
{
    public PuReqCollection getPuReqCollection() throws BOSException;
    public PuReqCollection getPuReqCollection(EntityViewInfo view) throws BOSException;
    public PuReqCollection getPuReqCollection(String oql) throws BOSException;
    public PuReqInfo getPuReqInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PuReqInfo getPuReqInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PuReqInfo getPuReqInfo(String oql) throws BOSException, EASBizException;
}