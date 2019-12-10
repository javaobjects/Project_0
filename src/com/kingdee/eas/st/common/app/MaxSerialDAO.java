/*
 * @(#)MaxSerialDAO.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.base.codingrule.app.Mutex;
import com.kingdee.eas.base.codingrule.app.MutexFactory;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * ����: ��װ���˳��ŵ����ݿ⴦��.
 * 
 * @author daij date:2007-10-17
 *         <p>
 * @version EAS5.4
 */
public abstract class MaxSerialDAO {

	/**
	 * Ĭ�ϵ������Ų���ֵ = 0
	 */
	public static final int DEFAULT_MAXSERIAL_STEP = 1;

	/**
	 * Ĭ�ϵ������ų�ʼֵ = 0
	 */
	public static final long DEFAULT_MAXSERIAL_INITVALUE = 1;

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
	public static long getMaxSerial(Context ctx, String keyItem,
			String keyItemValue) throws BOSException {
		return getMaxSerial(ctx, keyItem, keyItemValue, DEFAULT_MAXSERIAL_STEP,
				DEFAULT_MAXSERIAL_INITVALUE);
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
	public static long getMaxSerial(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {

		// �Ϸ��Լ���
		legality(keyItem, keyItemValue);
		// ��ס�ؼ�����Դ
		Mutex mutex = mutexInstance(keyItem, keyItemValue);
		lockKeyItem(mutex);

		long maxSerial = -1;
		try {
			// ��ȡ������
			IRowSet rs = DbUtil.executeQuery(ctx, SQLSTRING_SELECT_SERIAL,
					new Object[] { keyItem, keyItemValue });
			if (STUtils.isNotNull(rs) && rs.next()) {
				maxSerial = rs.getLong("FMaxSerial");
			} else {
				maxSerial = initValue;
			}
			// �����걨MAX���к�.
			declare(ctx, keyItem, keyItemValue, step, initValue);

		} catch (SQLException e) {
			throw new SQLDataException(e);
		} finally {
			// �ͷŹؼ�����Դ��
			unLockKeyItem(mutex);
		}
		return maxSerial;
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
	public static long[] getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue) throws BOSException {
		return getMaxSerial(ctx, keyItem, keyItemValue, DEFAULT_MAXSERIAL_STEP,
				DEFAULT_MAXSERIAL_INITVALUE);
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
	public static long[] getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue, int step, long initValue)
			throws BOSException {

		if (STUtils.isNull(keyItemValue) || keyItemValue.length == 0)
			return null;

		long[] serial = new long[keyItemValue.length];
		for (int i = 0, size = keyItemValue.length; i < size; i++) {
			serial[i] = getMaxSerial(ctx, keyItem, keyItemValue[i], step,
					initValue);
		}
		return serial;
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
	public static boolean isExists(Context ctx, String keyItem,
			String keyItemValue) throws BOSException {
		boolean isExists = false;
		if (isEffectualKey(keyItem, keyItemValue)) {
			IRowSet rs = DbUtil.executeQuery(ctx, SQLSTRING_EXISTS_SERIAL,
					new Object[] { keyItem, keyItemValue });
			try {
				isExists = STUtils.isNotNull(rs) && rs.next();
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return isExists;
	}

	/**
	 * 
	 * �������걨������к�ʵ��. ע��: �˷�����Ҫ���ڹؼ��ֻ�����������.
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
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	private static void declare(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {
		// �Ϸ��Լ���
		legality(keyItem, keyItemValue);
		// �����걨������к�
		if (isExists(ctx, keyItem, keyItemValue)) {
			// ���ھ͸���.
			DbUtil.execute(ctx, SQLSTRING_UPDATE_SERIAL, new Object[] {
					new Integer(step), keyItem, keyItemValue });
		} else {
			// �����ھͲ���.
			DbUtil.execute(ctx, SQLSTRING_INSERT_SERIAL, new Object[] {
					keyItem, keyItemValue, new Long(initValue + step),
					new Long(initValue) });
		}
	}

	/**
	 * 
	 * �������ؼ��ֻ�����
	 * 
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @return String
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	private static String keyItemLock(String keyItem, String keyItemValue) {
		if (isEffectualKey(keyItem, keyItemValue)) {
			StringBuffer lock = new StringBuffer();
			lock.append(keyItem).append("!").append(keyItemValue);
			return lock.toString();
		} else {
			return null;
		}
	}

	/**
	 * 
	 * ���������������ŵĹؼ�����Ϣ�Ϸ���.
	 * 
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	public static void legality(String keyItem, String keyItemValue)
			throws BOSException {
		if (!isEffectualKey(keyItem, keyItemValue)) {
			throw new BOSException(
					"MaxSerial KeyItem or KeyItemValue Params is'nt legality");
		}
	}

	/**
	 * 
	 * �������Ƿ���Ч�Ĺؼ���
	 * 
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	public static boolean isEffectualKey(String keyItem, String keyItemValue) {
		return !StringUtils.isEmpty(keyItem)
				&& !StringUtils.isEmpty(keyItemValue);
	}

	/**
	 * 
	 * ������������ʵ��
	 * 
	 * @param keyItem
	 *            �ؼ��ִ�
	 * @param keyItemValue
	 *            �ؼ���ֵ
	 * @return Mutex
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	private static Mutex mutexInstance(String keyItem, String keyItemValue) {
		Mutex mutex = null;
		String lock = keyItemLock(keyItem, keyItemValue);
		if (STUtils.isNotNull(lock)) {
			mutex = MutexFactory.createMutex(lock);
		}
		return mutex;
	}

	/**
	 * 
	 * ��������ס�ؼ�����Դ
	 * 
	 * @param mutex
	 *            ������
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	private static void lockKeyItem(Mutex mutex) {
		if (STUtils.isNotNull(mutex)) {
			mutex.lock();
		}
	}

	/**
	 * 
	 * �������ͷŹؼ�����Դ��
	 * 
	 * @param mutex
	 *            ������
	 * @author:daij ����ʱ�䣺2007-10-17
	 *              <p>
	 */
	private static void unLockKeyItem(Mutex mutex) {
		if (STUtils.isNotNull(mutex)) {
			mutex.unlock();
		}
	}

	// �������SQLString
	private static final String SQLSTRING_UPDATE_SERIAL = "Update T_ST_MaxSerial Set FMaxSerial = FMaxSerial + ? Where FKeyItem = ? And FKeyItemValue = ?";

	// �������SQLString
	private static final String SQLSTRING_INSERT_SERIAL = "Insert Into T_ST_MaxSerial(FKeyItem,FKeyItemValue,FMaxSerial,FInitValue) values(?,?,?,?)";

	// ��ѯ���SQLString
	private static final String SQLSTRING_SELECT_SERIAL = "Select FMaxSerial From T_ST_MaxSerial Where FKeyItem = ? And FKeyItemValue = ? ";

	// �ж��Ƿ�������SQLString
	private static final String SQLSTRING_EXISTS_SERIAL = "Select FKeyItem,FKeyItemValue From T_ST_MaxSerial Where FKeyItem = ? And FKeyItemValue = ? ";
}
