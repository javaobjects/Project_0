/*
 * @(#)STBaseDataListUI.java
 *
 * ������׿���Ƽ����޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.query.QueryFieldCollection;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.util.QueryUtil4Client;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @author haizi date: 2017-7-11 <br />
 * 
 */
public class STBaseDataListUI extends AbstractSTBaseDataListUI {

	private static final Logger logger = CoreUIObject
			.getLogger(STBaseDataListUI.class);
	protected ListUiHelper listUiHelper = new ListUiHelper();

	public STBaseDataListUI() throws Exception {
		super();
	}

	@Override
	public void onLoad() throws Exception {
		setMergeColumn();// �ϲ���ͷ

		super.onLoad();
	}

	@Override
	protected void initWorkButton() {
		super.initWorkButton();

		if (isNeedImportAndExport()) {
			actionImportData.setVisible(true);
			actionImportData.setEnabled(true);
			actionExportData.setVisible(true);
			actionExportData.setEnabled(true);
		}
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	@Override
	protected String getEditUIModal() {
		return UIModelDialogFactory.class.getName();
	}

	@Override
	protected String getEditUIName() {
		return null;
	}

	@Override
	protected String getControlType() {
		String controlType = super.getControlType();

		if (controlType == null || "".equals(controlType)) {
			MsgBox.showWarning(this, "���ʵ����ӿ��Ʋ��ԣ�");
			SysUtil.abort();
		}

		return controlType;
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String cancelMsg = EASResource
				.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Cancel");
		if (!(confirmDialog(cancelMsg)))
			return;
		if (UtilRequest.isPrepare("ActionCancel", this)) {
			prepareCancel(null).callHandler();
		}
		cancel();

		refreshList();
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String cancelCancelMsg = EASResource
				.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_CancelCancel");
		if (!(confirmDialog(cancelCancelMsg)))
			return;
		if (UtilRequest.isPrepare("ActionCancelCancel", this)) {
			prepareCancel(null).callHandler();
		}
		cancelCancel();

		refreshList();
	}

	@Override
	public void initTableListner(KDTable table) {
		super.initTableListner(table);

		table.addKDTSelectListener(new KDTSelectListener() {
			public void tableSelectChanged(KDTSelectEvent e) {
				int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
				List selectSet = new ArrayList();
				for (int i = 0; i < selectRows.length; ++i) {
					String cellValue = listUiHelper.getTableCellValue(tblMain,
							selectRows[i], "isEnabled");
					if ((cellValue != null) && (!(cellValue.equals("")))) {
						selectSet.add(cellValue);
					}
				}
				if (selectSet.size() == 1
						&& !Boolean.parseBoolean(selectSet.get(0).toString())) {
					actionEdit.setEnabled(true);
				} else {
					actionEdit.setEnabled(false);
				}
			}
		});
	}

	/**
	 * �Ƿ���Ҫ���뵼�����ܣ�Ĭ��Ϊ��
	 * 
	 * @return
	 */
	protected boolean isNeedImportAndExport() {
		return false;
	}

	/**
	 * ���ط������룬������ȡ�������һ�����Ϊ��������ģ����룬�����ڲ�ѯ���ǵ�һ����������������ģ�塣
	 * 
	 * @return ���뵼��ģ�����
	 * @throws EASBizException
	 */
	protected String getImportExportSolutionNumber() {

		MsgBox.showWarning(this,
				"����дgetImportExportSolutionNumber���������ص��뵼��ģ����룡");
		return "";
		// return "com.kingdee.eas.custom.CustomerArchives";
	}

	@Override
	protected ArrayList getImportParam() {
		DatataskParameter param = new DatataskParameter();
		String solutionName = getImportExportSolutionNumber();
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	@Override
	protected ArrayList getExportParam() {
		DatataskParameter param = new DatataskParameter();

		String solutionName = getImportExportSolutionNumber();
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
	}

	/**
	 * �Ƿ���Ҫ�ϲ���ͷ
	 * 
	 * @return
	 */
	protected boolean isNeedMergeHeader() {
		return true;
	}

	private void setMergeColumn() {
		if (!isNeedMergeHeader())
			return;

		String[] mergeColumnKeys = getMergeColumnKeys();
		if ((mergeColumnKeys == null) || (mergeColumnKeys.length <= 0))
			return;
		this.tblMain.checkParsed();
		this.tblMain.getGroupManager().setGroup(true);
		IColumn col = null;
		for (int i = 0; i < mergeColumnKeys.length; ++i) {
			col = this.tblMain.getColumn(mergeColumnKeys[i]);
			if (col != null) {
				col.setGroup(true);
				col.setMergeable(true);
			} else {
				logger.info("mergeColumn no found:" + mergeColumnKeys[i]);
			}
		}
	}

	/**
	 * 
	 * @return ��ͷ����
	 */
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
}
