package com.kingdee.eas.custom.basedata;

import java.io.Serializable;

public class TestInfo extends AbstractTestInfo implements Serializable 
{
    public TestInfo()
    {
        super();
    }
    protected TestInfo(String pkField)
    {
        super(pkField);
    }
}