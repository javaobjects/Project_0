package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STTreeBaseDataCollection extends AbstractObjectCollection 
{
    public STTreeBaseDataCollection()
    {
        super(STTreeBaseDataInfo.class);
    }
    public boolean add(STTreeBaseDataInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STTreeBaseDataCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STTreeBaseDataInfo item)
    {
        return removeObject(item);
    }
    public STTreeBaseDataInfo get(int index)
    {
        return(STTreeBaseDataInfo)getObject(index);
    }
    public STTreeBaseDataInfo get(Object key)
    {
        return(STTreeBaseDataInfo)getObject(key);
    }
    public void set(int index, STTreeBaseDataInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STTreeBaseDataInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STTreeBaseDataInfo item)
    {
        return super.indexOf(item);
    }
}