package com.kingdee.eas.Interfacetools;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.Interfacetools.app.*;
import com.kingdee.bos.util.*;

public class CommonInterface extends AbstractBizCtrl implements ICommonInterface
{
    public CommonInterface()
    {
        super();
        registerInterface(ICommonInterface.class, this);
    }
    public CommonInterface(Context ctx)
    {
        super(ctx);
        registerInterface(ICommonInterface.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2F5B901A");
    }
    private CommonInterfaceController getController() throws BOSException
    {
        return (CommonInterfaceController)getBizController();
    }
    /**
     *�ϴ�����-User defined method
     *@param easTemplateNumber ����ģ�����
     *@param jsonDatas ҵ������
     *@param isUpdate �Ƿ����
     *@return
     */
    public String[][] onloadData(String easTemplateNumber, String[] jsonDatas, boolean isUpdate) throws BOSException
    {
        try {
            return getController().onloadData(getContext(), easTemplateNumber, jsonDatas, isUpdate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}