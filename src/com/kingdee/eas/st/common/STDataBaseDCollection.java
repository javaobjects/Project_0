package com.kingdee.eas.st.common;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STDataBaseDCollection extends AbstractObjectCollection 
{
    public STDataBaseDCollection()
    {
        super(STDataBaseDInfo.class);
    }
    public boolean add(STDataBaseDInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STDataBaseDCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STDataBaseDInfo item)
    {
        return removeObject(item);
    }
    public STDataBaseDInfo get(int index)
    {
        return(STDataBaseDInfo)getObject(index);
    }
    public STDataBaseDInfo get(Object key)
    {
        return(STDataBaseDInfo)getObject(key);
    }
    public void set(int index, STDataBaseDInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STDataBaseDInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STDataBaseDInfo item)
    {
        return super.indexOf(item);
    }
}