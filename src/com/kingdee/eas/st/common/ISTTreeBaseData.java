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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface ISTTreeBaseData extends IDataBase
{
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STTreeBaseDataInfo getSTTreeBaseDataInfo(String oql) throws BOSException, EASBizException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection() throws BOSException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(EntityViewInfo view) throws BOSException;
    public STTreeBaseDataCollection getSTTreeBaseDataCollection(String oql) throws BOSException;
    public void audit(IObjectPK[] pks) throws BOSException, EASBizException;
    public void unaudit(IObjectPK[] pks) throws BOSException, EASBizException;
    public String queryState(IObjectPK[] pks, int state) throws BOSException, EASBizException;
    public boolean checkHasItems(String treeid) throws BOSException;
    public String getTreeId(String id) throws BOSException;
}