package com.kingdee.eas.st.common.util;

import java.util.Observable;
import java.util.Observer;

import com.kingdee.bos.ctrl.kdf.table.IRows;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.st.common.client.STBillBaseEditUI;
import com.kingdee.eas.st.common.util.KDTableEditObservable;

/**
 * 处理EditUI内分录合并
 * 
 * @author jiwei_xiao
 * 
 */
public class MergeEntryInEditUI implements IMergeKDTable, Observer {

	protected STBillBaseEditUI editUI = null;
	protected KDTable table = null;
	protected String[] mergeColumnKeys = null;
	protected Observable observable = null;
	// 进行合并的条件列条件
	protected String[] mergeByColKey = null;
	/**
	 * 是否开启合并
	 */
	private boolean isMergeEnabled = true;
	private boolean isRealtimeMerge = false;
	private boolean isSynchronizeEntry = false;

	private IRows rows = null;

	/**
	 * 在loadField之后初始化
	 * 
	 * @param mergeColmun
	 *            合并列
	 * @param table
	 *            分录
	 */
	public MergeEntryInEditUI(Observable observable, KDTable table,
			String[] mergeColmun, String[] mergeByColKey) {
		super();
		this.mergeColumnKeys = mergeColmun;
		this.table = table;
		this.observable = observable;
		this.mergeByColKey = mergeByColKey;
		setEditUIProperties();
		observable.addObserver(this);
	}

	public void setEditUIProperties() {
		if (isMergeEnabled) {
			table.getMergeManager().setMergeMode(
					KDTDataRequestManager.REAL_MODE);
			table.getMergeManager().setDataMode(KDTMergeManager.DATA_UNIFY);
			table.checkParsed();

			if (mergeColumnKeys != null && mergeColumnKeys.length > 0) {
				// table.getGroupManager().setGroup(isMergeEnabled);
				for (int i = 0; i < mergeColumnKeys.length; i++) {
					table.getColumn(mergeColumnKeys[i])
							.setGroup(isMergeEnabled);
					table.getColumn(mergeColumnKeys[i]).setMergeable(
							isMergeEnabled);
				}
			}
			if (mergeByColKey == null) {
				table.getGroupManager().setGroup(isMergeEnabled);
			}
		}
	}

	/**
	 * 执行合并动作
	 */
	public void doMerge() {
		// 再次合并，防止出现前一后一错误
		/** table.getGroupManager().group();这个方法会造成大量的内存消耗，甚至死机，尽量避免执行 */
		// if(isRealtimeMerge){
		// table.getMergeManager().getMergeBlockSet().clear();
		// 同步数据,确保合并中进行合并的列数据完全相同
		if (isSynchronizeEntry) {
			synchronizeMergeInfo();
		}
		if (isRealtimeMerge) {
			groupPart();
		}
	}

	public void groupPart() {
		if (mergeByColKey == null) {
			table.getGroupManager().group();
		} else {
			int rowIndex = table.getSelectManager().getActiveRowIndex();
			int rowIndexCount = table.getRowCount();
			int rowCursor = -1;

			if (rowIndex < 0) { // 说明是第一次合并分录，要合并合并
				rowCursor = 0; // 定义游标为合并top
				while (rowCursor < rowIndexCount) {
					int bottom = getMergePartBottom(table, rowCursor);
					if (rowCursor <= bottom) {
						table.getGroupManager().group(rowCursor, bottom);
						table.getGroupManager().removeGroup();
					}
					rowCursor = bottom + 1;
				}
			} else {
				rowCursor = rowIndex;
				int top = getMergePartTop(table, rowCursor);
				int bottom = getMergePartBottom(table, rowCursor);
				if (rowCursor >= top && rowCursor <= bottom) {
					table.getGroupManager().group(top, bottom);
					table.getGroupManager().removeGroup();
				}
			}
		}
		table.getMergeManager().getMergeBlockSet().clear();
		table.getGroupManager().removeGroup();
	}

	// 返回合并top
	private int getMergePartTop(KDTable table, int rowCursor) {
		rows = table.getBody();
		KDTRow row1 = rows.getRow(rowCursor);
		int rowCursor2 = rowCursor - 1;
		for (; rowCursor2 > -1; rowCursor2--) {
			KDTRow row2 = rows.getRow(rowCursor2);
			for (int j = 0; j < mergeByColKey.length; j++) {
				KDTCell cell1 = row1.getCell(table
						.getColumnIndex(mergeByColKey[j]));
				KDTCell cell2 = row2.getCell(table
						.getColumnIndex(mergeByColKey[j]));
				if (!rows.isCellEquals(cell1, cell2)) {
					// if(STQMUtils.isDiffrent(cell1.getValue(),
					// cell2.getValue())){
					return rowCursor2 + 1;
				}
			}
		}
		return rowCursor2 + 1;
	}

	// 返回合并的bottom,这里必须要跳过所在行合并的分录，不然会出错
	private int getMergePartBottom(KDTable table, int rowCursor) {
		rows = table.getBody();
		int rowIndexCount = table.getRowCount();
		KDTRow row1 = rows.getRow(rowCursor);
		int rowCursor2 = rowCursor + 1;
		// if(jumpMyMerge){
		// KDTMergeBlock mergeBolock =
		// table.getMergeManager().getMergeBlockOfCell(
		// table.getSelectManager().getActiveRowIndex(),
		// table.getSelectManager().getActiveColumnIndex());
		// if(mergeBolock!=null){
		// rowCursor2 = mergeBolock.getBottom()+1;
		// }
		// }
		for (; rowCursor2 < rowIndexCount; rowCursor2++) {
			KDTRow row2 = rows.getRow(rowCursor2);
			for (int j = 0; j < mergeByColKey.length; j++) {
				KDTCell cell1 = row1.getCell(table
						.getColumnIndex(mergeByColKey[j]));
				KDTCell cell2 = row2.getCell(table
						.getColumnIndex(mergeByColKey[j]));
				if (!rows.isCellEquals(cell1, cell2)) {
					// if(STQMUtils.isDiffrent(cell1.getValue(),
					// cell2.getValue())){
					return rowCursor2 - 1;
				}
			}
		}
		return rowCursor2 - 1;
	}

	public void synchronizeMergeInfo() {
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		int colIndex = table.getSelectManager().getActiveColumnIndex();
		if (table.getColumn(colIndex).isGroup()) {
			KDTMergeBlock mergeBolock = table.getMergeManager()
					.getMergeBlockOfCell(rowIndex, colIndex);
			if (mergeBolock != null) {
				int MergeNum = mergeBolock.getBottom();
				Object obj = table.getCell(rowIndex, colIndex).getValue();
				for (int i = rowIndex + 1; i <= MergeNum; i++) {
					table.getCell(i, colIndex).setValue(obj);
				}
			}
		}
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof KDTableEditObservable) {
			isRealtimeMerge = ((KDTableEditObservable) arg0).isAutoMerge();
			isSynchronizeEntry = ((KDTableEditObservable) arg0)
					.isAutoSynEntry();
			doMerge();
		}
	}

	public void setIsMergeEnabled(boolean bl) {
		isMergeEnabled = bl;
		setEditUIProperties();
	}

}
