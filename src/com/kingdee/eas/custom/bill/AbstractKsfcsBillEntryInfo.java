package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractKsfcsBillEntryInfo extends com.kingdee.eas.st.common.STBillBaseEntryInfo implements Serializable 
{
    public AbstractKsfcsBillEntryInfo()
    {
        this("id");
    }
    protected AbstractKsfcsBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 单据分录 's 模板单头 property 
     */
    public com.kingdee.eas.custom.bill.KsfcsBillInfo getParent()
    {
        return (com.kingdee.eas.custom.bill.KsfcsBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.bill.KsfcsBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C519539");
    }
}