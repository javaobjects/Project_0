package com.kingdee.eas.custom.basedata;

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

public interface INewMaterial extends IDataBase
{
    public NewMaterialInfo getNewMaterialInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewMaterialInfo getNewMaterialInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewMaterialInfo getNewMaterialInfo(String oql) throws BOSException, EASBizException;
    public NewMaterialCollection getNewMaterialCollection() throws BOSException;
    public NewMaterialCollection getNewMaterialCollection(EntityViewInfo view) throws BOSException;
    public NewMaterialCollection getNewMaterialCollection(String oql) throws BOSException;
}