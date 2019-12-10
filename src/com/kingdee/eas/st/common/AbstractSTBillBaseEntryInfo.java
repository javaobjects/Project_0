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
     * Object:�������������'s ��Դ����Idproperty 
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
     * Object:�������������'s ��Դ���ݱ���property 
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
     * Object:�������������'s ��Դ���ݷ�¼Idproperty 
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
     * Object: ������������� 's ��Դ�������� property 
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
     * Object:�������������'s ���ĵ���Idproperty 
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
     * Object:�������������'s ���ĵ��ݱ���property 
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
     * Object:�������������'s ���ĵ��ݷ�¼Idproperty 
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
     * Object: ������������� 's ���ĵ������� property 
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
     * Object:�������������'s ��עproperty 
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
     * Object:�������������'s ��ͬIdproperty 
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
     * Object:�������������'s ��ͬ��¼Idproperty 
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
     * Object:�������������'s ��ͬ����property 
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
     * Object: ������������� 's ��ͬ���� property 
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