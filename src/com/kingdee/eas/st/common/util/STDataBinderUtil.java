/*
 * @(#)STDataBuilderUtil.java
 * 
 * �����������������޹�˾��Ȩ����
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
 * ����: ����ģ���DataBinder�����࣬����ͨ��ʵ���������Ƶõ� ��Ӧ���������(����Ƿ�¼�����ԣ������ͨ����¼�õ�����)��
 * 
 * @author: Angus date: 2008-3-19
 *          <p>
 * 
 *          �޸��ˣ�<�޸���>
 *          <p>
 *          �޸�ʱ�䣺<�޸�ʱ��>
 *          <p>
 *          �޸�������<�޸�����>
 *          <p>
 * 
 * @version EAS 5.4
 */
public final class STDataBinderUtil {
	private static final Logger logger = CoreUIObject
			.getLogger(STDataBinderUtil.class);

	/**
	 * ������ ͨ����¼ʵ����������ƣ��õ�UI�����
	 * 
	 * @param builder
	 *            ��ǰUI��Ӧ��builder����
	 * @param propertyName
	 *            ��ǰ������(��'.'��Ϊ�����Ե������ԣ� ����entries.number,���ֻ�ܳ���һ��'.')
	 * @return �õ���UI�������
	 * @author Angus ����ʱ�䣺2008-3-19
	 *         <p>
	 */
	public static Object getUICompValue(
			com.kingdee.bos.appframework.databinding.DataBinder binder,
			String propertyName) {
		// �õ����Map
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
		// �õ����Map
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
