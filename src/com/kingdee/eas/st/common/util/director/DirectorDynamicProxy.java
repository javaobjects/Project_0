/*
 * @(#)DirectorDynamicProxy.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * ����: Director�Ķ�̬����.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class DirectorDynamicProxy {

	/**
	 * �ӿڴ���ķ���������Handler.
	 */
	protected InvocationHandler director = null;

	/**
	 * �ӿڴ���ʵ��.
	 */
	protected Object proxyInstance = null;

	/**
	 * ����Ľӿ��б�.
	 */
	protected Class[] proxyInterfaces = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy() {
		super();
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param director
	 *            �ӿڴ���ķ���������Handler.
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public DirectorDynamicProxy(InvocationHandler director) {
		super();

		setDirector(director);
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param proxyInterfaces
	 *            ����Ľӿ��б�.
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy(Class[] proxyInterfaces) {
		super();

		changeProxyInterfaces(proxyInterfaces);
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param proxyInterfaces
	 *            ����Ľӿ��б�.
	 * @param director
	 *            �ӿڴ���ķ���������Handler.
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy(Class[] proxyInterfaces,
			InvocationHandler director) {
		super();

		setDirector(director);

		changeProxyInterfaces(proxyInterfaces);
	}

	/**
	 * ����:@return ���� director��
	 */
	public InvocationHandler getDirector() {
		return director;
	}

	/**
	 * ����:����director��ֵ��
	 * 
	 * @param director
	 */
	public void setDirector(InvocationHandler director) {
		this.director = director;

		// �����������ʵ��.
		setupProxyInstance();
	}

	/**
	 * 
	 * ���������ؽӿڴ���ʵ��
	 * 
	 * @return Object
	 * @author:daij ����ʱ�䣺2006-11-22
	 *              <p>
	 */
	public final Object getProxyInstance() {
		return proxyInstance;
	}

	/**
	 * 
	 * �����������µ���Ҫ����Ľӿ��б�.
	 * 
	 * @param interfaces
	 *            ������Ľӿ��б�
	 * @author:daij ����ʱ�䣺2006-11-17
	 *              <p>
	 */
	public final void addProxyInterfaces(Class[] interfaces) {
		if (STQMUtils.isNotNull(interfaces) && interfaces.length > 0) {
			Object o = null;
			List ls = new ArrayList(Arrays.asList(proxyInterfaces));
			for (int i = 0, size = interfaces.length; i < size; i++) {
				o = interfaces[i];
				if (STQMUtils.isNotNull(o) && !ls.contains(o)) {
					ls.add(o);
				}
			}
			if (STQMUtils.isNotNull(proxyInterfaces)
					&& ls.size() > proxyInterfaces.length) {
				changeProxyInterfaces((Class[]) ls.toArray(new Class[0]));
			}
		}
	}

	/**
	 * 
	 * �������ı���Ҫ����Ľӿ��б�. ע��: ��ȫ�滻DirectorDynamicProxy������Ľӿ��б���ܻᵼ������Ľӿ�ʧЧ.
	 * ����Ϊprotected��ֱ����¶��Client��,��DirectorDynamicProxy�ļ̳������鴦��.
	 * 
	 * @param interfaces
	 *            ������Ľӿ��б�
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	protected void changeProxyInterfaces(Class[] interfaces) {
		proxyInterfaces = interfaces;
		// �����������ʵ��.
		setupProxyInstance();
	}

	/**
	 * 
	 * ���������õ�ǰ����ʵ��.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	protected void setupProxyInstance() {
		if (STQMUtils.isNotNull(director)
				&& STQMUtils.isNotNull(proxyInterfaces)
				&& proxyInterfaces.length != 0) {
			proxyInstance = moldProxyInstance(proxyInterfaces, director);
		}
	}

	/**
	 * 
	 * ����������ӿڶ�̬����ʵ��.
	 * 
	 * @param interfaces
	 *            ������Ľӿ��б�
	 * @param invokHandler
	 *            �ӿڴ���ķ���������Handler
	 * @return Object Proxy��ʵ��
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	protected static Object moldProxyInstance(Class[] interfaces,
			InvocationHandler invokHandler) {
		return Proxy.newProxyInstance(DirectorDynamicProxy.class
				.getClassLoader(), interfaces, invokHandler);
	}
}
