package com.kingdee.eas.custom.bill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.st.common.ISTBillBaseEntry;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.STBillBaseEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class TestBillEntry extends STBillBaseEntry implements ITestBillEntry
{
    public TestBillEntry()
    {
        super();
        registerInterface(ITestBillEntry.class, this);
    }
    public TestBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITestBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1AFF06DD");
    }
    private TestBillEntryController getController() throws BOSException
    {
        return (TestBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public TestBillEntryInfo getTestBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTestBillEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public TestBillEntryInfo getTestBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTestBillEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public TestBillEntryInfo getTestBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTestBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TestBillEntryCollection getTestBillEntryCollection() throws BOSException
    {
        try {
            return getController().getTestBillEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public TestBillEntryCollection getTestBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTestBillEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public TestBillEntryCollection getTestBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTestBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}