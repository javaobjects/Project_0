package com.kingdee.eas.custom.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewMaterialTreeCollection extends AbstractObjectCollection 
{
    public NewMaterialTreeCollection()
    {
        super(NewMaterialTreeInfo.class);
    }
    public boolean add(NewMaterialTreeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewMaterialTreeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewMaterialTreeInfo item)
    {
        return removeObject(item);
    }
    public NewMaterialTreeInfo get(int index)
    {
        return(NewMaterialTreeInfo)getObject(index);
    }
    public NewMaterialTreeInfo get(Object key)
    {
        return(NewMaterialTreeInfo)getObject(key);
    }
    public void set(int index, NewMaterialTreeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewMaterialTreeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewMaterialTreeInfo item)
    {
        return super.indexOf(item);
    }
}