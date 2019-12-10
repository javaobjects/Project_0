package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractSTBillBaseEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSTBillBaseEntryInfo()
    {
        this("id");
    }
    protected AbstractSTBillBaseEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:钢铁单据体基类's 来源单据Idproperty 
     */
    public String getSourceBillId()
    {
        return getString("sourceBillId");
    }
    public void setSourceBillId(String item)
    {
        setString("sourceBillId", item);
    }
    /**
     * Object:钢铁单据体基类's 来源单据编码property 
     */
    public String getSourceBillNumber()
    {
        return getString("sourceBillNumber");
    }
    public void setSourceBillNumber(String item)
    {
        setString("sourceBillNumber", item);
    }
    /**
     * Object:钢铁单据体基类's 来源单据分录Idproperty 
     */
    public String getSourceBillEntryId()
    {
        return getString("sourceBillEntryId");
    }
    public void setSourceBillEntryId(String item)
    {
        setString("sourceBillEntryId", item);
    }
    /**
     * Object: 钢铁单据体基类 's 来源单据类型 property 
     */
    public com.kingdee.eas.basedata.scm.common.BillTypeInfo getSourceBillType()
    {
        return (com.kingdee.eas.basedata.scm.common.BillTypeInfo)get("sourceBillType");
    }
    public void setSourceBillType(com.kingdee.eas.basedata.scm.common.BillTypeInfo item)
    {
        put("sourceBillType", item);
    }
    /**
     * Object:钢铁单据体基类's 核心单据Idproperty 
     */
    public String getCoreBillId()
    {
        return getString("coreBillId");
    }
    public void setCoreBillId(String item)
    {
        setString("coreBillId", item);
    }
    /**
     * Object:钢铁单据体基类's 核心单据编码property 
     */
    public String getCoreBillNumber()
    {
        return getString("coreBillNumber");
    }
    public void setCoreBillNumber(String item)
    {
        setString("coreBillNumber", item);
    }
    /**
     * Object:钢铁单据体基类's 核心单据分录Idproperty 
     */
    public String getCoreBillEntryId()
    {
        return getString("coreBillEntryId");
    }
    public void setCoreBillEntryId(String item)
    {
        setString("coreBillEntryId", item);
    }
    /**
     * Object: 钢铁单据体基类 's 核心单据类型 property 
     */
    public com.kingdee.eas.basedata.scm.common.BillTypeInfo getCoreBillType()
    {
        return (com.kingdee.eas.basedata.scm.common.BillTypeInfo)get("coreBillType");
    }
    public void setCoreBillType(com.kingdee.eas.basedata.scm.common.BillTypeInfo item)
    {
        put("coreBillType", item);
    }
    /**
     * Object:钢铁单据体基类's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:钢铁单据体基类's 合同Idproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object:钢铁单据体基类's 合同分录Idproperty 
     */
    public String getContractEntryId()
    {
        return getString("contractEntryId");
    }
    public void setContractEntryId(String item)
    {
        setString("contractEntryId", item);
    }
    /**
     * Object:钢铁单据体基类's 合同编码property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object: 钢铁单据体基类 's 合同类型 property 
     */
    public com.kingdee.eas.basedata.scm.common.BillTypeInfo getContractType()
    {
        return (com.kingdee.eas.basedata.scm.common.BillTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.basedata.scm.common.BillTypeInfo item)
    {
        put("contractType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("05B845BF");
    }
}