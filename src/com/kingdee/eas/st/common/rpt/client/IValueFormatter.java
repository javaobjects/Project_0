package com.kingdee.eas.st.common.rpt.client;

/**
 * @author xiaofeng_liu
 *         初始用途，用在报表数据的展现上，如从数据库取出来的数据为1,0的boolean类型，那必须将这个decimal转换为Boolean
 *         ，报表才能 将它显示为复选框的形式
 * 
 */
public interface IValueFormatter {
	public Object format(Object object);
}
