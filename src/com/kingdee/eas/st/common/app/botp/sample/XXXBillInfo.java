/*
 * @(#)XXXBillInfo.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.eas.st.common.STBillBaseInfo;

/**
 * ����:
 * 
 * @author daij date:2006-12-19
 *         <p>
 * @version EAS5.2.0
 */
public class XXXBillInfo extends STBillBaseInfo {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 5445310432082933659L;

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public XXXBillInfo() {
		this("id");
	}

	/**
	 * ���������캯��
	 * 
	 * @param pkField
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public XXXBillInfo(String pkField) {
		/* TODO �Զ����ɹ��캯����� */
		super(pkField);
		put("entries", new XXXBillEntryCollection());
	}

	public XXXBillEntryCollection getEntries() {
		return (XXXBillEntryCollection) get("entries");
	}
}
