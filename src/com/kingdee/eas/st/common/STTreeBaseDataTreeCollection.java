package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STTreeBaseDataTreeCollection extends AbstractObjectCollection 
{
    public STTreeBaseDataTreeCollection()
    {
        super(STTreeBaseDataTreeInfo.class);
    }
    public boolean add(STTreeBaseDataTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STTreeBaseDataTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STTreeBaseDataTreeInfo item)
    {
        return removeObject(item);
    }
    public STTreeBaseDataTreeInfo get(int index)
    {
        return(STTreeBaseDataTreeInfo)getObject(index);
    }
    public STTreeBaseDataTreeInfo get(Object key)
    {
        return(STTreeBaseDataTreeInfo)getObject(key);
    }
    public void set(int index, STTreeBaseDataTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STTreeBaseDataTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STTreeBaseDataTreeInfo item)
    {
        return super.indexOf(item);
    }
}