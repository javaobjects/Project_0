/*
 * @(#)CreditOrgUnitUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.DelegationPartsEnum;
import com.kingdee.eas.basedata.org.HROrgUnitFactory;
import com.kingdee.eas.basedata.org.HROrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgTypeRelationInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.OrgUtils;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysContextConstant;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.StringUtils;

/**
 * 描述: 组织单元工具类 职责: 1.获取组织间的正向委托关系 2.财务组织树的追朔
 * 
 * @author daij date:2006-7-26
 *         <p>
 * @version EAS5.1.1
 */
public abstract class OrgUnitUtils {

	/**
	 * 销售组织委托财务组织的类型关系
	 */
	public static final String ORG_DELEGATE_SALE_FI = "00000000-0000-0000-0000-0000000000050FE9F8B5";

	/**
	 * 采购组织委托财务组织的类型关系
	 */
	public static final String ORG_DELEGATE_PURCHARSE_FI = "00000000-0000-0000-0000-0000000000030FE9F8B5";

	/**
	 * 库存组织委托财务组织的类型关系
	 */
	public static final String ORG_DELEGATE_STORAGE_FI = "00000000-0000-0000-0000-0000000000070FE9F8B5";

	/**
	 * 行政委托财务组织的类型关系
	 */
	public static final String ORG_DELEGATE_ADMIN_FI = "00000000-0000-0000-0000-0000000000010FE9F8B5";

	/**
	 * HR组织委托财务组织的类型关系
	 */
	public static final String ORG_DELEGATE_HR_FI = "00000000-0000-0000-0000-0000000000130FE9F8B5";

	/**
	 * 组织实体
	 */
	public static final int ORG_OU_BIZUNIT = 1;

	/**
	 * 组织虚体
	 */
	public static final int ORG_OU_VIRTUAL = 2;

	/**
	 * 实体和虚体
	 */
	public static final int ORG_OU_ALL = 3;

	/**
	 * 向上追溯OU树
	 */
	public static final int ORG_OU_UP = 1;

	/**
	 * 向下追溯OU树
	 */
	public static final int ORG_OU_DOWN = 2;

	/**
	 * 
	 * 描述：返回业务组织是否为实体组织
	 * 
	 * @param orgUnit
	 *            业务组织
	 * @return boolean 是否实体
	 * @throws EASBizException
	 * @throws BOSException
	 * 
	 * @author williamwu 2007-1-6
	 */
	public final static boolean isBizUnit(OrgUnitInfo orgUnit)
			throws EASBizException, BOSException {

		boolean bIsBizUnit = false;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(new SelectorItemInfo("id"));
		selectors.add(new SelectorItemInfo("number"));
		selectors.add(new SelectorItemInfo("name"));
		selectors.add(new SelectorItemInfo("isBizUnit"));

		if (orgUnit != null) {
			if (orgUnit instanceof CompanyOrgUnitInfo
					&& orgUnit.getId() != null) {
				CompanyOrgUnitInfo company = CompanyOrgUnitFactory
						.getRemoteInstance().getCompanyOrgUnitInfo(
								new ObjectUuidPK(orgUnit.getId()), selectors);
				if (STQMUtils.isNotNull(company)) {
					bIsBizUnit = company.isIsBizUnit();
				}
			}
			if (orgUnit instanceof StorageOrgUnitInfo
					&& orgUnit.getId() != null) {
				StorageOrgUnitInfo company = StorageOrgUnitFactory
						.getRemoteInstance().getStorageOrgUnitInfo(
								new ObjectUuidPK(orgUnit.getId()), selectors);
				if (STQMUtils.isNotNull(company)) {
					bIsBizUnit = company.isIsBizUnit();
				}
			}
			if (orgUnit instanceof SaleOrgUnitInfo && orgUnit.getId() != null) {
				SaleOrgUnitInfo company = SaleOrgUnitFactory
						.getRemoteInstance().getSaleOrgUnitInfo(
								new ObjectUuidPK(orgUnit.getId()), selectors);
				if (STQMUtils.isNotNull(company)) {
					bIsBizUnit = company.isIsBizUnit();
				}
			}
			if (orgUnit instanceof PurchaseOrgUnitInfo
					&& orgUnit.getId() != null) {
				PurchaseOrgUnitInfo company = PurchaseOrgUnitFactory
						.getRemoteInstance().getPurchaseOrgUnitInfo(
								new ObjectUuidPK(orgUnit.getId()), selectors);
				if (STQMUtils.isNotNull(company)) {
					bIsBizUnit = company.isIsBizUnit();
				}
			}
			if (orgUnit instanceof HROrgUnitInfo && orgUnit.getId() != null) {
				HROrgUnitInfo company = HROrgUnitFactory.getRemoteInstance()
						.getHROrgUnitInfo(new ObjectUuidPK(orgUnit.getId()),
								selectors);
				if (STQMUtils.isNotNull(company)) {
					bIsBizUnit = company.isIsBizUnit();
				}
			}

		}
		return bIsBizUnit;
	}

