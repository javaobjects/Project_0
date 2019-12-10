/*
 * @(#)STParamConstant.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util.param;

import com.kingdee.util.StringUtils;

/**
 * ����: ST������Ϣ�����б�
 * 
 * 1. ��������. 2. �����Ĺ̶�Number
 * 
 * ST������Ź���
 * 
 * 1. �뵥��������صĲ�����ȡ�� A + B + C ����
 * 
 * A: STϵͳ����ǰ��׺ - STParamConstant.STSYSPARAMS_PRE B: ϵͳ�����ı����ʶ�� - ����������ͬ������:
 * IsAuditAfterSubmit �Ƿ񱣴�����. C: ��ȡϵͳ��������ʱ���뵱ǰ���ݵ�����Number.
 * 
 * ��ȡ����ST�����������Ĳ����������ã�STParamConstant.getSTSysParamNumberByBillType����.
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STParamConstant {

	// ������ͱ�����Ϊϵͳ�����ı�ʶKey

	public static final String STSYSPARAMS_PREFIX = "STSYS_";

	/**
	 * �Ƿ��ύ�����.
	 */
	public static final String STSYSPARAMS_KEYID_ISAUDITSUBMIT = "IsAuditAfterSubmit";

	/**
	 * �Ƿ�ȷ��.
	 */
	public static final String STSYSPARAMS_KEYID_ISAFFIRM = "IsAffirm";

	/**
	 * ���ۼƻ��Ƿ�������Ч����
	 */
	public static final String STSYSPARAMS_KEYID_ISEFFECTEDATE = "STSALEPLAN001";

	/**
	 * 
	 * ���������������ͺͲ��������ʶ�����ط���ST������Ź���Ĳ�������.
	 * 
	 * ST������Ź���A + B + C
	 * 
	 * A: STϵͳ����ǰ��׺ - STParamConstant.STSYSPARAMS_PREFIX B: ϵͳ�����ı����ʶ�� -
	 * ����������ͬ������: IsAuditAfterSubmit �Ƿ񱣴�����. C: ��ȡϵͳ��������ʱ���������Number.
	 * 
	 * @param keyId
	 *            �����ı�ʶ��
	 * @param typeNumber
	 *            ���͵�Number
	 * @return STϵͳϵͳ�����ı���
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static final String getSTSysParamNumberByType(String keyId,
			String typeNumber) {
		StringBuffer sb = new StringBuffer();
		if (!StringUtils.isEmpty(keyId) && !StringUtils.isEmpty(typeNumber)) {
			sb.append(STSYSPARAMS_PREFIX).append(keyId).append("_").append(
					typeNumber);
		}
		return sb.toString();
	}
}
