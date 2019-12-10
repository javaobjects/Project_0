package com.kingdee.eas.st.common.template;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public abstract class AbstractSTBillTemplateInfo extends com.kingdee.eas.st.common.STBillBaseInfo implements Serializable 
{
    public AbstractSTBillTemplateInfo()
    {
        this("id");
    }
    protected AbstractSTBillTemplateInfo(String pkField)
    {
        super(pkField);
        put("entries", new com.kingdee.eas.st.common.template.STBillTemplateEntryCollection());
    }
    /**
     * Object: ��������ģ�� 's ���ݷ�¼ property 
     */
    public com.kingdee.eas.st.common.template.STBillTemplateEntryCollection getEntries()
    {
        return (com.kingdee.eas.st.common.template.STBillTemplateEntryCollection)get("entries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0F04DD08");
    }
}