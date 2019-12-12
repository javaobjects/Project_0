package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPuReqInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPuReqInfo()
    {
        this("id");
    }
    protected AbstractPuReqInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.custom.bill.PuReqEntryCollection());
    }
    /**
     * Object: 采购申请单 's 分录 property 
     */
    public com.kingdee.eas.custom.bill.PuReqEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.bill.PuReqEntryCollection)get("entrys");
    }
    /**
     * Object:采购申请单's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 采购申请单 's 采购组织 property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getPurchaseOrg()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("purchaseOrg");
    }
    public void setPurchaseOrg(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("purchaseOrg", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CED62FD");
    }
}