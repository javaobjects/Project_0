/*
 * @(#)TableChangeProcessor.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils.tableprocess;

import java.util.ArrayList;
import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;

public class TableChangeProcessor {

	List columnChangeProcessors = new ArrayList();
	protected KDTable table = null;
	protected String materialColumnName = null;

	/**
	 * 
	 * ����:���������λ�ı�
	 * 
	 * @author: colin_xu ����ʱ��:2007-6-25
	 *          <p>
	 */
	public void processMeasureUnit(String sourceColumnName,
			String[] targetColumnNames) {
		MeasureUnitColumnChangeProcessor processor = new MeasureUnitColumnChangeProcessor(
				this.table, materialColumnName, sourceColumnName,
				targetColumnNames);
		addProcessor(processor);

	}

	/**
	 * 
	 * ����:���������ı�
	 * 
	 * @author: colin_xu ����ʱ��:2007-6-25
	 *          <p>
	 */
	public void processQty(String sourceMeasureUnitColumnName,
			String sourceColumnName, String[] targetMeasureUnitColumnNames,
			String[] targetColumnNames) {
		QtyColumnChangeProcessor processor = new QtyColumnChangeProcessor(
				this.table, materialColumnName, sourceMeasureUnitColumnName,
				sourceColumnName, targetMeasureUnitColumnNames,
				targetColumnNames);
		addProcessor(processor);

	}

	/**
	 * 
	 * ����: �����иı䴦����
	 * 
	 * @param processor
	 *            : ��������ϴ�����
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void addProcessor(ColumnChangeProcessor processor) {
		columnChangeProcessors.add(processor);
	}

	/**
	 * 
	 * ����: �Ƴ��иı䴦����
	 * 
	 * @param processor
	 *            : ��������ϴ�����
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void removeProcessor(ColumnChangeProcessor processor) {
		columnChangeProcessors.remove(processor);
	}

	/**
	 * 
	 * ����: ����иı䴦����
	 * 
	 * @author: colin_xu ����ʱ��:2007-6-10
	 *          <p>
	 */
	public void clear() {
		columnChangeProcessors.clear();
	}

	/**
	 * 
	 * ����:����ı�
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @author: colin_xu ����ʱ��:2007-6-25
	 *          <p>
	 */
	public boolean process() throws Exception {

		KDTEditAdapter entryTableChange = new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				for (int i = 0; i < columnChangeProcessors.size(); i++) {
					((ColumnChangeProcessor) columnChangeProcessors.get(i))
							.process(e);
				}
			}
		};
		table.addKDTEditListener(entryTableChange);

		return true;
	}

	public TableChangeProcessor(KDTable table, String materialColumnName) {
		this.table = table;
		this.materialColumnName = materialColumnName;
	}

	public KDTable getTable() {
		return table;
	}

}
