/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.DefaultNoteDataProvider;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.eas.base.btp.client.BOTClientTools;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.basedata.mm.qm.client.multif7.MultiBaseDataAdapter;
import com.kingdee.eas.basedata.mm.qm.client.multif7.MultiBaseDataManager;
import com.kingdee.eas.basedata.mm.qm.client.multif7.MultiMaterialProcessor;
import com.kingdee.eas.basedata.mm.qm.client.multif7.MultiQIItemProcessor;
import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractEditUI;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.client.MaterialQueryListUI;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.scm.common.util.SCMConstant;
import com.kingdee.eas.scm.framework.bizflow.client.BizFlowClientHelper;
import com.kingdee.eas.scm.im.inv.client.InventoryListUI;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.eas.st.common.STBillBaseCodingRuleVo;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.MillerUtils.PrecisionManager;
import com.kingdee.eas.st.common.client.decorator.IUIDirector;
import com.kingdee.eas.st.common.client.utils.AuditUtils;
import com.kingdee.eas.st.common.client.utils.STRequiredUtils;
import com.kingdee.eas.st.common.listenerutils.ListenerManager;
import com.kingdee.eas.st.common.util.PermissionUtils;
import com.kingdee.eas.st.common.util.STCodingRuleUtils;
import com.kingdee.eas.st.common.util.STDataBinderUtil;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.st.common.util.director.IDirectable;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STBillBaseEditUI extends AbstractSTBillBaseEditUI {
	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -6010437587702488380L;

	protected static final String MENU_NAME = "MENU_NAME";

	protected static final String MENU_NUMBER = "MENU_NUMBER";

	protected static final String DESCBILL_BOSTYPE = "DESCBILL_BOSTYPE";

	protected static final String BOTPID = "BOTPID";

	private boolean isEditable = false; // 编码是否可以编辑

	protected PrecisionManager precisionManager = null;

	private String targetBillBosTypeAndAliasString;
	// private static final Logger logger =
	// CoreUIObject.getLogger(STBillBaseEditUI.class);

	/**
	 * UI修饰模型.
	 */
	protected IUIDirector uiDirector = null;

	/**
	 * 为了处理多页签时，前一、后一等操作，自动将页签切回第一个 added by miller_xiao 2008-01-12 21:13
	 */
	protected KDTabbedPane mainTabbedPane = null;

	/**
	 * 多基础资料选择管理器
	 */
	protected MultiBaseDataManager multBaseDataManager = new MultiBaseDataManager();

	// protected com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager
	// multSTBaseDataManager = new com.kingdee.eas.st.basedata.
	// multiF7.MultiBaseDataManager();

	protected String addNewPermItemName = null;

	private static final String STResource = "com.kingdee.eas.st.common.STResource";

	// 设置窗体标题使用
	private String ADDNEW = null;
	private String UPDATE = null;
	private String VIEW = null;

	private boolean isInitBotpReadOnly = false;// 是否已经设置过BOTP下推下来的控件的状态。
	private HashMap oldControlStatusMap = new HashMap();// 保存在设置BOTP状态之前的控件的状态。

	protected OrgUnitInfo orgInfo = null;

	/**
	 * 单头必填项控件集合，由于有些控件是可变必填的，因此经常需要在listener中设定xx.setRequired(true)；
	 * 还要在beforeStorefields中设定检查。
	 * 两道工序分别在不同的地方，容易遗漏。所以添加这么一个Set，在super.beforeStorefields中执行检查。
	 * 由于单头空间可以通过parent找到lable，进而找到描述文本，方便提示，而kdTable中的cell则不行,所以暂时只支持单头控件.
	 * 
	 * @author xiaofeng_liu
	 */
	protected Set headFieldRequiredSet = new HashSet();

	// 需要设置主业务组织的F7数组, 如D类基础资料和有委托关系的组织, 子类负责在initNeedMainOrgF7s中初始化
	private KDBizPromptBox[] prmtNeedOrgF7s = null;

	/** 查询即时库存窗口 */
	protected IUIWindow inventoryWindow = null;

	/** 编码规则缓存 */
	protected STBillBaseCodingRuleVo codingRuleVo = new STBillBaseCodingRuleVo();

	/** new afterChg */
	protected DataChangeListener mainOrgChangeListener = null;

	/** 是否正在载入，即是否正运行在onLoad方法中 **/
	protected boolean isLoading = false;

	private IUIWindow materialWindow = null;// 查询即时库存

	/**
	 * 监听管理器
	 * 
	 * 所加临听须为com.kingdee.eas.st.common.listenerutils.base.BaseDataListener子类
	 */
	protected ListenerManager listenerManager = new ListenerManager();

	/**
	 * output class constructor
	 */
	public STBillBaseEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// 去除license检验，须删除

		isLoading = true;

		// 保存组织单元 //如果在子类已经取过,就不再取了,提高性能 xmcy 2007-09-30
		if (orgInfo == null)
			orgInfo = getBizOrgUnitInfo();

		if (orgInfo != null)
			initUIMainOrgContext(orgInfo.getId().toString());

		super.onLoad();
		menuItemCopyFrom.setVisible(false);

		btnCopyFrom.setVisible(false);

		// 给行复制新增增加图标
		btnLineCopy.setVisible(false);
		btnLineCopy.setIcon(EASResource.getIcon("imgTbtn_copyline"));
		menuItemLineCopy.setVisible(false);
		menuItemLineCopy.setIcon(EASResource.getIcon("imgTbtn_copyline"));

		menuBizProcess.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		workbtnBizProcess.setIcon(EASResource
				.getIcon("imgTbtn_associatecreate"));

		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
		this.menuItemAudit.setVisible(true);
		this.menuItemUnAudit.setVisible(true);

		// 设置窗体标题使用
		ADDNEW = getSTResource("ADDNEW");
		UPDATE = getSTResource("UPDATE");
		VIEW = getSTResource("VIEW");

		setAuditEnabled();
		setCurrentStorageMenu(); // 设置是否显示即时库存查询菜单

		// if(STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		setEntryEditable();
		// 禁用掉所有KDTable上的向下箭头和回车新增行的功能,设置单元格复制时，只复制值，不复制背景色
		setKDTableFunction();

		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setEnabled(false);
		this.actionSubmitAndPrint.setVisible(true);
		this.chkMenuItemSubmitAndPrint.setVisible(true);

		this.actionSubmitAndPrint.setEnabled(true);
		this.chkMenuItemSubmitAndPrint.setEnabled(true);
		ResourceBundleHelper resHelper2 = new ResourceBundleHelper(
				AbstractEditUI.class.getName());
		this.chkMenuItemSubmitAndPrint.setText(resHelper2
				.getString("chkMenuItemSubmitAndPrint.text"));
		this.chkMenuItemSubmitAndPrint.setMnemonic(80);
		this.initNeedMainOrgF7s();

		setOldData(editData);
		HashMap param = new HashMap();
		if (editData.getId() != null) {
			param.put("infoId", editData.getId().toString());
		}
		param.put("bostype", editData.getBOSType().toString());
		setSTBillParam(param); // 设置钢铁通用参数
		setBizProcessControl();

		setCopyLineEnabled(false);

		if (!this.editData.isIsFromBOTP()
				&& STATUS_ADDNEW.equalsIgnoreCase(getOprtState())) {
			setAutoNumberWhenHasCodingRule();
		} else if (STATUS_EDIT.equalsIgnoreCase(getOprtState())) {
			if (this.editData.isIsFromBOTP() && editData.getNumber() == null) {
				setAutoNumberWhenHasCodingRule();
			} else {
				setNumberEnabled(null);
			}
		}

		if (this.editData.isIsFromBOTP()
				&& STATUS_ADDNEW.equalsIgnoreCase(getOprtState())) {
			if (this.editData.isIsFromBOTP() && editData.getNumber() == null) {
				setAutoNumberWhenHasCodingRule();
			}
		}

		isLoading = false;

		// 处理左右对齐的问题
		KDTable[] tables = getAllEntry();
		if (tables != null) {
			for (int i = 0; i < tables.length; i++) {
				// FIXME KDTableUtils.setTableAlign(tables[i],
				// dataBinder);//对齐暂时屏蔽，待寻找替代工具
			}
		}

		// 设置默认值
		if (getOprtState() == STATUS_ADDNEW) {
			setDefaultValue();
		}

	}

	protected void setDefaultValue() {
		// 设置默认值
	}

	protected void setCopyLineEnabled(boolean isEnabled) {
		/**
		 * 修改记录 1 修改内容 ：屏蔽掉框架里的复制分录按钮，用自己的 modified by miller_xiao 2008-12-29
		 * 16:41:31
		 */
		actionCopyLine.setEnabled(isEnabled);
		menuItemCopyLine.setEnabled(isEnabled);
		btnCopyLine.setEnabled(isEnabled);
		actionCopyLine.setVisible(isEnabled);
		btnCopyLine.setVisible(isEnabled);
		menuItemCopyLine.setVisible(isEnabled);
	}

	/**
	 * 如果需要显示即时库存查询菜单，必须重载且返回true。
	 * 
	 * @return
	 */
	protected boolean isCurrentStorageShow() {
		return false;
	}

	/**
	 * 执行即时明细库库存查询。
	 */
	public void actionCurrentStorage_actionPerformed(ActionEvent e)
			throws Exception {
		/**
		 * 1.检查用户是否有权限操作 <br>
		 * 2.检查是否只有一个条件(存的是库存组织) <br>
		 */
		HashMap hm = getQueryCondition();
		// if(hm == null || hm.size() == 0){
		// //提示用户，先选择库存组织
		// MsgBox.showError("查询即时库存，请先选择对应的库存组织！");
		// return;
		// }
		//    	
		// // 库存组织
		// StorageOrgUnitInfo aStorageOrgUnitInfo = (StorageOrgUnitInfo) hm
		// .get(STCurrentStorageUtils.QUERY_STORAGEORGUNIT);
		// if(aStorageOrgUnitInfo == null){
		// //提示用户，先选择库存组织
		// MsgBox.showError("查询即时库存，请先选择对应的库存组织！");
		// return;
		// }

		queryProduceDetailByCondition(hm);
	}

	/**
	 * 根据汇总页签上的描述得到过滤条件Map， 要实现即时库存查询的单据一定要实现此方法。
	 * 
	 * @return
	 * @throws Exception
	 */
	protected HashMap getQueryCondition() throws Exception {
		return null;
	}

	/**
	 * 执行查询。
	 * 
	 * @param hm
	 * @throws Exception
	 */
	protected void queryProduceDetailByCondition(HashMap hm) throws Exception {
		// 子类做具体的业务
		UIContext uiContext = new UIContext(this);

		com.kingdee.eas.common.client.SysContext.getSysContext().setProperty(
				"st_currentstorage_properties", hm);

		try {
			inventoryWindow = UIFactory
					.createUIFactory(UIFactoryName.MODEL)
					.create(
							"com.kingdee.eas.st.produce.biz.client.ProduceDetailTreeListUI",
							uiContext, null, OprtState.VIEW);

			inventoryWindow.show();
		} finally {
			// 不管怎样，最后都要保证清掉暂存的Properties
			clearPropertiesAfterQuery();
		}
	}

	protected void clearPropertiesAfterQuery() {
		HashMap hm = (HashMap) com.kingdee.eas.common.client.SysContext
				.getSysContext().getProperty("st_currentstorage_properties");
		if (hm != null) {
			hm.clear();
			hm = null;
		}
		com.kingdee.eas.common.client.SysContext.getSysContext().setProperty(
				"st_currentstorage_properties", null);
	}

	protected void setCurrentStorageMenu() {
		boolean blShow = isCurrentStorageShow();
		Icon iconQueryByMaterial = EASResource
				.getIcon("imgTbtn_demandcollateresult");
		this.menuItemCurrentStorage.setVisible(blShow);
		this.menuItemCurrentStorage.setIcon(iconQueryByMaterial);
		this.actionCurrentStorage.setVisible(blShow);
		this.menuItemCurrentStorage.setEnabled(blShow);
		this.actionCurrentStorage.setEnabled(blShow);
		this.currentStorageSeparator.setVisible(blShow);
	}

	/**
	 * 设置oldData的值等于value，目的是在关闭单据时，不弹出提示保存的对话框 /*
	 * 
	 * @author zhiwei_wang
	 * 
	 * @param value
	 */
	protected void setOldData(STBillBaseInfo value) {
		if (!value.isIsFromBOTP()) {
			super.storeFields();
			initOldData(value);
		}
	}

	/**
	 * 描述：初始化需要设置主业务组织F7数组, 子类需覆盖
	 * 
	 * @author:paul 创建时间：2006-8-15
	 *              <p>
	 */
	protected void initNeedMainOrgF7s() {

	}

	/**
	 * 描述：设置需要主业务组织的组织F7列表
	 * 
	 * @param f7s
	 *            , cols
	 * @return
	 * @author:paul 创建时间：2006-8-18
	 *              <p>
	 */
	protected void setNeedMainOrgF7s(KDBizPromptBox[] f7s) {
		prmtNeedOrgF7s = f7s;

	}

	public void onShow() throws Exception {

		super.onShow();

		setAuditEnabled();

		if (!isInitBotpReadOnly) {
			setBOTPReadOnly();

		}
	}

	protected void setAuditEnabled() {

		if (STUtils.isNull(editData)) {
			return;
		}

		// 新增状态
		if (STATUS_ADDNEW.equalsIgnoreCase(getOprtState())) {
			actionEdit.setEnabled(false);
			actionSubmit.setEnabled(true);
			actionMultiapprove.setEnabled(false);

		} else if (STATUS_EDIT.equals(getOprtState())) {
			actionEdit.setEnabled(false);
			actionMultiapprove.setEnabled(true);

			// 通过上一下一查看时要控制actionSubmit和actionSave的状态,如果已审核则灰显
			if (BillBaseStatusEnum.AUDITED.equals(editData.getBillStatus())) {
				actionSave.setEnabled(false);
				actionSubmit.setEnabled(false);
			} else if (BillBaseStatusEnum.SUBMITED.equals(editData
					.getBillStatus())) {
				actionSave.setEnabled(false);
				actionSubmit.setEnabled(true);
			} else {
				actionSave.setEnabled(true);
				actionSubmit.setEnabled(true);
			}
		} else if (STATUS_VIEW.equalsIgnoreCase(getOprtState())) {
			actionEdit.setVisible(true);
			actionMultiapprove.setEnabled(true);
			actionSave.setEnabled(false); // 察看状态,禁用保存和提交按钮
			actionSubmit.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.btnSubmit.setEnabled(false);
			this.menuItemSubmit.setEnabled(false);
		}

		if (editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED_VALUE) {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(true);
			this.menuItemAudit.setEnabled(false);
			this.menuItemUnAudit.setEnabled(true);
		} else if (editData.getBillStatus().getValue() == BillBaseStatusEnum.SUBMITED_VALUE) {
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(false);
			this.menuItemAudit.setEnabled(true);
			this.menuItemUnAudit.setEnabled(false);
		} else if (editData.getBillStatus().getValue() == BillBaseStatusEnum.ALTERING_VALUE) {
			this.btnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(false);
			this.menuItemAudit.setEnabled(true);
			this.menuItemUnAudit.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.menuItemSave.setEnabled(false);
			this.btnSubmit.setEnabled(false);
			this.menuItemSubmit.setEnabled(false);

		} else {
			this.btnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
			this.menuItemUnAudit.setEnabled(false);
		}
	}

	/**
	 * 把number的值赋予caller中相应的属性.子类覆盖
	 * 
	 * 写法如下： super.prepareNumber(caller,number); txtXXXX.setText(number);
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		caller.setString("number", number);
	}

	/**
	 * 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入.
	 * 
	 * 写法如下： super.setNumberTextEnabled(); txtXXXX.setEnable(true);
	 */
	protected void setNumberTextEnabled() {

	}

	// 回收编码
	protected void removeByPK(IObjectPK pk) throws Exception {
		IObjectValue editData = this.editData;
		if (pk != null) {
			super.removeByPK(pk);
			recycleNumberByOrg(editData, "NONE", editData.getString("number"));
		}
	}

	protected void recycleNumberByOrg(IObjectValue editData, String orgType,
			String number) {
		if (!StringUtils.isEmpty(number)) {
			try {
				// 将方法放至后台处理,减少RPC调用 xmcy 2007-09-29
				ICoreBase ie = getBizInterface();
				if (ie instanceof ISTBillBase) {
					((ISTBillBase) ie).recycleNumberByOrg(
							(STBillBaseInfo) editData, orgType, number);
				}
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}

	public KDBizPromptBox getMainOrgUnit() {
		return null;
	}

	/**
	 * 获取编码规则。
	 * 
	 * @param orgType
	 * @return
	 */
	public STBillBaseCodingRuleVo getSTCodingRuleVoByOrg(String orgType) {
		STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo = new STBillBaseCodingRuleVo();
		try {
			// 将方法放至后台处理,减少RPC调用 xmcy 2007-09-29
			if (editData.getCU() == null) {
				// editData.setCU(getMainOrgInfo().getCU());
				editData.setCU(SysContext.getSysContext().getCurrentOrgUnit()
						.getCU());
				OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit(
						getMainType());
				if (org != null && org.getCU() != null) {
					editData.setCU(org.getCU()); // 加了判空操作，by miller_xiao
													// 2009-01-20 23:33
				}
			}

			aSTBillBaseCodingRuleVo.setCoreBillInfo(editData);
			aSTBillBaseCodingRuleVo.setOrgType(orgType);

			// 根据选择的组织取编码规则
			OrgUnitInfo orgUnitInfo = null;
			KDBizPromptBox prmtOrg = getMainOrgUnit();
			if (STUtils.isNotNull(prmtOrg)) {
				Object o = prmtOrg.getValue();
				if (o instanceof OrgUnitInfo) {
					OrgUnitInfo _orgUnitInfo = (OrgUnitInfo) o;
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
							.getRemoteInstance();
					if (iCodingRuleManager.isExist(editData, _orgUnitInfo
							.getId().toString()))
						orgUnitInfo = _orgUnitInfo;
				}
			}

			// 如果传入的组织为空，则取登陆组织
			if (STUtils.isNull(orgUnitInfo)) {
				if (!StringUtils.isEmpty(orgType)
						&& !"NONE".equalsIgnoreCase(orgType)
						&& SysContext.getSysContext().getCurrentOrgUnit(
								OrgType.getEnum(orgType)) != null) {
					orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit(
							OrgType.getEnum(orgType));
				} else if (SysContext.getSysContext().getCurrentOrgUnit() != null) {
					orgUnitInfo = (OrgUnitInfo) SysContext.getSysContext()
							.getCurrentOrgUnit();
				}
			}

			if (STUtils.isNotNull(orgUnitInfo)
					&& STUtils.isNotNull(orgUnitInfo.getId())) {
				if (editData.getCU() == null) {
					editData.setCU(orgUnitInfo.getCU());
				}
				if (editData.getCU() == null) {
					editData.setCU(SysContext.getSysContext()
							.getCurrentOrgUnit(getMainType()).getCU());
				}
				aSTBillBaseCodingRuleVo.setCompanyID(orgUnitInfo.getId()
						.toString());
			} else {
				if (editData.getCU() == null) {
					editData.setCU(SysContext.getSysContext()
							.getCurrentOrgUnit(getMainType()).getCU());
				}
			}

			aSTBillBaseCodingRuleVo = getSTBillVOToControl(aSTBillBaseCodingRuleVo);

		} catch (Exception e) {
			handUIException(e);
		}

		return aSTBillBaseCodingRuleVo;
	}

	/**
	 * 设置根据编码规则带出来的编码.
	 * 
	 * @param orgType
	 */
	protected void setAutoNumberByOrgByPass(String orgType) {

		STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo = getSTCodingRuleVoByOrg(orgType);

		if ((BillBaseStatusEnum.NULL.equals(editData.getBillStatus())
				|| BillBaseStatusEnum.ADD.equals(editData.getBillStatus()) || BillBaseStatusEnum.TEMPORARILYSAVED
				.equals(editData.getBillStatus()))
		// && STATUS_ADDNEW.equals(this.getOprtState())
		) {
			// 按组织设置单据编码规则
			String sysNumber = aSTBillBaseCodingRuleVo.getSysNumber();
			codingRuleVo = aSTBillBaseCodingRuleVo; // 每次取了之后取当前最新的设置

			if ((editData.getNumber() == null || editData.getNumber().trim()
					.length() == 0)
					&& sysNumber != null && sysNumber.trim().length() > 0) {
				editData.setNumber(sysNumber);
				// 同时给界面赋值
				Component txtNumber = dataBinder.getComponetByField("number");
				if (txtNumber instanceof KDTextField) {
					if (codingRuleVo.isExist() && !codingRuleVo.isAddView()) {
						// 新增不显示
						editData.setNumber(null);
						((KDTextField) txtNumber).setText(null);
					} else {
						editData.setNumber(sysNumber);
						((KDTextField) txtNumber).setText(sysNumber);
					}
				}
			}
		}

		setNumberEnabled(aSTBillBaseCodingRuleVo);
	}

	protected void setNumberEnabled(
			STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo) {
		if (aSTBillBaseCodingRuleVo == null) {
			if (getMainBizOrgType() != null)
				aSTBillBaseCodingRuleVo = getSTCodingRuleVoByOrg(getMainBizOrgType()
						.toString());
			else
				aSTBillBaseCodingRuleVo = getSTCodingRuleVoByOrg(null);
		}

		// 管理编码文本框的输入状态
		// 根据editData单据状态来判断以及Edit界面状态来判断
		if ((BillBaseStatusEnum.NULL.equals(editData.getBillStatus())
				|| BillBaseStatusEnum.ADD.equals(editData.getBillStatus()) || BillBaseStatusEnum.TEMPORARILYSAVED
				.equals(editData.getBillStatus()))
				&& (STATUS_ADDNEW.equals(this.getOprtState()) || STATUS_EDIT
						.equals(this.getOprtState()))) {
			boolean isModifiable = false;
			boolean isExist = false;
			try {
				isExist = aSTBillBaseCodingRuleVo.isExist();
				if (isExist)
					isModifiable = aSTBillBaseCodingRuleVo.isModifiable();
			} catch (Exception e) {
			}
			Component txtNumber = dataBinder.getComponetByField("number");
			if (STQMUtils.isNotNull(txtNumber)) {
				boolean isEnabled = !isExist || isModifiable;
				txtNumber.setEnabled(isEnabled);
			}
			if (txtNumber.isEnabled())
				txtNumber.requestFocus();
		} else {// 非新增状态则不许修改编码规则
			Component txtNumber = dataBinder.getComponetByField("number");
			if (STUtils.isNotNull(txtNumber)) {
				txtNumber.setEnabled(false);
			}
		}

		// 设置主业务组织是否可编辑
		STCodingRuleUtils.setMainOrgUnitEditable(aSTBillBaseCodingRuleVo,
				getMainOrgUnit(), editData.getBillStatus());

		// 如果不存在编码规则，则允许录入编码
		if ((!aSTBillBaseCodingRuleVo.isExist())
				|| aSTBillBaseCodingRuleVo.isModifiable()) {
			Component txtNumber = dataBinder.getComponetByField("number");
			if (STUtils.isNotNull(txtNumber)) {
				((KDTextField) txtNumber).setEditable(true);
				((KDTextField) txtNumber).setEnabled(true);
			}
		}
	}

	/**
	 * 设置参数。
	 * 
	 * @param map
	 */
	public void setSTBillParam(HashMap param) {
		HashMap map = new HashMap();
		try {
			ICoreBase ie = getBizInterface();
			map = ((ISTBillBase) ie).getSTBillParam(param);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 设置是否提交即打印的状态。
		if (map.get("isSubmitPrint") != null) {
			if (map.get("isSubmitPrint").toString().equals("1"))
				this.chkMenuItemSubmitAndPrint.setSelected(true);
			else
				this.chkMenuItemSubmitAndPrint.setSelected(false);

		}
		if (map.get("isAuditPrint") != null) {
			if (map.get("isAuditPrint").toString().equals("1"))
				this.chkAuditAndPrint.setSelected(true);
			else
				this.chkAuditAndPrint.setSelected(false);

		}
		if (map.get("targetBillBosTypeAndAliasString") != null)
			targetBillBosTypeAndAliasString = map.get(
					"targetBillBosTypeAndAliasString").toString();

		// 赋已打印次数的控件。
		if (getPrintQtyControl() != null) {
			getPrintQtyControl().setEditable(false);
			if (map.get("BillprintQty") != null)
				getPrintQtyControl()
						.setText(map.get("BillprintQty").toString());
			else
				getPrintQtyControl().setText("0");
		}
	}

	public STBillBaseCodingRuleVo getSTBillVOToControl(
			STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo) {

		if (aSTBillBaseCodingRuleVo == null) {
			aSTBillBaseCodingRuleVo = new STBillBaseCodingRuleVo();
			// 54错误 姜宜辉 2009-07-20
			// 修改功能描述：修正当打印或者打印预览时，编辑界面的已打印次数显示为0的错误
			String orgType = this.getMainBizOrgType().toString();
			setSTBillBaseCodingRuleVoByOrgType(aSTBillBaseCodingRuleVo, orgType);
		}
		HashMap map = new HashMap();
		map.put("codeRuleVo", aSTBillBaseCodingRuleVo);
		map.put("ui", this.getClass().getName().toString());
		// 更换为整体的取数接口，因为这个方法是所有单据都会在loadfileds进行操作的，故放在此xmcy
		// aSTBillBaseCodingRuleVo = ((ISTBillBase) ie)
		// .getCodeRuleBizVo(aSTBillBaseCodingRuleVo);

		// 在这里屏蔽异常，因为当前组织不存在编码组织时，还是允许进行操作，不用抛出异常的，只打印出来xmcy

		try {
			ICoreBase ie = getBizInterface();
			map = ((ISTBillBase) ie).getSTBillVo(map);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		aSTBillBaseCodingRuleVo = (STBillBaseCodingRuleVo) map
				.get("codeRuleVo");
		// 设置是否提交即打印的状态。
		if (map.get("isSubmitPrint") != null) {
			if (map.get("isSubmitPrint").toString().equals("1"))
				this.chkMenuItemSubmitAndPrint.setSelected(true);
			else
				this.chkMenuItemSubmitAndPrint.setSelected(false);

		}
		if (map.get("isAuditPrint") != null) {
			if (map.get("isAuditPrint").toString().equals("1"))
				this.chkAuditAndPrint.setSelected(true);
			else
				this.chkAuditAndPrint.setSelected(false);

		}
		if (map.get("targetBillBosTypeAndAliasString") != null)
			targetBillBosTypeAndAliasString = map.get(
					"targetBillBosTypeAndAliasString").toString();

		// 赋已打印次数的控件。
		if (getPrintQtyControl() != null) {
			getPrintQtyControl().setEditable(false);
			if (map.get("BillprintQty") != null)
				getPrintQtyControl()
						.setText(map.get("BillprintQty").toString());
			else
				getPrintQtyControl().setText("0");

		}

		return aSTBillBaseCodingRuleVo;
	}

	// 54错误 姜宜辉
	// 修改功能描述：修正当打印或者打印预览时，编辑界面的已打印次数显示为0的错误
	private void setSTBillBaseCodingRuleVoByOrgType(
			STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo, String orgType) {
		aSTBillBaseCodingRuleVo.setOrgType(orgType);
		if (editData.getCU() == null) {
			// editData.setCU(getMainOrgInfo().getCU());
			editData.setCU(SysContext.getSysContext().getCurrentOrgUnit()
					.getCU());
		}
		aSTBillBaseCodingRuleVo.setCoreBillInfo(editData);
		// 根据选择的组织取编码规则
		OrgUnitInfo orgUnitInfo = null;
		KDBizPromptBox prmtOrg = getMainOrgUnit();
		if (STUtils.isNotNull(prmtOrg)) {
			Object o = prmtOrg.getValue();
			if (o instanceof OrgUnitInfo) {
				orgUnitInfo = (OrgUnitInfo) o;
			}
		}

		if (!STUtils.isNull(orgUnitInfo)) {
			// 如果有主业务组织控件,但其没有内容,退出
			// return;

			// 如果传入的组织为空，则取登陆组织
			if (STUtils.isNull(orgUnitInfo)) {
				orgUnitInfo = com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit(
								com.kingdee.eas.basedata.org.OrgType
										.getEnum(orgType));
			}

			if (STUtils.isNull(orgUnitInfo)) {
				if (!com.kingdee.util.StringUtils.isEmpty(orgType)
						&& !"NONE".equalsIgnoreCase(orgType)
						&& com.kingdee.eas.common.client.SysContext
								.getSysContext().getCurrentOrgUnit(
										com.kingdee.eas.basedata.org.OrgType
												.getEnum(orgType)) != null) {

					orgUnitInfo = com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit(
									com.kingdee.eas.basedata.org.OrgType
											.getEnum(orgType));

				} else if (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentOrgUnit() != null) {
					orgUnitInfo = (OrgUnitInfo) com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentOrgUnit();
				}
			}

			if (STUtils.isNotNull(orgUnitInfo)
					&& STUtils.isNotNull(orgUnitInfo.getId())) {
				aSTBillBaseCodingRuleVo.setCompanyID(orgUnitInfo.getId()
						.toString());
			}
		}
	}

	/**
	 * edit by xiaofeng_liu 2007.11.23.
	 * 因为比较怪异的问题，导致我把原来的这段代码拷贝到一个新方法中，然后再在本方法中调用新方法。
	 * 这个问问题就是：SamplebillEdit，QiDelegateBillEdit元数据发布后的Abstract
	 * ***EditUI.java代码中会生成一个同名方法setAutoNumberByOrg，
	 * 并且在同类内的loadfields中调用这个方法。这个自动生成的同名方法会彻底覆盖了我们这个方法，造成了一些bug。
	 * 起初我的办法是直接copy这个方法的代码到具体类，但是由于这段代码还不稳定，修改后没有同步则会出现bug，实际上已经出现了不少次了。
	 * 同时由于这个问题翁列加那边还为找到原因，很难保证以后某个EditUI不会出现同样的问题。
	 * 如此我还不如在本类中新建一个方法，然后把本方法代码逻辑迁移到新方法中，这样我在具体类里面就可以调用这段逻辑了。
	 * 同时在SampleBillEditUI
	 * .java，QIDelegateBillEditUI.java中覆盖setAutoNumberByOrg为一个空方法。还可以减少那次无谓的rpc。
	 * 新方法名就是setAutoNumberByOrgByPass。
	 * 
	 * @param orgType
	 */
	protected void setAutoNumberByOrg(String orgType) {

		this.setAutoNumberByOrgByPass(orgType);

	}

	public void actionSubmitAndPrint_actionPerformed(ActionEvent arg0)
			throws Exception {

		if (!checkInput())
			return;

		// TODO Auto-generated method stub
		super.actionSubmitAndPrint_actionPerformed(arg0);
		// HashMap map=new HashMap();
		// map.put("ui", this.getClass().getName().toString());
		// map.put("configType", "isSubmitPrint");
		// if (this.chkMenuItemSubmitAndPrint.isSelected())
		// map.put("configValue", "1");
		// else
		// map.put("configValue", "0");
		// ISTBillConfig iStBillConfig=STBillConfigFactory.getRemoteInstance();
		// iStBillConfig.setConfigValue(map);
	}

	public KDTextField getPrintQtyControl() {

		return null;

	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {

		// 设置自动编码
		// setAutoNumberByOrg("NONE");
		listenerManager.setEanbleListener(false);
		dataBinder.loadFields();
		listenerManager.setEanbleListener(true);
		setAuditEnabled();

		// if(STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		setEntryEditable();
	}

	/**
	 * 
	 * 描述：用UI分离器修饰XXXUI.
	 * 
	 * @author:daij 创建时间：2006-11-17
	 *              <p>
	 */
	protected final void decorateUI() throws Exception {
		if (STQMUtils.isNull(uiDirector)) {
			if (this instanceof IDirectable) {
				// 如果希望使用UI修饰模型则由子类实例化AbstractUIDirector
				((IDirectable) this).setupDirector();
			}
		}

		if (STQMUtils.isNotNull(uiDirector)) {
			// 整理UI绑定的数据.
			uiDirector.trimData();
			// 安装UI Element
			uiDirector.setupUIElement();
			// UI数据装载
			uiDirector.loadData();
			// 修饰UI Element
			uiDirector.decorateUIElement();
		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.kingdee.eas.framework.client.EditUI#setFieldsNull(com.kingdee.bos
	 * .dao.AbstractObjectValue)
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		// TODO 自动生成方法存根
		super.setFieldsNull(newData);
		STBillBaseInfo stInfo = (STBillBaseInfo) newData;
		stInfo.setNumber(null);
		stInfo.setIsFromBOTP(false);
		stInfo
				.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
						.getSysContext().getCurrentUserInfo()));
		stInfo.setCreateTime(null);
		stInfo.setAuditor(null);
		stInfo.setAuditTime(null);
		stInfo.setLastUpdateUser(null);
		stInfo.setLastUpdateTime(null);
		stInfo.setBillStatus(BillBaseStatusEnum.ADD);

		// 设置分录的来源单据信息为空
		Object o = stInfo.get(getEntriesName());
		if (o instanceof AbstractObjectCollection) {
			AbstractObjectCollection collection = (AbstractObjectCollection) o;
			for (int i = 0, count = collection.size(); i < count; i++) {
				IObjectValue entriesInfo = collection.getObject(i);
				entriesInfo.setString("contractId", null);
				entriesInfo.setString("contractEntryId", null);
				entriesInfo.setString("contractNumber", null);
				entriesInfo.setString("contractType", null);
				entriesInfo.setString("coreBillId", null);
				entriesInfo.setString("coreBillEntryId", null);
				entriesInfo.setString("coreBillNumber", null);
				entriesInfo.setString("coreBillType", null);
				entriesInfo.setString("sourceBillId", null);
				entriesInfo.setString("sourceBillEntryId", null);
				entriesInfo.setString("sourceBillNumber", null);
				entriesInfo.setString("sourceBillType", null);
			}
		}

	}

	protected String getEntriesName() {
		return "entries";
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	protected void initDataStatus() {
		super.initDataStatus();

		setBOTPReadOnly();
		setStatus();

	}

	private void setBOTPReadOnly() {
		// 由于设置BOTP下推状态，必须是在LOADFILEDS之前，而在loadfileds之前，EDITUI中的关系还没取出来，
		// 所以这样为了处理只能再取一次。

		if (isInitBotpReadOnly)
			return;

		Object id = null;
		if (editData != null)
			id = this.editData.getId();
		else
			id = getUIContext().get(UIContext.ID);

		try {
			if (id != null) {
				// 为修改检斤单两次点击保存时出现中断的问题 bugId：PBG020024 added by yangyong
				// 20081028
				// if (getMakeRelations() == null)
				// {
				// IBTPManager iBTPManager =
				// BTPManagerFactory.getRemoteInstance();
				// this.setMakeRelations(iBTPManager.getRelationCollection(id.
				// toString()));
				// }
				// edit end 去掉判空的判断，每次都取最新的关系
			}
			if (getMakeRelations() != null) {
				BOTRelationCollection aBOTRelationCollection = getMakeRelations();
				// 单据下推下来只会应用一个规则，故这边就不循环处理 ，就直接取第一个对象进行取规则属性处理
				if (aBOTRelationCollection.size() > 0) {
					BOTRelationInfo aBOTRelationInfo = aBOTRelationCollection
							.get(0);
					IBOTMapping iBOTMapping = BOTMappingFactory
							.getRemoteInstance();

					// 为修改检斤单两次点击保存时出现中断的问题 bugId：PBG020024 added by yangyong
					// 20081028
					String mappingId = aBOTRelationInfo.getBOTMappingID();
					if (mappingId == null || mappingId.trim().length() == 0) {
						return;
					}
					// add end

					List l = iBOTMapping
							.getBillPropertiesCannotModify(new ObjectUuidPK(
									aBOTRelationInfo.getBOTMappingID()));
					if (l.size() > 0) {
						isInitBotpReadOnly = true;
						oldControlStatusMap = new HashMap();
						for (int i = 0; i < l.size(); i++) {
							if (l.get(i) == null)
								continue;

							Object comp = (Object) STDataBinderUtil
									.getUICompValueForBotp(this.dataBinder, l
											.get(i).toString());

							if (comp == null)
								continue;

							if (comp instanceof Component) {
								oldControlStatusMap.put(comp,
										Boolean.valueOf(((Component) comp)
												.isEnabled()));
								((Component) comp).setEnabled(false);

							} else if (comp instanceof IKDTextComponent) {
								oldControlStatusMap.put(comp,
										Boolean.valueOf(((Component) comp)
												.isEnabled()));
								((IKDTextComponent) comp).setEnabled(false);

							} else if (comp instanceof KDPromptBox) {
								oldControlStatusMap.put(comp,
										Boolean.valueOf(((Component) comp)
												.isEnabled()));
								((KDPromptBox) comp).setEnabled(false);

							} else if (comp instanceof IColumn) {
								oldControlStatusMap.put(comp, Boolean
										.valueOf(((IColumn) comp)
												.getStyleAttributes()
												.isLocked()));
								((IColumn) comp).getStyleAttributes()
										.setLocked(true);
							} else if (comp instanceof IKDTextComponent
									&& !(comp instanceof KDPromptBox)
									&& !(comp instanceof KDComboBox)) {

								oldControlStatusMap.put(comp,
										Boolean.valueOf(((Component) comp)
												.isEnabled()));
								((IKDTextComponent) comp).setEnabled(false);
							} else if (comp instanceof KDCheckBox
									|| comp instanceof KDRadioButton) {

								oldControlStatusMap.put(comp,
										Boolean.valueOf(((Component) comp)
												.isEnabled()));
								((Component) comp).setEnabled(false);
							}
						}
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setStatus() {
		// 上下查非新增时一直可用
		actionTraceUp.setEnabled(true);
		btnTraceUp.setEnabled(true);
		menuItemTraceUp.setEnabled(true);
		actionTraceDown.setEnabled(true);
		btnTraceDown.setEnabled(true);
		menuItemTraceDown.setEnabled(true);

		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			// 上下查新增时不可用，
			actionTraceUp.setEnabled(false);
			actionTraceDown.setEnabled(false);
			menuItemTraceUp.setEnabled(false);
			menuItemTraceDown.setEnabled(false);

			// 行处理状态
			if (isFromBotp()) {// 关联生成的单据要灰显 行新增、插入
				setAddLineStatus(false);
			} else {
				// setAddLineStatus(true);
			}
			setRemoveLineStatus(true);

			// 关联生成状态
			setCreateFromStatus(true);

			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			menuItemCopy.setEnabled(false);
			menuItemRemove.setEnabled(false);

			// 打印按钮无效
			btnPrint.setEnabled(false);
			btnPrintPreview.setEnabled(false);
			menuItemPrint.setEnabled(false);
			menuItemPrintPreview.setEnabled(false);

		} else if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState())) {// 查看

			// 行处理状态___灰显 行新增、插入、删除
			setAddLineStatus(false);
			setRemoveLineStatus(false);

			// 上下查新增时不可用，
			actionTraceDown.setEnabled(true);

			// 关联生成状态_灰显
			setCreateFromStatus(false);

			if (STATUS_FINDVIEW.equals(getOprtState())) {// 工作流中)
				btnCopy.setEnabled(false);
				btnRemove.setEnabled(false);
				menuItemCopy.setEnabled(false);
				menuItemRemove.setEnabled(false);
				// 打印按钮无效
				btnPrint.setEnabled(false);
				btnPrintPreview.setEnabled(false);
				menuItemPrint.setEnabled(false);
				menuItemPrintPreview.setEnabled(false);

			} else {
				btnCopy.setEnabled(true);
				btnRemove.setEnabled(true);
				menuItemCopy.setEnabled(true);
				menuItemRemove.setEnabled(true);
				// 打印按钮无效
				btnPrint.setEnabled(true);
				btnPrintPreview.setEnabled(true);
				menuItemPrint.setEnabled(true);
				menuItemPrintPreview.setEnabled(true);

			}

			if (editData != null && editData.getBillStatus() != null
					&& editData.getBillStatus().getValue() == -3) {
				btnAudit.setEnabled(false);
				menuItemAudit.setEnabled(false);
				actionAudit.setEnabled(false);
				actionAddNew.setEnabled(false);
				actionSubmit.setEnabled(false);
				btnSubmit.setEnabled(false);
				actionCopy.setEnabled(false);
				btnCopy.setEnabled(false);
				actionEdit.setEnabled(false);
				btnEdit.setEnabled(false);
				menuItemEdit.setEnabled(false);
				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
				menuItemSave.setEnabled(false);
				menuItemUnAudit.setEnabled(false);
				btnUnAudit.setEnabled(false);
				actionAddNew.setEnabled(false);
				actionEdit.setEnabled(false);
				menuItemAddNew.setEnabled(false);
				menuItemEdit.setEnabled(false);
				btnAddNew.setEnabled(false);
				btnEdit.setEnabled(false);
				btnRemove.setEnabled(false);
				menuItemRemove.setEnabled(false);
				actionRemove.setEnabled(false);

			}

		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改
			actionEdit.setEnabled(false);
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);

			// 上下查新增时不可用，
			actionTraceDown.setEnabled(true);

			// 行处理状态
			if (isFromBotp()) {// 关联生成的单据要灰显 行新增、插入
				setAddLineStatus(false);
			}
			// else {
			// setAddLineStatus(true);
			// }
			setRemoveLineStatus(true);
			// 关联生成状态_可用
			setCreateFromStatus(true);
			btnCopy.setEnabled(true);
			btnRemove.setEnabled(true);
			menuItemCopy.setEnabled(true);
			menuItemRemove.setEnabled(true);
			// 打印按钮无效
			btnPrint.setEnabled(true);
			btnPrintPreview.setEnabled(true);
			menuItemPrint.setEnabled(true);
			menuItemPrintPreview.setEnabled(true);

		}

		setAuditEnabled();
	}

	private boolean isFromBotp() {
		boolean isFromBotp = false;
		// if (STUtils.isNotNull(editData)) {
		// if (editData.isIsFromBOTP()) {
		// isFromBotp = true;
		// }
		// }
		return isFromBotp;
	}

	/**
	 * 设置"行处理增"菜单工具栏状态
	 * 
	 * @param status
	 */
	protected void setAddLineStatus(boolean status) {

		actionAddLine.setEnabled(status);
		actionInsertLine.setEnabled(status);
		actionLineCopy.setEnabled(status);

		menuItemAddLine.setEnabled(status);
		menuItemInsertLine.setEnabled(status);
		menuItemLineCopy.setEnabled(status);

		btnAddLine.setEnabled(status);
		btnInsertLine.setEnabled(status);
		btnLineCopy.setEnabled(status);

	}

	// /**
	// * 设置"行处理删除"菜单工具栏状态
	// *
	// * @param status
	// */
	// protected void setAddLineStatus(boolean status) {
	//
	// actionRemoveLine.setEnabled(status);
	// menuItemRemoveLine.setEnabled(status);
	// btnRemoveLine.setEnabled(status);
	// }

	/**
	 * 设置"行处理删除"菜单工具栏状态
	 * 
	 * @param status
	 */
	protected void setLineCopyStatus(boolean status) {
		actionLineCopy.setEnabled(status);
		menuItemLineCopy.setEnabled(status);
		btnLineCopy.setEnabled(status);
	}

	/**
	 * 设置"行处理删除"菜单工具栏状态
	 * 
	 * @param status
	 */
	protected void setInsertLineStatus(boolean status) {
		actionInsertLine.setEnabled(status);
		menuItemInsertLine.setEnabled(status);
		btnInsertLine.setEnabled(status);
	}

	/**
	 * 设置"行处理删除"菜单工具栏状态
	 * 
	 * @param status
	 */
	protected void setRemoveLineStatus(boolean status) {
		actionRemoveLine.setEnabled(status);
		menuItemRemoveLine.setEnabled(status);
		btnRemoveLine.setEnabled(status);
	}

	/**
	 * 设置"关联生成"菜单工具栏状态
	 * 
	 * @param status
	 */
	protected void setCreateFromStatus(boolean status) {
		actionCreateFrom.setEnabled(status);
		menuItemCreateFrom.setEnabled(status);
		btnCreateFrom.setEnabled(status);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * 检查界面输入，正常返回true
	 * 
	 * @return
	 */
	protected boolean checkInput() {
		return true;
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.SUBMITED
						.getValue()) {
			// 单据提交状态不能保存
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_SUBMIT_NOT_SAVE));
		}

		// 单据提交时需要判断此时单据状态（作废、审核、关闭状态的单据不能保存）
		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// 单据作废状态不能保存
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELTET_NOT_SAVE));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// 单据审核状态不能保存
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_SAVE));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// 单据关闭状态不能保存
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_SAVE));
		}

		checkMainOrgUnit();

		if (!checkInput())
			return;

		// UI界面中用户调用的保存动作，设置为不是由BOTP调用
		// colin_xu,2007/05/28
		editData.setBotpCallSave(false);

		super.actionSave_actionPerformed(e);

		setAuditEnabled();
	}

	// 检查主业务组织是否为空. 因为过滤时主业务组织为必填，所以在保存时也需要检查否则未填写的单据无法查询出来. daij
	protected void checkMainOrgUnit() {
		if (getMainBizOrg() != null
				&& STClientUtils.isF7NullInfo(getMainBizOrg())) {

			OrgType orgType = getMainBizOrgType();
			String orgTypeString = (orgType == null) ? "" : "："
					+ orgType.getAlias();
			getMainOrgUnit().requestFocus();
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_MAINORG_ISNULL,
					new Object[] { orgTypeString }));
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

		if (!checkInput())
			return;

		// 单据提交时需要判断此时单据状态（作废、审核、关闭状态的单据不能提交）
		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// 单据作废状态不能提交
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELETE_NOT_SUBMIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// 单据审核状态不能提交
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_SUBMIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// 单据关闭状态不能提交
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_SUBMIT));
		}

		checkMainOrgUnit();

		// 设置CU,colin 2007-12-17
		if (getMainOrgInfo() != null)
			editData.setCU(getMainOrgInfo().getCU());

		if (editData.getId() != null) {
			IObjectPK pk = new ObjectUuidPK(editData.getId().toString());
			ISTBillBase ie = ((ISTBillBase) this.getBillInterface());
			// 如果存在再取值. botp生成单据，如果选择： 不保存单据然后提交那么editData中的id是假的，是没有对应的数据库记录的.
			// edit by daij
			if (ie.exists(pk)) {
				STBillBaseInfo oldinfo = ie.getSTBillBaseInfo(pk);
				if (oldinfo.getBillStatus() != null) {
					if (oldinfo.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED_VALUE) {
						// 单据审核状态不能修改
						super.handUIExceptionAndAbort(new STBillException(
								STBillException.EXC_BILL_AUDIT_NOT_SUBMIT));
					}
				}
			}
		}

		// checkMainOrgUnit();

		super.actionSubmit_actionPerformed(e);

		setAuditEnabled();

		if (chkMenuItemSubmitAndAddNew.isSelected()) {
			// 根据必要设置编码
			setAutoNumberWhenHasCodingRule();
		}
		setStatus();

		// 若单据已审核，则要将状态置为查看 Robin 2007-4-12
		if (editData.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {

		if (STQMUtils.isNotNull(uiDirector)) {
			uiDirector.validateBizLogic(editData);
		}
		checkAndSetNumberField();
		
		
	}

	/**
	 * 在用户保存或提交前判断操作，保存编码。
	 * 
	 * @throws Exception
	 */
	protected void checkAndSetNumberField() throws Exception {
		// 同时给界面赋值
		Component txtNumber = dataBinder.getComponetByField("number");
		if (this.codingRuleVo.isExist() && !this.codingRuleVo.isAddView()) {
			// 同时给界面赋值
			if (txtNumber instanceof KDTextField) {
				String str = ((KDTextField) txtNumber).getText();
				if (str == null || str.length() == 0) {
					editData.setNumber(codingRuleVo.getSysNumber());
					((KDTextField) txtNumber).setText(codingRuleVo
							.getSysNumber());
					// storeFields();
				}
			}
		}

		if (txtNumber instanceof KDTextField) {
			String str = ((KDTextField) txtNumber).getText();
			if (str == null || str.length() == 0) {
				txtNumber.requestFocus();
				MsgBox
						.showInfo(EASResource
								.getString("com.kingdee.eas.st.common.STResource.NULL_Number"));
				txtNumber.setEnabled(true);
				txtNumber.setEnabled(true);
				SysUtil.abort(); // 退出
			}
		}
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * 第一，勿删。
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionFirst_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * 前一，勿删。
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}
		super.actionPre_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * 后一，勿删。
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionNext_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * 最后，勿删。
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionLast_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	protected void setAuditAndUnAuditEnable() {
		// 设置审核反审核显示;
		BillBaseStatusEnum billStatus = this.editData.getBillStatus();
		// 提交，则审核可用
		if (BillBaseStatusEnum.SUBMITED.equals(billStatus)) {
			this.actionAudit.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
		} else {
			this.actionAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
		}

		// 审核，则反审可用
		if (BillBaseStatusEnum.AUDITED.equals(billStatus)) {
			this.actionUnAudit.setEnabled(true);
			this.btnUnAudit.setEnabled(true);
			this.menuItemUnAudit.setEnabled(true);
		} else {
			this.actionUnAudit.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
			this.menuItemUnAudit.setEnabled(false);
		}
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		if (editData instanceof STBillBaseInfo) {
			// copy from SCMBillEditUI by wangzhiwei 20080609
			// 关联生成的单据不允许复制 Robin 2007-4-9
			if (editData.isIsFromBOTP()) {
				MsgBox.showError(this, SCMClientUtils
						.getResource("CANT_COPY_BOTPBILL"));
				// actionCopy.setEnabled(false);
				SysUtil.abort();
			}
		}

		super.actionCopy_actionPerformed(e);

		// if(STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		setEntryEditable();

		if (STATUS_EDIT.equals(getOprtState())
				|| STATUS_ADDNEW.equals(getOprtState())) {
			btnEdit.setEnabled(false);
			menuItemEdit.setEnabled(false);
			actionEdit.setEnabled(false);
		}

		setAutoNumberWhenHasCodingRule();
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		// oldControlStatusMap.size()
		setControlEnabledByBotpMap();

		setAuditEnabled();

		setEntryEditable();

		// 如有必要，则设置编码规则
		setAutoNumberWhenHasCodingRule();
	}

	/**
	 * 设置当有编码规则存在时，设置相应的编码规则。
	 */
	public void setAutoNumberWhenHasCodingRule() {
		if (getMainBizOrgType() != null)
			setAutoNumberByOrgByPass(getMainBizOrgType().toString());
		else
			setAutoNumberByOrgByPass(null);

		setDataObject(editData);
	}

	private void setControlEnabledByBotpMap() {
		Set entries;
		entries = oldControlStatusMap.keySet();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof Component) {
				((Component) obj).setEnabled(((Boolean) oldControlStatusMap
						.get(obj)).booleanValue());
			} else if (obj instanceof IColumn) {

				((IColumn) obj).getStyleAttributes()
						.setLocked(
								((Boolean) oldControlStatusMap.get(obj))
										.booleanValue());
			}

		}
		oldControlStatusMap = new HashMap();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// 单据提交时需要判断此时单据状态（作废、审核、关闭状态的单据不能修改）
		if (editData == null
				| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// 单据作废状态不能修改
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELETE_NOT_EDIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// 单据审核状态不能修改
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_EDIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// 单据关闭状态不能修改
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_EDIT));
		}
		if (editData == null
				|| editData.getId() == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.ALTERING
						.getValue()) {
			// 单据变更中状态不能修改
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_ALTER_NOT_EDIT));
		}

		if (editData.getId() != null) {
			if (!isMutexControlOK(editData.getId().toString())) {
				return;
			}
		}

		super.actionEdit_actionPerformed(e);

		setAuditEnabled();

		// 设置是否可编辑--一般不会出现线程之间的冲突--yangyong 2008-12-09
		STCodingRuleUtils.setMainOrgUnitEditable(codingRuleVo,
				getMainOrgUnit(), editData.getBillStatus());

		// if(STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		setEntryEditable();
		if (getPrintQtyControl() != null)
			getPrintQtyControl().setEnabled(false);

		if (editData.getId() != null) {
			try {
				pubFireVOChangeListener(null);
			} catch (Throwable ex) {
				handUIException(ex);
			}
		}
		// 设置编码是否可编辑
		setAutoNumberWhenHasCodingRule();
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		// 表选模式，不允许删除分录
		// 增加非空判断，避免空指针，liming_su 20090107
		if (getDetailTable() != null
				&& getDetailTable().getSelectManager() != null
				&& getDetailTable().getSelectManager().get(0) != null) {
			if (getDetailTable().getSelectManager().get(0).getMode() == KDTSelectManager.TABLE_SELECT) {
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.SELECT_REMOVELINE));
			}
		}

		super.actionRemoveLine_actionPerformed(e);

		if (getDetailTable().getRowCount() > 0)
			getDetailTable().getSelectManager().set(
					getDetailTable().getRowCount() - 1, 0);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionMultiapprove_actionPerformed(e);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
		ICoreBase ie = getBizInterface();
		STBillBaseInfo info = ((ISTBillBase) ie).getSTBillBaseInfo(pk);
		editData.setBillStatus(info.getBillStatus());
		setDataObject(editData);
		loadFields();
		setAuditEnabled();

	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	// /**
	// * output actionAudit_actionPerformed
	// */
	// public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	// {
	// super.actionAudit_actionPerformed(e);
	//        
	// //审核条件检查
	// auditConditionCheck(true);
	//        
	// ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
	// /*审核*/
	// ICoreBase ie = getBizInterface();
	//		
	// if(ie instanceof ISTBillBase){
	// ((ISTBillBase)ie).audit(new ObjectUuidPK[]{pk});
	// }
	// editData.setBillStatus(BillBaseStatusEnum.AUDITED);
	// setDataObject(editData);
	// loadFields();
	//
	// }
	//
	// /**
	// * 描述： 审核条件检查
	// * @param bIsAudit true为审核，false为反审核
	// * @author williamwu 2006-12-27
	// */
	// protected void auditConditionCheck(boolean bIsAudit){
	// if (bIsAudit){
	// if (editData == null || editData.getId() == null
	// ||
	// editData.getBillStatus().getValue()!=BillBaseStatusEnum.SUBMITED.getValue
	// ()){
	// // 单据未提交不能审核
	// super.handUIExceptionAndAbort( new STBillException (
	// STBillException.EXC_BILL_NOT_SUBMIT)) ;
	// }
	// }
	// else{
	// if (editData == null ||
	// editData.getBillStatus().getValue()!=BillBaseStatusEnum
	// .AUDITED.getValue()) {
	// // 单据未保存、未审核不能反审核
	// super.handUIExceptionAndAbort( new STBillException (
	// STBillException.EXC_BILL_NOT_AUDIT)) ;
	// }
	//    		
	// }
	// }

	// 供子类可以另行处理是否要进行审核的单据。
	public boolean isAuditBill(STBillBaseInfo info) throws Exception {
		return true;

	}

	// 供子类处理变更审核时调用
	public void doAgreeAlter() throws Exception {

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();

		// 进行网络互斥控制
		if (!isMutexControlOK(id)) {
			return;
		}

		// 要选中的单据做审批操作吗？
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		super.actionAudit_actionPerformed(e);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());

		// 判断是否符合审核的条件
		checkAudit(pk);

		if (isAuditBill(editData)) {
			if (!multiApproveIfInWF()) {

				// 调用服务端处理审核
				ICoreBase ie = getBizInterface();
				if (ie instanceof ISTBillBase) {
					if (!editData.getBillStatus().equals(
							BillBaseStatusEnum.ALTERING)) {
						Map retMap = ((ISTBillBase) ie)
								.audit(new ObjectUuidPK[] { pk });
						getUIContext().put("ST_AUDIT_MSG_MAP", retMap);
					} else {
						doAgreeAlter();
					}

					releaseMutexControl(id); // 释放互斥锁
					setOprtState(STATUS_VIEW);
					// true ：工作流的多级审批
					// 由于某些地方审核要求时间较长，此处把睡眠时间从1000改为3000
					Thread.sleep(3000);
					this.refreshCurPage();

				} else {
					releaseMutexControl(id); // 释放互斥锁
					setOprtState(STATUS_VIEW);
				}
			}

			// 更新状态栏消息及数据列表
			if (editData.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {

				setOprtState(STATUS_VIEW);
				this.refreshCurPage(); // 审核后锁定界面 added by miller_xiao
										// 2009-01-14 15:31

				setMessageText(STQMUtils.getResource("AUDIT_SUCCESS"));
				showMessage();

				btnAudit.setEnabled(false);
				btnUnAudit.setEnabled(true);
				menuItemAudit.setEnabled(false);
				menuItemUnAudit.setEnabled(true);

				if (this.chkAuditAndPrint.isSelected()) {
					ActionEvent evt = new ActionEvent(btnPrint, 0, "Print");
					ItemAction actPrint = getActionFromActionEvent(evt);
					actPrint.actionPerformed(evt);

				}
				setEntryEditable();

				// 审核成功后应该重新设置工具栏的可用性
				setStatus();
			}
		}
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
	 * 审核时调用工作流的多级审批（条件：单据必须在工作流中）
	 * 
	 * @param e
	 * @throws BOSException
	 * @throws Exception
	 * @author:tianpan date: 2007-5-29 20:00:49
	 */
	protected boolean multiApproveIfInWF() throws BOSException, Exception {
		if (!editData.getBillStatus().equals(BillBaseStatusEnum.SUBMITED)
				&& !editData.getBillStatus()
						.equals(BillBaseStatusEnum.ALTERING)) {
			throw new STBillException(STBillException.BILLMUSTSUBMIT);
		}
		String[] idListArray = new String[1];
		idListArray[0] = this.editData.getId().toString();

		// 获取需要多级审批的ID列表
		ArrayList IDsInWF = STClientUtils.getIDsInActiveWorkFlow(idListArray);
		// 不在工作流中
		if (IDsInWF.size() == 0) {
			return false;
		} else {
			// true ：工作流的多级审批
			actionMultiapprove_actionPerformed(null);
			// 由于某些地方审核要求时间较长，此处把睡眠时间从1000改为3000
			Thread.sleep(3000);
			this.refreshCurPage();
			// SysUtil.abort();
			return true;
		}
	}

	private void checkAudit(ObjectUuidPK pk) throws Exception {

		// TODO 检查参数设置,在此判断是否需要审核的允许审核的参数控制

		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			// 下边的循环体用于判断是不是所选的单据是不是都符合反审核的条件，如果有一个不符合条件，就抛出异常-------

			STBillBaseInfo info = ((ISTBillBase) ie).getSTBillBaseInfo(pk);

			// 如果为变更中,则不需校验状态
			if (!editData.getBillStatus().equals(BillBaseStatusEnum.ALTERING)) {
				AuditUtils.checkAuditDetail(info);
			}
		}

	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();

		// 进行网络互斥控制
		if (!isMutexControlOK(id)) {
			return;
		}

		// 要选中的单据做反审批操作吗？
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsUnAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		super.actionUnAudit_actionPerformed(e);

		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());

		// 判断是否符合反审核的条件
		checkUnAudit(pk);

		// 调用服务端处理反审核
		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			((ISTBillBase) ie).unAudit(new ObjectUuidPK[] { pk });
		}

		// 释放锁定
		try {
			pubFireVOChangeListener(null);
		} catch (Throwable e1) {
			// TODO 自动生成 catch 块
			handUIException(e1);
		}

		// 更新状态栏消息及数据列表
		setMessageText(STQMUtils.getResource("UNAUDIT_SUCCESS"));
		showMessage();

		// 更新界面
		editData.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		editData.setAuditor(null);
		editData.setAuditTime(null);

		// 重新装载数据
		refreshCurPage();

		btnAudit.setEnabled(true);
		btnUnAudit.setEnabled(false);
		menuItemAudit.setEnabled(true);
		menuItemUnAudit.setEnabled(false);
		super.setOprtState(STATUS_VIEW);
		setStatus();
		this.actionAddNew.setEnabled(true);
		this.actionEdit.setEnabled(true);
	}

	private void checkUnAudit(ObjectUuidPK pk) throws Exception {

		// TODO 检查参数设置,在此判断是否需要反审核的允许反审核的参数控制

		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			// 下边的循环体用于判断是不是所选的单据是不是都符合反审核的条件，如果有一个不符合条件，就抛出异常-------

			STBillBaseInfo info = ((ISTBillBase) ie).getSTBillBaseInfo(pk);
			if (info.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
				checkUnAudit(info);
				AuditUtils.checkUnAuditDetail(info);
			}

			// 检查是否有生成下游单据了,检查所选每个id，如果有任何一个记录有下游单据，就会抛出异常--所有单据
			if (hasDestBill(pk.toString()) == true) {
				// 编码为{0}的单据已关联生成其它单据，不允许反审核
				throw new STBillException(
						STBillException.HASDESTBILL_CANNOTUNAUDIT,
						new Object[] {});
			}
		}

	}

	protected void checkUnAudit(STBillBaseInfo info) throws Exception {

	}

	/**
	 * 
	 * 描述：返回单据单头对应的数据Info,比如XXXBillInfo并赋默认值. 注意: 1.要求子类覆盖 2.可在此提供单头的默认数据
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.EditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		return null;
	}

	/**
	 * 
	 * 描述：返回单据明细分录对应的数据Info,比如XXXEntryInfo并赋默认值. 注意: 1.要求子类覆盖 2.可在此提供分录的默认数据
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#createNewDetailData(com.kingdee.bos.ctrl.kdf.table.KDTable)
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	/**
	 * 
	 * 描述：返回单据明细分录的KDTable实例. 注意: 要求子类覆盖
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * 
	 * 描述：返回业务单据实体的业务接口,若单据为XXXBill那么此接口形如:IXXXBill 注意: 要求子类覆盖.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.EditUI#getBizInterface()
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	/**
	 * 目前单据上拉生成有三种方式，业务系统可以选择采用何种方式；因此增加一个方法，用于选择上拉方式；
	 * 三种方式分别为单一单据类型单张单据，单一单据类型多张单据，多种单据类型多张单据，依次用0，1，2表示，默认返回0； public final
	 * static int pullTypeSingleToSingle = 0; public final static int
	 * pullTypeSingleToMutil = 1; public final static int pullTypeMutilToMutil =
	 * 2;
	 */
	public int getBtpCreateFromType() {
		return pullTypeSingleToMutil;
	}

	/**
	 * 描述：初始化工具栏按钮 创建时间：2006-12-2
	 */
	protected void initButtons() {

		setAuditEnabled();

		int billStatus = editData.getBillStatus().getValue();

		if (STATUS_FINDVIEW.equals(getOprtState())
				|| STATUS_VIEW.equals(getOprtState())) {
			switch (billStatus) {
			case 0: // ADD_VALUE
			case 1: // TEMPORARILYSAVED_VALUE
			case 2: // 提交状态
				btnEdit.setEnabled(true);
				menuItemEdit.setEnabled(true);
				actionEdit.setEnabled(true);
				break;
			case 4:
				btnEdit.setEnabled(false);
				menuItemEdit.setEnabled(false);
				actionEdit.setEnabled(false);
				break;
			default:
				btnEdit.setEnabled(true);
				menuItemEdit.setEnabled(true);
				actionEdit.setEnabled(true);
			}

			if (billStatus == -3) {
				btnAudit.setEnabled(false);
				menuItemAudit.setEnabled(false);
				actionAudit.setEnabled(false);

				btnAddNew.setEnabled(false);
				menuItemAddNew.setEnabled(false);
				actionAddNew.setEnabled(false);

				actionSubmit.setEnabled(false);
				btnSubmit.setEnabled(false);
				menuItemSubmit.setEnabled(false);

				actionCopy.setEnabled(false);
				btnCopy.setEnabled(false);
				menuItemCopy.setEnabled(false);

				actionEdit.setEnabled(false);
				btnEdit.setEnabled(false);
				menuItemEdit.setEnabled(false);

				actionSave.setEnabled(false);
				btnSave.setEnabled(false);
				menuItemSave.setEnabled(false);

				menuItemUnAudit.setEnabled(false);
				btnUnAudit.setEnabled(false);

				actionEdit.setEnabled(false);
				menuItemEdit.setEnabled(false);
				btnEdit.setEnabled(false);

				btnRemove.setEnabled(false);
				menuItemRemove.setEnabled(false);
				actionRemove.setEnabled(false);

			}

			return;
		}
		switch (billStatus) {
		case 0: // ADD_VALUE
		case 1: // TEMPORARILYSAVED_VALUE
			btnAudit.setEnabled(false);
			menuItemAudit.setEnabled(false);
			actionAudit.setEnabled(false);

			btnUnAudit.setEnabled(false);
			menuItemUnAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);

			btnSave.setEnabled(true);
			actionSave.setEnabled(true);
			menuItemSave.setEnabled(true);

			btnSubmit.setEnabled(true);
			menuItemSubmit.setEnabled(true);
			actionSubmit.setEnabled(true);

			// btnEdit.setEnabled(true);
			// menuItemEdit.setEnabled(true);
			// actionEdit.setEnabled(true);
			break;
		case 2:
			btnAudit.setEnabled(true);
			menuItemAudit.setEnabled(true);
			actionAudit.setEnabled(true);

			btnUnAudit.setEnabled(false);
			menuItemUnAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);

			btnSave.setEnabled(false);
			menuItemSave.setEnabled(true);
			actionSave.setEnabled(false);

			btnSubmit.setEnabled(true);
			menuItemSubmit.setEnabled(true);
			actionSubmit.setEnabled(true);

			// btnEdit.setEnabled(true);
			// menuItemEdit.setEnabled(true);
			// actionEdit.setEnabled(true);
			break;
		case 4:
			btnAudit.setEnabled(false);
			menuItemAudit.setEnabled(false);
			actionAudit.setEnabled(false);

			btnUnAudit.setEnabled(true);
			menuItemUnAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			btnSave.setEnabled(false);
			menuItemSave.setEnabled(false);
			actionSave.setEnabled(false);

			btnEdit.setEnabled(false);
			menuItemEdit.setEnabled(false);
			actionEdit.setEnabled(false);

			btnSubmit.setEnabled(false);
			menuItemSubmit.setEnabled(false);
			actionSubmit.setEnabled(false);
			break;
		case -2:
			btnAudit.setEnabled(true);
			menuItemAudit.setEnabled(true);
			actionAudit.setEnabled(true);

			actionAddNew.setEnabled(false);
			btnAddNew.setEnabled(false);
			menuItemAddNew.setEnabled(false);

			actionSubmit.setEnabled(false);
			btnSubmit.setEnabled(false);
			menuItemSubmit.setEnabled(false);

			actionCopy.setEnabled(false);
			btnCopy.setEnabled(false);
			menuItemCopy.setEnabled(false);

			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			menuItemEdit.setEnabled(false);

			actionSave.setEnabled(false);
			btnSave.setEnabled(false);
			menuItemSave.setEnabled(false);

			menuItemUnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
			break;

		default:
			actionSave.setEnabled(true);
			btnSave.setEnabled(true);
			menuItemSave.setEnabled(true);

			btnSubmit.setEnabled(true);
			menuItemSubmit.setEnabled(true);
			actionSubmit.setEnabled(true);

			btnAudit.setEnabled(false);
			menuItemAudit.setEnabled(false);
			actionAudit.setEnabled(false);

			btnUnAudit.setEnabled(false);
			menuItemAudit.setEnabled(false);
			menuItemUnAudit.setEnabled(false);

			// btnEdit.setEnabled(true);
			// menuItemEdit.setEnabled(true);
			// actionEdit.setEnabled(true);
		}

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.framework.client.EditUI#checkBeforeWindowClosing()
	 */
	public boolean checkBeforeWindowClosing() {
		// EditBy Brina 2007/01/18 控制是否保存的提示框（将状态置为保存状态）
		if (hasWorkThreadRunning()) {
			return false;
		}

		// this.savePrintSetting(this.getTableForPrintSetting());
		// storeFields()抛出异常或者editdata有改变，询问是否保存退出
		if (isModify()) {
			// editdata有改变
			int result = MsgBox.showConfirm3(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Confirm_Save_Exit"));

			if (result == KDOptionPane.YES_OPTION) {

				try {
					if (BillBaseStatusEnum.SUBMITED.equals(editData
							.getBillStatus())) { // 如果是提交状态，则调用Submit，否则调用Save
						actionSubmit.setDaemonRun(false);
						ActionEvent event = new ActionEvent(btnSubmit,
								ActionEvent.ACTION_PERFORMED, btnSubmit
										.getActionCommand());
						actionSubmit.actionPerformed(event);
						return !actionSubmit.isInvokeFailed();
					} else {
						actionSave.setDaemonRun(false);
						ActionEvent event = new ActionEvent(btnSave,
								ActionEvent.ACTION_PERFORMED, btnSave
										.getActionCommand());
						actionSave.actionPerformed(event);
						return !actionSave.isInvokeFailed();
					}
					// return true;
				} catch (Exception exc) {
					// handUIException(exc);
					return false;
				}

			} else if (result == KDOptionPane.NO_OPTION) {
				// stopTempSave();
				return true;
			} else {
				return false;
			}
		} else {
			// stopTempSave();
			return true;
		}

	}

	/**
	 * 设置行数据项为空
	 * 
	 * @author:colin 创建时间：2007_04_25
	 *               <p>
	 */
	protected IObjectValue processLineCopyItem(IObjectValue info,
			IObjectValue emptyData) {
		emptyData.putAll(info);

		if (emptyData != null && emptyData instanceof CoreBaseInfo) {
			((CoreBaseInfo) emptyData).setId(null);
		}
		return emptyData;
	}

	/**
	 * 复制新增一行(被复制数据分录行可能通过BOTP下推生成,也可能是手工录入的数据分录),新增的分录自动复制到单据体的最后一行 描述：
	 * 
	 * @author:colin 创建时间：2007_04_25
	 *               <p>
	 */
	public void actionLineCopy_actionPerformed(ActionEvent e) throws Exception {

		// 先保存，再复制
		storeFields();

		int copies = 1;

		// 调用通知单选择窗口
		IUIFactory uiFactory = null;

		try {

			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);

			Map uiContext = getUIContext();

			uiContext.put(UIContext.OWNER, this);

			IUIWindow inputUI = uiFactory.create(CopyLineNumInputUI.class
					.getName(), uiContext);
			inputUI.show();

			copies = ((CopyLineNumInputUI) inputUI.getUIObject()).inputValue;

		} catch (BOSException ex) {
			super.handUIExceptionAndAbort(ex);
		}

		// 如果copies为-1,说明选择取消
		if (copies == -1) {
			return;
		}

		if (copies < 1) {
			MsgBox.showInfo("复制的数必须是大于或等于1，不能复制！");
			return;
		}

		// 取得明细表
		KDTable kdTable = getDetailTable();

		if (STQMUtils.isNotNull(kdTable)) {
			if (kdTable.getSelectManager().size() > 0
					&& kdTable.getSelectManager().get(0).getMode() != KDTSelectManager.TABLE_SELECT) { // 表选模式
																										// ，
																										// 不允许复制分录

				int oldBottomIndex = kdTable.getRowCount() - 1;
				int oldLeftIndex = 1;
				IObjectValue[] infos = KDTableUtils.selectedInfosForTable(
						kdTable, dataBinder);
				IObjectValue info = null;

				if (STQMUtils.isNotNull(kdTable.getSelectManager())) {
					if (STQMUtils.isNotNull(kdTable.getSelectManager().get())) {
						oldLeftIndex = kdTable.getSelectManager().get()
								.getLeft();
					}
				}
				// 获取选择行号
				int[] selectRows = KDTableUtil.getSelectedRows(kdTable);

				for (int i = 0; i < copies; i++) {
					for (int rowIndex = 0; rowIndex < infos.length; rowIndex++) {

						info = infos[rowIndex];

						// info不为空时加入到KDTable
						if (info instanceof CoreBaseInfo) {

							// 对info进行个性化处理,一般在应用的xxxEditUI覆盖实现
							IObjectValue detailData = createNewDetailData(kdTable);

							detailData = processLineCopyItem(info, detailData);
							IRow row = kdTable.addRow();
							super.loadLineFields(kdTable, row, detailData);

							// 复制editor
							for (int colIndex = 0; colIndex < kdTable
									.getColumnCount() - 1; colIndex++) {
								ICellEditor editor = kdTable.getRow(
										selectRows[rowIndex]).getCell(colIndex)
										.getKDTCell().getEditor();
								row.getCell(colIndex).setEditor(editor);

								String formatString = kdTable.getRow(
										selectRows[rowIndex]).getCell(colIndex)
										.getStyleAttributes().getNumberFormat();
								if (formatString != null && formatString != "") {
									row.getCell(colIndex).getStyleAttributes()
											.setNumberFormat(formatString);
								}
							}
						}
					}

				}

				// 焦点转移到新复制的第一行
				if (oldBottomIndex + 1 < kdTable.getRowCount()) {
					// 改成编辑状态
					kdTable.getEditManager().editCellAt(oldBottomIndex + 1,
							oldLeftIndex);
				}

			} else {
				// 单据体分录为空，提示不能进行行复制新增操作
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.NULL_LINEINFO));
			}
		}

		storeFields();
		// setDataObject(editData);

	}

	/**
	 * 注册多物料选择器，
	 * 
	 * @param table
	 *            ，要注册的table
	 * @param materialColumnName
	 *            ，table中物料所在列的列名
	 * @param isClearQty
	 *            ，是否清除数量所在列
	 * @throws Exception
	 */
	protected MultiMaterialProcessor registerMultiMaterialProcessor(
			KDTable table, String materialColumnName, boolean isClearQty)
			throws Exception {
		// 主业务组织
		OrgUnitInfo mainOrgInfo = (OrgUnitInfo) getMainOrgContext().get(
				getMainBizOrgType());
		MultiMaterialProcessor mmp = MultiMaterialProcessor.getInstance(this,
				table, materialColumnName, getMainBizOrgType(), mainOrgInfo,
				isClearQty);

		mmp.setDisplayName("$name$");

		multBaseDataManager.addProcessor(mmp);

		// 生成多基础资料选择的适配器
		multBaseDataManager
				.addAfterInsertEntryListener(new MultiBaseDataAdapter() {
					public void beforeLoadEntryFields(
							MultiMaterialProcessor mmp, KDTable table,
							int rowIdx) throws Exception {
						beforeInsertMaterialEntry(table, rowIdx);
					}

					public void loadEntryFields(MultiMaterialProcessor mmp,
							KDTable table, int rowIdx) throws Exception {
						afterInsertMaterialEntry(table, rowIdx);
					}
				});
		multBaseDataManager.registerMultiBaseDataF7();

		return mmp;
	}

	/**
	 * 注册多物料选择器，
	 * 
	 * @param table
	 *            ，要注册的table
	 * @param materialColumnName
	 *            ，table中物料所在列的列名
	 * @param isClearQty
	 *            ，是否清除数量所在列
	 * @throws Exception
	 */
	// protected com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor
	// registerSTMultiMaterialProcessor(KDTable table, String
	// materialColumnName,
	// boolean isClearQty) throws Exception{
	// //主业务组织
	// OrgUnitInfo mainOrgInfo = (OrgUnitInfo) getMainOrgContext().get(
	// getMainBizOrgType());
	// com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor mmp
	// = com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor
	// .getInstance(
	// this,
	// table,
	// materialColumnName,
	// getMainBizOrgType(),
	// mainOrgInfo,
	// isClearQty);
	//		
	// mmp.setDisplayName("$name$");
	//		
	// multSTBaseDataManager.addProcessor(mmp);
	//		
	// //生成多基础资料选择的适配器
	// multSTBaseDataManager.addAfterInsertEntryListener(
	// new com.kingdee.eas.st.basedata.multiF7.MultiBaseDataAdapter() {
	//			
	// /*
	// * modify by kangyu_zou 010204
	// * MultiMaterialProcessor必须用st.basedata.multiF7包中类，import 中引用的是qm中类
	// */
	// public void beforeLoadEntryFields(
	// com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor mmp,
	// KDTable table,int rowIdx) throws Exception {
	// beforeInsertMaterialEntry(table,rowIdx);
	// }
	// public void loadEntryFields(
	// com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor mmp,
	// KDTable table,int rowIdx) throws Exception {
	// afterInsertMaterialEntry(table,rowIdx);
	// }
	//			
	// });
	// multSTBaseDataManager.registerMultiBaseDataF7();
	//		
	// return mmp;
	// }
	/**
	 * 注册多质检项目选择器，
	 * 
	 * @param table
	 *            ，要注册的table
	 * @param materialColumnName
	 *            ，table中物料所在列的列名
	 * @param isClearQty
	 *            ，是否清除数量所在列
	 * @throws Exception
	 */
	protected MultiQIItemProcessor registerMultiQIItemProcessor(KDTable table,
			String qiItemColumnName) throws Exception {
		MultiQIItemProcessor mqp = MultiQIItemProcessor.getInstance(table,
				qiItemColumnName);

		multBaseDataManager.addProcessor(mqp);

		multBaseDataManager.registerMultiBaseDataF7();

		return mqp;
	}

	/**
	 * 注册多作业面选择器，
	 * 
	 * @param table
	 *            ，要注册的table
	 * @param opSurfaceColumnName
	 *            ，table中作业面所在列的列名
	 * @throws Exception
	 */
	// protected MultiOpSurfaceProcessor registerMultiOpSurfaceProcessor(KDTable
	// table, String opSurfaceColumnName) throws Exception{
	// MultiOpSurfaceProcessor mop =
	// MultiOpSurfaceProcessor.getInstance(table,opSurfaceColumnName);
	//		
	// //因为bd_mm模块已经不归冶金项目组织维护，故在basedata中新增了一套F7基类，重新定义
	// com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager
	// multSTBaseDataManager =
	// new com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager();
	//		
	// multSTBaseDataManager.addProcessor(mop);
	// multSTBaseDataManager.registerMultiBaseDataF7();
	//		
	// //生成作业面选择的适配器
	// multSTBaseDataManager.addAfterInsertEntryListener(
	// new com.kingdee.eas.st.basedata.multiF7.MultiBaseDataAdapter() {
	//					
	// public void loadEntryFields(MultiOpSurfaceProcessor mmp, KDTable
	// table,int rowIdx) throws Exception {
	// afterInsertOpSurfaceEntry(table,rowIdx);
	// }
	// });
	//		
	// return mop;
	// }
	/**
	 * 描述：分录选择作业面后的处理，供子类重载 添加删除行后的相关处理
	 * 
	 * @author kangyu_zou 2009-10-13 修改
	 */
	protected void afterInsertOpSurfaceEntry(KDTable table, int row) {

	}

	protected void beforeInsertMaterialEntry(KDTable table, int row) {
		IObjectValue detailData = createNewDetailData(table);
		IRow iRow = table.getRow(row);
		loadLineFields(table, iRow, detailData);
		afterInsertLine(table, detailData);
	}

	protected void afterInsertMaterialEntry(KDTable table, int row) {

	}

	protected void setTableToSumField() {

		HashMap sumFields = getSumFields();

		if (sumFields == null)
			return;

		// 取出所有的KDTable及合计列信息
		Iterator sumFieldsIterator = sumFields.keySet().iterator();
		while (sumFieldsIterator.hasNext()) {
			KDTable table = (KDTable) sumFieldsIterator.next();
			String[] sumColNames = (String[]) sumFields.get(table);
			setTableToSumField(table, sumColNames);
		}

	}

	protected HashMap getSumFields() {
		return null;
	}

	protected String getAddNewPermItemName() {

		String permItemName = null;

		try {
			permItemName = PermissionUtils.getAddNewPermItemName(
					getBizInterface(), getEntityBOSType());
		} catch (Exception e) {

		}

		return permItemName;
	}

	protected OrgUnitInfo getBizOrgUnitInfo() {

		// 为了提高性能，只有当新增权限项名称为空时才取
		if (STQMUtils.isNull(addNewPermItemName)) {
			addNewPermItemName = getAddNewPermItemName();
		}

		if (STQMUtils.isNull(addNewPermItemName)) {
			return null;
		}

		OrgType orgType = getMainBizOrgType();

		// 获得授权的实体组织
		OrgUnitInfo aOrgUnitInfo = null;
		try {
			aOrgUnitInfo = OrgUnitClientUtils.getBizOrgUnitInfo(
					getBizInterface(), addNewPermItemName, orgType,
					getMainOrgContext());
		} catch (Exception e) {
			super.handUIException(e);
		}

		return aOrgUnitInfo;

	}

	protected String getUITitleByBizType() {
		return super.getUITitle();
	}

	public String getUITitle() {

		String strTitle = null;

		if (STATUS_ADDNEW.equals(getOprtState())) {
			strTitle = getUITitleByBizType() + " - " + ADDNEW;
		} else if (STATUS_EDIT.equals(getOprtState())) {
			strTitle = getUITitleByBizType() + " - " + UPDATE;
		} else if (STATUS_VIEW.equals(getOprtState())) {
			strTitle = getUITitleByBizType() + " - " + VIEW;
		} else {
			strTitle = getUITitleByBizType();
		}
		return strTitle;
	}

	private static String getSTResource(String strKey) {
		if (strKey == null || strKey.trim().length() == 0) {
			return null;
		}
		return EASResource.getString(STResource, strKey);
	}

	protected void setEntryEditable() {
		// if(editData == null
		// || (!BillBaseStatusEnum.AUDITED.equals(editData.getBillStatus()))
		// || STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			setEntryEditable(true);
		} else {
			setEntryEditable(false);
		}
	}

	private void setEntryEditable(boolean isEnabled) {
		// ocean.he 注释掉这一行，引发了很多的BUG
		// 界面按键的可用性应该在setStatus中控制不应该放在这里面来，
		// 不然控制不住
		// setAddLineStatus(isEnabled);

		KDTable[] tables = getAllEntry();

		if (tables == null) {
			return;
		}

		for (int i = 0, count = tables.length; i < count; i++) {
			tables[i].setEditable(isEnabled);
			// tables[i].setgetStyleAttributes().setLocked(isEnabled);
		}
	}

	/**
	 * 禁用掉所有KDTable上的向下箭头和回车新增行的功能 设置单元格复制时，只复制值，不复制背景色 子类可以覆盖
	 * 
	 * @author zhiwei_wang
	 * @date 2008-5-13
	 */
	protected void setKDTableFunction() {
		KDTable[] tables = getAllEntry();
		if (tables == null) {
			return;
		}
		for (int i = 0, count = tables.length; i < count; i++) {
			try {
				KDTableHelper.updateEnterWithTab(tables[i], false);
				KDTableHelper.downArrowAutoAddRow(tables[i], false, null);
				tables[i].getEditHelper().setCoypMode(KDTEditHelper.VALUE);
			} catch (Exception e) {
				// 吃掉异常，继续执行
			}
		}
	}

	protected KDTable[] getAllEntry() {

		if (getDetailTable() == null) {
			return null;
		}

		return (new KDTable[] { getDetailTable() });

	}

	public Set getHeadFieldRequiredSet() {
		return headFieldRequiredSet;
	}

	public void setHeadFieldRequiredSet(Set headFieldRequiredSet) {
		this.headFieldRequiredSet = headFieldRequiredSet;
	}

	public KDTabbedPane getMainTabbedPane() {
		return mainTabbedPane;
	}

	public void setMainTabbedPane(KDTabbedPane mainTabbedPane) {
		this.mainTabbedPane = mainTabbedPane;
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

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);

		KDLabelContainer kdlc = null;
		if (this.headFieldRequiredSet != null) {
			Iterator ite = this.headFieldRequiredSet.iterator();
			while (ite.hasNext()) {
				Object label = null;
				Object o = ite.next();
				Component input = null;
				if (o instanceof JComponent) {
					input = (Component) o;
					label = input.getParent();
					if (label instanceof KDLabelContainer) {
						kdlc = (KDLabelContainer) label;
						STException exc = STRequiredUtils
								.checkRequiredItem(new KDLabelContainer[] { kdlc });
						if (exc != null) {
							super.handUIExceptionAndAbort(exc);
						}
					}
				}
			}
		}

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

	}

	protected void setCloseVisible(boolean isVisible) {
		this.btnClose.setVisible(isVisible);
		this.btnUnClose.setVisible(isVisible);
		this.btnCloseEntry.setVisible(isVisible);
		this.btnUnCloseEntry.setVisible(isVisible);
		this.btnClose.setEnabled(isVisible);
		this.btnUnClose.setEnabled(isVisible);
		this.btnCloseEntry.setEnabled(isVisible);
		this.btnUnCloseEntry.setEnabled(isVisible);
		this.menuItemClose.setVisible(isVisible);
		this.menuItemUnClose.setVisible(isVisible);
		this.menuItemCloseEntry.setVisible(isVisible);
		this.menuItemUnCloseEntry.setVisible(isVisible);
		this.menuItemClose.setEnabled(isVisible);
		this.menuItemUnClose.setEnabled(isVisible);
		this.menuItemCloseEntry.setEnabled(isVisible);
		this.menuItemUnCloseEntry.setEnabled(isVisible);
		this.actionClose.setEnabled(isVisible);
		this.actionUnClose.setEnabled(isVisible);
		this.actionCloseEntry.setEnabled(isVisible);
		this.actionUnCloseEntry.setEnabled(isVisible);
	}

	protected void setEnableAddLine(boolean v) {
		actionAddLine.setEnabled(v);
		btnAddLine.setEnabled(v);
		menuItemAddLine.setEnabled(v);
	}

	protected void setEnableRemoveLine(boolean v) {
		actionRemoveLine.setEnabled(v);
		btnRemoveLine.setEnabled(v);
		menuItemRemoveLine.setEnabled(v);
	}

	protected void setEnableInsertLine(boolean v) {
		actionInsertLine.setEnabled(v);
		btnInsertLine.setEnabled(v);
		menuItemInsertLine.setEnabled(v);
	}

	protected void setEnableLineCopy(boolean v) {
		actionLineCopy.setEnabled(v);
		btnLineCopy.setEnabled(v);
		menuItemLineCopy.setEnabled(v);
	}

	protected void setEntryEnable(boolean v) {
		setEnableAddLine(v);
		setEnableRemoveLine(v);
		setEnableInsertLine(v);
		setEnableLineCopy(v);
	}

	protected void setVisibleAddLine(boolean v) {
		actionAddLine.setVisible(v);
		btnAddLine.setVisible(v);
		menuItemAddLine.setVisible(v);
	}

	protected void setVisibleRemoveLine(boolean v) {
		actionRemoveLine.setVisible(v);
		btnRemoveLine.setVisible(v);
		menuItemRemoveLine.setVisible(v);
	}

	protected void setVisibleInsertLine(boolean v) {
		actionInsertLine.setVisible(v);
		btnInsertLine.setVisible(v);
		menuItemInsertLine.setVisible(v);
	}

	protected void setVisibleLineCopy(boolean v) {
		actionLineCopy.setVisible(v);
		btnLineCopy.setVisible(v);
		menuItemLineCopy.setVisible(v);
	}

	protected void setEntryVisible(boolean v) {
		setVisibleAddLine(v);
		setVisibleRemoveLine(v);
		setVisibleInsertLine(v);
		setVisibleLineCopy(v);
	}

	public OrgType getMainBizOrgTypeByPass() {
		return getMainBizOrgType();
	}

	public Context getMainOrgContextByPass() {
		return this.getMainOrgContext();
	}

	// 设置光标顺序
	/**
	 * @author xiaofeng_liu
	 * 
	 */
	protected void resetFocusByPass() {
		Component[] cs = this.getComponents();
		List list = new ArrayList();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] instanceof JComponent) {
				list.add(cs[i]);
			}
		}

		Collections.sort(list, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				if ((arg0 instanceof Component) && (arg1 instanceof Component)) {
					Component comp0 = (Component) arg0;
					Component comp1 = (Component) arg1;
					Component a = Math.abs(comp0.getY() - comp1.getY()) > 3 ? (comp0
							.getY() < comp1.getY() ? comp0 : comp1)
							: null;
					if (a == null) {
						a = Math.abs(comp0.getX() - comp1.getX()) > 3 ? (comp0
								.getX() < comp1.getX() ? comp0 : comp1) : null;
					}
					if (a == null) {
						return 0;
					} else {
						return a == comp0 ? -1 : 1;
					}
				} else {

					return 0;
				}
			}
		});
		List nList = new ArrayList();
		Iterator ite = list.iterator();
		while (ite.hasNext()) {
			Object o = ite.next();
			if (o instanceof IKDTextComponent) {
				nList.add(o);
			} else if (o instanceof JCheckBox) {
				nList.add(o);
			} else if (o instanceof KDLabelContainer) {
				nList.add(((KDLabelContainer) o).getBoundEditor());
			} else if (o instanceof KDTable) {
				nList.add(o);
			} else if (o instanceof KDTabbedPane) {
				KDTabbedPane kdtp = (KDTabbedPane) o;
				Component[] tlist = kdtp.getComponents();
				for (int i = 0; i < tlist.length; i++) {
					if (tlist[i] instanceof KDPanel) {
						KDPanel p = (KDPanel) tlist[i];
						Component[] cns = p.getComponents();
						for (int j = 0; j < cns.length; j++) {
							if (cns[j] instanceof KDTable) {
								nList.add(cns[j]);
							}
						}

					}
				}

			}

		}

		Object[] objs = nList.toArray();
		Component[] comps = new Component[objs.length];
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] instanceof Component) {
				comps[i] = (Component) objs[i];
			}
		}
		this
				.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(
						comps));
		this.setFocusCycleRoot(true);
	}

	/**
	 * BOTP快捷生成下游目标单据
	 */
	protected void setBizProcessControl() throws Exception {
		// 清除旧的菜单
		workbtnBizProcess.removeAllAssistButton();
		menuBizProcess.removeAll();

		// 检查是否需要显示菜单

		// String targetBillBosTypeAndAliasString;
		// // if(this.getSCMBizDataVO() == null) {
		// IBOTMapping iBOTMapping = BOTMappingFactory.getRemoteInstance();
		// targetBillBosTypeAndAliasString = iBOTMapping
		// .getTargetBillTypeList(editData.getBOSType().toString());
		// }
		// else {
		// targetBillBosTypeAndAliasString =
		// this.getSCMBizDataVO().getTargetBillBosTypeAndAlias();
		// }
		if (targetBillBosTypeAndAliasString == null) {
			return;
		}
		String[] targetBillBosTypeAndAliasArray = com.kingdee.eas.util.StringUtil
				.split(targetBillBosTypeAndAliasString, "|");
		if (targetBillBosTypeAndAliasArray.length != 2) {
			// 没有配置并启用BOTP转换规则
			// SysUtil.abort();
			return;
		}

		String targetBillBosTypeString = targetBillBosTypeAndAliasArray[0];
		String targetBillAliasString = targetBillBosTypeAndAliasArray[1];
		String[] targetBillBosTypeArray = com.kingdee.eas.util.StringUtil
				.split(targetBillBosTypeString, ","); // 取到一组BOSType
		String[] targetBillAliasArray = com.kingdee.eas.util.StringUtil.split(
				targetBillAliasString, ",");

		// 不需要的目标单，请把targetBillBosTypeArray[i] 置成 null值即可
		processTargetBillArray(targetBillBosTypeArray, targetBillAliasArray);

		for (int i = 0, size = targetBillBosTypeArray.length; i < size; i++) {
			if (targetBillBosTypeArray[i] == null)
				continue;
			// 如果不需要添加业务处理菜单,继续
			if (!isSetBizProcessControl(targetBillBosTypeArray[i])) {
				continue;
			}

			Map param = getParamMap(targetBillAliasArray[i],
					targetBillBosTypeArray[i]);
			ActionBizProcess actionBizProcess = getActionBizProcess(param);

			KDMenuItem menuItem = new KDMenuItem();
			menuItem.setName(targetBillAliasArray[i]);
			menuItem.setAction(actionBizProcess);
			menuBizProcess.add(menuItem);
			workbtnBizProcess.addAssistMenuItem(actionBizProcess);
		}

		showBotpMenu();
	}

	// 是否要添加业务处理按钮
	protected boolean isSetBizProcessControl(String targetBillBosType) {
		return true;
	}

	/**
	 * Robin 空方法，对目标单据做处理。例如：销售出－采购入 不允许下推，必须在编辑界面隐藏掉
	 * 不需要的目标单，请把targetBillBosTypeArray[i] 置成 null值即可
	 * 
	 * @param targetBillBosTypeArray
	 * @param targetBillAliasArray
	 */
	protected void processTargetBillArray(String[] targetBillBosTypeArray,
			String[] targetBillAliasArray) {

	}

	/*
	 * 生成目标单据
	 */
	private void createToBill(Map map) throws Exception {
		String destBillBosType = (String) map.get(DESCBILL_BOSTYPE);

		int billBaseStatusValue = editData.getBillStatus().getValue();
		// 新增、保存、提交、冻结、变更中、作废状态的单据不能关联生成下游单据
		if (billBaseStatusValue == BillBaseStatusEnum.ADD_VALUE
				|| billBaseStatusValue == BillBaseStatusEnum.TEMPORARILYSAVED_VALUE
				|| billBaseStatusValue == BillBaseStatusEnum.SUBMITED_VALUE
				|| billBaseStatusValue == BillBaseStatusEnum.BLOCKED_VALUE
				|| billBaseStatusValue == BillBaseStatusEnum.ALTERING_VALUE
				|| billBaseStatusValue == BillBaseStatusEnum.DELETED_VALUE) {
			MsgBox.showInfo(editData.getBillStatus().getAlias()
					+ EASResource.getString(SCMConstant.SCMRESOURCE_PATH,
							"BASESTATUS_CANNOT_BOTP"));
			SysUtil.abort();
		}

		if (editData.getId() == null || destBillBosType == null) {
			return;
		}

		CoreBillBaseCollection inputInfo = new CoreBillBaseCollection();
		inputInfo.add(editData);

		IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
		// BOTMappingInfo botMappingInfo = botMapping.getMapping(inputInfo,
		// destBillBosType);
		// modefied by jiwei_xiao 2010-05-13
		// 发现这段代码在下面的业务逻辑中会抛出异常,固舍弃,并使用BOTMappingCollection
		//如果有两个BOTP规则A、B(按顺序)，如果A的过滤规则不符合，B的符合，则会提示不符合BOTP规则(好像不会去找第二个符合规则的BOTP)
		BOTMappingCollection botMappingCollection = botMapping
				.getMappingCollectionForSelect(inputInfo, destBillBosType,
						DefineSysEnum.BTP);
		if (botMappingCollection != null) {
			for (int i = 0; i < botMappingCollection.size(); i++) {
				BOTMappingInfo botMappingInfo = botMappingCollection.get(i);
				if (botMappingInfo != null) {
					BOTClientTools.reBuildControl(this, botMappingInfo,
							inputInfo, destBillBosType);
				}
			}
		}
		// if (BOTClientTools.hasReBuild(inputInfo, destBillBosType)) {
		// MsgBox.showInfo("根据配置，该目标单据不允许重复生成");
		// SysUtil.abort();
		// }

		if (getBillEdit() != null) {
			getBillEdit().CreateTo(editData, destBillBosType);
		}
	}

	/*
	 * 生成目标单据前校验，子类可重写
	 */
	protected boolean beforeActionBizProcess(Map map) {
		return true;
	}

	/*
	 * 返回不同的业务处理action对应有处理内部类
	 */
	protected class ActionBizProcess extends ItemAction {
		private Map param = null;

		public ActionBizProcess() {
			this(null, null);
		}

		public ActionBizProcess(IUIObject uiObject, Map map) {
			super(uiObject);

			param = map;

			this.putValue(ItemAction.SHORT_DESCRIPTION, (String) param
					.get(ItemAction.SHORT_DESCRIPTION));
			this.putValue(ItemAction.LONG_DESCRIPTION, (String) param
					.get(ItemAction.LONG_DESCRIPTION));
			this.putValue(ItemAction.NAME, (String) param.get(ItemAction.NAME));
		}

		public void actionPerformed(ActionEvent e) {
			if (!beforeActionBizProcess(param)) {
				return;
			}
			try {
				createToBill(param);
			} catch (Exception ex) {
				handUIException(ex);
			}
		}
	}

	/**
	 * @param map
	 * @return
	 */
	protected ActionBizProcess getActionBizProcess(Map map) {
		ActionBizProcess actionBizProcess = new ActionBizProcess(this, map);
		getActionManager().registerAction("actionBizProcess", actionBizProcess);

		actionBizProcess
				.addService(new com.kingdee.eas.framework.client.service.PermissionService());
		actionBizProcess
				.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
		actionBizProcess
				.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());

		return actionBizProcess;
	}

	/**
	 * @param name
	 * @param destBillBosType
	 * @return
	 */
	protected Map getParamMap(String name, String destBillBosType) {
		Map map = new HashMap();
		map.put(MENU_NAME, name);
		map.put(ItemAction.SHORT_DESCRIPTION, name);
		map.put(ItemAction.LONG_DESCRIPTION, name);
		map.put(ItemAction.NAME, name);
		map.put(DESCBILL_BOSTYPE, destBillBosType);

		return map;
	}

	/**
	 * 设置业务处理相关控件显示
	 * 
	 * @param isVisible
	 */
	protected void showBotpMenu() {
		boolean isShowBotpMenu = checkValid()
				&& (workbtnBizProcess.getAssistButtonCount() > 0);
		menuBizProcess.setVisible(isShowBotpMenu);
		workbtnBizProcess.setVisible(isShowBotpMenu);
		sp_bizProcess.setVisible(isShowBotpMenu);
	}

	/**
	 * 动态生成action前的校验，决定是否显示此功能，子类可重写
	 */
	protected boolean checkValid() {
		return true;
	}

	public void actionAuditAndPrint_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAuditAndPrint_actionPerformed(e);
		// HashMap map=new HashMap();
		// map.put("ui", this.getClass().getName().toString());
		// map.put("configType", "isAuditPrint");
		// if (this.chkAuditAndPrint.isSelected())
		// map.put("configValue", "1");
		// else
		// map.put("configValue", "0");
		// ISTBillConfig iStBillConfig=STBillConfigFactory.getRemoteInstance();
		// iStBillConfig.setConfigValue(map);

	}

	protected void doFieldPermission() throws Exception {
		// super.doFieldPermission();
	}

	protected KDBizPromptBox getMainBizOrg() {
		return getMainOrgUnit();
	}

	/**
	 * 主业务组织改变后，业务可以进行的处理。
	 * 
	 */
	protected void afterMainOrgChanged(String oldOrgId, String newOrgId) {
		if (!isLoading) {
			boolean isDiffrent = STUtils.isDiffrent(oldOrgId, newOrgId);
			try {
				if (newOrgId != null) {
					if (isDiffrent) {

						// 只有新增和编辑才处理编号---切换主业务组织之后，需要清空
						if (STATUS_ADDNEW.equals(getOprtState())
								|| STATUS_EDIT.equals(getOprtState())) {
							editData.setNumber(null);
							Component txtNumber = dataBinder
									.getComponetByField("number");
							// 单据编号是根据组织设的，修改组织要清空编码
							if (txtNumber instanceof KDTextField) {
								((KDTextField) txtNumber).setText(null);
							}
							setAutoNumberWhenHasCodingRule();
						}

						// 校验字段权限
						super.doFieldPermission();
					}
				}
			} catch (Exception e) {
				this.handleException(e);
			}
		}
	}

	protected void loadData() throws Exception {
		super.loadData();
		if (editData != null
				&& !StringUtils.isEmpty(editData.getBizOrgPropertyName())
				&& editData.get(editData.getBizOrgPropertyName()) instanceof OrgUnitInfo) {
			orgInfo = (OrgUnitInfo) editData.get(editData
					.getBizOrgPropertyName());
		}

	}

	protected void initOldData(IObjectValue dataObject) {
		super.initOldData(dataObject);

		if (precisionManager != null) {
			precisionManager.dealPrecision();
		}
	}

	/**
	 * 网络互斥是否通过。yangyong 080627
	 * 
	 * @param id
	 * @return
	 */
	protected boolean isMutexControlOK(String id) {
		if (id == null)
			return false;

		try {
			pubFireVOChangeListener(id);
			return true;
		} catch (Throwable ex) {
			ex.printStackTrace();
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Error_ObjectUpdateLock_Request"));
			return false;
		}
	}

	/**
	 * 释放控制锁。yangyong 080627
	 * 
	 * @param id
	 */
	protected void releaseMutexControl(String id) {
		try {
			this.setOprtState("RELEASEALL");
			pubFireVOChangeListener(id);
		} catch (Throwable ex) {
			handUIException(ex);
		}
	}

	/**
	 * 检查是否有生成下游单据了
	 */
	protected boolean hasDestBill(String srcId) throws BOSException {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();

		// 单据ID过滤
		filterInfo.getFilterItems().add(
				new FilterItemInfo("srcObjectID", srcId, CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("destEntityID", "DD4053D5",
						CompareType.NOTEQUALS));
		viewInfo.setFilter(filterInfo);

		BOTRelationCollection collection = BOTRelationFactory
				.getRemoteInstance().getCollection(viewInfo);
		if (collection != null && collection.size() > 0) {
			// 有下游单据
			return true;
		}

		// 没有下游单据
		return false;
	}

	public boolean isModify() {
		if (STATUS_FINDVIEW.equals(getOprtState())
				|| STATUS_VIEW.equals(getOprtState())) {
			return false;
		}

		if (BillBaseStatusEnum.AUDITED.equals(editData.getBillStatus())) {
			return false;
		}
		return super.isModify();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("CU"));
		return sic;
	}

	/**
	 * 描述：返回单据类型
	 * 
	 * @return
	 * @author:paul 创建时间：2006-9-25
	 *              <p>
	 */
	protected String getBillTypeId() {
		STBillBaseInfo billInfo = (STBillBaseInfo) this.getEditData();
		if (billInfo != null && billInfo.getBillType() != null) {
			return billInfo.getBillType().getId().toString();
		} else {
			return null;
		}
	}

	/**
	 * 描述：单据上拉HMD模式的源单时选择所需的序时簿
	 * 
	 * @return
	 * @author fangchao_liu
	 * @date 2008-5-24
	 * @version <p>
	 */
	protected String getSCMHMDListUI() {
		return null;
	}

	public boolean getIsEditable() {
		return isEditable;
	}

	/**
	 * 描述：获默认的取库存组织
	 */
	protected OrgUnitInfo getDefaultStorageOU(String permissionNumber) {
		OrgUnitInfo currentBizOrg = (OrgUnitInfo) SysContext.getSysContext()
				.getCurrentCtrlUnit();
		StorageOrgUnitInfo orgUnitInfo = SysContext.getSysContext()
				.getCurrentStorageUnit();
		if (orgUnitInfo.isIsBizUnit()) {
			return orgUnitInfo;
		}
		OrgUnitInfo[] orgUnit = null;
		try {
			orgUnit = OrgUnitClientUtils.getBizOrgUnitInfo(null,
					getMainBizOrgType(), permissionNumber);
		} catch (Exception e) {
			super.handUIException(e);
		}

		for (int i = 0; i < orgUnit.length; i++) {
			if (orgUnit[i] != null && orgUnit[i].getCU() != null
					&& orgUnit[i].getCU().getId() != null) {
				if (orgUnit[i].getCU().getId().toString().equals(
						currentBizOrg.getId().toString())) {
					return orgUnit[i];
				}
			}
		}

		return null;
	}

	/**
	 * 新增一行的代码
	 */
	protected void addDefaultLine(KDTable table) {
		if (table == null || (getOprtState() != STATUS_ADDNEW)
				|| (table.getRowCount() != 0)) {
			return;
		}

		IObjectValue detailData = createNewDetailData(table);

		if (detailData != null) {
			IRow row = table.addRow();
			getUILifeCycleHandler().fireOnAddNewLine(table, detailData);
			loadLineFields(table, row, detailData);
		}
	}

	public void setPrecisionManager(PrecisionManager pm) {
		precisionManager = pm;
	}

	public PrecisionManager getPrecisionManager() {
		return precisionManager;
	}

	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		System.out.println("");
	}

	/** 煤炭专项改造 **/
	/**
	 * @author wanglh 按物料查询即时库存
	 *         在各种业务和出库事务时，包括录入采购订单、销售订单、生产订单、销售出库单等，提供一个物料的即时库存查询的功能
	 *         ，是用户可以查询选中单据分录行中的某条物料的即时库存
	 */
	public void actionQueryByMaterial_actionPerformed(ActionEvent e)
			throws Exception {
		HashMap hm = getQueryCondition();
		if (hm == null) {
			// MsgBox.showInfo(this, "子类未重载方法getQueryCondition()，或者这个方法返回值为空");
			return;
		}
		/** ************************** */
		/* 以下处理逻辑较难理解，先写清楚 */
		// 1、如果传入hm中有有物料，则认为是按物料查询即时库存
		// 2、如果传入hm中没有物料，有库存组织,则认为是按库存组织查询即时库存
		/* 为了性能问题修改：如果没有传入物料，则认为是查询所有即时库存 王林海 2006.02.10 */
		// 3、如果传入hm中没有物料，也没有库存组织，则认为是查询所有即时库存
		/** ************************** */

		// 物料
		MaterialInfo aMaterialInfo = (MaterialInfo) hm
				.get(SCMConstant.QUERY_MATERIAL);
		// 库存组织
		StorageOrgUnitInfo aStorageOrgUnitInfo = (StorageOrgUnitInfo) hm
				.get(SCMConstant.QUERY_STORAGEORGUNIT);

		// Boolean tablfocus = Boolean.valueOf(true);
		// if (hm.get(SCMConstant.QUERY_TABLEHASFOCUS) != null) {
		// tablfocus = (Boolean) hm.get(SCMConstant.QUERY_TABLEHASFOCUS);
		// }
		// if (!tablfocus.booleanValue()) {//未选中表格，和选中行物料为空处理一样
		// aMaterialInfo = null;
		// }

		if (aMaterialInfo == null) {
			/* 为了性能问题修改：如果没有传入物料，则认为是查询所有即时库存 王林海 2006.02.10 */
			// if (aStorageOrgUnitInfo != null) {
			// queryResultByStorageOrgUnit(hm);
			// } else {
			queryResultByInventory(hm);
			// }
		} else {
			queryResultByMaterial(hm);
		}

	}

	/**
	 * 查询所有即时库存
	 * 
	 * @param hm
	 * @throws UIException
	 * @throws BOSException
	 * @throws Exception
	 * @throws SQLException
	 */
	private void queryResultByInventory(HashMap hm) throws Exception {
		// 传入以上参数，调查询UI.....
		UIContext uiContext = new UIContext(this);
		uiContext.put(SCMConstant.QUERY_STORAGEORGUNIT, hm
				.get(SCMConstant.QUERY_STORAGEORGUNIT));
		// prepareUIContext(uiContext, e);
		// if (inventoryWindow == null) {
		inventoryWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create("com.kingdee.eas.scm.im.inv.client.InventoryListUI",
						uiContext, null, OprtState.EDIT);
		// }
		InventoryListUI aInventoryListUI = (InventoryListUI) inventoryWindow
				.getUIObject();
		// aInventoryListUI.setHmQueryCon(hm);
		// aInventoryListUI.executQuery();
		inventoryWindow.show();
	}

	/**
	 * 按物料查询即时库存
	 * 
	 * @param hm
	 * @throws UIException
	 * @throws BOSException
	 * @throws Exception
	 * @throws SQLException
	 */
	private void queryResultByMaterial(HashMap hm) throws UIException,
			BOSException, Exception, SQLException {
		// 传入以上参数，调查询UI.....
		UIContext uiContext = new UIContext(this);
		// uiContext.put(UIContext.ID, null);
		// prepareUIContext(uiContext, e);
		if (materialWindow == null) {
			materialWindow = UIFactory
					.createUIFactory(UIFactoryName.MODEL)
					.create(
							"com.kingdee.eas.scm.common.client.MaterialQueryListUI",
							uiContext, null, OprtState.EDIT);
		}
		MaterialQueryListUI aMaterialQueryListUI = (MaterialQueryListUI) materialWindow
				.getUIObject();
		aMaterialQueryListUI.setHmQueryCon(hm);
		aMaterialQueryListUI.executQuery();
		materialWindow.show();

		HashMap hmResult = aMaterialQueryListUI.getHmResult();
		Object needSet = hmResult.get("needSet"); // 只有需要赋值返回时才需要设置值，关闭窗口不需要设置
													// neujyj 2008-7-31
		if (needSet != null) {
			setLotAndAssistProperty(hmResult);
		}

	}

	/**
	 * 根据HashMap中的内容，给物料的批次和辅助属性赋值
	 * 
	 * @author 罗斌 需要子类重载实现
	 * @param hmResult
	 */
	protected void setLotAndAssistProperty(HashMap hmResult) {
	}

	/****/

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		invokePrintFunction(e, false);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		invokePrintFunction(e, true);
	}

	/**
	 * @param e
	 * @param noPreview
	 *            非预览
	 * @throws Exception
	 */
	protected void invokePrintFunction(ActionEvent e, boolean noPreview)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getPrintQueryPK() == null
				|| getPrintPathName() == null) {
			return;
		}
		DefaultNoteDataProvider data = new DefaultNoteDataProvider(idList,
				getPrintQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();

		if (noPreview) {
			appHlp.print(getPrintPathName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		} else {
			appHlp.printPreview(getPrintPathName(), data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
		}
	}

	/**
	 * 获取套打查询Query 子类必须重写
	 * 
	 * @return
	 */
	protected IMetaDataPK getPrintQueryPK() {
		return null;
	}

	/**
	 * 获取套打模板路径名称 子类必须重写
	 * 
	 * @return
	 */
	protected String getPrintPathName() {
		return null;
	}

	// ---------------------------------------------2016.04.13
	// _moerhao_流程反写功能_start---------------------------------------------
	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		super.doBeforeSubmit(e);
		new BizFlowClientHelper(this, null, getBillEntryName(),
				getAlterBillOldEntryIDName());
	}

	protected String getBillEntryName() {
		return "entries";
	}

	protected String getAlterBillOldEntryIDName() {
		return "";
	}
	//---------------------------------------------2016.04.13_moerhao_流程反写功能_end
	// ---------------------------------------------

}