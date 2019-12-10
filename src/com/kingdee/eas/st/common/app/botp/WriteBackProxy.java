/*
 * @(#)WriteBackProxy.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: BOTP��д����. ˵���� 1.
 * ��Ŀ�귽����WriteBackProxy����,����Դ���ṩר�ŵĴ�����ʵ��IWriteBack�ӿ���ɾ���ķ�д����. 2. ��Ŀ�귽�ṩ���������ݣ�
 * 2.1) ������Ϊ�����ṩĿ�귽���ݣ����Լ�����������ṹ������. 2.2) ���д������ʱ��. 3.
 * ͨ��Ŀ�귽�����ϼ�¼����Դ������Id��ȡ��Դ��ʵ���BOSType. 3.1) Ĭ�ϼ�¼����Դ������Id��������Ϊ��sourceBillId. 4.
 * ��Դ�����ڸ��԰�·��ά�������ļ�������Դ��ʵ���BOSType�����䷴д������ǿ���������ļ���ʽ: BOSType - ��д������ǿ��. 4.1)
 * Ĭ�ϴ�com.kingdee.eas.st.common.app.botp.BOTPWriteBackProcessor�����ļ���ȡ.
 * 
 * 5. ����ʾ����
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
	 * Ŀ�굥�ϼ�¼Դ��Id��Ĭ����������
	 */
	public static final String PROPERTYNAME_SOURCEBILLID_DEFAULT = "sourceBillId";

	/**
	 * ��¼Դ��ִ�з�д�Ĵ����������ļ�
	 */
	public static final String BUNDLE_NAME_DEFAULT = "com.kingdee.eas.st.common.app.botp.BOTPWriteBackProcessor";

	/**
	 * �������:��д�Ĵ����������ļ�
	 */
	public static final int KEY_PROCESSORFILENAME = 0;

	/**
	 * �������:Ŀ�굥�ϼ�¼Դ��Id����������
	 */
	public static final int KEY_SOURCEBILLID = 1;

	/**
	 * ��¼Դ����д�������Ĭ�������ļ�
	 */
	private static final ResourceBundle DEFAULT_RESOURCEBUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME_DEFAULT);

	/**
	 * �������ļ���ȡԴ����д�������ResourceBundle
	 */
	private ResourceBundle resourceBundle = DEFAULT_RESOURCEBUNDLE;

	/**
	 * ������д��Ŀ�굥Info
	 */
	private Object target = null;

	/**
	 * Ŀ�굥�ϼ�¼Դ��Id����������
	 */
	private String sourceBillIdPropertyName = PROPERTYNAME_SOURCEBILLID_DEFAULT;

	/**
	 * ��Ҫ��д��Դ���������б�.
	 */
	private Map writeBackProcessors = new HashMap();

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param target
	 *            ������д��Ŀ��
	 * @author:daij ����ʱ�䣺2006-11-30
	 *              <p>
	 */
	private WriteBackProxy(Object target) {
		super();
		this.target = target;
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param target
	 *            ������д��Ŀ��
	 * @param value
	 *            Ŀ�굥�ϼ�¼Դ��Id���������ֻ���Ŀ�굥�ϼ�¼Դ��Id����������
	 * @param key
	 *            WriteBackProxy.KEY_PROCESSORFILENAME ����
	 *            WriteBackProxy.KEY_PROCESSORFILENAME
	 * @author:daij ����ʱ�䣺2006-11-30
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
				// TODO �׳��޷�ʶ��Ĺ����������.
			}
		}
	}

	/**
	 * 
	 * ���������캯��
	 * 
	 * @param target
	 *            ������д��Ŀ��
	 * @param sourceBillIdPropertyName
	 *            Ŀ�굥�ϼ�¼Դ��Id����������
	 * @param processorFileName
	 *            ��¼Դ����д������������ļ�ǿ��
	 * @author:daij ����ʱ�䣺2006-11-30
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
	 * ��������ȡ��д����ʵ��. ע�⣺ 1. ��¼Դ����д������������ļ�ǿ��: ʹ��Ĭ��BUNDLE_NAME 2. Ŀ�굥�ϼ�¼Դ��Id����������:
	 * ʹ��Ĭ��PROPERTYNAME_SOURCEBILLID_DEFAULT
	 * 
	 * @param targetBill
	 *            ������д��Ŀ�굥Info
	 * @return IWriteBack
	 * @author:daij ����ʱ�䣺2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object target) {
		return new WriteBackProxy(target);
	}

	/**
	 * 
	 * ��������ȡ��д����ʵ��. ע�⣺ 1. ��¼Դ����д������������ļ�ǿ��: ʹ��Ĭ��BUNDLE_NAME
	 * 
	 * @param targetBill
	 *            ������д��Ŀ�굥Info
	 * @param value
	 *            Ŀ�굥�ϼ�¼Դ��Id���������ֻ���Ŀ�굥�ϼ�¼Դ��Id����������
	 * @param key
	 *            WriteBackProxy.KEY_PROCESSORFILENAME ����
	 *            WriteBackProxy.KEY_PROCESSORFILENAME
	 * @return IWriteBack
	 * @author:daij ����ʱ�䣺2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object targetBill, String value,
			int key) {
		return new WriteBackProxy(targetBill, value, key);
	}

	/**
	 * 
	 * ��������ȡ��д����ʵ��.
	 * 
	 * @param targetBill
	 *            ������д��Ŀ�굥Info
	 * @param sourceBillIdPropertyName
	 *            Ŀ�굥�ϼ�¼Դ��Id����������
	 * @param processorFileName
	 *            ��¼Դ����д������������ļ�ǿ��
	 * @return IWriteBack
	 * @author:daij ����ʱ�䣺2006-11-30
	 *              <p>
	 */
	public static IWriteBack getInstance(Object targetBill,
			String sourceBillIdPropertyName, String processorFileName) {
		return new WriteBackProxy(targetBill, sourceBillIdPropertyName,
				processorFileName);
	}

	/**
	 * 
	 * ������ת����д����.
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.app.botp.IWriteBack#writeBack(com.kingdee.bos.Context,
	 *      botp.IObjectValue)
	 */
	public void writeBack(Context ctx, Object targetBillInfo, Object action)
			throws BOSException, EASBizException {

		if (STQMUtils.isNotNull(target)) {
			// ����д�������б�.
			writeBackProcessors.clear();

			Iterator itor = null;
			if (target instanceof IObjectCollection) {
				// ����Ǽ�������Ҫ����д��������Դ��.
				itor = ((IObjectCollection) target).iterator();
				while (itor.hasNext()) {
					putWriteBackProcessor(getBosTypeString(itor.next()));
				}
			} else if (target instanceof IObjectValue) {
				// �������ϸ��Ŀ����ȡ��һ��Դ��.
				putWriteBackProcessor(getBosTypeString(target));
			} else {
				// ������BOSType�����ַ���.
				putWriteBackProcessor(target.toString());
			}

			// ����д�������б�����ص���д�ӿ�.
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

	// ��Ŀ����Ŀ��ȡ��Դ���ݵ�BOSType
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

	// ���跴д����Դ���ݴ����ఴBOSTypeѹ���ջ
	private void putWriteBackProcessor(String bosTypeString)
			throws BOSException {
		if (!StringUtils.isEmpty(bosTypeString)
				&& !writeBackProcessors.containsKey(bosTypeString)) {
			Object writeBackProcessor = null;
			try {
				writeBackProcessor = getWriteBackProcessor(bosTypeString);
			} catch (Exception e) {
				// TODO �׳��޷���ȡԴ����д�������쳣.
				throw new BOSException(e);
			}
			if (STQMUtils.isNotNull(writeBackProcessor)) {
				writeBackProcessors.put(bosTypeString, writeBackProcessor);
			}
		}
	}

	// �������ļ���BOSType��ȡ��Դ���ݷ�д������
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
