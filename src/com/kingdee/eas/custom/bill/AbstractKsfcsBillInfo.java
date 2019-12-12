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
     * Object: 关键服务费用确认单 's 单据分录 property 
     */
    public com.kingdee.eas.custom.bill.KsfcsBillEntryCollection getEntries()
    {
        return (com.kingdee.eas.custom.bill.KsfcsBillEntryCollection)get("entries");
    }
    /**
     * Object: 关键服务费用确认单 's 财务组织 property 
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
     * Object: 关键服务费用确认单 's 公司 property 
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
     * Object:关键服务费用确认单's 物业项目property 
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
     * Object:关键服务费用确认单's 物业项目编码property 
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
     * Object:关键服务费用确认单's 服务供方property 
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
     * Object:关键服务费用确认单's 单据状态property 
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
     * Object:关键服务费用确认单's 合同编号property 
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
     * Object:关键服务费用确认单's 合同总金额property 
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
     * Object:关键服务费用确认单's 币别property 
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
     * Object:关键服务费用确认单's 乙方违约事项描述property 
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
     * Object:关键服务费用确认单's 乙方违约金property 
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
     * Object:关键服务费用确认单's 甲方违约事项描述property 
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
     * Object:关键服务费用确认单's 甲方违约金property 
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
     * Object:关键服务费用确认单's 备注property 
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
     * Object:关键服务费用确认单's 税率property 
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
     * Object:关键服务费用确认单's 合同本期应付金额property 
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
     * Object:关键服务费用确认单's 争议金额property 
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
     * Object:关键服务费用确认单's 本期确认应付金额property 
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
     * Object:关键服务费用确认单's 计划支付日期property 
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
     * Object: 关键服务费用确认单 's 费用项目 property 
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
     * Object:关键服务费用确认单's 来源系统property 
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
     * Object:关键服务费用确认单's 总金额property 
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
     * Object:关键服务费用确认单's 总税额property 
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
     * Object:关键服务费用确认单's 总不含税金额property 
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
     * Object:关键服务费用确认单's 待开票金额property 
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
     * Object:关键服务费用确认单's 已开票金额property 
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