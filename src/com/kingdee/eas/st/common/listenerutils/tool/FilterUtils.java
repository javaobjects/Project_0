package com.kingdee.eas.st.common.listenerutils.tool;

import java.awt.Component;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.sql.ParserException;

public class FilterUtils {

	public static void addFilter(KDBizPromptBox box, FilterItemInfo info) {

		EntityViewInfo entityViewInfo = box.getEntityViewInfo();
		if (box.getEntityViewInfo() == null) {
			entityViewInfo = new EntityViewInfo();
			box.setEntityViewInfo(entityViewInfo);
		}

		FilterInfo filter = entityViewInfo.getFilter();
		if (filter == null) {
			filter = new FilterInfo();
			entityViewInfo.setFilter(filter);
		}
		filter.getFilterItems().add(info);
		box.setEntityViewInfo(entityViewInfo);

	}

	public static void addFilter(EntityViewInfo entityViewInfo,
			FilterItemInfo info) {

		if (entityViewInfo == null) {
			entityViewInfo = new EntityViewInfo();
		}

		FilterInfo filter = entityViewInfo.getFilter();
		if (filter == null) {
			filter = new FilterInfo();
			entityViewInfo.setFilter(filter);
		}
		filter.getFilterItems().add(info);
	}

	public static void addAndReplceFilter(KDBizPromptBox box,
			FilterItemInfo info) {
		FilterUtils.removeFilter(box, info.getPropertyName());
		FilterUtils.addFilter(box, info);
	}

	public static void addAndReplceFilter(EntityViewInfo entityViewInfo,
			FilterItemInfo info) {
		FilterUtils.removeFilter(entityViewInfo, info.getPropertyName());
		FilterUtils.addFilter(entityViewInfo, info);
	}

	public static void removeFilter(KDBizPromptBox box, String filedName) {

		EntityViewInfo entityViewInfo = box.getEntityViewInfo();
		if (entityViewInfo == null) {
			return;
		}

		FilterInfo filterInfo = entityViewInfo.getFilter();
		if (filterInfo == null) {
			return;

		}

		if (filterInfo.getFilterItems() == null
				|| filterInfo.getFilterItems().size() == 0) {
			return;
		}

		int size = filterInfo.getFilterItems().size();
		boolean isNew = true;
		for (int i = size - 1; i >= 0; i--) {
			FilterItemInfo itemInfo = (FilterItemInfo) filterInfo
					.getFilterItems().get(i);
			if (itemInfo.getPropertyName().equalsIgnoreCase(filedName)) {
				filterInfo.getFilterItems().remove(itemInfo);
				isNew = false;
			}
		}

		// 新增的过滤条件
		if (isNew) {
			if (size >= 1)
				if (null != filterInfo.getMaskString()) {
					filterInfo.setMaskString("(" + filterInfo.getMaskString()
							+ ") and #" + size);
				} else {
					StringBuffer mStr = new StringBuffer("");
					for (int i = 0; i <= size; i++) {
						if (i > 0)
							mStr.append(" and ");
						mStr.append("#" + i);
					}
					filterInfo.setMaskString(mStr.toString());
				}
			else
				filterInfo.setMaskString("#" + size);
		}

		box.setEntityViewInfo(entityViewInfo);
	}

	/**
	 * add by hai_zhong
	 * 
	 * @param box
	 * @param filedName
	 */
	public static void removeFilter(EntityViewInfo entityViewInfo,
			String filedName) {
		if (entityViewInfo == null) {
			return;
		}

		FilterInfo filterInfo = entityViewInfo.getFilter();
		if (filterInfo == null) {
			return;

		}

		String oql = entityViewInfo.toString();

		oql = oql.replaceAll("(AND|OR)\\s*?\\(" + filedName + ".*?\\)", "");
		oql = oql.replaceAll("\\(" + filedName + ".*?\\)\\s*?(AND|OR)", "");
		oql = oql.replaceAll("" + filedName + "\\s*?(AND|OR)", "");

		try {
			entityViewInfo = new EntityViewInfo(oql);
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public static KDBizPromptBox getBoxFromKDTable(KDTable table, String colKey) {
		KDBizPromptBox prmtColum = null;

		// 表格中可能没有editor直接不管了
		if (null == table.getColumn(colKey)) {
			return null;
		}
		if (null == table.getColumn(colKey).getEditor()) {
			return null;
		}

		Component colum = table.getColumn(colKey).getEditor().getComponent();
		if (colum instanceof KDBizPromptBox) {
			prmtColum = (KDBizPromptBox) colum;
		}
		return prmtColum;
	}

	public static KDBizPromptBox getBoxFromKDTableRow(KDTable table,
			int rowIndex, String colKey) {
		KDBizPromptBox prmtColum = null;

		// 表格中可能没有editor直接不管了
		if (null == table.getRow(rowIndex)) {
			return null;
		}
		if (null == table.getRow(rowIndex).getCell(colKey)) {
			return null;
		}
		if (table.getRow(rowIndex).getCell(colKey).getEditor() == null) {
			return null;
		}
		Component colum = table.getRow(rowIndex).getCell(colKey).getEditor()
				.getComponent();
		if (colum instanceof KDBizPromptBox) {
			prmtColum = (KDBizPromptBox) colum;
		}
		return prmtColum;
	}

	public static void addFilter(KDTable table, String colKey,
			FilterItemInfo info) {
		KDBizPromptBox prmtColum = getBoxFromKDTable(table, colKey);
		if (prmtColum != null) {
			addFilter(prmtColum, info);
		}
	}

	public static void addAndReplceFilter(KDTable table, String colKey,
			FilterItemInfo info) {
		KDBizPromptBox prmtColum = getBoxFromKDTable(table, colKey);
		if (prmtColum != null) {
			addAndReplceFilter(prmtColum, info);
		}
	}

	public static void removeFilter(KDTable table, String colKey,
			String filedName) {
		KDBizPromptBox prmtColum = getBoxFromKDTable(table, colKey);
		if (prmtColum != null) {
			removeFilter(prmtColum, filedName);
		}
	}

	public static void removeAllFilter(KDTable table, String colKey) {
		KDBizPromptBox prmtColum = getBoxFromKDTable(table, colKey);
		if (prmtColum != null) {
			removeAllFilter(prmtColum);
		}
	}

	public static void removeLineAllFilter(KDTable table, int rowIndex,
			String colKey) {
		KDBizPromptBox prmtColum = getBoxFromKDTableRow(table, rowIndex, colKey);
		if (prmtColum != null) {
			removeAllFilter(prmtColum);
		}

	}

	public static void removeAllFilter(KDBizPromptBox box) {

		EntityViewInfo entityViewInfo = box.getEntityViewInfo();
		if (entityViewInfo == null) {
			return;
		}

		FilterInfo filterInfo = entityViewInfo.getFilter();
		if (filterInfo == null) {
			return;
		}

		if (filterInfo.getFilterItems() == null
				|| filterInfo.getFilterItems().size() == 0) {
			return;
		}

		int size = filterInfo.getFilterItems().size();
		for (int i = size - 1; i >= 0; i--) {
			FilterItemInfo itemInfo = (FilterItemInfo) filterInfo
					.getFilterItems().get(i);
			filterInfo.getFilterItems().remove(itemInfo);
		}
		box.setEntityViewInfo(entityViewInfo);
	}
}
