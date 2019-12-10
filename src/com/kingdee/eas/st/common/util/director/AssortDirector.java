/*
 * �������� 2006-11-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
 * ����: ���ദ������. �����ṩĬ�ϵĴ������.
 * 
 * @author daij date:2006-11-17
 *         <p>
 * @version EAS5.2.0
 */
public class AssortDirector extends AbstractDirector {

	/**
	 * ��ǰ�Ĵ�����Ըı�������ؼ���.
	 */
	public static final String LISTENER_KEY_BUILDSTRATEGYCHANGED = "builderStrategy";

	/**
	 * ���ദ������б�: 1. IUIDirector, ILoadDataTemplate,
	 * IUIElementDecorator��Builderʵ��. 2. �������ظ�, Map�е�ÿ��Ԫ�ض��Ƕ����Ĵ���Strategy.
	 */
	protected Map buildStrategies = new HashMap();

	/**
	 * ��ǰ�Ĵ�����Ըı�ʱ�������Ըı�Event.
	 */
	private PropertyChangeSupport changeSupport = null;

	/**
	 * 
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public AssortDirector() {
		super();
	}

	/**
	 * 
	 * ���������캯�� ע��: Ĭ�ϵ�keyΪString: Strategy.class.getName();
	 * 
	 * @param strategies
	 *            ���ദ������б�.
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public AssortDirector(Object[] strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param strategies
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public AssortDirector(Map strategies) {
		super();
		addBuildStrategies(strategies);
	}

	/**
	 * �����µ�Builder����
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
	 * �����������µķ��ദ�����.
	 * 
	 * @param strategyKey
	 *            ������Թؼ���.
	 * @param strategy
	 *            ���ദ�����.
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final void addBuildStrategy(Object strategyKey, Object strategy) {
		if (!isExists(strategyKey)) {
			// Build������Ҫ��ص�ǰ������Ըı���������Ըı������.
			if (strategy instanceof PropertyChangeListener) {
				this
						.addPropertyChangeListener((PropertyChangeListener) strategy);
			}
			// �����������.
			buildStrategies.put(strategyKey, strategy);
		}
	}

	/**
	 * 
	 * ���������������µķ��ദ�����. ע��: Ĭ�ϵ�keyΪString: Strategy.class.getName();
	 * 
	 * @param strategies
	 *            ���ദ������б�.
	 * @author:daij ����ʱ�䣺2006-11-6
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
	 * ���������������µķ��ദ�����.
	 * 
	 * @param strategies
	 *            ���ദ������б�.
	 * @author:daij ����ʱ�䣺2006-11-6
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
	 * �������������еķ��ദ�����.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final void clearBuildStrategy() {
		buildStrategies.clear();
	}

	/**
	 * 
	 * ������ɾ��ĳ�����ദ�����.
	 * 
	 * @param strategyKey
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final void deleteBuildStrategy(Object strategyKey) {
		if (isExists(strategyKey)) {
			// ��ȥ�����Ըı������.
			Object listener = buildStrategies.get(strategyKey);
			if (listener instanceof PropertyChangeListener) {
				removePropertyChangeListener((PropertyChangeListener) listener);
			}
			// �������ɵ�Build����.
			buildStrategies.remove(strategyKey);
		}
	}

	/**
	 * 
	 * �������ı䵱ǰ�ķ��ദ�����.
	 * 
	 * @param strategyKey
	 *            ������Թؼ���.
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public void changeBuildStrategy(Object strategyKey) {
		if (isExists(strategyKey)) {
			// ���ǰ������Ըı��¼�.
			if (STQMUtils.isNotNull(changeSupport)) {
				changeSupport.firePropertyChange(
						LISTENER_KEY_BUILDSTRATEGYCHANGED,
						this.builderStrategy, buildStrategies.get(strategyKey));
			}
			// �ı䵱ǰ�ķ��ദ�����.
			this.builderStrategy = buildStrategies.get(strategyKey);
		} else {
			// TODO �׳�û���ҵ�ָ���Ĵ�������쳣.
		}
	}

	/**
	 * 
	 * �������Ƿ��Ѵ���ĳ�������.
	 * 
	 * @param strategyKey
	 *            ������Թؼ���.
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final boolean isExists(Object strategyKey) {
		return ((STQMUtils.isNotNull(strategyKey)) && buildStrategies
				.containsKey(strategyKey));
	}

	/**
	 * 
	 * ���������ദ������б�Ĺؼ��ֵ�����.
	 * 
	 * @return Iterator
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public final Iterator buildStrategyKeyIterator() {
		return buildStrategies.keySet().iterator();
	}

	/**
	 * 
	 * ������������Ըı������
	 * 
	 * @param listener
	 *            ���Ըı������
	 * @author:daij ����ʱ�䣺2006-11-20
	 *              <p>
	 */
	protected void addPropertyChangeListener(PropertyChangeListener listener) {
		// ʵ�������Ըı����������
		if (STQMUtils.isNull(changeSupport))
			changeSupport = new PropertyChangeSupport(this);
		// �������Ըı������.
		changeSupport.addPropertyChangeListener(
				LISTENER_KEY_BUILDSTRATEGYCHANGED, listener);
	}

	/**
	 * 
	 * ������ɾ�����Ըı������
	 * 
	 * @param listener
	 *            ���Ըı������
	 * @author:daij ����ʱ�䣺2006-11-20
	 *              <p>
	 */
	protected void removePropertyChangeListener(PropertyChangeListener listener) {
		if (STQMUtils.isNotNull(changeSupport)) {
			changeSupport.removePropertyChangeListener(
					LISTENER_KEY_BUILDSTRATEGYCHANGED, listener);
		}
	}
}
