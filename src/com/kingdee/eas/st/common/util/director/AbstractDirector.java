/*
 * 创建日期 2006-11-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 
 * 描述: 分离策略容器.
 * 
 * 1. 抽象为分类和分步两组, 支持分类中分步, 分步中分类 => 复杂的情况可以层层叠加组合起来,以及两种类型进行转化. 2. 常用的是:
 * 最外层的壳是分步处理器, 其中把XXXUI作为公共的处理策略, 然后再按业务类型制作分类处理器添加到分步处理器中. 3.
 * 在接口中需要提供方便定位到容器中某个元素的方法.
 * 
 * @author daij date:2006-11-16
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractDirector implements InvocationHandler {

	/**
	 * 当前的处理策略
	 */
	protected Object builderStrategy = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public AbstractDirector() {
		super();
	}

	/**
	 * 
	 * 描述：放入新的构建策略.
	 * 
	 * @param builderStrategy
	 *            Builder处理策略.
	 * @author:daij 创建时间：2006-11-16
	 *              <p>
	 */
	public abstract void addBuildStrategy(Object builderStrategy);

	/**
	 * 
	 * 描述：分离方法调用. 职责: 1.施加分步和分类处理. 2.转发方法调用
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
				// 如果仍然是导演那么就把invoke主导权转交给他.
				result = ((AbstractDirector) builderStrategy).invoke(proxy,
						method, args);
			} else if (STQMUtils.isNotNull(builderStrategy)) {
				// 鉴定builderStrategy是否支持method
				if (method.getDeclaringClass().isAssignableFrom(
						builderStrategy.getClass())) {
					// 如果已经是具体的Builder处理策略则转发具体方法调用.
					try {
						result = method.invoke(builderStrategy, args);
					} catch (Exception e) {
						if (STQMUtils.isNull(e.getCause())) {
							// 如果没有原始异常，那么则抛出方法定位异常.
							throw e;
						} else {
							// 把定位方法异常摘掉，只把原始异常转发出去.
							throw e.getCause();
						}
					}
				}
			}
		}
		return result;
	}
}
