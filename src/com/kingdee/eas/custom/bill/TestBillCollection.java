package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestBillCollection extends AbstractObjectCollection 
{
    public TestBillCollection()
    {
        super(TestBillInfo.class);
    }
    public boolean add(TestBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestBillInfo item)
    {
        return removeObject(item);
    }
    public TestBillInfo get(int index)
    {
        return(TestBillInfo)getObject(index);
    }
    public TestBillInfo get(Object key)
    {
        return(TestBillInfo)getObject(key);
    }
    public void set(int index, TestBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TestBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestBillInfo item)
    {
        return super.indexOf(item);
    }
}