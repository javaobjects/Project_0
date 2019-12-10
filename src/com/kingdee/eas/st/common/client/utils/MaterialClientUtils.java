package com.kingdee.eas.st.common.client.utils;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;

public class MaterialClientUtils {

	private Component owner = null;
	private KDBizPromptBox prmtOrgUnit = null;
	private OrgType orgType = null;
	private KDBizPromptBox bizMaterial = null;
	private KDTable kdtEntry = null;
	private String columnName = null;
	private Object preOrgUnitInfo = null; // 组织单元改变之前的Value
	boolean isClearValue = false;

	public MaterialClientUtils(Component owner, KDBizPromptBox prmtOrgUnit,
			OrgType orgType, KDTable kdtEntry, String columnName,
			boolean isClearValue) {
		this.owner = owner;
		this.prmtOrgUnit = prmtOrgUnit;
		this.orgType = orgType;
		this.kdtEntry = kdtEntry;
		this.columnName = columnName;
		this.isClearValue = isClearValue;

		// 生成KDTable指定列的f7
		KDBizPromptBox bizMaterial = new KDBizPromptBox();
		Map editors = new HashMap();
		editors.put(columnName, bizMaterial);
		KDTableUtils.setColEditor(kdtEntry, editors);

		this.bizMaterial = bizMaterial;
	}

	public MaterialClientUtils(Component owner, KDBizPromptBox prmtOrgUnit,
			OrgType orgType, KDBizPromptBox bizMaterial, boolean isClearValue) {
		this.owner = owner;
		this.prmtOrgUnit = prmtOrgUnit;
		this.orgType = orgType;
		this.bizMaterial = bizMaterial;
		this.isClearValue = isClearValue;
	}

	/**
	 * 
	 * 描述：设置物料的选择器，需要传入部门,根据部门过滤物料的选择数据
	 * 
	 * @param mainOrgInfo
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu 创建时间：2007-5-31
	 *                  <p>
	 */
	public void setMaterialF7ByOrgUnit() {

		// 提取prmtOrgUnit的值
		OrgUnitInfo mainOrgInfo = null;
		Object o = prmtOrgUnit.getValue();
		if (o instanceof OrgUnitInfo) {
			mainOrgInfo = (OrgUnitInfo) o;
		}

		// SCMGroupClientUtils.isCurrentMainOrgChanged(bizMaterial,
		// QueryInfoConstants.getMaterialQueryOrgId(orgType));

		// 需要清空
		// if (isClearValue) {
		// bizMaterial.setValue(null);
		// }

		// 更新物料的选择数据
		CSMF7Utils.setBizMaterialF7(bizMaterial, owner, orgType, mainOrgInfo,
				null, false, true);

	}

	/**
	 * 
	 * 描述：设置物料的选择器，需要传入部门,根据部门过滤物料的选择数据
	 * 
	 * @param mainOrgInfo
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu 创建时间：2007-5-31
	 *                  <p>
	 */
	public void setMaterialF7ByOrgUnit(KDBizPromptBox bizMaterial) {

		// 提取prmtOrgUnit的值
		OrgUnitInfo mainOrgInfo = null;
		Object o = prmtOrgUnit.getValue();
		if (o instanceof OrgUnitInfo) {
			mainOrgInfo = (OrgUnitInfo) o;
		}

		// SCMGroupClientUtils.isCurrentMainOrgChanged(bizMaterial,
		// QueryInfoConstants.getMaterialQueryOrgId(orgType));

		// //需要清空
		// if (isClearValue) {
		// bizMaterial.setValue(null);
		// }

		// 更新物料的选择数据
		CSMF7Utils.setBizMaterialF7(bizMaterial, owner, orgType, mainOrgInfo,
				null, false, true);

	}

