package com.kingdee.eas.st.common.template;

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

public interface ISTBaseDataTemplate extends ISTDataBase
{
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBaseDataTemplateInfo getSTBaseDataTemplateInfo(String oql) throws BOSException, EASBizException;
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection() throws BOSException;
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection(EntityViewInfo view) throws BOSException;
    public STBaseDataTemplateCollection getSTBaseDataTemplateCollection(String oql) throws BOSException;
}