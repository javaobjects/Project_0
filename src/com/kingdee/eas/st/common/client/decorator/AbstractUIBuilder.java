/*
 * @(#)AbstractUIBuilder.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: ʵ��UI���ε�Builder
 * 
 * @author daij date:2006-11-8
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractUIBuilder implements ISetDataEntironmentCommand,
		PropertyChangeListener {

	/**
	 * ���ݻ�������: UIContext, editData, XXXXUI.
	 */
	protected DataEntironment dataEntironment = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	public AbstractUIBuilder() {
		super();
	}

	/**
	 * 
	 * ������װ��DataEntironment.
	 * 
	 * @param dataEntironment
	 *            ���ε����ݰ�.
	 * @author:daij ����ʱ�䣺2006-11-8
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
	 * ����������UIContext
	 * 
	 * @return Map �ϼ�UI�����UIContext.
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	protected final Map getUIContext() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getUIContext();
	}

	/**
	 * 
	 * ������������XXXUI�󶨵�����ֵ����.
	 * 
	 * @return IObjectValue
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	protected final IObjectValue getEditData() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getEditData();
	}

	/**
	 * 
	 * �����������ε�UI Object���.
	 * 
	 * @return CoreUI
	 * @author:daij ����ʱ�䣺2006-11-8
	 *              <p>
	 */
	protected final CoreUI getUIObject() {
		return (STQMUtils.isNull(dataEntironment)) ? null : dataEntironment
				.getUIObject();
	}

	/**
	 * 
	 * ��������ǰ������Ըı�ʱ�Ĵ���.
	 * 
	 * @author:daij
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (STQMUtils.isNotNull(evt)
				&& AssortDirector.LISTENER_KEY_BUILDSTRATEGYCHANGED.equals(evt
						.getPropertyName())) {
			// �ɴ������
			Object oldStrategy = evt.getOldValue();
			// �´������
			Object newStrategy = evt.getNewValue();
			// �Ӿɲ��Զ����ݻ������²���
			if (oldStrategy instanceof AbstractUIBuilder
					&& newStrategy instanceof AbstractUIBuilder) {
				((AbstractUIBuilder) newStrategy)
						.loadDataEntironment(((AbstractUIBuilder) oldStrategy).dataEntironment);
			}
		}
	}
}
