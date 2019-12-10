package com.kingdee.eas.st.common.template;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractSTBillTemplateEntryInfo extends com.kingdee.eas.st.common.STBillBaseEntryInfo implements Serializable 
{
    public AbstractSTBillTemplateEntryInfo()
    {
        this("id");
    }
    protected AbstractSTBillTemplateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 单据分录 's 模板单头 property 
     */
    public com.kingdee.eas.st.common.template.STBillTemplateInfo getParent()
    {
        return (com.kingdee.eas.st.common.template.STBillTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.st.common.template.STBillTemplateInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E424204A");
    }
}