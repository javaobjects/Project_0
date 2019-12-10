package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestBillEntryE2Info extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTestBillEntryE2Info()
    {
        this("id");
    }
    protected AbstractTestBillEntryE2Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第2个表体 's null property 
     */
    public com.kingdee.eas.custom.bill.TestBillEntryInfo getParent1()
    {
        return (com.kingdee.eas.custom.bill.TestBillEntryInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.custom.bill.TestBillEntryInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:第2个表体's 子分录字段property 
     */
    public String getBIMUDF0003()
    {
        return getString("BIMUDF0003");
    }
    public void setBIMUDF0003(String item)
    {
        setString("BIMUDF0003", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5758CC2A");
    }
}