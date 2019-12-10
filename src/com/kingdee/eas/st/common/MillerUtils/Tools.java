package com.kingdee.eas.st.common.MillerUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.mm.qm.client.KDTableUtils;
import com.kingdee.eas.basedata.mm.qm.utils.QtyMultiMeasureUtils;

public class Tools {
	public static void SimpleF7Setup(KDTable table, String column,
			String queryName) {
		final KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setQueryInfo(queryName);
		f7.setVisible(true);
		f7.setEditable(true);
		f7.setDisplayFormat("$name$");
		f7.setEditFormat("number");
		f7.setCommitFormat("$number$");
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(f7);
		table.getColumn(column).setEditor(cellEditor);
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$name$"));
		table.getColumn(column).setRenderer(render);
	}

	public static void setPrecisionByUnit(KDTable table, String unitColumn,
			String[] qtyColumns) {
		for (int i = 0; i < table.getRowCount(); i++) {
			int precision = 0;
			Object unit = table.getCell(i, unitColumn).getValue();

			if (unit != null) {
				precision = ((MeasureUnitInfo) unit).getQtyPrecision();
			}

			KDTableUtils.setBigDecimalFieldsPrecision(precision, table, i,
					qtyColumns);

		}
	}

	public static void addValueChangeListener(final KDTable table,
			final ValueChangeListener listener) {
		addValueChangeListener(table, false, listener);
	}

	public static void addValueChangeListener(final KDTable table,
			final boolean fireWhenRemoveRow, final ValueChangeListener listener) {
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				listener.afterChangeAction();
			}
		});

		table.addAfterActionListener(new BeforeActionListener() {

			public void beforeAction(BeforeActionEvent e) {
				if (e.getType() == e.ACTION_CUT
						|| e.getType() == e.ACTION_PASTE || fireWhenRemoveRow
						&& e.getType() == e.ACTION_REMOVE_ROW) {
					listener.afterChangeAction();
				}
			}
		});

		table.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					listener.afterChangeAction();
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}
}
