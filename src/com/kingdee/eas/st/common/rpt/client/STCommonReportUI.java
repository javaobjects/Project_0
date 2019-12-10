/**
 * output package name
 */
package com.kingdee.eas.st.common.rpt.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.uiframe.client.NewMainFrame;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.DefaultKDTableInsertHandler;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public abstract class STCommonReportUI extends AbstractSTCommonReportUI {

	private static final Logger logger = CoreUIObject
			.getLogger(STCommonReportUI.class);

	protected static final String total1 = EASResource
			.getString("com.kingdee.eas.st.common.STResource.ReportTotal1"); // С��
	protected static final String total2 = EASResource
			.getString("com.kingdee.eas.st.common.STResource.ReportTotal2"); // �ϼ�
	protected static final String total3 = EASResource
			.getString("com.kingdee.eas.st.common.STResource.ReportTotal3"); // �ܼ�

	public static final Color COLOR1 = Color.decode("#E8E4CB"); // С��

	public static final Color COLOR2 = Color.decode("#E9E2B8"); // �ϼ�

	public static final Color COLOR3 = Color.decode("#F6F6BF"); // �ܼ�

	private static final Icon WAIT_ICON = EASResource
			.getIcon("imgPic_wait_sandglass");

	/**
	 * output class constructor
	 */
	public STCommonReportUI() throws Exception {
		super();
		// ������ģʽ��

		this.tblMain.checkParsed();
		// this.tblMain.getViewManager().setFreezeView(0,2);
		this.tblMain.getDataRequestManager().addDataRequestListener(this);
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		// this.tblMain.getDataRequestManager().setPageRowCount(200);
		enableExportExcel(this.tblMain);

		// ���ڿ�ݼ�����,���ؼ̳еĲ�ѯ�˵�,����
		this.menuItemQuery.setEnabled(false);
		this.menuItemQuery.setVisible(false);
	}

	/**
	 * �����л�����(BorderLayout->KdLayout)��������Ľ�����ֿؼ��ص�������<br>
	 * (�˴����ε�����Ĵ��󣬲��ı�ԭ����ʵ�ַ�ʽ)--yangyong <br>
	 * 
	 * @return
	 */
	protected boolean isNeedChangeLayout() {
		return true;
	}

	public void onShow() throws Exception {
		// TODO �Զ����ɷ������
		super.onShow();
		if (isNeedChangeLayout()) {
			this.setBounds(new Rectangle(0, 0, 1013, 629));
			this.setLayout(new KDLayout());
			this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013,
					629));
			this.add(tblMain, new KDLayout.Constraints(10, 10, 993, 609,
					KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_LEFT
							| KDLayout.Constraints.ANCHOR_RIGHT
							| KDLayout.Constraints.ANCHOR_BOTTOM));
		}
	}

	protected abstract ICommRptBase getRemoteInstance() throws BOSException;

	protected KDTable getTableForPrintSetting() {
		return this.tblMain;
	}

	protected RptParams getParamsForInit() {
		// ���õ��÷���˳�ʼ�����ĳ�ʼ����
		RptParams initParam = new RptParams();
		return initParam;
	}

	protected abstract CommRptBaseConditionUI getQueryDialogUserPanel()
			throws Exception;

	protected void setQueryingActionStatus(boolean enable) {
		this.actionRefresh.setEnabled(enable);
		this.actionQuery.setEnabled(enable);
		this.actionPrint.setEnabled(enable);
		this.actionPrintPreview.setEnabled(enable);
	}

	private void setTitleStatus(boolean enabled) {
		NewMainFrame frame = getMainFrame();
		if ((frame != null) && (frame.getMainUI() != null)
				&& (frame.getMainUI().getBodyUI() != null)) {
			Component[] comps = frame.getMainUI().getBodyUI().getComponents();
			for (int i = 0; i < comps.length; ++i) {
				if (comps[i] instanceof KDTabbedPane) {
					KDTabbedPane tabPane = (KDTabbedPane) comps[i];
					int index = tabPane.indexOfComponent(this);
					if (index != -1) {
						tabPane.setDisabledIconAt(index, WAIT_ICON);
						tabPane.setEnabledAt(index, enabled);
						return;
					}
				}
			}
		}
	}

	private NewMainFrame getMainFrame() {
		Object owner = getUIContext().get("Owner");
		NewMainFrame frame = null;
		if (owner instanceof NewMainFrame) {
			frame = (NewMainFrame) owner;
		} else {
			do {
				if (!(owner instanceof CoreUIObject))
					break;
				owner = ((CoreUIObject) owner).getUIContext().get("Owner");
			} while (!(owner instanceof NewMainFrame));
			frame = (NewMainFrame) owner;

		}

		return frame;
	}

	protected void query() {
		this.tblMain.removeColumns();
		this.tblMain.removeRows();// ����ȡ��

		// this.tblMain.removeAll();
		initUserConfig();
	}

	@Override
	public void getData(KDTDataRequestEvent e) {
		tblMain.getDataRequestManager().removeDataRequestListener(this);

		try {
			int from = e.getFirstRow();
			int len = e.getLastRow() - from + 1;
			RptParams pp = new RptParams();

			if (from == 0) {
				// ��ʱ��/��ͷ
				pp.setString("tempTable", getTempTable());
				pp.setString("cuId", SysContext.getSysContext()
						.getCurrentCtrlUnit().getId().toString());
				// ����conditionPanel�е���������pp
				pp.putAll(params.toMap());
				RptParams rpt = getRemoteInstance().createTempTable(pp);
				setTempTable(rpt.getString("tempTable"));
				RptTableHeader header = (RptTableHeader) rpt
						.getObject("header");
				// ���ݻ�����Ϣ���Ÿ��б�ͷ
				arrangeColumn(header);
				KDTableUtil.setHeader(header, tblMain);
				tblMain.setRowCount(rpt.getInt("verticalCount"));
			}
			// ��ѯ���������
			pp.setString("tempTable", getTempTable());
			pp.setString("cuId", SysContext.getSysContext()
					.getCurrentCtrlUnit().getId().toString());
			pp.putAll(params.toMap());
			RptParams rpt = getRemoteInstance().query(pp, from, len);
			RptRowSet rs = (RptRowSet) rpt.getObject("rowset");
			rs = _arrangeRowSetColumnName(rs);
			KDTableUtil.insertRows(rs, from, tblMain,
					new MyKDTableInsertHandler(rs));

			decorateTable(tblMain);
			// ����table��ʽ
			// setDefaultStyleOfTable(tblMain);
			// �ϲ���������...
			setMergeColumn();
		} catch (Exception ex) {
			handleException(ex);
		}

		tblMain.getDataRequestManager().addDataRequestListener(this);
	}

	@Override
	public boolean isAsynchronism() {
		return false;
	}

	public void tableDataRequest(final KDTDataRequestEvent e) {
		super.tableDataRequest(e);
	}

	// ���ݻ�������,�����������������������ݵ������ݼ�
	private RptRowSet _arrangeRowSetColumnName(RptRowSet rs) {
		String[] columnNames = rs.getColumnNames();
		// arrangeColumnName(columnNames);
		arrangeRowSetColumn(columnNames);
		Object[][] data = rs.toArray();
		for (int i = 0; i < data.length; i++) {
			arrangeRowSetColumn(data[i]);
		}
		RptRowSet rowSet = new RptRowSet(columnNames, data);
		return rowSet;
	}

	// �������ݼ���������,ע������������ͬ�ĵط�������,�ֱ������������ݼ������������ݼ�������
	protected void arrangeRowSetColumn(Object[] rowData) {
		Object[] columnNames = rowData;
		List gBCL = getGroupByColumnNameList();
		if (gBCL == null) {
			return;
		}
		Iterator ite = gBCL.iterator();
		// int i=1;//�ǵôӵ�һ�п�ʼ,��Ϊ��0��Ϊgroupbyorderֵ
		int destColIndex = 1;
		while (ite.hasNext()) {
			int index = getIndexInRawSqlColumnNames(ite.next().toString());
			if (index >= 0) {
				int srcColIndex = index + 1;
				Object temp = columnNames[srcColIndex];
				columnNames[srcColIndex] = columnNames[destColIndex];
				columnNames[destColIndex] = temp;
			}
			destColIndex++;
		}
	}

	/**
	 * ����tblMain����ı��п�
	 */
	protected abstract void decorateTable(KDTable tblMain);

	/**
	 * ����KDTable��Style
	 * 
	 * @author zhiwei_wang
	 * @date 2008-7-4
	 * @param tblMain
	 */
	protected void setDefaultStyleOfTable(KDTable tblMain) {
		if (tblMain != null && tblMain.getBody().getRows().size() > 0) {
			for (int i = 0; i < tblMain.getColumnCount(); i++) {
				if (null == tblMain.getBody().getCell(0, i)) {
					continue;
				}

				Object o = tblMain.getBody().getCell(0, i).getValue();
				// �������ֵ���ֶΣ������ж���
				if (o instanceof Number) {
					tblMain.getColumn(i).getStyleAttributes()
							.setHorizontalAlign(HorizontalAlignment.RIGHT);
				}
			}
		}
	}

	class MyKDTableInsertHandler extends DefaultKDTableInsertHandler {

		public MyKDTableInsertHandler(RptRowSet rs) {
			super(rs);
		}

		/**
		 * ����������: 1)��Ԫ������ row.getCell(i).setValue(rowData[i]); 2)�б�����ɫ
		 * row.getStyleAttributes().setBackground(#Color); 3)�ж���
		 * row.setUserObject(#row_object)
		 */
		public void setTableRowData(IRow row, Object[] rowData) {
			if (rowData.length == 0)
				return;
			// //�����������ݼ��и�������
			// arrangeRowSetColumn(rowData);
			// �����ⲿ��

			// �������ײ���������ע�������ֱ��Σ�չ������
			if (isNeedTranslate(rowData)) {
				int columnIndex = -1;
				Map map = getColumnValueFormatterMap();
				if (map != null) {
					Iterator ite = map.keySet().iterator();
					while (ite.hasNext()) {
						Object col = ite.next();
						columnIndex = KDTableUtils.getColumnIndex(tblMain, col
								.toString());
						if (columnIndex > 0) {
							rowData[columnIndex] = ((IValueFormatter) map
									.get(col)).format(rowData[columnIndex]);
						}
					}
				}
			}
			super.setTableRowData(row, rowData);
		}
	}

	/**
	 * 
	 * �������Ƿ���Ҫ����ö��, Boolean��.
	 * 
	 * @param rowData
	 *            ������
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-4-17
	 *              <p>
	 */
	protected boolean isNeedTranslate(Object[] rowData) {
		return true;
	}

	protected abstract Map getColumnValueFormatterMap();

	// ���ز�����
	protected RptParams getParams() {
		return this.params;
	}

	// �õ����������б�,��һ����ʾ��������һ,�ڶ�����ʾ�������ݶ�
	protected List getGroupByColumnNameList() {
		return null;
	}

	/**
	 * ���Ӷ��ڵ���ͷ�ı���ں�����
	 */
	protected void setMergeColumn() {
		List groupByColNameList = getGroupByColumnNameList();
		if (groupByColNameList == null) {
			return;
		}
		// ���Ӷ��ڵ���ͷ�ı���ں�����
		tblMain.checkParsed();
		tblMain.getGroupManager().setGroup(true);

		Iterator ite = groupByColNameList.iterator();
		int i = 1;// �ǵôӵ�һ�п�ʼ,��Ϊ��0��Ϊ
		while (ite.hasNext()) {
			tblMain.getColumn(i).setGroup(true);
			tblMain.getColumn(i).setMergeable(true);
			ite.next();
			i++;
		}
	}

	// ��������header����
	protected void arrangeColumn(RptTableHeader header) {
		// ��ͷ����
		Object[][] data = header.getLabels();

		RptTableColumn srcColumn = null;

		List groupByColNameList = getGroupByColumnNameList();
		if (groupByColNameList == null) {
			return;
		}
		Iterator ite = groupByColNameList.iterator();
		// int i=1;//�ǵôӵ�һ�п�ʼ,��Ϊ��0��Ϊgroupbyorderֵ
		int destColIndex = 1;
		while (ite.hasNext()) {
			String colName = ite.next().toString();
			int index = getIndexInRawSqlColumnNames(colName);
			if (index >= 0) {
				int srcColIndex = index + 1;
				for (int i = 0; i < data.length; i++) {
					Object temp = data[0][srcColIndex];
					data[i][srcColIndex] = data[0][destColIndex];
					data[i][destColIndex] = temp;

					// ����Ҫ��column
					srcColumn = header.getColumn(srcColIndex);
					header.setColumn(srcColIndex, header
							.getColumn(destColIndex));
					header.setColumn(destColIndex, srcColumn);
				}
			}
			destColIndex++;
		}
		header.setLabels(data);
	}

	protected abstract int getIndexInRawSqlColumnNames(String colName);

	public Object getTablePreferenceSchemaKey() {
		return queryDialog.getCurrentSolutionInfo().getId();
	}

}