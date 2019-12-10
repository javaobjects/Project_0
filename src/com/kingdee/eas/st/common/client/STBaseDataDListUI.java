/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectPK;

import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.util.UIConfigUtility;

import com.kingdee.eas.st.common.ISTDataBaseD;
import com.kingdee.eas.st.common.STBasedataException;
import com.kingdee.eas.st.common.STDataBaseDInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.util.KDTableUtils;

import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
abstract public class STBaseDataDListUI extends AbstractSTBaseDataDListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STBaseDataDListUI.class);

	/**
	 * output class constructor
	 */
	public STBaseDataDListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// 去除license检验，须删除
		setMergeColumn();
		super.onLoad();

		this.btnAssignDataBaseD.setVisible(true);
		this.btnReferDataBaseD.setVisible(true);

		this.tblMain
				.addKDTSelectListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener() {
					public void tableSelectChanged(
							com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
						try {
							_tblMain_tableSelectChanged(e);
						} catch (Exception exc) {
							handUIException(exc);
						}
					}
				});
	}

	final protected void _tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex != -1) {
			if (this.tblMain.getRow(activeRowIndex).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getCell(
						activeRowIndex, "isEnabled").getValue()).booleanValue();
				actionCancelCancel.setEnabled(!status);
				actionCancel.setEnabled(status);
				this.btnCancelCancel.setEnabled(!status);
				this.btnCancel.setEnabled(status);
			}
			if (this.tblMain.getRow(activeRowIndex).getCell("isAudited") != null) {
				boolean status = ((Boolean) this.tblMain.getCell(
						activeRowIndex, "isAudited").getValue()).booleanValue();
				actionAudit.setEnabled(!status);
				actionUnAudit.setEnabled(status);
				this.btnAudit.setEnabled(!status);
				this.btnUnAudit.setEnabled(status);
			}

		} else {
			actionCancelCancel.setEnabled(false);
			actionCancel.setEnabled(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
			this.btnCancel.setEnabled(false);

		}
	}

	/**
	 * 删除操作 它已添加了 对S类基础资料的控制
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (isSystemDefaultData(activeRowIndex)) {
			MsgBox.showError(EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}
		// 处理网络互斥的信息
		ICoreBase ie = getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		STDataBaseDInfo stDataBaseInfo = null;

		for (int i = 0; i < pks.length; i++) {
			stDataBaseInfo = (STDataBaseDInfo) ie.getValue(pks[i]);
			// 已经核准的不能删除
			if (isAudited(stDataBaseInfo)) {
				throw new STBasedataException(
						STBasedataException.AUDIT_CANNOT_DELETE);
			}
			// 已经启用的不能删除
			if (isEnable(stDataBaseInfo)) {
				throw new STBasedataException(
						STBasedataException.ENABLE_CANNOT_DELETE);
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 修改操作 它已添加了 对S类基础资料的控制
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (isSystemDefaultData(activeRowIndex)) {
			MsgBox.showError(EASResource.getString(
					STBaseDataClientUtils.STBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}

		// 处理网络互斥的信息
		ICoreBase ie = getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		STDataBaseDInfo stDataBaseInfo = null;

		if (pks.length > 0) {
			stDataBaseInfo = (STDataBaseDInfo) ie.getValue(pks[0]);

			// 已经核准的不能修改
			if (isAudited(stDataBaseInfo)) {
				throw new STBasedataException(
						STBasedataException.AUDIT_CANNOT_EDIT);
			}
			// 已经启用的不能修改
			if (isEnable(stDataBaseInfo)) {
				throw new STBasedataException(
						STBasedataException.ENABLE_CANNOT_EDIT);
			}
		}

		super.actionEdit_actionPerformed(e);

		actionRefresh_actionPerformed(e);
	}

	// 判断是否核准
	private boolean isAudited(STDataBaseDInfo stDataBaseInfo) {
		boolean isUsed = false;
		if (stDataBaseInfo != null) {
			isUsed = stDataBaseInfo.isIsAudited();
		}

		return isUsed;
	}

	// 判断是否 启用
	private boolean isEnable(STDataBaseDInfo stDataBaseInfo) {
		boolean isUsed = false;
		if (stDataBaseInfo != null) {
			isUsed = stDataBaseInfo.isIsEnabled();
		}

		return isUsed;
	}

	/**
	 * 
	 * 描述：得到当前选中行的id
	 * 
	 * @return listPK[]
	 */
	private IObjectPK[] getSelectedListPK() {
		ArrayList listId = getSelectedIdValues();

		ObjectStringPK[] ids = new ObjectStringPK[listId.size()];

		if (listId != null && listId.size() > 0) {
			for (int i = 0, num = listId.size(); i < num; i++) {
				ids[i] = new ObjectStringPK(listId.get(i).toString());
			}
		}
		return ids;
	}

	protected void setIsEnabled(boolean flag) throws Exception {
		// getCtrler().checkPermission(STBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (!flag)// && !STBaseDataEditUI.canCancel
			if (isSystemDefaultData(activeRowIndex)) {
				MsgBox.showError(EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		/*
		 * String id =
		 * tblMain.getRow(activeRowIndex).getCell("id").getValue().toString
		 * ().trim(); STDataBaseInfo info = getBaseDataInfo();
		 * info.setId(BOSUuid.read(id)); info.setIsEnabled(flag);
		 * //便于启用、禁用时得到编码及名称 String number =
		 * tblMain.getRow(activeRowIndex).getCell
		 * ("number").getValue().toString().trim(); String name =
		 * tblMain.getRow(
		 * activeRowIndex).getCell("name").getValue().toString().trim();
		 * info.setNumber(number); info.setName(name); SelectorItemCollection
		 * sic = new SelectorItemCollection();
		 * 
		 * sic.add(new SelectorItemInfo("isEnabled")); String message = null; if
		 * (flag) { getBizInterface().updatePartial(info, sic); message =
		 * EASResource.getString(STBaseDataClientUtils.STBASEDATA_RESOURCE,
		 * "Enabled_OK"); } //
		 * ContractCostPropertyFactory.getRemoteInstance().enabled(new //
		 * ObjectUuidPK(id), info); else { getBizInterface().updatePartial(info,
		 * sic); message =
		 * EASResource.getString(STBaseDataClientUtils.STBASEDATA_RESOURCE,
		 * "DisEnabled_OK"); } //
		 * ContractCostPropertyFactory.getRemoteInstance().disEnabled(new //
		 * ObjectUuidPK(id), info);
		 */

		String message = null;
		String detailmessage = null;
		ICoreBase ie = getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		STDataBaseDInfo stDataBaseInfo = null;
		StringBuffer enableString = new StringBuffer();
		StringBuffer auditString = new StringBuffer();

		for (int i = 0; i < pks.length; i++) {
			stDataBaseInfo = (STDataBaseDInfo) ie.getValue(pks[i]);

			if (!STBaseDataClientUtils.isMutexControlOK(stDataBaseInfo.getId()
					.toString(), this)) {
				continue;
			}
			if (flag) {
				detailmessage = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"Enabled_Failure");
				if (isEnable(stDataBaseInfo)) {
					enableString.append(stDataBaseInfo.getNumber() + ",");
					continue;
				}
				message = EASResource
						.getString(STBaseDataClientUtils.STBASEDATA_RESOURCE,
								"Enabled_OK");

			} else {
				detailmessage = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"DisEnabled_Failure");
				if (!isEnable(stDataBaseInfo)) {
					enableString.append(stDataBaseInfo.getNumber() + ",");
					continue;
				}

				message = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"DisEnabled_OK");

			}

			stDataBaseInfo.setIsEnabled(flag);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("isEnabled"));
			getBizInterface().updatePartial(stDataBaseInfo, sic);
			// 网络互斥解锁
			STBaseDataClientUtils.releaseMutexControl(stDataBaseInfo.getId()
					.toString(), this);
		}

		if (auditString.length() > 0) {
			enableString.setLength(enableString.length() - 1);

			MsgBox.showError(enableString.toString() + detailmessage);
		}
		setMessageText(message);
		showMessage();

	}

	protected void setIsAduit(boolean flag) throws Exception {
		// getCtrler().checkPermission(STBaseDataClientCtrler.ACTION_MODIFY);
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		// 不允许操作系统预设数据
		if (!flag)// && !STBaseDataEditUI.canCancel
			if (isSystemDefaultData(activeRowIndex)) {
				MsgBox.showError(EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		String message = null;
		String detailmessage = null;
		ICoreBase ie = getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		STDataBaseDInfo stDataBaseInfo = null;
		StringBuffer enableString = new StringBuffer();
		StringBuffer auditString = new StringBuffer();

		for (int i = 0; i < pks.length; i++) {
			stDataBaseInfo = (STDataBaseDInfo) ie.getValue(pks[i]);

			if (!STBaseDataClientUtils.isMutexControlOK(stDataBaseInfo.getId()
					.toString(), this)) {
				continue;
			}

			if (flag) {
				detailmessage = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"Audit_Failure");
				if (isAudited(stDataBaseInfo)) {
					auditString.append(stDataBaseInfo.getNumber() + ",");
					continue;
				}
				stDataBaseInfo
						.setAuditUser((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
								.getSysContext().getCurrentUser()));
				stDataBaseInfo.setAuditTime(new Timestamp(System
						.currentTimeMillis()));
				message = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE, "Audit_OK");

				ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
				iSTDataBase.audit(new ObjectUuidPK(stDataBaseInfo.getId()
						.toString()), stDataBaseInfo);

			} else {
				detailmessage = EASResource.getString(
						STBaseDataClientUtils.STBASEDATA_RESOURCE,
						"UnAudit_Failure");
				if (!isAudited(stDataBaseInfo)) {
					auditString.append(stDataBaseInfo.getNumber() + ",");
					continue;
				}
				stDataBaseInfo.setAuditUser(null);
				stDataBaseInfo.setAuditTime(null);
				message = EASResource
						.getString(STBaseDataClientUtils.STBASEDATA_RESOURCE,
								"UnAudit_OK");

				ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
				iSTDataBase.disAudit(new ObjectUuidPK(stDataBaseInfo.getId()
						.toString()), stDataBaseInfo);
			}

			stDataBaseInfo.setIsAudited(flag);
			// SelectorItemCollection sic = new SelectorItemCollection();
			// sic.add(new SelectorItemInfo("isAudited"));
			// sic.add(new SelectorItemInfo("auditUser.id"));
			// sic.add(new SelectorItemInfo("auditTime"));

			// 网络互斥解锁
			STBaseDataClientUtils.releaseMutexControl(stDataBaseInfo.getId()
					.toString(), this);

		}

		if (auditString.length() > 0) {
			auditString.setLength(auditString.length() - 1);

			MsgBox.showError(auditString.toString() + detailmessage);
		}

		setMessageText(message);
		showMessage();
	}

	/**
	 * 禁用操作
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {

		setIsEnabled(false);

		// 设置按钮
		tblMain.refresh();
		loadFields();
		setAferActionBtn(STBaseDataClientUtils.ENABLE, false);
		ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
		iSTDataBase.cancel(null, null);
	}

	/**
	 * 启用操作
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {

		setIsEnabled(true);

		tblMain.refresh();
		loadFields();
		// 设置按钮
		setAferActionBtn(STBaseDataClientUtils.ENABLE, true);
		ISTDataBaseD iSTDataBase = (ISTDataBaseD) getBizInterface();
		iSTDataBase.cancelCancel(null, null);
	}

	/**
	 * 核准操作
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		IObjectPK[] pks = getSelectedListPK();
		// 只有本组织的才能反核准
		StringBuffer err = new StringBuffer();
		for (int i = 0; i < pks.length; i++) {
			DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
					.getValue(pks[i]);
			if (!dataBaseDInfo.getCU().getId().toString().equals(
					getBizCUPK().toString()))
				err.append(dataBaseDInfo.getNumber()).append("、");
		}
		if (err.length() > 0) {
			err.deleteCharAt(err.length() - 1);
			throw new STTreeException(STTreeException.CU_CANNOT_AUDIT,
					new String[] { err.toString() });
		}

		int[] selectRows = KDTableUtils.getSelectedRows(tblMain);

		setIsAduit(true);
		tblMain.refresh();
		loadFields();

		// 防止丢失用户光标
		for (int i = 0, size = selectRows.length; i < size; i++) {
			tblMain.getSelectManager().add(selectRows[i], 0);
		}

		setAferActionBtn(STBaseDataClientUtils.AUDIT, true);

	}

	/**
	 * 反核准操作
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		IObjectPK[] pks = getSelectedListPK();
		// 只有本组织的才能反核准
		StringBuffer err = new StringBuffer();
		for (int i = 0; i < pks.length; i++) {
			DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
					.getValue(pks[i]);
			if (!dataBaseDInfo.getCU().getId().toString().equals(
					getBizCUPK().toString()))
				err.append(dataBaseDInfo.getNumber()).append("、");
		}
		if (err.length() > 0) {
			err.deleteCharAt(err.length() - 1);
			throw new STTreeException(STTreeException.CU_CANNOT_UNAUDIT,
					new String[] { err.toString() });
		}

		int[] selectRows = KDTableUtils.getSelectedRows(tblMain);

		setIsAduit(false);
		tblMain.refresh();
		loadFields();
		// 防止丢失用户光标
		for (int i = 0, size = selectRows.length; i < size; i++) {
			tblMain.getSelectManager().add(selectRows[i], 0);
		}

		setAferActionBtn(STBaseDataClientUtils.AUDIT, false);

	}

	// 动作做完后的按钮状态
	protected void setAferActionBtn(String actionType, boolean flag) {
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
		}

		if (STBaseDataClientUtils.ADDNEW.equals(actionType)) {
			// btnCancel.setVisible(false);
			// btnCancelCancel.setVisible(false);
			this.btnUnAudit.setEnabled(false);
			// this.btnAudit.setVisible(false);
			this.btnAudit.setEnabled(false);
		}
	}

	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.MODEL;
		}
	}

	/**
	 * 判断是否系统预设数据
	 * 
	 * @return
	 */
	protected boolean isSystemDefaultData(int activeRowIndex) {
		// ICell cell = tblMain.getCell(activeRowIndex, "CU.id");
		// if (cell == null)
		// cell = tblMain.getCell(activeRowIndex, "CU.ID");
		// String CU = (String) cell.getValue();
		// if (OrgConstants.SYS_CU_ID.equals(CU)) {
		// return true;
		// }
		return false;
	}

	protected String getControlType() {
		return getCtrler().getControlType();
	}

	/**
	 * 获取该基础资料实体对象
	 * 
	 * @return 该基础资料实体对象
	 */
	// abstract protected STDataBaseDInfo getBaseDataInfo();
	private STBaseDataClientCtrler ctrler = null;

	protected STBaseDataClientCtrler getCtrler() {
		if (ctrler == null) {
			try {
				ctrler = new STBaseDataClientCtrler(this, getBizInterface());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}

	private void setMergeColumn() {
		String mergeColumnKeys[] = getMergeColumnKeys();
		if (mergeColumnKeys != null && mergeColumnKeys.length > 0) {
			tblMain.checkParsed();
			tblMain.getGroupManager().setGroup(true);
			for (int i = 0; i < mergeColumnKeys.length; i++) {
				tblMain.getColumn(mergeColumnKeys[i]).setGroup(true);
				tblMain.getColumn(mergeColumnKeys[i]).setMergeable(true);
			}
		}
	}

	public String[] getMergeColumnKeys() {
		return new String[] {};
	}

	protected String[] getCountQueryFields() {
		return new String[] { "id" };
	}
}