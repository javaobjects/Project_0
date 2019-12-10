package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestCollection extends AbstractObjectCollection 
{
    public TestCollection()
    {
        super(TestInfo.class);
    }
    public boolean add(TestInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestInfo item)
    {
        return removeObject(item);
    }
    public TestInfo get(int index)
    {
        return(TestInfo)getObject(index);
    }
    public TestInfo get(Object key)
    {
        return(TestInfo)getObject(key);
    }
    public void set(int index, TestInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TestInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestInfo item)
    {
        return super.indexOf(item);
    }
}