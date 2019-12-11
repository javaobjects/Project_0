package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTtaskListInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractTtaskListInfo()
    {
        this("id");
    }
    protected AbstractTtaskListInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.custom.bill.TtaskListEntryCollection());
    }
    /**
     * Object: 出差任务单 's 单据分录 property 
     */
    public com.kingdee.eas.custom.bill.TtaskListEntryCollection getEntries()
    {
        return (com.kingdee.eas.custom.bill.TtaskListEntryCollection)get("entries");
    }
    /**
     * Object: 出差任务单 's 财务组织 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getFICompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("FICompany");
    }
    public void setFICompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("FICompany", item);
    }
    /**
     * Object:出差任务单's 当前组织property 
     */
    public String getCompany()
    {
        return getString("company");
    }
    public void setCompany(String item)
    {
        setString("company", item);
    }
    /**
     * Object:出差任务单's 币种property 
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
     * Object:出差任务单's 汇率property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:出差任务单's 紧急程度property 
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
     * Object:出差任务单's 制单日期property 
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
     * Object:出差任务单's 修改日期property 
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
     * Object:出差任务单's 审核日期property 
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
        return new BOSObjectType("BBBB14DB");
    }
}