/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.st.common.ISTTreeBaseDataD;
import com.kingdee.eas.st.common.STTreeBaseDataDInfo;
import com.kingdee.eas.st.common.STTreeBaseDataDTreeInfo;
import com.kingdee.eas.st.common.STTreeException;
import com.kingdee.eas.st.common.util.KDTableUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STTreeBaseDataDListUI extends AbstractSTTreeBaseDataDListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STTreeBaseDataDListUI.class);

	/**
	 * output class constructor
	 */
	public STTreeBaseDataDListUI() throws Exception {
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
	 * output getTreeInterface method
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataDTreeFactory
				.getRemoteInstance();
	}

	/**
	 * output getGroupEditUIName method
	 */
	protected String getGroupEditUIName() {
		return com.kingdee.eas.st.common.client.STTreeBaseDataDTreeEditUI.class
				.getName();
	}

	/**
	 * output getQueryFieldName method
	 */
	protected String getQueryFieldName() {
		return "tree.id";
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
		return "左树基础资料D类";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.st.common.STTreeBaseDataDInfo objectValue = new com.kingdee.eas.st.common.STTreeBaseDataDInfo();

		return objectValue;
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// 去除license检验，须删除
		setMergeColumn();
		super.onLoad();

		tblMain.addKDTSelectListener(new KDTSelectListener() {
			public void tableSelectChanged(KDTSelectEvent e) {
				try {
					tblMain_tableSelectChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				}
			}
		});

		menuItemAudit.setVisible(true);
		menuItemUnAudit.setVisible(true);
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
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
	 * 不允许在非叶子节点下新增记录
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();

		if (!(treeNode.getUserObject() instanceof com.kingdee.eas.framework.TreeBaseInfo)) {
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
		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();
		if (pks == null || pks.length == 0)
			return;

		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) remote.getValue(pks[0]);
		if (!dataBaseDInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			throw new STTreeException(STTreeException.CU_CANNOT_EDIT);

		String err = remote.queryState(new IObjectPK[] { pks[0] }, 1);
		if (err.length() > 0)
			throw new STTreeException(STTreeException.CANNOT_EDIT,
					new String[] { err });

		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
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

	public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK[] pks = getSelectedListPK();
		if (pks == null || pks.length == 0)
			return;

		DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
				.getValue(pks[0]);
		if (!dataBaseDInfo.getCU().getId().toString().equals(
				getBizCUPK().toString()))
			throw new STTreeException(STTreeException.CU_CANNOT_MOVE);

		super.actionMoveTree_actionPerformed(e);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
		IObjectPK[] pks = getSelectedListPK();

		// 只有本组织的才能核准
		StringBuffer err = new StringBuffer();
		for (int i = 0; i < pks.length; i++) {
			DataBaseDInfo dataBaseDInfo = (DataBaseDInfo) getBizInterface()
					.getValue(pks[0]);
			if (!dataBaseDInfo.getCU().getId().toString().equals(
					getBizCUPK().toString()))
				err.append(dataBaseDInfo.getNumber()).append("、");
		}
		if (err.length() > 0) {
			err.deleteCharAt(err.length() - 1);
			throw new STTreeException(STTreeException.CU_CANNOT_AUDIT,
					new String[] { err.toString() });
		}

		// 加锁
		if (!requestMutexLock())
			return;

		try {
			remote.audit(pks);
		} catch (EASBizException ex) {
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
		ISTTreeBaseDataD remote = (ISTTreeBaseDataD) getBizInterface();
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
		/*
		 * 父结点也可加数据 KDTreeNode treeNode = getSelectedTreeNode(); if (null !=
		 * (treeNode)) { if (null != treeNode.getParent()) { TreeBaseInfo
		 * groupInfo = (TreeBaseInfo) treeNode.getUserObject(); if (groupInfo !=
		 * null) { ISTTreeBaseDataD remote = (ISTTreeBaseDataD)
		 * getBizInterface();
		 * if(remote.checkHasItems(groupInfo.getId().toString())) throw new
		 * STTreeException(STTreeException.CANNOT_ADDGROUP, getPromptNames()); }
		 * } }
		 */

		super.actionGroupAddNew_actionPerformed(e);
	}

	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		KDTreeNode treeNode = getSelectedTreeNode();
		/*
		 * 编辑不作当前CU校验 if (treeNode != null && treeNode.getUserObject()
		 * instanceof TreeBaseInfo) { TreeBaseInfo groupInfo = (TreeBaseInfo)
		 * treeNode.getUserObject();
		 * if(!groupInfo.getCU().getId().toString().equals
		 * (getBizCUPK().toString())) throw new
		 * STTreeException(STTreeException.CU_CANNOT_EDIT); }
		 */

		super.actionGroupEdit_actionPerformed(e);
	}

	public void actionGroupRemove_actionPerformed(ActionEvent e)
			throws Exception {
		/*
		 * 删除不作当前CU验校 KDTreeNode treeNode = getSelectedTreeNode(); if (treeNode
		 * != null && treeNode.getUserObject() instanceof TreeBaseInfo) {
		 * TreeBaseInfo groupInfo = (TreeBaseInfo) treeNode.getUserObject();
		 * if(!
		 * groupInfo.getCU().getId().toString().equals(getBizCUPK().toString()))
		 * throw new STTreeException(STTreeException.CU_CANNOT_REMOVE); }
		 */

		super.actionGroupRemove_actionPerformed(e);
	}

	public void actionGroupMoveTree_actionPerformed(ActionEvent e)
			throws Exception {
		/*
		 * 移动不作当前CU校验 KDTreeNode treeNode = getSelectedTreeNode(); if (treeNode
		 * != null && treeNode.getUserObject() instanceof
		 * STTreeBaseDataDTreeInfo) { STTreeBaseDataDTreeInfo groupInfo =
		 * (STTreeBaseDataDTreeInfo) treeNode.getUserObject();
		 * if(!groupInfo.getCU
		 * ().getId().toString().equals(getBizCUPK().toString())) throw new
		 * STTreeException(STTreeException.CU_CANNOT_MOVE); }
		 */

		super.actionGroupMoveTree_actionPerformed(e);
	}

	public void actionAssignDataBaseD_actionPerformed(ActionEvent e)
			throws Exception {
		IObjectPK[] pks = getSelectedListPK();
		if (pks == null || pks.length == 0)
			return;

		STTreeBaseDataDInfo dataBaseDInfo = (STTreeBaseDataDInfo) getBizInterface()
				.getValue(pks[0]);
		if (!dataBaseDInfo.isIsAudited())
			throw new STTreeException(STTreeException.CANNOT_ASSIGN);

		super.actionAssignDataBaseD_actionPerformed(e);
	}

	public String[] getPromptNames() {
		return new String[] { "节点", "条目" };
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	// 获取选中行的对象关联的树节点的名称。
	protected String getSelectDetailTreeName() {
		return "tree";
	}

	protected FilterInfo getDefaultFilterForTree() {
		return getDefaultCUFilter(isIgnoreTreeCUFilter());
	}

	protected String getBatchAssignUIName() {
		return DataBaseDAssignmentUI_ST.class.getName();
	}

	protected String[] getCountQueryFields() {
		return new String[] { "id" };
	}

	protected boolean isIgnoreTreeCUFilter() {
		return true;
	}
}