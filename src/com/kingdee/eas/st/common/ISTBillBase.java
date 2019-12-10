package com.kingdee.eas.st.common;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface ISTBillBase extends ICoreBillBase
{
    public STBillBaseInfo getSTBillBaseInfo(String oql) throws BOSException, EASBizException;
    public STBillBaseInfo getSTBillBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public STBillBaseInfo getSTBillBaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public STBillBaseCollection getSTBillBaseCollection() throws BOSException;
    public STBillBaseCollection getSTBillBaseCollection(EntityViewInfo view) throws BOSException;
    public STBillBaseCollection getSTBillBaseCollection(String oql) throws BOSException;
    public Map audit(IObjectPK[] pks) throws BOSException, EASBizException;
    public Map unAudit(IObjectPK[] pks) throws BOSException, EASBizException;
    public String getAddNewPermItemName(String objectType) throws BOSException;
    public String getViewPermItemName(String objectType) throws BOSException;
    public String getCodeRuleServer(STBillBaseInfo coreBillInfo, String companyID) throws BOSException, EASBizException;
    public void recycleNumberByOrg(STBillBaseInfo editData, String orgType, String number) throws BOSException, EASBizException;
    public STBillBaseCodingRuleVo getCodeRuleBizVo(STBillBaseCodingRuleVo codeVo) throws BOSException, EASBizException;
    public OrgUnitInfo[] getBizOrgUnit(ObjectStringPK userID, OrgType orgType, String permissionItem) throws BOSException, EASBizException;
    public HashMap getSTBillVo(HashMap map) throws BOSException, EASBizException;
    public HashMap getSTBillParam(HashMap map) throws BOSException, EASBizException;
    public List getUnAuditBills(List idList) throws BOSException, EASBizException;
    public OrgUnitCollection getAuthOrgsByType(OrgType orgType, BOSObjectType bosType) throws BOSException, EASBizException;
}