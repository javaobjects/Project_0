package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STTreeBaseDataDCollection extends AbstractObjectCollection 
{
    public STTreeBaseDataDCollection()
    {
        super(STTreeBaseDataDInfo.class);
    }
    public boolean add(STTreeBaseDataDInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STTreeBaseDataDCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STTreeBaseDataDInfo item)
    {
        return removeObject(item);
    }
    public STTreeBaseDataDInfo get(int index)
    {
        return(STTreeBaseDataDInfo)getObject(index);
    }
    public STTreeBaseDataDInfo get(Object key)
    {
        return(STTreeBaseDataDInfo)getObject(key);
    }
    public void set(int index, STTreeBaseDataDInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STTreeBaseDataDInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STTreeBaseDataDInfo item)
    {
        return super.indexOf(item);
    }
}