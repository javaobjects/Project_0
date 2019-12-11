package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TlSlipEntryCollection extends AbstractObjectCollection 
{
    public TlSlipEntryCollection()
    {
        super(TlSlipEntryInfo.class);
    }
    public boolean add(TlSlipEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TlSlipEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TlSlipEntryInfo item)
    {
        return removeObject(item);
    }
    public TlSlipEntryInfo get(int index)
    {
        return(TlSlipEntryInfo)getObject(index);
    }
    public TlSlipEntryInfo get(Object key)
    {
        return(TlSlipEntryInfo)getObject(key);
    }
    public void set(int index, TlSlipEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TlSlipEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TlSlipEntryInfo item)
    {
        return super.indexOf(item);
    }
}