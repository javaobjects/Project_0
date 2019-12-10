package com.kingdee.eas.st.common.app;

import org.apache.log4j.Logger;
import com.kingdee.bos.*;

import java.lang.String;

public class MaxSerialServerFacadeControllerBean extends
		AbstractMaxSerialServerFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.st.common.app.MaxSerialServerFacadeControllerBean");

	/**
	 * 
	 * ��������ȡ������
	 * 
	 * @param ctx
	 *            �����������
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @return long
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	protected long _getMaxSerial(Context ctx, String keyItem,
			String keyItemValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue);
	}

	/**
	 * 
	 * ��������ȡ������
	 * 
	 * @param ctx
	 *            �����������
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @param step
	 *            ����
	 * @param initValue
	 *            ��ʼֵ
	 * @return long
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	protected long _getMaxSerial(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue, step,
				initValue);
	}

	/**
	 * 
	 * ������������ȡ��������
	 * 
	 * @param ctx
	 *            �����������
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ��
	 * @return long[]
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	protected long[] _getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue);
	}

	/**
	 * 
	 * ������������ȡ��������
	 * 
	 * @param ctx
	 *            �����������
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ��
	 * @param step
	 * @param initValue
	 * @return long[]
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	protected long[] _getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue, int step, long initValue)
			throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue, step,
				initValue);
	}

	/**
	 * 
	 * �������ؼ��������ż�¼�Ƿ����
	 * 
	 * @param ctx
	 *            �����������
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @return boolean
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	protected boolean _isExists(Context ctx, String keyItem, String keyItemValue)
			throws BOSException {
		return MaxSerialDAO.isExists(ctx, keyItem, keyItemValue);
	}
}