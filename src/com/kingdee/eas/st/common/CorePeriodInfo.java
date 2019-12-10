/**
 * 
 */
package com.kingdee.eas.st.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 日期期间超类
 * 
 */
public class CorePeriodInfo implements Serializable {
	/**
	 * @description 期间上限
	 */
	private Date upPeriod = null;

	/**
	 * @description 期间下限
	 */
	private Date downPeriod = null;

	/**
	 * @return 返回 downPeriod。
	 */
	public Date getDownPeriod() {
		return downPeriod;
	}

	/**
	 * @param downPeriod
	 *            要设置的 downPeriod。
	 */
	public void setDownPeriod(Date downPeriod) {
		this.downPeriod = downPeriod;
	}

	/**
	 * @return 返回 upPeriod。
	 */
	public Date getUpPeriod() {
		return upPeriod;
	}

	/**
	 * @param upPeriod
	 *            要设置的 upPeriod。
	 */
	public void setUpPeriod(Date upPeriod) {
		this.upPeriod = upPeriod;
	}

}
