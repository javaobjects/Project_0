/*
 * @(#)WriteBackProxy.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.StringUtils;

/**
 * 描述: BOTP反写代理. 说明： 1.
 * 在目标方发起WriteBackProxy调用,由来源方提供专门的处理类实现IWriteBack接口完成具体的反写处理. 2. 在目标方提供两部分数据：
 * 2.1) 以整单为粒度提供目标方数据，亦可约定放入其他结构的数据. 2.2) 激活反写操作的时点. 3.
 * 通过目标方对象上记录的来源方对象Id提取来源方实体的BOSType. 3.1) 默认记录的来源方对象Id的属性名为：sourceBillId. 4.
 * 来源方可在各自包路径维护属性文件，按来源方实体的BOSType检索其反写处理类强名，属性文件格式: BOSType - 反写处理类强名. 4.1)
 * 默认从com.kingdee.eas.st.common.app.botp.BOTPWriteBackProcessor属性文件提取.
 * 
 * 5. 调用示例：
 * 
 * WriteBackProxy.getInstance(
 * targetBill.getEntries(),"sourceBillId").writeBack(ctx,targetBill);
 * 
 * @author daij date:2006-11-29
 *         <p>
 * @version EAS5.2.0
 */
public final class WriteBackProxy implements IWriteBack {

	/**
	 * 目标单上记录源单Id的默认属性名字
	 */
	public static final String PROPERTYNAME_SOURCEBILLID_DEFAULT = "sourceBillId";

	/**
	 * 记录源单执行反写的处理类属性文件
	 */
	public static final String BUNDLE_NAME_DEFAULT = "com.kingdee.eas.st.common.app.botp.BOTPWriteBackProcessor";

	/**
	 * 构造参数:反写的处理类属性文件
	 */
	public static final int KEY_PROCESSORFILENAME = 0;

	/**
	 * 构造参数:目标单上记录源单Id的属性名字
	 */
	public static final int KEY_SOURCEBILLID = 1;

