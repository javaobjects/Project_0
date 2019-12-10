package com.kingdee.eas.st.common.filter;

import java.awt.Component;

import com.kingdee.bos.ctrl.swing.ButtonStates;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;

public class AuditedByCurrentUserFilterElement extends CheckRadioFilterElement {

	private CompareType compareType = CompareType.INNER;

	private String sqlStr = " select ASSIGN.FBIZOBJID from  T_WFR_Assign AS ASSIGN where "
			+ " (((ASSIGN.FSTATE = 2 OR ASSIGN.FSTATE = 1) AND ASSIGN.FBIZPACKAGE = 'com.kingdee.eas.base.multiapprove.client') AND ASSIGN.FBIZFUNCTION = 'MultiApproveUIFunction') AND ASSIGN.FBIZOPERATION = 'ActionSubmit' "
			+ " and ASSIGN.FPERSONUSERID = ";

	public AuditedByCurrentUserFilterElement(String id, Component component) {
		super(id, component);
	}

	public FilterInfo getFilterInfo() {
		Boolean isSelected;
		isSelected = isSelected();
		if (isSelected == null) {
			return null;
		}

		FilterInfo filter = new FilterInfo();
		if (isSelected().booleanValue()) {
			filter.getFilterItems().add(
					new FilterItemInfo(this.getId(), assembleSqlStr(),
							compareType));
		}
		return filter;
	}

	private String assembleSqlStr() {
		String userId = "";
		UserInfo uinfo = (com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
				.getSysContext().getCurrentUserInfo());
		if (uinfo != null) {
			userId = uinfo.getId().toString();
		}
		return this.sqlStr + "'" + userId + "'";
	}

	/**
	 * 是否以审核人身份查看，不能通过这里保存查询方案，需要使用customerparams，已经在STBillBaseFilterUI中做了处理
	 * 
	 * @author zhiwei_wang
	 * @date 2008-10-4
	 * @see com.kingdee.eas.st.common.filter.CheckRadioFilterElement#setData(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void setData(EntityViewInfo entityViewInfo) {

		// KDCheckBox kcb=null;
		// if(this.getComponent() instanceof KDCheckBox){
		// kcb=(KDCheckBox)this.getComponent();
		// }
		// if (entityViewInfo.getFilter()==null)
		// return;
		// if(kcb==null){
		// return ;
		// }
		// FilterItemCollection currentFilterItemCollection =
		// entityViewInfo.getFilter().getFilterItems();
		// for (int i = 0; i < currentFilterItemCollection.size(); i++) {
		// FilterItemInfo filterItemInfo = currentFilterItemCollection.get(i);
		// if(getId().equals(filterItemInfo.getPropertyName())){
		// kcb.setSelected(ButtonStates.SELECTED);
		// return ;
		// }
		// }
		// kcb.setSelected(ButtonStates.UNSELECTED);
	}

	public void clear() {
		// if(defValue != null) {
		// this.setData(component, defValue);
		// }
	}

}
