package com.kingdee.eas.custom.bill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.st.common.STBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.custom.bill.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class KsfcsBill extends STBillBase implements IKsfcsBill
{
    public KsfcsBill()
    {
        super();
        registerInterface(IKsfcsBill.class, this);
    }
    public KsfcsBill(Context ctx)
    {
        super(ctx);
        registerInterface(IKsfcsBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0B0FCEB9");
    }
    private KsfcsBillController getController() throws BOSException
    {
        return (KsfcsBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public KsfcsBillInfo getKsfcsBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillInfo(getContext(), pk);
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
    public KsfcsBillInfo getKsfcsBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillInfo(getContext(), pk, selector);
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
    public KsfcsBillInfo getKsfcsBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getKsfcsBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public KsfcsBillCollection getKsfcsBillCollection() throws BOSException
    {
        try {
            return getController().getKsfcsBillCollection(getContext());
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
    public KsfcsBillCollection getKsfcsBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getKsfcsBillCollection(getContext(), view);
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
    public KsfcsBillCollection getKsfcsBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getKsfcsBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}