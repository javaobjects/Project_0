package com.kingdee.eas.st.common.listenerutils;

import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.base.IListenerExecute;

/**
 * 监听管理器，负责管理由此manager生成的所有监听
 * 
 * @author mingming_fan
 * 
 */
public class ListenerManager {
	private boolean clearData = true;
	private boolean eanbleListener = true;

	/**
	 * 是否清理数据，由子类实现决定，这里只是标志位
	 * 
	 * @return
	 */
	public boolean isClearData() {
		return clearData;
	}

	/**
	 * 是否清理数据，由子类实现决定，这里只是标志位
	 * 
	 * @param clearData
	 */
	public void setClearData(boolean clearData) {
		this.clearData = clearData;
	}

	/**
	 * 监听是否启用，由子类实现决定，这里只是标志位
	 * 
	 * @return
	 */
	public boolean isEanbleListener() {
		return eanbleListener;
	}

	/**
	 * 监听是否启用，由子类实现决定，这里只是标志位
	 * 
	 * @param eanbleListener
	 */
	public void setEanbleListener(boolean eanbleListener) {
		this.eanbleListener = eanbleListener;
	}

	/**
	 * 添加监听，并初始化构造.将自身注册到监听中，以便各监听统一使用自己的状态。 例如：是否清理数据，监听是否启用。
	 * 
	 * @param baseDataListener
	 * @return 返回生成的监听，由于生成的监听种类各异，故返回Object由使用人自行转换
	 */
	public Object addListener(BaseDataListener baseDataListener) {
		baseDataListener.setListenerManager(this);
		return baseDataListener.excute();
	}

	/**
	 * 添加监听，并初始化构造.但制作出监听器不受到manage管理。
	 * 
	 * @param executer
	 * @return
	 */
	public Object addListenerWithOutManager(IListenerExecute executer) {
		return addListenerWithOutManager(executer, false, true);
	}

	/**
	 * 添加监听，并初始化构造.但制作出监听器不受到manage管理。
	 * 
	 * @param executer
	 * @return
	 */
	public Object addListenerWithOutManager(IListenerExecute executer,
			boolean isClearData, boolean isEnable) {
		if (executer instanceof BaseDataListener) {
			BaseDataListener b = (BaseDataListener) executer;
			ListenerManager listenerManager = new ListenerManager();
			b.setListenerManager(listenerManager);
			listenerManager.setClearData(isClearData);
			listenerManager.setEanbleListener(isEnable);
		}
		return executer.excute();
	}

}
