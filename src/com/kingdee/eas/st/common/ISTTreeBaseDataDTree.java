package com.kingdee.eas.st.common;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.ITreeBase;
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

public interface ISTTreeBaseDataDTree extends ITreeBase
{
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STTreeBaseDataDTreeInfo getSTTreeBaseDataDTreeInfo(String oql) throws BOSException, EASBizException;
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection() throws BOSException;
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection(EntityViewInfo view) throws BOSException;
    public STTreeBaseDataDTreeCollection getSTTreeBaseDataDTreeCollection(String oql) throws BOSException;
}