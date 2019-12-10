package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractSTBillInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractSTBillInfo()
    {
        this("id");
    }
    protected AbstractSTBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ҵ�񵥾ݻ���'s ��Դ����property 
     */
    public String getSourceBizBillId()
    {
        return getString("sourceBizBillId");
    }
    public void setSourceBizBillId(String item)
    {
        setString("sourceBizBillId", item);
    }
    /**
     * Object:����ҵ�񵥾ݻ���'s ��Դ���ݷ�¼Idproperty 
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
     * Object:����ҵ�񵥾ݻ���'s ��Դ���ݱ��property 
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
     * Object: ����ҵ�񵥾ݻ��� 's ��Դ�������� property 
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
     * Object:����ҵ�񵥾ݻ���'s ���ĵ���IDproperty 
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
     * Object:����ҵ�񵥾ݻ���'s ���ĵ��ݷ�¼Idproperty 
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
     * Object:����ҵ�񵥾ݻ���'s ���ĵ��ݱ��property 
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
     * Object: ����ҵ�񵥾ݻ��� 's ���ĵ������� property 
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
     * Object:����ҵ�񵥾ݻ���'s ��ͬIdproperty 
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
     * Object:����ҵ�񵥾ݻ���'s ��ͬ��¼Idproperty 
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
     * Object:����ҵ�񵥾ݻ���'s ��ͬ���property 
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
     * Object: ����ҵ�񵥾ݻ��� 's ��ͬ���� property 
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
        return new BOSObjectType("6AE7DAD0");
    }
}