package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class KsfcsBillCollection extends AbstractObjectCollection 
{
    public KsfcsBillCollection()
    {
        super(KsfcsBillInfo.class);
    }
    public boolean add(KsfcsBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(KsfcsBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(KsfcsBillInfo item)
    {
        return removeObject(item);
    }
    public KsfcsBillInfo get(int index)
    {
        return(KsfcsBillInfo)getObject(index);
    }
    public KsfcsBillInfo get(Object key)
    {
        return(KsfcsBillInfo)getObject(key);
    }
    public void set(int index, KsfcsBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(KsfcsBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(KsfcsBillInfo item)
    {
        return super.indexOf(item);
    }
}