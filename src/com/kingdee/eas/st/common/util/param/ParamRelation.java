package com.kingdee.eas.st.common.util.param;

import com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation;
import java.util.HashMap;
import com.kingdee.bos.ctrl.data.modal.DefObj;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.eas.basedata.org.client.PositionPromptBox2;
import com.kingdee.eas.rpts.ctrlreport.ISqlF7Selector;
import com.kingdee.eas.rpts.ctrlreport.model.CtrlReportFinal;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import java.util.Iterator;

import com.kingdee.bos.ctrl.data.modal.DefObj;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.rpts.ctrlreport.model.CtrlReportUtil;
import com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation;
import com.kingdee.jdbc.rowset.IRowSet;

public class ParamRelation extends AbstractParamRelation {
	public ParamRelation() {
		super();
	}

	/**
	 * 弹出过滤方案界面后，显示过滤值前马上调用，可以手工干预过滤条件 <br>
	 * 例如，如果在过滤方案中有参数为部门的默认值，则可以再这里可以设置人员的过滤条件,基本步骤: <br>
	 * 1、取驱动F7的值：当前变化的F7 <br>
	 * 2、查找目标F7 <br>
	 * 3、通过setXXXfilter或者setFilterString接口把驱动F7的值设置到目标F7
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#loadSolutionAfter()
	 */
	public void loadSolutionAfter() throws Exception {
		// 获得机构F7控件
		Object storageOrgUnitF7 = objectComponents.get("storageOrgUnitID");
		if (storageOrgUnitF7 == null) {
			return; // 如果机构F7控件为空，直接返回
		}

		KDBizPromptBox storageOrgUnitF7Prompt = (KDBizPromptBox) storageOrgUnitF7;
		// 设置过滤器
		FilterInfo filterInfo = getDefaultCUFilter(false);
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		entityViewInfo.setFilter(filterInfo);
		storageOrgUnitF7Prompt.setEntityViewInfo(entityViewInfo);
		storageOrgUnitF7Prompt.getQueryAgent()
				.setEntityViewInfo(entityViewInfo);
	}

	/**
	 * 当前选择的控件值变化，设置其他控件的值或者传入过滤条件(暂时不支持文本框的值变化事件)
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#dataChange(java.lang.String)
	 */
	public void dataChange(String paramName) throws Exception {
		if (paramName.equals("depName")) // 这个名称就是在参数设计器中的名称
		{
		}
	}

	/**
	 * 点击确定按钮，准备执行过滤之前，可以设置过滤条件的值<br>
	 * 例如控制某个日期不输入，默认为本月最后一天 <br>
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#confirmFilterBefore()
	 */
	public boolean confirmFilterBefore() throws Exception {
		// 判断某个条件不符合，返回false，取消执行
		String dateBegin = this.getComponentValue("dateBegin");
		String dateEnd = this.getComponentValue("dateEnd");

		if (dateBegin != null && dateEnd != null
				&& dateBegin.compareTo(dateEnd) > 0) {
			MsgBox.showInfo("开始日期不能大于结束日期");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 描述：过滤制单组织所属CU及其下级CU下，当前用户有权限的销售组织，若销售组织实体的上级销售组织没有授予当前用户，那么在执
	 * 行机构树可能出现离散的树结点，退化得像表格
	 * 
	 * @param isIgnore
	 *            是否进行CU过滤
	 * @return
	 * @author:heng_wang 创建时间：2009-7-14
	 *                   <p>
	 */
	protected FilterInfo getDefaultCUFilter(boolean isIgnore) {
		FilterInfo filter = new FilterInfo();

		if (isIgnore) {
			return filter;
		}

		// 当前CU及其下级CU
		StringBuffer oql = new StringBuffer();
		oql.setLength(0);
		oql
				.append(IFWEntityStruct.objectBase_CU)
				.append("In (")
				.append(
						"Select a.FId From T_org_baseUnit a, T_org_baseUnit b where (a.fnumber = b.fnumber ")
				.append(
						"Or SUBSTRING(a.FLongNumber,0,LENGTH(b.FLongNumber) + 1) = b.FLongNumber || '!') And b.FId ='")
				.append(getUICtrolUnit().getId().toString()).append("' ")
				.append("And a.FIsCU = 1").append(")");
		filter.getFilterItems().add(new FilterItemInfo(oql.toString()));

		// 当前用户有权限的销售组织，包括实体和虚体
		oql.setLength(0);
		oql
				.append(IFWEntityStruct.coreBase_ID)
				.append("In (")
				.append("SELECT DISTINCT BaseUnit.FID")
				.append("FROM T_PM_OrgRange OrgRange ")
				.append(
						"INNER JOIN T_ORG_BaseUnit BaseUnit ON OrgRange.FOrgID = BaseUnit.FID  ")
				.append("WHERE OrgRange.FUserID = '").append(
						SysContext.getSysContext().getCurrentUserInfo().getId()
								.toString()).append("' ").append(
						"AND BaseUnit.FIsSaleOrgUnit = 1 ").append(
						"AND OrgRange.FType = ").append(
						OrgRangeType.BIZ_ORG_TYPE_VALUE).append(
						"AND BaseUnit.FIsAssistantOrg = 0) ");

		filter.getFilterItems().add(new FilterItemInfo(oql.toString()));
		return filter;
	}

	public CtrlUnitInfo getUICtrolUnit() {
		return SysContext.getSysContext().getCurrentCtrlUnit();
	}
}
