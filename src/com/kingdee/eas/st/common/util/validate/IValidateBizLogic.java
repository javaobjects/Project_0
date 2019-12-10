/*
 * @(#)IValidateBizLogic.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.validate;

import java.util.Map;

import com.kingdee.bos.dao.IObjectValue;

/**
 * ����: ��֤ҵ���߼�ģ��,������֤ҵ���߼���ͳһ����ǩ��. ְ��: ��װ�����ҵ��ʵ����̬������ҵ���߼����.
 * 
 * ע��: 1.�����Խӿڲ�����DataEntironment,ֻ�����ӿڲ���
 * 2.���ӿڶ�ҵ���쳣�Ĵ���ʽΪ��������֤���еļ����Ŀ������ҵ���쳣�ռ���Map�У�����boolean = (Map.size() == 0)
 * 3.Map��ֻ�ܷ���ϵͳ�ɿص�ҵ���쳣,��֤�е�run time�쳣����ͨ��throws�׳�.
 * 
 * @author daij date:2006-11-14
 *         <p>
 * @version EAS5.2.0
 */
public interface IValidateBizLogic {

	/**
	 * 
	 * ��������֤ҵ���߼�.
	 * 
	 * @param data
	 *            ����֤��ֵ����Info
	 * @param bizLogicExceptions
	 *            ҵ���߼��쳣
	 * @return boolean �Ƿ���֤ͨ��
	 * @throws Exception
	 * @author:daij ����ʱ�䣺2006-11-14
	 *              <p>
	 */
	public abstract boolean validateBizLogic(IObjectValue data,
			Map bizLogicExceptions) throws Exception;
}
