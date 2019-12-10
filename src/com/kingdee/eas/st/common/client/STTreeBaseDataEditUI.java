/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.mm.qm.STBillBaseCodingRuleVo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.st.common.ISTTreeBaseData;
import com.kingdee.eas.st.common.STTreeBaseDataInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STTreeBaseDataEditUI extends AbstractSTTreeBaseDataEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STTreeBaseDataEditUI.class);

	/** ������򻺴� */
	protected STBillBaseCodingRuleVo codingRuleVo = new STBillBaseCodingRuleVo();

	/**
	 * output class constructor
	 */
	public STTreeBaseDataEditUI() throws Exception {
		super();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataFactory
				.getRemoteInstance();
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
		if (STATUS_ADDNEW.equals(getOprtState())) {
			editData
					.put(
							"treeid",
							(com.kingdee.eas.st.common.STTreeBaseDataTreeInfo) getUIContext()
									.get(UIContext.PARENTNODE));
		}

		refreshUIState();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.st.common.STTreeBaseDataInfo objectValue = new com.kingdee.eas.st.common.STTreeBaseDataInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUser()));

		return objectValue;
	}

	/**
	 * �Ƿ��ȱ�������롣
	 * 
	 */
	protected boolean isNeedCheckNumberFirst() {
		if (this.codingRuleVo.isExist() && !this.codingRuleVo.isAddView()) {
			return false;
		} else {
			return true;
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		if (STATUS_ADDNEW.equals(super.getOprtState())) {
			refreshUIState();
		}

		btnFirst.setVisible(true);
		btnPre.setVisible(true);
		btnNext.setVisible(true);
		btnLast.setVisible(true);

		btnAudit.setVisible(isAuditSupported());
		btnUnAudit.setVisible(isAuditSupported());
		menuItemAudit.setVisible(isAuditSupported());
		menuItemUnAudit.setVisible(isAuditSupported());
	}

	// ��ո���ʱ��Ҫ��յ��ֶ�
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		STTreeBaseDataInfo info = (STTreeBaseDataInfo) newData;
		info.setName(null);
		info.setIsAudited(false);
	}

	/**
	 * �������ˢ�£�����ʱ���ô˷��� �ɽ��а�ť״̬������
	 */
	public void refreshUIState() {
		if (STATUS_VIEW.equals(super.getOprtState())
				|| STATUS_EDIT.equals(super.getOprtState())) {
			actionAudit.setEnabled(!editData.isIsAudited());
			btnUnAudit.setEnabled(editData.isIsAudited());
		} else {
			actionAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
		}

		if (editData.getId() != null) {
			dataBinder.getComponetByField("number").setEnabled(false);
		}

		String[] fields = getCannotModifiedFields();
		for (int i = 0; i < fields.length; i++) {
			dataBinder.getComponetByField(fields[i]).setEnabled(
					editData.getId() == null);
		}

		if (STATUS_VIEW.equals(super.getOprtState()) && editData.isIsAudited()) {
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// �������
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}

		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = new IObjectPK[] { new ObjectStringPK(editData.getId()
				.toString()) };
		try {
			remote.audit(pks);
		} catch (EASBizException ex) {
			super.handUIExceptionAndAbort(ex);
		}
		String message = EASResource.getString(
				STBaseDataClientUtils.STBASEDATA_RESOURCE, "Audit_OK");
		MsgBox.showInfo(message);

		setOprtState(STATUS_VIEW);
		refreshCurPage();
		setSaved(true);

		// ����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// �������
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}

		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = new IObjectPK[] { new ObjectStringPK(editData.getId()
				.toString()) };
		try {
			remote.unaudit(pks);
		} catch (EASBizException ex) {
			super.handUIExceptionAndAbort(ex);
		}
		String message = EASResource.getString(
				STBaseDataClientUtils.STBASEDATA_RESOURCE, "UnAudit_OK");
		MsgBox.showInfo(message);

		setOprtState(STATUS_VIEW);
		refreshCurPage();
		setSaved(true);

		// ����
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionSubmit_actionPerformed(e);
		} catch (EASBizException ex) {
			ExceptionHandler.handle(this, ex);

			// ����Ϊ�յ��쳣����ʾ��λ����
			if (ex.getMainCode().equals("10") && ex.getSubCode().equals("23")) {
				Component txtNumber = dataBinder.getComponetByField("number");
				txtNumber.requestFocus();
			}
			// �����ظ����쳣����ʾ��λ����
			if (ex.getMainCode().equals("86") && ex.getSubCode().equals("007")) {
				Component txtName = dataBinder.getComponetByField("name");
				txtName.requestFocus();
			}
		}
	}

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		String treeID = null;
		if (editData.getId() != null)
			treeID = remote.getTreeId(editData.getId().toString());
		super.actionCopy_actionPerformed(e);
		editData.put("treeid", treeID);
	}

	/**
	 * ���»�ȡ���󣬲�ˢ�½���
	 */
	protected void refreshCurPage() throws EASBizException, BOSException,
			Exception {
		if (this.editData.getId() != null) {
			IObjectPK iObjectPk = new ObjectUuidPK(this.editData.getId());
			IObjectValue iObjectValue = getValue(iObjectPk);
			setDataObject(iObjectValue);
			loadFields();
			setSave(true);
		}
	}

	/**
	 * ����У���߼�
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ҫ�Ա����ֶν��бȽϣ����ȱȽ�
		if (isNeedCheckNumberFirst()) {
			Component txtNumber = dataBinder.getComponetByField("number");
			if (txtNumber instanceof KDTextField) {
				String str = ((KDTextField) txtNumber).getText();
				if (str == null || str.length() == 0) {
					txtNumber.requestFocus();
					MsgBox
							.showInfo(EASResource
									.getString("com.kingdee.eas.st.common.STResource.NULL_Number"));
					txtNumber.setEnabled(true);
					SysUtil.abort(); // �˳�
				}
			}
		}

		// ���Ʊ���
		Component txtName = dataBinder.getComponetByField("name");
		if (txtName instanceof KDBizMultiLangBox) {
			boolean isNull = STBaseDataClientUtils
					.isMultiLangBoxInputNameEmpty((KDBizMultiLangBox) txtName,
							editData, "name");
			if (isNull) {
				txtName.requestFocus();
				MsgBox
						.showInfo(EASResource
								.getString("com.kingdee.eas.st.common.STResource.NULL_NAME"));
				txtName.setEnabled(true);
				SysUtil.abort(); // �˳�
			}
		}
	}

	protected OrgType getMainBizOrgType() {
		return OrgType.NONE;
	}

	/**
	 * ���������Ƿ���Ҫ��׼����
	 * 
	 * @return
	 */
	protected boolean isAuditSupported() {
		return true;
	}

	protected String[] getCannotModifiedFields() {
		return new String[0];
	}
}