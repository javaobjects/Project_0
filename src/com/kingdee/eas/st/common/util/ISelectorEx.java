/*
 * @(#)ISelectorEx.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;

/**
 * 描述: 二次开发具体的getSelectors
 * 
 * 1. 在EditUI中getSelectors会将所有的LinkedProperty展开为:LinkedProperty.*
 * 而在ORMapping会将LinkedProperty.*翻译为该表所有的字段，如果某个对象层次比较深并且LinkedProperty
 * 比较多就会导致最终执行getValue的SQL语句超长. 2. 存在此问题的两个大类： ListUI.getBOTPSelectors 和
 * EditUI.getSelectors
 * 
 * a.)ListUI.getBOTPSelectors可以采用在XXXListUIPIEX中覆盖getBOTPSelectors方法调用本接口的具体实现类.
 * b.)EditUI.getSelectors因为现场环境最终是二次开发的AbstractXXXEditUI.getSelectors起作用，
 * 所以定义此接口由二次开发人员将
 * AbstractXXXEditUI.getSelectors中的LinkedProperty.*展开为真正需要的属性(比如:
 * LinkedProperty.p1,LinkedProperty.p2...)，
 * 也就是说在本接口实现类中原则上不允许出现LinkedProperty.*.
 * c.)扩展开发人员在PIEX中将扩展开发增加的LinkedProperty.*展开
 * ,二次开发人员通过本接口的实现类仅展开二次开发增加的LinkedProperty.*
 * 
 * 3.本接口通过约定具体实现类名反射实例化，约定的实现类命名规则为:XXXInfo + SelectorEx，
 * 即形如：com.kingdee.eas.scm.sd.sale.SaleOrderInfoSelectorEx
 * 
 * @author daij date:2008-6-3
 *         <p>
 * @version EAS5.4
 */
public interface ISelectorEx {

	/**
	 * 
	 * 描述：获取二次开发
	 * 
	 * 将AbstractXXXEditUI.getSelectors中的LinkedProperty.*展开为真正需要的属性(比如:
	 * LinkedProperty.p1,LinkedProperty.p2...)，
	 * 也就是说在本接口实现类中原则上不允许出现LinkedProperty.*
	 * 
	 * 注意： 此Selectors需按照实体上的属性名进行描述，而不是按Query.
	 * 
	 * @return SelectorItemCollection
	 * @author:daij 创建时间：2008-6-3
	 *              <p>
	 */
	SelectorItemCollection getExtSelectors();
}
