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
     *取值-System defined method
     *@param oql 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param pk 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *单据审核-User defined method
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
     *单据反审核-User defined method
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
     *获取新增权限项名称-User defined method
     *@param objectType 对象类型
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
     *获取查看权限项名称-User defined method
     *@param objectType 对象类型
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
     *编码规则-User defined method
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
     *回收编码规则-User defined method
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
     *获取编码规则的VO-User defined method
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
     *返回实体组织-User defined method
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
     *ST单据批量数据返回接口-User defined method
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
     *ST单据批量参数返回接口-User defined method
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
     *根据传入的ID，取得未审核的单据-User defined method
     *@param idList ID列表
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
     *获得指定类型的有权限的组织-User defined method
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