package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class TlSlipInfo extends AbstractTlSlipInfo implements Serializable 
{
    public TlSlipInfo()
    {
        super();
    }
    protected TlSlipInfo(String pkField)
    {
        super(pkField);
    }
}