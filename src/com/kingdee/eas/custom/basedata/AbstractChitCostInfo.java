package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChitCostInfo extends com.kingdee.eas.st.common.STDataBaseInfo implements Serializable 
{
    public AbstractChitCostInfo()
    {
        this("id");
    }
    protected AbstractChitCostInfo(String pkField)
    {
        super(pkField);
        put("PropertyCost", new com.kingdee.eas.custom.basedata.ChitCostPropertyCostCollection());
    }
    /**
     * Object: 收费项目成本 's 收费项目成本单据体 property 
     */
    public com.kingdee.eas.custom.basedata.ChitCostPropertyCostCollection getPropertyCost()
    {
        return (com.kingdee.eas.custom.basedata.ChitCostPropertyCostCollection)get("PropertyCost");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9A07B9A5");
    }
}