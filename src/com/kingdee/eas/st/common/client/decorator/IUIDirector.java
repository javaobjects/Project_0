/*
 * @(#)IUIDirector.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.util.EventObject;

import com.kingdee.bos.dao.IObjectValue;

/**
 * ����:
 * 
 * @author daij date:2006-11-6
 *         <p>
 * @version EAS5.2.0
 */
public interface IUIDirector {

	/**
	 * 
	 * ����������UI�󶨵�����.
	 * 
	 * @author:daij ����ʱ�䣺2006-11-22
	 *              <p>
	 */
	public abstract void trimData() throws Exception;

	/**
	 * 
	 * ����������UI Element
	 * 
	 * @author:daij ����ʱ�䣺2006-11-24
	 *              <p>
	 */
	public abstract void setupUIElement() throws Exception;

	/**
	 * 
	 * ����������UI Element
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIElement() throws Exception;

	/**
	 * 
	 * ������UI����װ��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void loadData() throws Exception;

	/**
	 * 
	 * ����������UI Element���¼�����.
	 * 
	 * @param event
	 *            ������¼�
	 * 
	 * @author:daij ����ʱ�䣺2006-11-6
	 *              <p>
	 */
	public abstract void proxyActionEvent(EventObject event) throws Exception;

	/**
	 * 
	 * ��������֤ҵ���߼�
	 * 
	 * @param data
	 *            ����֤��ֵ����Info
	 * @return boolean �Ƿ���֤ͨ��
	 * @throws Exception
	 * @author:daij ����ʱ�䣺2006-11-27
	 *              <p>
	 */
	public abstract void validateBizLogic(IObjectValue data) throws Exception;
}
