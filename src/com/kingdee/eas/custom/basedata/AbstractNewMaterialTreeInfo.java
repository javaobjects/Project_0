package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewMaterialTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractNewMaterialTreeInfo()
    {
        this("id");
    }
    protected AbstractNewMaterialTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 新建物料组别 's 父节点 property 
     */
    public com.kingdee.eas.custom.basedata.NewMaterialTreeInfo getParent()
    {
        return (com.kingdee.eas.custom.basedata.NewMaterialTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.basedata.NewMaterialTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CE6060BD");
    }
}