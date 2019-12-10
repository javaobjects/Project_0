package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractSTBillBaseInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSTBillBaseInfo()
    {
        this("id");
    }
    protected AbstractSTBillBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:钢铁单据基类's 审核时间property 
     */
    public java.sql.Timestamp getAuditTime()
    {
        return getTimestamp("auditTime");
    }
    public void setAuditTime(java.sql.Timestamp item)
    {
        setTimestamp("auditTime", item);
    }
    /**
     * Object:钢铁单据基类's 单据状态property 
     */
    public com.kingdee.eas.scm.common.BillBaseStatusEnum getBillStatus()
    {
        return com.kingdee.eas.scm.common.BillBaseStatusEnum.getEnum(getInt("billStatus"));
    }
    public void setBillStatus(com.kingdee.eas.scm.common.BillBaseStatusEnum item)
    {
		if (item != null) {
        setInt("billStatus", item.getValue());
		}
    }
    /**
     * Object: 钢铁单据基类 's 单据类型 property 
     */
    public com.kingdee.eas.basedata.scm.common.BillTypeInfo getBillType()
    {
        return (com.kingdee.eas.basedata.scm.common.BillTypeInfo)get("billType");
    }
    public void setBillType(com.kingdee.eas.basedata.scm.common.BillTypeInfo item)
    {
        put("billType", item);
    }
    /**
     * Object:钢铁单据基类's 是否BOTP单据property 
     */
    public boolean isIsFromBOTP()
    {
        return getBoolean("isFromBOTP");
    }
    public void setIsFromBOTP(boolean item)
    {
        setBoolean("isFromBOTP", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1CB26273");
    }
}