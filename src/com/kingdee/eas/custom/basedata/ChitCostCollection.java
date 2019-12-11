package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChitCostCollection extends AbstractObjectCollection 
{
    public ChitCostCollection()
    {
        super(ChitCostInfo.class);
    }
    public boolean add(ChitCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChitCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChitCostInfo item)
    {
        return removeObject(item);
    }
    public ChitCostInfo get(int index)
    {
        return(ChitCostInfo)getObject(index);
    }
    public ChitCostInfo get(Object key)
    {
        return(ChitCostInfo)getObject(key);
    }
    public void set(int index, ChitCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChitCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChitCostInfo item)
    {
        return super.indexOf(item);
    }
}