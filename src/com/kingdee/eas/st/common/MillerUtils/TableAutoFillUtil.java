package com.kingdee.eas.st.common.MillerUtils;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

/**
 * 用来自动填充所有行的空白字段
 * 
 * @author miller_xiao
 * 
 */
public class TableAutoFillUtil {
	public static void setup(final KDTable table, final String column) {
		if (table == null || column == null || table.getColumn(column) == null
				|| table.getColumn(column).getEditor() == null
				|| table.getColumn(column).getEditor().getComponent() == null)
			return;

		table.addKDTEditListener(new KDTEditAdapter() {

			Object oldValue = null;

			public void editStarting(KDTEditEvent event) {
				int colIndex = event.getColIndex();
				String fieldName = table.getColumnKey(colIndex);
				if (column.equals(fieldName)) {
					oldValue = event.getOldValue();
				}
			}

			public void editStopped(KDTEditEvent event) {
				int colIndex = event.getColIndex();
				String fieldName = table.getColumnKey(colIndex);
				if (column.equals(fieldName)) {
					Object obj = event.getValue();
					if (obj != null) {
						int rowIndex = event.getRowIndex();
						if (rowIndex != -1 && !obj.equals(oldValue)) {
							for (int i = 0; i < table.getRowCount(); i++) {
								if (table.getCell(i, column).getValue() == null)
									table.getCell(i, column).setValue(obj);
							}
						}
					}
				}
			}
		});

	}
}
