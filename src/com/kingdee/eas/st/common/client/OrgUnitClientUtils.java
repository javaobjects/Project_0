/*
 * @(#)OrgUnitClientUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.MultiOUs4OrgFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitDelegationFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.DelegationPartsEnum;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.IQualityOrgUnit;
import com.kingdee.eas.basedata.org.ISaleOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.ITransportOrgUnit;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.QualityOrgUnitCollection;
import com.kingdee.eas.basedata.org.QualityOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitCollection;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.org.TransportOrgUnitCollection;
import com.kingdee.eas.basedata.org.TransportOrgUnitFactory;
import com.kingdee.eas.basedata.org.client.f7.OrgF7QueryStringConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.eas.st.common.client.utils.SortUtil;
import com.kingdee.eas.st.common.util.OrgUnitUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.util.StringUtils;

/**
 * 描述: 组织单元客户端工具类.
 * 
 * @author daij date:2007-1-10
 *         <p>
 * @version EAS5.2
 */
public abstract class OrgUnitClientUtils {

	public static Map orgF7Query = null;

	static {
		orgF7Query = new HashMap();

		orgF7Query.put(OrgType.Storage, OrgF7QueryStringConstants.STORAGE);

		orgF7Query.put(OrgType.Purchase, OrgF7QueryStringConstants.PURCHASE);

		orgF7Query.put(OrgType.Sale, OrgF7QueryStringConstants.SALE);

		orgF7Query.put(OrgType.Company, OrgF7QueryStringConstants.COMPANY);

		orgF7Query.put(OrgType.Admin, OrgF7QueryStringConstants.ADMIN);

		orgF7Query.put(OrgType.Quality,
				"com.kingdee.eas.st.common.app.QualityOrgUnitQuery");
	}

	public static void setF7ByDelegation(KDBizPromptBox f7,
			OrgUnitInfo mainOrg, int fromType, int toType) throws Exception {
		setF7ByDelegation(new KDBizPromptBox[] { f7 }, mainOrg, fromType,
				toType);
	}

	public static void setF7ByDelegation(KDBizPromptBox[] f7s,
			OrgUnitInfo mainOrg, int fromType, int toType) throws Exception {
		if (f7s == null || f7s.length == 0) {
			return;
		}
		if (mainOrg == null || mainOrg.getId() == null) {
			for (int i = 0, size = f7s.length; i < size; i++) {
				f7s[i].setValue(null);
			}
			return;
		}
		IOrgUnitRelation iUnitRel = OrgUnitRelationFactory.getRemoteInstance();
		OrgUnitCollection orgCol = iUnitRel.getToUnit(mainOrg.getId()
				.toString(), fromType, toType);
		// Map ls = new HashMap();
		// for(int i= 0; i< orgCol.size(); i++){
		// OrgUnitInfo toOrgInfo = (OrgUnitInfo)orgCol.get(i);
		// if(toOrgInfo == null || toOrgInfo.getId() == null){
		// continue;
		// }
		// ls.put(toOrgInfo.getId(),toOrgInfo);
		// }
		// 没有委托的组织
		if (orgCol.size() == 0) {
			for (int j = 0, size = f7s.length; j < size; j++) {
				f7s[j].setValue(null);
			}
		} else {
			OrgUnitInfo org = orgCol.get(0);
			for (int j = 0, size = f7s.length; j < size; j++) {
				f7s[j].setValue(org);
			}
		}

		// 没有委托的组织
		// if(orgCol.size() == 0){
		// Object o = null;
		// for(int j = 0, size = f7s.length; j < size; j++){
		// if(f7s[j] != null
		// && f7s[j].getValue() instanceof IObjectValue){
		// o =((IObjectValue)f7s[j].getValue()).get("id");
		// if(o instanceof BOSUuid){
		// if(!ls.containsKey((BOSUuid)o)){
		// f7s[j].setValue(null);
		// }
		// }
		// }
		// }
		// return;
		// }

		// if(f7s.length > 0){
		// Object o = null;
		// OrgUnitInfo org = orgCol.get(0);
		// for(int j = 0, size = f7s.length; j < size; j++){
		// if(f7s[j] != null
		// && f7s[j].getValue() instanceof IObjectValue){
		// o =((IObjectValue)f7s[j].getValue()).get("id");
		// if(o instanceof BOSUuid){
		// if(!ls.containsKey((BOSUuid)o)){
		// f7s[j].setValue(org);
		// }
		// }
		// }
		// }
		// }
	}

