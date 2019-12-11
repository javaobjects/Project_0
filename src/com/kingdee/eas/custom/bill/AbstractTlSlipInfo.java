package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTlSlipInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractTlSlipInfo()
    {
        this("id");
    }
    protected AbstractTlSlipInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.custom.bill.TlSlipEntryCollection());
    }
    /**
     * Object: ����� 's ���ݷ�¼ property 
     */
    public com.kingdee.eas.custom.bill.TlSlipEntryCollection getEntries()
    {
        return (com.kingdee.eas.custom.bill.TlSlipEntryCollection)get("entries");
    }
    /**
     * Object: ����� 's ��ǰ��֯ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:�����'s �����̶�property 
     */
    public com.kingdee.eas.cp.taskmng.instancyDegree getInstancy()
    {
        return com.kingdee.eas.cp.taskmng.instancyDegree.getEnum(getString("instancy"));
    }
    public void setInstancy(com.kingdee.eas.cp.taskmng.instancyDegree item)
    {
		if (item != null) {
        setString("instancy", item.getValue());
		}
    }
    /**
     * Object:�����'s ���ʽproperty 
     */
    public com.kingdee.eas.scm.sm.pur.PaymentTypeEnum getPayType()
    {
        return com.kingdee.eas.scm.sm.pur.PaymentTypeEnum.getEnum(getString("payType"));
    }
    public void setPayType(com.kingdee.eas.scm.sm.pur.PaymentTypeEnum item)
    {
		if (item != null) {
        setString("payType", item.getValue());
		}
    }
    /**
     * Object:�����'s ����property 
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
     * Object:�����'s ����property 
     */
    public java.math.BigDecimal getExchageRate()
    {
        return getBigDecimal("exchageRate");
    }
    public void setExchageRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchageRate", item);
    }
    /**
     * Object:�����'s �Ƶ�����property 
     */
    public java.util.Date getCreateDate()
    {
        return getDate("createDate");
    }
    public void setCreateDate(java.util.Date item)
    {
        setDate("createDate", item);
    }
    /**
     * Object:�����'s �޸�����property 
     */
    public java.util.Date getLastUpdateDate()
    {
        return getDate("lastUpdateDate");
    }
    public void setLastUpdateDate(java.util.Date item)
    {
        setDate("lastUpdateDate", item);
    }
    /**
     * Object:�����'s �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("371419B4");
    }
}