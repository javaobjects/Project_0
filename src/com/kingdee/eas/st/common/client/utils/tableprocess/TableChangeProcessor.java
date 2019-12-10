/*
 * @(#)TableChangeProcessor.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
	 * 描述:处理计量单位改变
	 * 
	 * @author: colin_xu 创建时间:2007-6-25
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
	 * 描述:处理数量改变
	 * 
	 * @author: colin_xu 创建时间:2007-6-25
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
	 * 描述: 加入列改变处理器
	 * 
	 * @param processor
	 *            : 多基础资料处理器
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void addProcessor(ColumnChangeProcessor processor) {
		columnChangeProcessors.add(processor);
	}

	/**
	 * 
	 * 描述: 移除列改变处理器
	 * 
	 * @param processor
	 *            : 多基础资料处理器
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void removeProcessor(ColumnChangeProcessor processor) {
		columnChangeProcessors.remove(processor);
	}

	/**
	 * 
	 * 描述: 清空列改变处理器
	 * 
	 * @author: colin_xu 创建时间:2007-6-10
	 *          <p>
	 */
	public void clear() {
		columnChangeProcessors.clear();
	}

	/**
	 * 
	 * 描述:处理改变
	 * 
	 * @param e
	 * @return
	 * @throws Exception
	 * @author: colin_xu 创建时间:2007-6-25
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
