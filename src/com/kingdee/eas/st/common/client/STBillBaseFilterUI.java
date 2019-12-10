/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer;
import com.kingdee.bos.ctrl.swing.ButtonStates;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgF7PromptDialog;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.st.common.filter.AuditedByCurrentUserFilterElement;
import com.kingdee.eas.st.common.filter.CheckRadioFilterElement;
import com.kingdee.eas.st.common.filter.CustomQueryFilterManager;
import com.kingdee.eas.st.common.filter.QueryFilterManager;
import com.kingdee.eas.st.common.util.PermissionUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public abstract class STBillBaseFilterUI extends AbstractSTBillBaseFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STBillBaseFilterUI.class);
	// private CustomerParams params= null;

	private QueryFilterManager manager = null;// 查询条件管理器

	private String viewPermItemName = null;

	/**
	 * output class constructor
	 */
	public STBillBaseFilterUI() throws Exception {
		super();
		CustomQueryFilterManager cff = new CustomQueryFilterManager();
		CheckRadioFilterElement c = new AuditedByCurrentUserFilterElement("id",
				this.kDisViewByAuditedCheckBox);
		cff.addCustomeFilterElement(c);
		this.manager = cff;

		// initOrgUnitInfo();
		// params = new CustomerParams();
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();

		if (STUtils.isNotNull(getMainBizOrgF7())) {
			setMainBizF7(getMainBizOrgF7());
			// getMainBizOrgF7().setValue(getBizOrgUnitInfo());
		}
	}

	public String getViewPermItemName() {

		String permItemName = null;

		try {
			permItemName = PermissionUtils.getViewPermItemName(
					getBizInterface(), getEntityBOSType());
		} catch (Exception e) {

		}

		return permItemName;
	}

	protected void setMainBizF7(KDBizPromptBox bizOrgUnitBox) {
		// 支持助记码 paul 2007-1-15
		if (bizOrgUnitBox == null)
			return;

		bizOrgUnitBox.setCommitFormat("$number$;$code$");
		OrgF7PromptDialog model = null;
		// if(this.getMainBizOrgType().equals(OrgType.Sale)){
		// model = new SaleF7();
		// }
		OrgUnitFilterInfoProducer iProducer = null;

		if (model != null) {
			iProducer = (OrgUnitFilterInfoProducer) FilterInfoProducerFactory
					.getOrgUnitFilterInfoProducer(model);
		} else {
			iProducer = (OrgUnitFilterInfoProducer) FilterInfoProducerFactory
					.getOrgUnitFilterInfoProducer(this.getMainBizOrgType());
		}

		String permissionItem = getViewPermItemName();
		if (permissionItem != null && iProducer.getModel() != null) {
			iProducer.getModel().setPermissionItem(permissionItem);
		}
		bizOrgUnitBox.setFilterInfoProducer(iProducer);

	}

	// 返回授权的实体组织
	protected OrgUnitInfo getBizOrgUnitInfo() {

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
		// if(STUtils.isNotNull(getMainBizOrgF7()) &&
		// STUtils.isNull(getMainBizOrgF7().getValue())){
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
			case OrgType.QUALITY_VALUE:
				orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit(
						OrgType.Quality);
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

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	// /**
	// *?
	// * @author:paul
	// * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#
	// getCustomerParams()
	// */
	// public CustomerParams getCustomerParams()
	// {
	// return manager.getCustomerParams();
	// }

	/**
	 * 描述：返回Query的主业务组织
	 * 
	 * @return
	 * @author:paul 创建时间：2006-8-31
	 *              <p>
	 */
	public OrgType getMainBizOrgType() {
		return null;
	}

	/**
	 * 返回远程接口,子类必须实现
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract ICoreBase getBizInterface() throws Exception;

	protected abstract String getEntityBOSType() throws Exception;

	/**
	 * ????
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.scm.common.filter.FilterElement#getFilterInfo()
	 */
	public FilterInfo getFilterInfo() {
		// 处理“以审核人身份查看”控件
		CustomerParams params = getInitCustParams();
		String isViewByAudit = "false";

		if (kDisViewByAuditedCheckBox.isSelected()) {
			isViewByAudit = "true";
		}
		params.addCustomerParam("isViewByAudit", isViewByAudit);
		setCustomerParams(params);

		return manager.getFilterInfo();

	}

	/**
	 * 描述：校验过滤条件
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#verify()
	 */
	public boolean verify() {
		if (manager.verify()) {
			// 如果需要检查主业务组织
			if (MainBizOrgIsRequired()) {
				if (STUtils.isNotNull(getMainBizOrgF7())) {
					if (STUtils.isNull(getMainBizOrgF7().getData())) {
						MsgBox.showError(getResource("MAINBIZORG_FIELD_NULL"));
						getMainBizOrgF7().requestFocus();
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	protected boolean MainBizOrgIsRequired() {
		return true;
	}

	private String getResource(String key) {
		return EASResource.getString("com.kingdee.eas.st.common.STResource",
				key);
	}

	protected CustomerParams getInitCustParams() {
		CustomerParams params = super.getCustomerParams();
		if (params == null) {
			params = new CustomerParams();
		}
		return params;
	}

	/**
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#loadData(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	public void loadData(EntityViewInfo entityViewInfo) {
		super.loadData(entityViewInfo);
		manager.clearAll();
		manager.setData(entityViewInfo);

		// 是否以审核人查看，需要特殊处理
		CustomerParams custParams = getCustomerParams();
		if (new Boolean(custParams.getCustomerParam("isViewByAudit"))
				.booleanValue()) {
			this.kDisViewByAuditedCheckBox.setSelected(ButtonStates.SELECTED);
		} else {
			this.kDisViewByAuditedCheckBox.setSelected(ButtonStates.UNSELECTED);
		}
	}

	public void clear() {
		super.clear();
		manager.clearAll();

		kDisViewByAuditedCheckBox.setSelected(false);

		setDefaultValue();
		setMainBizF7(this.getMainBizOrgF7());
	}

	/**
	 * 描述：设置默认值
	 * 
	 * @author:paul 创建时间：2006-10-17
	 *              <p>
	 */
	protected void setDefaultValue() {

	}

	// // [begin] ?????UI??Context?????????????2006-8-6
	// private Context mainOrgContext = null;
	//    
	// private final void initOrgUnitInfo(){
	// if(getMainBizOrgType() != null)
	// {
	// getUIContext().put(this.getMainBizOrgType(),SysContext.getSysContext().
	// getCurrentOrgUnit(this.getMainBizOrgType()));
	// OrgUnitInfo org =
	// SysContext.getSysContext().getCurrentOrgUnit(this.getMainBizOrgType());
	//	
	// if(org != null && org.getId() != null)
	// {
	// initUIMainOrgContext(org.getId().toString());
	// }
	// }
	// }
	// private final void initUIMainOrgContext(String orgID)
	// {
	// if(orgID == null)
	// {
	// return;
	// }
	// try
	// {
	// if(mainOrgContext == null)
	// {
	// mainOrgContext = new Context();
	// }
	// FrameWorkUtils.switchOrg(mainOrgContext, orgID);
	// if(FrameWorkUtils.getCurrentOrgUnit(mainOrgContext,getMainBizOrgType())
	// != null)
	// {
	// getUIContext().put(this.getMainBizOrgType(),FrameWorkUtils.
	// getCurrentOrgUnit(mainOrgContext,getMainBizOrgType()));
	// }
	// }
	// catch (Exception e)
	// {
	// logger.error(e);
	// super.handUIException(e);
	// }
	// }
	//
	// protected final Context getMainOrgContext()
	// {
	// return mainOrgContext;
	// }
	//
	// protected final Context getUserContext()
	// {
	// return mainOrgContext;
	// }
	// //[end]

	/**
	 * 
	 * 描述：根据传入的属性值构造相应的过滤view
	 * 
	 * @param userFilterInfo
	 * @param property
	 * @param values
	 * @return
	 * @author:dongpeng 创建时间：2006-11-9
	 *                  <p>
	 */
	protected EntityViewInfo setEntityViewInfo(FilterInfo userFilterInfo,
			String property, String values) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = null;
		if (values != null) {
			filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo(property, values, CompareType.INCLUDE));
		}
		if (userFilterInfo != null
				&& userFilterInfo.getFilterItems().size() > 0) {
			try {
				if (filterInfo != null) {
					filterInfo.mergeFilter(userFilterInfo, "and");
				} else {
					view.setFilter(userFilterInfo);
					return view;
				}
			} catch (BOSException e) {
				// e.printStackTrace();
			}
		}
		if (filterInfo != null && filterInfo.getFilterItems().size() > 0) {
			view.setFilter(filterInfo);
			return view;
		}
		return null;
	}

	public QueryFilterManager getManager() {
		return manager;
	}

	public CustomerParams getCustomerParams() {
		CustomerParams cp = super.getCustomerParams();
		if (cp == null) {
			cp = new CustomerParams();
		}

		if (MainBizOrgIsRequired()) {
			if (this.getMainBizOrgF7() != null
					&& this.getMainBizOrgType() != null
					&& this.getMainBizOrgF7().getValue() != null
					&& this.getMainBizOrgF7().getValue() instanceof OrgUnitInfo)
				cp.addCustomerParam(this.getMainBizOrgType().getName(),
						((OrgUnitInfo) this.getMainBizOrgF7().getValue())
								.getId().toString());
		}
		return cp;
	}
}
