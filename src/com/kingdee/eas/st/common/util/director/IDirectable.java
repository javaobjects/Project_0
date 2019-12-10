/*
 * @(#)IWishDirected.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.director;

/**
 * 描述: XXX对象与分离模型的契合接口
 * 
 * 职责: 希望使用分离模型的XXX对象通过实现此接口表达意愿,并实现: 1.选择一种Director方式(TODO 提供统一的导演工厂)
 * 2.实例化并填充Director结构.
 * 
 * 目标: 实现模型对XXX对象的弱侵入性, 通过此接口简易的插拔.
 * 
 * @author daij date:2006-11-6
 *         <p>
 * @version EAS5.2.0
 */
public interface IDirectable {

	/**
	 * 
	 * 描述：设置分离模型的导演. 职责: 1.选择Director方式(TODO 提供统一的导演工厂) 2.实例化并填充Director结构.
	 * 
	 * @author:daij 创建时间：2006-11-6
	 *              <p>
	 */
	public abstract void setupDirector();
}
