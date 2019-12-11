package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class TlSlipEntryInfo extends AbstractTlSlipEntryInfo implements Serializable 
{
    public TlSlipEntryInfo()
    {
        super();
    }
    protected TlSlipEntryInfo(String pkField)
    {
        super(pkField);
    }
}