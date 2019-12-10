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
	 * ��ȡָ����֯ί�л�ί�е���֯����Filter orgUnitId ��֯ID isFromUnit ����orgUnitId�Ƿ�Դ��֯(��ί����֯,
	 * true��ʾorgUnitIdΪί����֯,false:��ʾorgUnitIdΪ��ί����֯) fromOrgType ί����֯����
	 * toOrgType ��ί����֯���� userId �û�ID(��Ϊ����ͬʱ�����û�ҵ����֯Ȩ�޿���,Ϊ���򲻽���Ȩ�޿���) itemName
	 * �����ֶ���
	 * 
	 * ��ȡ��ǰ�û��Ĵ��� SysContext.getSysContext().getCurrentUserInfo()
	 */
	public static FilterInfo getOrgUnitRelationFilter(Context ctx,
			String orgUnitId, boolean isFromUnit, OrgType fromOrgType,
			OrgType toOrgType, String userId, String itemName)
			throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		OrgTypeRelationInfo orgTypeRelationInfo = getOrgTypeRelation(ctx,
				fromOrgType, toOrgType);
		if (orgTypeRelationInfo == null) {
			throw new BOSException("�Ҳ�����Ӧ��ί�й�ϵ����");
		}
		StringBuffer sql = new StringBuffer();
		if (isFromUnit) {// ͨ��Դ��֯��Ŀ����֯
			sql
					.append("select FToUnitID from T_ORG_UnitRelation where FFromUnitID='");
		} else {// ͨ��Ŀ����֯��Դ��֯
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

	// ��ȡί�й�ϵ����
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
	 * ��ȡָ���û���Ȩ�޵�ҵ����֯Filter userInfo �û� itemName �����ֶ���
	 */
	public static FilterInfo getUserPermisionOrgFilter(String userId,
			String itemName) {
		return getUserPermisionOrgFilter(userId,
				OrgRangeType.BIZ_ORG_TYPE_VALUE, itemName);
	}

	/**
	 * ��ȡָ���û���Ȩ�޵���֯����Filter userInfo �û� orgRangeType �û���֯��Χ���� itemName �����ֶ���
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
