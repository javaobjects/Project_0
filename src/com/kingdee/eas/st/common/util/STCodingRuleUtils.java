package com.kingdee.eas.st.common.util;

import java.awt.Component;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.st.common.STBillBaseCodingRuleVo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;

/**
 * ���������������ࡣ
 * 
 * @author yangyong
 * 
 */
public final class STCodingRuleUtils {
	/**
	 * 
	 * ���������ݱ������ȷ��number�ؼ�����Ч��.�����Ż��ع�
	 * 
	 * @param databinder
	 * @author:xmcy ����ʱ�䣺2007-5-24
	 *              <p>
	 */
	public static void setNumberEnabledByCodeRule(IObjectValue caller,
			DataBinder dataBinder, STBillBaseCodingRuleVo codeVo) {

		// ��ȡ��������趨���Ƿ�����޸�
		boolean isModifiable = false;
		boolean isExist = false;
		try {
			// //�Ƿ�������õı������
			isExist = codeVo.isExist();
			if (isExist) {
				isModifiable = codeVo.isModifiable();
			}
		} catch (Exception e) {

		}

		Component txtNumber = dataBinder.getComponetByField("number");
		if (STUtils.isNotNull(txtNumber)) {
			// ���������ñ��������ߴ��ڵ�֧���޸�ʱ�������޸�number��Ӧ�������
			boolean isEnabled = (!isExist || isModifiable);
			txtNumber.setEnabled(isEnabled);
		}

		if (txtNumber.isEnabled()) {
			txtNumber.requestFocus();
		}

	}

	/**
	 * ���ݱ�������ǲ��Ѿ����ڣ�ȷ��������ҵ����֯�Ƿ�ɱ༭��
	 * 
	 * @param codeVo
	 *            �������
	 * @param mainOrgUnitBox
	 *            ��ҵ����֯PromtBox
	 * @param oprtStatus
	 *            ����״̬
	 */
	public static void setMainOrgUnitEditable(STBillBaseCodingRuleVo codeVo,
			KDBizPromptBox mainOrgUnitBox, BillBaseStatusEnum billStatus) {
		boolean isExit = false;

		try {
			isExit = codeVo.isExist();
		} catch (Exception e) {
			// do nothing
		}

		// ���ڱ���������Ƿ�����״̬���������޸�
		// ����Ƿ�����ҵ����֯ colin 2009-1-13
		if (mainOrgUnitBox != null) {
			if (isExit && !BillBaseStatusEnum.ADD.equals(billStatus)
					&& !BillBaseStatusEnum.NULL.equals(billStatus)) {
				mainOrgUnitBox.setEditable(false);
				mainOrgUnitBox.setEnabled(false);
			} else {
				mainOrgUnitBox.setEditable(true);
				mainOrgUnitBox.setEnabled(true);
			}
		}
	}

	/**
	 * ȷ��������ҵ����֯�Ƿ�ɱ༭,���������޹ء�
	 * 
	 * @param mainOrgUnitBox
	 *            ��ҵ����֯PromtBox
	 * @param oprtStatus
	 *            ����״̬
	 */
	public static void setMainOrgUnitEditable(KDBizPromptBox mainOrgUnitBox,
			BillBaseStatusEnum billStatus) {

		// ���ڱ���������Ƿ�����״̬���������޸�
		// ����Ƿ�����ҵ����֯ colin 2009-1-13
		if (mainOrgUnitBox != null) {
			if (!BillBaseStatusEnum.ADD.equals(billStatus)
					&& !BillBaseStatusEnum.NULL.equals(billStatus)) {
				mainOrgUnitBox.setEditable(false);
				mainOrgUnitBox.setEnabled(false);
			} else {
				mainOrgUnitBox.setEditable(true);
				mainOrgUnitBox.setEnabled(true);
			}
		}
	}

