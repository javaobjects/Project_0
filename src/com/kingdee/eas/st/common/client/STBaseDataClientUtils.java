package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.basedata.org.OrgType;

import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;

import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.client.utils.STRequiredUtils;

import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:基础资料客户端工具类
 * 
 * @author
 * @version EAS5.1
 */
public class STBaseDataClientUtils {

	public static final String STBASEDATA_RESOURCE = "com.kingdee.eas.st.common.STBaseDataResource";

	public static final String UI_TITLE_SEPRATOR = " - "; // UI标题分隔符

	public static final String ADDNEW = "addnew"; // 新增

	public static final String EDIT = "edit"; // 修改

	public static final String VIEW = "view"; // 查看

	public static final String ENABLE = "enable"; // 启用

	public static final String DISENABLE = "disenable"; // 禁用

	public static final String AUDIT = "audit"; // 核准

	public static final String UNAUDIT = "unaudit"; // 反核准

	public static final String CLOSED = "closed"; // 关闭

	public static final String UNCLOSED = "unclosed"; // 反关闭

	public static final String DELIVERY = "delivery"; // 验收

	public static final String UNDELIVERY = "unDelivery"; // 反验收

	public static final String DISABLE_SUCCESSED = "disableSuccessed"; // 禁用成功

	public static final String DISABLE_FAILED = "disableFailed"; // 禁用失败

	public static final String ENABLE_SUCCESSED = "enableSuccessed"; // 启用成功

	public static final String ENABLE_FAILED = "enableFailed"; // 启用失败

	public static final String CONFIRM_DISABLE = "confirmDisable"; // 请确认禁用

	public static final String ADJUSTREASON = "AdjustReason";

	public static final String CONTRACTSOURCE = "ContractSource";

	public static final String ADJUSTTYPE = "AdjustType";

	public static final String CHANGEREASON = "ChangeReason";

	public static final String CHANGETYPE = "ChangeType";

	public static final String APPORTIONTYPE = "ApportionType";

	public static final String INVITETYPE = "InviteType";

	public static final String LANDDEVELOPER = "LandDeveloper";

	public static final String PAYMENTTYPE = "PaymentType";

	public static final String PRODUCTTYPE = "ProductType";

	public static final String CONTRACTTYPE = "ContractType";

	public static final String CONTRACTDETAILDEF = "ContractDetailDef";

	public static final String PROJECT = "Project";

	public static final String DEDUCTTYPE = "DeductType";

	public static final String COSTACCOUNT = "CostAccount";

	public static final String TARGETTYPE = "TargetType";

	public static final String PROJECTTYPE = "ProjectType";

	public static final String COSTTARGET = "CostTarget";

	public static final String PROJECTSTATUS = "ProjectStatus";

	public static final String JOBTYPE = "JobType";

	public static final String SPECIALTYTYPE = "SpecialtyType";

	public static final String COUNTERCLAIMTYPE = "CounterclaimType";

	public static final String CONTRACTCODINGTYPE = "ContractCodingType";

	public static final String UNITDATAMANAGER = "UnitDataManager";

	public static final String INVALIDCOSTREASON = "InvalidCostReason";

	private final static String STResource = "com.kingdee.eas.st.common.STResource";

	/**
	 * 根据界面状态设置窗口的标题
	 * 
	 * @param ui
	 *            CoreUIObject
	 * @param bindedEntityName
	 *            String CoreUIObject绑定的业务对象的名称
	 * @return
	 */
	public static String setupUITitle(String oprtState) {

		// 设置窗体标题使用
		return getResource(STResource, oprtState.toUpperCase());

		// String ADDNEW = getResource(STResource,"ADDNEW");
		// String UPDATE = getResource(STResource,"UPDATE");
		// String VIEW = getResource(STResource,"VIEW");

		// if (ui != null) {
		// if (ui instanceof EditUI) {
		// if (ui.getOprtState().equalsIgnoreCase(OprtState.ADDNEW)) {
		// // 对象 - 新增
		// ui.setUITitle(ui.getUITitle()+"-"+ADDNEW);
		// } else if (ui.getOprtState().equalsIgnoreCase(OprtState.EDIT)) {
		// // 对象 - 修改
		// ui.setUITitle(ui.getUITitle()+"-"+UPDATE);
		// } else if (ui.getOprtState().equalsIgnoreCase(OprtState.VIEW)) {
		// // 对象 - 查看
		// ui.setUITitle(ui.getUITitle()+"-"+VIEW);
		// }
		// } else {
		// ui.setUITitle(bindedEntityName);
		// }
		//
		// }
	}

	/**
	 * 判断容器字段的值是否为空，
	 * 
	 * @param container数组
	 * @return STException
	 */
	public static STException checkRequiredItem(KDLabelContainer[] container) {
		STException exc = null;
		return exc = STRequiredUtils.checkRequiredItem(container);
	}

	/**
	 * 设置是否可以编辑特性
	 * 
	 * @param container
	 * @param flag
	 */

