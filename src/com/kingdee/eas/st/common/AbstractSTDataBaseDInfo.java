package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSTDataBaseDInfo extends com.kingdee.eas.basedata.framework.DataBaseDInfo implements Serializable 
{
    public AbstractSTDataBaseDInfo()
    {
        this("id");
    }
    protected AbstractSTDataBaseDInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:钢铁行业基础资料's 是否启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:钢铁行业基础资料's 是否被核准property 
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
     * Object:钢铁行业基础资料's 核准时间property 
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
     * Object: 钢铁行业基础资料 's 核准人 property 
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
        return new BOSObjectType("329C4CE0");
    }
}