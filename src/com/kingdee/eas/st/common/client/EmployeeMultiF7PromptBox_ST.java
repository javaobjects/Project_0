package com.kingdee.eas.st.common.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.HashMap;
import java.util.HashSet;

import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.core.hr.client.f7.F7ParamOnRegister;
import com.kingdee.eas.base.core.hr.client.f7.IF7ParamFromUI;
import com.kingdee.eas.base.core.hr.client.f7.IF7Support;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.hr.base.F7ScopeEnum;
import com.kingdee.eas.hr.emp.EmployeeException;
import com.kingdee.eas.hr.org.client.AdminByOrgRangeF7UI;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * ������ְԱF7
 * 
 * @author wxyxl date: 2006-6-19
 *         <p>
 * @version EAS6.0SP
 */
public class EmployeeMultiF7PromptBox_ST extends KDCommonPromptDialog implements
		IF7Support {
	protected IUIWindow currOrgTreeDialog;

	HashMap ctx = new HashMap();

	private boolean isClearHRO = false;

	// F7������ز���
	private IF7ParamFromUI f7ParamFromUI = null;
	private F7ParamOnRegister f7ParamOnRegister = null;
	protected FilterInfo customFilter = null;
	private FilterInfo additionalFilter = null;

	public EmployeeMultiF7PromptBox_ST() {
		ctx.put(UIContext.OWNER, this);
	}

	/**
	 * @param owner
	 */
	public EmployeeMultiF7PromptBox_ST(Frame owner) {
		super(owner);
		ctx.put(UIContext.OWNER, this);
	}

	public EmployeeMultiF7PromptBox_ST(IUIObject owner) {
		ctx.put(UIContext.OWNER, owner);
	}

	public EmployeeMultiF7PromptBox_ST(IUIObject owner, HashMap hash) {
		if (hash != null)
			ctx = hash;
		else
			ctx = new HashMap();
		ctx.put(UIContext.OWNER, owner);
	}

	/**
	 * @param owner
	 */
	public EmployeeMultiF7PromptBox_ST(Dialog owner) {
		super(owner);
		ctx.put(UIContext.OWNER, this);
	}

	public EmployeeMultiF7PromptBox_ST(HashMap hash) {
		if (hash != null)
			ctx = hash;
		else
			ctx = new HashMap();
		ctx.put(UIContext.OWNER, this);

	}

	/**
	 * �����Ƿ�ֻ��ʾ��н��Ա
	 * 
	 * @param isHrCmp
	 */
	public void showOnlyPayPerson(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.SHOW_PAYPERSON, Boolean.valueOf(flag));
	}

	/**
	 * �����Ƿ�ֻ��ʾ��������Ա
	 * 
	 * @param flag
	 */
	public void showOnlyTryoutPerson(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.SHOW_TRYOUTPERSON, Boolean.valueOf(flag));
	}

	/**
	 * �����Ƿ�ֻ��ʾ�μ��籣��Ա
	 * 
	 * @param isHrCmp
	 */
	public void showOnlySocietyPerson(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.SHOW_SOCIETY, Boolean.valueOf(flag));
	}

	/**
	 * �����Ƿ�ֻ��ʾ�ɲ� (add by jie_fan)
	 * 
	 * @param flag
	 */
	public void showOnlyCadre(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.SHOW_CADRE, Boolean.valueOf(flag));
	}

	/**
	 * �����Ƿ�Ϊ��ѡ
	 * 
	 * @param isSingle
	 */
	public void setIsSingleSelect(boolean isSingle) {
		ctx.put(EmployeeMultiF7UI_ST.IS_SINGLE_SELECT, Boolean
				.valueOf(isSingle));
	}

	/**
	 * �����Ƿ���ʾ���е�������֯
	 * 
	 * @param isShowAllAdmin
	 */
	public void setIsShowAllAdmin(boolean isShowAllAdmin) {
		ctx.put(EmployeeMultiF7UI_ST.IS_SHOW_ALL_ADMIN, Boolean
				.valueOf(isShowAllAdmin));
	}

	/**
	 * �Ƿ���ʾ��ְλ��Ա
	 * 
	 * @param flag
	 */
	public void showNoPositionPerson(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.SHOW_NO_POSITION_PERSON, Boolean
				.valueOf(flag));
	}

	/**
	 * ��showNoPositionPerson(true)������ʾ��ְλ��Աʱ,��������Ч,�����ù�������.
	 * 
	 * @param filter
	 */
	public void setNopositionPersonFilter(FilterInfo filter) {
		ctx.put(EmployeeMultiF7UI_ST.NO_POSOTION_PERSON_FILTER, filter);
	}

	/**
	 * �����Ƿ��ܲ���(ת���춯��ְ���˴�ְ������ʱ��ְ��Ա�Ƿ���ڼ�ְ��λ�Ϳ���ѡ��)����
	 * 
	 * @author xianfeng_zhu 2009-10-03
	 * @param IsPluralityIsChooseContral
	 */
	public void setIsPluralityIsChooseContral(boolean IsPluralityIsChooseContral) {
		ctx.put(EmployeeMultiF7UI_ST.IS_PLURALITY_IS_CHOOSE_CONTRAL, Boolean
				.valueOf(IsPluralityIsChooseContral));
	}

	public void hideAdminTreeTab(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.HIDE_ADMINTREE_TAB, Boolean.valueOf(flag));
	}

	/**
	 * ���÷��ص�ְλ����֯��ѡ�еĻ���ְԱ����Ҫְλ����Ҫ��֯ flagΪ���Ƿ���ְԱ����Ҫְλ����Ҫ��֯
	 * 
	 * @param flag
	 */
	public void SetReturnPositionAndUnit(boolean flag) {
		ctx.put(EmployeeMultiF7UI_ST.RETURN_POSITION_UNIT, Boolean
				.valueOf(flag));
	}

	/**
	 * ����Ĭ��ѡ�е�ְλ
	 * 
	 * @param position
	 */
	public void setSelectDefaultPostion(PositionInfo position) {
		if (position == null) {
			return;
		}
		ctx.put(EmployeeMultiF7UI_ST.DEFAULT_POSITION, position);
	}

	/**
	 * ����ֻ��HR��֯���룬�������û���Χ����
	 * 
	 * @param isHRFilter
	 */
	public void setIsHRFilter(boolean isHRFilter) {
		ctx.put(EmployeeMultiF7UI_ST.IS_HR_FILTER, Boolean.valueOf(isHRFilter));
	}

	/**
	 * ����F7��ͨ���ĸ�HR��֯�������ݣ�Ĭ�ϵ�ǰHR��֯���û�������֯��Χ�Ľ�����
	 * 
	 * @param hroId
	 */
	public void setHROFilter(HashSet hroSet) {
		if (hroSet == null || hroSet.size() == 0 || isClearHRO)
			return;

		setUserOrgRangeFilter(true);
		ctx.put(EmployeeMultiF7UI_ST.HRO_SET, hroSet);
	}

	public void clearHROFilter() {
		isClearHRO = true;
		ctx.remove(AdminByOrgRangeF7UI.HRO_SET);
	}

	/**
	 * �����Ƿ�ֻ����������֯��Χ����
	 * 
	 * @param flag
	 */
	public void setUserOrgRangeFilter(boolean flag) {
		ctx
				.put(EmployeeMultiF7UI_ST.SHOW_USER_ORG_RANGE, Boolean
						.valueOf(flag));
	}

	public void show() {
		IUIFactory uiFactory = null;
		try {
			if (ctx.get(EmployeeMultiF7UI_ST.IS_SHOW_ALL_ADMIN) != null
					&& ((Boolean) ctx
							.get(EmployeeMultiF7UI_ST.IS_SHOW_ALL_ADMIN))
							.booleanValue()) {
			} else {
				if (SysContext.getSysContext().getCurrentHRUnit() == null) {
					if (ctx != null
							&& ctx.get(EmployeeMultiF7UI_ST.HRO_SET) != null) {
						if (((HashSet) ctx.get(EmployeeMultiF7UI_ST.HRO_SET))
								.size() <= 0) {
							throw new EmployeeException(
									EmployeeException.HRORG_NOT_FOUND);
						}
					} else {
						throw new EmployeeException(
								EmployeeException.HRORG_NOT_FOUND);
					}
				}
			}

			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			this.ctx.put(EmployeeMultiF7UI_ST.FILTER_INFO, customFilter);
			currOrgTreeDialog = uiFactory.create(EmployeeMultiF7UI_ST.class
					.getName(), ctx);

			// if (getAdditionalFilter() != null) {
			// ((EmployeeMultiF7UI_ST)
			// currOrgTreeDialog.getUIObject()).setAdditionalFilter
			// (getAdditionalFilter());
			// ((EmployeeMultiF7UI_ST)
			// currOrgTreeDialog.getUIObject()).setPersonFilterInfo();
			// }

			currOrgTreeDialog.show();
		} catch (Exception ex) {
			ExceptionHandler.handle(ex);
		}
	}

	public boolean isCanceled() {
		if (currOrgTreeDialog == null) {
			return true;
		} else {
			return ((EmployeeMultiF7UI_ST) currOrgTreeDialog.getUIObject()).isCanceled;
		}
	}

	public Object getData() {
		if ((ctx.get(EmployeeMultiF7UI_ST.IS_SINGLE_SELECT) == null)
				|| (ctx.get(EmployeeMultiF7UI_ST.IS_SINGLE_SELECT) != null
						&& ctx.get(EmployeeMultiF7UI_ST.IS_SINGLE_SELECT) instanceof Boolean && ((Boolean) ctx
						.get(EmployeeMultiF7UI_ST.IS_SINGLE_SELECT))
						.booleanValue())) {
			Object[] obj = new Object[1];
			obj[0] = ((EmployeeMultiF7UI_ST) currOrgTreeDialog.getUIObject())
					.getSingleResult();
			return obj;
		} else {
			PersonCollection personColl = ((EmployeeMultiF7UI_ST) currOrgTreeDialog
					.getUIObject()).getMultiResult();
			return personColl.toArray();
		}
	}

	public IF7ParamFromUI getF7ParamFromUI() {
		return this.f7ParamFromUI;
	}

	public F7ParamOnRegister getF7ParamOnRegister() {
		return this.f7ParamOnRegister;
	}

	public void setF7ParamFromUI(IF7ParamFromUI param) {
		this.f7ParamFromUI = param;
	}

	public void setF7ParamOnRegister(F7ParamOnRegister param) {
		this.f7ParamOnRegister = param;
	}

	public void setScope(F7ScopeEnum scope, IF7ParamFromUI param) {
		if (F7ScopeEnum.GROUP.equals(scope)) {
			this.ctx.remove(EmployeeMultiF7UI_ST.IS_HR_FILTER);
			this.ctx.remove(EmployeeMultiF7UI_ST.SHOW_USER_ORG_RANGE);
			this.ctx.remove(EmployeeMultiF7UI_ST.HRO_SET);
			this.setIsShowAllAdmin(true);
		} else if (F7ScopeEnum.UserAdminOrgRange.equals(scope)) {
			this.ctx.remove(EmployeeMultiF7UI_ST.IS_SHOW_ALL_ADMIN);
			this.ctx.remove(EmployeeMultiF7UI_ST.IS_HR_FILTER);
			this.ctx.remove(EmployeeMultiF7UI_ST.SHOW_USER_ORG_RANGE);
			clearHROFilter();
			this.setUserOrgRangeFilter(true);
		} else if (F7ScopeEnum.BusinessAndUser.equals(scope)) {
			this.ctx.remove(EmployeeMultiF7UI_ST.IS_SHOW_ALL_ADMIN);
			this.ctx.remove(EmployeeMultiF7UI_ST.IS_HR_FILTER);
			this.isClearHRO = false;
			this.setHROFilter(param.getBizOrgIDs());
		}
	}

	// �����������
	public void setFilter(FilterInfo filter) {
		customFilter = filter;
	}

	public void setPreSelect(Object[] preSelect) {
		this.ctx.put(EmployeeMultiF7UI_ST.PRE_SELECT, preSelect);
	}

	public FilterInfo getAdditionalFilter() {
		return additionalFilter;
	}

	public void setAdditionalFilter(FilterInfo filterInfo) {
		additionalFilter = filterInfo;
	}
}
