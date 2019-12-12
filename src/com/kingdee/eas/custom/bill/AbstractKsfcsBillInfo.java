package com.kingdee.eas.custom.bill;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractKsfcsBillInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractKsfcsBillInfo()
    {
        this("id");
    }
    protected AbstractKsfcsBillInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.custom.bill.KsfcsBillEntryCollection());
    }
    /**
     * Object: �ؼ��������ȷ�ϵ� 's ���ݷ�¼ property 
     */
    public com.kingdee.eas.custom.bill.KsfcsBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.custom.bill.KsfcsBillEntryCollection)get("entries");
    }
    /**
     * Object: �ؼ��������ȷ�ϵ� 's ������֯ property 
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
     * Object: �ؼ��������ȷ�ϵ� 's ��˾ property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��ҵ��Ŀproperty 
     */
    public String getPropertyName()
    {
        return getString("propertyName");
    }
    public void setPropertyName(String item)
    {
        setString("propertyName", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��ҵ��Ŀ����property 
     */
    public String getPropertyNumber()
    {
        return getString("propertyNumber");
    }
    public void setPropertyNumber(String item)
    {
        setString("propertyNumber", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ���񹩷�property 
     */
    public String getSupplier()
    {
        return getString("supplier");
    }
    public void setSupplier(String item)
    {
        setString("supplier", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ����״̬property 
     */
    public com.kingdee.eas.fm.fin.BillStatusEnum getStatus()
    {
        return com.kingdee.eas.fm.fin.BillStatusEnum.getEnum(getString("Status"));
    }
    public void setStatus(com.kingdee.eas.fm.fin.BillStatusEnum item)
    {
		if (item != null) {
        setString("Status", item.getValue());
		}
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��ͬ���property 
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
     * Object:�ؼ��������ȷ�ϵ�'s ��ͬ�ܽ��property 
     */
    public java.math.BigDecimal getContractSumCost()
    {
        return getBigDecimal("contractSumCost");
    }
    public void setContractSumCost(java.math.BigDecimal item)
    {
        setBigDecimal("contractSumCost", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ұ�property 
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
     * Object:�ؼ��������ȷ�ϵ�'s �ҷ�ΥԼ��������property 
     */
    public String getBDefaultTxt()
    {
        return getString("bDefaultTxt");
    }
    public void setBDefaultTxt(String item)
    {
        setString("bDefaultTxt", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ҷ�ΥԼ��property 
     */
    public java.math.BigDecimal getBDefaultMoney()
    {
        return getBigDecimal("bDefaultMoney");
    }
    public void setBDefaultMoney(java.math.BigDecimal item)
    {
        setBigDecimal("bDefaultMoney", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �׷�ΥԼ��������property 
     */
    public String getADefaultTxt()
    {
        return getString("aDefaultTxt");
    }
    public void setADefaultTxt(String item)
    {
        setString("aDefaultTxt", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �׷�ΥԼ��property 
     */
    public java.math.BigDecimal getADefaultMoney()
    {
        return getBigDecimal("aDefaultMoney");
    }
    public void setADefaultMoney(java.math.BigDecimal item)
    {
        setBigDecimal("aDefaultMoney", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��עproperty 
     */
    public String getRemarks()
    {
        return getString("remarks");
    }
    public void setRemarks(String item)
    {
        setString("remarks", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ˰��property 
     */
    public java.math.BigDecimal getTaxRate()
    {
        return getBigDecimal("taxRate");
    }
    public void setTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("taxRate", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��ͬ����Ӧ�����property 
     */
    public java.math.BigDecimal getAmountPayable()
    {
        return getBigDecimal("amountPayable");
    }
    public void setAmountPayable(java.math.BigDecimal item)
    {
        setBigDecimal("amountPayable", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ������property 
     */
    public java.math.BigDecimal getAmountContentious()
    {
        return getBigDecimal("amountContentious");
    }
    public void setAmountContentious(java.math.BigDecimal item)
    {
        setBigDecimal("amountContentious", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ����ȷ��Ӧ�����property 
     */
    public java.math.BigDecimal getAmountConfirmation()
    {
        return getBigDecimal("amountConfirmation");
    }
    public void setAmountConfirmation(java.math.BigDecimal item)
    {
        setBigDecimal("amountConfirmation", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ƻ�֧������property 
     */
    public java.util.Date getDueDate()
    {
        return getDate("dueDate");
    }
    public void setDueDate(java.util.Date item)
    {
        setDate("dueDate", item);
    }
    /**
     * Object: �ؼ��������ȷ�ϵ� 's ������Ŀ property 
     */
    public com.kingdee.eas.basedata.scm.common.ExpenseItemInfo getCostItem()
    {
        return (com.kingdee.eas.basedata.scm.common.ExpenseItemInfo)get("costItem");
    }
    public void setCostItem(com.kingdee.eas.basedata.scm.common.ExpenseItemInfo item)
    {
        put("costItem", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��Դϵͳproperty 
     */
    public String getSourceSystem()
    {
        return getString("sourceSystem");
    }
    public void setSourceSystem(String item)
    {
        setString("sourceSystem", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ܽ��property 
     */
    public java.math.BigDecimal getTotalSum()
    {
        return getBigDecimal("totalSum");
    }
    public void setTotalSum(java.math.BigDecimal item)
    {
        setBigDecimal("totalSum", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ��˰��property 
     */
    public java.math.BigDecimal getTotalTax()
    {
        return getBigDecimal("totalTax");
    }
    public void setTotalTax(java.math.BigDecimal item)
    {
        setBigDecimal("totalTax", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ܲ���˰���property 
     */
    public java.math.BigDecimal getTotalNoTax()
    {
        return getBigDecimal("totalNoTax");
    }
    public void setTotalNoTax(java.math.BigDecimal item)
    {
        setBigDecimal("totalNoTax", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s ����Ʊ���property 
     */
    public java.math.BigDecimal getNoInvoiceSum()
    {
        return getBigDecimal("noInvoiceSum");
    }
    public void setNoInvoiceSum(java.math.BigDecimal item)
    {
        setBigDecimal("noInvoiceSum", item);
    }
    /**
     * Object:�ؼ��������ȷ�ϵ�'s �ѿ�Ʊ���property 
     */
    public java.math.BigDecimal getYesInvoiceSum()
    {
        return getBigDecimal("YesInvoiceSum");
    }
    public void setYesInvoiceSum(java.math.BigDecimal item)
    {
        setBigDecimal("YesInvoiceSum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0B0FCEB9");
    }
}