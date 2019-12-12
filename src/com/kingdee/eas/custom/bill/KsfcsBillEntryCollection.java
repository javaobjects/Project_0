package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class KsfcsBillEntryCollection extends AbstractObjectCollection 
{
    public KsfcsBillEntryCollection()
    {
        super(KsfcsBillEntryInfo.class);
    }
    public boolean add(KsfcsBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(KsfcsBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(KsfcsBillEntryInfo item)
    {
        return removeObject(item);
    }
    public KsfcsBillEntryInfo get(int index)
    {
        return(KsfcsBillEntryInfo)getObject(index);
    }
    public KsfcsBillEntryInfo get(Object key)
    {
        return(KsfcsBillEntryInfo)getObject(key);
    }
    public void set(int index, KsfcsBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(KsfcsBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(KsfcsBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}