	public static void setInputCanEdit(KDLabelContainer[] container,
			boolean flag) {

		for (int i = 0, count = container.length; i < count; i++) {

			JComponent input = container[i].getBoundEditor();

			// 文本
			if ((input instanceof KDTextField)
					|| (input instanceof JFormattedTextField)) {
				((JTextField) input).setEnabled(flag);

			}

			// 多语言文本
			if (input instanceof KDBizMultiLangBox) {
				((KDBizMultiLangBox) input).setEnabled(flag);
			}

			// 日期
			if (input instanceof KDDatePicker) {
				((KDDatePicker) input).setEnabled(flag);
			}

			// F7
			if (input instanceof KDBizPromptBox) {
				((KDBizPromptBox) input).setEnabled(flag);
			}

			// 下拉框
			if (input instanceof KDComboBox) {
				((KDComboBox) input).setEnabled(flag);
			}
		}
	}

	private static String getResource(String strResource, String strKey) {
		if (strKey == null || strKey.trim().length() == 0) {
			return null;
		}
		return EASResource.getString(strResource, strKey);
	}

	/**
	 * 子类重载此方法，负责清空一些不能重复的域的值
	 */
	public static void setFieldsNull(IObjectValue newData) {
		newData.setString("number", null);
		newData.setString("name", null);
	}

	/**
	 * 判断一个多语言KDBizMultiLangBox控件中用户输入是否为空
	 * 
	 * @param mltBox
	 *            KDBizMultiLangBox
	 * @param editData
	 *            IObjectValue 绑定EditUI的editData
	 * @param propertyName
	 *            String 绑定mltBox的值对象的属性名称
	 * @return boolean 当输入为空或""时返回TRUE，否则返回FALSE
	 */
	public static boolean isMultiLangBoxInputNameEmpty(
			KDBizMultiLangBox mltBox, IObjectValue editData, String propertyName) {
		// 检查参数是否合法
		if (mltBox == null || editData == null || propertyName == null
				|| propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// 判断控件中用户输入是否为空
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltBox, editData,
				propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.trim().equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断一个多语言KDBizMultiLangArea控件中用户输入是否为空
	 * 
	 * @param mltArea
	 *            KDBizMultiLangArea
	 * @param editData
	 *            IObjectValue 绑定EditUI的editData
	 * @param propertyName
	 *            String 绑定mltArea的值对象的属性名称
	 * @return boolean 当输入为空或""时返回TRUE，否则返回FALSE
	 */
	public static boolean isMultiLangAreaInputDescriptionEmpty(
			KDBizMultiLangArea mltArea, IObjectValue editData,
			String propertyName) {
		// 检查参数是否合法
		if (mltArea == null || editData == null || propertyName == null
				|| propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// 判断控件中用户输入是否为空
		boolean isEmpty = true;
		com.kingdee.bos.ui.util.UIHelper.storeMultiLangFields(mltArea,
				editData, propertyName);

		String inputText = (String) editData.get(propertyName);
		if (inputText == null || inputText.equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * 得到当前用户的财务组织ID串
	 * 
	 * @return String 当前用户的财务组织ID串
	 */
	public static String getUserCompanyOrgIDString() {
		String comOrgString = (String) SysContext.getSysContext()
				.getCurrentFIUnit().getId().toString();
		if (comOrgString == null) {
			throw new RuntimeException();
		}

		return comOrgString;
	}

	/**
	 * “请先选中行！”提示对话框
	 * 
	 * @param owner
	 *            Component
	 */
	public static void plsSelectRowFirst(Component owner) {
		MsgBox.showWarning(owner, EASResource
				.getString(FrameWorkClientUtils.strResource
						+ "Msg_MustSelected"));
	}

	// /**
	// * Login
	// *
	// * @throws Exception
	// */
	// public static void login() throws Exception {
	// LoginHelper.login("test", "test", "eas", "eas", new Locale("L2"),
	// LoginHelper.DB_SQLSERVER);
	// }

	/**
	 * 网络互斥是否通过。
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isMutexControlOK(String id, CoreUI stBaseDataListUI) {
		if (id == null)
			return false;

		try {
			stBaseDataListUI.pubFireVOChangeListener(id);
			return true;
		} catch (Throwable ex) {
			ex.printStackTrace();
			MsgBox.showWarning(stBaseDataListUI, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Error_ObjectUpdateLock_Request"));
			return false;
		}
	}

	/**
	 * 释放控制锁。
	 * 
	 * @param id
	 */
	public static void releaseMutexControl(String id, CoreUI stBaseDataListUI) {
		try {
			String tempString = stBaseDataListUI.getOprtState();
			stBaseDataListUI.setOprtState("RELEASEALL");
			stBaseDataListUI.pubFireVOChangeListener(id);
			stBaseDataListUI.setOprtState(tempString);
		} catch (Throwable ex) {
			stBaseDataListUI.handUIException(ex);
		}
	}

	/**
	 * 
	 * 描述：为组织机构F7增加“虚体组织”过滤.
	 * 
	 * @param bizBox
	 *            bizBox是一个选择组织的F7
	 * @author: 创建时间：
	 *          <p>
	 */
	public static void setOrgF7IsEntity(KDBizPromptBox bizBox, OrgType orgType,
			String[] permissionItemKeyString) {
		OrgUnitClientUtils.setOrgF7IsEntity(bizBox);
		OrgUnitClientUtils.setOrgF7IsEntity(bizBox);
		OrgUnitClientUtils.setMainBizOrgF7(bizBox, orgType,
				permissionItemKeyString);
	}

}