	/**
	 * 
	 * 描述：为组织机构F7增加“虚体组织”过滤.
	 * 
	 * @param bizBox
	 *            bizBox是一个选择组织的F7
	 * @author:daij 创建时间：2007-1-9
	 *              <p>
	 */
	public static void setOrgF7IsEntity(KDBizPromptBox bizBox) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		if (STQMUtils.isNotNull(bizBox)
				&& STQMUtils.isNotNull(bizBox.getEntityViewInfo())) {
			viewInfo = bizBox.getEntityViewInfo();
		}

		FilterInfo filterInfo = new FilterInfo();
		if (STQMUtils.isNull(viewInfo.getFilter())) {
			viewInfo.setFilter(filterInfo);
		}
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isBizUnit", Boolean.TRUE,
						CompareType.EQUALS));
		bizBox.setEntityViewInfo(viewInfo);
	}

	/**
	 * 
	 * 描述：设置主业务组织F7 注意: 过滤出当前用户组织范围内的组织单元.
	 * 
	 * @param bizOrgUnitBox
	 * @param orgType
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static void setMainBizOrgF7(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType) {
		setMainBizOrgF7(bizOrgUnitBox, orgType, null);
	}

	/**
	 * 
	 * 描述：设置主业务组织F7 注意: 过滤出当前用户组织范围内具备某个功能权限的组织单元.
	 * 
	 * @param bizOrgUnitBox
	 *            F7控件
	 * @param orgType
	 *            主业务组织类型
	 * @param permissionItemKeyString
	 *            权限项关键字串
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static void setMainBizOrgF7(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, String[] permissionItemKeyString) {

		// 获取各种组织类型的专用F7 Producer(默认是按User组织范围过滤)
		OrgUnitFilterInfoProducer iProducer = (OrgUnitFilterInfoProducer) FilterInfoProducerFactory
				.getOrgUnitFilterInfoProducer(orgType);
		if (iProducer.getModel() != null) {
			// TODO 只显示实体组织
			iProducer.getModel().setShowVirtual(false);

			// 增加权限项过滤.
			if (STQMUtils.isNotNull(permissionItemKeyString)
					&& permissionItemKeyString.length > 0) {

				iProducer.getModel().setPermissionItem(
						permissionItemKeyString[0]);
			}
		}
		bizOrgUnitBox.setFilterInfoProducer(iProducer);

		// Name显示
		bizOrgUnitBox.setDisplayFormat("$name$");
		// Number编辑
		bizOrgUnitBox.setEditFormat("$number$");
		// 支持助记码
		bizOrgUnitBox.setCommitFormat("$number$;$code$");
	}

	/**
	 * 
	 * 描述：设置业务组织F7 注意: 在主业务业务组织与主业务组织的委托关系设置F7
	 * 
	 * @param bizOrgUnitBox
	 *            F7控件.
	 * @param orgType
	 *            待设置的业务组织类型
	 * @param mainOrgType
	 *            主业务组织类型
	 * @param isSingleMainOrg
	 *            是否单主业务组织.
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static void setBizOrgF7ByDelegation(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, OrgType mainOrgType, OrgUnitInfo mainOrgUnitInfo,
			boolean isSingleMainOrg) {
		if (STQMUtils.isNull(bizOrgUnitBox) || STQMUtils.isNull(bizOrgUnitBox)
				|| STQMUtils.isNull(mainOrgType)) {
			return;
		}

		// 按组织类型获取F7Query
		if (orgF7Query.containsKey(orgType)) {
			// Name显示
			bizOrgUnitBox.setDisplayFormat("$name$");
			// Number编辑
			bizOrgUnitBox.setEditFormat("$number$");
			// 支持助记码
			bizOrgUnitBox.setCommitFormat("$number$;$code$");
			// bizOrgUnitBox.setSelector(null);
			bizOrgUnitBox.setEntityViewInfo(null);
			// F7查询Query名
			bizOrgUnitBox.setQueryInfo(orgF7Query.get(orgType).toString());

			// 设置该组织类型与主业务组织的委托关系过滤
			bizOrgUnitBox.setFilterInfoProducer(getOrgUnitProducer(orgType,
					mainOrgType, isSingleMainOrg));

			// 过滤出与当前的主业务组织具备委托关系的业务组织.
			if (STQMUtils.isNotNull(mainOrgUnitInfo)) {
				bizOrgUnitBox.setCurrentMainBizOrgUnit(mainOrgUnitInfo,
						mainOrgType);
			}
		}
	}

	/**
	 * 
	 * 描述：设置业务组织F7 注意: 在主业务业务组织与主业务组织的反向委托关系设置F7
	 * 
	 * @param bizOrgUnitBox
	 *            F7控件.
	 * @param orgType
	 *            待设置的业务组织类型
	 * @param mainOrgType
	 *            主业务组织类型
	 * @param isSingleMainOrg
	 *            是否单主业务组织.
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static void setBizOrgF7ByFromUnit(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, OrgType mainOrgType, OrgUnitInfo mainOrgUnitInfo,
			boolean isSingleMainOrg) {
		if (STQMUtils.isNull(bizOrgUnitBox) || STQMUtils.isNull(bizOrgUnitBox)
				|| STQMUtils.isNull(mainOrgType)) {
			return;
		}

		// 按组织类型获取F7Query
		if (orgF7Query.containsKey(orgType)) {
			// Name显示
			bizOrgUnitBox.setDisplayFormat("$name$");
			// Number编辑
			bizOrgUnitBox.setEditFormat("$number$");
			// 支持助记码
			bizOrgUnitBox.setCommitFormat("$number$;$code$");
			bizOrgUnitBox.setSelector(null);
			bizOrgUnitBox.setEntityViewInfo(null);
			// F7查询Query名
			bizOrgUnitBox.setQueryInfo(orgF7Query.get(orgType).toString());

			// 设置该组织类型与主业务组织的委托关系过滤
			bizOrgUnitBox.setFilterInfoProducer(getFromOrgUnitProducer(orgType,
					mainOrgType, isSingleMainOrg));

			// 过滤出与当前的主业务组织具备委托关系的业务组织.
			if (STQMUtils.isNotNull(mainOrgUnitInfo)) {
				bizOrgUnitBox.setCurrentMainBizOrgUnit(mainOrgUnitInfo,
						mainOrgType);
			}
		}
	}

	/**
	 * 
	 * 描述：返回F7的组织条件委托关系过滤条件
	 * 
	 * @param orgType
	 *            组织类型
	 * @param mainOrgType
	 *            主业务组织类型
	 * @param isSingleMainOrg
	 *            是否单一主业务组织.
	 * @return IFilterInfoProducer
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static IFilterInfoProducer getOrgUnitProducer(OrgType orgType,
			OrgType mainOrgType, boolean isSingleMainOrg) {
		if (isSingleMainOrg) {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getOrgUnitDelegationFilterInfoProducer(OrgUnitUtils
							.getDelegationDirection(orgType, mainOrgType),
							orgType);
			OrgUnitDelegationFilterInfoProducer producer = (OrgUnitDelegationFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		} else {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getMultiOUs4OrgFilterInfoProducer(null, mainOrgType,
							OrgUnitUtils.getDelegationDirection(orgType,
									mainOrgType), orgType);
			MultiOUs4OrgFilterInfoProducer producer = (MultiOUs4OrgFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		}

	}

	/**
	 * 
	 * 描述：返回F7的组织条件委托关系过滤条件
	 * 
	 * @param orgType
	 *            组织类型
	 * @param mainOrgType
	 *            主业务组织类型
	 * @param isSingleMainOrg
	 *            是否单一主业务组织.
	 * @return IFilterInfoProducer
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static IFilterInfoProducer getFromOrgUnitProducer(OrgType orgType,
			OrgType mainOrgType, boolean isSingleMainOrg) {
		if (isSingleMainOrg) {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getOrgUnitDelegationFilterInfoProducer(
							DelegationPartsEnum.THE_FROM_UNIT, orgType);
			OrgUnitDelegationFilterInfoProducer producer = (OrgUnitDelegationFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		} else {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getMultiOUs4OrgFilterInfoProducer(null, mainOrgType,
							DelegationPartsEnum.THE_FROM_UNIT, orgType);
			MultiOUs4OrgFilterInfoProducer producer = (MultiOUs4OrgFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		}

	}

	/**
	 * 
	 * 描述：取当前用户有权限的默认业务类型组织. 取数规则: 1. 指定用户,默认为当前用户. 2. 指定业务组织类型,获取实体组织 3.
	 * 通过权限项关键字指定用户有权限的组织范围. 4. 满足1,2,3条件的组织有多个按组织编码排序取top1.
	 * 
	 * @param orgType
	 *            组织类型
	 * @param permissionItem
	 *            权限项
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-9-28
	 *              <p>
	 */
	public static OrgUnitInfo getDefaultBizOrgUnitInfo(OrgType orgType,
			String permissionItem) throws EASBizException, BOSException {
		return getDefaultBizOrgUnitInfo(null, orgType, permissionItem);
	}

	/**
	 * 
	 * 描述：取用户有权限的默认业务类型组织. 取数规则: 1. 指定用户,默认为当前用户. 2. 指定业务组织类型,获取实体组织 3.
	 * 通过权限项关键字指定用户有权限的组织范围. 4. 满足1,2,3条件的组织有多个按组织编码排序取top1.
	 * 
	 * @param user
	 *            用户, 可以为NULL则取当前用户
	 * @param orgType
	 *            组织类型
	 * @param permissionItem
	 *            权限项
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-9-28
	 *              <p>
	 */
	public static OrgUnitInfo getDefaultBizOrgUnitInfo(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {

		OrgUnitInfo orgUnit = null;
		if (STUtils.isNotNull(orgType) && !StringUtils.isEmpty(permissionItem)) {
			// 没有指定用户则取当前默认的用户.
			if (STUtils.isNull(user)) {
				user = SysContext.getSysContext().getCurrentUserInfo();
			}

			if (STUtils.isNotNull(user)) {
				// 按用户获取有某项功能权限的业务类型组织集合(包括虚体)
				FullOrgUnitCollection result = PermissionFactory
						.getRemoteInstance().getAuthorizedOrg(
								new ObjectUuidPK(user.getId()), orgType, null,
								permissionItem);
				// 从业务类型组织集合中过滤出实体组织
				OrgUnitInfo[] mainOrgs = getOrgUnitInfosByType(result, orgType);
				if (STUtils.isNotNull(mainOrgs) && mainOrgs.length > 0) {
					// 对业务实体组织按编号排序
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
					// 返回top 1
					orgUnit = mainOrgs[0];
				}
			}
		}
		return orgUnit;
	}

	/**
	 * 郭飞添加 描述：取用户有权限的默认业务类型组织. 取数规则: 1. 指定用户,默认为当前用户. 2. 指定业务组织类型,获取实体组织 3.
	 * 通过权限项关键字指定用户有权限的组织范围.
	 * 
	 * 
	 * @param user
	 *            用户, 可以为NULL则取当前用户
	 * @param orgType
	 *            组织类型
	 * @param permissionItem
	 *            权限项
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:guof 创建时间：2009-8-28
	 *              <p>
	 */
	public static OrgUnitInfo[] getBizOrgUnitInfo(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {

		OrgUnitInfo orgUnit[] = null;
		if (STUtils.isNotNull(orgType) && !StringUtils.isEmpty(permissionItem)) {
			// 没有指定用户则取当前默认的用户.
			if (STUtils.isNull(user)) {
				user = SysContext.getSysContext().getCurrentUserInfo();
			}

			if (STUtils.isNotNull(user)) {
				// 按用户获取有某项功能权限的业务类型组织集合(包括虚体)
				FullOrgUnitCollection result = PermissionFactory
						.getRemoteInstance().getAuthorizedOrg(
								new ObjectUuidPK(user.getId()), orgType, null,
								permissionItem);
				// 从业务类型组织集合中过滤出实体组织
				OrgUnitInfo[] mainOrgs = getOrgUnitInfosByType(result, orgType);
				if (STUtils.isNotNull(mainOrgs) && mainOrgs.length > 0) {
					// 对业务实体组织按编号排序
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
					// 返回top 1
					orgUnit = mainOrgs;
				}
			}
		}
		return orgUnit;
	}

	/**
	 * 描述：取用户当前CU下有权限的默认业务类型组织，默认值为当前登陆的业务组织. 取数规则: 1. 指定用户,默认为当前用户. 2.
	 * 指定业务组织类型,获取实体组织 3. 通过权限项关键字指定用户有权限的组织范围.
	 * 
	 * @param user
	 * @param orgType
	 * @param permissionItem
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author jiwei_xiao
	 */
	public static OrgUnitInfo getDefaultOrgUnitInCU(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {
		OrgUnitInfo[] orgUnit = null;
		orgUnit = OrgUnitClientUtils.getBizOrgUnitInfo(user, orgType,
				permissionItem);
		StorageOrgUnitInfo orgUnitInfo = SysContext.getSysContext()
				.getCurrentStorageUnit();
		if (orgUnit != null) {
			if (orgUnitInfo != null) {
				for (int i = 0; i < orgUnit.length; i++) {
					if (orgUnit[i].getId().toString().equals(
							orgUnitInfo.getId().toString())) {
						return orgUnit[i];
					}
				}
			}
			OrgUnitInfo currentBizOrg = (OrgUnitInfo) SysContext
					.getSysContext().getCurrentCtrlUnit();
			if (currentBizOrg != null) {
				for (int i = 0; i < orgUnit.length; i++) {
					if (orgUnit[i].getCU().getId().toString().equals(
							currentBizOrg.getId().toString())) {
						return orgUnit[i];
					}
				}
			}
		}
		return null;
	}

	/**
	 * 返回实体组织
	 * 
	 * @param userInfo
	 * @param orgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizOrgUnit(UserInfo userInfo, OrgType orgType,
			String permissionItem, OrgUnitInfo orgUnitInfo) {

		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// 如果是实体，直接返回
		if (STQMUtils.isNotNull(orgUnitInfo)) {
			if (orgUnitInfo.getBoolean("isBizUnit")) {
				bizSaleOrgUnitInfo = orgUnitInfo;
				return bizSaleOrgUnitInfo;
			}
		}

		// 返回实体销售组织
		try {
			OrgUnitInfo[] mainOrgs = null;
			// TODO 能否从SCM引用？？？
			FullOrgUnitCollection collection = SCMGroupClientUtils
					.getAuthOrgByPermItem(new ObjectStringPK(userInfo.getId()
							.toString()), orgType, permissionItem);
			mainOrgs = getOrgUnitInfosByType(collection, orgType);
			if (mainOrgs == null) {
				mainOrgs = new OrgUnitInfo[] {};
				return null;
			}
			if (mainOrgs.length > 0) {
				mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(mainOrgs,
						true);
			}
			bizSaleOrgUnitInfo = mainOrgs[0];
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

		return bizSaleOrgUnitInfo;
	}

	public static OrgUnitInfo getBizOrgUnit(ICoreBase coreBase,
			UserInfo userInfo, OrgType orgType, String permissionItem,
			OrgUnitInfo orgUnitInfo) {

		// if (STQMUtils.isNull(orgUnitInfo)) {
		// return null;
		// }
		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// 如果是实体，直接返回
		if (STQMUtils.isNotNull(orgUnitInfo)
				&& orgUnitInfo.getBoolean("isBizUnit")) {
			bizSaleOrgUnitInfo = orgUnitInfo;
		} else {
			// 返回实体销售组织
			try {
				OrgUnitInfo[] mainOrgs = null;
				if (coreBase instanceof ISTBillBase) {
					try {

						ISTBillBase iSTBillBase = (ISTBillBase) coreBase;

						mainOrgs = iSTBillBase
								.getBizOrgUnit(new ObjectStringPK(userInfo
										.getId().toString()), orgType,
										permissionItem);

					} catch (Exception e) {
						ExceptionHandler.handle(e);
					}
				}

				if (mainOrgs == null) {
					mainOrgs = new OrgUnitInfo[] {};
					return null;
				}
				if (mainOrgs.length > 0) {
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
				}
				bizSaleOrgUnitInfo = mainOrgs[0];
			} catch (Exception e) {
				ExceptionHandler.handle(e);
			}
		}

		return bizSaleOrgUnitInfo;
	}

	public static OrgUnitInfo getBizOrgUnitInfo(ICoreBase coreBase,
			String addNewPermItemName, OrgType orgType, Context mainOrgContext) {

		if (StringUtils.isEmpty(addNewPermItemName)) {
			return null;
		}

		OrgUnitInfo orgUnitInfo = null;
		if (STQMUtils.isNotNull(mainOrgContext)) {
			orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);
		}
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		}

		OrgUnitInfo retOrgUnitInfo = null;

		// 获得授权的实体组织
		retOrgUnitInfo = getBizOrgUnit(coreBase, (UserInfo) (SysContext
				.getSysContext().getCurrentUserInfo()), orgType,
				addNewPermItemName, orgUnitInfo);

		return retOrgUnitInfo;

	}

	public static OrgUnitInfo getBizOrgUnitInfo(String addNewPermItemName,
			OrgType orgType, Context mainOrgContext) {

		if (STQMUtils.isNull(addNewPermItemName)
				|| STQMUtils.isNull(mainOrgContext)) {
			return null;
		}

		OrgUnitInfo orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);

		OrgUnitInfo retOrgUnitInfo = null;

		// 获得授权的实体组织
		retOrgUnitInfo = getBizOrgUnit((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()), orgType, addNewPermItemName,
				orgUnitInfo);

		return retOrgUnitInfo;

	}

	public static OrgUnitInfo getBizOrgUnitInfo(String addNewPermItemName,
			OrgType orgType, OrgUnitInfo orgUnitInfo) {

		// ||
		// STQMUtils.isNull(orgUnitInfo)

		if (STQMUtils.isNull(addNewPermItemName)) {
			return null;
		}

		// OrgUnitInfo orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);

		OrgUnitInfo retOrgUnitInfo = null;

		// 获得授权的实体组织
		retOrgUnitInfo = getBizOrgUnit((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()), orgType, addNewPermItemName,
				orgUnitInfo);

		return retOrgUnitInfo;

	}

	/**
	 * 描述：根据组织集合，得到某类型的实体组织数组
	 * 
	 * @param orgs
	 * @param orgType
	 * @return
	 * @author:paul 创建时间：2006-10-23
	 *              <p>
	 */
	public static OrgUnitInfo[] getOrgUnitInfosByType(
			FullOrgUnitCollection orgs, OrgType orgType) {
		OrgUnitInfo[] results = null;

		if (orgs == null || orgs.size() == 0) {
			return null;
		}

		LinkedHashSet keys = new LinkedHashSet();
		for (int i = 0; i < orgs.size(); i++) {
			keys.add(orgs.get(i).getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", keys, CompareType.INCLUDE));
		// 行政组织无虚体实体之分
		if (!orgType.equals(OrgType.Admin)) {
			filter.getFilterItems().add(
					new FilterItemInfo("isBizUnit", Boolean.valueOf(true),
							CompareType.EQUALS));
		}
		view.setFilter(filter);
		try {
			switch (orgType.getValue()) {
			case 0: // Admin
			{
				IAdminOrgUnit iAdmin = AdminOrgUnitFactory.getRemoteInstance();

				AdminOrgUnitCollection collection = iAdmin
						.getAdminOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}

			case 1: // Company
			{
				ICompanyOrgUnit iCompany = CompanyOrgUnitFactory
						.getRemoteInstance();
				CompanyOrgUnitCollection collection = iCompany
						.getCompanyOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 2: // Sale
			{
				ISaleOrgUnit iSale = SaleOrgUnitFactory.getRemoteInstance();
				SaleOrgUnitCollection collection = iSale
						.getSaleOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 3: // Purchase
			{
				IPurchaseOrgUnit iPurchase = PurchaseOrgUnitFactory
						.getRemoteInstance();
				PurchaseOrgUnitCollection collection = iPurchase
						.getPurchaseOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 4: // Storage
			{
				IStorageOrgUnit iStorage = StorageOrgUnitFactory
						.getRemoteInstance();
				StorageOrgUnitCollection collection = iStorage
						.getStorageOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 32: // Quality
			{
				IQualityOrgUnit iQuality = QualityOrgUnitFactory
						.getRemoteInstance();
				QualityOrgUnitCollection collection = iQuality
						.getQualityOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 24: // Transport
			{
				ITransportOrgUnit iTransport = TransportOrgUnitFactory
						.getRemoteInstance();
				TransportOrgUnitCollection collection = iTransport
						.getTransportOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