	/**
	 * 
	 * 描述：从业务组织委托关系读出强类型的财务组织.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param orgUnit
	 *            业务组织
	 * @return CompanyOrgUnitInfo 财务组织
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-7-26
	 *              <p>
	 */
	public final static CompanyOrgUnitInfo readCompanyOrgUnitInfo(Context ctx,
			OrgUnitInfo orgUnit) throws EASBizException, BOSException {
		return readCompanyOrgUnitInfo(ctx, orgUnit, false);
	}

	/**
	 * 
	 * 描述：从业务组织委托关系读出强类型的财务组织. 注意: 读取是包含常用字段的财务组织.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param orgUnit
	 *            业务组织
	 * @return CompanyOrgUnitInfo 财务组织
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-7-27
	 *              <p>
	 */
	public final static CompanyOrgUnitInfo readFullCompanyOrgUnitInfo(
			Context ctx, OrgUnitInfo orgUnit) throws EASBizException,
			BOSException {
		return readCompanyOrgUnitInfo(ctx, orgUnit, true);
	}

	/**
	 * 
	 * 描述：从业务组织委托关系读出强类型的财务组织. 注意: 读取是包含常用字段的财务组织.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param orgUnit
	 *            业务组织
	 * @param isFull
	 *            是否包含常用字段
	 * @return CompanyOrgUnitInfo 财务组织
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-7-27
	 *              <p>
	 */
	public static CompanyOrgUnitInfo readCompanyOrgUnitInfo(Context ctx,
			OrgUnitInfo orgUnit, boolean isFull) throws EASBizException,
			BOSException {
		CompanyOrgUnitInfo company = null;
		if (orgUnit != null) {
			if (orgUnit instanceof CompanyOrgUnitInfo
					&& orgUnit.getId() != null) {
				company = (CompanyOrgUnitInfo) orgUnit;
				if (isFull
						&& (company.getBaseCurrency() == null
								|| company.getBaseCurrency().getName() == null
								|| !company.getBaseCurrency().containsKey(
										"precision") || company
								.getBaseExchangeTable() == null)) {
					company = fullCompanyOrgUnitInfo(ctx, company.getId()
							.toString());
				}

			} else {
				OrgUnitInfo ou = null;
				if (ctx != null) {
					ou = OrgUnitRelationFactory
							.getLocalInstance(ctx)
							.getToUint(getOrgRelationTypeByOU(orgUnit), orgUnit);
				} else {
					ou = OrgUnitRelationFactory.getRemoteInstance().getToUint(
							getOrgRelationTypeByOU(orgUnit), orgUnit);
				}
				if (ou != null && ou.getId() != null) {
					if (isFull) {
						company = fullCompanyOrgUnitInfo(ctx, ou.getId()
								.toString());
					} else {
						company = new CompanyOrgUnitInfo();
						company.setId(ou.getId());
					}
				}
			}
		}
		return company;
	}

