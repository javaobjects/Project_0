package com.kingdee.eas.custom.bill;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TlSlipCollection extends AbstractObjectCollection 
{
    public TlSlipCollection()
    {
        super(TlSlipInfo.class);
    }
    public boolean add(TlSlipInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TlSlipCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TlSlipInfo item)
    {
        return removeObject(item);
    }
    public TlSlipInfo get(int index)
    {
        return(TlSlipInfo)getObject(index);
    }
    public TlSlipInfo get(Object key)
    {
        return(TlSlipInfo)getObject(key);
    }
    public void set(int index, TlSlipInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TlSlipInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TlSlipInfo item)
    {
        return super.indexOf(item);
    }
}