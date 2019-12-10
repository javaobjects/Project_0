/*
 * 创建日期 2006-11-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.st.common.util.director;

import java.lang.reflect.Method;
import java.util.LinkedList;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 
 * 描述: 分类处理容器.
 * 
 * @author daij date:2006-11-17
 *         <p>
 * @version EAS5.2.0
 */
public class StepDirector extends AbstractDirector {

	/**
	 * 分步骤处理策略列表:
	 * 
	 * 1.按顺序填入IUIDirector, ILoadDataTemplate, IUIElementDecorator的Builder实现.
	 * 2.允许重复,要求顺序,后进先出处理原则.
	 */
	protected LinkedList builderStrategies = new LinkedList();

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public StepDirector() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param strategies
	 *            分步处理策略列表.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public StepDirector(Object[] strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * 添加新的Builder策略
	 * 
	 * @see template.AbstractDirector#addBuilderStrategy(java.lang.Object)
	 */
	public void addBuildStrategy(Object builderStrategy) {
		builderStrategies.add(builderStrategy);
	}

	/**
	 * 
	 * 描述：批量放入新的分步处理策略.
	 * 
	 * @param strategies
	 *            分步处理策略列表.
	 * @author:daij 创建时间：2006-11-6
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
	 * 描述：转发代理方法调用. 职责: 1.施加分步和分类处理. 2.转发方法调用
	 * 
	 * @author:daij
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		for (int i = 0, size = builderStrategies.size(); i < size; i++) {
			// 迭代当前处理的Builder策略
			builderStrategy = builderStrategies.get(i);
			// 转发方法调用.
			result = super.invoke(proxy, method, args);
		}
		return result;
	}
}
