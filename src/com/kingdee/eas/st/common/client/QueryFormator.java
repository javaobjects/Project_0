/*
 * @(#)SCMQueryFormat.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client;

import java.sql.Timestamp;
import com.kingdee.eas.scm.common.util.SCMUtils;

/**
 * 描述: 给查询界面提供一些公共逻辑控制的工具类
 * 
 * @author daij date:2005-9-2
 *         <p>
 * @version EAS5.0
 */
public abstract class QueryFormator {

	/**
	 * 
	 * 描述：格式化条件查询的起始时间 业务规定: 起始时间默认值为系统日期前推一个月的日期
	 * 
	 * @param datePicker
	 * @author:daij 创建时间：2005-9-2
	 *              <p>
	 */
	public static void formatStartTime(
			com.kingdee.bos.ctrl.swing.KDDatePicker datePicker) {
		Timestamp t = SCMUtils.getSCMQueryTime(null, SCMUtils.KEY_STARTDATE);
		datePicker.setValue(t);
	}

	/**
	 * 
	 * 描述：格式化条件查询的截止时间 业务规定: 截止时间为当前系统时间
	 * 
	 * @param datePicker
	 * @author:daij 创建时间：2005-9-2
	 *              <p>
	 */
	public static void formatEndTime(
			com.kingdee.bos.ctrl.swing.KDDatePicker datePicker) {
		Timestamp t = SCMUtils.getSCMQueryTime(null, SCMUtils.KEY_ENDDATE);
		datePicker.setValue(t);
	}
}
