package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.framework.IDataBaseD;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface ISTDataBaseD extends IDataBaseD
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public STDataBaseDInfo getSTDataBaseDInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STDataBaseDInfo getSTDataBaseDInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STDataBaseDInfo getSTDataBaseDInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(STDataBaseDInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, STDataBaseDInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, STDataBaseDInfo model) throws BOSException, EASBizException;
    public void updatePartial(STDataBaseDInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, STDataBaseDInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public STDataBaseDCollection getSTDataBaseDCollection() throws BOSException;
    public STDataBaseDCollection getSTDataBaseDCollection(EntityViewInfo view) throws BOSException;
    public STDataBaseDCollection getSTDataBaseDCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException;
    public boolean audit(IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException;
    public boolean disAudit(IObjectPK ctPK, STDataBaseDInfo model) throws BOSException, EASBizException;
}