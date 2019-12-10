/*
 * 创建日期 2006-11-15
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.st.common.client.decorator;

import java.lang.reflect.InvocationHandler;
import java.util.Observable;
import java.util.Observer;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.st.common.util.director.DirectorDynamicProxy;

/**
 * 
 * 描述: UI界面流程的分离器. 职责: 把UI上的流程操作按业务逻辑分离到分类和分步两种容器中的各策略类上.
 * 
 * 注意: 1. UIDirector是默认的分离器实现,提供ILoadDataTemplate,IUIElementDecorator作为默认的分离模板.
 * 2. 如果认为默认的模板方法不适用,希望使用自定义的分离模板接口,那么需要如下步骤: 2.1)
 * 定义子类XXXUIDirector继承AbstractUIDirector. 2.2)
 * 调用addProxyInterfaces方法增加自定义模板接口到AbstractUIDirector代理的接口列表. 2.3)
 * 实现decorateUIElement,
 * loadData方法调用自定义模板的方法(因为AbstractUIDirector无法拿到强类型的自定义分离接口,所以只能延迟到子类.) 2.4)
 * 提供形如: ILoadDataTemplate loadDataTemplate()的方法,把自定义模板的接口曝露出去. 3.
 * UIDirector采用AbstractDirector(分类和分步容器)作为动态代理的方法拦截类,可以自定义方法拦截类Handler ->
 * 通过调用setDirector方法替换为自定义拦截类.
 * 
 * @author daij date:2006-11-16
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AbstractUIDirector extends DirectorDynamicProxy implements
		IUIDirector, ISetDataEntironmentCommand, Observer {

	/**
	 * 修饰的数据包.
	 */
	private DataEntironment dataEntironment = null;

	/**
	 * 代理的接口列表.
	 */
	public static final Class[] PROXYINTERFACES = new Class[] { ISetDataEntironmentCommand.class };

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-16
	 *              <p>
	 */
	public AbstractUIDirector() {
		super(PROXYINTERFACES);
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
	public AbstractUIDirector(Class[] proxyInterfaces) {
		super(PROXYINTERFACES);

		addProxyInterfaces(proxyInterfaces);
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
	public AbstractUIDirector(InvocationHandler director) {
		super(PROXYINTERFACES, director);
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param proxyInterfaces
	 *            代理的接口列表.
	 * @param director
	 *            接口代理的方法拦截类Handler.
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public AbstractUIDirector(Class[] proxyInterfaces,
			InvocationHandler director) {
		super(PROXYINTERFACES, director);

		addProxyInterfaces(proxyInterfaces);
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
	public final void loadDataEntironment(DataEntironment dataEntironment) {

		if (STQMUtils.isNull(dataEntironment))
			return;

		// 延迟初始化dataEntironment
		this.initializeDataEntironment();

		this.dataEntironment.editData = dataEntironment.getEditData();

		this.dataEntironment.uiContext = dataEntironment.getUIContext();

		this.dataEntironment.uiObject = dataEntironment.getUIObject();

		if (proxyInstance instanceof ISetDataEntironmentCommand) {
			((ISetDataEntironmentCommand) proxyInstance)
					.loadDataEntironment(this.dataEntironment);
		}
	}

	/**
	 * 描述:@return 返回 dataEntironment。
	 */
	public DataEntironment dataEntironment() {
		initializeDataEntironment();

		return this.dataEntironment;
	}

	private void initializeDataEntironment() {
		if (STQMUtils.isNull(this.dataEntironment)) {
			this.dataEntironment = new DataEntironment();

			this.dataEntironment.addObserver(this);
		}
	}

	/**
	 * 
	 * 描述：数据环境发生改变，需要重新装载.
	 * 
	 * @author:daij
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (arg instanceof DataEntironment) {
			loadDataEntironment((DataEntironment) arg);
		}
	}
}