	/**
	 * 
	 * 描述：获取包含常用字段的财务组织.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param Id
	 *            财务组织pk
	 * @return CompanyOrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-7-27
	 *              <p>
	 */
	public static CompanyOrgUnitInfo fullCompanyOrgUnitInfo(Context ctx,
			String Id) throws EASBizException, BOSException {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add(new SelectorItemInfo("id"));
		selectors.add(new SelectorItemInfo("number"));
		selectors.add(new SelectorItemInfo("longNumber"));
		selectors.add(new SelectorItemInfo("name"));
		selectors.add(new SelectorItemInfo("baseCurrency.id"));
		selectors.add(new SelectorItemInfo("baseCurrency.name"));
		selectors.add(new SelectorItemInfo("baseCurrency.precision"));
		selectors.add(new SelectorItemInfo("baseExchangeTable.id"));

		ICompanyOrgUnit ie = null;
		if (ctx == null) {
			ie = CompanyOrgUnitFactory.getRemoteInstance();
		} else {
			ie = CompanyOrgUnitFactory.getLocalInstance(ctx);
		}
		return ie.getCompanyOrgUnitInfo(new ObjectStringPK(Id), selectors);
	}

	/**
	 * 
	 * 获取Server端当前财务组织. 注意: Server端使用,需要Context
	 * 
	 * @param ctx
	 *            服务端上下文.
	 * @return CompanyOrgUnitInfo
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-22
	 *              <p>
	 * @throws EASBizException
	 */
	public static CompanyOrgUnitInfo getCurrentCompanyOrgUnitInfo(Context ctx)
			throws BOSException, EASBizException {
		CompanyOrgUnitInfo company = null;
		// 从Context取当前财务组织Id.
		String id = (String) ctx.get(SysContextConstant.CUR_COMPANY);
		if (id != null && id.trim().length() != 0) {
			company = CompanyOrgUnitFactory.getLocalInstance(ctx)
					.getCompanyOrgUnitInfo(new ObjectStringPK(id));
		}
		return company;
	}

	/**
	 * 
	 * 描述：返回指定财务组织的上级组织集合 注意: Server端使用,需要Context
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param companyId
	 *            财务组织pk
	 * @param bizUnit
	 *            1 - 实体,2 - 虚体, 3 - 所有; see CreditOrgUnitUtils
	 * @param isContainSelf
	 *            是否包含自己
	 * @param sortType
	 *            排序方式 see com.kingdee.bos.metadata.data.SortType
	 * @return CompanyOrgUnitCollection
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-21
	 *              <p>
	 */
	public static CompanyOrgUnitCollection getSuperiorCompanyCollection(
			Context ctx, String companyId, int isBizUnit,
			boolean isContainSelf, SortType sortType) throws EASBizException,
			BOSException {
		return getThreadCompanyCollection(ctx, companyId, isBizUnit,
				isContainSelf, sortType, ORG_OU_UP);
	}

	/**
	 * 
	 * 描述：返回指定财务组织的上级组织集合 注意: Server端使用,需要Context,LongNumber升序
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param companyId
	 *            财务组织pk
	 * @param bizUnit
	 *            1 - 实体,2 - 虚体, 3 - 所有; see CreditOrgUnitUtils
	 * @param isContainSelf
	 *            是否包含自己
	 * @return CompanyOrgUnitCollection
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-21
	 *              <p>
	 */
	public static CompanyOrgUnitCollection getSuperiorCompanyCollection(
			Context ctx, String companyId, int isBizUnit, boolean isContainSelf)
			throws EASBizException, BOSException {
		return getThreadCompanyCollection(ctx, companyId, isBizUnit,
				isContainSelf, SortType.ASCEND, ORG_OU_UP);
	}

