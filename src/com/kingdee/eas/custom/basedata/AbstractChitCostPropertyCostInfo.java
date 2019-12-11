package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChitCostPropertyCostInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChitCostPropertyCostInfo()
    {
        this("id");
    }
    protected AbstractChitCostPropertyCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 收费项目成本单据体 's null property 
     */
    public com.kingdee.eas.custom.basedata.ChitCostInfo getParent()
    {
        return (com.kingdee.eas.custom.basedata.ChitCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.basedata.ChitCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:收费项目成本单据体's 物业项目编码property 
     */
    public String getProjectCode()
    {
        return getString("projectCode");
    }
    public void setProjectCode(String item)
    {
        setString("projectCode", item);
    }
    /**
     * Object:收费项目成本单据体's 物业项目名称property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:收费项目成本单据体's 变动成本property 
     */
    public java.math.BigDecimal getChangeCost()
    {
        return getBigDecimal("changeCost");
    }
    public void setChangeCost(java.math.BigDecimal item)
    {
        setBigDecimal("changeCost", item);
    }
    /**
     * Object:收费项目成本单据体's 固定成本property 
     */
    public java.math.BigDecimal getFixedCost()
    {
        return getBigDecimal("fixedCost");
    }
    public void setFixedCost(java.math.BigDecimal item)
    {
        setBigDecimal("fixedCost", item);
    }
    /**
     * Object:收费项目成本单据体's 其他成本property 
     */
    public java.math.BigDecimal getOtherCost()
    {
        return getBigDecimal("otherCost");
    }
    public void setOtherCost(java.math.BigDecimal item)
    {
        setBigDecimal("otherCost", item);
    }
    /**
     * Object:收费项目成本单据体's 总成本property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:收费项目成本单据体's 状态property 
     */
    public com.kingdee.eas.base.ssc.StatusEnum getState()
    {
        return com.kingdee.eas.base.ssc.StatusEnum.getEnum(getInt("state"));
    }
    public void setState(com.kingdee.eas.base.ssc.StatusEnum item)
    {
		if (item != null) {
        setInt("state", item.getValue());
		}
    }
    /**
     * Object:收费项目成本单据体's 生效日期property 
     */
    public java.util.Date getEffectiveDate()
    {
        return getDate("effectiveDate");
    }
    public void setEffectiveDate(java.util.Date item)
    {
        setDate("effectiveDate", item);
    }
    /**
     * Object:收费项目成本单据体's 失效日期property 
     */
    public java.util.Date getExpiryDate()
    {
        return getDate("expiryDate");
    }
    public void setExpiryDate(java.util.Date item)
    {
        setDate("expiryDate", item);
    }
    /**
     * Object: 收费项目成本单据体 's 创建人 property 
     */
    public com.kingdee.eas.base.ssc.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.ssc.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.ssc.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object:收费项目成本单据体's 创建日期property 
     */
    public java.util.Date getCreateTime()
    {
        return getDate("createTime");
    }
    public void setCreateTime(java.util.Date item)
    {
        setDate("createTime", item);
    }
    /**
     * Object: 收费项目成本单据体 's 最后修改人 property 
     */
    public com.kingdee.eas.base.ssc.UserInfo getLastUpdateUser()
    {
        return (com.kingdee.eas.base.ssc.UserInfo)get("lastUpdateUser");
    }
    public void setLastUpdateUser(com.kingdee.eas.base.ssc.UserInfo item)
    {
        put("lastUpdateUser", item);
    }
    /**
     * Object:收费项目成本单据体's 最后修改日期property 
     */
    public java.util.Date getLastUpdateTime()
    {
        return getDate("lastUpdateTime");
    }
    public void setLastUpdateTime(java.util.Date item)
    {
        setDate("lastUpdateTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("07E9CD87");
    }
}