package com.kingdee.eas.st.common.util;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;

public class TableFillKDTEditListener implements KDTEditListener {

	public KDTable table;
	public String[] colums;

	public TableFillKDTEditListener(String[] a) {
		this.colums = a;
	}

	public TableFillKDTEditListener(KDTable table, String[] a) {
		this.colums = a;
		table.addKDTEditListener(this);
	}

	public void editCanceled(KDTEditEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void editStarted(KDTEditEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void editStarting(KDTEditEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void editStopped(KDTEditEvent arg0) {
		KDTable table = (KDTable) arg0.getSource();
		int rowIndex = arg0.getRowIndex();
		int colIndex = arg0.getColIndex();
		int rowcount = table.getRowCount();
		for (int i = 0; i < colums.length; i++) {
			String column = colums[i];
			if (table.getColumnIndex(column) == colIndex) {
				for (int j = rowIndex + 1; j <= rowcount - 1; j++) {
					Object value = table.getCell(rowIndex, column).getValue();
					if (table.getCell(j, column).getValue() != null) {
						return;
					}
					if (value != null) {
						table.getCell(j, column).setValue(value);
					}
				}

			}

		}

	}

	public void editStopping(KDTEditEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void editValueChanged(KDTEditEvent arg0) {
		// TODO Auto-generated method stub

	}

}
