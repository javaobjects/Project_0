package com.kingdee.eas.st.common.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import java.util.Map;
import java.util.List;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.st.common.STBillBaseInfo;
import java.util.HashMap;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.st.common.STBillBaseCodingRuleVo;
import com.kingdee.eas.st.common.STBillBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface STBillBaseController extends CoreBillBaseController
{
    public STBillBaseInfo getSTBillBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public STBillBaseInfo getSTBillBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public STBillBaseInfo getSTBillBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public STBillBaseCollection getSTBillBaseCollection(Context ctx) throws BOSException, RemoteException;
    public STBillBaseCollection getSTBillBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public STBillBaseCollection getSTBillBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map audit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public Map unAudit(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public String getAddNewPermItemName(Context ctx, String objectType) throws BOSException, RemoteException;
    public String getViewPermItemName(Context ctx, String objectType) throws BOSException, RemoteException;
    public String getCodeRuleServer(Context ctx, STBillBaseInfo coreBillInfo, String companyID) throws BOSException, EASBizException, RemoteException;
    public void recycleNumberByOrg(Context ctx, STBillBaseInfo editData, String orgType, String number) throws BOSException, EASBizException, RemoteException;
    public STBillBaseCodingRuleVo getCodeRuleBizVo(Context ctx, STBillBaseCodingRuleVo codeVo) throws BOSException, EASBizException, RemoteException;
    public OrgUnitInfo[] getBizOrgUnit(Context ctx, ObjectStringPK userID, OrgType orgType, String permissionItem) throws BOSException, EASBizException, RemoteException;
    public HashMap getSTBillVo(Context ctx, HashMap map) throws BOSException, EASBizException, RemoteException;
    public HashMap getSTBillParam(Context ctx, HashMap map) throws BOSException, EASBizException, RemoteException;
    public List getUnAuditBills(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public OrgUnitCollection getAuthOrgsByType(Context ctx, OrgType orgType, BOSObjectType bosType) throws BOSException, EASBizException, RemoteException;
}