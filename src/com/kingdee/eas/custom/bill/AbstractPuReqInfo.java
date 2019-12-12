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
    /**
     * Object:采购申请单's 供应商property 
     */
    public String getSupplier()
    {
        return getString("supplier");
    }
    public void setSupplier(String item)
    {
        setString("supplier", item);
    }
    /**
     * Object:采购申请单's 币种property 
     */
    public String getCurrency()
    {
        return getString("currency");
    }
    public void setCurrency(String item)
    {
        setString("currency", item);
    }
    /**
     * Object: 采购申请单 's 申请人 property 
     */
    public com.kingdee.eas.base.ssc.UserInfo getApplicant()
    {
        return (com.kingdee.eas.base.ssc.UserInfo)get("applicant");
    }
    public void setApplicant(com.kingdee.eas.base.ssc.UserInfo item)
    {
        put("applicant", item);
    }
    /**
     * Object:采购申请单's 申请时间property 
     */
    public java.util.Date getApplicationTime()
    {
        return getDate("applicationTime");
    }
    public void setApplicationTime(java.util.Date item)
    {
        setDate("applicationTime", item);
    }
    /**
     * Object:采购申请单's 状态property 
     */
    public String getStatus()
    {
        return getString("status");
    }
    public void setStatus(String item)
    {
        setString("status", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CED62FD");
    }
}