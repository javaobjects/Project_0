package com.kingdee.eas.st.common.client.utils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.basedata.org.IOrgTypeRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgTypeRelationCollection;
import com.kingdee.eas.basedata.org.OrgTypeRelationFactory;
import com.kingdee.eas.basedata.org.OrgTypeRelationInfo;
import com.kingdee.eas.common.EASBizException;

public class MtOrgUtils {

	/**
	 * 获取指定组织委托或被委托的组织集合Filter orgUnitId 组织ID isFromUnit 参数orgUnitId是否源组织(即委托组织,
	 * true表示orgUnitId为委托组织,false:表示orgUnitId为被委托组织) fromOrgType 委托组织类型
	 * toOrgType 被委托组织类型 userId 用户ID(不为空则同时进行用户业务组织权限控制,为空则不进行权限控制) itemName
	 * 过滤字段名
	 * 
	 * 获取当前用户的代码 SysContext.getSysContext().getCurrentUserInfo()
	 */
	public static FilterInfo getOrgUnitRelationFilter(Context ctx,
			String orgUnitId, boolean isFromUnit, OrgType fromOrgType,
			OrgType toOrgType, String userId, String itemName)
			throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		OrgTypeRelationInfo orgTypeRelationInfo = getOrgTypeRelation(ctx,
				fromOrgType, toOrgType);
		if (orgTypeRelationInfo == null) {
			throw new BOSException("找不到对应的委托关系类型");
		}
		StringBuffer sql = new StringBuffer();
		if (isFromUnit) {// 通过源组织查目标组织
			sql
					.append("select FToUnitID from T_ORG_UnitRelation where FFromUnitID='");
		} else {// 通过目标组织查源组织
			sql
					.append("select FFromUnitID from T_ORG_UnitRelation where FToUnitID='");
		}
		sql.append(orgUnitId).append("' and FTypeRelationID='").append(
				orgTypeRelationInfo.getId().toString()).append("'");
		filter.getFilterItems()
				.add(
						new FilterItemInfo(itemName, sql.toString(),
								CompareType.INNER));
		if (userId != null) {
			filter.mergeFilter(getUserPermisionOrgFilter(userId, itemName),
					"AND");
		}
		return filter;
	}

	// 获取委托关系类型
	public static OrgTypeRelationInfo getOrgTypeRelation(Context ctx,
			OrgType fromOrgType, OrgType toOrgType) throws EASBizException,
			BOSException {
		IOrgTypeRelation iOrgTypeRelation = null;
		OrgTypeRelationInfo info = null;
		if (ctx == null) {
			iOrgTypeRelation = OrgTypeRelationFactory.getRemoteInstance();
		} else {
			iOrgTypeRelation = OrgTypeRelationFactory.getLocalInstance(ctx);
		}
		String oql = "select id where fromType=" + fromOrgType.getValue()
				+ " and toType=" + toOrgType.getValue();
		OrgTypeRelationCollection coll = iOrgTypeRelation
				.getOrgTypeRelationCollection(oql);
		if (coll.size() > 0) {
			info = coll.get(0);
		}
		return info;
	}

	/**
	 * 获取指定用户有权限的业务组织Filter userInfo 用户 itemName 过滤字段名
	 */
	public static FilterInfo getUserPermisionOrgFilter(String userId,
			String itemName) {
		return getUserPermisionOrgFilter(userId,
				OrgRangeType.BIZ_ORG_TYPE_VALUE, itemName);
	}

	/**
	 * 获取指定用户有权限的组织集合Filter userInfo 用户 orgRangeType 用户组织范围类型 itemName 过滤字段名
	 */
	public static FilterInfo getUserPermisionOrgFilter(String userId,
			int orgRangeType, String itemName) {
		FilterInfo filter = new FilterInfo();
		if (userId != null && itemName != null) {
			String sql = "select FOrgID from T_PM_OrgRange where FUserID='"
					+ userId + "' and FType=" + orgRangeType;
			filter.getFilterItems().add(
					new FilterItemInfo(itemName, sql, CompareType.INNER));
		}
		return filter;
	}

}
