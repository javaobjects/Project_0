/*
 * @(#)SCMQueryFormat.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client;

import java.sql.Timestamp;
import com.kingdee.eas.scm.common.util.SCMUtils;

/**
 * ����: ����ѯ�����ṩһЩ�����߼����ƵĹ�����
 * 
 * @author daij date:2005-9-2
 *         <p>
 * @version EAS5.0
 */
public abstract class QueryFormator {

	/**
	 * 
	 * ��������ʽ��������ѯ����ʼʱ�� ҵ��涨: ��ʼʱ��Ĭ��ֵΪϵͳ����ǰ��һ���µ�����
	 * 
	 * @param datePicker
	 * @author:daij ����ʱ�䣺2005-9-2
	 *              <p>
	 */
	public static void formatStartTime(
			com.kingdee.bos.ctrl.swing.KDDatePicker datePicker) {
		Timestamp t = SCMUtils.getSCMQueryTime(null, SCMUtils.KEY_STARTDATE);
		datePicker.setValue(t);
	}

	/**
	 * 
	 * ��������ʽ��������ѯ�Ľ�ֹʱ�� ҵ��涨: ��ֹʱ��Ϊ��ǰϵͳʱ��
	 * 
	 * @param datePicker
	 * @author:daij ����ʱ�䣺2005-9-2
	 *              <p>
	 */
	public static void formatEndTime(
			com.kingdee.bos.ctrl.swing.KDDatePicker datePicker) {
		Timestamp t = SCMUtils.getSCMQueryTime(null, SCMUtils.KEY_ENDDATE);
		datePicker.setValue(t);
	}
}
