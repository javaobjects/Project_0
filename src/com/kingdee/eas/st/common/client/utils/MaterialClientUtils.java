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
	private Object preOrgUnitInfo = null; // ��֯��Ԫ�ı�֮ǰ��Value
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

		// ����KDTableָ���е�f7
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
	 * �������������ϵ�ѡ��������Ҫ���벿��,���ݲ��Ź������ϵ�ѡ������
	 * 
	 * @param mainOrgInfo
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public void setMaterialF7ByOrgUnit() {

		// ��ȡprmtOrgUnit��ֵ
		OrgUnitInfo mainOrgInfo = null;
		Object o = prmtOrgUnit.getValue();
		if (o instanceof OrgUnitInfo) {
			mainOrgInfo = (OrgUnitInfo) o;
		}

		// SCMGroupClientUtils.isCurrentMainOrgChanged(bizMaterial,
		// QueryInfoConstants.getMaterialQueryOrgId(orgType));

		// ��Ҫ���
		// if (isClearValue) {
		// bizMaterial.setValue(null);
		// }

		// �������ϵ�ѡ������
		CSMF7Utils.setBizMaterialF7(bizMaterial, owner, orgType, mainOrgInfo,
				null, false, true);

	}

	/**
	 * 
	 * �������������ϵ�ѡ��������Ҫ���벿��,���ݲ��Ź������ϵ�ѡ������
	 * 
	 * @param mainOrgInfo
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public void setMaterialF7ByOrgUnit(KDBizPromptBox bizMaterial) {

		// ��ȡprmtOrgUnit��ֵ
		OrgUnitInfo mainOrgInfo = null;
		Object o = prmtOrgUnit.getValue();
		if (o instanceof OrgUnitInfo) {
			mainOrgInfo = (OrgUnitInfo) o;
		}

		// SCMGroupClientUtils.isCurrentMainOrgChanged(bizMaterial,
		// QueryInfoConstants.getMaterialQueryOrgId(orgType));

		// //��Ҫ���
		// if (isClearValue) {
		// bizMaterial.setValue(null);
		// }

		// �������ϵ�ѡ������
		CSMF7Utils.setBizMaterialF7(bizMaterial, owner, orgType, mainOrgInfo,
				null, false, true);

	}

	/**
	 * 
	 * ������ע�����ϵ�ѡ��������Ҫ���벿��,���ݲ��Ź������ϵ�ѡ������
	 * 
	 * @param prmtOrgUnit
	 * @param orgType
	 * @param bizMaterial
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public void registerMaterialF7ByOrgUnit() {

		setMaterialF7ByOrgUnit();

		// ���Ÿı�֮ǰ���沿����Ϣ
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

							// ���û�иı䣬����
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
	 * ����ʵ������֯
	 * 
	 * @param userInfo
	 * @param storageOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizStorageOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			StorageOrgUnitInfo storageOrgUnitInfo) {

		OrgUnitInfo bizStorageOrgUnitInfo = null;

		// �����ʵ�壬ֱ�ӷ���
		if (storageOrgUnitInfo.isIsBizUnit()) {
			bizStorageOrgUnitInfo = storageOrgUnitInfo;
		} else {
			// ����ʵ������֯
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO �ܷ��SCM���ã�����
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
	 * ����ʵ������֯
	 * 
	 * @param userInfo
	 * @param saleOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizSaleOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			SaleOrgUnitInfo saleOrgUnitInfo) {

		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// �����ʵ�壬ֱ�ӷ���
		if (saleOrgUnitInfo.isIsBizUnit()) {
			bizSaleOrgUnitInfo = saleOrgUnitInfo;
		} else {
			// ����ʵ��������֯
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO �ܷ��SCM���ã�����
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
	 * ����ʵ�������֯
	 * 
	 * @param userInfo
	 * @param companyOrgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizCompanyOrgUnit(UserInfo userInfo,
			OrgType orgType, String permissionItem,
			CompanyOrgUnitInfo companyOrgUnitInfo) {

		OrgUnitInfo bizCompanyOrgUnitInfo = null;

		// �����ʵ�壬ֱ�ӷ���
		if (companyOrgUnitInfo.isIsBizUnit()) {
			bizCompanyOrgUnitInfo = companyOrgUnitInfo;
		} else {
			// ����ʵ�������֯
			try {
				OrgUnitInfo[] mainOrgs = null;
				// TODO �ܷ��SCM���ã�����
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
	 * @author xiaofeng_liu ����ʹ�ã��ܶ�ط�����ע�����ϼ���������ֵ��������һ��ʹ�õģ��������ϳ�һ�����õ�ɡ�
	 *         1.ע������ѡ��F7,����֯��Ԫ�仯ʱ���ϸ��ű仯 2.�������ϵ�ѡ������
	 */
	public void registerAndSetMaterialF7ByOrgUnit() {
		this.registerMaterialF7ByOrgUnit();
		this.setMaterialF7ByOrgUnit();
	}
}
