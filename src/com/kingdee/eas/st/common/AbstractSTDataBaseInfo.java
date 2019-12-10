package com.kingdee.eas.st.common;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSTDataBaseInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSTDataBaseInfo()
    {
        this("id");
    }
    protected AbstractSTDataBaseInfo(String pkField)
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E099AFE4");
    }
}