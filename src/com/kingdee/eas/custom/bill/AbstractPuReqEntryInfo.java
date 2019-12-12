package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPuReqEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPuReqEntryInfo()
    {
        this("id");
    }
    protected AbstractPuReqEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.custom.bill.PuReqInfo getParent()
    {
        return (com.kingdee.eas.custom.bill.PuReqInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.custom.bill.PuReqInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 分录 's 物料编码 property 
     */
    public com.kingdee.eas.custom.basedata.NewMaterialInfo getMaterialNumber()
    {
        return (com.kingdee.eas.custom.basedata.NewMaterialInfo)get("materialNumber");
    }
    public void setMaterialNumber(com.kingdee.eas.custom.basedata.NewMaterialInfo item)
    {
        put("materialNumber", item);
    }
    /**
     * Object:分录's 规格型号property 
     */
    public String getModel()
    {
        return getString("model");
    }
    public void setModel(String item)
    {
        setString("model", item);
    }
    /**
     * Object:分录's 申请数量property 
     */
    public java.math.BigDecimal getRCount()
    {
        return getBigDecimal("RCount");
    }
    public void setRCount(java.math.BigDecimal item)
    {
        setBigDecimal("RCount", item);
    }
    /**
     * Object:分录's 物料名称property 
     */
    public String getMeteriaName()
    {
        return getString("meteriaName");
    }
    public void setMeteriaName(String item)
    {
        setString("meteriaName", item);
    }
    /**
     * Object:分录's 已订货数量property 
     */
    public java.math.BigDecimal getARCount()
    {
        return getBigDecimal("ARCount");
    }
    public void setARCount(java.math.BigDecimal item)
    {
        setBigDecimal("ARCount", item);
    }
    /**
     * Object:分录's 建议采购单价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:分录's 总金额property 
     */
    public java.math.BigDecimal getSum()
    {
        return getBigDecimal("sum");
    }
    public void setSum(java.math.BigDecimal item)
    {
        setBigDecimal("sum", item);
    }
    /**
     * Object:分录's 需求日期property 
     */
    public java.util.Date getRdata()
    {
        return getDate("Rdata");
    }
    public void setRdata(java.util.Date item)
    {
        setDate("Rdata", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8DDA0B75");
    }
}