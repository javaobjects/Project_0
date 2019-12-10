/*
 * @(#)MaxSerialDAO.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 封装最大顺序号的数据库处理.
 * 
 * @author daij date:2007-10-17
 *         <p>
 * @version EAS5.4
 */
public abstract class MaxSerialDAO {

	/**
	 * 默认的最大序号步进值 = 0
	 */
	public static final int DEFAULT_MAXSERIAL_STEP = 1;

	/**
	 * 默认的最大序号初始值 = 0
	 */
	public static final long DEFAULT_MAXSERIAL_INITVALUE = 1;

	/**
	 * 
	 * 描述：获取最大序号
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @return long
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	public static long getMaxSerial(Context ctx, String keyItem,
			String keyItemValue) throws BOSException {
		return getMaxSerial(ctx, keyItem, keyItemValue, DEFAULT_MAXSERIAL_STEP,
				DEFAULT_MAXSERIAL_INITVALUE);
	}

	/**
	 * 
	 * 描述：获取最大序号
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @param step
	 *            步进
	 * @param initValue
	 *            初始值
	 * @return long
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	public static long getMaxSerial(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {

		// 合法性鉴定
		legality(keyItem, keyItemValue);
		// 锁住关键字资源
		Mutex mutex = mutexInstance(keyItem, keyItemValue);
		lockKeyItem(mutex);

		long maxSerial = -1;
		try {
			// 获取最大序号
			IRowSet rs = DbUtil.executeQuery(ctx, SQLSTRING_SELECT_SERIAL,
					new Object[] { keyItem, keyItemValue });
			if (STUtils.isNotNull(rs) && rs.next()) {
				maxSerial = rs.getLong("FMaxSerial");
			} else {
				maxSerial = initValue;
			}
			// 重新申报MAX序列号.
			declare(ctx, keyItem, keyItemValue, step, initValue);

		} catch (SQLException e) {
			throw new SQLDataException(e);
		} finally {
			// 释放关键字资源锁
			unLockKeyItem(mutex);
		}
		return maxSerial;
	}

	/**
	 * 
	 * 描述：批量获取最大序号组
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值组
	 * @return long[]
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	public static long[] getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue) throws BOSException {
		return getMaxSerial(ctx, keyItem, keyItemValue, DEFAULT_MAXSERIAL_STEP,
				DEFAULT_MAXSERIAL_INITVALUE);
	}

	/**
	 * 
	 * 描述：批量获取最大序号组
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值组
	 * @param step
	 * @param initValue
	 * @return long[]
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
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
	 * 描述：关键字最大序号记录是否存在
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @return boolean
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
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
	 * 描述：申报最大序列号实现. 注意: 此方法需要置于关键字互斥锁保护中.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @param step
	 *            步进
	 * @param initValue
	 *            初始值
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	private static void declare(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {
		// 合法性鉴定
		legality(keyItem, keyItemValue);
		// 重新申报最大序列号
		if (isExists(ctx, keyItem, keyItemValue)) {
			// 存在就更新.
			DbUtil.execute(ctx, SQLSTRING_UPDATE_SERIAL, new Object[] {
					new Integer(step), keyItem, keyItemValue });
		} else {
			// 不存在就插入.
			DbUtil.execute(ctx, SQLSTRING_INSERT_SERIAL, new Object[] {
					keyItem, keyItemValue, new Long(initValue + step),
					new Long(initValue) });
		}
	}

	/**
	 * 
	 * 描述：关键字互斥锁
	 * 
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @return String
	 * @author:daij 创建时间：2007-10-17
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
	 * 描述：申请最大序号的关键字信息合法性.
	 * 
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @throws BOSException
	 * @author:daij 创建时间：2007-10-17
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
	 * 描述：是否有效的关键字
	 * 
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @return boolean
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	public static boolean isEffectualKey(String keyItem, String keyItemValue) {
		return !StringUtils.isEmpty(keyItem)
				&& !StringUtils.isEmpty(keyItemValue);
	}

	/**
	 * 
	 * 描述：互斥锁实例
	 * 
	 * @param keyItem
	 *            关键字串
	 * @param keyItemValue
	 *            关键字值
	 * @return Mutex
	 * @author:daij 创建时间：2007-10-17
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
	 * 描述：锁住关键字资源
	 * 
	 * @param mutex
	 *            互斥锁
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	private static void lockKeyItem(Mutex mutex) {
		if (STUtils.isNotNull(mutex)) {
			mutex.lock();
		}
	}

	/**
	 * 
	 * 描述：释放关键字资源锁
	 * 
	 * @param mutex
	 *            互斥锁
	 * @author:daij 创建时间：2007-10-17
	 *              <p>
	 */
	private static void unLockKeyItem(Mutex mutex) {
		if (STUtils.isNotNull(mutex)) {
			mutex.unlock();
		}
	}

	// 更新序号SQLString
	private static final String SQLSTRING_UPDATE_SERIAL = "Update T_ST_MaxSerial Set FMaxSerial = FMaxSerial + ? Where FKeyItem = ? And FKeyItemValue = ?";

	// 插入序号SQLString
	private static final String SQLSTRING_INSERT_SERIAL = "Insert Into T_ST_MaxSerial(FKeyItem,FKeyItemValue,FMaxSerial,FInitValue) values(?,?,?,?)";

	// 查询序号SQLString
	private static final String SQLSTRING_SELECT_SERIAL = "Select FMaxSerial From T_ST_MaxSerial Where FKeyItem = ? And FKeyItemValue = ? ";

	// 判定是否存在序号SQLString
	private static final String SQLSTRING_EXISTS_SERIAL = "Select FKeyItem,FKeyItemValue From T_ST_MaxSerial Where FKeyItem = ? And FKeyItemValue = ? ";
}
