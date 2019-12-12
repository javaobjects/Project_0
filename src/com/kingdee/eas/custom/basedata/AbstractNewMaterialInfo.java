package com.kingdee.eas.custom.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewMaterialInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractNewMaterialInfo()
    {
        this("id");
    }
    protected AbstractNewMaterialInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½����� 's ��� property 
     */
    public com.kingdee.eas.custom.basedata.NewMaterialTreeInfo getTreeid()
    {
        return (com.kingdee.eas.custom.basedata.NewMaterialTreeInfo)get("treeid");
    }
    public void setTreeid(com.kingdee.eas.custom.basedata.NewMaterialTreeInfo item)
    {
        put("treeid", item);
    }
    /**
     * Object:�½�����'s ���Ϲ��property 
     */
    public String getMaterialModel()
    {
        return getString("materialModel");
    }
    public void setMaterialModel(String item)
    {
        setString("materialModel", item);
    }
    /**
     * Object:�½�����'s ���ϲ���property 
     */
    public String getMaterialCd()
    {
        return getString("materialCd");
    }
    public void setMaterialCd(String item)
    {
        setString("materialCd", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B522D07F");
    }
}