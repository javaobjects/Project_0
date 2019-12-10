package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSTTreeBaseDataDInfo extends com.kingdee.eas.basedata.framework.DataBaseDInfo implements Serializable 
{
    public AbstractSTTreeBaseDataDInfo()
    {
        this("id");
    }
    protected AbstractSTTreeBaseDataDInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:左树基础资料D类's 已核准property 
     */
    public boolean isIsAudited()
    {
        return getBoolean("isAudited");
    }
    public void setIsAudited(boolean item)
    {
        setBoolean("isAudited", item);
    }
    /**
     * Object:左树基础资料D类's 核准日期property 
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
     * Object: 左树基础资料D类 's 核准人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAuditUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("auditUser");
    }
    public void setAuditUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("auditUser", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("42D462E2");
    }
}