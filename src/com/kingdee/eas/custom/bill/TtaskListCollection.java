package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TtaskListCollection extends AbstractObjectCollection 
{
    public TtaskListCollection()
    {
        super(TtaskListInfo.class);
    }
    public boolean add(TtaskListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TtaskListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TtaskListInfo item)
    {
        return removeObject(item);
    }
    public TtaskListInfo get(int index)
    {
        return(TtaskListInfo)getObject(index);
    }
    public TtaskListInfo get(Object key)
    {
        return(TtaskListInfo)getObject(key);
    }
    public void set(int index, TtaskListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TtaskListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TtaskListInfo item)
    {
        return super.indexOf(item);
    }
}