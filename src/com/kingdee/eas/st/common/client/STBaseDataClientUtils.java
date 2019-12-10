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
 * ����:�������Ͽͻ��˹�����
 * 
 * @author
 * @version EAS5.1
 */
public class STBaseDataClientUtils {

	public static final String STBASEDATA_RESOURCE = "com.kingdee.eas.st.common.STBaseDataResource";

	public static final String UI_TITLE_SEPRATOR = " - "; // UI����ָ���

	public static final String ADDNEW = "addnew"; // ����

	public static final String EDIT = "edit"; // �޸�

	public static final String VIEW = "view"; // �鿴

	public static final String ENABLE = "enable"; // ����

	public static final String DISENABLE = "disenable"; // ����

	public static final String AUDIT = "audit"; // ��׼

	public static final String UNAUDIT = "unaudit"; // ����׼

	public static final String CLOSED = "closed"; // �ر�

	public static final String UNCLOSED = "unclosed"; // ���ر�

	public static final String DELIVERY = "delivery"; // ����

	public static final String UNDELIVERY = "unDelivery"; // ������

	public static final String DISABLE_SUCCESSED = "disableSuccessed"; // ���óɹ�

	public static final String DISABLE_FAILED = "disableFailed"; // ����ʧ��

	public static final String ENABLE_SUCCESSED = "enableSuccessed"; // ���óɹ�

	public static final String ENABLE_FAILED = "enableFailed"; // ����ʧ��

	public static final String CONFIRM_DISABLE = "confirmDisable"; // ��ȷ�Ͻ���

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
	 * ���ݽ���״̬���ô��ڵı���
	 * 
	 * @param ui
	 *            CoreUIObject
	 * @param bindedEntityName
	 *            String CoreUIObject�󶨵�ҵ����������
	 * @return
	 */
	public static String setupUITitle(String oprtState) {

		// ���ô������ʹ��
		return getResource(STResource, oprtState.toUpperCase());

		// String ADDNEW = getResource(STResource,"ADDNEW");
		// String UPDATE = getResource(STResource,"UPDATE");
		// String VIEW = getResource(STResource,"VIEW");

		// if (ui != null) {
		// if (ui instanceof EditUI) {
		// if (ui.getOprtState().equalsIgnoreCase(OprtState.ADDNEW)) {
		// // ���� - ����
		// ui.setUITitle(ui.getUITitle()+"-"+ADDNEW);
		// } else if (ui.getOprtState().equalsIgnoreCase(OprtState.EDIT)) {
		// // ���� - �޸�
		// ui.setUITitle(ui.getUITitle()+"-"+UPDATE);
		// } else if (ui.getOprtState().equalsIgnoreCase(OprtState.VIEW)) {
		// // ���� - �鿴
		// ui.setUITitle(ui.getUITitle()+"-"+VIEW);
		// }
		// } else {
		// ui.setUITitle(bindedEntityName);
		// }
		//
		// }
	}

	/**
	 * �ж������ֶε�ֵ�Ƿ�Ϊ�գ�
	 * 
	 * @param container����
	 * @return STException
	 */
	public static STException checkRequiredItem(KDLabelContainer[] container) {
		STException exc = null;
		return exc = STRequiredUtils.checkRequiredItem(container);
	}

	/**
	 * �����Ƿ���Ա༭����
	 * 
	 * @param container
	 * @param flag
	 */

	public static void setInputCanEdit(KDLabelContainer[] container,
			boolean flag) {

		for (int i = 0, count = container.length; i < count; i++) {

			JComponent input = container[i].getBoundEditor();

			// �ı�
			if ((input instanceof KDTextField)
					|| (input instanceof JFormattedTextField)) {
				((JTextField) input).setEnabled(flag);

			}

			// �������ı�
			if (input instanceof KDBizMultiLangBox) {
				((KDBizMultiLangBox) input).setEnabled(flag);
			}

			// ����
			if (input instanceof KDDatePicker) {
				((KDDatePicker) input).setEnabled(flag);
			}

			// F7
			if (input instanceof KDBizPromptBox) {
				((KDBizPromptBox) input).setEnabled(flag);
			}

			// ������
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
	 * �������ش˷������������һЩ�����ظ������ֵ
	 */
	public static void setFieldsNull(IObjectValue newData) {
		newData.setString("number", null);
		newData.setString("name", null);
	}

	/**
	 * �ж�һ��������KDBizMultiLangBox�ؼ����û������Ƿ�Ϊ��
	 * 
	 * @param mltBox
	 *            KDBizMultiLangBox
	 * @param editData
	 *            IObjectValue ��EditUI��editData
	 * @param propertyName
	 *            String ��mltBox��ֵ�������������
	 * @return boolean ������Ϊ�ջ�""ʱ����TRUE�����򷵻�FALSE
	 */
	public static boolean isMultiLangBoxInputNameEmpty(
			KDBizMultiLangBox mltBox, IObjectValue editData, String propertyName) {
		// �������Ƿ�Ϸ�
		if (mltBox == null || editData == null || propertyName == null
				|| propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// �жϿؼ����û������Ƿ�Ϊ��
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
	 * �ж�һ��������KDBizMultiLangArea�ؼ����û������Ƿ�Ϊ��
	 * 
	 * @param mltArea
	 *            KDBizMultiLangArea
	 * @param editData
	 *            IObjectValue ��EditUI��editData
	 * @param propertyName
	 *            String ��mltArea��ֵ�������������
	 * @return boolean ������Ϊ�ջ�""ʱ����TRUE�����򷵻�FALSE
	 */
	public static boolean isMultiLangAreaInputDescriptionEmpty(
			KDBizMultiLangArea mltArea, IObjectValue editData,
			String propertyName) {
		// �������Ƿ�Ϸ�
		if (mltArea == null || editData == null || propertyName == null
				|| propertyName.equals("")) {
			throw new IllegalArgumentException();
		}

		// �жϿؼ����û������Ƿ�Ϊ��
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
	 * �õ���ǰ�û��Ĳ�����֯ID��
	 * 
	 * @return String ��ǰ�û��Ĳ�����֯ID��
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
	 * ������ѡ���У�����ʾ�Ի���
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
	 * ���绥���Ƿ�ͨ����
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
	 * �ͷſ�������
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
	 * ������Ϊ��֯����F7���ӡ�������֯������.
	 * 
	 * @param bizBox
	 *            bizBox��һ��ѡ����֯��F7
	 * @author: ����ʱ�䣺
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
