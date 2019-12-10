package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.util.STUtils;

public class ClsTableDataListener extends BaseDataListener implements
		DataChangeListener {
	KDBizPromptBox sor = null;
	protected KDTable table = null;
	protected String[] dests = null;

	public ClsTableDataListener(KDBizPromptBox sor, KDTable table,
			String dests[]) {
		this.sor = sor;
		this.table = table;
		this.dests = dests;
	}

	public void dataChanged(DataChangeEvent e) {
		if (!getListenerManager().isEanbleListener())
			return;
		if (this.getListenerManager().isClearData()) {

			if (STUtils.easObjectEquals(e.getOldValue(), e.getNewValue())) {

				for (int i = 0; i < table.getRowCount(); i++) {

					for (int j = 0; j < dests.length; j++) {
						table.getRow(i).getCell(dests[j]).setValue(null);
					}
				}
			}
		}
	}

	public Object excute() {
		sor.addDataChangeListener(this);
		return this;
	}

}
