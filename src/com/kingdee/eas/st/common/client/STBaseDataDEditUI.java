/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.*;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectValue;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.basedata.mm.qm.ClientUIException;
import com.kingdee.eas.basedata.mm.qm.client.utils.ClientUIUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;

import com.kingdee.eas.framework.client.ListUI;

import com.kingdee.eas.st.common.ISTDataBaseD;
import com.kingdee.eas.st.common.STBasedataException;
import com.kingdee.eas.st.common.STDataBaseDInfo;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
abstract public class STBaseDataDEditUI extends AbstractSTBaseDataDEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STBaseDataDEditUI.class);

	protected STDataBaseDInfo baseDataInfo;

	// �Ƿ��������ϵͳԤ������
	static boolean canCancel = true;

	/**
	 * output class constructor
	 */
	public STBaseDataDEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private void setAuditButton() {
		if (editData instanceof STDataBaseDInfo) {
			if (((STDataBaseDInfo) editData).isIsAudited()) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
			} else {
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
			}
		}
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// ȥ��license���飬��ɾ��

		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(true);
		chkMenuItemSubmitAndAddNew.setVisible(true);

		String oprtState2 = getOprtState();
		baseDataInfo = getEditData();
		if (baseDataInfo != null) {
			setAferActionBtn(oprtState2, true);
		}

	}

	// ���������İ�ť״̬
	protected void setAferActionBtn(String actionType, boolean flag) {

		boolean isEnabled = this.baseDataInfo.isIsEnabled();
		boolean isAudited = this.baseDataInfo.isIsAudited();
		setEditControl(false);
		if (STBaseDataClientUtils.ENABLE.equals(actionType)) {
			// this.btnCancelCancel.setVisible(!flag);
			this.btnCancelCancel.setEnabled(!flag);
			// this.btnCancel.setVisible(flag);
			this.btnCancel.setEnabled(flag);
		}

		if (STBaseDataClientUtils.AUDIT.equals(actionType)) {
			// this.btnUnAudit.setVisible(flag);
			this.btnUnAudit.setEnabled(flag);
			// this.btnAudit.setVisible(!flag);
			this.btnAudit.setEnabled(!flag);
			this.btnSubmit.setEnabled(false);
			this.btnEdit.setEnabled(true);
		}

		if (STATUS_EDIT.equals(actionType)) {
			btnCancelCancel.setEnabled(!isEnabled);
			btnCancel.setEnabled(isEnabled);
			btnAudit.setEnabled(!isAudited);
			btnUnAudit.setEnabled(isAudited);
			this.btnSubmit.setEnabled(true);
		}

		if (STATUS_VIEW.equals(actionType)) {

			btnCancel.setEnabled(isEnabled);
			actionCancel.setEnabled(isEnabled);
			btnCancelCancel.setEnabled(!isEnabled);
			actionCancelCancel.setEnabled(!isEnabled);
			actionAudit.setEnabled(!isAudited);
			actionUnAudit.setEnabled(isAudited);
			this.btnAudit.setEnabled(!isAudited);
			this.btnUnAudit.setEnabled(isAudited);
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			// this.btnRemove.setEnabled(false);

			// if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().
			// getCurrentCtrlUnit().getId().toString())) {
			// this.btnAddNew.setEnabled(true);
			// this.btnEdit.setEnabled(true);
			// } else {
			// this.btnAddNew.setEnabled(false);
			// this.btnEdit.setEnabled(false);
			// this.btnRemove.setEnabled(false);
			// }
		}

		if (STATUS_ADDNEW.equals(actionType)) {
			btnCancel.setEnabled(false);
			actionCancel.setEnabled(false);
			btnCancelCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnAudit.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			setEditControl(true);
		}

	}

	// �����д�÷�������ֻ��Ҫ�༭ʱ��ҵ����������
	protected KDLabelContainer[] getEditControl() {
		return null;

	}

	protected void setEditControl(boolean flag) {
		KDLabelContainer[] kdlc = getEditControl();
		if (kdlc != null)
			STBaseDataClientUtils.setInputCanEdit(kdlc, flag);

	}

	// ���ò���
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}
		setIsEnable(false);
		// ���绥�����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
		// ���ð�ť
		setAferActionBtn(STBaseDataClientUtils.ENABLE, false);

		ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
		iSTDataBase.cancel(null, null);
	}

	// ���ò���
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}
		setIsEnable(true);
		// ���绥�����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
		setAferActionBtn(STBaseDataClientUtils.ENABLE, true);

		ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
		iSTDataBase.cancelCancel(null, null);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}
		setIsAduit(true);
		// ���绥�����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
		setAferActionBtn(STBaseDataClientUtils.AUDIT, true);

		lockUIForViewStatus();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		StringBuffer err = new StringBuffer();

		if (!baseDataInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			err.append(baseDataInfo.getNumber()).append("��");

		if (err.length() > 0) {
			err.deleteCharAt(err.length() - 1);
			throw new STTreeException(STTreeException.CU_CANNOT_UNAUDIT,
					new String[] { err.toString() });
		}

		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}
		setIsAduit(false);
		// ���绥�����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
		setAferActionBtn(STBaseDataClientUtils.AUDIT, false);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		setAferActionBtn(STATUS_ADDNEW, false);
		this.getNumberCtrl().requestFocus();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (isSystemDefaultData()) {
			MsgBox.showError(EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}

		if (getEditData().isIsAudited()) {
			throw new STBasedataException(STBasedataException.AUDIT_CANNOT_EDIT);
		}
		if (getEditData().isIsEnabled()) {
			throw new STBasedataException(
					STBasedataException.ENABLE_CANNOT_EDIT);
		}
		super.actionEdit_actionPerformed(e);
		if (getNumberCtrl() != null)
			this.getNumberCtrl().requestFocus();
		setAferActionBtn(STATUS_EDIT, true);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (isSystemDefaultData()) {
			MsgBox.showError(EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}
		// �Ѿ���׼�Ĳ���ɾ��
		if (baseDataInfo.isIsAudited()) {
			throw new STBasedataException(
					STBasedataException.AUDIT_CANNOT_DELETE);
		}
		// �Ѿ����õĲ���ɾ��
		if (baseDataInfo.isIsEnabled()) {
			throw new STBasedataException(
					STBasedataException.ENABLE_CANNOT_DELETE);
		}

		super.actionRemove_actionPerformed(e);
		// ���绥�����
	}

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		setAferActionBtn(STATUS_ADDNEW, false);
		this.getNumberCtrl().requestFocus();
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (getNumberCtrl() != null)
			this.getNumberCtrl().requestFocus();
		super.actionSubmit_actionPerformed(e);
		setAferActionBtn(STATUS_ADDNEW, true);
		if (getOprtState().equals(OprtState.ADDNEW)
				|| getOprtState().equals(OprtState.EDIT))
			((ListUI) getUIContext().get(UIContext.OWNER)).getMainTable()
					.removeRows();
	}

	protected void setIsEnable(boolean flag) throws Exception {
		baseDataInfo = getEditData();
		// getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag && !canCancel)
			if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		baseDataInfo.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();

		setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
		loadFields();
		setSave(true);
		setSaved(true);
		// this.chkIsEnabled.setSelected(flag);
	}

	// ��׼����
	protected void setIsAduit(boolean flag) throws Exception {
		baseDataInfo = getEditData();

		StringBuffer err = new StringBuffer();

		if (!baseDataInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			err.append(baseDataInfo.getNumber()).append("��");

		if (err.length() > 0) {
			err.deleteCharAt(err.length() - 1);
			throw new STTreeException(STTreeException.CU_CANNOT_UNAUDIT,
					new String[] { err.toString() });
		}

		// getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag && !canCancel)
			if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		baseDataInfo.setIsAudited(flag);
		if (flag) {
			baseDataInfo
					.setAuditUser((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentUser()));
			baseDataInfo
					.setAuditTime(new Timestamp(System.currentTimeMillis()));
		} else {
			baseDataInfo.setAuditUser(null);
			baseDataInfo.setAuditTime(null);
		}
		// SelectorItemCollection sic = new SelectorItemCollection();
		// sic.add(new SelectorItemInfo("isAudited"));
		// sic.add(new SelectorItemInfo("auditUser.id"));
		// sic.add(new SelectorItemInfo("auditTime"));
		String message = null;
		if (flag) {
			ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
			iSTDataBase.audit(
					new ObjectUuidPK(baseDataInfo.getId().toString()),
					baseDataInfo);
			message = EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE, "Audit_OK");
		} else {
			ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
			iSTDataBase.disAudit(new ObjectUuidPK(baseDataInfo.getId()
					.toString()), baseDataInfo);
			message = EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE, "UnAudit_OK");
		}
		setMessageText(message);
		showMessage();
		setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
		loadFields();
		setSave(true);
		setSaved(true);
	}

	/**
	 * �ж��Ƿ�ϵͳԤ������
	 * 
	 * @return
	 */
	protected boolean isSystemDefaultData() {
		baseDataInfo = getEditData();
		if (baseDataInfo == null || baseDataInfo.getCU() == null
				|| baseDataInfo.getId() == null) {
			return false;
		}
		if (OrgConstants.SYS_CU_ID.equals(this.baseDataInfo.getCU().getId()
				.toString())) {
			return true;
		}
		return false;
	}

	protected void setFieldsNull(AbstractObjectValue newData) {
		STBaseDataClientUtils.setFieldsNull(newData);
	}

	protected abstract STDataBaseDInfo getEditData();

	protected abstract KDTextField getNumberCtrl();

	protected abstract KDBizMultiLangBox getNameCtrl();

	protected abstract String getTableName();

	private STBaseDataClientCtrler ctrler = null;

	protected STBaseDataClientCtrler getCtrler() {

		if (ctrler == null) {
			try {
				ctrler = new STBaseDataClientCtrler(this, getBizInterface());
				ctrler.setControlType(getControlType());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}

	protected String getControlType() {
		return getCtrler().getControlType();
		// return this.getControlType();
	}

	// У����������
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		// KDTextField txtNumber = this.getNumberCtrl();
		// if (txtNumber.getText() == null ||
		// txtNumber.getText().trim().length() < 1) {
		// txtNumber.requestFocus(true);
		// throw new STBasedataException(STBasedataException.NUMBER_IS_EMPTY);
		// }
		// �����Ƿ�Ϊ��
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = false;
		if (flag && txtName != null)
			flag = STBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName,
					getEditData(), "name");
		if (flag && txtName != null) {
			txtName.requestFocus(true);
			throw new STBasedataException(STBasedataException.NAME_IS_EMPTY);
		}
		// if (getOprtState().equals(OprtState.ADDNEW) ||
		// getOprtState().equals(OprtState.EDIT))
		// STBaseTypeValidator.validate(((ListUI)
		// getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber,
		// txtName, getSelectBOID());
	}

	// ��������У��,���������
	protected void checkBeforeStoreFields(KDLabelContainer[] container)
			throws Exception {
		STException exc = STBaseDataClientUtils.checkRequiredItem(container);
		if (STUtils.isNotNull(exc)) {
			super.handUIExceptionAndAbort(exc);
		}
	}

	// ��������У��
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		// //��������
		// STException exc = null;
		//		
		// //��������
		// KDLabelContainer[] container = new KDLabelContainer[] {
		// kDLabelContainer2
		// };
		//
		// exc = STRequiredUtils.checkRequiredItem(container);
		// if (STUtils.isNotNull(exc)) {
		// super.handUIExceptionAndAbort(exc);
		// }
		//		
		// ���Ʊ��롢�����Ƿ��Ѿ�����
		ClientUIException uiexc = null;
		String billID = null;
		if (!STATUS_ADDNEW.equals(getOprtState())) {
			billID = this.editData.getId().toString();
		}

		if (getNumberCtrl() != null) {
			uiexc = ClientUIUtils.checkBaseDataDup(getTableName(), billID,
					getNumberCtrl(), getNameCtrl());
			if (STUtils.isNotNull(uiexc)) {
				super.handUIExceptionAndAbort(uiexc);
			}
		}
	}

	public String getUITitle() {
		String strTitle = null;

		// STBaseDataClientUtils.setupUITitle(this, "");
		if (!this.getOprtState().equals(OprtState.EDIT))
			strTitle = super.getUITitle() + "-"
					+ STBaseDataClientUtils.setupUITitle(this.getOprtState());
		else
			strTitle = super.getUITitle() + "-"
					+ STBaseDataClientUtils.setupUITitle("UPDATE");

		return strTitle;
	}

	public void setPrmtOrgUnit(KDBizPromptBox bizOrgUnitBox, OrgType orgType,
			String[] permissionItemKeyString) {

	}

	protected OrgType getMainBizOrgType() {
		// ��ȡ�������ڵ�ҵ����֯
		return OrgType.Storage;
	}

	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);

	}

	/**
	 * ǰһ����ɾ��
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
		setAuditButton();
	}

	/**
	 * ��һ����ɾ��
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionNext_actionPerformed(e);
			setAuditButton();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �����ɾ��
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
		setAuditButton();
	}
}