package com.kingdee.eas.st.common.template;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class STBillTemplateEntryCollection extends AbstractObjectCollection 
{
    public STBillTemplateEntryCollection()
    {
        super(STBillTemplateEntryInfo.class);
    }
    public boolean add(STBillTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(STBillTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(STBillTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public STBillTemplateEntryInfo get(int index)
    {
        return(STBillTemplateEntryInfo)getObject(index);
    }
    public STBillTemplateEntryInfo get(Object key)
    {
        return(STBillTemplateEntryInfo)getObject(key);
    }
    public void set(int index, STBillTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(STBillTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(STBillTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}