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
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -6010437587702488380L;

	protected static final String MENU_NAME = "MENU_NAME";

	protected static final String MENU_NUMBER = "MENU_NUMBER";

	protected static final String DESCBILL_BOSTYPE = "DESCBILL_BOSTYPE";

	protected static final String BOTPID = "BOTPID";

	private boolean isEditable = false; // �����Ƿ���Ա༭

	protected PrecisionManager precisionManager = null;

	private String targetBillBosTypeAndAliasString;
	// private static final Logger logger =
	// CoreUIObject.getLogger(STBillBaseEditUI.class);

	/**
	 * UI����ģ��.
	 */
	protected IUIDirector uiDirector = null;

	/**
	 * Ϊ�˴����ҳǩʱ��ǰһ����һ�Ȳ������Զ���ҳǩ�лص�һ�� added by miller_xiao 2008-01-12 21:13
	 */
	protected KDTabbedPane mainTabbedPane = null;

	/**
	 * ���������ѡ�������
	 */
	protected MultiBaseDataManager multBaseDataManager = new MultiBaseDataManager();

	// protected com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager
	// multSTBaseDataManager = new com.kingdee.eas.st.basedata.
	// multiF7.MultiBaseDataManager();

	protected String addNewPermItemName = null;

	private static final String STResource = "com.kingdee.eas.st.common.STResource";

	// ���ô������ʹ��
	private String ADDNEW = null;
	private String UPDATE = null;
	private String VIEW = null;

	private boolean isInitBotpReadOnly = false;// �Ƿ��Ѿ����ù�BOTP���������Ŀؼ���״̬��
	private HashMap oldControlStatusMap = new HashMap();// ����������BOTP״̬֮ǰ�Ŀؼ���״̬��

	protected OrgUnitInfo orgInfo = null;

	/**
	 * ��ͷ������ؼ����ϣ�������Щ�ؼ��ǿɱ����ģ���˾�����Ҫ��listener���趨xx.setRequired(true)��
	 * ��Ҫ��beforeStorefields���趨��顣
	 * ��������ֱ��ڲ�ͬ�ĵط���������©�����������ôһ��Set����super.beforeStorefields��ִ�м�顣
	 * ���ڵ�ͷ�ռ����ͨ��parent�ҵ�lable�������ҵ������ı���������ʾ����kdTable�е�cell����,������ʱֻ֧�ֵ�ͷ�ؼ�.
	 * 
	 * @author xiaofeng_liu
	 */
	protected Set headFieldRequiredSet = new HashSet();

	// ��Ҫ������ҵ����֯��F7����, ��D��������Ϻ���ί�й�ϵ����֯, ���ฺ����initNeedMainOrgF7s�г�ʼ��
	private KDBizPromptBox[] prmtNeedOrgF7s = null;

	/** ��ѯ��ʱ��洰�� */
	protected IUIWindow inventoryWindow = null;

	/** ������򻺴� */
	protected STBillBaseCodingRuleVo codingRuleVo = new STBillBaseCodingRuleVo();

	/** new afterChg */
	protected DataChangeListener mainOrgChangeListener = null;

	/** �Ƿ��������룬���Ƿ���������onLoad������ **/
	protected boolean isLoading = false;

	private IUIWindow materialWindow = null;// ��ѯ��ʱ���

	/**
	 * ����������
	 * 
	 * ����������Ϊcom.kingdee.eas.st.common.listenerutils.base.BaseDataListener����
	 */
	protected ListenerManager listenerManager = new ListenerManager();

	/**
	 * output class constructor
	 */
	public STBillBaseEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// ȥ��license���飬��ɾ��

		isLoading = true;

		// ������֯��Ԫ //����������Ѿ�ȡ��,�Ͳ���ȡ��,������� xmcy 2007-09-30
		if (orgInfo == null)
			orgInfo = getBizOrgUnitInfo();

		if (orgInfo != null)
			initUIMainOrgContext(orgInfo.getId().toString());

		super.onLoad();
		menuItemCopyFrom.setVisible(false);

		btnCopyFrom.setVisible(false);

		// ���и�����������ͼ��
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

		// ���ô������ʹ��
		ADDNEW = getSTResource("ADDNEW");
		UPDATE = getSTResource("UPDATE");
		VIEW = getSTResource("VIEW");

		setAuditEnabled();
		setCurrentStorageMenu(); // �����Ƿ���ʾ��ʱ����ѯ�˵�

		// if(STATUS_ADDNEW.equals(getOprtState()) ||
		// STATUS_EDIT.equals(getOprtState())) {
		// setEntryEditable(true);
		// } else {
		// setEntryEditable(false);
		// }
		setEntryEditable();
		// ���õ�����KDTable�ϵ����¼�ͷ�ͻس������еĹ���,���õ�Ԫ����ʱ��ֻ����ֵ�������Ʊ���ɫ
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
		setSTBillParam(param); // ���ø���ͨ�ò���
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

		// �������Ҷ��������
		KDTable[] tables = getAllEntry();
		if (tables != null) {
			for (int i = 0; i < tables.length; i++) {
				// FIXME KDTableUtils.setTableAlign(tables[i],
				// dataBinder);//������ʱ���Σ���Ѱ���������
			}
		}

		// ����Ĭ��ֵ
		if (getOprtState() == STATUS_ADDNEW) {
			setDefaultValue();
		}

	}

	protected void setDefaultValue() {
		// ����Ĭ��ֵ
	}

	protected void setCopyLineEnabled(boolean isEnabled) {
		/**
		 * �޸ļ�¼ 1 �޸����� �����ε������ĸ��Ʒ�¼��ť�����Լ��� modified by miller_xiao 2008-12-29
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
	 * �����Ҫ��ʾ��ʱ����ѯ�˵������������ҷ���true��
	 * 
	 * @return
	 */
	protected boolean isCurrentStorageShow() {
		return false;
	}

	/**
	 * ִ�м�ʱ��ϸ�����ѯ��
	 */
	public void actionCurrentStorage_actionPerformed(ActionEvent e)
			throws Exception {
		/**
		 * 1.����û��Ƿ���Ȩ�޲��� <br>
		 * 2.����Ƿ�ֻ��һ������(����ǿ����֯) <br>
		 */
		HashMap hm = getQueryCondition();
		// if(hm == null || hm.size() == 0){
		// //��ʾ�û�����ѡ������֯
		// MsgBox.showError("��ѯ��ʱ��棬����ѡ���Ӧ�Ŀ����֯��");
		// return;
		// }
		//    	
		// // �����֯
		// StorageOrgUnitInfo aStorageOrgUnitInfo = (StorageOrgUnitInfo) hm
		// .get(STCurrentStorageUtils.QUERY_STORAGEORGUNIT);
		// if(aStorageOrgUnitInfo == null){
		// //��ʾ�û�����ѡ������֯
		// MsgBox.showError("��ѯ��ʱ��棬����ѡ���Ӧ�Ŀ����֯��");
		// return;
		// }

		queryProduceDetailByCondition(hm);
	}

	/**
	 * ���ݻ���ҳǩ�ϵ������õ���������Map�� Ҫʵ�ּ�ʱ����ѯ�ĵ���һ��Ҫʵ�ִ˷�����
	 * 
	 * @return
	 * @throws Exception
	 */
	protected HashMap getQueryCondition() throws Exception {
		return null;
	}

	/**
	 * ִ�в�ѯ��
	 * 
	 * @param hm
	 * @throws Exception
	 */
	protected void queryProduceDetailByCondition(HashMap hm) throws Exception {
		// �����������ҵ��
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
			// �������������Ҫ��֤����ݴ��Properties
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
	 * ����oldData��ֵ����value��Ŀ�����ڹرյ���ʱ����������ʾ����ĶԻ��� /*
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
	 * ��������ʼ����Ҫ������ҵ����֯F7����, �����踲��
	 * 
	 * @author:paul ����ʱ�䣺2006-8-15
	 *              <p>
	 */
	protected void initNeedMainOrgF7s() {

	}

	/**
	 * ������������Ҫ��ҵ����֯����֯F7�б�
	 * 
	 * @param f7s
	 *            , cols
	 * @return
	 * @author:paul ����ʱ�䣺2006-8-18
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

		// ����״̬
		if (STATUS_ADDNEW.equalsIgnoreCase(getOprtState())) {
			actionEdit.setEnabled(false);
			actionSubmit.setEnabled(true);
			actionMultiapprove.setEnabled(false);

		} else if (STATUS_EDIT.equals(getOprtState())) {
			actionEdit.setEnabled(false);
			actionMultiapprove.setEnabled(true);

			// ͨ����һ��һ�鿴ʱҪ����actionSubmit��actionSave��״̬,�������������
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
			actionSave.setEnabled(false); // �쿴״̬,���ñ�����ύ��ť
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
	 * ��number��ֵ����caller����Ӧ������.���า��
	 * 
	 * д�����£� super.prepareNumber(caller,number); txtXXXX.setText(number);
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		caller.setString("number", number);
	}

	/**
	 * �ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������.
	 * 
	 * д�����£� super.setNumberTextEnabled(); txtXXXX.setEnable(true);
	 */
	protected void setNumberTextEnabled() {

	}

	// ���ձ���
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
				// ������������̨����,����RPC���� xmcy 2007-09-29
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
	 * ��ȡ�������
	 * 
	 * @param orgType
	 * @return
	 */
	public STBillBaseCodingRuleVo getSTCodingRuleVoByOrg(String orgType) {
		STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo = new STBillBaseCodingRuleVo();
		try {
			// ������������̨����,����RPC���� xmcy 2007-09-29
			if (editData.getCU() == null) {
				// editData.setCU(getMainOrgInfo().getCU());
				editData.setCU(SysContext.getSysContext().getCurrentOrgUnit()
						.getCU());
				OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit(
						getMainType());
				if (org != null && org.getCU() != null) {
					editData.setCU(org.getCU()); // �����пղ�����by miller_xiao
													// 2009-01-20 23:33
				}
			}

			aSTBillBaseCodingRuleVo.setCoreBillInfo(editData);
			aSTBillBaseCodingRuleVo.setOrgType(orgType);

			// ����ѡ�����֯ȡ�������
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

			// ����������֯Ϊ�գ���ȡ��½��֯
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
	 * ���ø��ݱ������������ı���.
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
			// ����֯���õ��ݱ������
			String sysNumber = aSTBillBaseCodingRuleVo.getSysNumber();
			codingRuleVo = aSTBillBaseCodingRuleVo; // ÿ��ȡ��֮��ȡ��ǰ���µ�����

			if ((editData.getNumber() == null || editData.getNumber().trim()
					.length() == 0)
					&& sysNumber != null && sysNumber.trim().length() > 0) {
				editData.setNumber(sysNumber);
				// ͬʱ�����渳ֵ
				Component txtNumber = dataBinder.getComponetByField("number");
				if (txtNumber instanceof KDTextField) {
					if (codingRuleVo.isExist() && !codingRuleVo.isAddView()) {
						// ��������ʾ
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

		// ��������ı��������״̬
		// ����editData����״̬���ж��Լ�Edit����״̬���ж�
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
		} else {// ������״̬�����޸ı������
			Component txtNumber = dataBinder.getComponetByField("number");
			if (STUtils.isNotNull(txtNumber)) {
				txtNumber.setEnabled(false);
			}
		}

		// ������ҵ����֯�Ƿ�ɱ༭
		STCodingRuleUtils.setMainOrgUnitEditable(aSTBillBaseCodingRuleVo,
				getMainOrgUnit(), editData.getBillStatus());

		// ��������ڱ������������¼�����
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
	 * ���ò�����
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
		// �����Ƿ��ύ����ӡ��״̬��
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

		// ���Ѵ�ӡ�����Ŀؼ���
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
			// 54���� ���˻� 2009-07-20
			// �޸Ĺ�����������������ӡ���ߴ�ӡԤ��ʱ���༭������Ѵ�ӡ������ʾΪ0�Ĵ���
			String orgType = this.getMainBizOrgType().toString();
			setSTBillBaseCodingRuleVoByOrgType(aSTBillBaseCodingRuleVo, orgType);
		}
		HashMap map = new HashMap();
		map.put("codeRuleVo", aSTBillBaseCodingRuleVo);
		map.put("ui", this.getClass().getName().toString());
		// ����Ϊ�����ȡ���ӿڣ���Ϊ������������е��ݶ�����loadfileds���в����ģ��ʷ��ڴ�xmcy
		// aSTBillBaseCodingRuleVo = ((ISTBillBase) ie)
		// .getCodeRuleBizVo(aSTBillBaseCodingRuleVo);

		// �����������쳣����Ϊ��ǰ��֯�����ڱ�����֯ʱ������������в����������׳��쳣�ģ�ֻ��ӡ����xmcy

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
		// �����Ƿ��ύ����ӡ��״̬��
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

		// ���Ѵ�ӡ�����Ŀؼ���
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

	// 54���� ���˻�
	// �޸Ĺ�����������������ӡ���ߴ�ӡԤ��ʱ���༭������Ѵ�ӡ������ʾΪ0�Ĵ���
	private void setSTBillBaseCodingRuleVoByOrgType(
			STBillBaseCodingRuleVo aSTBillBaseCodingRuleVo, String orgType) {
		aSTBillBaseCodingRuleVo.setOrgType(orgType);
		if (editData.getCU() == null) {
			// editData.setCU(getMainOrgInfo().getCU());
			editData.setCU(SysContext.getSysContext().getCurrentOrgUnit()
					.getCU());
		}
		aSTBillBaseCodingRuleVo.setCoreBillInfo(editData);
		// ����ѡ�����֯ȡ�������
		OrgUnitInfo orgUnitInfo = null;
		KDBizPromptBox prmtOrg = getMainOrgUnit();
		if (STUtils.isNotNull(prmtOrg)) {
			Object o = prmtOrg.getValue();
			if (o instanceof OrgUnitInfo) {
				orgUnitInfo = (OrgUnitInfo) o;
			}
		}

		if (!STUtils.isNull(orgUnitInfo)) {
			// �������ҵ����֯�ؼ�,����û������,�˳�
			// return;

			// ����������֯Ϊ�գ���ȡ��½��֯
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
	 * ��Ϊ�ȽϹ�������⣬�����Ұ�ԭ������δ��뿽����һ���·����У�Ȼ�����ڱ������е����·�����
	 * �����������ǣ�SamplebillEdit��QiDelegateBillEditԪ���ݷ������Abstract
	 * ***EditUI.java�����л�����һ��ͬ������setAutoNumberByOrg��
	 * ������ͬ���ڵ�loadfields�е����������������Զ����ɵ�ͬ�������᳹�׸�����������������������һЩbug��
	 * ����ҵİ취��ֱ��copy��������Ĵ��뵽�����࣬����������δ��뻹���ȶ����޸ĺ�û��ͬ��������bug��ʵ�����Ѿ������˲��ٴ��ˡ�
	 * ͬʱ��������������м��Ǳ߻�Ϊ�ҵ�ԭ�򣬺��ѱ�֤�Ժ�ĳ��EditUI�������ͬ�������⡣
	 * ����һ������ڱ������½�һ��������Ȼ��ѱ����������߼�Ǩ�Ƶ��·����У��������ھ���������Ϳ��Ե�������߼��ˡ�
	 * ͬʱ��SampleBillEditUI
	 * .java��QIDelegateBillEditUI.java�и���setAutoNumberByOrgΪһ���շ����������Լ����Ǵ���ν��rpc��
	 * �·���������setAutoNumberByOrgByPass��
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

		// �����Զ�����
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
	 * ��������UI����������XXXUI.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-17
	 *              <p>
	 */
	protected final void decorateUI() throws Exception {
		if (STQMUtils.isNull(uiDirector)) {
			if (this instanceof IDirectable) {
				// ���ϣ��ʹ��UI����ģ����������ʵ����AbstractUIDirector
				((IDirectable) this).setupDirector();
			}
		}

		if (STQMUtils.isNotNull(uiDirector)) {
			// ����UI�󶨵�����.
			uiDirector.trimData();
			// ��װUI Element
			uiDirector.setupUIElement();
			// UI����װ��
			uiDirector.loadData();
			// ����UI Element
			uiDirector.decorateUIElement();
		}
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see
	 * com.kingdee.eas.framework.client.EditUI#setFieldsNull(com.kingdee.bos
	 * .dao.AbstractObjectValue)
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		// TODO �Զ����ɷ������
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

		// ���÷�¼����Դ������ϢΪ��
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
		// ��������BOTP����״̬����������LOADFILEDS֮ǰ������loadfileds֮ǰ��EDITUI�еĹ�ϵ��ûȡ������
		// ��������Ϊ�˴���ֻ����ȡһ�Ρ�

		if (isInitBotpReadOnly)
			return;

		Object id = null;
		if (editData != null)
			id = this.editData.getId();
		else
			id = getUIContext().get(UIContext.ID);

		try {
			if (id != null) {
				// Ϊ�޸ļ�ﵥ���ε������ʱ�����жϵ����� bugId��PBG020024 added by yangyong
				// 20081028
				// if (getMakeRelations() == null)
				// {
				// IBTPManager iBTPManager =
				// BTPManagerFactory.getRemoteInstance();
				// this.setMakeRelations(iBTPManager.getRelationCollection(id.
				// toString()));
				// }
				// edit end ȥ���пյ��жϣ�ÿ�ζ�ȡ���µĹ�ϵ
			}
			if (getMakeRelations() != null) {
				BOTRelationCollection aBOTRelationCollection = getMakeRelations();
				// ������������ֻ��Ӧ��һ�����򣬹���߾Ͳ�ѭ������ ����ֱ��ȡ��һ���������ȡ�������Դ���
				if (aBOTRelationCollection.size() > 0) {
					BOTRelationInfo aBOTRelationInfo = aBOTRelationCollection
							.get(0);
					IBOTMapping iBOTMapping = BOTMappingFactory
							.getRemoteInstance();

					// Ϊ�޸ļ�ﵥ���ε������ʱ�����жϵ����� bugId��PBG020024 added by yangyong
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
		// ���²������ʱһֱ����
		actionTraceUp.setEnabled(true);
		btnTraceUp.setEnabled(true);
		menuItemTraceUp.setEnabled(true);
		actionTraceDown.setEnabled(true);
		btnTraceDown.setEnabled(true);
		menuItemTraceDown.setEnabled(true);

		if (STATUS_ADDNEW.equals(getOprtState())) {// ����
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);
			// ���²�����ʱ�����ã�
			actionTraceUp.setEnabled(false);
			actionTraceDown.setEnabled(false);
			menuItemTraceUp.setEnabled(false);
			menuItemTraceDown.setEnabled(false);

			// �д���״̬
			if (isFromBotp()) {// �������ɵĵ���Ҫ���� ������������
				setAddLineStatus(false);
			} else {
				// setAddLineStatus(true);
			}
			setRemoveLineStatus(true);

			// ��������״̬
			setCreateFromStatus(true);

			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			menuItemCopy.setEnabled(false);
			menuItemRemove.setEnabled(false);

			// ��ӡ��ť��Ч
			btnPrint.setEnabled(false);
			btnPrintPreview.setEnabled(false);
			menuItemPrint.setEnabled(false);
			menuItemPrintPreview.setEnabled(false);

		} else if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState())) {// �鿴

			// �д���״̬___���� �����������롢ɾ��
			setAddLineStatus(false);
			setRemoveLineStatus(false);

			// ���²�����ʱ�����ã�
			actionTraceDown.setEnabled(true);

			// ��������״̬_����
			setCreateFromStatus(false);

			if (STATUS_FINDVIEW.equals(getOprtState())) {// ��������)
				btnCopy.setEnabled(false);
				btnRemove.setEnabled(false);
				menuItemCopy.setEnabled(false);
				menuItemRemove.setEnabled(false);
				// ��ӡ��ť��Ч
				btnPrint.setEnabled(false);
				btnPrintPreview.setEnabled(false);
				menuItemPrint.setEnabled(false);
				menuItemPrintPreview.setEnabled(false);

			} else {
				btnCopy.setEnabled(true);
				btnRemove.setEnabled(true);
				menuItemCopy.setEnabled(true);
				menuItemRemove.setEnabled(true);
				// ��ӡ��ť��Ч
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

		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�
			actionEdit.setEnabled(false);
			actionSave.setEnabled(true);
			actionSubmit.setEnabled(true);

			// ���²�����ʱ�����ã�
			actionTraceDown.setEnabled(true);

			// �д���״̬
			if (isFromBotp()) {// �������ɵĵ���Ҫ���� ������������
				setAddLineStatus(false);
			}
			// else {
			// setAddLineStatus(true);
			// }
			setRemoveLineStatus(true);
			// ��������״̬_����
			setCreateFromStatus(true);
			btnCopy.setEnabled(true);
			btnRemove.setEnabled(true);
			menuItemCopy.setEnabled(true);
			menuItemRemove.setEnabled(true);
			// ��ӡ��ť��Ч
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
	 * ����"�д�����"�˵�������״̬
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
	// * ����"�д���ɾ��"�˵�������״̬
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
	 * ����"�д���ɾ��"�˵�������״̬
	 * 
	 * @param status
	 */
	protected void setLineCopyStatus(boolean status) {
		actionLineCopy.setEnabled(status);
		menuItemLineCopy.setEnabled(status);
		btnLineCopy.setEnabled(status);
	}

	/**
	 * ����"�д���ɾ��"�˵�������״̬
	 * 
	 * @param status
	 */
	protected void setInsertLineStatus(boolean status) {
		actionInsertLine.setEnabled(status);
		menuItemInsertLine.setEnabled(status);
		btnInsertLine.setEnabled(status);
	}

	/**
	 * ����"�д���ɾ��"�˵�������״̬
	 * 
	 * @param status
	 */
	protected void setRemoveLineStatus(boolean status) {
		actionRemoveLine.setEnabled(status);
		menuItemRemoveLine.setEnabled(status);
		btnRemoveLine.setEnabled(status);
	}

	/**
	 * ����"��������"�˵�������״̬
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
	 * ���������룬��������true
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
			// �����ύ״̬���ܱ���
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_SUBMIT_NOT_SAVE));
		}

		// �����ύʱ��Ҫ�жϴ�ʱ����״̬�����ϡ���ˡ��ر�״̬�ĵ��ݲ��ܱ��棩
		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// ��������״̬���ܱ���
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELTET_NOT_SAVE));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// �������״̬���ܱ���
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_SAVE));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// ���ݹر�״̬���ܱ���
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_SAVE));
		}

		checkMainOrgUnit();

		if (!checkInput())
			return;

		// UI�������û����õı��涯��������Ϊ������BOTP����
		// colin_xu,2007/05/28
		editData.setBotpCallSave(false);

		super.actionSave_actionPerformed(e);

		setAuditEnabled();
	}

	// �����ҵ����֯�Ƿ�Ϊ��. ��Ϊ����ʱ��ҵ����֯Ϊ��������ڱ���ʱҲ��Ҫ������δ��д�ĵ����޷���ѯ����. daij
	protected void checkMainOrgUnit() {
		if (getMainBizOrg() != null
				&& STClientUtils.isF7NullInfo(getMainBizOrg())) {

			OrgType orgType = getMainBizOrgType();
			String orgTypeString = (orgType == null) ? "" : "��"
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

		// �����ύʱ��Ҫ�жϴ�ʱ����״̬�����ϡ���ˡ��ر�״̬�ĵ��ݲ����ύ��
		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// ��������״̬�����ύ
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELETE_NOT_SUBMIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// �������״̬�����ύ
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_SUBMIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// ���ݹر�״̬�����ύ
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_SUBMIT));
		}

		checkMainOrgUnit();

		// ����CU,colin 2007-12-17
		if (getMainOrgInfo() != null)
			editData.setCU(getMainOrgInfo().getCU());

		if (editData.getId() != null) {
			IObjectPK pk = new ObjectUuidPK(editData.getId().toString());
			ISTBillBase ie = ((ISTBillBase) this.getBillInterface());
			// ���������ȡֵ. botp���ɵ��ݣ����ѡ�� �����浥��Ȼ���ύ��ôeditData�е�id�Ǽٵģ���û�ж�Ӧ�����ݿ��¼��.
			// edit by daij
			if (ie.exists(pk)) {
				STBillBaseInfo oldinfo = ie.getSTBillBaseInfo(pk);
				if (oldinfo.getBillStatus() != null) {
					if (oldinfo.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED_VALUE) {
						// �������״̬�����޸�
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
			// ���ݱ�Ҫ���ñ���
			setAutoNumberWhenHasCodingRule();
		}
		setStatus();

		// ����������ˣ���Ҫ��״̬��Ϊ�鿴 Robin 2007-4-12
		if (editData.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
			setOprtState(STATUS_VIEW);
			lockUIForViewStatus();
		}
	}

	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {

		if (STQMUtils.isNotNull(uiDirector)) {
			uiDirector.validateBizLogic(editData);
		}
		checkAndSetNumberField();
		
		
	}

	/**
	 * ���û�������ύǰ�жϲ�����������롣
	 * 
	 * @throws Exception
	 */
	protected void checkAndSetNumberField() throws Exception {
		// ͬʱ�����渳ֵ
		Component txtNumber = dataBinder.getComponetByField("number");
		if (this.codingRuleVo.isExist() && !this.codingRuleVo.isAddView()) {
			// ͬʱ�����渳ֵ
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
				SysUtil.abort(); // �˳�
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
	 * ��һ����ɾ��
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionFirst_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * ǰһ����ɾ��
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}
		super.actionPre_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * ��һ����ɾ��
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionNext_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	/**
	 * �����ɾ��
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		if (mainTabbedPane != null) {
			mainTabbedPane.setSelectedIndex(0);
		}

		super.actionLast_actionPerformed(e);

		setAuditAndUnAuditEnable();
	}

	protected void setAuditAndUnAuditEnable() {
		// ������˷������ʾ;
		BillBaseStatusEnum billStatus = this.editData.getBillStatus();
		// �ύ������˿���
		if (BillBaseStatusEnum.SUBMITED.equals(billStatus)) {
			this.actionAudit.setEnabled(true);
			this.btnAudit.setEnabled(true);
			this.menuItemAudit.setEnabled(true);
		} else {
			this.actionAudit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.menuItemAudit.setEnabled(false);
		}

		// ��ˣ��������
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
			// �������ɵĵ��ݲ������� Robin 2007-4-9
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

		// ���б�Ҫ�������ñ������
		setAutoNumberWhenHasCodingRule();
	}

	/**
	 * ���õ��б���������ʱ��������Ӧ�ı������
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
		// �����ύʱ��Ҫ�жϴ�ʱ����״̬�����ϡ���ˡ��ر�״̬�ĵ��ݲ����޸ģ�
		if (editData == null
				| editData.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
						.getValue()) {
			// ��������״̬�����޸�
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_DELETE_NOT_EDIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
						.getValue()) {
			// �������״̬�����޸�
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_AUDIT_NOT_EDIT));
		}

		if (editData == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
						.getValue()) {
			// ���ݹر�״̬�����޸�
			super.handUIExceptionAndAbort(new STBillException(
					STBillException.EXC_BILL_CLOSE_NOT_EDIT));
		}
		if (editData == null
				|| editData.getId() == null
				|| editData.getBillStatus().getValue() == BillBaseStatusEnum.ALTERING
						.getValue()) {
			// ���ݱ����״̬�����޸�
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

		// �����Ƿ�ɱ༭--һ�㲻������߳�֮��ĳ�ͻ--yangyong 2008-12-09
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
		// ���ñ����Ƿ�ɱ༭
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
		// ��ѡģʽ��������ɾ����¼
		// ���ӷǿ��жϣ������ָ�룬liming_su 20090107
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
	// //����������
	// auditConditionCheck(true);
	//        
	// ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
	// /*���*/
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
	// * ������ ����������
	// * @param bIsAudit trueΪ��ˣ�falseΪ�����
	// * @author williamwu 2006-12-27
	// */
	// protected void auditConditionCheck(boolean bIsAudit){
	// if (bIsAudit){
	// if (editData == null || editData.getId() == null
	// ||
	// editData.getBillStatus().getValue()!=BillBaseStatusEnum.SUBMITED.getValue
	// ()){
	// // ����δ�ύ�������
	// super.handUIExceptionAndAbort( new STBillException (
	// STBillException.EXC_BILL_NOT_SUBMIT)) ;
	// }
	// }
	// else{
	// if (editData == null ||
	// editData.getBillStatus().getValue()!=BillBaseStatusEnum
	// .AUDITED.getValue()) {
	// // ����δ���桢δ��˲��ܷ����
	// super.handUIExceptionAndAbort( new STBillException (
	// STBillException.EXC_BILL_NOT_AUDIT)) ;
	// }
	//    		
	// }
	// }

	// ������������д����Ƿ�Ҫ������˵ĵ��ݡ�
	public boolean isAuditBill(STBillBaseInfo info) throws Exception {
		return true;

	}

	// �����ദ�������ʱ����
	public void doAgreeAlter() throws Exception {

	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();

		// �������绥�����
		if (!isMutexControlOK(id)) {
			return;
		}

		// Ҫѡ�еĵ���������������
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		super.actionAudit_actionPerformed(e);
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());

		// �ж��Ƿ������˵�����
		checkAudit(pk);

		if (isAuditBill(editData)) {
			if (!multiApproveIfInWF()) {

				// ���÷���˴������
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

					releaseMutexControl(id); // �ͷŻ�����
					setOprtState(STATUS_VIEW);
					// true ���������Ķ༶����
					// ����ĳЩ�ط����Ҫ��ʱ��ϳ����˴���˯��ʱ���1000��Ϊ3000
					Thread.sleep(3000);
					this.refreshCurPage();

				} else {
					releaseMutexControl(id); // �ͷŻ�����
					setOprtState(STATUS_VIEW);
				}
			}

			// ����״̬����Ϣ�������б�
			if (editData.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {

				setOprtState(STATUS_VIEW);
				this.refreshCurPage(); // ��˺��������� added by miller_xiao
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

				// ��˳ɹ���Ӧ���������ù������Ŀ�����
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
	 * ���ʱ���ù������Ķ༶���������������ݱ����ڹ������У�
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

		// ��ȡ��Ҫ�༶������ID�б�
		ArrayList IDsInWF = STClientUtils.getIDsInActiveWorkFlow(idListArray);
		// ���ڹ�������
		if (IDsInWF.size() == 0) {
			return false;
		} else {
			// true ���������Ķ༶����
			actionMultiapprove_actionPerformed(null);
			// ����ĳЩ�ط����Ҫ��ʱ��ϳ����˴���˯��ʱ���1000��Ϊ3000
			Thread.sleep(3000);
			this.refreshCurPage();
			// SysUtil.abort();
			return true;
		}
	}

	private void checkAudit(ObjectUuidPK pk) throws Exception {

		// TODO ����������,�ڴ��ж��Ƿ���Ҫ��˵�������˵Ĳ�������

		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			// �±ߵ�ѭ���������ж��ǲ�����ѡ�ĵ����ǲ��Ƕ����Ϸ���˵������������һ�����������������׳��쳣-------

			STBillBaseInfo info = ((ISTBillBase) ie).getSTBillBaseInfo(pk);

			// ���Ϊ�����,����У��״̬
			if (!editData.getBillStatus().equals(BillBaseStatusEnum.ALTERING)) {
				AuditUtils.checkAuditDetail(info);
			}
		}

	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = editData.getId().toString();

		// �������绥�����
		if (!isMutexControlOK(id)) {
			return;
		}

		// Ҫѡ�еĵ�����������������
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsUnAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		super.actionUnAudit_actionPerformed(e);

		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());

		// �ж��Ƿ���Ϸ���˵�����
		checkUnAudit(pk);

		// ���÷���˴������
		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			((ISTBillBase) ie).unAudit(new ObjectUuidPK[] { pk });
		}

		// �ͷ�����
		try {
			pubFireVOChangeListener(null);
		} catch (Throwable e1) {
			// TODO �Զ����� catch ��
			handUIException(e1);
		}

		// ����״̬����Ϣ�������б�
		setMessageText(STQMUtils.getResource("UNAUDIT_SUCCESS"));
		showMessage();

		// ���½���
		editData.setBillStatus(BillBaseStatusEnum.TEMPORARILYSAVED);
		editData.setAuditor(null);
		editData.setAuditTime(null);

		// ����װ������
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

		// TODO ����������,�ڴ��ж��Ƿ���Ҫ����˵�������˵Ĳ�������

		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			// �±ߵ�ѭ���������ж��ǲ�����ѡ�ĵ����ǲ��Ƕ����Ϸ���˵������������һ�����������������׳��쳣-------

			STBillBaseInfo info = ((ISTBillBase) ie).getSTBillBaseInfo(pk);
			if (info.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
				checkUnAudit(info);
				AuditUtils.checkUnAuditDetail(info);
			}

			// ����Ƿ����������ε�����,�����ѡÿ��id��������κ�һ����¼�����ε��ݣ��ͻ��׳��쳣--���е���
			if (hasDestBill(pk.toString()) == true) {
				// ����Ϊ{0}�ĵ����ѹ��������������ݣ����������
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
	 * ���������ص��ݵ�ͷ��Ӧ������Info,����XXXBillInfo����Ĭ��ֵ. ע��: 1.Ҫ�����า�� 2.���ڴ��ṩ��ͷ��Ĭ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.EditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		return null;
	}

	/**
	 * 
	 * ���������ص�����ϸ��¼��Ӧ������Info,����XXXEntryInfo����Ĭ��ֵ. ע��: 1.Ҫ�����า�� 2.���ڴ��ṩ��¼��Ĭ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#createNewDetailData(com.kingdee.bos.ctrl.kdf.table.KDTable)
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	/**
	 * 
	 * ���������ص�����ϸ��¼��KDTableʵ��. ע��: Ҫ�����า��
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * 
	 * ����������ҵ�񵥾�ʵ���ҵ��ӿ�,������ΪXXXBill��ô�˽ӿ�����:IXXXBill ע��: Ҫ�����า��.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.EditUI#getBizInterface()
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	/**
	 * Ŀǰ�����������������ַ�ʽ��ҵ��ϵͳ����ѡ����ú��ַ�ʽ���������һ������������ѡ��������ʽ��
	 * ���ַ�ʽ�ֱ�Ϊ��һ�������͵��ŵ��ݣ���һ�������Ͷ��ŵ��ݣ����ֵ������Ͷ��ŵ��ݣ�������0��1��2��ʾ��Ĭ�Ϸ���0�� public final
	 * static int pullTypeSingleToSingle = 0; public final static int
	 * pullTypeSingleToMutil = 1; public final static int pullTypeMutilToMutil =
	 * 2;
	 */
	public int getBtpCreateFromType() {
		return pullTypeSingleToMutil;
	}

	/**
	 * ��������ʼ����������ť ����ʱ�䣺2006-12-2
	 */
	protected void initButtons() {

		setAuditEnabled();

		int billStatus = editData.getBillStatus().getValue();

		if (STATUS_FINDVIEW.equals(getOprtState())
				|| STATUS_VIEW.equals(getOprtState())) {
			switch (billStatus) {
			case 0: // ADD_VALUE
			case 1: // TEMPORARILYSAVED_VALUE
			case 2: // �ύ״̬
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
	 * ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.framework.client.EditUI#checkBeforeWindowClosing()
	 */
	public boolean checkBeforeWindowClosing() {
		// EditBy Brina 2007/01/18 �����Ƿ񱣴����ʾ�򣨽�״̬��Ϊ����״̬��
		if (hasWorkThreadRunning()) {
			return false;
		}

		// this.savePrintSetting(this.getTableForPrintSetting());
		// storeFields()�׳��쳣����editdata�иı䣬ѯ���Ƿ񱣴��˳�
		if (isModify()) {
			// editdata�иı�
			int result = MsgBox.showConfirm3(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Confirm_Save_Exit"));

			if (result == KDOptionPane.YES_OPTION) {

				try {
					if (BillBaseStatusEnum.SUBMITED.equals(editData
							.getBillStatus())) { // ������ύ״̬�������Submit���������Save
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
	 * ������������Ϊ��
	 * 
	 * @author:colin ����ʱ�䣺2007_04_25
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
	 * ��������һ��(���������ݷ�¼�п���ͨ��BOTP��������,Ҳ�������ֹ�¼������ݷ�¼),�����ķ�¼�Զ����Ƶ�����������һ�� ������
	 * 
	 * @author:colin ����ʱ�䣺2007_04_25
	 *               <p>
	 */
	public void actionLineCopy_actionPerformed(ActionEvent e) throws Exception {

		// �ȱ��棬�ٸ���
		storeFields();

		int copies = 1;

		// ����֪ͨ��ѡ�񴰿�
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

		// ���copiesΪ-1,˵��ѡ��ȡ��
		if (copies == -1) {
			return;
		}

		if (copies < 1) {
			MsgBox.showInfo("���Ƶ��������Ǵ��ڻ����1�����ܸ��ƣ�");
			return;
		}

		// ȡ����ϸ��
		KDTable kdTable = getDetailTable();

		if (STQMUtils.isNotNull(kdTable)) {
			if (kdTable.getSelectManager().size() > 0
					&& kdTable.getSelectManager().get(0).getMode() != KDTSelectManager.TABLE_SELECT) { // ��ѡģʽ
																										// ��
																										// �������Ʒ�¼

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
				// ��ȡѡ���к�
				int[] selectRows = KDTableUtil.getSelectedRows(kdTable);

				for (int i = 0; i < copies; i++) {
					for (int rowIndex = 0; rowIndex < infos.length; rowIndex++) {

						info = infos[rowIndex];

						// info��Ϊ��ʱ���뵽KDTable
						if (info instanceof CoreBaseInfo) {

							// ��info���и��Ի�����,һ����Ӧ�õ�xxxEditUI����ʵ��
							IObjectValue detailData = createNewDetailData(kdTable);

							detailData = processLineCopyItem(info, detailData);
							IRow row = kdTable.addRow();
							super.loadLineFields(kdTable, row, detailData);

							// ����editor
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

				// ����ת�Ƶ��¸��Ƶĵ�һ��
				if (oldBottomIndex + 1 < kdTable.getRowCount()) {
					// �ĳɱ༭״̬
					kdTable.getEditManager().editCellAt(oldBottomIndex + 1,
							oldLeftIndex);
				}

			} else {
				// �������¼Ϊ�գ���ʾ���ܽ����и�����������
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.NULL_LINEINFO));
			}
		}

		storeFields();
		// setDataObject(editData);

	}

	/**
	 * ע�������ѡ������
	 * 
	 * @param table
	 *            ��Ҫע���table
	 * @param materialColumnName
	 *            ��table�����������е�����
	 * @param isClearQty
	 *            ���Ƿ��������������
	 * @throws Exception
	 */
	protected MultiMaterialProcessor registerMultiMaterialProcessor(
			KDTable table, String materialColumnName, boolean isClearQty)
			throws Exception {
		// ��ҵ����֯
		OrgUnitInfo mainOrgInfo = (OrgUnitInfo) getMainOrgContext().get(
				getMainBizOrgType());
		MultiMaterialProcessor mmp = MultiMaterialProcessor.getInstance(this,
				table, materialColumnName, getMainBizOrgType(), mainOrgInfo,
				isClearQty);

		mmp.setDisplayName("$name$");

		multBaseDataManager.addProcessor(mmp);

		// ���ɶ��������ѡ���������
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
	 * ע�������ѡ������
	 * 
	 * @param table
	 *            ��Ҫע���table
	 * @param materialColumnName
	 *            ��table�����������е�����
	 * @param isClearQty
	 *            ���Ƿ��������������
	 * @throws Exception
	 */
	// protected com.kingdee.eas.st.basedata.multiF7.MultiMaterialProcessor
	// registerSTMultiMaterialProcessor(KDTable table, String
	// materialColumnName,
	// boolean isClearQty) throws Exception{
	// //��ҵ����֯
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
	// //���ɶ��������ѡ���������
	// multSTBaseDataManager.addAfterInsertEntryListener(
	// new com.kingdee.eas.st.basedata.multiF7.MultiBaseDataAdapter() {
	//			
	// /*
	// * modify by kangyu_zou 010204
	// * MultiMaterialProcessor������st.basedata.multiF7�����࣬import �����õ���qm����
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
	 * ע����ʼ���Ŀѡ������
	 * 
	 * @param table
	 *            ��Ҫע���table
	 * @param materialColumnName
	 *            ��table�����������е�����
	 * @param isClearQty
	 *            ���Ƿ��������������
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
	 * ע�����ҵ��ѡ������
	 * 
	 * @param table
	 *            ��Ҫע���table
	 * @param opSurfaceColumnName
	 *            ��table����ҵ�������е�����
	 * @throws Exception
	 */
	// protected MultiOpSurfaceProcessor registerMultiOpSurfaceProcessor(KDTable
	// table, String opSurfaceColumnName) throws Exception{
	// MultiOpSurfaceProcessor mop =
	// MultiOpSurfaceProcessor.getInstance(table,opSurfaceColumnName);
	//		
	// //��Ϊbd_mmģ���Ѿ�����ұ����Ŀ��֯ά��������basedata��������һ��F7���࣬���¶���
	// com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager
	// multSTBaseDataManager =
	// new com.kingdee.eas.st.basedata.multiF7.MultiBaseDataManager();
	//		
	// multSTBaseDataManager.addProcessor(mop);
	// multSTBaseDataManager.registerMultiBaseDataF7();
	//		
	// //������ҵ��ѡ���������
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
	 * ��������¼ѡ����ҵ���Ĵ������������� ���ɾ���к����ش���
	 * 
	 * @author kangyu_zou 2009-10-13 �޸�
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

		// ȡ�����е�KDTable���ϼ�����Ϣ
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

		// Ϊ��������ܣ�ֻ�е�����Ȩ��������Ϊ��ʱ��ȡ
		if (STQMUtils.isNull(addNewPermItemName)) {
			addNewPermItemName = getAddNewPermItemName();
		}

		if (STQMUtils.isNull(addNewPermItemName)) {
			return null;
		}

		OrgType orgType = getMainBizOrgType();

		// �����Ȩ��ʵ����֯
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
		// ocean.he ע�͵���һ�У������˺ܶ��BUG
		// ���水���Ŀ�����Ӧ����setStatus�п��Ʋ�Ӧ�÷�������������
		// ��Ȼ���Ʋ�ס
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
	 * ���õ�����KDTable�ϵ����¼�ͷ�ͻس������еĹ��� ���õ�Ԫ����ʱ��ֻ����ֵ�������Ʊ���ɫ ������Ը���
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
				// �Ե��쳣������ִ��
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

	// ���ù��˳��
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
	 * BOTP�����������Ŀ�굥��
	 */
	protected void setBizProcessControl() throws Exception {
		// ����ɵĲ˵�
		workbtnBizProcess.removeAllAssistButton();
		menuBizProcess.removeAll();

		// ����Ƿ���Ҫ��ʾ�˵�

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
			// û�����ò�����BOTPת������
			// SysUtil.abort();
			return;
		}

		String targetBillBosTypeString = targetBillBosTypeAndAliasArray[0];
		String targetBillAliasString = targetBillBosTypeAndAliasArray[1];
		String[] targetBillBosTypeArray = com.kingdee.eas.util.StringUtil
				.split(targetBillBosTypeString, ","); // ȡ��һ��BOSType
		String[] targetBillAliasArray = com.kingdee.eas.util.StringUtil.split(
				targetBillAliasString, ",");

		// ����Ҫ��Ŀ�굥�����targetBillBosTypeArray[i] �ó� nullֵ����
		processTargetBillArray(targetBillBosTypeArray, targetBillAliasArray);

		for (int i = 0, size = targetBillBosTypeArray.length; i < size; i++) {
			if (targetBillBosTypeArray[i] == null)
				continue;
			// �������Ҫ���ҵ����˵�,����
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

	// �Ƿ�Ҫ���ҵ����ť
	protected boolean isSetBizProcessControl(String targetBillBosType) {
		return true;
	}

	/**
	 * Robin �շ�������Ŀ�굥�����������磺���۳����ɹ��� ���������ƣ������ڱ༭�������ص�
	 * ����Ҫ��Ŀ�굥�����targetBillBosTypeArray[i] �ó� nullֵ����
	 * 
	 * @param targetBillBosTypeArray
	 * @param targetBillAliasArray
	 */
	protected void processTargetBillArray(String[] targetBillBosTypeArray,
			String[] targetBillAliasArray) {

	}

	/*
	 * ����Ŀ�굥��
	 */
	private void createToBill(Map map) throws Exception {
		String destBillBosType = (String) map.get(DESCBILL_BOSTYPE);

		int billBaseStatusValue = editData.getBillStatus().getValue();
		// ���������桢�ύ�����ᡢ����С�����״̬�ĵ��ݲ��ܹ����������ε���
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
		// ������δ����������ҵ���߼��л��׳��쳣,������,��ʹ��BOTMappingCollection
		//���������BOTP����A��B(��˳��)�����A�Ĺ��˹��򲻷��ϣ�B�ķ��ϣ������ʾ������BOTP����(���񲻻�ȥ�ҵڶ������Ϲ����BOTP)
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
		// MsgBox.showInfo("�������ã���Ŀ�굥�ݲ������ظ�����");
		// SysUtil.abort();
		// }

		if (getBillEdit() != null) {
			getBillEdit().CreateTo(editData, destBillBosType);
		}
	}

	/*
	 * ����Ŀ�굥��ǰУ�飬�������д
	 */
	protected boolean beforeActionBizProcess(Map map) {
		return true;
	}

	/*
	 * ���ز�ͬ��ҵ����action��Ӧ�д����ڲ���
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
	 * ����ҵ������ؿؼ���ʾ
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
	 * ��̬����actionǰ��У�飬�����Ƿ���ʾ�˹��ܣ��������д
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
	 * ��ҵ����֯�ı��ҵ����Խ��еĴ���
	 * 
	 */
	protected void afterMainOrgChanged(String oldOrgId, String newOrgId) {
		if (!isLoading) {
			boolean isDiffrent = STUtils.isDiffrent(oldOrgId, newOrgId);
			try {
				if (newOrgId != null) {
					if (isDiffrent) {

						// ֻ�������ͱ༭�Ŵ�����---�л���ҵ����֮֯����Ҫ���
						if (STATUS_ADDNEW.equals(getOprtState())
								|| STATUS_EDIT.equals(getOprtState())) {
							editData.setNumber(null);
							Component txtNumber = dataBinder
									.getComponetByField("number");
							// ���ݱ���Ǹ�����֯��ģ��޸���֯Ҫ��ձ���
							if (txtNumber instanceof KDTextField) {
								((KDTextField) txtNumber).setText(null);
							}
							setAutoNumberWhenHasCodingRule();
						}

						// У���ֶ�Ȩ��
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
	 * ���绥���Ƿ�ͨ����yangyong 080627
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
	 * �ͷſ�������yangyong 080627
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
	 * ����Ƿ����������ε�����
	 */
	protected boolean hasDestBill(String srcId) throws BOSException {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();

		// ����ID����
		filterInfo.getFilterItems().add(
				new FilterItemInfo("srcObjectID", srcId, CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("destEntityID", "DD4053D5",
						CompareType.NOTEQUALS));
		viewInfo.setFilter(filterInfo);

		BOTRelationCollection collection = BOTRelationFactory
				.getRemoteInstance().getCollection(viewInfo);
		if (collection != null && collection.size() > 0) {
			// �����ε���
			return true;
		}

		// û�����ε���
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
	 * ���������ص�������
	 * 
	 * @return
	 * @author:paul ����ʱ�䣺2006-9-25
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
	 * ��������������HMDģʽ��Դ��ʱѡ���������ʱ��
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
	 * ��������Ĭ�ϵ�ȡ�����֯
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
	 * ����һ�еĴ���
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

	/** ú̿ר����� **/
	/**
	 * @author wanglh �����ϲ�ѯ��ʱ���
	 *         �ڸ���ҵ��ͳ�������ʱ������¼��ɹ����������۶������������������۳��ⵥ�ȣ��ṩһ�����ϵļ�ʱ����ѯ�Ĺ���
	 *         �����û����Բ�ѯѡ�е��ݷ�¼���е�ĳ�����ϵļ�ʱ���
	 */
	public void actionQueryByMaterial_actionPerformed(ActionEvent e)
			throws Exception {
		HashMap hm = getQueryCondition();
		if (hm == null) {
			// MsgBox.showInfo(this, "����δ���ط���getQueryCondition()�����������������ֵΪ��");
			return;
		}
		/** ************************** */
		/* ���´����߼�������⣬��д��� */
		// 1���������hm���������ϣ�����Ϊ�ǰ����ϲ�ѯ��ʱ���
		// 2���������hm��û�����ϣ��п����֯,����Ϊ�ǰ������֯��ѯ��ʱ���
		/* Ϊ�����������޸ģ����û�д������ϣ�����Ϊ�ǲ�ѯ���м�ʱ��� ���ֺ� 2006.02.10 */
		// 3���������hm��û�����ϣ�Ҳû�п����֯������Ϊ�ǲ�ѯ���м�ʱ���
		/** ************************** */

		// ����
		MaterialInfo aMaterialInfo = (MaterialInfo) hm
				.get(SCMConstant.QUERY_MATERIAL);
		// �����֯
		StorageOrgUnitInfo aStorageOrgUnitInfo = (StorageOrgUnitInfo) hm
				.get(SCMConstant.QUERY_STORAGEORGUNIT);

		// Boolean tablfocus = Boolean.valueOf(true);
		// if (hm.get(SCMConstant.QUERY_TABLEHASFOCUS) != null) {
		// tablfocus = (Boolean) hm.get(SCMConstant.QUERY_TABLEHASFOCUS);
		// }
		// if (!tablfocus.booleanValue()) {//δѡ�б�񣬺�ѡ��������Ϊ�մ���һ��
		// aMaterialInfo = null;
		// }

		if (aMaterialInfo == null) {
			/* Ϊ�����������޸ģ����û�д������ϣ�����Ϊ�ǲ�ѯ���м�ʱ��� ���ֺ� 2006.02.10 */
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
	 * ��ѯ���м�ʱ���
	 * 
	 * @param hm
	 * @throws UIException
	 * @throws BOSException
	 * @throws Exception
	 * @throws SQLException
	 */
	private void queryResultByInventory(HashMap hm) throws Exception {
		// �������ϲ���������ѯUI.....
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
	 * �����ϲ�ѯ��ʱ���
	 * 
	 * @param hm
	 * @throws UIException
	 * @throws BOSException
	 * @throws Exception
	 * @throws SQLException
	 */
	private void queryResultByMaterial(HashMap hm) throws UIException,
			BOSException, Exception, SQLException {
		// �������ϲ���������ѯUI.....
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
		Object needSet = hmResult.get("needSet"); // ֻ����Ҫ��ֵ����ʱ����Ҫ����ֵ���رմ��ڲ���Ҫ����
													// neujyj 2008-7-31
		if (needSet != null) {
			setLotAndAssistProperty(hmResult);
		}

	}

	/**
	 * ����HashMap�е����ݣ������ϵ����κ͸������Ը�ֵ
	 * 
	 * @author �ޱ� ��Ҫ��������ʵ��
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
	 *            ��Ԥ��
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
	 * ��ȡ�״��ѯQuery ���������д
	 * 
	 * @return
	 */
	protected IMetaDataPK getPrintQueryPK() {
		return null;
	}

	/**
	 * ��ȡ�״�ģ��·������ ���������д
	 * 
	 * @return
	 */
	protected String getPrintPathName() {
		return null;
	}

	// ---------------------------------------------2016.04.13
	// _moerhao_���̷�д����_start---------------------------------------------
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
	//---------------------------------------------2016.04.13_moerhao_���̷�д����_end
	// ---------------------------------------------

}