/*
 * @(#)UIDirector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.lang.reflect.InvocationHandler;
import java.util.EventObject;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.st.common.util.validate.IValidateBizLogicThrowable;

/**
 * 描述: 默认的UI界面流程的分离器. 注意:
 * UIDirector提供ILoadDataTemplate,IUIElementDecorator作为默认的分离模板.
 * 
 * @author daij date:2006-11-22
 *         <p>
 * @version EAS5.2.0
 */
public class UIDirector extends AbstractUIDirector {

	/**
	 * 默认的Builder模板接口列表.
	 */
	public static final Class[] defaultUIBuilderInterfaces = new Class[] {
			ILoadDataTemplate.class, IUIElementDecorator.class,
			IValidateBizLogicThrowable.class };

	/**
	 * 接口代理实例的ILoadDataTemplate切面
	 */
	private ILoadDataTemplate loadDataTemplate = null;

	/**
	 * 接口代理实例的IUIElementDecorator切面
	 */
	private IUIElementDecorator UIElementDecorator = null;

	/**
	 * 接口代理实例的IValidateBizLogicThrowable切面
	 */
	private IValidateBizLogicThrowable validateBizLogicThrowable = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-22
	 *              <p>
	 */
	public UIDirector() {
		super(defaultUIBuilderInterfaces);
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param director
	 * @author:daij 创建时间：2006-11-22
	 *              <p>
	 */
	public UIDirector(InvocationHandler director) {
		super(defaultUIBuilderInterfaces, director);
	}

	/**
	 * 
	 * 描述：UIBuildDirector的静态构建.
	 * 
	 * @param director
	 *            接口代理的方法拦截类Handler.
	 * @return UIBuildDirector
	 * @author:daij 创建时间：2006-11-17
	 *              <p>
	 */
	public static IUIDirector read(InvocationHandler director) {
		return new UIDirector(director);
	}

	/**
	 * 
	 * 描述：曝露接口代理实例的ILoadDataTemplate切面
	 * 
	 * @return ILoadDataTemplate
	 * @author:daij 创建时间：2006-11-16
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
	 * 描述：曝露接口代理实例的IUIElementDecorator切面
	 * 
	 * @return IUIElementDecorator
	 * @author:daij 创建时间：2006-11-16
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
	 * 描述：曝露接口代理实例的IValidateBizLogicThrowable切面
	 * 
	 * @return IValidateBizLogicThrowable
	 * @author:daij 创建时间：2006-11-27
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
	 * 描述：把指定Director绑定到ILoadDataTemplate上.
	 * 
	 * @param director
	 *            分类或分步导演
	 * @return ILoadDataTemplate
	 * @author:daij 创建时间：2006-11-17
	 *              <p>
	 */
	public static ILoadDataTemplate loadDataTemplate(InvocationHandler director) {
		Object proxyInstance = moldProxyInstance(
				new Class[] { ILoadDataTemplate.class }, director);
		return (ILoadDataTemplate) proxyInstance;
	}

	/**
	 * 
	 * 描述：把指定Director绑定到IUIElementDecorator上.
	 * 
	 * @param director
	 *            分类或分步导演
	 * @return ILoadDataTemplate
	 * @author:daij 创建时间：2006-11-17
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
	 * 描述：把指定Director绑定到IValidateBizLogicThrowable上.
	 * 
	 * @param director
	 *            分类或分步导演
	 * @return IValidateBizLogicThrowable
	 * @author:daij 创建时间：2006-11-27
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
	 * 描述：整理UI绑定的数据. 注意: UIDirector默认按ITrimDataTemplate接口整理UI绑定的editData.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.decorator.IUIDirector#trimData()
	 */
	public void trimData() throws Exception {
		if (STQMUtils.isNotNull(loadDataTemplate())) {

			// 整理UI绑定的数据.
			loadDataTemplate.trimEditData();
		}
	}

	/**
	 * 
	 * 描述：设置UI Element
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.decorator.IUIDirector#setupUIElement()
	 */
	public void setupUIElement() throws Exception {
		if (STQMUtils.isNotNull(UIElementDecorator())) {

			// 调整工具栏元素的布局
			UIElementDecorator.trimUIToolBarLayout();
			// 调整菜单元素的布局
			UIElementDecorator.trimUIMenuBarLayout();
			// 安装UI Element
			UIElementDecorator.setupUIElement();
		}
	}

	/**
	 * 
	 * 描述：修饰UI Element 注意: UIDirector默认按IUIElementDecorator接口修饰UI Elemnet.
	 * 
	 * @author:daij
	 * @see template.IUIDirector#decorateUIElement()
	 */
	public void decorateUIElement() throws Exception {
		if (STQMUtils.isNotNull(UIElementDecorator())) {

			// 调整UI Element的可见性.
			UIElementDecorator.decorateUIVisible();
			// 调整UI Element的可用性.
			UIElementDecorator.decorateUIEnable();
		}
	}

	/**
	 * 
	 * 描述：UI数据装载 注意: UIDirector默认按ILoadDataTemplate接口装载UI数据.
	 * 
	 * @author:daij
	 * @see template.IUIDirector#loadData()
	 */
	public void loadData() throws Exception {
		if (STQMUtils.isNotNull(loadDataTemplate())) {

			// 删除UI界面里的控件Listener.
			loadDataTemplate.deleteUIElementListeners();
			// 装载editData到UI Elemnet.
			loadDataTemplate.editDataToUIElement();
			// 恢复UI界面里的控件Listener.
			loadDataTemplate.addUIElementListeners();
		}
	}

	/**
	 * 
	 * 描述：代理UI Element的事件处理.
	 * 
	 * @param event
	 *            代理的事件
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public void proxyActionEvent(EventObject event) throws Exception {
		// TODO 挖坑待埋
	}

	/**
	 * 
	 * 描述：验证业务逻辑
	 * 
	 * @param data
	 *            待验证的值对象Info
	 * @return boolean 是否验证通过
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
