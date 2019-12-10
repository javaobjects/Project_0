package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestBillEntryInfo extends com.kingdee.eas.st.common.STBillBaseEntryInfo implements Serializable 
{
    public AbstractTestBillEntryInfo()
    {
        this("id");
    }
    protected AbstractTestBillEntryInfo(String pkField)
    {
        super(pkField);
        put("E2", new com.kingdee.eas.custom.bill.TestBillEntryE2Collection());
    }
    /**
     * Object: 单据分录 's 模板单头 property 
     */
    public com.kingdee.eas.custom.bill.TestBillInfo getParent()
    {
        return (com.kingdee.eas.custom.bill.TestBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.bill.TestBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:单据分录's 测试字段property 
     */
    public String getBIMUDF0001()
    {
        return getString("BIMUDF0001");
    }
    public void setBIMUDF0001(String item)
    {
        setString("BIMUDF0001", item);
    }
    /**
     * Object: 单据分录 's 第2个表体 property 
     */
    public com.kingdee.eas.custom.bill.TestBillEntryE2Collection getE2()
    {
        return (com.kingdee.eas.custom.bill.TestBillEntryE2Collection)get("E2");
    }
    /**
     * Object:单据分录's 父分录字段property 
     */
    public String getBIMUDF0002()
    {
        return getString("BIMUDF0002");
    }
    public void setBIMUDF0002(String item)
    {
        setString("BIMUDF0002", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1AFF06DD");
    }
}