package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBillBaseEntryCollection extends AbstractObjectCollection 
{
    public STBillBaseEntryCollection()
    {
        super(STBillBaseEntryInfo.class);
    }
    public boolean add(STBillBaseEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBillBaseEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBillBaseEntryInfo item)
    {
        return removeObject(item);
    }
    public STBillBaseEntryInfo get(int index)
    {
        return(STBillBaseEntryInfo)getObject(index);
    }
    public STBillBaseEntryInfo get(Object key)
    {
        return(STBillBaseEntryInfo)getObject(key);
    }
    public void set(int index, STBillBaseEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBillBaseEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBillBaseEntryInfo item)
    {
        return super.indexOf(item);
    }
}