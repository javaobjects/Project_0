package com.kingdee.eas.st.common.template;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBillTemplateCollection extends AbstractObjectCollection 
{
    public STBillTemplateCollection()
    {
        super(STBillTemplateInfo.class);
    }
    public boolean add(STBillTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBillTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBillTemplateInfo item)
    {
        return removeObject(item);
    }
    public STBillTemplateInfo get(int index)
    {
        return(STBillTemplateInfo)getObject(index);
    }
    public STBillTemplateInfo get(Object key)
    {
        return(STBillTemplateInfo)getObject(key);
    }
    public void set(int index, STBillTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBillTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBillTemplateInfo item)
    {
        return super.indexOf(item);
    }
}