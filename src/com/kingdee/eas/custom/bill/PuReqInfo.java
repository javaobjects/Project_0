package com.kingdee.eas.custom.bill;

import java.io.Serializable;

public class PuReqInfo extends AbstractPuReqInfo implements Serializable 
{
    public PuReqInfo()
    {
        super();
    }
    protected PuReqInfo(String pkField)
    {
        super(pkField);
    }
}