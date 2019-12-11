package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChitCostPropertyCostCollection extends AbstractObjectCollection 
{
    public ChitCostPropertyCostCollection()
    {
        super(ChitCostPropertyCostInfo.class);
    }
    public boolean add(ChitCostPropertyCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChitCostPropertyCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChitCostPropertyCostInfo item)
    {
        return removeObject(item);
    }
    public ChitCostPropertyCostInfo get(int index)
    {
        return(ChitCostPropertyCostInfo)getObject(index);
    }
    public ChitCostPropertyCostInfo get(Object key)
    {
        return(ChitCostPropertyCostInfo)getObject(key);
    }
    public void set(int index, ChitCostPropertyCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChitCostPropertyCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChitCostPropertyCostInfo item)
    {
        return super.indexOf(item);
    }
}