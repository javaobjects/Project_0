package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTlSlipEntryInfo extends com.kingdee.eas.st.common.STBillBaseEntryInfo implements Serializable 
{
    public AbstractTlSlipEntryInfo()
    {
        this("id");
    }
    protected AbstractTlSlipEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 单据分录 's 模板单头 property 
     */
    public com.kingdee.eas.custom.bill.TlSlipInfo getParent()
    {
        return (com.kingdee.eas.custom.bill.TlSlipInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.bill.TlSlipInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:单据分录's 借款人property 
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
     * Object:单据分录's 目的地点property 
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
     * Object:单据分录's 交通工具property 
     */
    public int getVehicle()
    {
        return getInt("vehicle");
    }
    public void setVehicle(int item)
    {
        setInt("vehicle", item);
    }
    /**
     * Object:单据分录's 交通费property 
     */
    public java.math.BigDecimal getTrafficFee1()
    {
        return getBigDecimal("trafficFee1");
    }
    public void setTrafficFee1(java.math.BigDecimal item)
    {
        setBigDecimal("trafficFee1", item);
    }
    /**
     * Object:单据分录's 市内交通费property 
     */
    public java.math.BigDecimal getTrafficFee2()
    {
        return getBigDecimal("trafficFee2");
    }
    public void setTrafficFee2(java.math.BigDecimal item)
    {
        setBigDecimal("trafficFee2", item);
    }
    /**
     * Object:单据分录's 住宿费property 
     */
    public java.math.BigDecimal getQuarterFee()
    {
        return getBigDecimal("quarterFee");
    }
    public void setQuarterFee(java.math.BigDecimal item)
    {
        setBigDecimal("quarterFee", item);
    }
    /**
     * Object:单据分录's 其他property 
     */
    public int getOtherFee()
    {
        return getInt("otherFee");
    }
    public void setOtherFee(int item)
    {
        setInt("otherFee", item);
    }
    /**
     * Object:单据分录's 合计property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5BBEF1E");
    }
}