package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.framework.AbstractCoreBaseInfo;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.tool.FilterUtils;

public class KDTableF7RelationDataChangeListener extends BaseDataListener
		implements DataChangeListener {

	private KDBizPromptBox sor = null;
	private KDTable table = null;
	private String colKey = null;
	private String filedName;

	public KDTableF7RelationDataChangeListener(KDBizPromptBox sor,
			KDTable table, String colKey, String filedName) {
		this.sor = sor;
		this.table = table;
		this.colKey = colKey;
		this.filedName = filedName;

	}

	private void addFilter(Object value) {
		String key = "";
		AbstractCoreBaseInfo baseInfo = (AbstractCoreBaseInfo) value;
		if (baseInfo != null) {
			key = baseInfo.getId().toString();
		}

		// 清除旧的过滤并增加新的过滤
		FilterUtils.removeFilter(table, colKey, filedName);
		FilterUtils.addFilter(table, colKey, new FilterItemInfo(filedName, key,
				CompareType.EQUALS));
	}

	public void dataChanged(DataChangeEvent datachangeevent) {
		if (this.getListenerManager().isClearData()) {
			if (table.getColumn(colKey) != null) {
				for (int i = 0, size = table.getRowCount(); i < size; i++) {
					this.table.getCell(i, colKey).setValue(null);
				}
			}
		}

		this.addFilter(datachangeevent.getNewValue());

	}

	public Object excute() {
		this.addFilter(sor.getValue());
		sor.addDataChangeListener(this);
		return this;

	}

}
