package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBillBaseCollection extends AbstractObjectCollection 
{
    public STBillBaseCollection()
    {
        super(STBillBaseInfo.class);
    }
    public boolean add(STBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBillBaseInfo item)
    {
        return removeObject(item);
    }
    public STBillBaseInfo get(int index)
    {
        return(STBillBaseInfo)getObject(index);
    }
    public STBillBaseInfo get(Object key)
    {
        return(STBillBaseInfo)getObject(key);
    }
    public void set(int index, STBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}