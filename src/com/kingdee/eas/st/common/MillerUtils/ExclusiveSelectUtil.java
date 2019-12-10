package com.kingdee.eas.st.common.MillerUtils;

import java.awt.Component;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.eas.basedata.master.material.MaterialInfo;

public class ExclusiveSelectUtil {
	public static void setup(final KDTable table, final String fieldName) {
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStarting(KDTEditEvent e) {
				int colIndex = e.getColIndex();

				if (colIndex == table.getColumnIndex(fieldName)) {
					Boolean value = (Boolean) table.getRow(e.getRowIndex())
							.getCell(fieldName).getValue();
					if (value.booleanValue()) {
						e.setCancel(true);
					}

					for (int i = 0; i < table.getRowCount(); i++) {
						table.getRow(i).getCell(fieldName).setValue(
								new Boolean(false));
					}

					table.getRow(e.getRowIndex()).getCell(fieldName).setValue(
							new Boolean(true));

				}
			}

			public void editStopped(KDTEditEvent e) {
			}
		});
	}

}
