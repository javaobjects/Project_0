package com.kingdee.eas.custom.basedata;

import java.io.Serializable;

public class TestTreeInfo extends AbstractTestTreeInfo implements Serializable 
{
    public TestTreeInfo()
    {
        super();
    }
    protected TestTreeInfo(String pkField)
    {
        super(pkField);
    }
}