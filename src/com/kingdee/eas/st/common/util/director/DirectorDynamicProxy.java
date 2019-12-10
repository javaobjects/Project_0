/*
 * @(#)DirectorDynamicProxy.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 描述: Director的动态代理.
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class DirectorDynamicProxy {

	/**
	 * 接口代理的方法拦截类Handler.
	 */
	protected InvocationHandler director = null;

	/**
	 * 接口代理实例.
	 */
	protected Object proxyInstance = null;

	/**
	 * 代理的接口列表.
	 */
	protected Class[] proxyInterfaces = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param director
	 *            接口代理的方法拦截类Handler.
	 * @author:daij 创建时间：2006-11-16
	 *              <p>
	 */
	public DirectorDynamicProxy(InvocationHandler director) {
		super();

		setDirector(director);
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param proxyInterfaces
	 *            代理的接口列表.
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy(Class[] proxyInterfaces) {
		super();

		changeProxyInterfaces(proxyInterfaces);
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param proxyInterfaces
	 *            代理的接口列表.
	 * @param director
	 *            接口代理的方法拦截类Handler.
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public DirectorDynamicProxy(Class[] proxyInterfaces,
			InvocationHandler director) {
		super();

		setDirector(director);

		changeProxyInterfaces(proxyInterfaces);
	}

	/**
	 * 描述:@return 返回 director。
	 */
	public InvocationHandler getDirector() {
		return director;
	}

	/**
	 * 描述:设置director的值。
	 * 
	 * @param director
	 */
	public void setDirector(InvocationHandler director) {
		this.director = director;

		// 重新塑造代理实例.
		setupProxyInstance();
	}

	/**
	 * 
	 * 描述：返回接口代理实例
	 * 
	 * @return Object
	 * @author:daij 创建时间：2006-11-22
	 *              <p>
	 */
	public final Object getProxyInstance() {
		return proxyInstance;
	}

	/**
	 * 
	 * 描述：补充新的需要代理的接口列表.
	 * 
	 * @param interfaces
	 *            待代理的接口列表
	 * @author:daij 创建时间：2006-11-17
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
	 * 描述：改变需要代理的接口列表. 注意: 完全替换DirectorDynamicProxy所代理的接口列表可能会导致子类的接口失效.
	 * 修饰为protected不直接曝露给Client端,由DirectorDynamicProxy的继承类酌情处理.
	 * 
	 * @param interfaces
	 *            待代理的接口列表
	 * @author:daij 创建时间：2006-11-16
	 *              <p>
	 */
	protected void changeProxyInterfaces(Class[] interfaces) {
		proxyInterfaces = interfaces;
		// 重新塑造代理实例.
		setupProxyInstance();
	}

	/**
	 * 
	 * 描述：设置当前代理实例.
	 * 
	 * @author:daij 创建时间：2006-11-16
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
	 * 描述：塑造接口动态代理实例.
	 * 
	 * @param interfaces
	 *            待代理的接口列表
	 * @param invokHandler
	 *            接口代理的方法拦截类Handler
	 * @return Object Proxy的实例
	 * @author:daij 创建时间：2006-11-16
	 *              <p>
	 */
	protected static Object moldProxyInstance(Class[] interfaces,
			InvocationHandler invokHandler) {
		return Proxy.newProxyInstance(DirectorDynamicProxy.class
				.getClassLoader(), interfaces, invokHandler);
	}
}
