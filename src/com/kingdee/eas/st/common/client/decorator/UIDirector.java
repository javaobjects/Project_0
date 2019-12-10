/*
 * @(#)UIDirector.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.lang.reflect.InvocationHandler;
import java.util.EventObject;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.st.common.util.validate.IValidateBizLogicThrowable;

/**
 * ����: Ĭ�ϵ�UI�������̵ķ�����. ע��:
 * UIDirector�ṩILoadDataTemplate,IUIElementDecorator��ΪĬ�ϵķ���ģ��.
 * 
 * @author daij date:2006-11-22
 *         <p>
 * @version EAS5.2.0
 */
public class UIDirector extends AbstractUIDirector {

	/**
	 * Ĭ�ϵ�Builderģ��ӿ��б�.
	 */
	public static final Class[] defaultUIBuilderInterfaces = new Class[] {
			ILoadDataTemplate.class, IUIElementDecorator.class,
			IValidateBizLogicThrowable.class };

	/**
	 * �ӿڴ���ʵ����ILoadDataTemplate����
	 */
	private ILoadDataTemplate loadDataTemplate = null;

	/**
	 * �ӿڴ���ʵ����IUIElementDecorator����
	 */
	private IUIElementDecorator UIElementDecorator = null;

	/**
	 * �ӿڴ���ʵ����IValidateBizLogicThrowable����
	 */
	private IValidateBizLogicThrowable validateBizLogicThrowable = null;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-11-22
	 *              <p>
	 */
	public UIDirector() {
		super(defaultUIBuilderInterfaces);
	}

	/**
	 * ���������캯��
	 * 
	 * @param director
	 * @author:daij ����ʱ�䣺2006-11-22
	 *              <p>
	 */
	public UIDirector(InvocationHandler director) {
		super(defaultUIBuilderInterfaces, director);
	}

	/**
	 * 
	 * ������UIBuildDirector�ľ�̬����.
	 * 
	 * @param director
	 *            �ӿڴ���ķ���������Handler.
	 * @return UIBuildDirector
	 * @author:daij ����ʱ�䣺2006-11-17
	 *              <p>
	 */
	public static IUIDirector read(InvocationHandler director) {
		return new UIDirector(director);
	}

	/**
	 * 
	 * ��������¶�ӿڴ���ʵ����ILoadDataTemplate����
	 * 
	 * @return ILoadDataTemplate
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public ILoadDataTemplate loadDataTemplate() {
		if (loadDataTemplate == null) {
			loadDataTemplate = (proxyInstance instanceof ILoadDataTemplate) ? (ILoadDataTemplate) proxyInstance
					: null;
		}
		return loadDataTemplate;
	}

	/**
	 * 
	 * ��������¶�ӿڴ���ʵ����IUIElementDecorator����
	 * 
	 * @return IUIElementDecorator
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public IUIElementDecorator UIElementDecorator() {
		if (UIElementDecorator == null) {
			UIElementDecorator = (proxyInstance instanceof IUIElementDecorator) ? (IUIElementDecorator) proxyInstance
					: null;
		}
		return UIElementDecorator;
	}

	/**
	 * 
	 * ��������¶�ӿڴ���ʵ����IValidateBizLogicThrowable����
	 * 
	 * @return IValidateBizLogicThrowable
	 * @author:daij ����ʱ�䣺2006-11-27
	 *              <p>
	 */
	public IValidateBizLogicThrowable validateBizLogicThrowable() {
		if (validateBizLogicThrowable == null) {
			validateBizLogicThrowable = (proxyInstance instanceof IValidateBizLogicThrowable) ? (IValidateBizLogicThrowable) proxyInstance
					: null;
		}
		return validateBizLogicThrowable;
	}

	/**
	 * 
	 * ��������ָ��Director�󶨵�ILoadDataTemplate��.
	 * 
	 * @param director
	 *            �����ֲ�����
	 * @return ILoadDataTemplate
	 * @author:daij ����ʱ�䣺2006-11-17
	 *              <p>
	 */
	public static ILoadDataTemplate loadDataTemplate(InvocationHandler director) {
		Object proxyInstance = moldProxyInstance(
				new Class[] { ILoadDataTemplate.class }, director);
		return (ILoadDataTemplate) proxyInstance;
	}

