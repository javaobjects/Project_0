package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TtaskListEntryCollection extends AbstractObjectCollection 
{
    public TtaskListEntryCollection()
    {
        super(TtaskListEntryInfo.class);
    }
    public boolean add(TtaskListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TtaskListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TtaskListEntryInfo item)
    {
        return removeObject(item);
    }
    public TtaskListEntryInfo get(int index)
    {
        return(TtaskListEntryInfo)getObject(index);
    }
    public TtaskListEntryInfo get(Object key)
    {
        return(TtaskListEntryInfo)getObject(key);
    }
    public void set(int index, TtaskListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TtaskListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TtaskListEntryInfo item)
    {
        return super.indexOf(item);
    }
}