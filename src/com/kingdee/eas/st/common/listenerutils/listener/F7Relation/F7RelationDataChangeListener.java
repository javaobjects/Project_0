package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.tool.FilterUtils;

public class F7RelationDataChangeListener extends BaseDataListener implements
		DataChangeListener {

	protected KDBizPromptBox sor = null;
	protected KDBizPromptBox dest = null;
	protected String filedName;

	/**
	 * 在来源F7上增加监听，并更新目标F7的过滤条件
	 * 
	 * @param sor
	 * @param dest
	 * @param filedName
	 */
	public F7RelationDataChangeListener(KDBizPromptBox sor,
			KDBizPromptBox dest, String filedName) {
		this.sor = sor;
		this.dest = dest;
		this.filedName = filedName;
	}

	protected void addFilter(Object value) {
		String key = "";
		AbstractCoreBaseInfo baseInfo = (AbstractCoreBaseInfo) value;
		if (baseInfo != null) {
			key = baseInfo.getId().toString();
		}
		// 清除旧的过滤并增加新的过滤
		FilterUtils.removeFilter(dest, filedName);
		FilterUtils.addFilter(dest, new FilterItemInfo(filedName, key,
				CompareType.EQUALS));
	}

	public void dataChanged(DataChangeEvent arg0) {
		if (!this.getListenerManager().isEanbleListener())
			return;
		if (this.getListenerManager().isClearData()) {
			dest.setValue(null);
		}

		this.addFilter(arg0.getNewValue());
	}

	public Object excute() {
		this.addFilter(sor.getValue());
		sor.addDataChangeListener(this);
		return this;

	}
}
