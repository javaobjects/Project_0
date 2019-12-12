package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class KsfcsBillEntryInfo extends AbstractKsfcsBillEntryInfo implements Serializable 
{
    public KsfcsBillEntryInfo()
    {
        super();
    }
    protected KsfcsBillEntryInfo(String pkField)
    {
        super(pkField);
    }
}