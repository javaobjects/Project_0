/*
 * @(#)IUIDirector.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.util.EventObject;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 描述:
 * 
 * @author daij date:2006-11-6
 *         <p>
 * @version EAS5.2.0
 */
public interface IUIDirector {

	/**
	 * 
	 * 描述：整理UI绑定的数据.
	 * 
	 * @author:daij 创建时间：2006-11-22
	 *              <p>
	 */
	public abstract void trimData() throws Exception;

	/**
	 * 
	 * 描述：设置UI Element
	 * 
	 * @author:daij 创建时间：2006-11-24
	 *              <p>
	 */
	public abstract void setupUIElement() throws Exception;

	/**
	 * 
	 * 描述：修饰UI Element
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void decorateUIElement() throws Exception;

	/**
	 * 
	 * 描述：UI数据装载
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void loadData() throws Exception;

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
	public abstract void proxyActionEvent(EventObject event) throws Exception;

	/**
	 * 
	 * 描述：验证业务逻辑
	 * 
	 * @param data
	 *            待验证的值对象Info
	 * @return boolean 是否验证通过
	 * @throws Exception
	 * @author:daij 创建时间：2006-11-27
	 *              <p>
	 */
	public abstract void validateBizLogic(IObjectValue data) throws Exception;
}
