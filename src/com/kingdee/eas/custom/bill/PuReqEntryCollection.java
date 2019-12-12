package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PuReqEntryCollection extends AbstractObjectCollection 
{
    public PuReqEntryCollection()
    {
        super(PuReqEntryInfo.class);
    }
    public boolean add(PuReqEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PuReqEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PuReqEntryInfo item)
    {
        return removeObject(item);
    }
    public PuReqEntryInfo get(int index)
    {
        return(PuReqEntryInfo)getObject(index);
    }
    public PuReqEntryInfo get(Object key)
    {
        return(PuReqEntryInfo)getObject(key);
    }
    public void set(int index, PuReqEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PuReqEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PuReqEntryInfo item)
    {
        return super.indexOf(item);
    }
}