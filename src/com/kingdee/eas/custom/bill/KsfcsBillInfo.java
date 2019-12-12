package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class KsfcsBillInfo extends AbstractKsfcsBillInfo implements Serializable 
{
    public KsfcsBillInfo()
    {
        super();
    }
    protected KsfcsBillInfo(String pkField)
    {
        super(pkField);
    }
}