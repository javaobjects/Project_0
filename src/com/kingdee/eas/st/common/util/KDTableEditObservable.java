package com.kingdee.eas.st.common.util;

import java.util.Observable;

/**
 * 分录添加此监听，实现合并分录行功能
 * 
 * @author jiwei_xiao
 * 
 */
public class KDTableEditObservable extends Observable {

	public KDTableEditObservable() {
		super();
	}

	// private int rowIndex = -1;
	// private int colIndex = -1;
	private boolean isAutoMerge = false;
	// 是否同步分录合并行之间的信息
	private boolean isAutoSynEntry = false;

	public void kDTableEditChanged(boolean bl) {
		// 发送观察者信息
		// bl负责是否更新携带物料
		setChanged();
		notifyObservers(new Boolean(bl));
	}

	// public int getRowIndex() {
	// return rowIndex;
	// }
	//
	// public void setRowIndex(int rowIndex) {
	// this.rowIndex = rowIndex;
	// }
	//
	// public int getColIndex() {
	// return colIndex;
	// }
	//
	// public void setColIndex(int colIndex) {
	// this.colIndex = colIndex;
	// }

	public boolean isAutoSynEntry() {
		return isAutoSynEntry;
	}

	public void setAutoSynEntry(boolean isAutoSynEntry) {
		this.isAutoSynEntry = isAutoSynEntry;
	}

	public boolean isAutoMerge() {
		return isAutoMerge;
	}

	public void setAutoMerge(boolean isAutoMerge) {
		this.isAutoMerge = isAutoMerge;
	}

}
