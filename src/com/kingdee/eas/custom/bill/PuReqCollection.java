package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PuReqCollection extends AbstractObjectCollection 
{
    public PuReqCollection()
    {
        super(PuReqInfo.class);
    }
    public boolean add(PuReqInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PuReqCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PuReqInfo item)
    {
        return removeObject(item);
    }
    public PuReqInfo get(int index)
    {
        return(PuReqInfo)getObject(index);
    }
    public PuReqInfo get(Object key)
    {
        return(PuReqInfo)getObject(key);
    }
    public void set(int index, PuReqInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PuReqInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PuReqInfo item)
    {
        return super.indexOf(item);
    }
}