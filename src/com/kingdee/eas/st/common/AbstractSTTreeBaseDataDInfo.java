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
     * Object:������������D��'s �Ѻ�׼property 
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
     * Object:������������D��'s ��׼����property 
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
     * Object: ������������D�� 's ��׼�� property 
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