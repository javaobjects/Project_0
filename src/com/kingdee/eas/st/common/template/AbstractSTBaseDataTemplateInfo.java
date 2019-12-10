package com.kingdee.eas.st.common.template;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSTBaseDataTemplateInfo extends com.kingdee.eas.st.common.STDataBaseInfo implements Serializable 
{
    public AbstractSTBaseDataTemplateInfo()
    {
        this("id");
    }
    protected AbstractSTBaseDataTemplateInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("892850FE");
    }
}