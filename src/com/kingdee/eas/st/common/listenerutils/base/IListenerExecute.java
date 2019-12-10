package com.kingdee.eas.st.common.listenerutils.base;

public interface IListenerExecute {

	/**
	 * 在此方法中必须将监听源加入到监听中，并且初始化也要在这里完成
	 * 
	 * this.addFilter(sor.getValue()); // 初始化阶段为目标增加过滤
	 * sor.addDataChangeListener(this); // 将监听放置到监听源中
	 * 
	 * @return 返回生成的监听，用于特殊控制的情况。需要用户自行转换类型
	 */
	public Object excute();

}
