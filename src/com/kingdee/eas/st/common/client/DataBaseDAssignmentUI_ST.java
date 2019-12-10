package com.kingdee.eas.st.common.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.framework.client.DataBaseDAssignmentUI;

public class DataBaseDAssignmentUI_ST extends DataBaseDAssignmentUI {
	private static final long serialVersionUID = 1315719235561760297L;

	public DataBaseDAssignmentUI_ST() throws Exception {
		super();
	}

	public void onLoad() throws Exception {

		super.onLoad();
		if (getMergeColumnKeys() != null && getMergeColumnKeys().length != 0) {
			setMergeColumn();
			tblData.addKDTDataFillListener(new KDTDataFillListener() {
				public void afterDataFill(KDTDataRequestEvent e) {
					mergerColumSelect();
				}
			});
		}
	}

	private void setMergeColumn() {
		String mergeColumnKeys[] = getMergeColumnKeys();
		if (mergeColumnKeys != null && mergeColumnKeys.length > 0) {
			tblData.checkParsed();
			tblData.getGroupManager().setGroup(true);
			tblData.getDataRequestManager().setDataRequestMode(
					KDTDataRequestManager.REAL_MODE);
			for (int i = 0; i < mergeColumnKeys.length; i++) {
				tblData.getColumn(mergeColumnKeys[i]).setGroup(true);
				tblData.getColumn(mergeColumnKeys[i]).setMergeable(true);
			}
		}
	}

	private void mergerColumSelect() {
		int from = 0;
		String groupColumn = getMergerGroupByColumn();
		for (int i = 1; i < tblData.getRowCount(); i++) {
			if (!tblData.getCell(from, groupColumn).getValue().equals(
					tblData.getCell(i, groupColumn).getValue())) {
				tblData.getMergeManager().mergeBlock(from, 0, i - 1, 0);
				from = i;
			}
		}
		tblData.getMergeManager().mergeBlock(from, 0,
				tblData.getRowCount() - 1, 0);
	}

	protected FilterInfo getDefaultDataFilter() {
		FilterInfo filter = super.getDefaultDataFilter();

		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(
				new FilterItemInfo("isAudited", new Integer(1),
						CompareType.EQUALS));

		try {
			filter.mergeFilter(filter2, "AND");
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return filter;
	}

	/**
	 * 设置需合并的列
	 * 
	 * @return
	 */
	public String[] getMergeColumnKeys() {
		return new String[] {};
	}

	/**
	 * 设置选取列进行分组所依赖的列
	 * 
	 * @return
	 */
	public String getMergerGroupByColumn() {
		return new String("number");
	}
}
