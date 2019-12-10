package com.kingdee.eas.st.common.listenerutils;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.ClsDataListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.ClsTableDataListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7MTRlDtChgListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7RelationDataChangeListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7RelationDataChangeListener_SL;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.KDTableF7RelationDataChangeListener;
import com.kingdee.eas.st.common.listenerutils.listener.F7Relation.impl.F7MoveTogetherListener;

public class F7RelationFactory {

	/**
	 * F7������ϵ���� f7-f7
	 * 
	 * @param sor
	 * @param dest
	 * @param filedName
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDBizPromptBox dest, String filedName) {
		BaseDataListener lis = new F7RelationDataChangeListener(sor, dest,
				filedName);
		return lis;
	}

	/**
	 * F7������ϵ���� f7-table
	 * 
	 * @param sor
	 * @param table
	 * @param colKey
	 * @param filedName
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDTable table, String colKey, String filedName) {
		BaseDataListener lis = new KDTableF7RelationDataChangeListener(sor,
				table, colKey, filedName);
		return lis;
	}

	/**
	 * F7������ϵ���� f7-f7</br> �˹�����sorΪ��ʱ��ʾ���ٹ���_showAll
	 * 
	 * @param sor
	 * @param dest
	 * @param filedName
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDBizPromptBox dest, String filedName, boolean isShowAll) {
		if (isShowAll) {
			BaseDataListener lis = new F7RelationDataChangeListener_SL(sor,
					dest, filedName);
			return lis;
		} else {
			createF7Relation(sor, dest, filedName);
		}
		BaseDataListener lis = new F7RelationDataChangeListener(sor, dest,
				filedName);
		return lis;
	}

	/**
	 * F7������ϵ���� f7-f7</br> ��ָ̬��F7���ݸı������������̳�<br />
	 * com.kingdee.eas.st.common.listenerutils
	 * .listener.F7Relation.F7MTRlDtChgListener<br />
	 * ����дgetFilterItemCollection���������÷�����:</br>
	 * 
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager listenerManager
	 * <br /> = new
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager();<br />
	 * listenerManager.addListener(F7RelationFactory.createF7Relation(<br />
	 * "F7���ݸı����",prmt1, prmt2));<br />
	 * 
	 * @author hai_zhong
	 * @param sor
	 * @param dest
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static BaseDataListener createF7Relation(String className,
			KDBizPromptBox sor, KDBizPromptBox dest) {
		return createF7Relation(className, sor, dest, -1, -1, 1, "");
	}

	/**
	 * F7��֯ί�й�ϵ����(Ŀ��F7ί��ԴF7),</br> �÷�����:</br>
	 * 
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager listenerManager
	 * <br /> = new
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager();<br />
	 * listenerManager.addListener(F7RelationFactory.createF7Relation(<br />
	 * prmt1, prmt2, OrgType.STORAGE_VALUE,OrgType.COMPANY_VALUE));<br />
	 * 
	 * @author hai_zhong
	 * @param sor
	 * @param dest
	 * @param sorType
	 * @param destType
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDBizPromptBox dest, int srcType, int destType) {
		return createF7Relation("", sor, dest, srcType, destType, 2, "");
	}

	/**
	 * F7��֯ί�й�ϵ����(Ŀ��F7ί��ԴF7),</br> �÷�����:</br>
	 * 
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager listenerManager
	 * <br /> = new
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager();<br />
	 * listenerManager.addListener(F7RelationFactory.createF7Relation(<br />
	 * prmt1, prmt2, OrgType.STORAGE_VALUE,OrgType.COMPANY_VALUE));<br />
	 * 
	 * @author hai_zhong
	 * @param sor
	 * @param dest
	 * @param sorType
	 * @param destType
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDTable kdt, String field, int srcType, int destType) {
		KDBizPromptBox f7 = (KDBizPromptBox) kdt.getColumn(field).getEditor()
				.getComponent();
		return createF7Relation("", sor, f7, srcType, destType, 2, "");
	}

	/**
	 * dir=1 destί��src, dir=2 srcί��dest
	 * 
	 * @param sor
	 * @param kdt
	 * @param field
	 * @param srcType
	 * @param destType
	 * @param dir
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDTable kdt, String field, int srcType, int destType, int dir,
			String filterField) {
		KDBizPromptBox f7 = (KDBizPromptBox) kdt.getColumn(field).getEditor()
				.getComponent();
		return createF7Relation("", sor, f7, srcType, destType, dir,
				filterField);
	}

	/**
	 * F7��֯ί�й�ϵ����(Ŀ��F7ί��ԴF7),</br> �÷�����:</br>
	 * 
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager listenerManager
	 * <br /> = new
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager();<br />
	 * listenerManager.addListener(F7RelationFactory.createF7Relation(<br />
	 * prmt1, prmt2, OrgType.STORAGE_VALUE,OrgType.COMPANY_VALUE));<br />
	 * 
	 * @author hai_zhong
	 * @param sor
	 * @param dest
	 * @param srcType
	 * @param destType
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDBizPromptBox dest, int srcType, int destType, int dir) {
		return createF7Relation("", sor, dest, srcType, destType, dir, "");
	}

	/**
	 * F7��֯ί�й�ϵ����(ָ��ί�з��򣬲�ָ��ԴF7������֯�����ֶ�fieldName��<br /> dir
	 * (1:srcί����dest,2:srcί��dest),</br> �÷�����:</br>
	 * <p>
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager listenerManager
	 * <br /> = new
	 * com.kingdee.eas.st.common.listenerutils.ListenerManager();<br />
	 * listenerManager.addListener(F7RelationFactory.createF7Relation(<br />
	 * prmt1, prmt2, OrgType.STORAGE_VALUE,OrgType.COMPANY_VALUE,1,"id"));<br />
	 * </p>
	 * 
	 * @author hai_zhong
	 * @param sor
	 * @param dest
	 * @param srcType
	 * @param destType
	 * @param dir
	 * @param fieldName
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDBizPromptBox dest, int srcType, int destType, int dir,
			String fieldName) {
		return createF7Relation("", sor, dest, srcType, destType, dir,
				fieldName);
	}

	protected static BaseDataListener createF7Relation(String className,
			KDBizPromptBox sor, KDBizPromptBox dest, int fromType, int toType,
			int dir, String fieldName) {
		BaseDataListener lis = null;
		try {
			if ("".equals(className))
				className = "com.kingdee.eas.st.common.listenerutils.listener.F7Relation.F7MTRlDtChgListener";
			lis = (F7MTRlDtChgListener) Class.forName(className).newInstance();
			((F7MTRlDtChgListener) lis).initParam(sor, dest, fromType, toType,
					dir, fieldName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lis;
	}

	/**
	 * f7�л������ؿ���ֵ
	 * 
	 * @param sor
	 * @param dests
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			Object[] dests) {
		BaseDataListener lis = new ClsDataListener(sor, dests);
		return lis;
	}

	/**
	 * f7�л������ؿ���ֵ
	 * 
	 * @param sor
	 * @param dests
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			KDTable table, String dests[]) {
		BaseDataListener lis = new ClsTableDataListener(sor, table, dests);
		return lis;
	}

	/**
	 * �ֶ�����
	 * 
	 * @param sor
	 * @param properties
	 * @param components
	 * @return
	 */
	public static BaseDataListener createF7Relation(KDBizPromptBox sor,
			String[] properties, Object[] components) {
		return new F7MoveTogetherListener(sor, properties, components);
	}

}
