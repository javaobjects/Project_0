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

public interface ISTTreeBaseDataTree extends ITreeBase
{
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STTreeBaseDataTreeInfo getSTTreeBaseDataTreeInfo(String oql) throws BOSException, EASBizException;
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection() throws BOSException;
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection(EntityViewInfo view) throws BOSException;
    public STTreeBaseDataTreeCollection getSTTreeBaseDataTreeCollection(String oql) throws BOSException;
}