	/**
	 * 
	 * 描述：返回指定财务组织的下级组织集合. 注意: Server端使用,需要Context
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param companyId
	 *            财务组织pk
	 * @param bizUnit
	 *            1 - 实体,2 - 虚体, 3 - 所有; see CreditOrgUnitUtils
	 * @param isContainSelf
	 *            是否包含自己
	 * @param sortType
	 *            排序方式 see com.kingdee.bos.metadata.data.SortType
	 * @return CompanyOrgUnitCollection
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-21
	 *              <p>
	 */
	public static CompanyOrgUnitCollection getChildrenCompanyCollection(
			Context ctx, String companyId, int isBizUnit,
			boolean isContainSelf, SortType sortType) throws EASBizException,
			BOSException {
		return getThreadCompanyCollection(ctx, companyId, isBizUnit,
				isContainSelf, sortType, ORG_OU_DOWN);
	}

	/**
	 * 
	 * 描述：返回指定财务组织的下级组织集合. 注意: Server端使用,需要Context,LongNumber升序
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param companyId
	 *            财务组织pk
	 * @param bizUnit
	 *            1 - 实体,2 - 虚体, 3 - 所有; see CreditOrgUnitUtils
	 * @param isContainSelf
	 *            是否包含自己
	 * @return CompanyOrgUnitCollection
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-21
	 *              <p>
	 */
	public static CompanyOrgUnitCollection getChildrenCompanyCollection(
			Context ctx, String companyId, int isBizUnit, boolean isContainSelf)
			throws EASBizException, BOSException {
		return getThreadCompanyCollection(ctx, companyId, isBizUnit,
				isContainSelf, SortType.ASCEND, ORG_OU_DOWN);
	}

