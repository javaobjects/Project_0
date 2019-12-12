package com.kingdee.eas.custom.bill;

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

public interface IPuReqEntry extends ICoreBillEntryBase
{
    public PuReqEntryInfo getPuReqEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PuReqEntryInfo getPuReqEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PuReqEntryInfo getPuReqEntryInfo(String oql) throws BOSException, EASBizException;
    public PuReqEntryCollection getPuReqEntryCollection() throws BOSException;
    public PuReqEntryCollection getPuReqEntryCollection(EntityViewInfo view) throws BOSException;
    public PuReqEntryCollection getPuReqEntryCollection(String oql) throws BOSException;
}