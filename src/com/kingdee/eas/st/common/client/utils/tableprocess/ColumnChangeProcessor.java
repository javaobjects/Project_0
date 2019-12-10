/*
 * @(#)ColumnChangeProcessor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

public class ColumnChangeProcessor implements IColumnChangeProcessor {

	protected KDTable table = null;
	protected String materialColumnName = null;
	protected String sourceColumnName = null;
	protected String[] targetColumnNames = null;

	public String getSourceColumnName() {
		return sourceColumnName;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public String[] getTargetColumnNames() {
		return targetColumnNames;
	}

	public void setTargetColumnNames(String[] targetColumnNames) {
		this.targetColumnNames = targetColumnNames;
	}

	public boolean process(KDTEditEvent e) {
		return true;
	}

	public ColumnChangeProcessor(KDTable table, String materialColumnName,
			String sourceColumnName, String[] targetColumnNames) {
		super();
		// TODO �Զ����ɹ��캯�����
		this.table = table;
		this.materialColumnName = materialColumnName;
		this.sourceColumnName = sourceColumnName;
		this.targetColumnNames = targetColumnNames;
	}

}
