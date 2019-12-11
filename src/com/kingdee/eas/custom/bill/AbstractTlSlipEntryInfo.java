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
     * Object: ���ݷ�¼ 's ģ�嵥ͷ property 
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
     * Object:���ݷ�¼'s �����property 
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
     * Object:���ݷ�¼'s ��ʼ����property 
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
     * Object:���ݷ�¼'s ��������property 
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
     * Object:���ݷ�¼'s Ŀ�ĵص�property 
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
     * Object:���ݷ�¼'s ��ͨ����property 
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
     * Object:���ݷ�¼'s ��ͨ��property 
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
     * Object:���ݷ�¼'s ���ڽ�ͨ��property 
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
     * Object:���ݷ�¼'s ס�޷�property 
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
     * Object:���ݷ�¼'s ����property 
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
     * Object:���ݷ�¼'s �ϼ�property 
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