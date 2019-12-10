/**
 * output package name
 */
package com.kingdee.eas.custom.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.framework.ITreeBase;

/**
 * Java类描述：
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * @author DZ_yanb
 * @date 2019-12-10    
 * @version 1.0
 */
public class TestListUI extends AbstractTestListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestListUI.class);
    
    /**
     * output class constructor
     */
    public TestListUI() throws Exception
    {
        super();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.basedata.TestFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.custom.basedata.TestTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.custom.basedata.client.TestTreeEditUI.class.getName();
    }

    /**
     * output getQueryFieldName method
     */
    protected String getQueryFieldName()
    {
        return "treeid.id";
    }

    /**
     * output getKeyFieldName method
     */
    protected String getKeyFieldName()
    {
        return "id";
    }

    /**
     * output getRootName method
     */
    protected String getRootName()
    {
        return "多级基础资料模板样例";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.basedata.TestInfo objectValue = new com.kingdee.eas.custom.basedata.TestInfo();
		
        return objectValue;
    }
  //####################################以下代码为修改原有方法###############################################
    private static String TABLE_NAME ="CT_BAS_Test";
	
	//com.kingdee.eas.custom.basedata.Test   用于搜索更新

	/**
	 * 使用f7时，需要隐藏的按钮
	 */
	public void setF7Use(boolean isF7Use, java.util.HashMap ctx) {
		super.setF7Use(isF7Use, ctx);

		btnGroupAddNew.setVisible(false);
		btnGroupEdit.setVisible(false);
		btnGroupMoveTree.setVisible(false);
		btnGroupRemove.setVisible(false);
		btnGroupView.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);

		btnAddNew.setVisible(false);
		btnEdit.setVisible(false);
		btnRemove.setVisible(false);
		btnMoveTree.setVisible(false);
		btnView.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		menuFile.setVisible(false);
		menuEdit.setVisible(false);
		MenuService.setVisible(false);
		menuView.setVisible(false);
		menuBiz.setVisible(false);
		menuTool.setVisible(false);
		menuTools.setVisible(false);
		menuHelp.setVisible(false);

	}

	/**
	 * 启用
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent actionevent)
	throws Exception {

		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;

		com.kingdee.eas.framework.ICoreBase ie = getBizInterface();
		com.kingdee.bos.dao.IObjectPK[] pks = getSelectedListPK();
		com.kingdee.eas.custom.basedata.TestInfo info = null;

		for (int i = 0; i < pks.length; i++) {
			info = (com.kingdee.eas.custom.basedata.TestInfo) ie.getValue(pks[i]);

			// 判断是否启用
			if (isEnable(info)) {
				com.kingdee.eas.util.client.MsgBox.showInfo("已经启用！");

			} else {
				// 调用后台启用方法
				com.kingdee.eas.custom.basedata.TestFactory.getRemoteInstance().cancelCancel(pks[i],info);
				tblMain.refresh();
			}
		}
		super.actionCancelCancel_actionPerformed(actionevent);

	}

	/**
	 * 禁用
	 */
	public void actionCancel_actionPerformed(ActionEvent actionevent)
	throws Exception {

		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;

		com.kingdee.eas.framework.ICoreBase ie = getBizInterface();
		com.kingdee.bos.dao.IObjectPK[] pks = getSelectedListPK();
		com.kingdee.eas.custom.basedata.TestInfo info = null;

		for (int i = 0; i < pks.length; i++) {
			info = (com.kingdee.eas.custom.basedata.TestInfo) ie.getValue(pks[i]);

			// 判断是否启用，来决定能否禁用
			if (isEnable(info)) {
				// 调用后台禁用方法
				com.kingdee.eas.custom.basedata.TestFactory.getRemoteInstance().cancel(pks[i], info);
				tblMain.refresh();
			} else {
				com.kingdee.eas.util.client.MsgBox.showInfo("该基础资料没有启用，不能禁用！");
			}
		}
		super.actionCancelCancel_actionPerformed(actionevent);
	}

	/**
	 * 不允许在根节点上新增
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		com.kingdee.eas.framework.client.tree.KDTreeNode treeNode = getSelectedTreeNode();

		if (!(treeNode.getUserObject() instanceof com.kingdee.eas.custom.basedata.TestTreeInfo)) {
			com.kingdee.eas.util.client.MsgBox.showInfo("不能在根节点下新增");
			return;
		}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 选中行监听事件
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		com.kingdee.eas.framework.ICoreBase ie = getBizInterface();
		com.kingdee.bos.dao.IObjectPK[] pks = getSelectedListPK();
		com.kingdee.eas.custom.basedata.TestInfo info = null;

		if(pks.length>1){
			btnCancelCancel.setEnabled(true);
			btnCancel.setEnabled(true);
		}else{
			for (int i = 0; i < pks.length; i++) {

				info = (com.kingdee.eas.custom.basedata.TestInfo) ie.getValue(pks[i]);

				// 判断是否启用
				if (isEnable(info)) {
					btnCancelCancel.setEnabled(false);
					btnCancel.setEnabled(true);
				} else {
					btnCancelCancel.setEnabled(true);
					btnCancel.setEnabled(false);
				}
			}
		}
		super.tblMain_tableSelectChanged(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;

		// 处理网络互斥的信息
		com.kingdee.eas.framework.ICoreBase ie = getBizInterface();
		com.kingdee.bos.dao.IObjectPK[] pks = getSelectedListPK();
		com.kingdee.eas.custom.basedata.TestInfo info = null;

		for (int i = 0; i < pks.length; i++) {
			info = (com.kingdee.eas.custom.basedata.TestInfo) ie.getValue(pks[i]);

			// 已经启用的不能删除
			if (isEnable(info)) {
				com.kingdee.eas.util.client.MsgBox.showInfo("启用状态，不能删除！");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 修改操作 
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		com.kingdee.eas.framework.ICoreBase ie = getBizInterface();
		com.kingdee.bos.dao.IObjectPK[] pks = getSelectedListPK();
		com.kingdee.eas.custom.basedata.TestInfo info = null;

		if (pks.length > 0) {
			info = (com.kingdee.eas.custom.basedata.TestInfo) ie.getValue(pks[0]);
			// 已经启用的不能修改
			if (isEnable(info)) {
				com.kingdee.eas.util.client.MsgBox.showInfo("启用状态，不能修改！");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
		actionRefresh_actionPerformed(e);
	}

	//组别相关的方法
	/**
	 * 增加s3逻辑，并对删除操作增加逻辑
	 */
	public void actionGroupRemove_actionPerformed(ActionEvent arg0)throws Exception {
		//对删除节点进行判断，如果节点下有数据，则不能删除节点
		com.kingdee.eas.framework.client.tree.KDTreeNode treeNode = (com.kingdee.eas.framework.client.tree.KDTreeNode)treeMain.getLastSelectedPathComponent();
		com.kingdee.eas.custom.basedata.TestTreeInfo treeInfo = (com.kingdee.eas.custom.basedata.TestTreeInfo) treeNode.getUserObject();

		//判断当前选中的节点是否存在
		if(treeNode.getUserObject()!=null){
			try{
				String sql = "select * from "+TABLE_NAME+" where FTreeID='"+treeInfo.getId()+"'";
				com.kingdee.jdbc.rowset.IRowSet rs = com.kingdee.bos.dao.query.SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
				if(rs.next()){
					com.kingdee.eas.util.client.MsgBox.showInfo("该节点下有数据，请先删除数据后再删除节点！");
					return;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		super.actionGroupRemove_actionPerformed(arg0);
	}

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@以下代码为公共方法@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	/**
	 * 使窗口固定
	 */
	protected String getEditUIModal(){
		String openModel = com.kingdee.eas.framework.util.UIConfigUtility.getOpenModel();
		if(openModel != null){
			return openModel;
		}else{
			return com.kingdee.eas.common.client.UIFactoryName.MODEL;
		}
	}

	/**
	 * 
	 * 描述：得到当前选中行的id
	 * 
	 * @return listPK[]
	 */
	private com.kingdee.bos.dao.IObjectPK[] getSelectedListPK() {
		java.util.ArrayList listId = getSelectedIdValues();

		com.kingdee.bos.dao.ormapping.ObjectStringPK[] ids = new com.kingdee.bos.dao.ormapping.ObjectStringPK[listId.size()];

		if (listId != null && listId.size() > 0) {
			for (int i = 0, num = listId.size(); i < num; i++) {
				ids[i] = new com.kingdee.bos.dao.ormapping.ObjectStringPK(listId.get(i).toString());
			}
		}
		return ids;
	}

	/**
	 * 判断是否 启用
	 * @param com.kingdee.eas.custom.basedata.TestInfo
	 * @return boolean
	 */
	private boolean isEnable(com.kingdee.eas.custom.basedata.TestInfo info) {
		boolean isUsed = false;
		if (info != null) {
			isUsed = info.isIsEnable();
		}
		return isUsed;
	}

	/**
	 * 忽略序时簿的 CU过滤
	 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * 忽略Tree的 CU过滤
	 */
	protected boolean isIgnoreTreeCUFilter() {
		return true;
	}

	/**
	 * 在edit界面获取listUI界面中选中的Tree节点值
	 */
	protected void prepareUIContext(com.kingdee.eas.common.client.UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		javax.swing.tree.TreePath path = treeMain.getSelectionPath();
		if (path == null)
			return;
		com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode treeNode = (com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode) path.getLastPathComponent();
		if (treeNode.getUserObject() != null
				&& treeNode.getUserObject() instanceof com.kingdee.eas.custom.basedata.TestTreeInfo) {
			com.kingdee.eas.custom.basedata.TestTreeInfo nodeInfo = (com.kingdee.eas.custom.basedata.TestTreeInfo) treeNode.getUserObject();
			uiContext.put("selectedTreeInfo", nodeInfo);
		}
	}
}