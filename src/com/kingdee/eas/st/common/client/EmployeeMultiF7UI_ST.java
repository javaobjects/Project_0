package com.kingdee.eas.st.common.client;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDFilterTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.core.hr.util.AdminTreeBuilder;
import com.kingdee.eas.base.core.hr.util.HRParamUtil;
import com.kingdee.eas.basedata.hraux.util.HRTreeUtil;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgF7InnerUtils;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.client.tree.NewOrgTreeHelper;
import com.kingdee.eas.basedata.person.Genders;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.hr.base.EmployeeFenLeiInfo;
import com.kingdee.eas.hr.base.EmployeeTypeInfo;
import com.kingdee.eas.hr.base.client.HRF7SingleChangePromptBoxFactory;
import com.kingdee.eas.hr.emp.client.AbstractEmployeeMutiF7UI;
import com.kingdee.eas.hr.emp.client.EmployeeMutiF7UI;
import com.kingdee.eas.hr.org.client.AdminByOrgRangeF7UI;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class EmployeeMultiF7UI_ST extends AbstractEmployeeMutiF7UI {

	private static final Logger logger = CoreUIObject
			.getLogger(EmployeeMultiF7UI_ST.class);

	public static final String PRE_SELECT = "PRE_SELECT";
	public static final String FILTER_INFO = "FILTER_INFO";
	public FilterInfo additionalFilter = null;

	protected FilterInfo customFilter = null;

	public EmployeeMultiF7UI_ST() throws Exception {
		super();
	}

	private String resClass = "com.kingdee.eas.basedata.org.client.PositionResource.";

	// 是否取消
	public boolean isCanceled = true;

	// 返回值
	protected PersonInfo personInfo = null;

	protected PersonCollection personInfos = new PersonCollection();

	// 已选职员ID集合
	private HashSet selectPersonSet = new HashSet();
	// 已选的职员_职位集合
	private Map selectPersonPositionMap = new HashMap();

	// 已选职员的组织集合
	private HashMap selPersonUnitMap = new HashMap();

	// 已选职员的职位的集合
	private HashMap selPersonPositionMap = new HashMap();

	// 标识员工状态树是否装载
	private boolean loaded = false;

	// 是否单选，默认为单选
	private boolean isSingleSelect = true;

	public static final String IS_SINGLE_SELECT = "IS_SINGLE_SELECT";

	public static final String SHOW_PAYPERSON = "SHOW_PAYPERSON";

	public static final String SHOW_SOCIETY = "SHOW_SOCIETY";

	public static final String SHOW_TRYOUTPERSON = "SHOW_TRYOUTPERSON";

	public static final String SHOW_NO_POSITION_PERSON = "SHOW_NO_POSITION_PERSON";

	public static final String NO_POSOTION_PERSON_FILTER = "NO_POSOTION_PERSON_FILTER";

	public static final String RETURN_POSITION_UNIT = "RETURN_POSITION_UNIT";

	// 只显示干部(add by jie_fan)
	public static final String SHOW_CADRE = "SHOW_CADRE";

	public static final String HIDE_ADMINTREE_TAB = "hideAdminTreeTab";
	// 是否只是HR组织隔离
	public static final String IS_HR_FILTER = "IS_HR_FILTER";

	// 是否显示所有的行政组织
	public static final String IS_SHOW_ALL_ADMIN = "IS_SHOW_ALL_ADMIN";

	// 显示用户行政组织范围内的组织
	public static final String SHOW_USER_ORG_RANGE = "SHOW_USER_ORG_RANGE";

	// 默认职位
	public static final String DEFAULT_POSITION = "DEFAULT_POSITION";

	public static final String HRO_SET = "HRO_SET";

	// add by xianfeng_zhu 2009-10-03 是否受参数'是否兼职人员在兼职单位可以选到参数'控制
	public static final String IS_PLURALITY_IS_CHOOSE_CONTRAL = "IS_PLURALITY_IS_CHOOSE_CONTRAL";
	private boolean isPlurality = true;

	// 参与过滤的HRO组织集合
	private HashSet hroSet = null;

	// 是否只是HR组织隔离
	private boolean isHRFilter = false;

	private boolean isShowUserOrgRange = false;

	private boolean isShowAllAdmin = true;

	private FilterInfo noPositionPersonFilter = null;

	// 隐藏行政组织树开关
	private boolean isHideAdminTreeTab = false;
	private boolean returnPositionUnit = true;

	private boolean isDistabled = true;

	private boolean isMaxSize = false;

	private Rectangle winSize = null;

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected JButton getDefaultButton() {
		return this.btnConfirm;
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		tblMain.removeRows();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
	}

	/**
	 * 初始化工具栏及其他控件
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		txtValue.setFilterType(KDFilterTextField.ANYTHING);
		kdContainer.addButton(this.btnInertRow);
		kdContainer.addButton(this.btnDeleteRow);
		kdContainer.addButton(this.btnInsertBatchRow);
		kdContainer.addButton(this.btnDeleteBatchRow);
		treeMain.setShowsRootHandles(true);
		treeMain2.setShowsRootHandles(true);
		tblMain.getStyleAttributes().setLocked(true);
		tblSelectPerson.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						if (tblMain.getRowCount() != 0) {
							tblMain.getSelectManager().select(0, 0);
						}
					}
				});
		tblSelectPerson.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {

					public void afterDataFill(KDTDataRequestEvent e) {
						if (tblSelectPerson.getRowCount() != 0) {
							tblSelectPerson.getSelectManager().select(0, 0);
						}
					}
				});

		tabTree.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					if ("treeView2".equals(tabTree.getSelectedComponent()
							.getName())
							&& !loaded) {
						// 初始化员工状态树
						// initEmployeeStateTree();
						initEmpFenLeiTree();
						loaded = true;
					}
					if ("treeView1".equals(tabTree.getSelectedComponent()
							.getName())) {
						treeMain_valueChanged(null);
					} else {
						treeMain2_valueChanged(null);
					}
				} catch (Exception e1) {
					ExceptionHandler.handle(treeMain, e1);
				}

			}
		});
	}

	public void onLoad() throws Exception {
		// 从eas参数平台取数
		isDistabled = HRParamUtil.getParamOfHR(null, HRParamUtil.IS_AFFAIR,
				null);

		HashMap map = (HashMap) this.getUIContext();

		// add by xianfeng_zhu 2009-10-03
		if (map.get(IS_PLURALITY_IS_CHOOSE_CONTRAL) != null
				&& map.get(IS_PLURALITY_IS_CHOOSE_CONTRAL) instanceof Boolean) {
			this.isPlurality = ((Boolean) map
					.get(IS_PLURALITY_IS_CHOOSE_CONTRAL)).booleanValue();
		}

		if (map.get(AdminByOrgRangeF7UI.HRO_SET) != null) {
			hroSet = (HashSet) map.get(AdminByOrgRangeF7UI.HRO_SET);
		}

		if (map.get(EmployeeMutiF7UI.IS_SINGLE_SELECT) != null
				&& map.get(EmployeeMutiF7UI.IS_SINGLE_SELECT) instanceof Boolean) {
			isSingleSelect = ((Boolean) map
					.get(EmployeeMutiF7UI.IS_SINGLE_SELECT)).booleanValue();
		}

		if (map.get(EmployeeMutiF7UI.IS_HR_FILTER) != null
				&& map.get(EmployeeMutiF7UI.IS_HR_FILTER) instanceof Boolean) {
			isHRFilter = ((Boolean) map.get(EmployeeMutiF7UI.IS_HR_FILTER))
					.booleanValue();
		}

		if (map.get(EmployeeMutiF7UI.IS_SHOW_ALL_ADMIN) != null
				&& map.get(EmployeeMutiF7UI.IS_SHOW_ALL_ADMIN) instanceof Boolean) {
			isShowAllAdmin = ((Boolean) map
					.get(EmployeeMutiF7UI.IS_SHOW_ALL_ADMIN)).booleanValue();
		}

		if (map.get(EmployeeMutiF7UI.RETURN_POSITION_UNIT) != null
				&& map.get(EmployeeMutiF7UI.RETURN_POSITION_UNIT) instanceof Boolean) {
			returnPositionUnit = ((Boolean) map
					.get(EmployeeMutiF7UI.RETURN_POSITION_UNIT)).booleanValue();
		}

		if (map.get(EmployeeMutiF7UI.SHOW_USER_ORG_RANGE) != null
				&& map.get(EmployeeMutiF7UI.SHOW_USER_ORG_RANGE) instanceof Boolean) {
			isShowUserOrgRange = ((Boolean) map
					.get(EmployeeMutiF7UI.SHOW_USER_ORG_RANGE)).booleanValue();
		}
		if (map.get(EmployeeMutiF7UI.NO_POSOTION_PERSON_FILTER) != null
				&& map.get(EmployeeMutiF7UI.NO_POSOTION_PERSON_FILTER) instanceof FilterInfo) {
			noPositionPersonFilter = (FilterInfo) map
					.get(EmployeeMutiF7UI.NO_POSOTION_PERSON_FILTER);
		}
		if (Boolean.valueOf(true).equals(
				map.get(EmployeeMutiF7UI.HIDE_ADMINTREE_TAB))) {
			isHideAdminTreeTab = true;
		}
		if (map.get(EmployeeMultiF7UI_ST.FILTER_INFO) != null) {
			customFilter = (FilterInfo) map
					.get(EmployeeMultiF7UI_ST.FILTER_INFO);
		}
		super.onLoad();

		tblSelectPerson.checkParsed();

		if (isSingleSelect) {
			kDSplitPane2.remove(kdContainer);
			kDSplitPane2.setDividerSize(0);
			tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.ROW_SELECT);
		} else {
			tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_ROW_SELECT);
			tblSelectPerson.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_ROW_SELECT);
		}

		if ((map.get(EmployeeMutiF7UI.SHOW_NO_POSITION_PERSON) != null
				&& map.get(EmployeeMutiF7UI.SHOW_NO_POSITION_PERSON) instanceof Boolean && !((Boolean) map
				.get(EmployeeMutiF7UI.SHOW_NO_POSITION_PERSON)).booleanValue())
				|| map.get(EmployeeMutiF7UI.SHOW_NO_POSITION_PERSON) == null) {
			tabTree.removeTabAt(1);
		}

		if (Boolean.valueOf(true).equals(
				map.get(EmployeeMutiF7UI.HIDE_ADMINTREE_TAB))) {
			tabTree.setSelectedIndex(1);
			tabTree.removeTabAt(0);
		}

		// 初始化行政组织树
		if (!isHideAdminTreeTab) {
			initAdminTree();
		}

		kdContainer.getContentPane().putClientProperty("OriginalBounds",
				new Rectangle(0, 0, 415, 135));

		txtValue.setText("");
		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(txtValue,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		txtValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnFastQuery_actionPerformed(e);
				} catch (Exception e1) {
					handUIException(e1);
				}
			}
		});

		KDTableHelper.releaseEnterAndTab(this.tblMain);
		KDTableHelper.releaseEnterAndTab(this.tblSelectPerson);
		tblMain.getInputMap().remove(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(
				this.tblMain, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(
				this.tblSelectPerson, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
						0));

		// 如果传入默认职位则默认选中此职位,陈波,2008-1-31
		setDefaultPositionNode();

		if (map.get(EmployeeMultiF7UI_ST.PRE_SELECT) != null) {
			Object[] preSelect = (Object[]) map
					.get(EmployeeMultiF7UI_ST.PRE_SELECT);
			doPreSelect(preSelect);
		}

	}

	private void setDefaultPositionNode() {
		PositionInfo position = (PositionInfo) this.getUIContext().get(
				DEFAULT_POSITION);
		if (position == null) {
			return;
		}

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain
				.getModel().getRoot();
		if (root == null) {
			return;
		}

		DefaultKingdeeTreeNode node = null;
		PositionInfo holdPosition = null;
		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultKingdeeTreeNode) e.nextElement();
			if (!(node.getUserObject() instanceof PositionInfo)) {
				continue;
			}

			holdPosition = (PositionInfo) node.getUserObject();
			if (holdPosition.getId().equals(position.getId())) {
				break;
			}
		}

		if (node != null) {
			this.treeMain.expandAllNodes(true, node);
			this.treeMain.setSelectionNode(node);
		}
	}

	/**
	 * 创建行政组织树
	 * 
	 * @throws Exception
	 */
	private void initAdminTree() throws Exception {
		DefaultKingdeeTreeNode rootNode = null;

		if (isHRFilter)
			rootNode = NewOrgTreeHelper.createAdminTreeNodeByHRO(null, false,
					true);
		else if (isShowAllAdmin)
			rootNode = NewOrgTreeHelper.createAllAdminTreeNode(false, false);

		else if (isShowUserOrgRange) {
			AdminTreeBuilder treeBuilder = null;
			if (hroSet == null || hroSet.size() == 0) {
				treeBuilder = new AdminTreeBuilder();
			} else {
				treeBuilder = new AdminTreeBuilder(true, hroSet);
			}
			treeBuilder.buildTree(treeMain, true);
			if (treeMain.getModel() != null) {
				treeMain.setSelectionNode((DefaultKingdeeTreeNode) treeMain
						.getModel().getRoot());
			}
			return;
		} else
			rootNode = NewOrgTreeHelper.createAdminTreeNodeByHRO(null, true,
					true);

		if (rootNode == null) {
			treeMain.setModel(null);
			return;
		}
		treeMain.setModel(new DefaultTreeModel(rootNode));
		if (!OrgF7InnerUtils.isTreeNodeDisable(rootNode)
				|| rootNode.getChildCount() == 0) {
			TreePath path = new TreePath(rootNode.getPath());
			treeMain.expandPath(path);
			treeMain.setSelectionPath(path);
		} else {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) rootNode
					.getChildAt(0);
			TreePath path = new TreePath(node.getPath());
			treeMain.expandPath(path);
			treeMain.setSelectionPath(path);
		}
	}

	/**
	 * 创建人员分类树
	 * 
	 * @throws Exception
	 */
	private void initEmpFenLeiTree() throws Exception {
		DefaultKingdeeTreeNode rootNode = HRTreeUtil
				.createEmployeeFenLeiTreeNode(true);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		this.treeMain2.setModel(model);
		treeMain2.expandAllNodes(true, rootNode);
		treeMain2.setSelectionNode(rootNode);
		treeMain2.setShowsRootHandles(true);
	}

	/**
	 * 用户选取消
	 */
	protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
		super.btnQuit_actionPerformed(e);

		this.isCanceled = true;
		this.getUIWindow().close();
	}

	private void setReturnPositionAndUnit(String pId, int index)
			throws Exception {
		if (tblMain.getRow(index).getCell("adminUnitId").getValue() != null) {
			AdminOrgUnitInfo adminInfo = new AdminOrgUnitInfo();
			Object v = null;
			IRow row = tblMain.getRow(index);
			adminInfo.setId(BOSUuid.read(row.getCell("adminUnitId").getValue()
					.toString()));
			adminInfo.setName(row.getCell("adminUnit").getValue().toString());
			v = row.getCell("adminDisName").getValue();
			if (v != null) {
				adminInfo.setDisplayName(v.toString());
			}
			selPersonUnitMap.put(pId, adminInfo);
		}

		if (tblMain.getRow(index).getCell("positionId").getValue() != null) {
			PositionInfo posiInfo = new PositionInfo();
			posiInfo.setId(BOSUuid.read(tblMain.getRow(index).getCell(
					"positionId").getValue().toString()));
			posiInfo.setName(tblMain.getRow(index).getCell("position")
					.getValue().toString());
			selPersonPositionMap.put(pId, posiInfo);
		}
	}

	/**
	 * 用户选择确定
	 */
	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {

		if (isSingleSelect) {
			int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
			if (rowID.length > 0) {
				String strID = tblMain.getRow(rowID[0]).getCell("id")
						.getValue().toString();
				selectPersonSet = new HashSet();
				selPersonUnitMap = new HashMap();
				selPersonPositionMap = new HashMap();
				selectPersonSet.add(strID);
				addPersonSelectPositionMap(rowID[0]);
				setReturnPositionAndUnit(strID, rowID[0]);
			}
		}

		if (selectPersonSet == null || selectPersonSet.size() == 0) {
			// MsgBox.showInfo(EASResource.getString(resClass +
			// "NO_SELECT_ROW"));
			// return;
		} else {
			IMetaDataPK queryPK = new MetaDataPK(
					"com.kingdee.eas.hr.emp.app.EmployeeF7SelectQuery");
			IQueryExecutor queryExcu = QueryExecutorFactory
					.getRemoteInstance(queryPK);

			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(
					new FilterItemInfo("id", selectPersonSet,
							CompareType.INCLUDE));
			viewInfo.setFilter(fi);
			queryExcu.setObjectView(viewInfo);
			IRowSet rows = queryExcu.executeQuery();

			while (rows.next()) {
				personInfos.add(getPersonInfo(rows));
			}

			if (isSingleSelect && personInfos.size() > 0) {
				personInfo = personInfos.get(0);
			}

		}
		this.isCanceled = false;
		this.getUIWindow().close();
	}

	/**
	 * 根据RowSet构造职员值对象
	 * 
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	private PersonInfo getPersonInfo(IRowSet rows) throws Exception {
		PersonInfo pInfo = new PersonInfo();
		pInfo.setId(BOSUuid.read(rows.getString("id")));
		pInfo.setNumber(rows.getString("number"));
		pInfo.setName(rows.getString("name"));
		pInfo.setIdCardNO(rows.getString("idCardNO"));
		pInfo.setPassportNO(rows.getString("passportNO"));
		if (Genders.getEnum(rows.getInt("gender")) != null) {
			pInfo.setGender(Genders.getEnum(rows.getInt("gender")));
		}
		pInfo.setEmail(rows.getString("email"));

		if (!returnPositionUnit) {
			if (selPersonUnitMap.get(pInfo.getId().toString()) != null) {
				pInfo.put("primaryAdminOrg",
						(AdminOrgUnitInfo) selPersonUnitMap.get(pInfo.getId()
								.toString()));
			}
			if (selPersonUnitMap.get(pInfo.getId().toString()) != null) {
				pInfo.put("primaryPosition",
						(PositionInfo) selPersonPositionMap.get(pInfo.getId()
								.toString()));
			}
		} else {
			if (rows.getBoolean("PositionMember.isPrimary")) {
				pInfo.put("primaryAdminOrg", getAdminOrgUnitInfo(rows));
				pInfo.put("primaryPosition", getPositionInfo(rows));
			}

			if (pInfo.get("primaryAdminOrg") == null) {
				pInfo.put("primaryAdminOrg", getAdminOrgUnitInfo(rows));
			}
		}
		pInfo.put("personSelectPosition", this.selectPersonPositionMap
				.get(pInfo.getId().toString()));
		if (rows.getDate("SocialInsurance.tranSInsuTime") != null) {
			pInfo.put("STime", rows.getDate("SocialInsurance.tranSInsuTime"));
		}
		pInfo.put("employeeClassify", rows.getString("employeeClassify.name"));

		return pInfo;
	}

	/**
	 * 根据RowSet构造行政组织值对象
	 * 
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	private AdminOrgUnitInfo getAdminOrgUnitInfo(IRowSet rows) throws Exception {
		AdminOrgUnitInfo adminInfo = null;
		if (rows.getString("AdminOrgUnit.id") != null) {
			adminInfo = new AdminOrgUnitInfo();
			adminInfo.setId(BOSUuid.read(rows.getString("AdminOrgUnit.id")));
			adminInfo.setNumber(rows.getString("AdminOrgUnit.number"));
			adminInfo.setName(rows.getString("AdminOrgUnit.name"));
			adminInfo.setLevel(rows.getInt("AdminOrgUnit.level"));
			adminInfo.setLongNumber(rows.getString("AdminOrgUnit.longNumber"));
			adminInfo
					.setDisplayName(rows.getString("AdminOrgUnit.displayName"));
		}
		return adminInfo;
	}

	/**
	 * 根据RowSet构造职位值对象
	 * 
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	private PositionInfo getPositionInfo(IRowSet rows) throws Exception {
		PositionInfo posiInfo = new PositionInfo();
		if (rows.getString("Position.id") != null) {
			posiInfo = new PositionInfo();
			posiInfo.setId(BOSUuid.read(rows.getString("Position.id")));
			posiInfo.setNumber(rows.getString("Position.number"));
			posiInfo.setName(rows.getString("Position.name"));
		}
		return posiInfo;
	}

	/**
	 * 清空已选列表
	 */
	protected void btnDeleteBatchRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDeleteBatchRow_actionPerformed(e);

		tblSelectPerson.removeRows();

		selectPersonSet = new HashSet();
		selPersonUnitMap = new HashMap();
		selPersonPositionMap = new HashMap();
	}

	/**
	 * 删除已选列表中已选的行
	 */
	protected void btnDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		super.btnDeleteRow_actionPerformed(e);

		int[] rowID = KDTableUtil.getSelectedRows(this.tblSelectPerson);

		delDataFromSelectTable(rowID);
	}

	/**
	 * 快速查询
	 */
	protected void btnFastQuery_actionPerformed(ActionEvent e) throws Exception {
		super.btnFastQuery_actionPerformed(e);

		FilterInfo filterInfo = new FilterInfo();

		if (this.cboKey.getSelectedIndex() == 0) {
			if (chkLike.isSelected()) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("name", "%"
								+ txtValue.getText().trim() + "%",
								CompareType.LIKE));
			} else {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("name", txtValue.getText().trim()));
			}
		} else if (this.cboKey.getSelectedIndex() == 1) {
			if (chkLike.isSelected()) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("number", "%"
								+ txtValue.getText().trim() + "%",
								CompareType.LIKE));
			} else {
				filterInfo.getFilterItems()
						.add(
								new FilterItemInfo("number", txtValue.getText()
										.trim()));
			}
		} else if (this.cboKey.getSelectedIndex() == 2) {
			if (chkLike.isSelected()) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("idNum", "%"
								+ txtValue.getText().trim() + "%",
								CompareType.LIKE));
			} else {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("idNum", txtValue.getText().trim()));
			}
		}

		if (!isShowAllAdmin) {
			if (hroSet != null && hroSet.size() > 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("ToUnit.id", hroSet,
								CompareType.INCLUDE));
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			} else if (!isShowUserOrgRange) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("ToUnit.id", SysContext
								.getSysContext().getCurrentHRUnit().getId()
								.toString()));
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			}

			if (!isHRFilter) {
				// Modify by jiwei_xiao in DaMing
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("PMUser.id",
				// SysContext.getSysContext().getCurrentUserInfo
				// ().getId().toString()));
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("OrgRange.type", new Integer(20)));
			}
			addFilterPositionEnable(filterInfo);

			// add by xianfeng_zhu 2009-10-03
			addFilterPositionMemberEnable(filterInfo);
		}
		FilterInfo filterSealUp = new FilterInfo("AdminOrgUnit.isSealUp = 0");
		filterInfo.mergeFilter(filterSealUp, "and");
		this.mainQuery = new EntityViewInfo();
		this.mainQuery.setFilter(filterInfo);

		getCustomFilter();

		this.tblMain.removeRows();
		tblMain.requestFocusInWindow();
	}

	private void addFilterPositionEnable(FilterInfo filter) throws Exception {
		if (!isDistabled) {
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems()
					.add(
							new FilterItemInfo("Position.deletedStatus",
									new Integer(1)));
			fi.getFilterItems().add(
					new FilterItemInfo("Position.deletedStatus", null));
			fi.setMaskString("#0 or #1");
			if (filter != null && filter.getFilterItems().size() > 0)
				filter.mergeFilter(fi, "and");
			else
				filter = fi;
		}
	}

	/**
	 * @description 兼职人员在兼职单位是否可以选到
	 * @author xianfeng_zhu 2009-10-03
	 * @param filter
	 * @throws Exception
	 */
	private void addFilterPositionMemberEnable(FilterInfo filter)
			throws Exception {
		if (!this.isPlurality) {
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(
					new FilterItemInfo("PositionMember.isPrimary", new Integer(
							1)));
			if (filter != null && filter.getFilterItems().size() > 0)
				filter.mergeFilter(fi, "and");
			else
				filter = fi;
		}
	}

	/**
	 * 取自定义的过滤条件，比如只显示计薪人员等等
	 * 
	 * @throws Exception
	 */
	private void getCustomFilter() throws Exception {
		if (mainQuery.getFilter() != null
				&& mainQuery.getFilter().getFilterItems() != null) {
			int flag = 0;
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_PAYPERSON) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_PAYPERSON) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_PAYPERSON)).booleanValue())
					flag = 1;
				else
					flag = 0;
				FilterInfo ffInfo = new FilterInfo();
				ffInfo.getFilterItems().add(
						new FilterItemInfo("PersonCMP.isPayPerson",
								new Integer(flag)));
				mainQuery.getFilter().mergeFilter(ffInfo, "and");
			}
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_TRYOUTPERSON) != null
					&& this.getUIContext().get(
							EmployeeMutiF7UI.SHOW_TRYOUTPERSON) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_TRYOUTPERSON)).booleanValue())
					flag = 1;
				else
					flag = 0;
				if (flag == 1) {
					FilterInfo ffInfo = new FilterInfo();
					ffInfo
							.getFilterItems()
							.add(
									new FilterItemInfo("EmployeeType.id",
											"00000000-0000-0000-0000-000000000002A29E85B3"));
					mainQuery.getFilter().mergeFilter(ffInfo, "and");
				}
			}
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_SOCIETY) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_SOCIETY) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_SOCIETY)).booleanValue())
					flag = 1;
				else
					flag = 0;
				FilterInfo ffInfo = new FilterInfo();
				ffInfo.getFilterItems().add(
						new FilterItemInfo("SocialInsurance.isSocialInsu",
								new Integer(flag)));
				mainQuery.getFilter().mergeFilter(ffInfo, "and");
			}
			// 添加干部过滤条件 add by jie_fan
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_CADRE) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_CADRE) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_CADRE)).booleanValue())
					flag = 1;
				else
					flag = 0;
				if (flag == 1) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("isStandbyCadre", new Integer(
									flag)));
					mainQuery.getFilter().mergeFilter(filter, "and");
				}
			}
			// end
			if (noPositionPersonFilter != null) {
				mainQuery.getFilter()
						.mergeFilter(noPositionPersonFilter, "and");
			}

			// if (customFilter != null) {
			// try {
			// // mainQuery.getFilter().mergeFilter(customFilter, "AND");
			// mainQuery.setFilter(customFilter);
			// } catch (Exception e) {
			// logger.error(e.getStackTrace());
			// }
			// }
			// Modify by jiwei_xiao in DaMing
			// mainQuery.setFilter(customFilter);
		}
	}

	/**
	 * 把选择的职员加入到已选列表中
	 */
	protected void btnInertRow_actionPerformed(ActionEvent e) throws Exception {
		super.btnInertRow_actionPerformed(e);

		int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);

		addDataToSelectTable(rowID);
	}

	/**
	 * 把上面能看见的职员全部加到已选列表中(虚模式列表则只能取看得见的)
	 */
	protected void btnInsertBatchRow_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnInsertBatchRow_actionPerformed(e);

		int[] rowID = null;
		if (tblMain.getRowCount() == -1) {
			rowID = new int[100];
		} else {
			rowID = new int[tblMain.getRowCount()];
		}

		for (int i = 0; i < rowID.length; i++) {
			rowID[i] = i;
		}
		addDataToSelectTable(rowID);
	}

	private void setCommonQueryPK() throws Exception {
		CommonQueryDialog commonDialog = this.getDialog();
		if (commonDialog != null)
			commonDialog.setQueryObjectPK(this.mainQueryPK);
	}

	/**
	 * 行政组织节点变化时触发此事件
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		// 行政组织树隐藏时，该事件不用处理了。
		if (isHideAdminTreeTab) {
			return;
		}
		if (isHRFilter)
			mainQueryPK = new MetaDataPK("com.kingdee.eas.hr.emp.app",
					"EmployeeF7ByHRQuery");
		else if (isShowAllAdmin)
			mainQueryPK = new MetaDataPK("com.kingdee.eas.hr.emp.app",
					"EmployeeF7ByAllAdminQuery");
		else
			mainQueryPK = new MetaDataPK("com.kingdee.eas.hr.emp.app",
					"EmployeeF7SelectQuery");

		TreePath path = treeMain.getSelectionPath();
		if (path == null)
			return;
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();

		FilterInfo filter = new FilterInfo();
		mainQuery = new EntityViewInfo();

		if (OrgF7InnerUtils.isTreeNodeDisable(treenode)
				|| HRTreeUtil.isTreeNodeDisable(treenode)) {
			filter.getFilterItems().add(new FilterItemInfo("id", "nodata"));
			mainQuery.setFilter(filter);
			tblMain.removeRows();
			return;
		}

		if (treenode.getUserObject() != null
				&& treenode.getUserObject() instanceof AdminOrgUnitInfo) {
			chkInclude.setVisible(true);

			AdminOrgUnitInfo nodeInfo = (AdminOrgUnitInfo) treenode
					.getUserObject();

			filter.getFilterItems().add(
					new FilterItemInfo("AdminOrgUnit.id", nodeInfo.getId()
							.toString()));
			if (chkInclude.isSelected()) {
				filter.getFilterItems().add(
						new FilterItemInfo("AdminOrgUnit.longNumber", nodeInfo
								.getLongNumber()
								+ "!%", CompareType.LIKE));
				filter.setMaskString("#0 or #1");
			}
		} else if (treenode.getUserObject() != null
				&& treenode.getUserObject() instanceof PositionInfo) {
			chkInclude.setVisible(false);
			PositionInfo nodeInfo = (PositionInfo) treenode.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("Position.id", nodeInfo.getId()
							.toString()));
		}

		if (!isShowAllAdmin) {
			FilterInfo filterInfo = new FilterInfo();

			if (hroSet != null && hroSet.size() > 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("ToUnit.id", hroSet,
								CompareType.INCLUDE));
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			} else if (!isShowUserOrgRange) {
				if (SysContext.getSysContext().getCurrentHRUnit() != null) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", SysContext
									.getSysContext().getCurrentHRUnit().getId()
									.toString()));
				} else {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", "nodata"));
				}
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			}

			if (!isHRFilter) {
				// Modify by jiwei_xiao in DaMing
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("PMUser.id",
				// SysContext.getSysContext().getCurrentUserInfo
				// ().getId().toString()));
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("OrgRange.type", new Integer(20)));
			}
			if (!StringUtils.isEmpty(filter.toString())) {
				filter.mergeFilter(filterInfo, "AND");
			} else {
				filter = filterInfo;
			}
		}

		addFilterPositionEnable(filter);

		// add by xianfeng_zhu 2009-10-03
		addFilterPositionMemberEnable(filter);
		FilterInfo filterSealUp = new FilterInfo("AdminOrgUnit.isSealUp = 0");
		filter.mergeFilter(filterSealUp, "and");

		if (getAdditionalFilter() != null) {
			filter.mergeFilter(getAdditionalFilter(), "AND");
		}

		mainQuery.setFilter(filter);

		getCustomFilter();

		tblMain.removeRows();

		// 有些测试人员的机器Table没有滚动条，符修湖说调用这个方法让Table重新布局
		tblMain.reLayoutAndPaint();

		setCommonQueryPK();
	}

	public FilterInfo getAdditionalFilter() {
		return additionalFilter;
	}

	public void setAdditionalFilter(FilterInfo filterInfo) {
		additionalFilter = filterInfo;
	}

	protected void treeMain2_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.treeMain2_valueChanged(e);

		chkInclude.setVisible(false);

		TreePath path = treeMain2.getSelectionPath();
		if (path == null) {
			return;
		}

		DefaultKingdeeTreeNode selectNode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();

		mainQueryPK = new MetaDataPK("com.kingdee.eas.hr.emp.app",
				"EmployeeNoPosition2Query");
		this.mainQuery = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		if (selectNode.getUserObject() instanceof EmployeeFenLeiInfo) {
			EmployeeFenLeiInfo fenLeiInfo = (EmployeeFenLeiInfo) selectNode
					.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("EmpFenLei.longNumber", fenLeiInfo
							.getLongNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("EmpFenLei.longNumber", fenLeiInfo
							.getLongNumber()
							+ "!%", CompareType.LIKE));

			filter.setMaskString("#0 or #1");
		} else if (selectNode.getUserObject() instanceof EmployeeTypeInfo) {
			EmployeeTypeInfo typeInfo = (EmployeeTypeInfo) selectNode
					.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("employeeType.id", typeInfo.getId()
							.toString()));
		}

		if (!isShowAllAdmin) {
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("PMUser.id", SysContext.getSysContext()
							.getCurrentUserInfo().getId().toString()));
			filterInfo.getFilterItems().add(
					new FilterItemInfo("OrgRange.type", new Integer(20)));

			if (hroSet != null && hroSet.size() > 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("ToUnit.id", hroSet,
								CompareType.INCLUDE));
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			} else if (!isShowUserOrgRange) {
				if (SysContext.getSysContext().getCurrentHRUnit() != null) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", SysContext
									.getSysContext().getCurrentHRUnit().getId()
									.toString()));
				} else {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", "nodata"));
				}
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			}

			filter.mergeFilter(filterInfo, "and");
		}
		this.mainQuery.setFilter(filter);
		getCustomFilter();
		this.tblMain.removeRows();

		// 有些测试人员的机器Table没有滚动条，符修湖说调用这个方法让Table重新布局
		tblMain.reLayoutAndPaint();

		setCommonQueryPK();
	}

	public void setPersonFilterInfo() throws Exception {
		// 行政组织树隐藏时，该事件不用处理了。
		TreePath path = treeMain.getSelectionPath();
		if (path == null)
			return;
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();

		FilterInfo filter = new FilterInfo();
		if (treenode.getUserObject() != null
				&& treenode.getUserObject() instanceof AdminOrgUnitInfo) {
			chkInclude.setVisible(true);

			AdminOrgUnitInfo nodeInfo = (AdminOrgUnitInfo) treenode
					.getUserObject();
			// filter.getFilterItems().add(new FilterItemInfo("AdminOrgUnit.id",
			// nodeInfo.getId().toString()));

			if (chkInclude.isSelected()) {
				filter.getFilterItems().add(
						new FilterItemInfo("AdminOrgUnit.longNumber", nodeInfo
								.getLongNumber()
								+ "!%", CompareType.LIKE));
				filter.setMaskString("#0 or #1");
			}
		} else if (treenode.getUserObject() != null
				&& treenode.getUserObject() instanceof PositionInfo) {
			chkInclude.setVisible(false);
			PositionInfo nodeInfo = (PositionInfo) treenode.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("Position.id", nodeInfo.getId()
							.toString()));
		}

		if (!isShowAllAdmin) {
			FilterInfo filterInfo = new FilterInfo();

			if (hroSet != null && hroSet.size() > 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("ToUnit.id", hroSet,
								CompareType.INCLUDE));
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			} else if (!isShowUserOrgRange) {
				if (SysContext.getSysContext().getCurrentHRUnit() != null) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", SysContext
									.getSysContext().getCurrentHRUnit().getId()
									.toString()));
				} else {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", "nodata"));
				}
				filterInfo
						.getFilterItems()
						.add(
								new FilterItemInfo("TypeRela.id",
										"00000000-0000-0000-0000-0000000000100FE9F8B5"));
			}

			if (!isHRFilter) {
				// Modify by jiwei_xiao in DaMing
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("PMUser.id",
				// SysContext.getSysContext().getCurrentUserInfo
				// ().getId().toString()));
				// filterInfo.getFilterItems().add(new
				// FilterItemInfo("OrgRange.type", new Integer(20)));
			}
			if (!StringUtils.isEmpty(filter.toString())) {
				filter.mergeFilter(filterInfo, "AND");
			} else {
				filter = filterInfo;
			}
		}

		addFilterPositionEnable(filter);

		// add by xianfeng_zhu 2009-10-03
		addFilterPositionMemberEnable(filter);
		FilterInfo filterSealUp = new FilterInfo("AdminOrgUnit.isSealUp = 0");
		filter.mergeFilter(filterSealUp, "and");

		if (getAdditionalFilter() != null) {
			filter.mergeFilter(getAdditionalFilter(), "AND");
		}

		mainQuery.setFilter(filter);

		getCustomFilter();

		this.tblMain.removeRows();
	}

	/**
	 * 通用过滤查询时加上默认的过滤条件
	 */
	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filterInfo = new FilterInfo();
		try {
			if (!isShowAllAdmin) {
				if (hroSet != null && hroSet.size() > 0) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("ToUnit.id", hroSet,
									CompareType.INCLUDE));
					filterInfo
							.getFilterItems()
							.add(
									new FilterItemInfo("TypeRela.id",
											"00000000-0000-0000-0000-0000000000100FE9F8B5"));
				} else if (!isShowUserOrgRange) {
					if (SysContext.getSysContext().getCurrentHRUnit() != null) {
						filterInfo.getFilterItems().add(
								new FilterItemInfo("ToUnit.id", SysContext
										.getSysContext().getCurrentHRUnit()
										.getId().toString()));
					} else {
						filterInfo.getFilterItems().add(
								new FilterItemInfo("ToUnit.id", "nodata"));
					}
					filterInfo
							.getFilterItems()
							.add(
									new FilterItemInfo("TypeRela.id",
											"00000000-0000-0000-0000-0000000000100FE9F8B5"));
				}

				if (!isHRFilter) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("PMUser.id", SysContext
									.getSysContext().getCurrentUserInfo()
									.getId().toString()));
					filterInfo.getFilterItems()
							.add(
									new FilterItemInfo("OrgRange.type",
											new Integer(20)));
				}

				addFilterPositionEnable(filterInfo);

				// add by xianfeng_zhu 2009-10-03
				addFilterPositionMemberEnable(filterInfo);
			}

			int flag = 0;
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_PAYPERSON) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_PAYPERSON) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_PAYPERSON)).booleanValue())
					flag = 1;
				else
					flag = 0;
				FilterInfo ffInfo = new FilterInfo();
				ffInfo.getFilterItems().add(
						new FilterItemInfo("PersonCMP.isPayPerson",
								new Integer(flag)));
				if (filterInfo.getFilterItems().size() > 0)
					filterInfo.mergeFilter(ffInfo, "and");
				else
					filterInfo = ffInfo;
			}
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_TRYOUTPERSON) != null
					&& this.getUIContext().get(
							EmployeeMutiF7UI.SHOW_TRYOUTPERSON) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_TRYOUTPERSON)).booleanValue())
					flag = 1;
				else
					flag = 0;
				if (flag == 1) {
					FilterInfo ffInfo = new FilterInfo();
					ffInfo
							.getFilterItems()
							.add(
									new FilterItemInfo("EmployeeType.id",
											"00000000-0000-0000-0000-000000000002A29E85B3"));
					if (filterInfo.getFilterItems().size() > 0)
						filterInfo.mergeFilter(ffInfo, "and");
					else
						filterInfo = ffInfo;
				}
			}
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_SOCIETY) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_SOCIETY) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_SOCIETY)).booleanValue())
					flag = 1;
				else
					flag = 0;
				FilterInfo ffInfo = new FilterInfo();
				ffInfo.getFilterItems().add(
						new FilterItemInfo("SocialInsurance.isSocialInsu",
								new Integer(flag)));
				if (filterInfo.getFilterItems().size() > 0)
					filterInfo.mergeFilter(ffInfo, "and");
				else
					filterInfo = ffInfo;
			}
			// 干部过滤条件 add by jie_fan
			if (this.getUIContext().get(EmployeeMutiF7UI.SHOW_CADRE) != null
					&& this.getUIContext().get(EmployeeMutiF7UI.SHOW_CADRE) instanceof Boolean) {
				if (((Boolean) this.getUIContext().get(
						EmployeeMutiF7UI.SHOW_CADRE)).booleanValue())
					flag = 1;
				else
					flag = 0;
				if (flag == 1) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("isStandbyCadre", new Integer(
									flag)));
					if (filterInfo.getFilterItems().size() > 0) {
						filterInfo.mergeFilter(filter, "and");
					} else {
						filterInfo = filter;
					}
				}
			}
			// end
			if (noPositionPersonFilter != null) {
				filterInfo.mergeFilter(noPositionPersonFilter, "and");
			}
			FilterInfo filterSealUp = new FilterInfo(
					"AdminOrgUnit.isSealUp = 0");
			filterInfo.mergeFilter(filterSealUp, "and");
		} catch (Exception e) {
			handUIException(e);
		}

		return filterInfo;
	}

	/**
	 * 双击上面Table中的某行时加到已选列表中
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
			if (isSingleSelect) {
				if (rowID.length > 0) {
					if (tblMain.getRow(rowID[0]) == null)
						return;
					String strID = tblMain.getRow(rowID[0]).getCell("id")
							.getValue().toString();
					selectPersonSet = new HashSet();
					selPersonUnitMap = new HashMap();
					selPersonPositionMap = new HashMap();
					selectPersonSet.add(strID);

					setReturnPositionAndUnit(strID, rowID[0]);

					btnConfirm_actionPerformed(null);
				}
			} else {
				addDataToSelectTable(rowID);
			}
		}
	}

	/**
	 * 添加已选数据到已选Table中
	 * 
	 * @param rowID
	 */
	private void addDataToSelectTable(int[] rowID) throws Exception {
		String pId = null;
		for (int i = 0; i < rowID.length; i++) {
			if (tblMain.getRow(rowID[i]) == null)
				continue;
			pId = tblMain.getRow(rowID[i]).getCell("id").getValue().toString();
			if (selectPersonSet.contains(pId)) {
				continue;
			} else {
				IRow curRow = tblSelectPerson.addRow();
				curRow.getCell("id").setValue(pId);
				curRow.getCell("number").setValue(
						tblMain.getRow(rowID[i]).getCell("number").getValue());
				curRow.getCell("name").setValue(
						tblMain.getRow(rowID[i]).getCell("name").getValue());
				curRow.getCell("position")
						.setValue(
								tblMain.getRow(rowID[i]).getCell("position")
										.getValue());
				curRow.getCell("positionId").setValue(
						tblMain.getRow(rowID[i]).getCell("positionId")
								.getValue());
				// if(tabTree.getSelectedIndex() == 0)
				// {
				curRow.getCell("adminUnitId").setValue(
						tblMain.getRow(rowID[i]).getCell("adminUnitId")
								.getValue());
				curRow.getCell("adminUnit").setValue(
						tblMain.getRow(rowID[i]).getCell("adminUnit")
								.getValue());
				// 增加组织单元全名称显示
				curRow.getCell("adminDisName").setValue(
						tblMain.getRow(rowID[i]).getCell("adminDisName")
								.getValue());
				// }
				// else
				// {
				//curRow.getCell("adminUnitId").setValue(tblMain.getRow(rowID[i]
				// )
				// .getCell("gkAdminId").getValue());
				// curRow.getCell("adminUnit").setValue(tblMain.getRow(rowID[i])
				// .getCell("gkAdmin").getValue());
				// }
				selectPersonSet.add(pId);
				addPersonSelectPositionMap(rowID[i]);
				setReturnPositionAndUnit(pId, rowID[i]);

			}
		}
	}

	private void addPersonSelectPositionMap(int row) {
		if (tblMain.getRow(row).getCell("positionId").getValue() != null
				&& tblMain.getRow(row).getCell("adminUnitId").getValue() != null) {
			PositionInfo positionInfo = new PositionInfo();
			positionInfo.setId(BOSUuid.read(tblMain.getRow(row).getCell(
					"positionId").getValue().toString()));
			positionInfo.setName((String) tblMain.getRow(row).getCell(
					"position").getValue());
			AdminOrgUnitInfo adminInfo = new AdminOrgUnitInfo();
			adminInfo.setId(BOSUuid.read(tblMain.getRow(row).getCell(
					"adminUnitId").getValue().toString()));
			adminInfo.setName((String) tblMain.getRow(row).getCell("adminUnit")
					.getValue());
			adminInfo.setDisplayName((String) tblMain.getRow(row).getCell(
					"adminDisName").getValue());
			positionInfo.setAdminOrgUnit(adminInfo);
			String pId = tblMain.getRow(row).getCell("id").getValue()
					.toString();
			selectPersonPositionMap.put(pId, positionInfo);
		}

	}

	/**
	 * 从已选列表中删除选择的行
	 * 
	 * @param rowID
	 */
	private void delDataFromSelectTable(int[] rowID) {
		String pId = null;
		for (int i = rowID.length - 1; i >= 0; i--) {
			if (rowID[i] >= 0) {
				pId = tblSelectPerson.getRow(rowID[i]).getCell("id").getValue()
						.toString();
				tblSelectPerson.removeRow(rowID[i]);
				selectPersonSet.remove(pId);
				selectPersonPositionMap.remove(pId);
				selPersonPositionMap.remove(pId);
				selPersonUnitMap.remove(pId);
			}
		}
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog dialog = super.initCommonQueryDialog();
		if (hroSet != null && hroSet.size() > 0) {
			dialog.setPromptBoxFactory(new HRF7SingleChangePromptBoxFactory(
					hroSet));
		}
		return dialog;
	}

	/**
	 * 是否包含下级
	 */
	protected void chkInclude_actionPerformed(ActionEvent e) throws Exception {
		super.chkInclude_actionPerformed(e);

		treeMain_valueChanged(null);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		btnConfirm_actionPerformed(null);
	}

	/**
	 * 由于框架目前列表权限控制比较弱，所以我们在V6.0不支持列表字段权限 add by Eric Xieli 2008/06/30
	 */
	protected void doFieldPermission() {
	}

	protected void initKeyStroke() {

	}

	protected void btnMaxBothWin_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnMaxBothWin_actionPerformed(e);

		Window dialog = ComponentUtil.getOwnerWindow(null);
		if (!isMaxSize) {
			this.btnMaxBothWin.setToolTipText("窗口居中");
			winSize = dialog.getBounds();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
					dialog.getGraphicsConfiguration());
			Rectangle desktopBounds = new Rectangle(screenInsets.left,
					screenInsets.top, screenSize.width - screenInsets.left
							- screenInsets.right, screenSize.height
							- screenInsets.top - screenInsets.bottom);
			dialog.setBounds(0, 0, desktopBounds.width, desktopBounds.height);
		} else {
			this.btnMaxBothWin.setToolTipText("最大化");
			dialog.setBounds(winSize);
		}
		isMaxSize = !isMaxSize;
	}

	private void doPreSelect(Object[] preSelect) throws Exception {
		HashSet set = new HashSet();
		for (int i = 0; i < preSelect.length; i++) {
			if (preSelect[i] instanceof PersonInfo) {
				set.add(((PersonInfo) preSelect[i]).getId().toString());
			}
		}

		if (set.size() == 0)
			return;

		IMetaDataPK queryPK = new MetaDataPK(
				"com.kingdee.eas.hr.emp.app.EmployeeF7SelectQuery");
		IQueryExecutor queryExcu = QueryExecutorFactory
				.getRemoteInstance(queryPK);
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		fi.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		viewInfo.setFilter(fi);
		queryExcu.setObjectView(viewInfo);
		IRowSet rows = queryExcu.executeQuery();

		while (rows.next()) {
			IRow curRow = tblSelectPerson.addRow();
			curRow.getCell("id").setValue(BOSUuid.read(rows.getString("id")));
			curRow.getCell("number").setValue(rows.getString("number"));
			curRow.getCell("name").setValue(rows.getString("name"));
			curRow.getCell("position")
					.setValue(rows.getString("Position.name"));
			curRow.getCell("positionId")
					.setValue(rows.getString("Position.id"));

			curRow.getCell("adminUnitId").setValue(
					rows.getString("AdminOrgUnit.id"));
			curRow.getCell("adminUnit").setValue(
					rows.getString("AdminOrgUnit.name"));
			// 增加组织单元全名称显示
			curRow.getCell("adminDisName").setValue(
					rows.getString("AdminOrgUnit.displayName"));

			selectPersonSet.add(rows.getString("id"));
		}
	}

	public PersonInfo getSingleResult() {
		return personInfo;
	}

	public PersonCollection getMultiResult() {
		return personInfos;
	}
}
