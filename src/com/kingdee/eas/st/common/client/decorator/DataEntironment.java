/*
 * @(#)DataEntironment.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

import java.util.Map;
import java.util.Observable;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * 描述: 封装UI修饰模型中需要处理的数据.
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public class DataEntironment extends Observable {

	/**
	 * 上级UI传入的UIContext.
	 */
	protected Map uiContext = null;

	/**
	 * 与XXXUI绑定的数据值对象.
	 */
	protected IObjectValue editData = null;

	/**
	 * 被修饰的UI Object存根.
	 * 
	 * 1.用于访问UI Element 2.用于方法回调(会破坏class的清晰度,不推荐)
	 */
	protected CoreUI uiObject = null;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public DataEntironment() {
		super();
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param uiContext
	 *            上级UI传入的UIContext.
	 * @param editData
	 *            与XXXUI绑定的数据值对象.
	 * @param uiObject
	 *            被修饰的UI Object存根.
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public DataEntironment(Map uiContext, IObjectValue editData, CoreUI uiObject) {

		super();

		this.editData = editData;

		this.uiContext = uiContext;

		this.uiObject = uiObject;
	}

	/**
	 * 描述:@return 返回 editData。
	 */
	public IObjectValue getEditData() {
		return editData;
	}

	/**
	 * 描述:设置editData的值。
	 * 
	 * @param editData
	 */
	public void setEditData(IObjectValue editData) {
		this.editData = editData;

		this.dataEntironmentChanged();
	}

	/**
	 * 描述:@return 返回 uiContext。
	 */
	public Map getUIContext() {
		return uiContext;
	}

	/**
	 * 描述:设置uiContext的值。
	 * 
	 * @param uiContext
	 */
	public void setUIContext(Map uiContext) {
		this.uiContext = uiContext;

		this.dataEntironmentChanged();
	}

	/**
	 * 描述:@return 返回 uiObject。
	 */
	public CoreUI getUIObject() {
		return uiObject;
	}

	/**
	 * 描述:设置uiObject的值。
	 * 
	 * @param uiObject
	 */
	public void setUIObject(CoreUI uiObject) {
		this.uiObject = uiObject;

		this.dataEntironmentChanged();
	}

	/**
	 * 
	 * 描述：当前处理的数据包发生的改变.
	 * 
	 * @author:daij
	 * @see java.util.Observable#setChanged()
	 */
	protected void dataEntironmentChanged() {
		// 标记当前数据数据环境已经发生变化
		setChanged();
		// 通知所有数据环境的使用者
		notifyObservers(this);
	}
}
