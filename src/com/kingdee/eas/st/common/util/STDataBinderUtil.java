/*
 * @(#)STDataBuilderUtil.java
 * 
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.st.common.util;

import java.awt.Component;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.appframework.databinding.ComponentProperty;
import com.kingdee.bos.appframework.databinding.DataComponentMap;
import com.kingdee.bos.appframework.databinding.Field;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * 描述: 钢铁模块的DataBinder工具类，用于通过实体属性名称得到 相应组件的名称(如果是分录的属性，则可以通过分录得到列名)。
 * 
 * @author: Angus date: 2008-3-19
 *          <p>
 * 
 *          修改人：<修改人>
 *          <p>
 *          修改时间：<修改时间>
 *          <p>
 *          修改描述：<修改描述>
 *          <p>
 * 
 * @version EAS 5.4
 */
public final class STDataBinderUtil {
	private static final Logger logger = CoreUIObject
			.getLogger(STDataBinderUtil.class);

	/**
	 * 描述： 通过分录实体的属性名称，得到UI组件。
	 * 
	 * @param builder
	 *            当前UI对应的builder对象
	 * @param propertyName
	 *            当前属性名(带'.'的为有属性的子属性， 例：entries.number,最多只能出现一个'.')
	 * @return 得到的UI组件对象
	 * @author Angus 创建时间：2008-3-19
	 *         <p>
	 */
	public static Object getUICompValue(
			com.kingdee.bos.appframework.databinding.DataBinder binder,
			String propertyName) {
		// 得到组件Map
		DataComponentMap dataCompMap = binder.getDataComponentMap();
		if (dataCompMap == null)
			return null;
		Component component = binder.getComponetByField(propertyName);
		if (component instanceof KDTable) {
			return getKDTableColumn(propertyName, dataCompMap, component);
		}
		return component;
	}

	public static Object getUICompValueForBotp(
			com.kingdee.bos.appframework.databinding.DataBinder binder,
			String propertyName) {
		// 得到组件Map
		DataComponentMap dataCompMap = binder.getDataComponentMap();
		if (dataCompMap == null)
			return null;
		Component component = binder.getComponetByField(propertyName);
		if (component instanceof KDTable) {
			return getTableColumn(propertyName, dataCompMap, component);
		}
		return component;
	}

	public static Object getKDTableColumn(String propertyName,
			DataComponentMap dataCompMap, Component component) {
		KDTable table = (KDTable) component;
		List fields = dataCompMap.getDetailFields(dataCompMap.getDetail(table));
		for (int i = 0; i < fields.size(); i++) {
			Field colField = (Field) fields.get(i);
			String propName = colField.getName();
			if (propertyName.equalsIgnoreCase(propName)) {
				ComponentProperty compProp = dataCompMap
						.getComponentProperty(colField);
				String name = compProp.getPropertyName();
				KDTColumn column = table.getColumn(name).getKDTColumn();
				return column;
			}
		}
		return null;
	}

	public static String getKDTableColumnName(String propertyName,
			DataComponentMap dataCompMap, Component component) {
		KDTable table = (KDTable) component;
		List fields = dataCompMap.getDetailFields(dataCompMap.getDetail(table));
		for (int i = 0; i < fields.size(); i++) {
			Field colField = (Field) fields.get(i);
			String propName = colField.getName();
			if (propertyName.equalsIgnoreCase(propName)) {
				ComponentProperty compProp = dataCompMap
						.getComponentProperty(colField);
				String name = compProp.getPropertyName();
				// KDTColumn column = table.getColumn(name).getKDTColumn();
				return name;
			}
		}
		return null;
	}

	public static Object getTableColumn(String propertyName,
			DataComponentMap dataCompMap, Component component) {
		KDTable table = (KDTable) component;
		List fields = dataCompMap.getDetailFields(dataCompMap.getDetail(table));
		for (int i = 0; i < fields.size(); i++) {
			Field colField = (Field) fields.get(i);
			String propName = colField.getName();
			if (propertyName.equalsIgnoreCase(propName)) {
				ComponentProperty compProp = dataCompMap
						.getComponentProperty(colField);
				String name = compProp.getPropertyName();
				IColumn column = table.getColumn(name);
				return column;
			}
		}
		return null;
	}
}
