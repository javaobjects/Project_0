package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTtaskListEntryInfo extends com.kingdee.eas.st.common.STBillBaseEntryInfo implements Serializable 
{
    public AbstractTtaskListEntryInfo()
    {
        this("id");
    }
    protected AbstractTtaskListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 单据分录 's 模板单头 property 
     */
    public com.kingdee.eas.custom.bill.TtaskListInfo getParent()
    {
        return (com.kingdee.eas.custom.bill.TtaskListInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.bill.TtaskListInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:单据分录's 出差人property 
     */
    public String getPersonnel()
    {
        return getString("personnel");
    }
    public void setPersonnel(String item)
    {
        setString("personnel", item);
    }
    /**
     * Object:单据分录's 目标地property 
     */
    public String getDestination()
    {
        return getString("destination");
    }
    public void setDestination(String item)
    {
        setString("destination", item);
    }
    /**
     * Object:单据分录's 起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:单据分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:单据分录's 交通工具property 
     */
    public java.util.Date getVehicle()
    {
        return getDate("vehicle");
    }
    public void setVehicle(java.util.Date item)
    {
        setDate("vehicle", item);
    }
    /**
     * Object:单据分录's 预计费用property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FBC6D457");
    }
}