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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.basedata.mm.qm.STBillBaseCodingRuleVo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.st.common.ISTTreeBaseDataD;
import com.kingdee.eas.st.common.STDataBaseDInfo;
import com.kingdee.eas.st.common.STTreeBaseDataDInfo;
import com.kingdee.eas.st.common.STTreeBaseDataDTreeInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STTreeBaseDataDEditUI extends AbstractSTTreeBaseDataDEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STTreeBaseDataDEditUI.class);

	/** 编码规则缓存 */
	protected STBillBaseCodingRuleVo codingRuleVo = new STBillBaseCodingRuleVo();

	/**
	 * output class constructor
	 */
	public STTreeBaseDataDEditUI() throws Exception {
		super();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataDFactory
				.getRemoteInstance();
	}

	/**
	 * output setDataObject method
	 */
	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
		if (STATUS_ADDNEW.equals(getOprtState())) {
			// editData.put("tree",(com.kingdee.eas.st.common.
			// STTreeBaseDataDTreeInfo
			// )getUIContext().get(UIContext.PARENTNODE));
			editData.put("tree",
					(com.kingdee.eas.framework.TreeBaseInfo) getUIContext()
							.get(UIContext.PARENTNODE));
		}

		refreshUIState();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.st.common.STTreeBaseDataDInfo objectValue = new com.kingdee.eas.st.common.STTreeBaseDataDInfo();
		objectValue
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUser()));

		return objectValue;
	}

	/**
	 * 是否先必须检查编码。
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
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// 去除license检验，须删除
		super.onLoad();

		if (STATUS_ADDNEW.equals(super.getOprtState())) {
			refreshUIState();
		}

		btnFirst.setVisible(true);
		btnPre.setVisible(true);
		btnNext.setVisible(true);
		btnLast.setVisible(true);

		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
		menuItemAudit.setVisible(true);
		menuItemUnAudit.setVisible(true);
	}

	// 清空复制时需要清空的字段
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		STTreeBaseDataDInfo info = (STTreeBaseDataDInfo) newData;
		info.setName(null);
		info.setIsAudited(false);

		Object object = getUIContext().get(getMainBizOrgType());
		OrgUnitInfo currentBizOrg = ((OrgUnitInfo) (object != null ? (OrgUnitInfo) object
				: ((OrgUnitInfo) (SysContext.getSysContext()
						.getCurrentCtrlUnit()))));
		info.put("CU", currentBizOrg);
	}

	/**
	 * 界面对象刷新，新增时调用此方法 可进行按钮状态等设置
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

		// [start]编辑界面点新增时恢复编码可编辑 modified by hai_zhong
		/*
		 * if (editData.getId() != null) {
		 * dataBinder.getComponetByField("number").setEnabled(false); }
		 */
		dataBinder.getComponetByField("number").setEnabled(
				editData.getId() == null);
		// [end]

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

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
				.getValue(new ObjectStringPK(editData.getId().toString()));
		if (!dataBaseDInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			throw new STTreeException(STTreeException.CU_CANNOT_EDIT);

		super.actionEdit_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
				.getValue(new ObjectStringPK(editData.getId().toString()));
		if (!dataBaseDInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			throw new STTreeException(STTreeException.CU_CANNOT_AUDIT,
					new String[] { editData.getNumber() });

		// 请求加锁
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}

		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
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

		// 解锁
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
				.getValue(new ObjectStringPK(editData.getId().toString()));
		if (!dataBaseDInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			throw new STTreeException(STTreeException.CU_CANNOT_UNAUDIT,
					new String[] { editData.getNumber() });

		// 请求加锁
		if (!STBaseDataClientUtils.isMutexControlOK(
				editData.getId().toString(), this)) {
			return;
		}

		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
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

		// 解锁
		STBaseDataClientUtils.releaseMutexControl(editData.getId().toString(),
				this);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionSubmit_actionPerformed(e);
		} catch (EASBizException ex) {
			ExceptionHandler.handle(this, ex);

			// 编码为空的异常，提示后定位焦点
			if (ex.getMainCode().equals("10") && ex.getSubCode().equals("23")) {
				Component txtNumber = dataBinder.getComponetByField("number");
				txtNumber.requestFocus();
			}
			// 名称重复的异常，提示后定位焦点
			if (ex.getMainCode().equals("86") && ex.getSubCode().equals("007")) {
				Component txtName = dataBinder.getComponetByField("name");
				txtName.requestFocus();
			}
		}
	}

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
		String tree = null;
		if (editData.getId() != null)
			tree = remote.getTreeId(editData.getId().toString());
		STTreeBaseDataDTreeInfo info = new STTreeBaseDataDTreeInfo();
		info.setId(BOSUuid.read(tree));
		super.actionCopy_actionPerformed(e);
		editData.put("tree", info);
	}

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
	 * 界面校验逻辑
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 如果需要对编码字段进行比较，则先比较
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
					SysUtil.abort(); // 退出
				}
			}
		}

		// 名称必填
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
				SysUtil.abort(); // 退出
			}
		}
	}

	protected OrgType getMainBizOrgType() {
		return OrgType.NONE;
	}

	protected String[] getCannotModifiedFields() {
		return new String[0];
	}

	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * 前一，勿删。
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
		setAuditButton();
	}

	/**
	 * 后一，勿删。
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
		setAuditButton();
	}

	/**
	 * 最后，勿删。
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
		setAuditButton();
	}

	private void setAuditButton() {
		if (editData instanceof STTreeBaseDataDInfo) {
			if (((STTreeBaseDataDInfo) editData).isIsAudited()) {
				actionAudit.setEnabled(false);
				actionUnAudit.setEnabled(true);
			} else {
				actionAudit.setEnabled(true);
				actionUnAudit.setEnabled(false);
			}
		}
	}
}