	/**
	 * 
	 * 描述：注册物料的选择器，需要传入部门,根据部门过滤物料的选择数据
	 * 
	 * @param prmtOrgUnit
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu 创建时间：2007-5-31
	 *                  <p>
	 */
	public void registerMaterialF7ByOrgUnit() {

		setMaterialF7ByOrgUnit();

		// 部门改变之前保存部门信息
		prmtOrgUnit
				.addPreChangeListener(new com.kingdee.bos.ctrl.swing.event.PreChangeListener() {

					public void preChange(PreChangeEvent e) {
						preOrgUnitInfo = prmtOrgUnit.getValue();
					}

				});

		prmtOrgUnit
				.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
					public void dataChanged(
							com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
						try {

							// 如果没有改变，返回
							if (preOrgUnitInfo.equals(prmtOrgUnit.getValue())) {
								return;
							}

							setMaterialF7ByOrgUnit();

						} catch (Exception exc) {

						} finally {
						}
					}
				});

	}

	/**
	 * 返回实体库存组织
	 * 
	 * @param userInfo
	 * @param storageOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizStorageOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			StorageOrgUnitInfo storageOrgUnitInfo) {

		OrgUnitInfo bizStorageOrgUnitInfo = null;

		// 如果是实体，直接返回
		if (storageOrgUnitInfo.isIsBizUnit()) {
			bizStorageOrgUnitInfo = storageOrgUnitInfo;
		} else {
			// 返回实体库存组织
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO 能否从SCM引用？？？
				FullOrgUnitCollection collection = SCMGroupClientUtils
						.getAuthOrgByPermItem(new ObjectStringPK(userInfo
								.getId().toString()), orgType, permissionItem);
				mainOrgs = SCMGroupClientUtils.getOrgUnitInfosByType(
						collection, orgType);
				if (mainOrgs == null) {
					mainOrgs = new OrgUnitInfo[] {};
					return null;
				}
				if (mainOrgs.length > 0) {
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
				}
				bizStorageOrgUnitInfo = mainOrgs[0];
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}

		return bizStorageOrgUnitInfo;
	}

	/**
	 * 返回实体库存组织
	 * 
	 * @param userInfo
	 * @param saleOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizSaleOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			SaleOrgUnitInfo saleOrgUnitInfo) {

		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// 如果是实体，直接返回
		if (saleOrgUnitInfo.isIsBizUnit()) {
			bizSaleOrgUnitInfo = saleOrgUnitInfo;
		} else {
			// 返回实体销售组织
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO 能否从SCM引用？？？
				FullOrgUnitCollection collection = SCMGroupClientUtils
						.getAuthOrgByPermItem(new ObjectStringPK(userInfo
								.getId().toString()), orgType, permissionItem);
				mainOrgs = SCMGroupClientUtils.getOrgUnitInfosByType(
						collection, orgType);
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
				// TODO
				e.printStackTrace();
			}
		}

		return bizSaleOrgUnitInfo;
	}

	/**
	 * 返回实体财务组织
	 * 
	 * @param userInfo
	 * @param companyOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizCompanyOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			CompanyOrgUnitInfo companyOrgUnitInfo) {

		OrgUnitInfo bizCompanyOrgUnitInfo = null;

		// 如果是实体，直接返回
		if (companyOrgUnitInfo.isIsBizUnit()) {
			bizCompanyOrgUnitInfo = companyOrgUnitInfo;
		} else {
			// 返回实体财务组织
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO 能否从SCM引用？？？
				FullOrgUnitCollection collection = SCMGroupClientUtils
						.getAuthOrgByPermItem(new ObjectStringPK(userInfo
								.getId().toString()), orgType, permissionItem);
				mainOrgs = SCMGroupClientUtils.getOrgUnitInfosByType(
						collection, orgType);
				if (mainOrgs == null) {
					mainOrgs = new OrgUnitInfo[] {};
					return null;
				}
				if (mainOrgs.length > 0) {
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
				}
				bizCompanyOrgUnitInfo = mainOrgs[0];
			} catch (Exception e) {
				// TODO
				e.printStackTrace();
			}
		}

		return bizCompanyOrgUnitInfo;
	}

	/**
	 * @author xiaofeng_liu 方便使用，很多地方都是注册物料监听和设置值两个方法一起使用的，把它复合成一个更好点吧。
	 *         1.注册物料选择F7,当组织单元变化时物料跟着变化 2.更新物料的选择数据
	 */
	public void registerAndSetMaterialF7ByOrgUnit() {
		this.registerMaterialF7ByOrgUnit();
		this.setMaterialF7ByOrgUnit();
	}
}
