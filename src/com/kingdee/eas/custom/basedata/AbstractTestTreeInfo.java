package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestTreeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTestTreeInfo()
    {
        this("id");
    }
    protected AbstractTestTreeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 多级基础资料模板样例组别 's 父节点 property 
     */
    public com.kingdee.eas.custom.basedata.TestTreeInfo getParent()
    {
        return (com.kingdee.eas.custom.basedata.TestTreeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.basedata.TestTreeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2613CB8");
    }
}