/*
 * @(#)STUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.kscript.ParserException;
import com.kingdee.bos.kscript.runtime.Interpreter;
import com.kingdee.bos.kscript.runtime.InterpreterException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.StringUtils;

/**
 * ����:
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STQMUtils {

	private static final String resource = "com.kingdee.eas.st.common.STResource";

	/**
	 * �������ݿ��ѯ��ʹ�õ�String��ʵ���Ͼ��Ǽ��ϵ����ţ�ֻ�����ڵ����ַ���
	 * 
	 * @param id
	 * @return
	 */
	public static String toIDString(String id) {
		if (id == null) {
			id = "";
		}
		return "'" + id + "'";
	}

	/**
	 * 
	 * ���������������飩ת����һ��String��
	 * 
	 * @param ids
	 * @return String ��ʽ: '','','',...
	 * @author:Brina ����ʱ�䣺2006-11-16
	 *               <p>
	 */
	public static String toIDString(Object[] ids) {

		StringBuffer paramString = new StringBuffer();

		if (isNotNull(ids) && ids.length != 0) {
			Object item = null;
			int j = 0;
			for (int i = 0, c = ids.length; i < c; i++) {
				item = ids[i];
				if (isNotNull(item) && item.toString().trim().length() != 0) {
					if (j > 0) {
						paramString.append(",");
					}
					paramString.append("'");
					paramString.append(item.toString());
					paramString.append("'");

					j = j + 1;
				}
			}
		}

		// Ϊ��ʱ��IDĬ��Ϊ'0'
		if (paramString == null || paramString.length() == 0) {
			paramString.append("'0'");
		}

		return paramString.toString();
	}

	/**
	 * 
	 * ������������ת����һ��String��
	 * 
	 * @param itor
	 * @return String ��ʽ: '','','',...
	 * @author:Brina ����ʱ�䣺2006-11-16
	 *               <p>
	 */
	public static String toIDString(Iterator itor) {
		int count = 0;
		int stringLen = 0;
		Object o = null;

		StringBuffer paramString = new StringBuffer();

		while (itor.hasNext()) {
			o = itor.next();
			if (o != null && o.toString().trim().length() != 0) {
				// if (count > 0){
				// paramString.append(",");
				// }
				paramString.append("'");
				paramString.append(o.toString());
				paramString.append("',");
				count++;
			}
		}

		// ������Ϊ��,paramString����""
		if (count == 0) {
			paramString.setLength(0);
		} else {
			stringLen = paramString.length() - 1;
			paramString.setLength(stringLen);
		}

		return paramString.toString();
	}

	/**
	 * ���ز��������ŵ��б�
	 * 
	 * @param itor
	 * @return
	 */
	public static String toIDStringLess(Iterator itor) {
		int count = 0;
		Object o = null;

		StringBuffer paramString = new StringBuffer();

		while (itor.hasNext()) {
			o = itor.next();
			if (o != null && o.toString().trim().length() != 0) {
				if (count > 0) {
					paramString.append(",");
				}
				paramString.append(o.toString());
				count++;
			}
		}

		// ������Ϊ��,paramString����""
		if (count == 0) {
			paramString.setLength(0);
		}

		return paramString.toString();
	}

	/**
	 * 
	 * ���������ݲ����б���SQL��ʽ��.
	 * 
	 * @param params
	 * @return String ��ʽ: ?,?,?,...
	 */
	public static String toParamString(Object[] ids, List params) {

		StringBuffer paramString = new StringBuffer();

		if (isNotNull(ids) && ids.length != 0) {
			Object item = null;
			int j = 0;
			for (int i = 0, c = ids.length; i < c; i++) {
				item = ids[i];
				if (isNotNull(item) && item.toString().trim().length() != 0) {

					if (j > 0) {
						paramString.append(",");
					}
					paramString.append(" ? ");
					params.add(item.toString());

					j = j + 1;

				}
			}
		}
		return paramString.toString();
	}

	/**
	 * 
	 * ���������ݲ����б���SQL��ʽ��.
	 * 
	 * @param itor
	 *            Id����������
	 * @param params
	 *            ��Ӧ�Ĳ����б�
	 * @return String ��ʽ (?,?,?,...) ��Ӧparams ���ӶԵ���Ŀ�Ĳ���.
	 * @author:daij ����ʱ�䣺2006-8-8
	 *              <p>
	 */
	public static String toParamString(Iterator itor, List params) {
		int count = 0;
		Object o = null;
		if (params == null)
			params = new ArrayList();
		StringBuffer paramString = new StringBuffer("(");
		while (itor.hasNext()) {
			o = itor.next();
			if (o != null) {
				if (count > 0)
					paramString.append(",");
				paramString.append(" ? ");
				params.add(o.toString());
				count++;
			}
		}

		if (count == 0) {
			paramString.setLength(0);
		} else {
			paramString.append(")");
		}
		return paramString.toString();
	}

	/**
	 * 
	 * ��������bosId���ض�̬���͵�IObjectValue����
	 * 
	 * @param bosId
	 *            ��BosType��Id
	 * @return IObjectValue
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2006-1-13
	 *              <p>
	 */
	public static IObjectValue getDynamicObject(Context ctx, String bosId)
			throws BOSException {
		IObjectValue objectValue = null;
		if (bosId != null && bosId.trim().length() != 0) {

			BOSUuid uuid = BOSUuid.read(bosId);
			ObjectUuidPK pk = new ObjectUuidPK(uuid);
			BOSObjectType objType = uuid.getType();

			IDynamicObject dynamicObject = null;
			if (ctx == null) {
				dynamicObject = DynamicObjectFactory.getRemoteInstance();
			} else {
				dynamicObject = DynamicObjectFactory.getLocalInstance(ctx);
			}
			objectValue = dynamicObject.getValue(objType, pk);
		}
		return objectValue;
	}

	/**
	 * 
	 * ��������������Ƿ�ΪNULL
	 * 
	 * @param o
	 *            ������Ķ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static boolean isNull(Object o) {
		return (o == null);
	}

	/**
	 * 
	 * ��������������Ƿ�ΪNULL
	 * 
	 * @param o
	 *            ������Ķ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-16
	 *              <p>
	 */
	public static boolean isNotNull(Object o) {
		return (!isNull(o));
	}

	public static boolean isNullString(String s) {
		if (isNull(s) || s.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * ��������EAS����Id��ȡBOSType�ַ���
	 * 
	 * @param objectId
	 *            EAS����Id
	 * @return String BOSType�ַ���
	 * @author:daij ����ʱ�䣺2006-11-30
	 *              <p>
	 */
	public static String getBOSTypeStringById(String objectId) {
		String s = null;

		if (!StringUtils.isEmpty(objectId)) {
			BOSUuid id = BOSUuid.read(objectId);

			if (STQMUtils.isNotNull(id)) {
				BOSObjectType bosType = id.getType();
				if (STQMUtils.isNotNull(bosType)) {
					s = bosType.toString();
				}
			}
		}
		return s;
	}

	/**
	 * 
	 * ����������ָ�����Ի��ֶεĶ����Դ�. ע��: �����ʹ��.
	 * 
	 * @param ctx
	 *            �����������
	 * @param propertyName
	 *            ���Ի��ֶ���.
	 * @return String
	 * @author:daij ����ʱ�䣺2007-4-25
	 *              <p>
	 */
	public static String getLocalePropertyName(Context ctx, String propertyName) {
		return getLocaleName(propertyName, ctx.getLocale());
	}

	/**
	 * 
	 * ����������ָ�����Ի��ֶεĶ����Դ�.
	 * 
	 * @param propertyName
	 *            ���Ի��ֶ���.
	 * @param locale
	 *            ����
	 * @return String
	 * @author:daij ����ʱ�䣺2007-4-25
	 *              <p>
	 */
	public static String getLocaleName(String propertyName, Locale locale) {
		String ln = propertyName;
		if (!StringUtils.isEmpty(propertyName) && STQMUtils.isNotNull(locale)) {
			ln = propertyName + "_" + LocaleUtils.getLocaleString(locale);
		}
		return ln;
	}

	public static String getResource(String strKey) {
		if (strKey == null || strKey.trim().length() == 0) {
			return null;
		}
		return EASResource.getString(resource, strKey);
	}

	public static boolean isDiffrent(Object arg0, Object arg1) {
		boolean isChanged = false;

		if ((arg0 == null && arg1 != null) || (arg0 != null && arg1 == null)) {
			isChanged = true;
		} else if (arg0 != null && arg1 != null) {
			// ����Info�����жϣ���PKΪ׼ by paul 2007-8-30
			if (arg0 instanceof CoreBaseInfo && arg1 instanceof CoreBaseInfo) {
				isChanged = !((CoreBaseInfo) arg0).getId().toString().equals(
						((CoreBaseInfo) arg1).getId().toString());
			} else {
				isChanged = !arg0.equals(arg1);
			}
		}
		return isChanged;
	}

	/**
	 * ��5.4�汾�У�viewû�й���setSelector�������ڹ�������ʵ��
	 * 
	 * @author zhiwei_wang
	 * @date 2008-5-27
	 * @param view
	 * @param selector
	 */
	public static void setSelectorOfView(EntityViewInfo view,
			SelectorItemCollection selector) {
		if (view != null && selector != null) {
			for (int i = 0, size = selector.size(); i < size; i++) {
				view.getSelector().add(selector.get(i));
			}
		}
	}

	public static void setSorterOfView(EntityViewInfo view,
			SorterItemCollection sorter) {
		if (view != null && sorter != null) {
			for (int i = 0, size = sorter.size(); i < size; i++) {
				view.getSorter().add(sorter.get(i));
			}
		}
	}

	// �������ʶ��
	private static final String VAR_LEFT = "left";
	// �ұ�����ʶ��
	private static final String VAR_RIGHT = "right";
	// �����ʶ��.
	private static final String PATTERN_STRING_ADD = "left + right";
	private static final String PATTERN_STRING_SUBSTRACT = "left - right";

	public static Object numberCalc(Object left, Object right, String pattern)
			throws Exception {
		Object result = null;
		if (left instanceof Number || right instanceof Number) {
			result = (left instanceof Number) ? left : right;
		}

		if (left instanceof Number && right instanceof Number) {
			Map params = new HashMap();

			params.put(VAR_LEFT, left);

			params.put(VAR_RIGHT, right);

			try {
				result = new Interpreter().eval(pattern, params);
			} catch (InterpreterException e) {
				throw e;
			} catch (ParserException e) {
				throw e;
			}
		}
		return result;
	}

	public static Object numberAdd(Object left, Object right) throws Exception {
		return numberCalc(left, right, PATTERN_STRING_ADD);
	}

	public static Object numberSubstract(Object left, Object right)
			throws Exception {
		return numberCalc(left, right, PATTERN_STRING_SUBSTRACT);
	}

	public static BigDecimal bigDecimalAdd(BigDecimal qty1, BigDecimal qty2) {
		if (qty1 == null) {
			qty1 = SysConstant.BIGZERO;
		}
		if (qty2 == null) {
			qty2 = SysConstant.BIGZERO;
		}
		return qty1.add(qty2);
	}

	public static BigDecimal bigDecimalSubtract(BigDecimal qty1, BigDecimal qty2) {
		if (qty1 == null) {
			qty1 = SysConstant.BIGZERO;
		}
		if (qty2 == null) {
			qty2 = SysConstant.BIGZERO;
		}
		return qty1.subtract(qty2);
	}

	/**
	 * �ַ�������ϲ�
	 * 
	 * @author zhiwei_wang
	 * @date 2008-10-14
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String[] mergeStringArray(String[] s1, String[] s2) {
		if (s1 == null && s2 == null) { // s1 ��s2��Ϊ��
			return new String[0];
		} else if (s1 == null) { // s1Ϊ�գ�s2��Ϊ��
			return s2;
		} else if (s2 == null) { // s1��Ϊ�գ�s2Ϊ��
			return s1;
		} else { // s1��s2����Ϊ��
			HashSet list = new HashSet();
			for (int i = 0; i < s1.length; i++) {
				list.add(s1[i]);
			}
			for (int i = 0; i < s2.length; i++) {
				list.add(s2[i]);
			}
			return (String[]) list.toArray(new String[0]);
		}
	}

	/**
	 * ɾ���ַ��������е�ָ���ַ���
	 * 
	 * @author zhiwei_wang
	 * @date 2008-10-14
	 * @param strArray
	 * @param string
	 * @return
	 */
	public static String[] deleteElementFromStringArray(String[] strArray,
			String string) {
		if (strArray == null) {
			return new String[0];
		}
		if (string == null) {
			return strArray;
		}
		HashSet list = new HashSet();
		for (int i = 0; i < strArray.length; i++) {
			if (!string.toString().equals(strArray[i])) {
				list.add(strArray[i]);
			}
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * ���ַ������������Ԫ��
	 * 
	 * @author zhiwei_wang
	 * @date 2008-10-14
	 * @param strArray
	 * @param string
	 * @return
	 */
	public static String[] addElementToStringArray(String[] strArray,
			String string) {
		if (strArray == null) {
			if (string == null) {
				return new String[0];
			} else {
				String[] s = new String[1];
				s[0] = string;
				return s;
			}
		} else {
			if (string == null) {
				return strArray;
			} else {
				HashSet list = new HashSet();
				for (int i = 0; i < strArray.length; i++) {
					if (!string.toString().equals(strArray[i])) {
						list.add(strArray[i]);
					}
				}
				list.add(string);
				return (String[]) list.toArray(new String[0]);
			}
		}

	}
}
