/*
 * @(#)STParamViewInfo.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.param;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.OrgType;

/**
 * ����: ST����ȡ���������������� ְ�� 1.�ṩ��ȡ���ֲ�������Ĺ�������EntityViewInfo 2.����ȫ��Ϊstatic����.
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STParamViewInfo {

	/**
	 * ����: ��ȡEntityViewInfo ְ�� 1.�ṩ��ȡ���ֲ�������Ĺ�������EntityViewInfo
	 * 
	 * @author daij date:2006-11-28
	 *         <p>
	 * @version EAS5.2.0
	 */
	public static EntityViewInfo IsAntiWriteViewInfo(String cuId,
			String weighTypeNumber) {

		EntityViewInfo entityViewInfo = new EntityViewInfo();

		FilterInfo filterInfo = new FilterInfo();

		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));// ��ӻ�ȡ����

		// ���ſ��Ʋ���������Ҫ������֯
		// filterInfo.getFilterItems().add(new FilterItemInfo("keyID.subSysID",
		// "com.kingdee.eas.st.basedata.basedata", CompareType.EQUALS));

		// filterInfo.getFilterItems().add(new FilterItemInfo("keyID.orgType",
		// new Integer(OrgType.NONE_VALUE), CompareType.EQUALS));

		// filterInfo.getFilterItems().add(new
		// FilterItemInfo("orgUnitID.id",OrgConstants.DEF_CU_ID,
		// CompareType.EQUALS));

		filterInfo.getFilterItems().add(
				new FilterItemInfo("keyID.number", weighTypeNumber,
						CompareType.EQUALS));

		entityViewInfo.setFilter(filterInfo);

		return entityViewInfo;

	}

	/**
	 * ����: ��ȡEntityViewInfo ְ�� 1.�ṩ��ȡ���ֲ�������Ĺ�������EntityViewInfo
	 * 
	 * @param billTypeNumber
	 *            �������͵ı���
	 * @param orgUnitId
	 *            ������������֯�����ϵͳ���ڿ����֯���ʼ�ϵͳ���ڲ�����֯��
	 * @return
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static EntityViewInfo isAuditAfterSubmitViewInfo(String orgUnitId,
			String billTypeNumber) {
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnitID.id", orgUnitId,
						CompareType.EQUALS));
		filterInfo
				.getFilterItems()
				.add(
						new FilterItemInfo(
								"keyID.number",
								STParamConstant
										.getSTSysParamNumberByType(
												STParamConstant.STSYSPARAMS_KEYID_ISAUDITSUBMIT,
												billTypeNumber),
								CompareType.EQUALS));
		entityViewInfo.setFilter(filterInfo);
		return entityViewInfo;
	}

	/**
	 * 
	 * ��������ȡĳ��������Ƿ���Ҫһ��ȷ��EntityViewInfo
	 * 
	 * @param orgUnitId
	 *            ��֯������Id
	 * @param weighTypeNumber
	 *            �������Number
	 * @return EntityViewInfo
	 * @author:daij ����ʱ�䣺2007-3-14
	 *              <p>
	 */
	public static EntityViewInfo isAffirmViewInfo(String orgUnitId,
			String weighTypeNumber) {
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnitID.id", orgUnitId,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("keyID.number", STParamConstant
						.getSTSysParamNumberByType(
								STParamConstant.STSYSPARAMS_KEYID_ISAFFIRM,
								weighTypeNumber), CompareType.EQUALS));
		entityViewInfo.setFilter(filterInfo);
		return entityViewInfo;
	}

	/**
	 * 
	 * ��������ȡĳ��������Ƿ���Ҫһ��ȷ��EntityViewInfo
	 * 
	 * @param orgUnitId
	 *            ��֯������Id
	 * @param weighTypeNumber
	 *            �������Number
	 * @return EntityViewInfo
	 * @author:daij ����ʱ�䣺2007-3-14
	 *              <p>
	 */
	public static EntityViewInfo isInWarehouse(String orgUnitId, String number) {
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnitID.id", orgUnitId,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("keyID.number", number, CompareType.EQUALS));
		entityViewInfo.setFilter(filterInfo);
		return entityViewInfo;
	}

	/**
	 * ��ȡ��ͨ����
	 * 
	 * @author zhiwei_wang
	 * @date 2008-6-19
	 * @param orgUnitId
	 * @param number
	 * @return
	 */
	public static EntityViewInfo commonEntityView(String orgUnitId,
			String number) {
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("orgUnitID.id", orgUnitId,
						CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("keyID.number", number, CompareType.EQUALS));
		entityViewInfo.setFilter(filterInfo);
		return entityViewInfo;
	}
}
