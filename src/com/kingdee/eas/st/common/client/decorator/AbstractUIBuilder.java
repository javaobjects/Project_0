/*
 * @(#)AbstractUIBuilder.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.st.common.util.director.AssortDirector;

/**
 * 描述: 实现UI修饰的Builder
 * 
 * @author daij date:2006-11-8
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractUIBuilder implements ISetDataEntironmentCommand,
		PropertyChangeListener {

	/**
	 * 数据环境包括: UIContext, editData, XXXXUI.
	 */
	protected DataEntironment dataEntironment = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	public AbstractUIBuilder() {
		super();
	}

	/**
	 * 
	 * 描述：装载DataEntironment.
	 * 
	 * @param dataEntironment
	 *            修饰的数据包.
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	public void loadDataEntironment(DataEntironment dataEntironment) {
		if (STQMUtils.isNull(this.dataEntironment)) {
			this.dataEntironment = new DataEntironment();
		}

		this.dataEntironment.editData = dataEntironment.getEditData();

		this.dataEntironment.uiContext = dataEntironment.getUIContext();

		this.dataEntironment.uiObject = dataEntironment.getUIObject();
	}

	/**
	 * 
	 * 描述：返回UIContext
	 * 
	 * @return Map 上级UI传入的UIContext.
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	protected final Map getUIContext() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getUIContext();
	}

	/**
	 * 
	 * 描述：返回与XXXUI绑定的数据值对象.
	 * 
	 * @return IObjectValue
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	protected final IObjectValue getEditData() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getEditData();
	}

	/**
	 * 
	 * 描述：被修饰的UI Object存根.
	 * 
	 * @return CoreUI
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	protected final CoreUI getUIObject() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getUIObject();
	}

	/**
	 * 
	 * 描述：当前处理策略改变时的处理.
	 * 
	 * @author:daij
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (STQMUtils.isNotNull(evt)
				&& AssortDirector.LISTENER_KEY_BUILDSTRATEGYCHANGED.equals(evt
						.getPropertyName())) {
			// 旧处理策略
			Object oldStrategy = evt.getOldValue();
			// 新处理策略
			Object newStrategy = evt.getNewValue();
			// 从旧策略读数据环境到新策略
			if (oldStrategy instanceof AbstractUIBuilder
					&& newStrategy instanceof AbstractUIBuilder) {
				((AbstractUIBuilder) newStrategy)
						.loadDataEntironment(((AbstractUIBuilder) oldStrategy).dataEntironment);
			}
		}
	}
}
