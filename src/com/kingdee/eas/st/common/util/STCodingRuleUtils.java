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
 * 编码规则钢铁工具类。
 * 
 * @author yangyong
 * 
 */
public final class STCodingRuleUtils {
	/**
	 * 
	 * 描述：根据编码规则确定number控件的有效性.性能优化重构
	 * 
	 * @param databinder
	 * @author:xmcy 创建时间：2007-5-24
	 *              <p>
	 */
	public static void setNumberEnabledByCodeRule(IObjectValue caller,
			DataBinder dataBinder, STBillBaseCodingRuleVo codeVo) {

		// 提取编码规则设定的是否可以修改
		boolean isModifiable = false;
		boolean isExist = false;
		try {
			// //是否存在启用的编码规则
			isExist = codeVo.isExist();
			if (isExist) {
				isModifiable = codeVo.isModifiable();
			}
		} catch (Exception e) {

		}

		Component txtNumber = dataBinder.getComponetByField("number");
		if (STUtils.isNotNull(txtNumber)) {
			// 不存在启用编码规则或者存在但支持修改时，可以修改number对应的输入框
			boolean isEnabled = (!isExist || isModifiable);
			txtNumber.setEnabled(isEnabled);
		}

		if (txtNumber.isEnabled()) {
			txtNumber.requestFocus();
		}

	}

	/**
	 * 根据编码规则是不已经存在，确定控制主业务组织是否可编辑。
	 * 
	 * @param codeVo
	 *            编码规则
	 * @param mainOrgUnitBox
	 *            主业务组织PromtBox
	 * @param oprtStatus
	 *            单据状态
	 */
	public static void setMainOrgUnitEditable(STBillBaseCodingRuleVo codeVo,
			KDBizPromptBox mainOrgUnitBox, BillBaseStatusEnum billStatus) {
		boolean isExit = false;

		try {
			isExit = codeVo.isExist();
		} catch (Exception e) {
			// do nothing
		}

		// 存在编码规则并且是非新增状态，不允许修改
		// 检查是否有主业务组织 colin 2009-1-13
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
	 * 确定控制主业务组织是否可编辑,与编码规则无关。
	 * 
	 * @param mainOrgUnitBox
	 *            主业务组织PromtBox
	 * @param oprtStatus
	 *            单据状态
	 */
	public static void setMainOrgUnitEditable(KDBizPromptBox mainOrgUnitBox,
			BillBaseStatusEnum billStatus) {

		// 存在编码规则并且是非新增状态，不允许修改
		// 检查是否有主业务组织 colin 2009-1-13
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

		// 提取编码规则设定的是否可以修改
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
				// //是否存在启用的编码规则
				if (iCodingRuleManager.isExist(editData, companyID)) {
					isModifiable = iCodingRuleManager.isModifiable(editData,
							companyID);
					isExist = true;
				} else {
					isExist = false;
				}
				if (STUtils.isNotNull(txtNumber)) {
					// 不存在启用编码规则或者存在但支持修改时，可以修改number对应的输入框
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

		// 为了性能问题改为在前台取编码后台其实不会在生成了 2006.04.15 wanglh
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

		// 为了性能问题改为在前台取编码后台其实不会在生成了 2006.04.15 wanglh
		if (iCodingRuleManager.isUseIntermitNumber(model, companyID)) {
			if (model.getString("number") == null) {
				number = iCodingRuleManager.getNumber(model, companyID, "");
			}
		} else if (iCodingRuleManager.isExist(model, companyID)) {// 设置过编码规则
			if (model.getString("number") == null) {
				/**
				 * 这里我把逻辑改成反的，miller_xiao 2009-01-20 20:20
				 */
				if (!iCodingRuleManager.isAddView(model, companyID)) {// 新增显示（
																		// 即前台生成
																		// ）
																		// 在这里没有生成还是要生成一下
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

		// 定义过，并且支持断号
		if (iCodingRuleManager.isExist(model, companyID)
				&& iCodingRuleManager.isUseIntermitNumber(model, companyID)) {
			return iCodingRuleManager.recycleNumber(model, companyID, model
					.getString("number"));
		}
		return false;
	}

}
