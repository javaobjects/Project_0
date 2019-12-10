package com.kingdee.eas.st.common.rpt.client;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.st.common.client.OrgUnitClientUtils;
import com.kingdee.eas.st.common.util.PermissionUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public abstract class STCommonReportConditionUI extends CommRptBaseConditionUI {

	private String viewPermItemName = null;

	public STCommonReportConditionUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (STUtils.isNotNull(getMainBizOrgF7())) {
			getMainBizOrgF7().setValue(getBizOrgUnitInfo());
		}
	}

	/**
	 * 描述：返回Query的主业务组织
	 * 
	 * @return
	 * @author:paul 创建时间：2006-8-31
	 *              <p>
	 */
	protected abstract OrgType getMainBizOrgType();

	/**
	 * 返回远程接口和bostype,用于取权限项
	 * 
	 * @return
	 * @throws Exception
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	};

	protected String getEntityBOSType() throws Exception {
		return null;
	};

	// 返回授权的实体组织
	public OrgUnitInfo getBizOrgUnitInfo() {

		// 为了提高性能，只有当新增权限项名称为空时才取
		if (STQMUtils.isNull(viewPermItemName)) {
			viewPermItemName = getViewPermItemName();
		}

		if (STQMUtils.isNull(viewPermItemName)) {
			return null;
		}

		OrgType orgType = getMainBizOrgType();

		// 获得授权的实体组织
		return OrgUnitClientUtils.getBizOrgUnitInfo(viewPermItemName, orgType,
				getMainOrgUnit());

	}

	// 这里返回的可能是虚体组织
	private OrgUnitInfo getMainOrgUnit() {
		OrgUnitInfo orgUnitInfo = null;
		if (STUtils.isNotNull(getMainBizOrgF7())) {
			switch (getMainBizOrgType().getValue()) {
			case OrgType.SALE_VALUE:
				orgUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
				break;
			case OrgType.ADMIN_VALUE:
				orgUnitInfo = SysContext.getSysContext().getCurrentAdminUnit();
				break;
			case OrgType.COMPANY_VALUE:
				orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
				break;
			case OrgType.STORAGE_VALUE:
				orgUnitInfo = SysContext.getSysContext()
						.getCurrentStorageUnit();
				break;
			case OrgType.PURCHASE_VALUE:
				orgUnitInfo = SysContext.getSysContext()
						.getCurrentPurchaseUnit();
				break;
			}
			// public static final int NONE_VALUE = -1;
			// public static final int ADMIN_VALUE = 0;
			// public static final int COMPANY_VALUE = 1;
			// public static final int SALE_VALUE = 2;
			// public static final int PURCHASE_VALUE = 3;
			// public static final int STORAGE_VALUE = 4;
			// public static final int COSTCENTER_VALUE = 5;
			// public static final int PROFITCENTER_VALUE = 6;
			// public static final int UNIONDEBT_VALUE = 8;
			// public static final int CONTROLUNIT_VALUE = 10;
			// public static final int HRO_VALUE = 16;
		}
		return orgUnitInfo;
	}

	protected String getViewPermItemName() {

		String permItemName = null;

		try {
			permItemName = PermissionUtils.getViewPermItemName(
					getBizInterface(), getEntityBOSType());
		} catch (Exception e) {

		}

		return permItemName;
	}

	/**
	 * 描述：校验过滤条件
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#verify()
	 */
	public boolean verify() {
		if (STUtils.isNotNull(getMainBizOrgF7())) {
			if (STUtils.isNull(getMainBizOrgF7().getData())) {
				MsgBox.showInfo(getResource("MAINBIZORG_FIELD_NULL"));
				getMainBizOrgF7().requestFocus();
				return false;
			}
		}
		return true;
	}

	private String getResource(String key) {
		return EASResource.getString("com.kingdee.eas.st.common.STResource",
				key);
	}
}
