package com.kingdee.eas.st.common.util;

import java.util.Observable;

/**
 * ��¼��Ӵ˼�����ʵ�ֺϲ���¼�й���
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
	// �Ƿ�ͬ����¼�ϲ���֮�����Ϣ
	private boolean isAutoSynEntry = false;

	public void kDTableEditChanged(boolean bl) {
		// ���͹۲�����Ϣ
		// bl�����Ƿ����Я������
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
