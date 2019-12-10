/*
 * 创建日期 2006-11-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.st.common.util.director;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 
 * 描述: 分类处理容器. 考虑提供默认的处理策略.
 * 
 * @author daij date:2006-11-17
 *         <p>
 * @version EAS5.2.0
 */
public class AssortDirector extends AbstractDirector {

	/**
	 * 当前的处理策略改变监听器关键字.
	 */
	public static final String LISTENER_KEY_BUILDSTRATEGYCHANGED = "builderStrategy";

	/**
	 * 分类处理策略列表: 1. IUIDirector, ILoadDataTemplate,
	 * IUIElementDecorator的Builder实现. 2. 不允许重复, Map中的每个元素都是独立的处理Strategy.
	 */
	protected Map buildStrategies = new HashMap();

	/**
	 * 当前的处理策略改变时发出属性改变Event.
	 */
	private PropertyChangeSupport changeSupport = null;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public AssortDirector() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数 注意: 默认的key为String: Strategy.class.getName();
	 * 
	 * @param strategies
	 *            分类处理策略列表.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public AssortDirector(Object[] strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param strategies
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public AssortDirector(Map strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * 放入新的Builder策略
	 * 
	 * @see template.AbstractDirector#addBuilderStrategy(java.lang.Object)
	 */
	public void addBuildStrategy(Object builderStrategy) {
		if (STQMUtils.isNotNull(builderStrategy)) {
			addBuildStrategy(builderStrategy.getClass().getName(),
					builderStrategy);
		}
	}

	/**
	 * 
	 * 描述：放入新的分类处理策略.
	 * 
	 * @param strategyKey
	 *            处理策略关键字.
	 * @param strategy
	 *            分类处理策略.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final void addBuildStrategy(Object strategyKey, Object strategy) {
		if (!isExists(strategyKey)) {
			// Build策略需要监控当前处理策略改变则添加属性改变监听器.
			if (strategy instanceof PropertyChangeListener) {
				this
						.addPropertyChangeListener((PropertyChangeListener) strategy);
			}
			// 放入分类容器.
			buildStrategies.put(strategyKey, strategy);
		}
	}

	/**
	 * 
	 * 描述：批量放入新的分类处理策略. 注意: 默认的key为String: Strategy.class.getName();
	 * 
	 * @param strategies
	 *            分类处理策略列表.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final void addBuildStrategies(Object[] strategies) {
		if (STQMUtils.isNotNull(strategies) && strategies.length != 0) {
			Object o = null;
			for (int i = 0, size = strategies.length; i < size; i++) {
				o = strategies[i];
				if (STQMUtils.isNotNull(o)) {
					addBuildStrategy(o.getClass().getName(), o);
				}
			}
		}
	}

	/**
	 * 
	 * 描述：批量放入新的分类处理策略.
	 * 
	 * @param strategies
	 *            分类处理策略列表.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final void addBuildStrategies(Map strategies) {
		if (STQMUtils.isNotNull(strategies) && strategies.size() != 0) {
			Iterator itor = strategies.keySet().iterator();

			Object key = null;
			while (itor.hasNext()) {
				key = itor.next();

				addBuildStrategy(key, strategies.get(key));
			}
		}
	}

	/**
	 * 
	 * 描述：清理所有的分类处理策略.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final void clearBuildStrategy() {
		buildStrategies.clear();
	}

	/**
	 * 
	 * 描述：删除某个分类处理策略.
	 * 
	 * @param strategyKey
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final void deleteBuildStrategy(Object strategyKey) {
		if (isExists(strategyKey)) {
			// 先去除属性改变监听器.
			Object listener = buildStrategies.get(strategyKey);
			if (listener instanceof PropertyChangeListener) {
				removePropertyChangeListener((PropertyChangeListener) listener);
			}
			// 从容器干掉Build策略.
			buildStrategies.remove(strategyKey);
		}
	}

	/**
	 * 
	 * 描述：改变当前的分类处理策略.
	 * 
	 * @param strategyKey
	 *            处理策略关键字.
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public void changeBuildStrategy(Object strategyKey) {
		if (isExists(strategyKey)) {
			// 激活当前处理策略改变事件.
			if (STQMUtils.isNotNull(changeSupport)) {
				changeSupport.firePropertyChange(
						LISTENER_KEY_BUILDSTRATEGYCHANGED,
						this.builderStrategy, buildStrategies.get(strategyKey));
			}
			// 改变当前的分类处理策略.
			this.builderStrategy = buildStrategies.get(strategyKey);
		} else {
			// TODO 抛出没有找到指定的处理策略异常.
		}
	}

	/**
	 * 
	 * 描述：是否已存在某处理策略.
	 * 
	 * @param strategyKey
	 *            处理策略关键字.
	 * @return boolean
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final boolean isExists(Object strategyKey) {
		return ((STQMUtils.isNotNull(strategyKey)) && buildStrategies
				.containsKey(strategyKey));
	}

	/**
	 * 
	 * 描述：分类处理策略列表的关键字迭代器.
	 * 
	 * @return Iterator
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public final Iterator buildStrategyKeyIterator() {
		return buildStrategies.keySet().iterator();
	}

	/**
	 * 
	 * 描述：添加属性改变监听器
	 * 
	 * @param listener
	 *            属性改变监听器
	 * @author:daij 创建时间：2006-11-20
	 *              <p>
	 */
	protected void addPropertyChangeListener(PropertyChangeListener listener) {
		// 实例化属性改变监听管理器
		if (STQMUtils.isNull(changeSupport))
			changeSupport = new PropertyChangeSupport(this);
		// 放入属性改变监听器.
		changeSupport.addPropertyChangeListener(
				LISTENER_KEY_BUILDSTRATEGYCHANGED, listener);
	}

	/**
	 * 
	 * 描述：删除属性改变监听器
	 * 
	 * @param listener
	 *            属性改变监听器
	 * @author:daij 创建时间：2006-11-20
	 *              <p>
	 */
	protected void removePropertyChangeListener(PropertyChangeListener listener) {
		if (STQMUtils.isNotNull(changeSupport)) {
			changeSupport.removePropertyChangeListener(
					LISTENER_KEY_BUILDSTRATEGYCHANGED, listener);
		}
	}
}
