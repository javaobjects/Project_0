package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestBillEntryCollection extends AbstractObjectCollection 
{
    public TestBillEntryCollection()
    {
        super(TestBillEntryInfo.class);
    }
    public boolean add(TestBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestBillEntryInfo item)
    {
        return removeObject(item);
    }
    public TestBillEntryInfo get(int index)
    {
        return(TestBillEntryInfo)getObject(index);
    }
    public TestBillEntryInfo get(Object key)
    {
        return(TestBillEntryInfo)getObject(key);
    }
    public void set(int index, TestBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TestBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}