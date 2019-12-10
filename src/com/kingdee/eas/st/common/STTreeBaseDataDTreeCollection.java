package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STTreeBaseDataDTreeCollection extends AbstractObjectCollection 
{
    public STTreeBaseDataDTreeCollection()
    {
        super(STTreeBaseDataDTreeInfo.class);
    }
    public boolean add(STTreeBaseDataDTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STTreeBaseDataDTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STTreeBaseDataDTreeInfo item)
    {
        return removeObject(item);
    }
    public STTreeBaseDataDTreeInfo get(int index)
    {
        return(STTreeBaseDataDTreeInfo)getObject(index);
    }
    public STTreeBaseDataDTreeInfo get(Object key)
    {
        return(STTreeBaseDataDTreeInfo)getObject(key);
    }
    public void set(int index, STTreeBaseDataDTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STTreeBaseDataDTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STTreeBaseDataDTreeInfo item)
    {
        return super.indexOf(item);
    }
}