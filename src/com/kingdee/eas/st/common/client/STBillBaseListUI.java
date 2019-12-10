/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.reportone.kdrs.exception.KDRSException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryFieldCollection;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.QueryPKInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.query.util.QueryUtil4Client;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QueryPanelCollection;
import com.kingdee.eas.base.commonquery.QueryPanelInfo;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.XMLBean;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.MutiOrgPermParam;
import com.kingdee.eas.base.permission.PermissionConstant;
import com.kingdee.eas.basedata.assistant.AbstractPrintIntegrationInfo;
import com.kingdee.eas.basedata.assistant.IPrintIntegration;
import com.kingdee.eas.basedata.assistant.PrintIntegrationFactory;
import com.kingdee.eas.basedata.assistant.util.CommonPrintIntegrationDataProvider;
import com.kingdee.eas.basedata.assistant.util.PrintIntegrationManager;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.attachMent.AttachMentConstant;
import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.scm.common.util.MultiDataSourceDataProviderProxy;
import com.kingdee.eas.scm.common.util.SCMConstant;
import com.kingdee.eas.scm.common.util.SCMSlaveCommonDataProvider;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillException;
import com.kingdee.eas.st.common.STBizUnitDataVO;
import com.kingdee.eas.st.common.MillerUtils.PrecisionManager;
import com.kingdee.eas.st.common.client.queryevent.AddressF7Event;
import com.kingdee.eas.st.common.client.queryevent.BoilerF7Event;
import com.kingdee.eas.st.common.client.queryevent.CustomerF7Event;
import com.kingdee.eas.st.common.client.queryevent.MaterialF7Event;
import com.kingdee.eas.st.common.client.queryevent.PersonF7Event;
import com.kingdee.eas.st.common.client.queryevent.ProductCheckTypeF7Event;
import com.kingdee.eas.st.common.client.queryevent.QIItemF7Event;
import com.kingdee.eas.st.common.client.queryevent.QISchemeF7Event;
import com.kingdee.eas.st.common.client.queryevent.QIStandardF7Event;
import com.kingdee.eas.st.common.client.queryevent.SupplierF7Event;
import com.kingdee.eas.st.common.client.utils.AuditUtils;
import com.kingdee.eas.st.common.util.STCommonDataProvider;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class STBillBaseListUI extends AbstractSTBillBaseListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STBillBaseListUI.class);

	private int checkAuditResult = 0;
	private int checkUnAuditResult = 0;

	private OrgUnitCollection authorizedMainOrgs = null; // ��Ȩ�޵���ҵ����֯����

	public static final String TB_CREATOR = "creator.name";
	public static final String TB_CREATETIME = "createTime";
	public static final String TB_UPDATOR = "lastUpdateUser.name";
	public static final String TB_UPDATETIME = "lastUpdateTime";
	public static final String TB_AUDITOR = "auditor.name";
	public static final String TB_AUDITIME = "auditTime";

	public final static String STATUS_ADDNEW = "ADDNEW";
	public final static String STATUS_EDIT = "EDIT";
	public final static String STATUS_VIEW = "VIEW";

	protected PrecisionManager precisionManager = null;
	/**
	 * �������������Ĵ�����.
	 */
	protected STCommonQueryProcessor queryProcessor = new STCommonQueryProcessor();

	/**
	 * output class constructor
	 */
	public STBillBaseListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		getUIContext().put(UIContext.CHECK_LICENSE, "false");// ȥ��license���飬��ɾ��
		super.onLoad();

		mainQuery.setIngorePreOrders(true);
		menuItemCopyTo.setVisible(false);

		btnCopyTo.setVisible(false);

		this.tblMain.setColumnMoveable(true);

		this.btnAudit.setEnabled(true);
		this.btnUnAudit.setEnabled(true);
		this.btnAudit.setVisible(true);
		this.btnUnAudit.setVisible(true);
		this.menuItemAudit.setEnabled(true);
		this.menuItemUnAudit.setEnabled(true);
		this.menuItemAudit.setVisible(true);
		this.menuItemUnAudit.setVisible(true);

		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setEnabled(false);

		setCloseVisible(false);

		// �������Ҷ��������
		// KDTableUtils.setTableAlign(tblMain, dataBinder);
		com.kingdee.eas.st.common.util.KDTableUtils.setTableAlign(tblMain,
				getQueryInfo(getMainQueryPK()));

		if (precisionManager != null) {
			precisionManager.dealPrecision();
		}
	}

	/**
	 * ���ø���ť��������ͼ��״̬
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		// ��˺ͷ����
		menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
	}

	/**
	 *output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
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
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	/**
	 * ������ӡ
	 */
	public void actionMultiPrint_actionPerformed(ActionEvent e)
			throws Exception {
		invokeMultiPrintFunction(true);
	}

	/**
	 * ������ӡԤ��
	 */
	public void actionMultiPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		invokeMultiPrintFunction(false);
	}

	/**
	 * @param isPrint
	 *            ��ӡ���ӡԤ��
	 * @throws Exception
	 */
	protected void invokeMultiPrintFunction(boolean isPrint) throws Exception {
		// û��ѡ���¼
		checkSelected();

		ArrayList idList = getSelectedIdValues();

		if (isHasNoAuditPrintBill(idList)) {
			int confirm = MsgBox.showConfirm2(this, EASResource.getString(
					"com.kingdee.eas.st.common.STResource", "HasUNAuditBill"));
			if (confirm != MsgBox.OK) {
				return;
			}
		}

		List list = invokeLimitPrintFunction(idList);
		invokeMultiPrintFunction(list, isPrint);
	}

	protected List invokeLimitPrintFunction(List idList) {
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			return null;
		}
		// R081103-073 �жϴ�ӡ�����Ƿ��Ѿ������������.������ӡ�����ĵ��ݽ��ڴ�ӡ����List��ȥ�������ڴ�ӡ����ʾ��ʾ��Ϣ
		StringBuffer failToPrintMsg = new StringBuffer();
		KDNoteHelper tpHelper = new KDNoteHelper();

		try {
			// ���û���������������ƣ���ֱ�ӷ���
			if (!tpHelper.isPrintTimesControllable2(getTDFileName())) {
				return idList;
			}

			int curNum = 1;// ��ȡ��ǰ��ӡ����.
			String bosType = getBizInterface().getType().toString();
			IPrintIntegration pinfo = PrintIntegrationFactory
					.getRemoteInstance();
			List infoList = pinfo.getBillsPrintInfoByList(idList, bosType);// �����ݿ��ȡ�д�ӡ��Ϣ���б����
																			// .
			tpHelper.prepareBizCall(getTDFileName());

			if (getTDFileName() != null && getTDFileName().trim().length() > 0) {
				for (int i = 0; i < infoList.size(); i++) {
					logger.info("start the print control!");
					int maxNum = tpHelper.getMaxPrintTimes2(getTDFileName());// ʹ��ʵ��������������ľ�̬����
					int pnum = ((AbstractPrintIntegrationInfo) infoList.get(i))
							.getPrintedNumber();// ��ȡ�Ѵ�ӡ����
					String billID = ((AbstractPrintIntegrationInfo) infoList
							.get(i)).getPrintBillID();// ��ȡ��ӡ����ID
					logger.info("Max print number:>>" + maxNum);
					logger.info("Alreadey print number:>>" + pnum);
					logger.info("current print number:>>" + curNum);

					if (pnum >= maxNum) {
						idList.remove(billID);
						String billNumber = pinfo.getBillNumberByBosType(
								bosType, billID);
						String msgInfo = EASResource
								.getString(
										"com.kingdee.eas.basedata.assistant.PrintIntegrationResource",
										"pi.controlinfo1");
						Object[] objs = { billNumber, String.valueOf(curNum),
								String.valueOf(pnum), String.valueOf(maxNum) };
						failToPrintMsg.append(MessageFormat.format(msgInfo,
								objs)
								+ "\n");
					} else if (curNum + pnum > maxNum) {
						idList.remove(billID);
						String billNumber = pinfo.getBillNumberByBosType(
								bosType, billID);
						String msgInfo = EASResource
								.getString(
										"com.kingdee.eas.basedata.assistant.PrintIntegrationResource",
										"pi.controlinfo2");
						Object[] objs = { billNumber, String.valueOf(curNum),
								String.valueOf(pnum), String.valueOf(maxNum) };
						failToPrintMsg.append(MessageFormat.format(msgInfo,
								objs)
								+ "\n");
					}
				}

				if (failToPrintMsg.toString().trim().length() > 0) {
					String error = EASResource.getString(
							"com.kingdee.eas.st.common.STResource",
							"FailToPrintMsg");
					MsgBox.showDetailAndOK(null, error, failToPrintMsg
							.toString(), AdvMsgBox.DETAIL_OK_OPTION);
					// return null;
				}
			}

			return idList;
		} catch (KDRSException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			handUIException(e);
		}

		return null;
	}

	/**
	 * ��������ӡ���ӡԤ��
	 * 
	 * @param idList
	 * @param isPrint
	 * @author:paul ����ʱ�䣺2007-6-1
	 *              <p>
	 */
	protected void invokeMultiPrintFunction(List idList, boolean isPrint) {
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			return;
		}

		KDNoteHelper tpHelper = new KDNoteHelper();
		MultiDataSourceDataProviderProxy data = new MultiDataSourceDataProviderProxy();
		// ֧�ֻ��ܴ�ӡ
		setCustomerDataProvider(data, idList);
		STCommonDataProvider mainQueryData = null;
		if (getParamName() != null) {
			mainQueryData = new STCommonDataProvider(idList, getTDQueryPK(),
					getParamName());
		} else {
			mainQueryData = new STCommonDataProvider(idList, getTDQueryPK());
		}
		mainQueryData.setFilter(getPrintFilter(idList));
		BOSQueryDelegate multiApproveViewQueryData = new SCMSlaveCommonDataProvider(
				idList, getMultiApproveViewQuery(), "billId");
		data.put("MainQuery", mainQueryData);
		data.put("MultiApproveViewQuery", multiApproveViewQueryData);
		// begin by lixiaojiao at 2008-06-13 ��Ӵ�ӡ��������Դ,ͬʱ������ӡ���Ƽ��ɼ���
		// KDNoteHelper appHlp = new KDNoteHelper();
		// appHlp.prepareBizCall("/path");
		BOSObjectType bosType = null;
		try {
			bosType = getBizInterface().getType();
			logger.info("current bostype:>>" + bosType.toString());
		} catch (Exception e) {
			MsgBox
					.showError(EASResource
							.getString(
									"com.kingdee.eas.basedata.assistant.PrintIntegrationResource",
									"pi.remoteerror"));
			SysUtil.abort();
		}
		CommonPrintIntegrationDataProvider printQueryData = new CommonPrintIntegrationDataProvider(
				bosType.toString(), PrintIntegrationManager.getPrintQueryPK());
		// ��������Ӵ�ӡ�����ṩ
		data.put("PrintQuery", printQueryData);
		PrintIntegrationManager.initPrint(tpHelper, bosType, idList, this
				.getTDFileName(), SCMConstant.SCMRESOURCE_PATH, false);
		// end

		if (isPrint) {
			tpHelper.print(getTDFileName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		} else {
			tpHelper.printPreview(getTDFileName(), data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
		}
	}

	/**
	 * �жϴ�ӡ�ĵ���֮���Ƿ����û����˵ĵ���
	 * 
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	protected boolean isHasNoAuditPrintBill(List idList) throws Exception {
		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			List list = (ArrayList) ((ISTBillBase) ie).getUnAuditBills(idList);
			if (list != null && list.size() > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ȡ�ô�ӡ��ѯ
	 * 
	 * @return
	 */
	protected IMetaDataPK getTDQueryPK() {
		return null;
	}

	/**
	 * ȡ�ô�ӡģ�������
	 * 
	 * @return
	 */
	protected String getTDFileName() {
		return null;
	}

	/**
	 * ȡ�ò���������
	 * 
	 * @return
	 */
	protected String getParamName() {
		return null;
	}

	/**
	 * ���������ÿͻ����Զ���Ĵ�ӡ����Դ.
	 * 
	 * @param data
	 *            ����Դ�������<����Դ����,��Ӧ��query>
	 * @param idList
	 *            Ҫ��ӡ��id�б�
	 */
	protected void setCustomerDataProvider(
			MultiDataSourceDataProviderProxy data, List idList) {
		// �������ܣ���ʱû��ʵ�֣�Ŀǰ��Ӧ��Ҳû��ʵ���������
	}

	/**
	 * ���������ͱ���״̬�ĵ���
	 * 
	 * @return
	 */
	protected FilterInfo getPrintFilter(List ids) {
		FilterInfo filter = new FilterInfo();
		if (ids.size() == 1) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", ids.toArray()[0].toString(),
							CompareType.EQUALS));
		} else {
			// �����������ȽϷ�ʽΪCompareType.INCLUDE��CompareType.NOTINCLUDEʱ��
			// �ڶ��������Ƚ�ֵObject���������Set���Զ��ŷָ���String
			filter.getFilterItems().add(
					new FilterItemInfo("id", new java.util.HashSet(ids),
							CompareType.INCLUDE));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", new Integer(
						BillBaseStatusEnum.ADD_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", new Integer(
						BillBaseStatusEnum.TEMPORARILYSAVED_VALUE),
						CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", new Integer(
						BillBaseStatusEnum.DELETED_VALUE),
						CompareType.NOTEQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3");
		return filter;
	}

	/**
	 * ȡ�ö༶������Ϣ��ѯ
	 * 
	 * @return
	 */
	protected IMetaDataPK getMultiApproveViewQuery() {
		return new MetaDataPK(
				"com.kingdee.eas.base.multiapprove.app.MultiApproveViewQuery");
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK[] pks = getSelectedListPK();
		// if (STQMUtils.isNotNull(pks) && pks.length >1){
		// //�޸ĵ���ʱ��ֻ��ѡ��һ�ŵ���
		// super.handUIExceptionAndAbort( new STBillException
		// (STBillException.EXC_BILL_NOT_MULT)) ;
		// }

		if (STQMUtils.isNotNull(pks) && pks.length > 0) {
			// �жϵ���״̬�����ϡ���ˡ��ر�״̬�ĵ��ݲ����޸ģ�ֻ����ˣ�
			ICoreBase ie = getBizInterface();
			STBillBaseInfo stInfo = (STBillBaseInfo) ie.getValue(pks[0]);

			if (stInfo == null
					|| stInfo.getId() == null
					|| stInfo.getBillStatus().getValue() == BillBaseStatusEnum.DELETED
							.getValue()) {
				// ��������״̬�����޸�
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.EXC_BILL_DELETE_NOT_EDIT));
			}

			if (stInfo == null
					|| stInfo.getId() == null
					|| stInfo.getBillStatus().getValue() == BillBaseStatusEnum.AUDITED
							.getValue()) {
				// �������״̬�����޸�
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.EXC_BILL_AUDIT_NOT_EDIT));
			}

			if (stInfo == null
					|| stInfo.getId() == null
					|| stInfo.getBillStatus().getValue() == BillBaseStatusEnum.CLOSED
							.getValue()) {
				// ���ݹر�״̬�����޸�
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.EXC_BILL_CLOSE_NOT_EDIT));
			}

			if (stInfo == null
					|| stInfo.getId() == null
					|| stInfo.getBillStatus().getValue() == BillBaseStatusEnum.ALTERING
							.getValue()) {
				// ���ݱ����״̬�����޸�
				super.handUIExceptionAndAbort(new STBillException(
						STBillException.EXC_BILL_ALTER_NOT_EDIT));
			}

		}
		ICoreBase ie = getBizInterface();
		STBillBaseInfo stInfo = null;
		if (pks.length > 0) {
			stInfo = (STBillBaseInfo) ie.getValue(pks[0]);
			if (stInfo.getId() != null) {
				if (!isMutexControlOK(stInfo.getId().toString())) {
					return;
				}
			}
		}

		super.actionEdit_actionPerformed(e);

		if (stInfo != null && stInfo.getId() != null) {
			try {
				pubFireVOChangeListener(null);
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// ����Ҳ��Ҫ������绥�⹦�� (��ʱ���Բ���)
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		String boID = this.getSelectedKeyValue();
		checkSelected();
		if (boID == null) {
			return;
		}
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		info.setCode("");
		info.setEdit(true);
		info
				.setListener(createAttatchListener(AttachMentConstant.HEAD_ATTACHMENT));
		// acm.showAttachmentListUIByBoID(this,info); // boID �� �븽�������� ҵ������ ID
		acm.showAttachmentListUIByBoID(boID, this, false);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPublishReport_actionPerformed(e);
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
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionCopyTo_actionPerformed
	 */
	public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyTo_actionPerformed(e);
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
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAuditResult_actionPerformed(e);
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
		actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * 
	 * �������õ���ǰѡ���е�id
	 * 
	 * @return listPK[]
	 * @author:williamwu ����ʱ�䣺2006-11-21
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

	protected boolean isOrderByIDForBill() {
		return false;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK[] iPks = getSelectedListPK();

		ICoreBase ie = getBizInterface();
		STBillBaseInfo stInfo = null;

		// ���ѡ�е�Key
		ObjectUuidPK[] pks;
		List idList = getSelectedIdValues();
		if (idList.size() > 0) {
			pks = new ObjectUuidPK[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				pks[i] = new ObjectUuidPK(idList.get(i).toString());
			}
		} else {
			pks = new ObjectUuidPK[0];
		}

		for (int i = 0; i < idList.size(); i++) {
			if (!isMutexControlOK(idList.get(i).toString())) {
				return;
			}
		}

		// Ҫѡ�еĵ���������������
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		int[] rows = KDTableUtils.getSelectedRows(tblMain);
		int[] status = new int[rows.length];
		for (int i = 0; i < rows.length; i++) {
			status[i] = ((BizEnumValueDTO) KDTableUtils.getCell(tblMain,
					rows[i], "billStatus").getValue()).getInt();
		}

		if (stInfo != null && stInfo.getId() != null) {
			try {
				pubFireVOChangeListener(null);
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}

		super.actionAudit_actionPerformed(e);

		// ����Ƿ��е��ݱ�ѡ��
		checkSelected();

		// ���ѡ�е�Key
		if (idList.size() > 0) {
			pks = new ObjectUuidPK[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				pks[i] = new ObjectUuidPK(idList.get(i).toString());
			}
		} else {
			pks = new ObjectUuidPK[0];
		}

		// ��ȡ���������id
		String[] pkStrings = checkAudit(pks);

		ObjectUuidPK[] auditPKs = new ObjectUuidPK[pkStrings.length];
		ArrayList normalAuditPKs = new ArrayList();
		ArrayList alterAuditPKs = new ArrayList();
		boolean isMultiApproveIfInWF = multiApproveIfInWF(pkStrings);
		for (int i = 0, count = pkStrings.length; i < count; i++) {
			auditPKs[i] = new ObjectUuidPK(pkStrings[i]);

			if (!isMultiApproveIfInWF) {
				// ���÷���˴������
				if (ie instanceof ISTBillBase) {
					if (BillBaseStatusEnum.ALTERING_VALUE != status[i]) {
						normalAuditPKs.add(auditPKs[i]);
					} else {
						alterAuditPKs.add(auditPKs[i]);
					}
				}
			} else {
				normalAuditPKs.add(auditPKs[i]);
			}
		}

		// ���÷���˴������
		if (ie instanceof ISTBillBase) {
			// �������
			if (normalAuditPKs.size() > 0 && !isMultiApproveIfInWF) {
				Map retMap = ((ISTBillBase) ie)
						.audit((ObjectUuidPK[]) normalAuditPKs
								.toArray(new ObjectUuidPK[0]));
				getUIContext().put("ST_AUDIT_MSG_MAP", retMap);
			}

			// ������
			for (int i = 0; i < alterAuditPKs.size(); i++) {
				doAgreeAlter((IObjectPK) alterAuditPKs.get(i));
			}
		}

		// ����״̬����Ϣ�������б�
		setMessageText(STQMUtils.getResource("AUDIT_SUCCESS"));
		showMessage();
		this.refreshList();

		if (checkAuditResult != pks.length) {
			MsgBox.showInfo("�����" + String.valueOf(pks.length) + "�ŵ��ݣ��ɹ�"
					+ String.valueOf(checkAuditResult) + "�ţ�"
					+ String.valueOf(pks.length - checkAuditResult) + "��δ��ˡ�");
		}

		try {
			for (int i = 0; i < idList.size(); i++) {
				this.setOprtState("RELEASEALL");
				releaseMutexControl(idList.get(i).toString());
			}
		} catch (Throwable ex) {
			handUIException(ex);
		}

		refreshList();
	}

	/**
	 * ���ʱ���ù������Ķ༶���������������ݱ����ڹ������У�
	 * 
	 * @return false��ʾ�������б�����ϵ���������,���ù������Ķ༶���������߲���������
	 * @return true ��ʾ����scm���������
	 * @param e
	 * @throws BOSException
	 * @throws Exception
	 * @author:tianpan date: 2007-5-29 20:00:49
	 */
	private boolean multiApproveIfInWF(String[] idListArray)
			throws BOSException, Exception {
		// ��ȡ��Ҫ�༶������ID�б�
		ArrayList IDsInWF = STClientUtils.getIDsInActiveWorkFlow(idListArray);
		// true����ʾ��ѡ��������һ�����ڹ������У�һ����û�й������������ǲ�������˵�
		// idListArray.length > 1����ʾ��ѡ���ݱ������1�������ж��Ƿ�����һ�£�
		// ȥ���ظ�id
		// String[] distinctIdListArray =
		// SCMClientUtils.getSetArray(idListArray);
		if (idListArray.length > 1 && IDsInWF.size() > 0
				&& IDsInWF.size() != idListArray.length) {
			// �в����Ƕ༶�������в�����ֱ����ˣ������ǲ������
			MsgBox.showError(getResource("AUDITNOTSAME"));
			SysUtil.abort();
		}
		// true ���������Ķ༶����
		if (IDsInWF.size() != 0) {
			String userID = SysContext.getSysContext().getCurrentUserInfo()
					.getId().toString();
			// ArrayList idList = getSelectedIdValues();
			// String[] boID = new String[idList.size()];
			// if(boID == null || boID.length == 0)
			// {
			// return;
			// }
			// for(int i = 0;i<idList.size() ;i++)
			// {
			// boID[i] = (String) idList.get(i);
			// }

			MultiApproveUtil.multiapproveBills(this, userID, idListArray);
			// multiApproved = true;
			this.refreshList();
			return true;
		}
		return false;
	}

	public String[] checkAudit(ObjectUuidPK[] pks) throws Exception {

		int auditedItemCount = 0;
		ArrayList auditedItem = new ArrayList();

		if (pks == null) {
			this.handleException(new STBillException(
					STBillException.AUDIT_MUSTSELECT_ONERECORD));
			return (new String[0]);
		}

		// TODO ����������,�ڴ��ж��Ƿ���Ҫ����˵�������˵Ĳ�������

		ICoreBase ie = getBizInterface();
		boolean isAuditBillFlag = true;
		if (ie instanceof ISTBillBase) {
			// �±ߵ�ѭ���������ж��ǲ�����ѡ�ĵ����ǲ��Ƕ�������˵������������һ�����������������׳��쳣-------
			for (int i = 0, cont = pks.length; i < cont; i++) {
				STBillBaseInfo info = ((ISTBillBase) ie)
						.getSTBillBaseInfo(pks[i]);
				// billInfoMap.put(pks[i], info);
				isAuditBillFlag = isAuditBill(info);
				if (isAuditBillFlag) {
					if (info.getBillStatus()
							.equals(BillBaseStatusEnum.SUBMITED)) {
						AuditUtils.checkAuditDetail(info);
						auditedItem.add(pks[i].toString());
						// δ��˵����ۼ�
						auditedItemCount++;
					}
					// ���Ϊ�����,����У��״̬
					if (info.getBillStatus()
							.equals(BillBaseStatusEnum.ALTERING)) {
						auditedItem.add(pks[i].toString());
						// δ��˵����ۼ�
						auditedItemCount++;
					}
				}

			}
		}

		// ����Ϊ{0}�ĵ��ݣ�����״̬��Ϊ���ύ�������������
		if (auditedItemCount == 0 && isAuditBillFlag) {
			throw new STBillException(STBillException.NOTEXISTUNAUDITEDBILL);
		}

		checkAuditResult = auditedItemCount;

		Object[] os = auditedItem.toArray();
		int count = os.length;
		String[] ss = new String[count];
		for (int i = 0; i < count; i++) {
			ss[i] = os[i].toString();
		}

		return ss;
	}

	// ������������д����Ƿ�Ҫ������˵ĵ��ݡ�
	public boolean isAuditBill(STBillBaseInfo info) throws Exception {
		return true;
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// ���ѡ�е�Key
		ObjectUuidPK[] pks;
		List idList = getSelectedIdValues();
		if (idList.size() > 0) {
			pks = new ObjectUuidPK[idList.size()];
			for (int i = 0; i < idList.size(); i++) {
				pks[i] = new ObjectUuidPK(idList.get(i).toString());
			}
		} else {
			pks = new ObjectUuidPK[0];
		}

		for (int i = 0; i < idList.size(); i++) {
			if (!isMutexControlOK(idList.get(i).toString())) {
				return;
			}
		}

		// Ҫѡ�еĵ�����������������
		int confirm = MsgBox.showConfirm2(this, EASResource.getString(
				"com.kingdee.eas.st.common.STResource", "BillIsUnAudit"));
		if (confirm != MsgBox.OK) {
			return;
		}

		super.actionUnAudit_actionPerformed(e);

		// ����Ƿ��е��ݱ�ѡ��
		checkSelected();

		// �ж��Ƿ���Ϸ���˵�����
		String[] pkStrings = checkUnAudit(pks);
		ObjectUuidPK[] unAuditPKs = new ObjectUuidPK[pkStrings.length];
		for (int i = 0, count = pkStrings.length; i < count; i++) {
			unAuditPKs[i] = new ObjectUuidPK(pkStrings[i]);
		}

		// ���÷���˴������
		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			((ISTBillBase) ie).unAudit(unAuditPKs);
		}

		// ����״̬����Ϣ�������б�
		setMessageText(STQMUtils.getResource("UNAUDIT_SUCCESS"));
		showMessage();
		this.refreshList();

		if (checkUnAuditResult != pks.length) {
			MsgBox.showInfo("�������" + String.valueOf(pks.length) + "�ŵ��ݣ��ɹ�"
					+ String.valueOf(checkUnAuditResult) + "�ţ�"
					+ String.valueOf(pks.length - checkUnAuditResult)
					+ "��δ����ˡ�");
		}

		try {
			for (int i = 0; i < idList.size(); i++) {
				this.setOprtState("RELEASEALL");
				releaseMutexControl(idList.get(i).toString());
			}
		} catch (Throwable ex) {
			handUIException(ex);
		}

		refreshList();
	}

	private String[] checkUnAudit(ObjectUuidPK[] pks) throws Exception {

		int unAuditedItemCount = 0;
		ArrayList unAuditedItem = new ArrayList();

		if (pks == null) {
			this.handleException(new STBillException(
					STBillException.UNAUDIT_MUSTSELECT_ONERECORD));
			return (new String[0]);
		}

		// TODO ����������,�ڴ��ж��Ƿ���Ҫ����˵�������˵Ĳ�������

		ICoreBase ie = getBizInterface();
		if (ie instanceof ISTBillBase) {
			// �±ߵ�ѭ���������ж��ǲ�����ѡ�ĵ����ǲ��Ƕ����Ϸ���˵������������һ�����������������׳��쳣-------
			for (int i = 0, cont = pks.length; i < cont; i++) {
				STBillBaseInfo info = ((ISTBillBase) ie)
						.getSTBillBaseInfo(pks[i]);
				if (info.getBillStatus().equals(BillBaseStatusEnum.AUDITED)) {
					AuditUtils.checkUnAuditDetail(info);

					// ����Ƿ����������ε�����,�����ѡÿ��id��������κ�һ����¼�����ε��ݣ��ͻ��׳��쳣--���е���
					if (hasDestBill(info.getId().toString()) == true) {
						// ����Ϊ{0}�ĵ����ѹ��������������ݣ����������
						throw new STBillException(
								STBillException.HASDESTBILL_CANNOTUNAUDIT,
								new Object[] { info.getNumber() });
					}

					unAuditedItem.add(pks[i].toString());
					checkUnAudit(info);
					// ����˵����ۼ�
					unAuditedItemCount++;
				}
			}
		}

		// ����Ϊ{0}�ĵ��ݣ�����״̬��Ϊ����ˡ������������
		if (unAuditedItemCount == 0) {
			throw new STBillException(STBillException.NOTEXISTAUDITEDBILL);
		}

		checkUnAuditResult = unAuditedItemCount;

		Object[] os = unAuditedItem.toArray();
		int count = os.length;
		String[] ss = new String[count];
		for (int i = 0; i < count; i++) {
			ss[i] = os[i].toString();
		}

		return ss;

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

	protected void checkUnAudit(STBillBaseInfo info) throws Exception {

	}

	protected String getEditUIName() {
		// TODO �Զ����ɷ������
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected boolean initDefaultFilter() {
		return false;
	}

	/**
	 * 
	 * ��������д���õ�ͨ�ù�����Ҫʹ��F7����,������Ը���.
	 * 
	 * 1. ����д��F7��: ����, �ʼ��׼, �ʼ���Ŀ; �����������������Ҫ���Ǵ˷���: 1.1)
	 * QueryEvent�еĹؼ���(QueryԪ�����е��ֶ�����)��Ĭ�ϲ�һ��, ����Ĭ���ʼ���Ŀ�ؼ���Ϊ:
	 * entries.qiItem.name���ʵ��ʹ�õ�query��ͬ����Ҫ���� 1.2) Ĭ�ϲ���:
	 * STCommonQueryProcessor����F7����,
	 * ���Ĭ��F7���ò�����Ҫ��,��ô���Դ�STCommonQueryProcessor�̳и�����Ӧ�ķ�������д�ر������. 1.3)
	 * �����Ҫ���õ�F7����Ĭ��֮��,��ô��Ҫ����initCommonQueryDialog�����Լ�����.
	 * 
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.framework.client.ListUI#initCommonQueryDialog()
	 */
	public CommonQueryDialog initCommonQueryDialog() {

		CommonQueryDialog conditionDialog = super.initCommonQueryDialog();

		// ��STCommonQueryProcessorʵ������queryProcessor extends
		// STCommonQueryProcessor
		// �����ҪҲ���Դ�STCommonQueryProcessor�̳г��Լ���CommonQueryProcessor.
		// ��: this.queryProcessor = new XXXQueryProcessor();

		// ��Ҫ��������F7��.

		// �ʼ��׼
		this.queryProcessor.putQueryEvent("qiStandard.name",
				new QIStandardF7Event());

		// �ʼ췽��
		this.queryProcessor.putQueryEvent("qIScheme.number",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("qIScheme.name",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("qiScheme.number",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("qiScheme.name",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("rawQIScheme.number",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("rawQIScheme.name",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("changeQIScheme.number",
				new QISchemeF7Event());
		this.queryProcessor.putQueryEvent("changeQIScheme.name",
				new QISchemeF7Event());

		// ����(���롢���ƾ���������ѡ�񴰿�)
		this.queryProcessor.putQueryEvent("material.number",
				new MaterialF7Event());
		this.queryProcessor.putQueryEvent("material.name",
				new MaterialF7Event());

		// �ʼ���Ŀ
		this.queryProcessor.putQueryEvent("entries.qiItem.name",
				new QIItemF7Event());

		// ��Ʒ��������
		this.queryProcessor.putQueryEvent("productCheckType.name",
				new ProductCheckTypeF7Event());

		// �ͻ�
		this.queryProcessor.putQueryEvent("customer.name",
				new CustomerF7Event());
		// ����ۼ����ؼ����ﵥ��ѯ
		// this.queryProcessor.putQueryEvent("relations.weighBillnumber",new
		// WeighBillnumberF7Event());
		// �ʼ�ҵ�����
		// this.queryProcessor.putQueryEvent("qiBizType.name",new
		// QiBizTypeF7Event());
		// �Ƶ���
		this.queryProcessor.putQueryEvent("creator.name", new PersonF7Event());
		this.queryProcessor.putQueryEvent("lastUpdateUser.name",
				new PersonF7Event());
		this.queryProcessor.putQueryEvent("auditor.name", new PersonF7Event());
		this.queryProcessor.putQueryEvent("closer.name", new PersonF7Event());
		this.queryProcessor.putQueryEvent("entries.address",
				new AddressF7Event());
		this.queryProcessor.putQueryEvent("entries.address.name",
				new AddressF7Event());
		this.queryProcessor.putQueryEvent("entries.supplier.name",
				new SupplierF7Event());

		// ¯��
		this.queryProcessor.putQueryEvent("boiler.number", new BoilerF7Event());
		this.queryProcessor.putQueryEvent("boiler.name", new BoilerF7Event());

		this.queryProcessor.setOwner(this);

		// ���ʼ��׼�������f7uiΪ���������listui
		conditionDialog.setProcessor(this.queryProcessor);

		// ���ù̶���ѯ����
		try {
			CustomerQueryPanel userPanel = getFilterUI();
			if (userPanel != null) {
				conditionDialog.addUserPanel(userPanel);
			}
		} catch (Exception e) {
			super.handUIException(e);
		}
		return (conditionDialog);
	}

	/**
	 * �̶���ѯUI
	 * 
	 * @return
	 * @throws Exception
	 * @author ywbair 2006-09-19
	 * @throws Exception
	 */
	protected CustomerQueryPanel getFilterUI() throws Exception {
		return null;
	}

	protected boolean isFootVisible() {
		return true;
	}

	private String getResource(String key) {
		return EASResource.getString("com.kingdee.eas.st.common.STResource",
				key);
	}

	protected STBizUnitDataVO getSTBizUnitDataVO() {
		return null;
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		STBizUnitDataVO dataVO = getSTBizUnitDataVO();
		if (dataVO == null) {
			return;
		}
		HashMap materialMap = new HashMap();
		HashMap unitMap = new HashMap();
		HashMap baseUnitMap = new HashMap();
		HashMap assistUnitMap = new HashMap();
		Map precisionMap = new HashMap();
		int unitPrecision = 4, baseUnitPrecision = 4, assistUnitPrecision = 4;
		String materialID = null, unitID = null, baseUnitID = null, assistUnitID = null;

		int i = 0;
		try {
			if (!StringUtils.isEmpty(dataVO.getMaterialColName())) {
				while (rowSet.next()) {
					if (STUtils.isNotNull(rowSet.getObject(dataVO
							.getMaterialColName()))) {
						materialMap.put(new Integer(i), rowSet.getObject(
								dataVO.getMaterialColName()).toString());
					}
					if (STUtils.isNotNull(dataVO.getUnitColName())
							&& STUtils.isNotNull(rowSet.getObject(dataVO
									.getUnitColName()))) {
						unitMap.put(new Integer(i), rowSet.getObject(
								dataVO.getUnitColName()).toString());
					}
					if (STUtils.isNotNull(dataVO.getBaseUnitColName())
							&& STUtils.isNotNull(rowSet.getObject(dataVO
									.getBaseUnitColName()))) {
						baseUnitMap.put(new Integer(i), rowSet.getObject(
								dataVO.getBaseUnitColName()).toString());
					}
					if (STUtils.isNotNull(dataVO.getAssistUnitColName())
							&& STUtils.isNotNull(rowSet.getObject(dataVO
									.getAssistUnitColName()))) {
						assistUnitMap.put(new Integer(i), rowSet.getObject(
								dataVO.getAssistUnitColName()).toString());
					}
					i++;

				}
			}
			String billTypeId = null;
			//if(!StringUtils.isEmpty(getSTBizUnitDataVO().getProductTypeColName
			// ())){ // ���ݲ�Ʒ�������
			/**
			 * ����Ҫ��Ʒ��� if(dataVO.getPdTypeFieldNames() != null &&
			 * dataVO.getPdTypeFieldNames().length > 0){ // ���ݲ�Ʒ������� Object o
			 * = createNewData(); if(o instanceof STBillBaseInfo) {
			 * STBillBaseInfo billInfo = (STBillBaseInfo) o; BillTypeInfo
			 * billType = billInfo.getBillType(); if(billType != null){
			 * billTypeId = billType.getId().toString(); Set billTypeIds = new
			 * HashSet(); billTypeIds.add(billTypeId); precisionMap =
			 * ProductAttributeUtils.getPrecision(null, billTypeIds); }else{
			 * billTypeId = dataVO.getBillTypeID(); if(billTypeId != null) { Set
			 * billTypeIds = new HashSet(); billTypeIds.add(billTypeId);
			 * precisionMap = ProductAttributeUtils.getPrecision(null,
			 * billTypeIds); } } } }
			 **/
			rowSet.first();
			rowSet.previous();
			Map multiUnitsPre = QtyMultiMeasureUtils.getMultiUnitsPrecision(
					null, materialMap, baseUnitMap, unitMap, assistUnitMap);

			while (rowSet.next()) {
				if (!StringUtils.isEmpty(dataVO.getMaterialColName())) { // �������Ϻͼ�����λ
																			// ��
																			// ������
					if (STUtils.isNotNull(rowSet.getObject(dataVO
							.getMaterialColName()))) {
						Object o = rowSet
								.getObject(dataVO.getMaterialColName());
						if (STUtils.isNotNull(o)) {
							materialID = o.toString();
						}
					}
					if (STUtils.isNotNull(dataVO.getUnitColName())
							&& STUtils.isNotNull(dataVO.getUnitColName())) {
						Object o = rowSet.getObject(dataVO.getUnitColName());
						if (STUtils.isNotNull(o)) {
							unitID = o.toString();
						}
					}
					if (STUtils.isNotNull(dataVO.getBaseUnitColName())
							&& STUtils.isNotNull(rowSet.getObject(dataVO
									.getBaseUnitColName()))) {
						Object o = rowSet
								.getObject(dataVO.getBaseUnitColName());
						if (STUtils.isNotNull(o)) {
							baseUnitID = o.toString();
						}
					}
					if (STUtils.isNotNull(dataVO.getAssistUnitColName())
							&& STUtils.isNotNull(rowSet
									.getObject(getSTBizUnitDataVO()
											.getAssistUnitColName()))) {
						Object o = rowSet.getObject(getSTBizUnitDataVO()
								.getAssistUnitColName());
						if (STUtils.isNotNull(o)) {
							assistUnitID = o.toString();
						}
					}
					Object o = null;
					// ������λ
					if (STUtils.isNotNull(materialID)
							&& STUtils.isNotNull(unitID)
							&& STUtils.isNotNull(multiUnitsPre)) {
						o = multiUnitsPre.get(materialID + unitID);
						if (STUtils.isNotNull(o)) {
							unitPrecision = new Integer(o.toString())
									.intValue();
						}
					}
					// ����������λ
					if (STUtils.isNotNull(materialID)
							&& STUtils.isNotNull(baseUnitID)
							&& STUtils.isNotNull(multiUnitsPre)) {
						o = multiUnitsPre.get(materialID + baseUnitID);
						if (STUtils.isNotNull(o)) {
							baseUnitPrecision = new Integer(o.toString())
									.intValue();
						}
					}
					// ����������λ
					if (STUtils.isNotNull(materialID)
							&& STUtils.isNotNull(assistUnitID)
							&& STUtils.isNotNull(multiUnitsPre)) {
						o = multiUnitsPre.get(assistUnitID + baseUnitID);
						if (STUtils.isNotNull(o)) {
							assistUnitPrecision = new Integer(o.toString())
									.intValue();
						}
					}
				}
				iniColumnsPrecision(unitPrecision, getSTBizUnitDataVO()
						.getUnitQtyColumns(), rowSet);
				iniColumnsPrecision(baseUnitPrecision, getSTBizUnitDataVO()
						.getBaseUnitQtyColumns(), rowSet);
				iniColumnsPrecision(assistUnitPrecision, getSTBizUnitDataVO()
						.getAssistUnitQtyColumns(), rowSet);
				// i++;
				if (!StringUtils.isEmpty(billTypeId)
						&& dataVO.getPdTypeFieldNames() != null) { // ���ݲ�Ʒ�������
					for (int index = 0; index < dataVO.getPdTypeFieldNames().length; index++) {
						String key = billTypeId
								+ dataVO.getPdTypeFieldNames()[index][0];
						Integer precision = (Integer) precisionMap.get(key);
						if (precision == null) {
							precision = new Integer(0);
						}
						iniColumnsPrecision(precision.intValue(), dataVO
								.getPdTypeFieldNames()[index][0], rowSet);
					}
				}
			}

		} catch (SQLException ex) {
			handUIException(ex);
		} catch (BOSException e) {
			// TODO �Զ����� catch ��
			handUIException(e);
		} catch (Exception e) {
			// TODO �Զ����� catch ��
			handUIException(e);
		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	private void iniColumnsPrecision(int precision, String[] fields,
			IRowSet rowSet) throws SQLException {
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				iniColumnsPrecision(precision, fields[i], rowSet);
			}
		}
	}

	private void iniColumnsPrecision(int precision, String field, IRowSet rowSet)
			throws SQLException {
		BigDecimal temp = rowSet.getBigDecimal(field);
		if (temp != null && temp.compareTo(SysConstant.BIGZERO) != 0) {
			temp = temp.setScale(precision, BigDecimal.ROUND_HALF_UP);
			rowSet.updateObject(field, temp);
		} else if (temp != null && temp.compareTo(SysConstant.BIGZERO) == 0) {
			rowSet.updateObject(field, new BigDecimal("0"));
		}

	}

	public List getFieldSumList() {
		// return new ArrayList();
		return super.getFieldSumList();
	}

	protected void initDapButtons() {
		this.actionVoucher.setVisible(false);
		this.actionDelVoucher.setVisible(false);

	}

	protected void setCloseVisible(boolean isVisible) {
		this.btnClose.setVisible(isVisible);
		this.btnUnClose.setVisible(isVisible);
		this.btnClose.setEnabled(isVisible);
		this.btnUnClose.setEnabled(isVisible);
		this.menuItemClose.setVisible(isVisible);
		this.menuItemUnClose.setVisible(isVisible);
		this.menuItemClose.setEnabled(isVisible);
		this.menuItemUnClose.setEnabled(isVisible);
		this.actionClose.setEnabled(isVisible);
		this.actionUnClose.setEnabled(isVisible);
	}

	// ����Ҫcu���ˣ�����ͨ����Ȩ��ҵ����֯ʵ��
	// colin add 2007-11-19
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * @author xiaofeng_liu ���������¼�������ķ������� ���� Javadoc��
	 * @see com.kingdee.eas.framework.client.AbstractListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK,
	 *      com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		// ǿ������
		// viewInfo.setIngorePreOrders(false);
		// return super.getQueryExecutor(queryPK,viewInfo);
		IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
		// ����������������BTPCreateMode:0��ʾ���ƣ�1��ʾ����
		boolean isCreateFrom = Integer.valueOf("1").equals(
				getUIContext().get("BTPCreateMode"));
		if (isCreateFrom && getBOTPViewStatus() == 1
				&& getUIContext().get("BTPEDITPARAMETER") instanceof HashMap) {
			Object disablePermission = ((HashMap) getUIContext().get(
					"BTPEDITPARAMETER")).get("IGNORE_DATAPERMISSION_CHECK");
			if (disablePermission != null
					&& disablePermission instanceof Boolean) {
				// getUIContext().put("IGNORE_DATAPERMISSION_CHECK",(Boolean)
				// disablePermission);
				exec.option().isIgnorePermissionCheck = ((Boolean) disablePermission)
						.booleanValue();
			}
		}

		if (viewInfo.getSorter() != null && viewInfo.getSorter().size() > 0
				&& !viewInfo.getSorter().toString().equals("")) {
			SorterItemCollection sorters = viewInfo.getSorter();
			boolean hasNumberSorter = false;
			boolean hasSeqSorter = false;

			for (int i = 0; i < sorters.size(); i++) {
				if (sorters.get(i).getPropertyName().equals("number")) {
					hasNumberSorter = true;
					// break;
				}

				if (sorters.get(i).getPropertyName().equals(
						getEntriesName() + ".seq")) {
					hasSeqSorter = true;
					// break;
				}
			}
			if (!hasNumberSorter) {// û�мӱ������򣬼���Ĭ�ϱ��뽵�����С�
				SorterItemInfo aSorterItemInfo = new SorterItemInfo("number");
				aSorterItemInfo.setSortType(SortType.DESCEND);
				viewInfo.getSorter().add(aSorterItemInfo);
			}

			if ((!hasSeqSorter) && getEntriesNameForQuery() != null) {// û�м���seq����
																		// ��
																		// ����Ĭ��seq��������
																		// ��
				SorterItemInfo bSorterItemInfo = new SorterItemInfo(
						getEntriesNameForQuery() + ".seq");
				bSorterItemInfo.setSortType(SortType.ASCEND);
				viewInfo.getSorter().add(bSorterItemInfo);
			}
		} else {
			SorterItemInfo aSorterItemInfo = new SorterItemInfo("number");
			aSorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(aSorterItemInfo);
			if (getEntriesNameForQuery() != null) {
				SorterItemInfo bSorterItemInfo = new SorterItemInfo(
						getEntriesNameForQuery() + ".seq");
				bSorterItemInfo.setSortType(SortType.ASCEND);

				viewInfo.getSorter().add(bSorterItemInfo);
			}
		}

		exec.option().isAutoTranslateEnum = true;

		return exec;
	}

	// ���ط�¼����������(ʵ��������)
	public String getEntriesNameForQuery() {
		QueryFieldCollection fields = getQueryPKFileds();
		if (fields != null && fields.size() == 2) {
			return fields.get(1).getPropertyRefs().get(0).getSubEntity()
					.getName();
		}

		return null;
	}

	private QueryFieldCollection getQueryPKFileds() {
		QueryFieldCollection fields = null;
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		QueryInfo queryInfo = loader.getQuery(this.mainQueryPK);
		QueryPKInfo pkInfo = queryInfo.getQueryPK();
		if (pkInfo != null)
			fields = queryInfo.getQueryPK().getKeyPropertys();
		return fields;
	}

	/**
	 * ������������ҵ����֯List��Ŀǰ���ֻ�����һ�������в�ѯ��������ʱ�����жϲ�ѯ����ֻ�ܰ���һ����ҵ����֯��������UIContext��
	 * ����һ��ʱ����ѯ Լ������ѯ������ʹ�õ���ҵ����֯�����Ĺؼ��ֱ������ҵ����֯������������ͬ��2007-8-8
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.framework.client.ListUI#getMainBizOrgs(com.kingdee.eas.base.commonquery.QuerySolutionInfo)
	 */
	protected List getMainBizOrgs(QuerySolutionInfo solution) {

		if (this.getMainBizOrgType() == null)
			return super.getMainBizOrgs(solution);

		List list = new ArrayList();

		if (solution != null) {
			OrgUnitInfo[] orgUnitInfos = getMainOrgFromSolution(solution);
			// �����ҵ����ֻ֯��һ��ʱ,���õ���������,����,�����в�ѯ
			// Ϊ��Ӧ���屨�����ʱ������֯��Ҫ�ж�����޸� paul 2007-3-6
			if (orgUnitInfos != null && orgUnitInfos.length >= 1) {
				this.getUIContext().put(this.getMainBizOrgType(),
						orgUnitInfos[0]);
				// ����֯�޸� paul 2005-5-25
				// list.add(orgUnitInfos[0].getId().toString());
				for (int i = 0; i < orgUnitInfos.length; i++) {
					if (orgUnitInfos[i] != null) {
						list.add(orgUnitInfos[i].getId().toString());
					}
				}
			} else {
				// ����Context�е���ҵ����֯Ϊnull
				this.getUIContext().put(this.getMainBizOrgType(), null);
				// this.getMainOrgContext().put(this.getMainBizOrgType(), null);
				if (this.getMainOrgContext() != null) {
					FrameWorkUtils.setCurrentOrgUnit(this.getMainOrgContext(),
							null);
				}
			}
		} else {
			if (getUIContext().get(getMainBizOrgType()) != null) {
				list
						.add(((OrgUnitInfo) getUIContext().get(
								getMainBizOrgType())).getId().toString());
			}
		}

		// ������ҵ����֯ί�еĲ�����֯
		// if(getMainBizOrgType() != OrgType.Company &&
		// getUIContext().get(getMainBizOrgType()) != null) {
		// try
		// {
		// OrgUnitCollection collection =
		// SCMGroupClientUtils.getOrgsByRelation(getMainBizOrgType(),
		// OrgType.Company,
		// super.getMainOrgInfo().getId());
		// if(collection != null && collection.size()>0) {
		// getUIContext().put(OrgType.Company, collection.get(0));
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		// }
		return list;
		// return super.getMainBizOrgs(solution);
	}

	protected boolean isAllowDefaultSolutionNull() {

		return false;
	}

	private OrgUnitInfo[] getMainOrgFromSolution(QuerySolutionInfo solution) {
		if (this.getMainBizOrgType() == null)
			return null;

		String key = this.getMainBizOrgType().getName();// getPropertyOfBizOrg(
														// getMainBizOrgType());
		if (solution != null && key != null) {
			QueryPanelCollection queryPanelCollection = solution
					.getQueryPanelInfo();
			int size = queryPanelCollection.size();
			for (int i = 0; i < size; i++) {
				QueryPanelInfo queryPanelInfo = queryPanelCollection.get(i);
				if (queryPanelInfo.getPanelClassName() != null
						&& !queryPanelInfo
								.getPanelClassName()
								.equalsIgnoreCase(
										"com.kingdee.eas.base.commonquery.client.CommonFilterPanel")
						&& !queryPanelInfo
								.getPanelClassName()
								.equalsIgnoreCase(
										"com.kingdee.eas.base.commonquery.client.CommonSorterPanel")) {

					// ��ѯ�������޸ģ�����CustomerParams��ʽ
					String params = queryPanelInfo.getCustomerParams();
					if (params != null) {
						CustomerParams cp = new CustomerParams();
						try {
							ArrayList al = XMLBean.TransStrToAL(params);
							Iterator j = al.iterator();
							XMLBean xb = null;

							while (j.hasNext()) {
								xb = (XMLBean) j.next();
								cp
										.addCustomerParam(xb.getName(), xb
												.getValue());
							}

							String orgs = cp.getCustomerParam(key);
							if (orgs != null) {
								return SCMGroupClientUtils
										.getOrgUnitInfos(orgs);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		return null;
	}

	/**
	 * ���������Ǵ˷���, 1.ȥ��CU���� 2.����BOTP����ʱ����֯���� 2006-8-4 3.�����ϲ��²����֯����
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.framework.client.ListUI#getDefaultFilterForQuery()
	 */
	protected FilterInfo getDefaultFilterForQuery() {
		if (this.getMainBizOrgType() == null) {
			return super.getDefaultFilterForQuery();
		}

		FilterInfo filterInfo = new FilterInfo();

		// ������ʱ�����˵���Ȩ�޵���֯
		FilterInfo filterPerm = getHasPermissionOrgFilterForQuery();
		if (filterPerm != null) {
			filterInfo = filterPerm;
		}
		// ������ʱ������ǰΪ��ҵ����֯ʵ�壬�������ҵ����֯ʵ���С����
		if (getBOTPViewStatus() != 1) {
			OrgUnitInfo ou = SysContext.getSysContext().getCurrentOrgUnit(
					this.getMainBizOrgType());
			if (null != ou && null != ou.get("isBizUnit")) {
				FilterInfo bizOUFilter = new FilterInfo();
				bizOUFilter
						.getFilterItems()
						.add(
								new FilterItemInfo(
										getPropertyOfBizOrg(getMainBizOrgType()),
										ou.getId().toString(),
										CompareType.EQUALS));
				try {
					if (((Boolean) ou.get("isBizUnit")).booleanValue()) {
						filterInfo.mergeFilter(bizOUFilter, "and");
					} else {
						filterInfo.mergeFilter(bizOUFilter, "or");
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}

		return filterInfo;
	}

	protected EntityViewInfo getInitDefaultSolution() {

		return null;

	}

	/**
	 * �����������ϲ��²����֯���� 1)�Ȼ�õ�ǰ�û��Ե�ǰ�����в�ѯȨ�޵�������ҵ����֯��������Щ��֯����һ�����ˣ���orgId in��'a',
	 * 'b', 'c') 2)������һ����Ȩ����֯����mainOrgContext���ڻ��BizInterfaceʱ�����ݹ�ȥ
	 * 
	 * @return
	 * @author:paul ����ʱ�䣺2006-10-23
	 *              <p>
	 */
	private FilterInfo getHasPermissionOrgFilterForQuery() {
		FilterInfo filterInfo = new FilterInfo();
		if (this.getMainBizOrgType() == null)
			return null;
		try {
			/*
			 * ��ȡ��ǰ�û��Ե�ǰ�����в鿴Ȩ�޵���֯���� by yuntao_li 2013-05-16
			 */
			if (authorizedMainOrgs == null) {
				BOSObjectType bosType = getBizInterface().getType();
				ICoreBase iBiz = getBizInterface();
				if (iBiz instanceof ISTBillBase) {
					authorizedMainOrgs = ((ISTBillBase) iBiz)
							.getAuthOrgsByType(this.getMainBizOrgType(),
									bosType);
				}
			}

			OrgUnitInfo newMainOrg = null;
			// ��������ʱ���������,����ȡ��Ȩ��֯ paul 2007-6-15
			/*
			 * ׿��������죬ע�����´��� by hai_zhong if (getBOTPViewStatus() != 1 ) { //add
			 * by wanglh 2007.06.19 Ϊ����ʱ���ֵ���������
			 * if(getUIContext().get("BillMainQuery")!= null){
			 * StorageOrgUnitInfo aStorageOrgUnitInfo =
			 * (StorageOrgUnitInfo)getUIContext().get("BillQueryCompany");
			 * this.getUIContext().put(this.getMainBizOrgType(),
			 * aStorageOrgUnitInfo); if(newMainOrg != null) {
			 * initUIMainOrgContext(aStorageOrgUnitInfo.getId().toString()); } }
			 * //end add by wanglh 2007.06.19 return null; }
			 */

			String key = getPropertyOfBizOrg(this.getMainBizOrgType());
			if (authorizedMainOrgs != null && authorizedMainOrgs.size() > 0) {
				if (key == null) {
					throw new IllegalArgumentException(
							"getMainBizOrgType() should return a correct value.");
				}
				newMainOrg = authorizedMainOrgs.get(0);
				/*
				 * ׿������ע�����´��� by hai_zhong //add by wanglh 2007.06.12
				 * Ϊ����ʱ���ֵ���������
				 * 
				 * if(getUIContext().get("BillMainQuery")!= null){
				 * StorageOrgUnitInfo aStorageOrgUnitInfo =
				 * (StorageOrgUnitInfo)getUIContext().get("BillQueryCompany");
				 * boolean hasThisOrg = false; for(int i=0;
				 * i<authorizedMainOrgs.size(); i++) {
				 * if(authorizedMainOrgs.get(
				 * i).getId().equals(aStorageOrgUnitInfo.getId())){ hasThisOrg =
				 * true; break; } } if(hasThisOrg){ newMainOrg =
				 * aStorageOrgUnitInfo; } Set keys = new LinkedHashSet();
				 * for(int i=0; i<authorizedMainOrgs.size(); i++) {
				 * keys.add(authorizedMainOrgs.get(i).getId().toString()); }
				 * filterInfo.getFilterItems().add( new FilterItemInfo(key,
				 * keys, CompareType.INCLUDE));
				 * this.getUIContext().put(this.getMainBizOrgType(),
				 * newMainOrg); if(newMainOrg != null) {
				 * initUIMainOrgContext(newMainOrg.getId().toString()); } return
				 * filterInfo; } // end add by wanglh 2007.06.12
				 */
				Set keys = new LinkedHashSet();
				for (int i = 0; i < authorizedMainOrgs.size(); i++) {
					keys.add(authorizedMainOrgs.get(i).getId().toString());
				}
				filterInfo.getFilterItems().add(
						new FilterItemInfo(key, keys, CompareType.INCLUDE));
				// ����UIContext����ҵ����֯
				// ֻ����֯�����ĵ���֯��Ȩ��ʱ�Ÿ��� paul 2007-5-29
				/*
				 * ׿��������죬ע�����´��� by hai_zhong OrgUnitInfo mainOrg =
				 * (OrgUnitInfo)
				 * this.getUIContext().get(this.getMainBizOrgType()); if(mainOrg
				 * == null || !keys.contains(mainOrg.getId().toString())) {
				 * if(newMainOrg != null) { //����UIContext����ҵ����֯
				 * this.getUIContext().put(this.getMainBizOrgType(),
				 * newMainOrg); //�л���ҵ����֯
				 * initUIMainOrgContext(newMainOrg.getId().toString()); } }
				 */
			}
			/*
			 * ׿��������죬ע�����´��� by hai_zhong else { //����UIContext����ҵ����֯
			 * this.getUIContext().put(this.getMainBizOrgType(), null); }
			 */
		} catch (Exception e) {
			this.handleException(e);
		}

		return filterInfo;
	}

	/**
	 * ���������ǳ���, ���������ĵ���ҵ����֯ 2006-8-17
	 * 
	 * @author:paul
	 * @see com.kingdee.eas.framework.client.CoreUI#getOrgPK(com.kingdee.bos.ui.face.ItemAction)
	 */
	protected IObjectPK getOrgPK(ItemAction action) {

		if (this.getMainBizOrgType() == null)
			return super.getOrgPK(action);

		IObjectPK pk = null;

		/** Ȩ����֤��ȡ������ҵ����֯ by kehao_huang 2013-5-14 begin **/
		String mainOrgPropertyName = getPropertyOfBizOrg(getMainBizOrgType());
		try {
			if (tblMain != null && mainOrgPropertyName != null
					&& getBizInterface() != null) {
				IObjectPK[] pks = getSelectedListPK();
				if (STQMUtils.isNotNull(pks) && pks.length > 0) {
					ICoreBase ie = getBizInterface();
					SelectorItemCollection sco = new SelectorItemCollection();
					sco.add(new SelectorItemInfo(mainOrgPropertyName));
					STBillBaseInfo stInfo = (STBillBaseInfo) ie.getValue(
							pks[0], sco);
					if (stInfo != null) {
						String[] arr = mainOrgPropertyName.split("\\.");//XXXXX.
																		// id
						CoreBaseInfo orgInfo = (CoreBaseInfo) stInfo
								.get(arr[0]);
						if (orgInfo != null) {
							String keyValue = orgInfo.getId().toString();
							return new ObjectStringPK(keyValue);
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			handleException(e);
		}
		/** Ȩ����֤��ȡ������ҵ����֯ by kehao_huang 2013-5-14 end **/

		if (getMainOrgContext() != null
				&& getMainOrgContext().get(this.getMainBizOrgType()) != null) {
			pk = new ObjectStringPK(((OrgUnitInfo) (getMainOrgContext()
					.get(this.getMainBizOrgType()))).getId().toString());

			return pk;
		} else {
			return super.getOrgPK(action);
		}
	}

	/**
	 * ������ȡ��Ȩ�ޡ���������BOTP״̬��Action״̬��ˢ�£�SCM��ҵ����У��Ϸ��ԣ�ͬʱ�������ܡ�
	 * 
	 * @author:paul Date��2007-5-22
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#registerActionStateProvider()
	 */
	protected void registerActionStateProvider() {
		if (this.getMainBizOrgType() == null)
			super.registerActionStateProvider();
	}

	/**
	 * �������������������ö���ҵ����ֵ֯����������Ȩ�޺��ֶε�У��
	 * 
	 * @author:paul Date��2007-5-24
	 * @see com.kingdee.eas.framework.client.ListUI#initMainBizOrg(java.util.List)
	 */
	protected void initMainBizOrg(List l) {

		super.initMainBizOrg(l);

		if (this.getMainBizOrgType() != null
				&& this.getMainOrgContext() != null) {
			MutiOrgPermParam mutiOrgParam = new MutiOrgPermParam();

			mutiOrgParam.setOrgType(this.getMainBizOrgType());

			mutiOrgParam.setOrgIdList(l);
			this.getMainOrgContext().put(PermissionConstant.MUTI_ORG_INFO,
					mutiOrgParam);
		}
	}

	/**
	 * ���������෵�ذ���֯���͵���������
	 * 
	 * @param orgType
	 * @return
	 * @author:paul ����ʱ�䣺2006-8-4
	 *              <p>
	 */
	protected String getPropertyOfBizOrg(OrgType orgType) {
		return null;
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

	public String[] getMergeColumnKeys() {

		String[] mergeKeys = new String[] {};
		List fieldLs = new ArrayList();
		try {
			QueryFieldCollection fieldCollection = QueryUtil4Client
					.getHeadSelectorCollection(getQueryInfo(mainQueryPK));
			if (fieldCollection != null) {
				for (int index = 0; index < fieldCollection.size(); index++) {
					QueryFieldInfo fieldInfo = fieldCollection.get(index);
					String fieldName = fieldInfo.getName();
					if (tblMain.getColumnIndex(fieldName) >= 0) {
						fieldLs.add(fieldName);
					}
				}
			}
			mergeKeys = (String[]) fieldLs.toArray(new String[] {});
		} catch (Exception e) {
			logger.error(e);
		}

		return mergeKeys;
	}

	public String[] getMergeColumnKeys_ST() {
		return new String[] { TB_CREATOR, TB_CREATETIME, TB_UPDATOR,
				TB_UPDATETIME, TB_AUDITOR, TB_AUDITIME };
	}

	/**
	 * ���ذ��ֶμ��������д��� �簴���ݡ�id�����㵥�����ݡ�
	 * 
	 * @return
	 */
	protected String[] getCountQueryFields() {
		// ����Ƶ����ť���Ǳ���������
		return new String[] { "id" };

	}

	/**
	 * ����getBotpFilter�������ַ�����Ҫ������������ʾ��ui��title��
	 * ��������Ҫ��getBotpFilter����һ�£���������Ҫ�����ı�ʾ
	 * 
	 * @return
	 */
	public String getBotpSrcFilterDesc() {
		return null;
	}

	// �����ദ�������ʱ����
	public void doAgreeAlter(IObjectPK pk) throws Exception {

	}

	/**
	 * ��ȡĬ�Ϸ����ı��������Ϣ��
	 * 
	 * @param iQuery
	 * @param queryName
	 * @throws Exception
	 */
	protected void doDefaultSolution(IQuerySolutionFacade iQuery,
			String queryName) throws Exception {
		super.doDefaultSolution(iQuery, queryName);

		QuerySolutionInfo querySolution = super.getQuerySolutionInfo();
		EntityViewInfo ev;
		if (querySolution != null && querySolution.getEntityViewInfo() != null) {
			ev = Util.getInnerFilterInfo(querySolution);
			replaceBizTypeFilter(ev);
			querySolution.setEntityViewInfo(ev.toString());
		}
	}

	protected void setDefaultEntityViewInfo(EntityViewInfo entryViewInfo) {
		replaceBizTypeFilter(entryViewInfo);

		super.setDefaultEntityViewInfo(entryViewInfo);
	}

	// �滻Ĭ�ϲ�ѯ�����е�ҵ������
	protected void replaceBizTypeFilter(EntityViewInfo entryViewInfo) {

	}

	public void beforeTransform(IObjectCollection srcObjCols,
			String destBillBosType) {
		if (isBotpWholeBill() && srcObjCols != null && srcObjCols.size() >= 0) {
			Object o = srcObjCols.getObject(0);
			if (o instanceof IObjectValue) {
				IObjectValue info = (IObjectValue) o;
				String id = info.get("id").toString();
				if (!StringUtils.isEmpty(id)) {
					try {
						BOSObjectType bosType = BOSUuid.read(id).getType();
						IObjectValue newInfo = DynamicObjectFactory
								.getRemoteInstance().getValue(bosType,
										new ObjectStringPK(id),
										getBOTPSelectors());
						srcObjCols.clear();
						srcObjCols.addObject(newInfo);
					} catch (BOSException e) {
						super.handUIException(e);
					}
				}
			}
		}
	}

	// �Ƿ�������BOTP
	public boolean isBotpWholeBill() {
		return false;
	}

	public void setPrecisionManager(PrecisionManager pm) {
		precisionManager = pm;
	}

	public PrecisionManager getPrecisionManager() {
		return precisionManager;
	}

	protected void execQuery() {
		super.execQuery();

		if (precisionManager != null) {
			precisionManager.dealPrecision();
		}
		if (getFieldSumArray() != null)
			try {
				IQueryExecutor iexec = getQueryExecutor(this.mainQueryPK,
						getEntityViewInfo(mainQuery));
				appendFootRow(iexec);
			} catch (BOSException e) {
				e.printStackTrace();
			}

	}

	/**
	 * �ϼ���
	 * 
	 * @return
	 */
	public String[] getFieldSumArray() {
		return null;
	}

	protected IRow appendFootRow(IQueryExecutor iexec) {
		if (!(isFootVisible()))
			return null;
		Object footVisible = getUIContext().get("SumVisible");
		if ((footVisible != null)
				&& (!(Boolean.valueOf(footVisible.toString()).booleanValue())))
			return null;
		try {
			String[] fieldSumArray = getFieldSumArray();
			if (fieldSumArray.length > 0) {
				IRowSet singleRowSet = iexec.sum(fieldSumArray);
				if (singleRowSet == null)
					return null;
				singleRowSet.next();
				IRow footRow = null;
				KDTFootManager footRowManager = tblMain.getFootManager();
				if (footRowManager == null) {
					String total = EASResource
							.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
					footRowManager = new KDTFootManager(this.tblMain);
					footRowManager.addFootView();
					this.tblMain.setFootManager(footRowManager);
					footRow = footRowManager.addFootRow(0);
					footRow.getStyleAttributes().setHorizontalAlign(
							Styles.HorizontalAlignment.getAlignment("right"));
					this.tblMain.getIndexColumn().setWidthAdjustMode((short) 1);
					this.tblMain.getIndexColumn().setWidth(30);
					footRowManager.addIndexText(0, total);
				} else {
					footRow = footRowManager.getFootRow(0);
				}
				String colFormat = "%{0.######}f";
				int columnCount = this.tblMain.getColumnCount();
				for (int c = 0; c < columnCount; ++c) {
					String fieldName = this.tblMain.getColumn(c).getFieldName();
					for (int i = 0; i < fieldSumArray.length; ++i) {
						String name = fieldSumArray[i];
						if (name.equalsIgnoreCase(fieldName)) {
							ICell cell = footRow.getCell(c);
							cell.getStyleAttributes()
									.setNumberFormat(colFormat);
							cell.getStyleAttributes().setHorizontalAlign(
									Styles.HorizontalAlignment
											.getAlignment("right"));
							cell.getStyleAttributes().setFontColor(Color.BLACK);
							cell.setValue(singleRowSet.getBigDecimal(name));
						}
					}
				}
				footRow.getStyleAttributes().setBackground(
						new Color(246, 246, 191));
				return footRow;
			}
		} catch (Exception E) {
			logger.error(E);
		}
		return null;
	}

	public void onShow() throws Exception {
		super.onShow();

		if (precisionManager != null) {
			precisionManager.dealPrecision();
		}
	}

	public boolean isAutoIgnoreZero() {
		return false;
	}
}