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
	protected long _getMaxSerial(Context ctx, String keyItem,
			String keyItemValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue);
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
	protected long _getMaxSerial(Context ctx, String keyItem,
			String keyItemValue, int step, long initValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue, step,
				initValue);
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
	protected long[] _getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue) throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue);
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
	protected long[] _getMaxSerial(Context ctx, String keyItem,
			String[] keyItemValue, int step, long initValue)
			throws BOSException {
		return MaxSerialDAO.getMaxSerial(ctx, keyItem, keyItemValue, step,
				initValue);
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
	protected boolean _isExists(Context ctx, String keyItem, String keyItemValue)
			throws BOSException {
		return MaxSerialDAO.isExists(ctx, keyItem, keyItemValue);
	}
}