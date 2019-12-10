/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.F7Render;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.st.common.ISTTreeBaseData;
import com.kingdee.eas.st.common.STTreeBaseDataTreeInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.util.KDTableUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STTreeBaseDataListUI extends AbstractSTTreeBaseDataListUI
		implements KDPromptSelector {
	private static final long serialVersionUID = 4766949375403324081L;

	private static final Logger logger = CoreUIObject
			.getLogger(STTreeBaseDataListUI.class);

	protected com.kingdee.eas.framework.client.F7Render f7Delegate;

	protected boolean isF7Use;

	protected boolean isShowOnlyAudit = true; // 是否显示未核准的项目（默认不显示），仅适用于F7

	/**
	 * output class constructor
	 */
	public STTreeBaseDataListUI() throws Exception {
		super();
	}

	public STTreeBaseDataListUI(HashMap ctx) throws Exception {
		super();
		isF7Use = true;
		f7Delegate = new F7Render(this, ctx);
		f7Delegate.init();
	}

	public void show() {
		if (isF7Use) {
			f7Delegate.show();
		} else {
			super.show();
		}
	}

	protected boolean isIgnoreCUFilter() {
		if (isF7Use) {
			return !(f7Delegate.isF7HasCuDefaultFilter());
		} else {
			return super.isIgnoreCUFilter();
		}
	}

	public void onLoad() throws Exception {
		setMergeColumn();
		super.onLoad();

		if (isAuditSupported()) {
			tblMain.addKDTSelectListener(new KDTSelectListener() {
				public void tableSelectChanged(KDTSelectEvent e) {
					try {
						tblMain_tableSelectChanged_forAuditButton(e);
					} catch (Exception exc) {
						handUIException(exc);
					}
				}
			});
			menuItemAudit.setVisible(true);
			menuItemUnAudit.setVisible(true);
		} else {
			btnAudit.setVisible(false);
			btnUnaudit.setVisible(false);
			menuItemAudit.setVisible(false);
			menuItemUnAudit.setVisible(false);
		}
	}

	protected void tblMain_tableSelectChanged_forAuditButton(KDTSelectEvent e)
			throws Exception {
		int[] rows = KDTableUtils.getSelectedRows(tblMain);
		boolean containAudit = false;
		boolean contrainUnaudit = false;
		for (int i = 0; i < rows.length; i++) {
			boolean isAudited = ((Boolean) this.tblMain.getCell(rows[i],
					"isAudited").getValue()).booleanValue();
			if (isAudited)
				containAudit = true;
			else
				contrainUnaudit = true;
			if (containAudit && contrainUnaudit)
				break;
		}

		if (containAudit)
			actionAudit.setEnabled(false);
		else
			actionAudit.setEnabled(true);
		if (contrainUnaudit)
			actionUnAudit.setEnabled(false);
		else
			actionUnAudit.setEnabled(true);
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

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataFactory
				.getRemoteInstance();
	}

	/**
	 * output getTreeInterface method
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataTreeFactory
				.getRemoteInstance();
	}

	/**
	 * output getGroupEditUIName method
	 */
	protected String getGroupEditUIName() {
		return com.kingdee.eas.st.common.client.STTreeBaseDataTreeEditUI.class
				.getName();
	}

	/**
	 * output getQueryFieldName method
	 */
	protected String getQueryFieldName() {
		return "treeid.id";
	}

	/**
	 * output getKeyFieldName method
	 */
	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * output getRootName method
	 */
	protected String getRootName() {
		return "STTreeBaseData";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.st.common.STTreeBaseDataInfo objectValue = new com.kingdee.eas.st.common.STTreeBaseDataInfo();

		return objectValue;
	}

	/**
	 * 不允许在非叶子节点下新增记录
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();

		if (!(treeNode.getUserObject() instanceof STTreeBaseDataTreeInfo)) {
			MsgBox.showInfo("不能在根节点下新增");
			return;
		}

		if (null != (treeNode)) {
			if (treeNode.getChildCount() > 0) {
				super
						.handUIExceptionAndAbort(new STTreeException(
								STTreeException.ONLY_ADD_ITEM_LOWEST,
								getPromptNames()));
			}

			super.actionAddNew_actionPerformed(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		if (pks == null || pks.length == 0)
			return;
		String err = remote.queryState(new IObjectPK[] { pks[0] }, 1);
		if (err.length() > 0)
			throw new STTreeException(STTreeException.CANNOT_EDIT,
					new String[] { err });

		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		if (pks == null || pks.length == 0)
			return;
		String err = remote.queryState(pks, 1);
		if (err.length() > 0)
			throw new STTreeException(STTreeException.CANNOT_REMOVE,
					new String[] { err });

		super.actionRemove_actionPerformed(e);

		tblMain.removeRows();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();

		// 加锁
		if (!requestMutexLock())
			return;

		try {
			remote.audit(pks);
		} catch (Exception ex) {
			super.handUIExceptionAndAbort(ex);
		}
		String message = EASResource.getString(
				STBaseDataClientUtils.STBASEDATA_RESOURCE, "Audit_OK");
		MsgBox.showInfo(message);
		refreshListForOrder();

		// 解锁
		releaseMutexLock();
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();

		// 加锁
		if (!requestMutexLock())
			return;

		try {
			remote.unaudit(pks);
		} catch (EASBizException ex) {
			super.handUIExceptionAndAbort(ex);
		}
		String message = EASResource.getString(
				STBaseDataClientUtils.STBASEDATA_RESOURCE, "UnAudit_OK");
		MsgBox.showInfo(message);
		refreshListForOrder();

		// 解锁
		releaseMutexLock();
	}

	/**
	 * 请求网络互斥锁
	 */
	private boolean requestMutexLock() {
		int[] index = KDTableUtils.getSelectedRows(tblMain);

		StringBuffer errNumbers = new StringBuffer();
		String id, number;
		for (int i = 0; i < index.length; i++) {
			try {
				id = tblMain.getCell(index[i], "id").getValue().toString();
				pubFireVOChangeListener(id);
			} catch (Throwable ex) {
				number = tblMain.getCell(index[i], "number").getValue()
						.toString();
				errNumbers.append(number).append("、");
			}
		}
		if (errNumbers.length() > 0) {
			String msg = "编号"
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Error_ObjectUpdateLock_Request");
			MsgBox.showWarning(msg);
			return false;
		}

		return true;
	}

	/**
	 * 网络互斥解锁
	 */
	private void releaseMutexLock() {
		int[] index = KDTableUtils.getSelectedRows(tblMain);
		String id;
		for (int i = 0; i < index.length; i++) {
			id = tblMain.getCell(index[i], "id").getValue().toString();
			STBaseDataClientUtils.releaseMutexControl(id, this);
		}
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

	public void actionGroupAddNew_actionPerformed(ActionEvent e)
			throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();
		if (null != (treeNode)) {
			if (null != treeNode.getParent()) {
				STTreeBaseDataTreeInfo groupInfo = (STTreeBaseDataTreeInfo) treeNode
						.getUserObject();
				if (groupInfo != null) {
					ISTTreeBaseData remote = (ISTTreeBaseData) getBizInterface();
					if (remote.checkHasItems(groupInfo.getId().toString()))
						throw new STTreeException(
								STTreeException.CANNOT_ADDGROUP,
								getPromptNames());
				}
			}
		}

		super.actionGroupAddNew_actionPerformed(e);
	}

	public String[] getPromptNames() {
		return new String[] { "节点", "条目" };
	}

	public Object getData() {
		return f7Delegate.getF7Data();
	}

	public boolean isCanceled() {
		return f7Delegate.isF7Cancel();
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		return null;
	}

	/**
	 * 基础资料是否需要核准功能
	 * 
	 * @return
	 */
	protected boolean isAuditSupported() {
		return true;
	}

	protected String getSelectDetailTreeName() {
		return "treeid";
	}

	protected String[] getCountQueryFields() {
		return new String[] { "id" };
	}
}