	/**
	 * 记录源单反写处理类的默认属性文件
	 */
	private static final ResourceBundle DEFAULT_RESOURCEBUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME_DEFAULT);

	/**
	 * 从属性文件提取源单反写处理类的ResourceBundle
	 */
	private ResourceBundle resourceBundle = DEFAULT_RESOURCEBUNDLE;

	/**
	 * 触发反写的目标单Info
	 */
	private Object target = null;

	/**
	 * 目标单上记录源单Id的属性名字
	 */
	private String sourceBillIdPropertyName = PROPERTYNAME_SOURCEBILLID_DEFAULT;

	/**
	 * 需要反写的源单处理类列表.
	 */
	private Map writeBackProcessors = new HashMap();

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param target
	 *            触发反写的目标
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	private WriteBackProxy(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param target
	 *            触发反写的目标
	 * @param value
	 *            目标单上记录源单Id的属性名字或者目标单上记录源单Id的属性名字
	 * @param key
	 *            WriteBackProxy.KEY_PROCESSORFILENAME 或者
	 *            WriteBackProxy.KEY_PROCESSORFILENAME
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	private WriteBackProxy(Object target, String value, int key) {
		this(target);

		if (!StringUtils.isEmpty(value)) {
			switch (key) {
			case KEY_PROCESSORFILENAME:
				this.setResourceBundle(value);
				break;
			case KEY_SOURCEBILLID:
				this.sourceBillIdPropertyName = value;
				break;
			default:
				// TODO 抛出无法识别的构造参数错误.
			}
		}
	}

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param target
	 *            触发反写的目标
	 * @param sourceBillIdPropertyName
	 *            目标单上记录源单Id的属性名字
	 * @param processorFileName
	 *            记录源单反写处理类的属性文件强名
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	private WriteBackProxy(Object target, String sourceBillIdPropertyName,
			String processorFileName) {

		this(target, sourceBillIdPropertyName, KEY_SOURCEBILLID);

		setResourceBundle(processorFileName);
	}

	private void setResourceBundle(String processorFileName) {
		if (!StringUtils.isEmpty(processorFileName)) {
			this.resourceBundle = ResourceBundle.getBundle(processorFileName);
		}
	}

	/**
	 * 
	 * 描述：获取反写代理实例. 注意： 1. 记录源单反写处理类的属性文件强名: 使用默认BUNDLE_NAME 2. 目标单上记录源单Id的属性名字:
	 * 使用默认PROPERTYNAME_SOURCEBILLID_DEFAULT
	 * 
	 * @param targetBill
	 *            触发反写的目标单Info
	 * @return IWriteBack
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object target) {
		return new WriteBackProxy(target);
	}

	/**
	 * 
	 * 描述：获取反写代理实例. 注意： 1. 记录源单反写处理类的属性文件强名: 使用默认BUNDLE_NAME
	 * 
	 * @param targetBill
	 *            触发反写的目标单Info
	 * @param value
	 *            目标单上记录源单Id的属性名字或者目标单上记录源单Id的属性名字
	 * @param key
	 *            WriteBackProxy.KEY_PROCESSORFILENAME 或者
	 *            WriteBackProxy.KEY_PROCESSORFILENAME
	 * @return IWriteBack
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object targetBill, String value,
			int key) {
		return new WriteBackProxy(targetBill, value, key);
	}

	/**
	 * 
	 * 描述：获取反写代理实例.
	 * 
	 * @param targetBill
	 *            触发反写的目标单Info
	 * @param sourceBillIdPropertyName
	 *            目标单上记录源单Id的属性名字
	 * @param processorFileName
	 *            记录源单反写处理类的属性文件强名
	 * @return IWriteBack
	 * @author:daij 创建时间：2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object targetBill,
			String sourceBillIdPropertyName, String processorFileName) {
		return new WriteBackProxy(targetBill, sourceBillIdPropertyName,
				processorFileName);
	}

	/**
	 * 
	 * 描述：转发反写方法.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      botp.IObjectValue)
	 */
	public void writeBack(Context ctx, Object targetBillInfo, Object action)
			throws BOSException, EASBizException {

		if (STQMUtils.isNotNull(target)) {
			// 清理反写处理类列表.
			writeBackProcessors.clear();

			Iterator itor = null;
			if (target instanceof IObjectCollection) {
				// 如果是集合则需要处理反写多种类型源单.
				itor = ((IObjectCollection) target).iterator();
				while (itor.hasNext()) {
					putWriteBackProcessor(getBosTypeString(itor.next()));
				}
			} else if (target instanceof IObjectValue) {
				// 如果是明细条目则提取单一来源单.
				putWriteBackProcessor(getBosTypeString(target));
			} else {
				// 传入了BOSType或其字符串.
				putWriteBackProcessor(target.toString());
			}

			// 按反写处理类列表迭代回调反写接口.
			Object writeBackProcessor = null;
			itor = writeBackProcessors.values().iterator();
			while (itor.hasNext()) {
				writeBackProcessor = itor.next();
				if (writeBackProcessor instanceof IWriteBack) {
					((IWriteBack) writeBackProcessor).writeBack(ctx,
							targetBillInfo, action);
				}
			}
		}
	}

	// 从目标条目提取来源单据的BOSType
	private String getBosTypeString(Object targetBill) {
		String bosTypeString = null;
		if (targetBill instanceof IObjectValue
				&& !StringUtils.isEmpty(sourceBillIdPropertyName)) {
			String idString = null;
			IObjectValue info = (IObjectValue) targetBill;
			if (info.containsKey(sourceBillIdPropertyName)) {
				idString = info.getString(sourceBillIdPropertyName);
				bosTypeString = STQMUtils.getBOSTypeStringById(idString);
			}
		}
		return bosTypeString;
	}

	// 把需反写的来源单据处理类按BOSType压入堆栈
	private void putWriteBackProcessor(String bosTypeString)
			throws BOSException {
		if (!StringUtils.isEmpty(bosTypeString)
				&& !writeBackProcessors.containsKey(bosTypeString)) {
			Object writeBackProcessor = null;
			try {
				writeBackProcessor = getWriteBackProcessor(bosTypeString);
			} catch (Exception e) {
				// TODO 抛出无法获取源单反写处理类异常.
				throw new BOSException(e);
			}
			if (STQMUtils.isNotNull(writeBackProcessor)) {
				writeBackProcessors.put(bosTypeString, writeBackProcessor);
			}
		}
	}

	// 从属性文件按BOSType提取来源单据反写处理类
	private Object getWriteBackProcessor(String bosTypeString) throws Exception {
		Object writeBackProcessor = null;
		if (!StringUtils.isEmpty(bosTypeString)) {
			String processorClassName = resourceBundle.getString(bosTypeString);

			if (!StringUtils.isEmpty(processorClassName)) {
				writeBackProcessor = Class.forName(processorClassName)
						.newInstance();
			}
		}
		return writeBackProcessor;
	}
}
