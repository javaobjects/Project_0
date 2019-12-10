/*
 * @(#)NamingWriteBackAcition.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;

/**
 * 描述: 封装命名的反写操作
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public final class NamingWriteBackAction extends AbstractAction implements
		Serializable {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = 5796923672146472855L;

	/**
	 * Action的名称.
	 */
	private String name = NamingWriteBackAction.class.getName();

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	private NamingWriteBackAction() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param actionName
	 *            Action的名称
	 * @author:daij 创建时间：2006-12-1
	 *              <p>
	 */
	public NamingWriteBackAction(String actionName) {
		super();

		this.name = actionName;
	}

	/**
	 * 描述:@return 返回 name。
	 */
	public String name() {
		return name;
	}

	/**
	 * 描述：输出Action的名称.
	 * 
	 * @author:daij
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name();
	}
}
