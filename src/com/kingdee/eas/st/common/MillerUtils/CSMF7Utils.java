package com.kingdee.eas.st.common.MillerUtils;

import java.awt.Component;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.scm.common.constants.QueryInfoConstants;
import com.kingdee.util.StringUtils;

public abstract class CSMF7Utils {
	public static KDBizPromptBox getBizMaterialF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo,
			String queryInfo, boolean isTreeDisplay) {
		KDBizPromptBox bizMaterialBox = new KDBizPromptBox();

		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, isTreeDisplay);

		return bizMaterialBox;
	}

	public static KDBizPromptBox getBizMaterialTreeF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizMaterialF7(owner, mainBizOrgType, mainBizOrgUnitInfo,
				null, true);
	}

	public static KDBizPromptBox getStdMaterialF7(OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizMaterialF7(null, mainBizOrgType, mainBizOrgUnitInfo, null,
				false);
	}

	public static void setBizMaterialTreeF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, null, true);
	}

	public static void setBizMaterialTreeF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String displayFormat) {
		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, null, true, displayFormat);
	}

	public static void setStdMaterialF7(KDBizPromptBox bizMaterialBox,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		setBizMaterialF7(bizMaterialBox, null, mainBizOrgType,
				mainBizOrgUnitInfo, null, false);
	}

	public static void setBizMaterialF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isTreeDisplay) {
		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, false, isTreeDisplay);
	}

	public static void setBizMaterialF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isTreeDisplay, String displayFormat) {
		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, false, isTreeDisplay,
				displayFormat);
	}

	public static void setBizMaterialF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isMultiSelect, boolean isTreeDisplay) {
		setBizMaterialF7(bizMaterialBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, isMultiSelect, isTreeDisplay,
				"$name$");
	}

	public static void setBizMaterialF7(KDBizPromptBox bizMaterialBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isMultiSelect, boolean isTreeDisplay, String displayFormat) {
		if (STQMUtils.isNull(bizMaterialBox)) {
			bizMaterialBox = new KDBizPromptBox();
		}

		bizMaterialBox.setEditFormat("$number$");

		bizMaterialBox.setDisplayFormat(displayFormat);

		bizMaterialBox.setCommitFormat("$number$;$helpCode$");

		bizMaterialBox.setCurrentMainBizOrgUnit(mainBizOrgUnitInfo,
				mainBizOrgType);

		GeneralKDPromptSelectorAdaptor dpt = null;
		if (isTreeDisplay) {
			dpt = SCMGroupClientUtils.setBizMaterialF7(bizMaterialBox, owner,
					mainBizOrgType, isMultiSelect, queryInfo);
		} else if (StringUtils.isEmpty(queryInfo)) {
			bizMaterialBox.setQueryInfo(QueryInfoConstants
					.getMaterialNoGroupQueryInfo(mainBizOrgType));
		}

		if (STQMUtils.isNull(queryInfo)) {
			bizMaterialBox.setQueryInfo(QueryInfoConstants
					.getMaterialNoGroupQueryInfo(mainBizOrgType));
		}

		// SCMGroupClientUtils.setApproved4MaterialF7(bizMaterialBox,
		// mainBizOrgType);

		// bizMaterialBox.setEnabledMultiSelection(isMultiSelect);

		if (STQMUtils.isNotNull(mainBizOrgUnitInfo)) {
			bizMaterialBox.setCurrentMainBizOrgUnit(mainBizOrgUnitInfo,
					mainBizOrgType);

			// if (dpt != null) dpt.isCurrentMainOrgChanged();
		}
	}

	public static KDBizPromptBox getBizSupplierF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo,
			String queryInfo, boolean isTreeDisplay) {
		KDBizPromptBox bizSupplierBox = new KDBizPromptBox();

		setBizSupplierF7(bizSupplierBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, isTreeDisplay);

		return bizSupplierBox;
	}

	public static KDBizPromptBox getBizSupplierTreeF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizSupplierF7(owner, mainBizOrgType, mainBizOrgUnitInfo,
				null, true);
	}

	public static KDBizPromptBox getStdSupplierF7(OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizSupplierF7(null, mainBizOrgType, mainBizOrgUnitInfo, null,
				false);
	}

	public static void setStdSupplierCUF7(KDBizPromptBox bizSupplierBox,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		setBizSupplierCUF7(bizSupplierBox, null, mainBizOrgUnitInfo);
	}

	public static void setStdSupplierF7(KDBizPromptBox bizSupplierBox,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		setBizSupplierF7(bizSupplierBox, null, mainBizOrgType,
				mainBizOrgUnitInfo, null, false);
	}

	public static void setBizSupplierCUF7(KDBizPromptBox bizSupplierBox,
			Component owner, OrgUnitInfo mainBizOrgUnitInfo) {
		if (STQMUtils.isNull(bizSupplierBox)) {
			bizSupplierBox = new KDBizPromptBox();
		}

		bizSupplierBox.setEditFormat("$number$");

		bizSupplierBox.setDisplayFormat("$name$");

		bizSupplierBox.setCommitFormat("$number$;$mnemonicCode$");

		bizSupplierBox
				.setQueryInfo("com.kingdee.eas.st.common.F7SupplierForCUQuery");
		EntityViewInfo viewInfo = new EntityViewInfo();

		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("assignCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		filter1.getFilterItems().add(
				new FilterItemInfo("Assign.bosObjectType", "37C67DFC",
						CompareType.EQUALS));

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("adminCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		try {
			filter1.mergeFilter(filter2, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}

		viewInfo.setFilter(filter1);

		bizSupplierBox.setEntityViewInfo(viewInfo);
	}

	public static void setBizSupplierCUF7(KDBizPromptBox bizSupplierBox,
			String displayFormat, Component owner,
			OrgUnitInfo mainBizOrgUnitInfo) {
		if (STQMUtils.isNull(bizSupplierBox)) {
			bizSupplierBox = new KDBizPromptBox();
		}

		bizSupplierBox.setEditFormat("$number$");

		bizSupplierBox.setDisplayFormat(displayFormat);

		bizSupplierBox.setCommitFormat("$number$;$mnemonicCode$");

		bizSupplierBox
				.setQueryInfo("com.kingdee.eas.st.common.F7SupplierForCUQuery");
		EntityViewInfo viewInfo = new EntityViewInfo();

		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("assignCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		filter1.getFilterItems().add(
				new FilterItemInfo("Assign.bosObjectType", "37C67DFC",
						CompareType.EQUALS));

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("adminCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		try {
			filter1.mergeFilter(filter2, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}

		viewInfo.setFilter(filter1);

		bizSupplierBox.setEntityViewInfo(viewInfo);
	}

	public static void setBizSupplierTreeF7(KDBizPromptBox bizSupplierBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		setBizSupplierF7(bizSupplierBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, null, true);
	}

	public static void setBizSupplierF7(KDBizPromptBox bizSupplierBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isTreeDisplay) {
		if (STQMUtils.isNull(bizSupplierBox)) {
			bizSupplierBox = new KDBizPromptBox();
		}

		OrgType usedMainBizOrgType = mainBizOrgType;
		OrgUnitInfo usedMainBizOrgUnitInfo = mainBizOrgUnitInfo;

		if ((mainBizOrgType.equals(OrgType.Storage))
				&& (mainBizOrgUnitInfo instanceof StorageOrgUnitInfo))
			try {
				IOrgUnitRelation iUnitRel = OrgUnitRelationFactory
						.getRemoteInstance();
				OrgUnitCollection orgCol = iUnitRel.getToUnit(
						mainBizOrgUnitInfo.getId().toString(), mainBizOrgType
								.getValue(), 1);
				if ((orgCol != null) && (orgCol.size() > 0)) {
					usedMainBizOrgUnitInfo = (CompanyOrgUnitInfo) orgCol.get(0);
					usedMainBizOrgType = OrgType.Company;
				}

			} catch (Exception e1) {
			}

		bizSupplierBox.setEditFormat("$number$");

		bizSupplierBox.setDisplayFormat("$name$");

		bizSupplierBox.setCommitFormat("$number$;$mnemonicCode$");

		if (STQMUtils.isNotNull(queryInfo)) {
			bizSupplierBox.setQueryInfo(queryInfo);
		}

		if (STQMUtils.isNull(bizSupplierBox.getQueryInfo())) {
			bizSupplierBox
					.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");
		}

		GeneralKDPromptSelectorAdaptor dpt = null;
		if (isTreeDisplay) {
			dpt = SCMGroupClientUtils.setBizSupplierF7(bizSupplierBox, owner,
					usedMainBizOrgType, queryInfo);
		} else if (StringUtils.isEmpty(queryInfo)) {
			bizSupplierBox.setQueryInfo(QueryInfoConstants
					.getStdSupplierQueryInfo(usedMainBizOrgType));
			if (QueryInfoConstants.getStdSupplierQueryInfo(usedMainBizOrgType) == null)
				bizSupplierBox
						.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery");

		}

		SCMGroupClientUtils.setApproved4SupplierF7(bizSupplierBox,
				usedMainBizOrgType);

		if (STQMUtils.isNotNull(usedMainBizOrgUnitInfo)) {
			bizSupplierBox.setCurrentMainBizOrgUnit(usedMainBizOrgUnitInfo,
					usedMainBizOrgType);

			if (dpt != null)
				dpt.isCurrentMainOrgChanged();
		}
	}

	public static KDBizPromptBox getBizCustomerF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo,
			String queryInfo, boolean isTreeDisplay) {
		KDBizPromptBox bizCustomerBox = new KDBizPromptBox();

		setBizCustomerF7(bizCustomerBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, isTreeDisplay);

		return bizCustomerBox;
	}

	public static KDBizPromptBox getBizCustomerTreeF7(Component owner,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizCustomerF7(owner, mainBizOrgType, mainBizOrgUnitInfo,
				null, true);
	}

	public static KDBizPromptBox getStdCustomerF7(OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		return getBizCustomerF7(null, mainBizOrgType, mainBizOrgUnitInfo, null,
				false);
	}

	public static void setStdCustomerF7(KDBizPromptBox bizCustomerBox,
			OrgType mainBizOrgType, OrgUnitInfo mainBizOrgUnitInfo) {
		setBizCustomerF7(bizCustomerBox, null, mainBizOrgType,
				mainBizOrgUnitInfo, null, false);
	}

	public static void setBizCustomerTreeF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo) {
		setBizCustomerF7(bizCustomerBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, null, true);
	}

	public static void setBizCustomerTreeF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String displayFormat) {
		setBizCustomerF7(bizCustomerBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, null, true, displayFormat);
	}

	public static void setBizCustomerF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isTreeDisplay) {
		setBizCustomerF7(bizCustomerBox, owner, mainBizOrgType,
				mainBizOrgUnitInfo, queryInfo, isTreeDisplay, "$name$");
	}

	public static void setBizCustomerF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgType mainBizOrgType,
			OrgUnitInfo mainBizOrgUnitInfo, String queryInfo,
			boolean isTreeDisplay, String displayFormat) {
		if (STQMUtils.isNull(mainBizOrgType))
			return;

		if (STQMUtils.isNull(bizCustomerBox)) {
			bizCustomerBox = new KDBizPromptBox();
		}

		bizCustomerBox.setEditFormat("$number$");

		bizCustomerBox.setDisplayFormat(displayFormat);

		bizCustomerBox.setCommitFormat("$number$;$mnemonicCode$");

		if (queryInfo != null)
			bizCustomerBox.setQueryInfo(queryInfo);

		if ((bizCustomerBox.getQueryInfo() == null)
				|| (bizCustomerBox.getQueryInfo().equals(""))) {
			bizCustomerBox
					.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
		}

		SCMGroupClientUtils.setApproved4CustomerF7(bizCustomerBox,
				mainBizOrgType);
		GeneralKDPromptSelectorAdaptor dpt = null;
		if (isTreeDisplay) {
			dpt = SCMGroupClientUtils.setBizCustomerF7(bizCustomerBox, owner,
					mainBizOrgType, queryInfo);
		} else if (StringUtils.isEmpty(queryInfo)) {
			bizCustomerBox.setQueryInfo(QueryInfoConstants
					.getStdCustomerQueryInfo(mainBizOrgType));

			if (QueryInfoConstants.getStdCustomerQueryInfo(mainBizOrgType) == null)
				bizCustomerBox
						.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.CustomerInfoQuery");
		}

		if (STQMUtils.isNotNull(mainBizOrgUnitInfo)) {
			bizCustomerBox.setCurrentMainBizOrgUnit(mainBizOrgUnitInfo,
					mainBizOrgType);

			if (dpt != null)
				dpt.isCurrentMainOrgChanged();
		}
	}

	public static void setBizCustomerCUF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgUnitInfo mainBizOrgUnitInfo) {
		setBizCustomerCUF7(bizCustomerBox, owner, mainBizOrgUnitInfo, "$name$");
	}

	public static void setBizCustomerCUF7(KDBizPromptBox bizCustomerBox,
			Component owner, OrgUnitInfo mainBizOrgUnitInfo,
			String displayFormat) {
		if (STQMUtils.isNull(bizCustomerBox)) {
			bizCustomerBox = new KDBizPromptBox();
		}

		bizCustomerBox.setEditFormat("$number$");

		bizCustomerBox.setDisplayFormat(displayFormat);

		bizCustomerBox.setCommitFormat("$number$;$mnemonicCode$");

		bizCustomerBox
				.setQueryInfo("com.kingdee.eas.st.common.F7CustomerForCUQuery");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("assignCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		filter1.getFilterItems().add(
				new FilterItemInfo("Assign.bosObjectType", "BF0C040E",
						CompareType.EQUALS));

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("adminCU.id", mainBizOrgUnitInfo.getCU()
						.getId().toString(), CompareType.EQUALS));
		try {
			filter1.mergeFilter(filter2, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}
		viewInfo.setFilter(filter1);
		bizCustomerBox.setEntityViewInfo(viewInfo);
	}

	public static void setBizMaterialF7(KDBizPromptBox f7, Component owner,
			boolean isMultiSelect, boolean isTreeDisplay, String displayFormat) {
		f7.setEditFormat("$number$");

		f7.setDisplayFormat(displayFormat);

		f7.setCommitFormat("$number$;$helpCode$");

		if (isTreeDisplay) {
			GeneralKDPromptSelectorAdaptor selectorLisenter = new GeneralKDPromptSelectorAdaptor(
					f7,
					"com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI",
					owner,
					"com.kingdee.eas.basedata.master.material.app.F7MaterialQuery");

			selectorLisenter.setIsMultiSelect(isMultiSelect);
			f7.setSelector(selectorLisenter);
			f7.addSelectorListener(selectorLisenter);
			selectorLisenter.setQueryProperty("helpCode", "or");
		} else {
			f7
					.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
		}
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("status", "1", CompareType.EQUALS));

		viewInfo.setFilter(filter);
		f7.setEntityViewInfo(viewInfo);
		f7.setEnabledMultiSelection(isMultiSelect);
	}

	public static void setBizMaterialF7(KDBizPromptBox f7, Component owner,
			boolean isMultiSelect, boolean isTreeDisplay) {
		setBizMaterialF7(f7, owner, isMultiSelect, isTreeDisplay, "$number$");
	}

	public static void setBizMaterialCUF7(KDBizPromptBox f7,
			boolean isMultiSelect, String cuID, String displayFormat) {
		// f7.setEditFormat("$number$");
		//
		// f7.setDisplayFormat(displayFormat);
		//
		// f7.setCommitFormat("$number$;$helpCode$");

		f7.setQueryInfo("com.kingdee.eas.st.common.F7MaterialForCUQuery");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("status", "1", CompareType.EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("BrowserGroup.deletedStatus",
						new Integer(1), CompareType.EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("BrowserGroup.deletedStatus", null,
						CompareType.EMPTY));

		filter.getFilterItems().add(
				new FilterItemInfo("assignCU.id", cuID, CompareType.EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("Assign.bosObjectType", "4409E7F0",
						CompareType.EQUALS));

		filter.getFilterItems().add(
				new FilterItemInfo("adminCU.id", cuID, CompareType.EQUALS));

		filter.setMaskString("#0 and (#1 or #2) and ((#3 and #4) or #5)");

		viewInfo.setFilter(filter);
		f7.setEntityViewInfo(viewInfo);
	}

	public static void setBizMaterialCUF7(KDBizPromptBox f7,
			boolean isMultiSelect, String CUID) {
		setBizMaterialCUF7(f7, isMultiSelect, CUID, "$name$");
	}
}