package com.kingdee.eas.custom.basedata;

import java.io.Serializable;

public class NewMaterialInfo extends AbstractNewMaterialInfo implements Serializable 
{
    public NewMaterialInfo()
    {
        super();
    }
    protected NewMaterialInfo(String pkField)
    {
        super(pkField);
    }
}