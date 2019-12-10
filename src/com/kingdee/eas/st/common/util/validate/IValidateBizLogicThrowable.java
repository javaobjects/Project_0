/*
 * @(#)IValidateBizLogicThrowable.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.validate;

import com.kingdee.bos.dao.IObjectValue;

/**
 * ����: ��֤ҵ���߼�ģ��,������֤ҵ���߼���ͳһ����ǩ��. ְ��: ��װ�����ҵ��ʵ����̬������ҵ���߼����.
 * 
 * ע��: 1.�����Խӿڲ�����DataEntironment,ֻ�����ӿڲ��� 2.����ҵ���쳣ֱ���׳�.
 * 
 * @author daij date:2006-11-27
 *         <p>
 * @version EAS5.2.0
 */
public interface IValidateBizLogicThrowable {

	/**
	 * 
	 * ��������֤ҵ���߼�.
	 * 
	 * @param data
	 *            ����֤��ֵ����Info
	 * @throws Exception
	 * @author:daij ����ʱ�䣺2006-11-14
	 *              <p>
	 */
	public abstract void validateBizLogic(IObjectValue data) throws Exception;
}
