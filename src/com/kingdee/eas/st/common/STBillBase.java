package com.kingdee.eas.st.common;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.BOSException;
import java.util.Map;
import java.util.List;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBillBase;
import java.lang.String;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.bos.framework.*;
import java.util.HashMap;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.st.common.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public abstract class STBillBase extends CoreBillBase implements ISTBillBase
{
    public STBillBase()
    {
        super();
        registerInterface(ISTBillBase.class, this);
    }
    public STBillBase(Context ctx)
    {
        super(ctx);
        registerInterface(ISTBillBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1CB26273");
    }
    private STBillBaseController getController() throws BOSException
    {
        return (STBillBaseController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public STBillBaseInfo getSTBillBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public STBillBaseInfo getSTBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public STBillBaseInfo getSTBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillBaseInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public STBillBaseCollection getSTBillBaseCollection() throws BOSException
    {
        try {
            return getController().getSTBillBaseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public STBillBaseCollection getSTBillBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSTBillBaseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public STBillBaseCollection getSTBillBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getSTBillBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������-User defined method
     *@param pks pks
     *@return
     */
    public Map audit(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݷ����-User defined method
     *@param pks pks
     *@return
     */
    public Map unAudit(IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), pks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ����Ȩ��������-User defined method
     *@param objectType ��������
     *@return
     */
    public String getAddNewPermItemName(String objectType) throws BOSException
    {
        try {
            return getController().getAddNewPermItemName(getContext(), objectType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ�鿴Ȩ��������-User defined method
     *@param objectType ��������
     *@return
     */
    public String getViewPermItemName(String objectType) throws BOSException
    {
        try {
            return getController().getViewPermItemName(getContext(), objectType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�������-User defined method
     *@param coreBillInfo coreBillInfo
     *@param companyID companyID
     *@return
     */
    public String getCodeRuleServer(STBillBaseInfo coreBillInfo, String companyID) throws BOSException, EASBizException
    {
        try {
            return getController().getCodeRuleServer(getContext(), coreBillInfo, companyID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ձ������-User defined method
     *@param editData editData
     *@param orgType orgType
     *@param number number
     */
    public void recycleNumberByOrg(STBillBaseInfo editData, String orgType, String number) throws BOSException, EASBizException
    {
        try {
            getController().recycleNumberByOrg(getContext(), editData, orgType, number);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ��������VO-User defined method
     *@param codeVo codeVo
     *@return
     */
    public STBillBaseCodingRuleVo getCodeRuleBizVo(STBillBaseCodingRuleVo codeVo) throws BOSException, EASBizException
    {
        try {
            return getController().getCodeRuleBizVo(getContext(), codeVo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ʵ����֯-User defined method
     *@param userID userID
     *@param orgType orgType
     *@param permissionItem permissionItem
     *@return
     */
    public OrgUnitInfo[] getBizOrgUnit(ObjectStringPK userID, OrgType orgType, String permissionItem) throws BOSException, EASBizException
    {
        try {
            return getController().getBizOrgUnit(getContext(), userID, orgType, permissionItem);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ST�����������ݷ��ؽӿ�-User defined method
     *@param map map
     *@return
     */
    public HashMap getSTBillVo(HashMap map) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillVo(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ST���������������ؽӿ�-User defined method
     *@param map map
     *@return
     */
    public HashMap getSTBillParam(HashMap map) throws BOSException, EASBizException
    {
        try {
            return getController().getSTBillParam(getContext(), map);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ݴ����ID��ȡ��δ��˵ĵ���-User defined method
     *@param idList ID�б�
     *@return
     */
    public List getUnAuditBills(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().getUnAuditBills(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ָ�����͵���Ȩ�޵���֯-User defined method
     *@param orgType orgType
     *@param bosType bosType
     *@return
     */
    public OrgUnitCollection getAuthOrgsByType(OrgType orgType, BOSObjectType bosType) throws BOSException, EASBizException
    {
        try {
            return getController().getAuthOrgsByType(getContext(), orgType, bosType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}