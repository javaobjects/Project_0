package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TestBillEntryE2Collection extends AbstractObjectCollection 
{
    public TestBillEntryE2Collection()
    {
        super(TestBillEntryE2Info.class);
    }
    public boolean add(TestBillEntryE2Info item)
    {
        return addObject(item);
    }
    public boolean addCollection(TestBillEntryE2Collection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TestBillEntryE2Info item)
    {
        return removeObject(item);
    }
    public TestBillEntryE2Info get(int index)
    {
        return(TestBillEntryE2Info)getObject(index);
    }
    public TestBillEntryE2Info get(Object key)
    {
        return(TestBillEntryE2Info)getObject(key);
    }
    public void set(int index, TestBillEntryE2Info item)
    {
        setObject(index, item);
    }
    public boolean contains(TestBillEntryE2Info item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TestBillEntryE2Info item)
    {
        return super.indexOf(item);
    }
}