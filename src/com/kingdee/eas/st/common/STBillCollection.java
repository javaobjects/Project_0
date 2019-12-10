package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBillCollection extends AbstractObjectCollection 
{
    public STBillCollection()
    {
        super(STBillInfo.class);
    }
    public boolean add(STBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBillInfo item)
    {
        return removeObject(item);
    }
    public STBillInfo get(int index)
    {
        return(STBillInfo)getObject(index);
    }
    public STBillInfo get(Object key)
    {
        return(STBillInfo)getObject(key);
    }
    public void set(int index, STBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBillInfo item)
    {
        return super.indexOf(item);
    }
}