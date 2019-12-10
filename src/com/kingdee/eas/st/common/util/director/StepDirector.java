/*
 * �������� 2006-11-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.Method;
import java.util.LinkedList;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 
 * ����: ���ദ������.
 * 
 * @author daij date:2006-11-17
 *         <p>
 * @version EAS5.2.0
 */
public class StepDirector extends AbstractDirector {

	/**
	 * �ֲ��账������б�:
	 * 
	 * 1.��˳������IUIDirector, ILoadDataTemplate, IUIElementDecorator��Builderʵ��.
	 * 2.�����ظ�,Ҫ��˳��,����ȳ�����ԭ��.
	 */
	protected LinkedList builderStrategies = new LinkedList();

	/**
	 * 
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public StepDirector() {
		super();
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param strategies
	 *            �ֲ���������б�.
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public StepDirector(Object[] strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * ����µ�Builder����
	 * 
	 * @see template.AbstractDirector#addBuilderStrategy(java.lang.Object)
	 */
	public void addBuildStrategy(Object builderStrategy) {
		builderStrategies.add(builderStrategy);
	}

	/**
	 * 
	 * ���������������µķֲ��������.
	 * 
	 * @param strategies
	 *            �ֲ���������б�.
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final void addBuildStrategies(Object[] strategies) {
		if (STQMUtils.isNotNull(strategies) && strategies.length != 0) {
			Object o = null;
			for (int i = 0, size = strategies.length; i < size; i++) {
				o = strategies[i];
				if (STQMUtils.isNotNull(o)) {
					addBuildStrategy(o);
				}
			}
		}
	}

	/**
	 * 
	 * ������ת������������. ְ��: 1.ʩ�ӷֲ��ͷ��ദ��. 2.ת����������
	 * 
	 * @author:daij
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		for (int i = 0, size = builderStrategies.size(); i < size; i++) {
			// ������ǰ�����Builder����
			builderStrategy = builderStrategies.get(i);
			// ת����������.
			result = super.invoke(proxy, method, args);
		}
		return result;
	}
}
