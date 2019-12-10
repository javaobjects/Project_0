package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.util.STUtils;

public class ClsDataListener extends BaseDataListener implements
		DataChangeListener {

	protected KDBizPromptBox sor = null;
	protected Object[] dests = null;

	public ClsDataListener(KDBizPromptBox sor, Object[] dests) {
		this.sor = sor;
		this.dests = dests;
	}

	public void dataChanged(DataChangeEvent e) {
		if (!getListenerManager().isEanbleListener())
			return;
		if (this.getListenerManager().isClearData()) {

			if (!STUtils.easObjectEquals(e.getOldValue(), e.getNewValue())) {

				for (int i = 0, count = dests.length; i < count; i++) {
					Object o = dests[i];
					if (o instanceof KDBizPromptBox) {
						((KDBizPromptBox) o).setValue(null);
					} else if (o instanceof KDTextField) {
						((KDTextField) o).setText(null);
					} else if (o instanceof KDTable) {
						((KDTable) o).removeRows();
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
