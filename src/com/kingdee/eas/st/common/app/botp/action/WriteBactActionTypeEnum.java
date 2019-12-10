/*
 * @(#)WriteBactActionTypeEnum.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.action;

/**
 * ����: ��д��������ʱ�������ö��. ע�⣺��ҵ��ϵͳ������д������ʱ�������Դ���Ϊ����
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class WriteBactActionTypeEnum {

	/**
	 * ����ʱ��д����.
	 */
	public static final String SAVE_WRITEBACKACTION = "SaveWriteBackAction";

	/**
	 * �ύʱ��д����.
	 */
	public static final String SUBMIT_WRITEBACKACTION = "SubmitWriteBackAction";

	/**
	 * ���ʱ��д����.
	 */
	public static final String AUDIT_WRITEBACKACTION = "AuditWriteBackAction";

	/**
	 * �����ʱ��д����.
	 */
	public static final String UNAUDIT_WRITEBACKACTION = "UnAuditWriteBackAction";
}