	/**
	 * 
	 * 描述：返回指定财务组织的树线索. 注意: Server端使用,需要Context
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param companyId
	 *            财务组织pk
	 * @param bizUnit
	 *            1 - 实体,2 - 虚体, 3 - 所有; see
	 *            com.kingdee.eas.scm.credit.util.CreditOrgUnitUtils
	 * @param isContainSelf
	 *            是否包含自己
	 * @param sortType
	 *            排序方式 see com.kingdee.bos.metadata.data.SortType
	 * @param upOrDown
	 *            1 - 向上追溯,2 - 向下追溯; see
	 *            com.kingdee.eas.scm.credit.util.CreditOrgUnitUtils
	 * @return CompanyOrgUnitCollection
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-8-21
	 *              <p>
	 */
	public static CompanyOrgUnitCollection getThreadCompanyCollection(
			Context ctx, String companyId, int isBizUnit,
			boolean isContainSelf, SortType sortType, int upOrDown)
			throws EASBizException, BOSException {
		if (ctx == null || companyId == null || companyId.trim().length() == 0)
			return null;

		String sp = " \r\n";
		String locale = "_" + LocaleUtils.getLocaleString(ctx.getLocale());

		StringBuffer strSQL = new StringBuffer();

		strSQL.append("SELECT b.FID, b.FNumber, b.FName").append(locale)
				.append(" As FCompanyName, b.FLongNumber, b.FBaseExgTableID, ")
				.append(sp);
		strSQL.append("  b.FBaseCurrencyID, c.FName").append(locale).append(
				" As FCurrencyName, c.FPrecision").append(sp);
		strSQL.append("FROM T_ORG_Company a, T_ORG_Company b, T_BD_Currency c")
				.append(sp);
		strSQL.append("WHERE a.FID = ? And b.FBaseCurrencyID = c.FID ").append(
				sp);

		// 是否包含自己
		if (isContainSelf) {
			if (ORG_OU_DOWN == upOrDown) {
				// 取所有下级
				strSQL
						.append(
								"  And ( (SUBSTRING(b.FLongNumber, 0,LENGTH(a.FLongNumber) + 1) = a.FLongNumber || '!' AND b.FLevel > a.FLevel) OR ")
						.append(sp);
				strSQL.append("  (b.FLongNumber = a.FLongNumber) ) ")
						.append(sp);
			} else if (ORG_OU_UP == upOrDown) {
				// 取所有上级
				strSQL
						.append(
								"  And ( (SUBSTRING(a.FLongNumber, 0,LENGTH(b.FLongNumber) + 1) = b.FLongNumber || '!' AND b.FLevel < a.FLevel) OR ")
						.append(sp);
				strSQL.append("  (b.FLongNumber = a.FLongNumber) ) ")
						.append(sp);
			}

		} else {
			if (ORG_OU_DOWN == upOrDown) {
				// 取所有下级
				strSQL
						.append(
								"  And (SUBSTRING(b.FLongNumber, 0,LENGTH(a.FLongNumber) + 1) = a.FLongNumber || '!' AND b.FLevel > a.FLevel) ")
						.append(sp);
			} else if (ORG_OU_UP == upOrDown) {
				// 取所有上级
				strSQL
						.append(
								"  And (SUBSTRING(a.FLongNumber, 0,LENGTH(b.FLongNumber) + 1) = b.FLongNumber || '!' AND b.FLevel < a.FLevel) ")
						.append(sp);
			}
		}

		// 虚实体选择
		if (isBizUnit == ORG_OU_BIZUNIT) {
			// 取实体
			strSQL.append("  And b.FIsBizUnit = 1").append(sp);
		} else if (isBizUnit == ORG_OU_VIRTUAL) {
			// 取虚体
			strSQL.append("  And b.FIsBizUnit = 0").append(sp);
		}
		// 设置排序
		if (sortType == null
				|| (sortType != null && SortType.ASCEND.getName()
						.equalsIgnoreCase(sortType.getName()))) {
			strSQL.append("Order By b.FLongNumber ASC");
		} else {
			strSQL.append("Order By b.FLongNumber DESC");
		}

		// 包装财务组织集合
		IRowSet rs = DbUtil.executeQuery(ctx, strSQL.toString(),
				new Object[] { companyId });
		CompanyOrgUnitCollection companys = new CompanyOrgUnitCollection();
		if (rs != null) {
			try {
				CompanyOrgUnitInfo company = null;
				while (rs.next()) {
					company = new CompanyOrgUnitInfo();
					// FID
					company.setId(BOSUuid.read(rs.getString("FID")));
					// FNumber
					company.setNumber(rs.getString("FNumber"));
					// FCompanyName
					company.setName(rs.getString("FCompanyName"));
					// FLongNumber
					company.setLongNumber(rs.getString("FLongNumber"));
					// FBaseExgTableID
					if (rs.getString("FBaseExgTableID") != null) {
						// 财务组织上可以不设置基本汇率表(因为可能没有外币业务)
						ExchangeTableInfo exchangeTable = new ExchangeTableInfo();
						exchangeTable.setId(BOSUuid.read(rs
								.getString("FBaseExgTableID")));
						company.setBaseExchangeTable(exchangeTable);
					}
					// FBaseCurrency
					CurrencyInfo baseCurrency = new CurrencyInfo();
					baseCurrency.setId(BOSUuid.read(rs
							.getString("FBaseCurrencyID")));
					baseCurrency.setName(rs.getString("FCurrencyName"));
					baseCurrency.setPrecision(rs.getInt("FPrecision"));
					company.setBaseCurrency(baseCurrency);

					companys.add(company);
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return companys;
	}

	/**
	 * 
	 * 描述：获取组织与主业务组织间的委托关系方向枚举. 比如: 若组织类型为:财务组织,那么主组织对它都是From关系.
	 * 
	 * @param orgType
	 *            组织类型
	 * @param mainOrgType
	 *            主业务组织类型
	 * @return DelegationPartsEnum
	 * @author:daij 创建时间：2007-1-26
	 *              <p>
	 */
	public static DelegationPartsEnum getDelegationDirection(OrgType orgType,
			OrgType mainOrgType) {
		int iMainType = mainOrgType.getValue();
		int iDeleType = orgType.getValue();

		switch (iMainType) {
		case 1:
			return DelegationPartsEnum.THE_FROM_UNIT;
		case 2:
			if (iDeleType == 4) {
				return DelegationPartsEnum.THE_FROM_UNIT;
			} else {
				return DelegationPartsEnum.THE_TO_UNIT;
			}
		case 3:
			if (iDeleType == 1 || iDeleType == 32) {
				return DelegationPartsEnum.THE_TO_UNIT;
			} else {
				return DelegationPartsEnum.THE_FROM_UNIT;
			}
		case 4:
			return DelegationPartsEnum.THE_TO_UNIT;
		}

		return DelegationPartsEnum.THE_TO_UNIT;
	}

	/**
	 * 
	 * 描述：按人员获取其所在部门.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param person
	 *            人员
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-4-28
	 *              <p>
	 */
	public static AdminOrgUnitInfo getAdminOrgUnitInfoByPerson(Context ctx,
			PersonInfo person) throws EASBizException, BOSException {

		AdminOrgUnitInfo adminOrg = null;

		if (STQMUtils.isNotNull(person) && STQMUtils.isNotNull(person.getId())) {
			String personId = person.getId().toString();

			// CU过滤
			CtrlUnitInfo cu = null;
			if (STQMUtils.isNotNull(ctx)) {
				cu = ContextUtil.getCurrentCtrlUnit(ctx);
			} else {
				cu = SysContext.getSysContext().getCurrentCtrlUnit();
			}
			// 查询部门.
			StringBuffer oql = new StringBuffer();
			oql.append("Select id,position.adminOrgUnit.*  ").append(
					"Where person.id = '").append(personId).append(
					"' and isPrimary = 1 ").append(
					"	And position.adminOrgUnit.unitLayerType.id = '").append(
					OrgUtils.LAYERTYPE_DEPT).append("' ");
			if (STQMUtils.isNotNull(cu)) {
				oql.append("	And position.adminOrgUnit.CU.id = '").append(
						cu.getString("id")).append("' ");
			}
			PositionMemberInfo positionMemInfo = PositionMemberFactory
					.getRemoteInstance().getPositionMemberInfo(oql.toString());
			adminOrg = positionMemInfo.getPosition().getAdminOrgUnit();
		}
		return adminOrg;
	}

	// 描述：按OU类型获取与财务组织的委托关系信息
	private static OrgTypeRelationInfo getOrgRelationTypeByOU(
			OrgUnitInfo orgUnit) {
		OrgTypeRelationInfo ri = null;
		if (orgUnit != null) {
			ri = new OrgTypeRelationInfo();
			// FullOrgUnitInfo fullOrg = orgUnit.castToFullOrgUnitInfo();

			if (orgUnit != null) {
				if (orgUnit instanceof PurchaseOrgUnitInfo) {
					ri.setId(BOSUuid.read(ORG_DELEGATE_PURCHARSE_FI));
				} else if (orgUnit instanceof SaleOrgUnitInfo) {
					ri.setId(BOSUuid.read(ORG_DELEGATE_SALE_FI));
				} else if (orgUnit instanceof StorageOrgUnitInfo) {
					ri.setId(BOSUuid.read(ORG_DELEGATE_STORAGE_FI));
				} else if (orgUnit instanceof AdminOrgUnitInfo) {
					ri.setId(BOSUuid.read(ORG_DELEGATE_ADMIN_FI));
				} else if (orgUnit instanceof HROrgUnitInfo) {
					ri.setId(BOSUuid.read(ORG_DELEGATE_HR_FI));
				}

				// if(fullOrg.isIsPurchaseOrgUnit()){
				// ri.setId(BOSUuid.read(ORG_DELEGATE_PURCHARSE_FI));
				// } else
				// if(fullOrg.isIsSaleOrgUnit()){
				// ri.setId(BOSUuid.read(ORG_DELEGATE_SALE_FI));
				// } else
				// if(fullOrg.isIsStorageOrgUnit()){
				// ri.setId(BOSUuid.read(ORG_DELEGATE_STORAGE_FI));
				// } else
				// if(fullOrg.isIsAdminOrgUnit()){
				// ri.setId(BOSUuid.read(ORG_DELEGATE_ADMIN_FI));
				// } else
				// if(fullOrg.isIsHROrgUnit()){
				// ri.setId(BOSUuid.read(ORG_DELEGATE_HR_FI));
				// }
			}
		}
		return ri;
	}

	/**
	 * 获得委托财务组织记账的 库存组织
	 * 
	 * @author zhiwei_wang
	 * @date 2009-6-12
	 * @param ctx
	 * @param companyId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static OrgUnitCollection getStorageByCompany(Context ctx,
			String companyId, boolean isUseCache) throws BOSException,
			EASBizException {
		return getFromOrgs(ctx, companyId, OrgType.STORAGE_VALUE,
				OrgType.COMPANY_VALUE, isUseCache);
	}

	/**
	 * 获取库存组织所委托记账的财务组织
	 * 
	 * @author zhiwei_wang
	 * @date 2009-6-22
	 * @param ctx
	 * @param storageOrgId
	 * @param isUseCache
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static OrgUnitInfo getCompanyByStorage(Context ctx,
			String storageOrgId, boolean isUseCache) throws BOSException,
			EASBizException {
		OrgUnitCollection orgCol = getToOrgs(ctx, storageOrgId,
				OrgType.STORAGE_VALUE, OrgType.COMPANY_VALUE, isUseCache);

		if (orgCol == null || orgCol.size() == 0) {
			throw new STBillException(STBillException.STORAGE_NOTEXIST_COMPANY);
		}

		return orgCol.get(0);
	}

	private static Map orgDelegateFrom_Map = new HashMap();

	/**
	 * 获得委托targetOrgId的 来源组织集合
	 * 
	 * @author zhiwei_wang
	 * @date 2009-6-12
	 * @param ctx
	 * @param companyId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static OrgUnitCollection getFromOrgs(Context ctx,
			String targetOrgId, int fromOrgType, int toOrgType,
			boolean isUseCache) throws BOSException, EASBizException {
		if (StringUtils.isEmpty(targetOrgId)) {
			return null;
		}

		String key = "fromOrgType=" + fromOrgType + ":toOrgType" + toOrgType
				+ ":targetOrgId=" + targetOrgId;

		OrgUnitCollection result = new OrgUnitCollection();
		if (isUseCache) {
			result = (OrgUnitCollection) orgDelegateFrom_Map.get(key);
			if (result != null) {
				return result;
			}
		}

		IOrgUnitRelation iUnitRel = null;
		if (ctx == null) {
			iUnitRel = OrgUnitRelationFactory.getRemoteInstance();
		} else {
			iUnitRel = OrgUnitRelationFactory.getLocalInstance(ctx);
		}
		result = iUnitRel.getFromUnit(targetOrgId, toOrgType, fromOrgType);

		orgDelegateFrom_Map.put(key, result);

		return result;
	}

	private static Map orgDelegateTo_Map = new HashMap();

	/**
	 * 获得委托targetOrgId的 来源组织集合
	 * 
	 * @author zhiwei_wang
	 * @date 2009-6-12
	 * @param ctx
	 * @param companyId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static OrgUnitCollection getToOrgs(Context ctx, String srcOrgId,
			int fromOrgType, int toOrgType, boolean isUseCache)
			throws BOSException, EASBizException {
		if (StringUtils.isEmpty(srcOrgId)) {
			return null;
		}

		String key = "fromOrgType=" + fromOrgType + ":toOrgType" + toOrgType
				+ ":srcOrgId=" + srcOrgId;

		OrgUnitCollection result = new OrgUnitCollection();
		if (isUseCache) {
			result = (OrgUnitCollection) orgDelegateFrom_Map.get(key);
			if (result != null) {
				return result;
			}
		}

		IOrgUnitRelation iUnitRel = null;
		if (ctx == null) {
			iUnitRel = OrgUnitRelationFactory.getRemoteInstance();
		} else {
			iUnitRel = OrgUnitRelationFactory.getLocalInstance(ctx);
		}
		result = iUnitRel.getToUnit(srcOrgId, fromOrgType, toOrgType);

		orgDelegateTo_Map.put(key, result);

		return result;
	}
}
