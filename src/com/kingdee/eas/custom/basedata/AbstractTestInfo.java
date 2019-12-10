package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTestInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractTestInfo()
    {
        this("id");
    }
    protected AbstractTestInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �༶��������ģ������ 's ��� property 
     */
    public com.kingdee.eas.custom.basedata.TestTreeInfo getTreeid()
    {
        return (com.kingdee.eas.custom.basedata.TestTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.custom.basedata.TestTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:�༶��������ģ������'s �Ƿ�����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object: �༶��������ģ������ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCancelCancelUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("cancelCancelUser");
    }
    public void setCancelCancelUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("cancelCancelUser", item);
    }
    /**
     * Object:�༶��������ģ������'s ��������property 
     */
    public java.util.Date getCancelCancelDate()
    {
        return getDate("cancelCancelDate");
    }
    public void setCancelCancelDate(java.util.Date item)
    {
        setDate("cancelCancelDate", item);
    }
    /**
     * Object: �༶��������ģ������ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCancelUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("cancelUser");
    }
    public void setCancelUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("cancelUser", item);
    }
    /**
     * Object:�༶��������ģ������'s ��������property 
     */
    public java.util.Date getCancelDate()
    {
        return getDate("cancelDate");
    }
    public void setCancelDate(java.util.Date item)
    {
        setDate("cancelDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("98CEE1FA");
    }
}