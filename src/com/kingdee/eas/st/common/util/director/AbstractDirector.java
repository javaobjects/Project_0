/*
 * �������� 2006-11-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 
 * ����: �����������.
 * 
 * 1. ����Ϊ����ͷֲ�����, ֧�ַ����зֲ�, �ֲ��з��� => ���ӵ�������Բ������������,�Լ��������ͽ���ת��. 2. ���õ���:
 * �����Ŀ��Ƿֲ�������, ���а�XXXUI��Ϊ�����Ĵ������, Ȼ���ٰ�ҵ�������������ദ������ӵ��ֲ���������. 3.
 * �ڽӿ�����Ҫ�ṩ���㶨λ��������ĳ��Ԫ�صķ���.
 * 
 * @author daij date:2006-11-16
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractDirector implements InvocationHandler {

	/**
	 * ��ǰ�Ĵ������
	 */
	protected Object builderStrategy = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public AbstractDirector() {
		super();
	}

	/**
	 * 
	 * �����������µĹ�������.
	 * 
	 * @param builderStrategy
	 *            Builder�������.
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public abstract void addBuildStrategy(Object builderStrategy);

	/**
	 * 
	 * ���������뷽������. ְ��: 1.ʩ�ӷֲ��ͷ��ദ��. 2.ת����������
	 * 
	 * @author:daij
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		if (STQMUtils.isNotNull(method)) {
			if (builderStrategy instanceof AbstractDirector) {
				// �����Ȼ�ǵ�����ô�Ͱ�invoke����Ȩת������.
				result = ((AbstractDirector) builderStrategy).invoke(proxy,
						method, args);
			} else if (STQMUtils.isNotNull(builderStrategy)) {
				// ����builderStrategy�Ƿ�֧��method
				if (method.getDeclaringClass().isAssignableFrom(
						builderStrategy.getClass())) {
					// ����Ѿ��Ǿ����Builder���������ת�����巽������.
					try {
						result = method.invoke(builderStrategy, args);
					} catch (Exception e) {
						if (STQMUtils.isNull(e.getCause())) {
							// ���û��ԭʼ�쳣����ô���׳�������λ�쳣.
							throw e;
						} else {
							// �Ѷ�λ�����쳣ժ����ֻ��ԭʼ�쳣ת����ȥ.
							throw e.getCause();
						}
					}
				}
			}
		}
		return result;
	}
}
