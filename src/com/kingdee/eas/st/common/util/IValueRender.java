package com.kingdee.eas.st.common.util;

/**
 * 数据转换接口，通常用于把数据库中读取出来的值转换为枚举类型
 * 
 * @author zhiwei_wang
 * 
 */
public interface IValueRender {

	Object render(Object src);
}
