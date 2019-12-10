package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import java.lang.reflect.Method;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.framework.DataBaseInfo;

public class F7BindingListener implements DataChangeListener {

	private KDBizPromptBox sor = null;
	private KDBizPromptBox dest = null;
	private String filedName;

	public void dataChanged(DataChangeEvent datachangeevent) {
		if (sor == null || sor.getValue() == null) {
			dest.setValue(null);
			return;
		}
		Object o = sor.getValue();

		try {
			Method method = o.getClass().getMethod("get" + filedName,
					new Class[] {});
			DataBaseInfo value = (DataBaseInfo) method.invoke(o,
					new Object[] {});
			Object destO = STQMUtils.getDynamicObject(null, value.getId()
					.toString());
			dest.setValue(destO);
		} catch (Exception e) {
			e.printStackTrace();
			dest.setValue(null);
		}
	}

	public F7BindingListener(KDBizPromptBox sor, KDBizPromptBox dest,
			String filedName) {
		this.sor = sor;
		this.dest = dest;
		this.filedName = filedName;

		this.dataChanged(new DataChangeEvent(this.sor));
	}

}
