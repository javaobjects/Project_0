package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestBillInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractTestBillInfo()
    {
        this("id");
    }
    protected AbstractTestBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.custom.bill.TestBillEntryCollection());
    }
    /**
     * Object: 单据测试样例 's 单据分录 property 
     */
    public com.kingdee.eas.custom.bill.TestBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.custom.bill.TestBillEntryCollection)get("entries");
    }
    /**
     * Object: 单据测试样例 's 库存组织 property 
     */
    public com.kingdee.eas.basedata.org.StorageOrgUnitInfo getStorageOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.StorageOrgUnitInfo)get("storageOrgUnit");
    }
    public void setStorageOrgUnit(com.kingdee.eas.basedata.org.StorageOrgUnitInfo item)
    {
        put("storageOrgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8722E695");
    }
}