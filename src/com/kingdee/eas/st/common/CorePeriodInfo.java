/**
 * 
 */
package com.kingdee.eas.st.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @description �����ڼ䳬��
 * 
 */
public class CorePeriodInfo implements Serializable {
	/**
	 * @description �ڼ�����
	 */
	private Date upPeriod = null;

	/**
	 * @description �ڼ�����
	 */
	private Date downPeriod = null;

	/**
	 * @return ���� downPeriod��
	 */
	public Date getDownPeriod() {
		return downPeriod;
	}

	/**
	 * @param downPeriod
	 *            Ҫ���õ� downPeriod��
	 */
	public void setDownPeriod(Date downPeriod) {
		this.downPeriod = downPeriod;
	}

	/**
	 * @return ���� upPeriod��
	 */
	public Date getUpPeriod() {
		return upPeriod;
	}

	/**
	 * @param upPeriod
	 *            Ҫ���õ� upPeriod��
	 */
	public void setUpPeriod(Date upPeriod) {
		this.upPeriod = upPeriod;
	}

}
