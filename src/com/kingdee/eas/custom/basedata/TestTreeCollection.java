package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestTreeCollection extends AbstractObjectCollection 
{
    public TestTreeCollection()
    {
        super(TestTreeInfo.class);
    }
    public boolean add(TestTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestTreeInfo item)
    {
        return removeObject(item);
    }
    public TestTreeInfo get(int index)
    {
        return(TestTreeInfo)getObject(index);
    }
    public TestTreeInfo get(Object key)
    {
        return(TestTreeInfo)getObject(key);
    }
    public void set(int index, TestTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TestTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestTreeInfo item)
    {
        return super.indexOf(item);
    }
}