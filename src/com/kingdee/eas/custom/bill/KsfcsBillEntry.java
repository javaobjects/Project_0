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

public class KsfcsBillEntry extends STBillBaseEntry implements IKsfcsBillEntry
{
    public KsfcsBillEntry()
    {
        super();
        registerInterface(IKsfcsBillEntry.class, this);
    }
    public KsfcsBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IKsfcsBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4C519539");
    }
    private KsfcsBillEntryController getController() throws BOSException
    {
        return (KsfcsBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public KsfcsBillEntryInfo getKsfcsBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillEntryInfo(getContext(), pk);
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
    public KsfcsBillEntryInfo getKsfcsBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillEntryInfo(getContext(), pk, selector);
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
    public KsfcsBillEntryInfo getKsfcsBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public KsfcsBillEntryCollection getKsfcsBillEntryCollection() throws BOSException
    {
        try {
            return getController().getKsfcsBillEntryCollection(getContext());
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
    public KsfcsBillEntryCollection getKsfcsBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getKsfcsBillEntryCollection(getContext(), view);
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
    public KsfcsBillEntryCollection getKsfcsBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getKsfcsBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}