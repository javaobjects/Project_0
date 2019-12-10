package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STDataBaseCollection extends AbstractObjectCollection 
{
    public STDataBaseCollection()
    {
        super(STDataBaseInfo.class);
    }
    public boolean add(STDataBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STDataBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STDataBaseInfo item)
    {
        return removeObject(item);
    }
    public STDataBaseInfo get(int index)
    {
        return(STDataBaseInfo)getObject(index);
    }
    public STDataBaseInfo get(Object key)
    {
        return(STDataBaseInfo)getObject(key);
    }
    public void set(int index, STDataBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STDataBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STDataBaseInfo item)
    {
        return super.indexOf(item);
    }
}