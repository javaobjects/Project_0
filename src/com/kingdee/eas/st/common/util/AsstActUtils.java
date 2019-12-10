/*
 * @(#)AsstActUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 往来户处理工具类. 职责: 对弱类型的往来户提供封装和解包.
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AsstActUtils {
	/**
	 * 往来户(供应商、客户、部门)ID的Index定义
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTID = 0;

	/**
	 * 往来户(供应商、客户、部门)Number的Index定义
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTNUMBER = 1;

	/**
	 * 往来户(供应商、客户、部门）Name的Index定义
	 */
	public static final int INDEX_FIELDNAMES_ASSTACTNAME = 2;

	/**
	 * 
	 * 描述：获取往来户的类型（供应商、客户、部门）
	 * 
	 * @param asstActId
	 *            供应商、客户、部门的ID号（带有BosType的Id）
	 * @return String BOSType字符串.
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static String getAsstActTypeString(String asstActId) {
		return STQMUtils.getBOSTypeStringById(asstActId);
	}

	/**
	 * 
	 * 描述：从数据库服务器上获取供应商、客户、部门的对象（存在ID、Number、Name信息）
	 * 
	 * @param asstActId
	 *            供应商、客户、部门的ID号
	 * @return IObjectValue
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 * @throws BOSException
	 */
	public static IObjectValue getAsstActInfoByRemote(String asstActId)
			throws BOSException {
		return STQMUtils.getDynamicObject(null, asstActId);
	}

	/**
	 * 
	 * 描述：获取供应商、客户、行政部门对象ID
	 * 
	 * @param asstActId
	 *            供应商、客户、行政部门ID
	 * @param asstActNumber
	 *            供应商、客户、行政部门Number
	 * @param asstActName
	 *            供应商、客户、行政部门Name
	 * @return IObjectValue
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static IObjectValue getAsstActInfo(String asstActId,
			String asstActNumber, String asstActName, Locale locale) {

		IObjectValue info = null;

		// 获取往来户的类型
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
	 * 描述：判断往来户类型是否为供应商
	 * 
	 * @param asstActTypeString
	 *            BOSType字符串
	 * @return boolean
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static boolean isSupplier(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_SUPPLIER
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * 描述：判断往来户类型是否为客户
	 * 
	 * @param asstActTypeString
	 *            BOSType字符串
	 * @return boolean
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static boolean isCustomer(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_CUSTOMER
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * 描述：判断往来户类型是否为行政组织
	 * 
	 * @param asstActTypeString
	 *            BOSType字符串
	 * @return boolean
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static boolean isAdminOrgUnit(String asstActTypeString) {
		return (!StringUtils.isEmpty(asstActTypeString) && STConstant.BOSTYPE_ADMINORGUNIT
				.equalsIgnoreCase(asstActTypeString));
	}

	/**
	 * 
	 * 描述：从单据的editData里提取用于设置F7的往来户Info
	 * 
	 * @param editData
	 *            业务数据值对象.
	 * @param fieldNames
	 *            asstActId, asstActNumber, asstActName在业务数据值对象上的属性名, 数组顺序see
	 *            AsstActUtils.
	 * @return IObjectValue
	 * @author:daij 创建时间：2006-11-7
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
	 * 描述：获取供应商对象ID（对象里面保存供应商ID、Number、Name等信息）
	 * 
	 * @param asstActId
	 *            供应商ID
	 * @param asstActNumber
	 *            供应商Number
	 * @param asstActName
	 *            供应商Name
	 * @return SupplierInfo
	 * @author:daij 创建时间：2006-11-7
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
	 * 描述：获取客户对象ID（对象里面保存客户ID、Number、Name等信息）
	 * 
	 * @param asstActId
	 *            客户ID
	 * @param asstActNumber
	 *            客户Number
	 * @param asstActName
	 *            客户Name
	 * @return CustomerInfo
	 * @author:daij 创建时间：2006-11-7
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
	 * 描述：获取行政组织对象ID（对象里面保存行政组织ID、Number、Name等信息）
	 * 
	 * @param asstActId
	 *            行政组织ID
	 * @param asstActNumber
	 *            行政组织Number
	 * @param asstActName
	 *            行政组织Name
	 * @return AdminOrgUnitInfo
	 * @author:daij 创建时间：2006-11-7
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
	 * 描述：将（供应商、客户、部门）的ID、Number、Name信息加载到Info中
	 * 
	 * @param info
	 * @param asstActId
	 *            往来部门（供应商、客户、部门）ID
	 * @param asstActNumber
	 *            往来部门（供应商、客户、部门）Number
	 * @param asstActName
	 *            往来部门（供应商、客户、部门）Name
	 * @author:daij 创建时间：2006-11-7
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
	 * 描述：将asstActInfo(保存供应商、客户、部门的ID、Number、Name信息对象)传递到editData中
	 * 
	 * @param asstActInfo
	 *            （供应商、客户、部门）对象（保存ID、Number、Name信息）
	 * @param editData
	 *            业务数据值对象.
	 * @param fieldNames
	 *            asstActId, asstActNumber, asstActName在业务数据值对象上的属性名, 数组顺序see
	 *            AsstActUtils.
	 * @author:brina 创建时间：2006-11-7
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
