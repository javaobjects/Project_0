/*
 * @(#)AsstActUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.util.StringUtils;

/**
 * ����: ��������������. ְ��: �������͵��������ṩ��װ�ͽ��.
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AsstActUtils {
	/**
	 * ������(��Ӧ�̡��ͻ�������)ID��Index����
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTID = 0;

	/**
	 * ������(��Ӧ�̡��ͻ�������)Number��Index����
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTNUMBER = 1;

	/**
	 * ������(��Ӧ�̡��ͻ������ţ�Name��Index����
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTNAME = 2;

	/**
	 * 
	 * ��������ȡ�����������ͣ���Ӧ�̡��ͻ������ţ�
	 * 
	 * @param asstActId
	 *            ��Ӧ�̡��ͻ������ŵ�ID�ţ�����BosType��Id��
	 * @return String BOSType�ַ���.
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static String getAsstActTypeString(String asstActId) {
		return STQMUtils.getBOSTypeStringById(asstActId);
	}

	/**
	 * 
	 * �����������ݿ�������ϻ�ȡ��Ӧ�̡��ͻ������ŵĶ��󣨴���ID��Number��Name��Ϣ��
	 * 
	 * @param asstActId
	 *            ��Ӧ�̡��ͻ������ŵ�ID��
	 * @return IObjectValue
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 * @throws BOSException
	 */
	public static IObjectValue getAsstActInfoByRemote(String asstActId)
			throws BOSException {
		return STQMUtils.getDynamicObject(null, asstActId);
	}

	/**
	 * 
	 * ��������ȡ��Ӧ�̡��ͻ����������Ŷ���ID
	 * 
	 * @param asstActId
	 *            ��Ӧ�̡��ͻ�����������ID
	 * @param asstActNumber
	 *            ��Ӧ�̡��ͻ�����������Number
	 * @param asstActName
	 *            ��Ӧ�̡��ͻ�����������Name
	 * @return IObjectValue
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static IObjectValue getAsstActInfo(String asstActId,
			String asstActNumber, String asstActName, Locale locale) {

		IObjectValue info = null;

		// ��ȡ������������
		String asstActTypeString = getAsstActTypeString(asstActId);

		if (isSupplier(asstActTypeString)) {
			info = getSupplierInfo(asstActId, asstActNumber, asstActName,
					locale);
		} else if (isCustomer(asstActTypeString)) {
			info = getCustmerInfo(asstActId, asstActNumber, asstActName, locale);
		} else if (isAdminOrgUnit(asstActTypeString)) {
			info = getAdminOrgUnitInfo(asstActId, asstActNumber, asstActName,
					locale);
		} else {
			info = getCustmerInfo(asstActId, asstActNumber, asstActName, locale);
		}

		return info;
	}

	/**
	 * 
	 * �������ж������������Ƿ�Ϊ��Ӧ��
	 * 
	 * @param asstActTypeString
	 *            BOSType�ַ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static boolean isSupplier(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_SUPPLIER
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * �������ж������������Ƿ�Ϊ�ͻ�
	 * 
	 * @param asstActTypeString
	 *            BOSType�ַ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static boolean isCustomer(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_CUSTOMER
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * �������ж������������Ƿ�Ϊ������֯
	 * 
	 * @param asstActTypeString
	 *            BOSType�ַ���
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static boolean isAdminOrgUnit(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_ADMINORGUNIT
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * �������ӵ��ݵ�editData����ȡ��������F7��������Info
	 * 
	 * @param editData
	 *            ҵ������ֵ����.
	 * @param fieldNames
	 *            asstActId, asstActNumber, asstActName��ҵ������ֵ�����ϵ�������, ����˳��see
	 *            AsstActUtils.
	 * @return IObjectValue
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static IObjectValue getAsstActInfo(IObjectValue editData,
			String[] fieldNames, Locale locale) {
		IObjectValue info = null;
		if (!STQMUtils.isNull(editData) && !STQMUtils.isNull(fieldNames)
				&& fieldNames.length >= INDEX_FIELDNAMES_ASSTACTNAME) {
			info = getAsstActInfo(editData.get(
					fieldNames[INDEX_FIELDNAMES_ASSTACTID]).toString(),
					editData.get(fieldNames[INDEX_FIELDNAMES_ASSTACTNUMBER])
							.toString(), editData.get(
							fieldNames[INDEX_FIELDNAMES_ASSTACTNAME])
							.toString(), locale);
		}
		return info;
	}

	/**
	 * 
	 * ��������ȡ��Ӧ�̶���ID���������汣�湩Ӧ��ID��Number��Name����Ϣ��
	 * 
	 * @param asstActId
	 *            ��Ӧ��ID
	 * @param asstActNumber
	 *            ��Ӧ��Number
	 * @param asstActName
	 *            ��Ӧ��Name
	 * @return SupplierInfo
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static SupplierInfo getSupplierInfo(String asstActId,
			String asstActNumber, String asstActName, Locale locale) {
		SupplierInfo supplier = new SupplierInfo();
		setAsstActToInfo(supplier, asstActId, asstActNumber, asstActName,
				locale);
		return supplier;
	}

	/**
	 * 
	 * ��������ȡ�ͻ�����ID���������汣��ͻ�ID��Number��Name����Ϣ��
	 * 
	 * @param asstActId
	 *            �ͻ�ID
	 * @param asstActNumber
	 *            �ͻ�Number
	 * @param asstActName
	 *            �ͻ�Name
	 * @return CustomerInfo
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static CustomerInfo getCustmerInfo(String asstActId,
			String asstActNumber, String asstActName, Locale locale) {
		CustomerInfo customer = new CustomerInfo();
		setAsstActToInfo(customer, asstActId, asstActNumber, asstActName,
				locale);
		return customer;
	}

	/**
	 * 
	 * ��������ȡ������֯����ID���������汣��������֯ID��Number��Name����Ϣ��
	 * 
	 * @param asstActId
	 *            ������֯ID
	 * @param asstActNumber
	 *            ������֯Number
	 * @param asstActName
	 *            ������֯Name
	 * @return AdminOrgUnitInfo
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static AdminOrgUnitInfo getAdminOrgUnitInfo(String asstActId,
			String asstActNumber, String asstActName, Locale locale) {
		AdminOrgUnitInfo adminOrg = new AdminOrgUnitInfo();
		setAsstActToInfo(adminOrg, asstActId, asstActNumber, asstActName,
				locale);
		return adminOrg;
	}

	/**
	 * 
	 * ������������Ӧ�̡��ͻ������ţ���ID��Number��Name��Ϣ���ص�Info��
	 * 
	 * @param info
	 * @param asstActId
	 *            �������ţ���Ӧ�̡��ͻ������ţ�ID
	 * @param asstActNumber
	 *            �������ţ���Ӧ�̡��ͻ������ţ�Number
	 * @param asstActName
	 *            �������ţ���Ӧ�̡��ͻ������ţ�Name
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static void setAsstActToInfo(IObjectValue info, String asstActId,
			String asstActNumber, String asstActName, Locale locale) {
		try {
			info.put("id", BOSUuid.read(asstActId));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		info.put("number", asstActNumber);

		info.put("name", asstActName);

		info.put(STQMUtils.getLocaleName("name", locale), asstActName);
	}

	/**
	 * 
	 * ��������asstActInfo(���湩Ӧ�̡��ͻ������ŵ�ID��Number��Name��Ϣ����)���ݵ�editData��
	 * 
	 * @param asstActInfo
	 *            ����Ӧ�̡��ͻ������ţ����󣨱���ID��Number��Name��Ϣ��
	 * @param editData
	 *            ҵ������ֵ����.
	 * @param fieldNames
	 *            asstActId, asstActNumber, asstActName��ҵ������ֵ�����ϵ�������, ����˳��see
	 *            AsstActUtils.
	 * @author:brina ����ʱ�䣺2006-11-7
	 *               <p>
	 */
	public static void setAsstActInfoToEditData(IObjectValue asstActInfo,
			IObjectValue editData, String[] fieldNames, Locale locale) {

		String id = null;
		String number = null;
		String name = null;
		String localName = null;
		if (!STQMUtils.isNull(asstActInfo)) {

			if (asstActInfo.get("id") != null) {
				id = asstActInfo.get("id").toString();
				number = asstActInfo.getString("number");
				name = asstActInfo.getString("name");
				localName = asstActInfo.getString(STQMUtils.getLocaleName(
						"name", locale));
			}
		}

		editData.setString(fieldNames[INDEX_FIELDNAMES_ASSTACTID], id);
		editData.setString(fieldNames[INDEX_FIELDNAMES_ASSTACTNUMBER], number);
		editData.setString(fieldNames[INDEX_FIELDNAMES_ASSTACTNAME], name);
		editData.setString(STQMUtils.getLocaleName(
				fieldNames[INDEX_FIELDNAMES_ASSTACTNAME], locale), localName);
	}
}
