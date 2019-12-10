/*
 * @(#)IloadData.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * 描述: UI数据装载模板. 职责: 1.为UI数据装载提供合理的顺序和方法签名.
 * 2.此模板接口可直接由XXXUI实现或由用于分离UI修饰的Builder实现.
 * 
 * 注意: 1. 在本模板中处理editData与UI Element的交互.
 * 
 * @author daij date:2006-11-3
 *         <p>
 * @version EAS5.2.0
 */
public interface ILoadDataTemplate {

	/**
	 * 
	 * 描述：整理UI绑定的数据. 职责: 1. 数据源: UIContext, editData. 2.
	 * 在super.loadFields前对UI即将装载的editData进行调整,输出调整后的editData. 3. 具体操作: 3.1)
	 * 根据UIContext中的上下文信息对editData数据进行调整. 3.2) 对editData进行默认值处理.
	 * 
	 * @author:daij 创建时间：2006-11-3
	 *              <p>
	 */
	public abstract void trimEditData() throws Exception;

	/**
	 * 
	 * 描述：删除UI界面里的控件Listener. 职责: 避免在super.loadFields和手工向控件绑定数据时激活控件事件响应.
	 * 
	 * @author:daij 创建时间：2006-11-3
	 *              <p>
	 */
	public abstract void deleteUIElementListeners() throws Exception;

	/**
	 * 
	 * 描述：装载editData到UI Elemnet. 职责: 包括两个部分
	 * 1.调用super.loadFields由应用框架的DataBinder装载editData到UI Element.
	 * 2.没有向DataBinder注册的数据项手工装载到UI Element.
	 * 
	 * @author:daij 创建时间：2006-11-3
	 *              <p>
	 */
	public abstract void editDataToUIElement() throws Exception;

	/**
	 * 
	 * 描述：恢复UI界面里的控件Listener.
	 * 
	 * @author:daij 创建时间：2006-11-3
	 *              <p>
	 */
	public abstract void addUIElementListeners() throws Exception;
}
