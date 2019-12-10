/*
 * @(#)STUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.framework.IDynamicObject;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.st.common.ISTBillCommonFacade;
import com.kingdee.eas.st.common.MaxSerialServerFacadeFactory;
import com.kingdee.eas.st.common.STBillBaseInfo;
import com.kingdee.eas.st.common.STBillCommonFacadeFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * ����:
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class STUtils {

	public static final String resourceFile = "com.kingdee.eas.st.common.STResource";

	/**
	 * 
	 * ���������𵥾��Ƿ�ΪBOTPת������
	 * 
	 * @param info
	 *            ������ĵ���editData
	 * @return boolean
	 * @author:Williamwu ����ʱ�䣺2006-11-28
	 *                   <p>
	 */
	public static boolean isFromBOTP(STBillBaseInfo info) {
		return (isNotNull(info) && info.isIsFromBOTP());
	}

	/**
	 * 
	 * ����������String��ȡboolean
	 * 
	 * @param String
	 *            (true��false)
	 * @return boolean
	 * @author:colin_xu ����ʱ�䣺2007-10-15
	 *                  <p>
	 */
	public static boolean StringToBooleanValue(String value) {

		return (Boolean.valueOf(value).booleanValue());
	}

	/**
	 * ��Map����ʽ����vo�е�ֵ
	 * 
	 * @author zhiwei_wang
	 * @param vo
	 * @param fieldNames
	 * @return
	 */
	public static Map getMapValues(IObjectValue vo, String[] fieldNames) {
		Map map = new HashMap();
		if (fieldNames != null && vo != null) {
			for (int i = 0; i < fieldNames.length; i++) {
				map.put(fieldNames[i], vo.get(fieldNames[i]));
			}
		}
		return map;
	}

	/**
	 * ����ID�ļ��ϣ�Ȼ��ʹF7ֻ������Щ�����ڵ�Ԫ��
	 * 
	 * @author qihui_sun 2007-1-12,modified 2007-03-16
	 * @param compareIds
	 *            �������ƵĶ���ids
	 * @param range
	 *            �����ƵĶ���
	 * @param field
	 *            �����,һ��Ϊ"xxx.id"
	 * @param kdtEntries
	 * @param rowIndex
	 * @param col
	 */

	public static void setF7Range(String field, String compareIds,
			IObjectValue range, KDTable kdtEntries, String col) {
		if (compareIds != null) {
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo(field, compareIds, CompareType.INCLUDE));
			viewInfo.setFilter(filter);

			ICellEditor cellEditor = kdtEntries.getColumn(col).getEditor();
			if (cellEditor != null) {
				KDBizPromptBox prmtF7 = ((KDBizPromptBox) cellEditor
						.getComponent());
				prmtF7.setEntityViewInfo(viewInfo);
				// ʹF7�е��б��ںϱ�ͷ
				prmtF7.setMergeColumnKeys(new String[] { "number", "name" });
				prmtF7.setSelector(null);

				kdtEntries.getColumn(col).setEditor(cellEditor);
			}
		}
	}

	/**
	 * �����������ʽ���ַ���: "a,b,...z" �� sunbird 2007-03-13
	 * 
	 * @param ch
	 *            �����
	 * @return ��������Ϊ�գ��򷵻ؿ�
	 */
	public static StringBuffer insertDelimit(Set materialSet, String ch) {
		if (ch == null || materialSet == null) {
			return null;
		}

		StringBuffer strParameter = new StringBuffer();
		Iterator it = materialSet.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Object o = it.next();
			if (o != null) {
				if (i > 0) {
					// strParameter.append("'");
					// strParameter.append(pks[i]);
					// strParameter.append("'");
					strParameter.append(ch);
				}

				// strParameter.append("'");
				strParameter.append(o.toString());
				// strParameter.append("'");
			}
		}
		return strParameter;
	}

	/**
	 * 
	 * ��������ȡ���ο����Զ����getSelectors
	 * 
	 * 1.��EditUI��getSelectors�Ὣ���е�LinkedPropertyչ��Ϊ:LinkedProperty.*
	 * ����ORMapping�ὫLinkedProperty.*����Ϊ�ñ����е��ֶΣ����ĳ�������αȽ����LinkedProperty
	 * �Ƚ϶�ͻᵼ������ִ��getValue��SQL��䳬��.
	 * 2.���ο����Զ����getSelectors��չ���ȫ��Ϊ��com.kingdee.eas
	 * .scm.sd.sale.SaleOrderInfoSelectorEx
	 * 
	 * @param entityInfoClassName
	 *            XXXInfo����ȫ��,���磺 com.kingdee.eas.scm.sd.sale.SaleOrderInfo
	 * @return SelectorItemCollection
	 * @author:daij ����ʱ�䣺2008-6-3
	 *              <p>
	 */
	public static SelectorItemCollection getExtSelectors(
			String entityInfoClassName) {
		SelectorItemCollection selectors = new SelectorItemCollection();
		if (!StringUtils.isEmpty(entityInfoClassName)) {
			try {
				// ���ο����Զ����getSelectors��չ���ȫ��Ϊ��com.kingdee.eas.scm.sd.sale.
				// SaleOrderInfoSelectorEx
				Object selectorEx = Class.forName(
						entityInfoClassName + "SelectorEx").newInstance();
				if (selectorEx instanceof ISelectorEx) {
					selectors = ((ISelectorEx) selectorEx).getExtSelectors();
				}
			} catch (Exception e) {
				// �����κδ���Ͳ������ⲿ��Selectors.
			}
		}
		return selectors;
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
	 * ͨ���������ݣ�ȡ��IDList��
	 * 
	 * @param vos
	 *            ��������
	 * @param delim
	 *            �ָ��
	 * @return ����ID�ַ�����
	 */
	public static String getKeyIdList(ObjectBaseInfo[] vos, String delim) {
		if (vos == null || vos.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < vos.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(vos[i].getId().toString());
		}
		return sb.toString();
	}

	public static Timestamp getServerDate() throws Exception {
		ISTBillCommonFacade iSTBillCommonFacade = STBillCommonFacadeFactory
				.getRemoteInstance();
		return iSTBillCommonFacade.getServerDate();

	}

	/**
	 * ���ض����ڶ��������е�λ��
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-18
	 * @param array
	 * @param str
	 * @return
	 */
	public static int getIndexOfArray(Object[] array, Object str) {
		if (array != null && str != null) {
			for (int i = 0; i < array.length; i++) {
				if (str.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * ��IObjectPK����ת��ΪString����
	 * 
	 * @author zhiwei_wang
	 * @date 2008-11-18
	 * @param pks
	 * @return
	 */
	public static String[] changeIObjectPK2String(IObjectPK[] pks) {
		if (pks != null) {
			ArrayList list = new ArrayList();
			for (int i = 0; i < pks.length; i++) {
				if (pks[i] != null) {
					list.add(pks[i].toString());
				} else {
					list.add(null);
				}
			}
			return (String[]) list.toArray(new String[0]);
		}
		return new String[0];
	}

	/**
	 * ���ַ������飬ת��ΪIObjectPK���飻�ַ��������е�ÿ���ַ�������Ӧһ��PK
	 * 
	 * @param ids
	 * @return
	 */
	public static IObjectPK[] changeStrings2IObjectPK(String[] ids) {
		if (ids != null) {
			ArrayList list = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				if (!StringUtils.isEmpty(ids[i])) {
					list.add(new ObjectStringPK(ids[i]));
				}
			}
			return (ObjectStringPK[]) list.toArray(new ObjectStringPK[0]);
		}
		return new ObjectStringPK[0];
	}

	public static String generateNumber(Context ctx, IObjectValue bizObj,
			OrgUnitInfo org, String subSystem) throws BOSException,
			EASBizException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		String number = null;

		// ������ñ������ֱ�Ӵ�ϵͳ��ȡ����
		if (org != null
				&& iCodingRuleManager.isExist(bizObj, org.getId().toString())) {
			number = iCodingRuleManager.getNumber(bizObj, org.getId()
					.toString(), "");
		} else // ���û�����ñ��������˳��ţ���ˮ��(3λ)
		{
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String dataStr = sdf.format(new Date());
				long num = MaxSerialServerFacadeFactory.getLocalInstance(ctx)
						.getMaxSerial(subSystem, dataStr);
				number = num % 1000 + "";
				while (number.length() < 3)
					number = "0" + number;
				number = dataStr + number;
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		return number;
	}

	public static Class getClassInstance(String className) {
		if (StringUtils.isEmpty(className)) {
			return null;
		} else {
			String piexClassName = null;
			String normalClassName = null;
			if (className.endsWith("PIEx")) {
				piexClassName = className;
				normalClassName = className
						.substring(0, className.length() - 4);
			} else {
				piexClassName = className + "PIEx";
				normalClassName = className;
			}
			// ���ȼ���piex
			try {
				Class process = Class.forName(piexClassName);
				return process;
			} catch (ClassNotFoundException e1) {
				// Do Nothing
			}
			// ��μ���������class
			try {
				Class process = Class.forName(normalClassName);
				return process;
			} catch (ClassNotFoundException e1) {
				// Do Nothing
			}
			// �����û�У��򷵻�null
			return null;
		}
	}

	/**
	 * ���ؼ����ip��ַ
	 * 
	 * @return
	 */
	public static String getHostAddress() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println("localhost: " + localhost.getHostAddress());
			System.out.println("localhost: " + localhost.getHostName());
			return localhost.getHostAddress();
		} catch (UnknownHostException uhe) {
			System.err.println("Localhost not seeable. Something is odd. ");
		}
		return "";
	}

	/**
	 * ������ 'id1','id2','id3' ���ַ�����ת��ΪIObjectPK���飬����ַ���Ϊ�գ��򷵻ش�СΪ0������
	 * 
	 * @param sqlString
	 * @return
	 */
	public static IObjectPK[] sqlString2IObjectArray(String sqlString) {
		IObjectPK[] retVal = new IObjectPK[0];
		List pkList = new ArrayList();
		if (!StringUtils.isEmpty(sqlString)) {
			String[] pkStringList = sqlString.split(",");
			for (int i = 0; i < pkStringList.length; i++) {
				String id = pkStringList[i].replaceAll("'", "");
				pkList.add(new ObjectStringPK(id));
			}
			retVal = (IObjectPK[]) pkList.toArray(retVal);
		}

		return retVal;
	}

	public static boolean easObjectEquals(Object oldValue, Object newValue) {

		// ��ΪnullҲ����ȵ�
		if (oldValue == null && newValue == null) {
			return true;
		}

		// ���һ��Ϊnullһ����Ϊnull�������
		if (!(oldValue != null && newValue != null)) {
			return false;
		}

		// ������϶����ͣ�ȷ�����ͺ��ٽ��бȽ�
		if (oldValue instanceof IObjectValue
				&& newValue instanceof IObjectValue) {
			String oldValueString = ((IObjectValue) oldValue).get("id")
					.toString();
			String newValueString = ((IObjectValue) newValue).get("id")
					.toString();

			if (oldValueString.equals(newValueString)) {
				return true;
			} else {
				return false;
			}
		}

		// ����ֱ治�����ͣ���ֱ����Ϊ����ȡ�
		return false;

	}

	public static void moveTogether(DataChangeEvent e, Map propMap) {
		if (isDiffrent(e.getNewValue(), e.getOldValue())) {
			try {
				IObjectValue iov = (IObjectValue) e.getNewValue();
				moveTogether2(iov, propMap);
			} catch (DataAccessException ex) {
				ex.printStackTrace();
			} catch (BOSException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void moveTogether2(IObjectValue iov, Map propMap)
			throws DataAccessException, BOSException {
		for (Iterator it = propMap.keySet().iterator(); it.hasNext();) {
			String prop = it.next().toString();
			Object comp = propMap.get(prop);
			if (comp instanceof KDTextField) {// ���ı�
				((KDTextField) comp).setText(UIRuleUtil.getString(UIRuleUtil
						.getProperty(iov, prop)));
			} else if (comp instanceof KDTextArea) {// ���ı�
				((KDTextArea) comp).setText(UIRuleUtil.getString(UIRuleUtil
						.getProperty(iov, prop)));
			} else if (comp instanceof KDBizPromptBox) {// F7
				((KDBizPromptBox) comp).setData(UIRuleUtil.getProperty(iov,
						prop));
			} else if (comp instanceof KDBizMultiLangBox) {
				((KDBizMultiLangBox) comp).setDefaultLangItemData(UIRuleUtil
						.getString(UIRuleUtil.getProperty(iov, prop)));
			} else if (comp instanceof KDComboBox) {// �����б�
				((KDComboBox) comp).setSelectedItem(UIRuleUtil.getProperty(iov,
						prop));
			} else if (comp instanceof KDDatePicker) {// ���ڿؼ�
				((KDDatePicker) comp).setValue(UIRuleUtil
						.getProperty(iov, prop));
			}
		}
	}

	/**
	 * ����:����ֹ��ǰ����������ǰ
	 */
	public final static int DATESTART = 0;
	public final static int DATEEND = 1;

	public static Timestamp getTimestamp(Timestamp value, int dateType) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		if (dateType == DATESTART) {
			calendar.set(calendar.get(Calendar.YEAR), calendar
					.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else {
			calendar.set(calendar.get(Calendar.YEAR), calendar
					.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
					23, 59, 59);
			calendar.set(Calendar.MILLISECOND, 998);
		}

		return new Timestamp(calendar.getTimeInMillis());

	}

	/**
	 * ��鵱ǰ��¼������֯�Ƿ������ʼ��
	 * 
	 * @return
	 * @throws BOSException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void checkIsInit() throws BOSException, EASBizException {
		ISystemStatusCtrol sysctrol = SystemStatusCtrolFactory
				.getRemoteInstance();
		String companyId = SysContext.getSysContext().getCurrentFIUnit()
				.getId().toString();
		if (!sysctrol.isStart(
				SystemEnum.ACTIVITY_BASED_COSTING_MANAGEMENT_VALUE, companyId)) {
			MsgBox.showError("��ǰ������֯δ������ʼ��");//EASResource.getString(resourceFile
												// ,"IS_NOT_BIZUNIT"));
			SysUtil.abort();
		}
	}

	/**
	 * ��鵱ǰ��¼�Ĳ�����֯�Ƿ�Ϊ����
	 */
	public static void checkCurrentCompanyOrg() {
		CompanyOrgUnitInfo companyOrgUnitInfo = SysContext.getSysContext()
				.getCurrentFIUnit();
		if (companyOrgUnitInfo == null || !companyOrgUnitInfo.isIsBizUnit()) {
			MsgBox.showError(EASResource.getString(resourceFile,
					"IS_NOT_BIZUNIT"));
			SysUtil.abort();
		}
	}

	/**
	 * ��ȡ��ǰ�ڼ�
	 * 
	 * @param ctx
	 * @param companyId
	 * @return ��ǰ�ڼ�
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static PeriodInfo getCurrentPeriod(Context ctx, String companyId)
			throws EASBizException, BOSException {
		ISystemStatusCtrol ibiz;
		if (ctx == null)
			ibiz = SystemStatusCtrolFactory.getRemoteInstance();
		else
			ibiz = SystemStatusCtrolFactory.getLocalInstance(ctx);
		return ibiz.getCacheCurrentPeriod(
				SystemEnum.ACTIVITY_BASED_COSTING_MANAGEMENT_VALUE, companyId);

	}

	/**
	 * ��ȡ�����ڼ�
	 * 
	 * @param ctx
	 * @param companyId
	 * @return �����ڼ�
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static PeriodInfo getStartPeriod(Context ctx, String companyId)
			throws EASBizException, BOSException {
		ISystemStatusCtrol ibiz;
		if (ctx == null)
			ibiz = SystemStatusCtrolFactory.getRemoteInstance();
		else
			ibiz = SystemStatusCtrolFactory.getLocalInstance(ctx);
		return ibiz.getStartPeriod(
				SystemEnum.ACTIVITY_BASED_COSTING_MANAGEMENT_VALUE, companyId);

	}

	/**
	 * Ϊ���ж�
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		return o == null;
	}

	/**
	 * ��Ϊ���ж�
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNotNull(Object o) {
		return null != o && !"".equalsIgnoreCase(o.toString());
	}

	/**
	 * �����Ƿ����
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean isDiffrent(Object arg0, Object arg1) {
		boolean isChanged = false;
		if (arg0 == null && arg1 != null || arg0 != null && arg1 == null)
			isChanged = true;
		else if (arg0 != null && arg1 != null)
			if ((arg0 instanceof CoreBaseInfo)
					&& (arg1 instanceof CoreBaseInfo))
				isChanged = !((CoreBaseInfo) arg0).getId().toString().equals(
						((CoreBaseInfo) arg1).getId().toString());
			else
				isChanged = !arg0.equals(arg1);
		return isChanged;
	}

	public static String toIDString(String id) {
		if (id == null)
			id = "";
		return "'" + id + "'";
	}

	public static String toIDString(Object ids[]) {
		StringBuffer paramString = new StringBuffer();
		if (isNotNull(((Object) (ids))) && ids.length != 0) {
			Object item = null;
			int j = 0;
			int i = 0;
			for (int c = ids.length; i < c; i++) {
				item = ids[i];
				if (!isNotNull(item) || item.toString().trim().length() == 0)
					continue;
				if (j > 0)
					paramString.append(",");
				paramString.append("'");
				paramString.append(item.toString());
				paramString.append("'");
				j++;
			}

		}
		return paramString.toString();
	}

	public static String toIDString(Iterator itor) {
		int count = 0;
		int stringLen = 0;
		Object o = null;
		StringBuffer paramString = new StringBuffer();
		do {
			if (!itor.hasNext())
				break;
			o = itor.next();
			if (o != null && o.toString().trim().length() != 0) {
				if (count > 0)
					paramString.append("'");
				paramString.append(o.toString());
				paramString.append("',");
				count++;
			}
		} while (true);
		if (count == 0) {
			paramString.setLength(0);
		} else {
			stringLen = paramString.length() - 1;
			paramString.setLength(stringLen);
		}
		return paramString.toString();
	}

	public static String toIDStringLess(Iterator itor) {
		int count = 0;
		Object o = null;
		StringBuffer paramString = new StringBuffer();
		do {
			if (!itor.hasNext())
				break;
			o = itor.next();
			if (o != null && o.toString().trim().length() != 0) {
				if (count > 0)
					paramString.append(",");
				paramString.append(o.toString());
				count++;
			}
		} while (true);
		if (count == 0)
			paramString.setLength(0);
		return paramString.toString();
	}

	public static String toParamString(Object ids[], List params) {
		StringBuffer paramString = new StringBuffer();
		if (isNotNull(((Object) (ids))) && ids.length != 0) {
			Object item = null;
			int j = 0;
			int i = 0;
			for (int c = ids.length; i < c; i++) {
				item = ids[i];
				if (!isNotNull(item) || item.toString().trim().length() == 0)
					continue;
				if (j > 0)
					paramString.append(",");
				paramString.append(" ? ");
				params.add(item.toString());
				j++;
			}

		}
		return paramString.toString();
	}

	public static String toParamString(Iterator itor, List params) {
		int count = 0;
		Object o = null;
		if (params == null)
			params = new ArrayList();
		StringBuffer paramString = new StringBuffer("(");
		do {
			if (!itor.hasNext())
				break;
			o = itor.next();
			if (o != null) {
				if (count > 0)
					paramString.append(",");
				paramString.append(" ? ");
				params.add(o.toString());
				count++;
			}
		} while (true);
		if (count == 0)
			paramString.setLength(0);
		else
			paramString.append(")");
		return paramString.toString();
	}
}
