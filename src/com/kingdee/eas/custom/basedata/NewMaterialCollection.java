package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewMaterialCollection extends AbstractObjectCollection 
{
    public NewMaterialCollection()
    {
        super(NewMaterialInfo.class);
    }
    public boolean add(NewMaterialInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewMaterialCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewMaterialInfo item)
    {
        return removeObject(item);
    }
    public NewMaterialInfo get(int index)
    {
        return(NewMaterialInfo)getObject(index);
    }
    public NewMaterialInfo get(Object key)
    {
        return(NewMaterialInfo)getObject(key);
    }
    public void set(int index, NewMaterialInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewMaterialInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewMaterialInfo item)
    {
        return super.indexOf(item);
    }
}