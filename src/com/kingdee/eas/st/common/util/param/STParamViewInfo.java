/*
 * @(#)STParamViewInfo.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.param;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.OrgType;

/**
 * 描述: ST参数取数过滤条件构造器 职责： 1.提供获取各种参数所需的过滤条件EntityViewInfo 2.方法全部为static修饰.
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STParamViewInfo {

	/**
	 * 描述: 获取EntityViewInfo 职责： 1.提供获取各种参数所需的过滤条件EntityViewInfo
	 * 
	 * @author daij date:2006-11-28
	 *         <p>
	 * @version EAS5.2.0
	 */
	public static EntityViewInfo IsAntiWriteViewInfo(String cuId,
			String weighTypeNumber) {

		EntityViewInfo entityViewInfo = new EntityViewInfo();

		FilterInfo filterInfo = new FilterInfo();

		entityViewInfo.getSelector().add(new SelectorItemInfo("Value"));// 添加获取属性

		// 集团控制参数，不需要设置组织
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
	 * 描述: 获取EntityViewInfo 职责： 1.提供获取各种参数所需的过滤条件EntityViewInfo
	 * 
	 * @param billTypeNumber
	 *            单据类型的编码
	 * @param orgUnitId
	 *            单据所属的组织（检斤系统属于库存组织、质检系统属于财务组织）
	 * @return
	 * @author:daij 创建时间：2006-12-19
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
	 * 描述：获取某检斤类型是否需要一检确认EntityViewInfo
	 * 
	 * @param orgUnitId
	 *            组织机构的Id
	 * @param weighTypeNumber
	 *            检斤类型Number
	 * @return EntityViewInfo
	 * @author:daij 创建时间：2007-3-14
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
	 * 描述：获取某检斤类型是否需要一检确认EntityViewInfo
	 * 
	 * @param orgUnitId
	 *            组织机构的Id
	 * @param weighTypeNumber
	 *            检斤类型Number
	 * @return EntityViewInfo
	 * @author:daij 创建时间：2007-3-14
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
	 * 读取普通参数
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
