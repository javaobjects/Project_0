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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface ISTDataBase extends IDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public STDataBaseInfo getSTDataBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STDataBaseInfo getSTDataBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STDataBaseInfo getSTDataBaseInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(STDataBaseInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, STDataBaseInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, STDataBaseInfo model) throws BOSException, EASBizException;
    public void updatePartial(STDataBaseInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, STDataBaseInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public STDataBaseCollection getSTDataBaseCollection() throws BOSException;
    public STDataBaseCollection getSTDataBaseCollection(EntityViewInfo view) throws BOSException;
    public STDataBaseCollection getSTDataBaseCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean enabled(IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException;
    public boolean disEnabled(IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException;
    public boolean audit(IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException;
    public boolean disAudit(IObjectPK ctPK, STDataBaseInfo model) throws BOSException, EASBizException;
    public boolean checkBaseDataDup(String tablename, String billID, String fieldName, String value) throws BOSException;
}