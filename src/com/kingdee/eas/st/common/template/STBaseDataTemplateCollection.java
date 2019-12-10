package com.kingdee.eas.st.common.template;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBaseDataTemplateCollection extends AbstractObjectCollection 
{
    public STBaseDataTemplateCollection()
    {
        super(STBaseDataTemplateInfo.class);
    }
    public boolean add(STBaseDataTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBaseDataTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBaseDataTemplateInfo item)
    {
        return removeObject(item);
    }
    public STBaseDataTemplateInfo get(int index)
    {
        return(STBaseDataTemplateInfo)getObject(index);
    }
    public STBaseDataTemplateInfo get(Object key)
    {
        return(STBaseDataTemplateInfo)getObject(key);
    }
    public void set(int index, STBaseDataTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBaseDataTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBaseDataTemplateInfo item)
    {
        return super.indexOf(item);
    }
}