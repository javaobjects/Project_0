package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class PuReqEntryInfo extends AbstractPuReqEntryInfo implements Serializable 
{
    public PuReqEntryInfo()
    {
        super();
    }
    protected PuReqEntryInfo(String pkField)
    {
        super(pkField);
    }
}