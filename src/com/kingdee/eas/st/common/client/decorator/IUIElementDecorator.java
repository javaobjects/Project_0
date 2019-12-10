/*
 * @(#)IUIElementDecorator.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * 描述: UI Elemnet修饰模板. 职责: 1.为UI Element修饰提供统一的方法签名.
 * 2.此模板接口可直接由XXXUI实现或由用于分离UI修饰的Builder实现.
 * 
 * 注意: 1. 在本模板中只对UI Element进行修饰和调整,不能对editData进行任何设置/改动和处理.
 * 
 * @author daij date:2006-11-3
 *         <p>
 * @version EAS5.2.0
 */
public interface IUIElementDecorator {

	/**
	 * 
	 * 描述：调整菜单布局. 职责: 根据各业务需求对菜单布局进行增减, 调整.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void trimUIMenuBarLayout() throws Exception;

	/**
	 * 
	 * 描述：调整工具栏布局. 职责: 根据各业务需求对工具栏布局进行增减, 调整.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void trimUIToolBarLayout() throws Exception;

	/**
	 * 
	 * 描述：安装UI Element. 诸如特殊F7的设置,KDTable上的列宽, 必录项颜色, 数值类字段的精度设置等等.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void setupUIElement() throws Exception;

	/**
	 * 
	 * 描述：根据各业务需求对UI Element的可见性进行调整.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIVisible() throws Exception;

	/**
	 * 
	 * 描述：根据各业务需求对UI Element的可用性进行调整.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIEnable() throws Exception;
}