	/**
	 * 
	 * ��������ָ��Director�󶨵�IUIElementDecorator��.
	 * 
	 * @param director
	 *            �����ֲ�����
	 * @return ILoadDataTemplate
	 * @author:daij ����ʱ�䣺2006-11-17
	 *              <p>
	 */
	public static IUIElementDecorator UIElementDecorator(
			InvocationHandler director) {
		Object proxyInstance = moldProxyInstance(
				new Class[] { IUIElementDecorator.class }, director);
		return (IUIElementDecorator) proxyInstance;
	}

	/**
	 * 
	 * ��������ָ��Director�󶨵�IValidateBizLogicThrowable��.
	 * 
	 * @param director
	 *            �����ֲ�����
	 * @return IValidateBizLogicThrowable
	 * @author:daij ����ʱ�䣺2006-11-27
	 *              <p>
	 */
	public static IValidateBizLogicThrowable validateBizLogicThrowable(
			InvocationHandler director) {
		Object proxyInstance = moldProxyInstance(
				new Class[] { IValidateBizLogicThrowable.class }, director);
		return (IValidateBizLogicThrowable) proxyInstance;
	}

	/**
	 * 
	 * ����������UI�󶨵�����. ע��: UIDirectorĬ�ϰ�ITrimDataTemplate�ӿ�����UI�󶨵�editData.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.decorator.IUIDirector#trimData()
	 */
	public void trimData() throws Exception {
		if (STQMUtils.isNotNull(loadDataTemplate())) {

			// ����UI�󶨵�����.
			loadDataTemplate.trimEditData();
		}
	}

	/**
	 * 
	 * ����������UI Element
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.decorator.IUIDirector#setupUIElement()
	 */
	public void setupUIElement() throws Exception {
		if (STQMUtils.isNotNull(UIElementDecorator())) {

			// ����������Ԫ�صĲ���
			UIElementDecorator.trimUIToolBarLayout();
			// �����˵�Ԫ�صĲ���
			UIElementDecorator.trimUIMenuBarLayout();
			// ��װUI Element
			UIElementDecorator.setupUIElement();
		}
	}

	/**
	 * 
	 * ����������UI Element ע��: UIDirectorĬ�ϰ�IUIElementDecorator�ӿ�����UI Elemnet.
	 * 
	 * @author:daij
	 * @see template.IUIDirector#decorateUIElement()
	 */
	public void decorateUIElement() throws Exception {
		if (STQMUtils.isNotNull(UIElementDecorator())) {

			// ����UI Element�Ŀɼ���.
			UIElementDecorator.decorateUIVisible();
			// ����UI Element�Ŀ�����.
			UIElementDecorator.decorateUIEnable();
		}
	}

	/**
	 * 
	 * ������UI����װ�� ע��: UIDirectorĬ�ϰ�ILoadDataTemplate�ӿ�װ��UI����.
	 * 
	 * @author:daij
	 * @see template.IUIDirector#loadData()
	 */
	public void loadData() throws Exception {
		if (STQMUtils.isNotNull(loadDataTemplate())) {

			// ɾ��UI������Ŀؼ�Listener.
			loadDataTemplate.deleteUIElementListeners();
			// װ��editData��UI Elemnet.
			loadDataTemplate.editDataToUIElement();
			// �ָ�UI������Ŀؼ�Listener.
			loadDataTemplate.addUIElementListeners();
		}
	}

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
	public void proxyActionEvent(EventObject event) throws Exception {
		// TODO �ڿӴ���
	}

	/**
	 * 
	 * ��������֤ҵ���߼�
	 * 
	 * @param data
	 *            ����֤��ֵ����Info
	 * @return boolean �Ƿ���֤ͨ��
	 * @throws Exception
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.decorator.IUIDirector#validateBizLogic(com.kingdee.bos.dao.IObjectValue)
	 */
	public void validateBizLogic(IObjectValue data) throws Exception {
		if (STQMUtils.isNotNull(validateBizLogicThrowable())) {
			validateBizLogicThrowable.validateBizLogic(data);
		}
	}
}
