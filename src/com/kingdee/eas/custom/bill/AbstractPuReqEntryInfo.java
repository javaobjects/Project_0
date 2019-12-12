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
     * Object: ��¼ 's ����ͷ property 
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
     * Object: ��¼ 's ���ϱ��� property 
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
     * Object:��¼'s ����ͺ�property 
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
     * Object:��¼'s ��������property 
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
     * Object:��¼'s ��������property 
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
     * Object:��¼'s �Ѷ�������property 
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
     * Object:��¼'s ����ɹ�����property 
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
     * Object:��¼'s �ܽ��property 
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
     * Object:��¼'s ��������property 
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