	public static void setNumberEnabledForBaseData(String orgType,
			IObjectValue editData, KDTextField txtNumber,
			KDBizMultiLangBox txtName, String oprtState) {

		// ��ȡ��������趨���Ƿ�����޸�
		if ("ADDNEW".equals(oprtState)) {
			boolean isModifiable = false;
			boolean isExist = false;
			try {
				String companyID = null;
				if (!com.kingdee.util.StringUtils.isEmpty(orgType)
						&& !"NONE".equalsIgnoreCase(orgType)
						&& com.kingdee.eas.common.client.SysContext
								.getSysContext().getCurrentOrgUnit(
										com.kingdee.eas.basedata.org.OrgType
												.getEnum(orgType)) != null) {
					companyID = com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit(
									com.kingdee.eas.basedata.org.OrgType
											.getEnum(orgType)).getString("id");
				} else if (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit())
							.getString("id");
				}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
						.getRemoteInstance();
				// //�Ƿ�������õı������
				if (iCodingRuleManager.isExist(editData, companyID)) {
					isModifiable = iCodingRuleManager.isModifiable(editData,
							companyID);
					isExist = true;
				} else {
					isExist = false;
				}
				if (STUtils.isNotNull(txtNumber)) {
					// ���������ñ��������ߴ��ڵ�֧���޸�ʱ�������޸�number��Ӧ�������
					boolean isEnabled = (!isExist || isModifiable);
					txtNumber.setEnabled(isEnabled);
					txtNumber.setEditable(isEnabled);
				}

				if (txtNumber.isEnabled()) {
					txtNumber.requestFocus();
				} else if (txtName != null && txtName.isEnabled()) {
					txtName.requestFocus();
				}
			} catch (Exception e) {

			}

		} else {
			txtNumber.setEnabled(false);
			txtNumber.setEditable(false);
			if (txtName != null && txtName.isEnabled()) {
				txtName.requestFocus();
			}
		}
	}

	public static String getNumberForClientBaseData(String orgType,
			IObjectValue editData, KDTextField txtNumber,
			KDBizMultiLangBox txtName, String oprtState) throws BOSException,
			EASBizException {

		String number = null;

		if (editData.getString("number") == null) {
			String companyID = null;
			try {
				if (!com.kingdee.util.StringUtils.isEmpty(orgType)
						&& !"NONE".equalsIgnoreCase(orgType)
						&& com.kingdee.eas.common.client.SysContext
								.getSysContext().getCurrentOrgUnit(
										com.kingdee.eas.basedata.org.OrgType
												.getEnum(orgType)) != null) {
					companyID = com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit(
									com.kingdee.eas.basedata.org.OrgType
											.getEnum(orgType)).getString("id");
				} else if (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit())
							.getString("id");
				}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
						.getRemoteInstance();
				if (iCodingRuleManager.isExist(editData, companyID)) {
					if (iCodingRuleManager.isAddView(editData, companyID)) {
						number = iCodingRuleManager.getNumber(editData,
								companyID);
					}
					txtNumber.setEnabled(false);
				}
			} catch (CodingRuleException e) {
				number = "error";
			}

		} else {
			if (editData.getString("number").trim().length() > 0) {
				number = editData.getString("number");
			}
		}

		return number;
	}

	public static boolean isExistNumberForBaseData(Context ctx,
			IObjectValue model) throws BOSException, EASBizException {

		boolean isExist = false;

		String companyID = null;
		if (com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx) != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.util.app.ContextUtil
					.getCurrentOrgUnit(ctx)).getString("id");
		}
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
				.getLocalInstance(ctx);

		// Ϊ�����������Ϊ��ǰ̨ȡ�����̨��ʵ������������ 2006.04.15 wanglh
		if (iCodingRuleManager.isUseIntermitNumber(model, companyID)) {
			isExist = true;
		} else {
			isExist = (model.getString("number") != null);
		}

		return isExist;
	}

	public static String getNumberForBaseData(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		String number = null;

		String companyID = null;
		if (com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx) != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.util.app.ContextUtil
					.getCurrentOrgUnit(ctx)).getString("id");
		}
		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
				.getLocalInstance(ctx);

		// Ϊ�����������Ϊ��ǰ̨ȡ�����̨��ʵ������������ 2006.04.15 wanglh
		if (iCodingRuleManager.isUseIntermitNumber(model, companyID)) {
			if (model.getString("number") == null) {
				number = iCodingRuleManager.getNumber(model, companyID, "");
			}
		} else if (iCodingRuleManager.isExist(model, companyID)) {// ���ù��������
			if (model.getString("number") == null) {
				/**
				 * �����Ұ��߼��ĳɷ��ģ�miller_xiao 2009-01-20 20:20
				 */
				if (!iCodingRuleManager.isAddView(model, companyID)) {// ������ʾ��
																		// ��ǰ̨����
																		// ��
																		// ������û�����ɻ���Ҫ����һ��
					number = iCodingRuleManager.getNumber(model, companyID, "");
				}
			}
		}

		return number;
	}

	public static boolean rolbackNumber(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		String companyID = null;
		if (com.kingdee.eas.util.app.ContextUtil.getCurrentOrgUnit(ctx) != null) {
			companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo) com.kingdee.eas.util.app.ContextUtil
					.getCurrentOrgUnit(ctx)).getString("id");
		}

		com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory
				.getLocalInstance(ctx);

		// �����������֧�ֶϺ�
		if (iCodingRuleManager.isExist(model, companyID)
				&& iCodingRuleManager.isUseIntermitNumber(model, companyID)) {
			return iCodingRuleManager.recycleNumber(model, companyID, model
					.getString("number"));
		}
		return false;
	}

}
