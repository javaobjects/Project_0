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
     * Object: �ɹ����뵥 's ��¼ property 
     */
    public com.kingdee.eas.custom.bill.PuReqEntryCollection getEntrys()
    {
        return (com.kingdee.eas.custom.bill.PuReqEntryCollection)get("entrys");
    }
    /**
     * Object:�ɹ����뵥's �Ƿ�����ƾ֤property 
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
     * Object: �ɹ����뵥 's �ɹ���֯ property 
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
     * Object:�ɹ����뵥's ��Ӧ��property 
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
     * Object:�ɹ����뵥's ����property 
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
     * Object: �ɹ����뵥 's ������ property 
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
     * Object:�ɹ����뵥's ����ʱ��property 
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
     * Object:�ɹ����뵥's ״̬property 
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