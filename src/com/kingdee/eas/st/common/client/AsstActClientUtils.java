/*
 * @(#)AsstActUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.ICSSPGroupStandard;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.client.F7CustomerTreeDetailListUI;
import com.kingdee.eas.basedata.master.cssp.client.F7SupplierTreeDetailListUI;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.st.common.util.AsstActUtils;
import com.kingdee.util.StringUtils;

/**
 * 描述:
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AsstActClientUtils {
	/**
	 * 
	 * 描述：根据往来户（客户、供应商、部门）的类型，F7显示不同的内容
	 * 
	 * @param uiObject
	 * @param asstActTypeString
	 *            一个不可重复的长度为8的字符串标识业务对象（客户、供应商、部门）
	 * @return
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 * @throws Exception
	 */
	public static KDBizPromptBox setAsstActF7(CoreUIObject uiObject,
			String asstActTypeString) throws Exception {
		KDBizPromptBox f7 = new KDBizPromptBox();
		setAsstActF7(uiObject, f7, asstActTypeString);
		return f7;
	}

	/**
	 * 
	 * 描述：根据往来户（客户、供应商、部门）的类型，控制F7的显示内容
	 * 
	 * @param uiObject
	 * @param f7
	 *            F7显示对象（客户、供应商、部门）
	 * @param asstActTypeString
	 *            一个不可重复的长度为8的字符串标识业务对象（客户、供应商、部门）
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setAsstActF7(CoreUIObject uiObject, KDBizPromptBox f7,
			String asstActTypeString) throws Exception {
		if (AsstActUtils.isSupplier(asstActTypeString)) {
			setSupplierF7(uiObject, f7);
		} else if (AsstActUtils.isCustomer(asstActTypeString)) {
			setCustomerF7(uiObject, f7);
		} else if (AsstActUtils.isAdminOrgUnit(asstActTypeString)) {
			setAdminOrgUnitF7(uiObject, f7);
		}
	}

	/**
	 * 
	 * 描述：根据往来户（客户、供应商、部门）的类型，控制F7的显示内容
	 * 
	 * @param uiObject
	 * @param f7
	 *            F7显示对象（客户、供应商、部门）
	 * @param asstActTypeString
	 *            一个不可重复的长度为8的字符串标识业务对象（客户、供应商、部门）
	 * @author:zhiwei_wang 创建时间：2006-11-7
	 *                     <p>
	 * @throws Exception
	 */
	public static void setAsstActF7ByTypeID(CoreUIObject uiObject,
			KDBizPromptBox f7, String typeId) throws Exception {
		if (typeId.equals(STConstant.QIBIZTYPE_PURCHASE)) {
			setSupplierF7(uiObject, f7);
		} else if (typeId.equals(STConstant.QIBIZTYPE_SALE)) {
			setCustomerF7(uiObject, f7);
		} else if (typeId.equals(STConstant.QIBIZTYPE_STORGE)) {
			setAdminOrgUnitF7(uiObject, f7);
		}
	}

	/**
	 * 
	 * 描述：获取（供应商、客户、部门）F7对象
	 * 
	 * @param f7
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 */
	public static IObjectValue getInfoByF7(KDBizPromptBox f7) {
		IObjectValue info = null;

		Object o = f7.getData();
		if (!STQMUtils.isNull(o) && o instanceof IObjectValue) {
			info = (IObjectValue) o;
		}
		return info;
	}

	/***************************************************************************************************************
	 * 描述：供应商对应的F7对象(供查询使用)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static KDBizPromptBox supplierF7(CoreUIObject uiObject)
			throws Exception {
		KDBizPromptBox f7 = new KDBizPromptBox();
		setSupplierF7(uiObject, f7);
		return f7;
	}

	/**
	 * 
	 * 描述：获取供应商F7查询对象信息
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setSupplierF7(CoreUIObject uiObject, KDBizPromptBox f7)
			throws Exception {
		// 判断传入的对象是否为空，为空的话创建一个F7控件
		if (f7 == null)
			f7 = new KDBizPromptBox();

		// 对F7显示的信息及格式进行初始化
		String queryInfo = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery";

		f7.setEditable(true);
		f7.setDisplayFormat("$name$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setSelector(null);
		f7.setEntityViewInfo(null);
		f7.setQueryInfo(queryInfo);

		FilterInfo filter = new FilterInfo();
		ICSSPGroupStandard cSSPGroupStandard = CSSPGroupStandardFactory
				.getRemoteInstance();
		String groupStandardId = cSSPGroupStandard.getBasicStandardId("2"); // type
																			// ：
																			// 1
																			// 表示客户
																			// 2
																			// 表示供应商
		filter.getFilterItems()
				.add(
						new FilterItemInfo("browseGroup.groupStandard",
								groupStandardId));

		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);

		f7.setEntityViewInfo(evi);

		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = null;
		selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(f7,
				new F7SupplierTreeDetailListUI(), uiObject, CSSPGroupInfo
						.getBosType(), queryInfo, "browseGroup.id");
		f7.setSelector(selectorLisenterSupplier);
		f7.addSelectorListener(selectorLisenterSupplier);

	}

	/**
	 * 描述： 获取供应商F7查询对象信息
	 * 
	 * @param f7
	 * @return
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 */
	public static SupplierInfo getSupplierByF7(KDBizPromptBox f7) {
		SupplierInfo supplier = new SupplierInfo();

		Object o = f7.getData();
		if (!STQMUtils.isNull(o) && o instanceof SupplierInfo) {
			supplier = (SupplierInfo) o;
		}
		return supplier;
	}

	/********************************************************************************************************************
	 * 描述：客户对应的F7对象(供查询使用)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static KDBizPromptBox customerF7(CoreUIObject uiObject)
			throws Exception {
		KDBizPromptBox f7 = new KDBizPromptBox();
		setCustomerF7(uiObject, f7);
		return f7;
	}

	/**
	 * 
	 * 描述：获取客户F7查询对象信息
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setCustomerF7(CoreUIObject uiObject, KDBizPromptBox f7)
			throws Exception {
		// 判断传入的对象是否为空，为空的话创建一个F7控件
		if (f7 == null)
			f7 = new KDBizPromptBox();

		// 对F7显示的信息及格式进行初始化
		String queryInfo = "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery";

		f7.setEditable(true);
		f7.setDisplayFormat("$name$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setSelector(null);
		f7.setEntityViewInfo(null);
		f7.setQueryInfo(queryInfo);

		FilterInfo filter = new FilterInfo();
		ICSSPGroupStandard cSSPGroupStandard = CSSPGroupStandardFactory
				.getRemoteInstance();
		String groupStandardId = cSSPGroupStandard.getBasicStandardId("1");// type
																			// ：
																			// 1
																			// 表示客户
																			// 2
																			// 表示供应商
		filter.getFilterItems()
				.add(
						new FilterItemInfo("browseGroup.groupStandard",
								groupStandardId));

		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);

		f7.setEntityViewInfo(evi);

		GeneralKDPromptSelectorAdaptor selectorLisenterCustomer = null;
		selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(f7,
				new F7CustomerTreeDetailListUI(), uiObject, CSSPGroupInfo
						.getBosType(), queryInfo, "browseGroup.id");

		f7.setSelector(selectorLisenterCustomer);
		f7.addSelectorListener(selectorLisenterCustomer);

	}

	/**
	 * 描述： 获取客户F7查询对象信息
	 * 
	 * @param f7
	 * @return
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 */
	public static CustomerInfo getCustomerByF7(KDBizPromptBox f7) {
		CustomerInfo customer = new CustomerInfo();

		Object o = f7.getData();
		if (!STQMUtils.isNull(o) && o instanceof CustomerInfo) {
			customer = (CustomerInfo) o;
		}
		return customer;
	}

	/***************************************************************************************************************
	 * 描述：行政组织对应的F7对象(供查询使用)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static KDBizPromptBox adminOrgUnitF7(CoreUIObject uiObject)
			throws Exception {
		KDBizPromptBox f7 = new KDBizPromptBox();
		setAdminOrgUnitF7(uiObject, f7);
		return f7;
	}

	/**
	 * 
	 * 描述：获取行政组织F7查询对象信息
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina 创建时间：2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setAdminOrgUnitF7(CoreUIObject uiObject,
			KDBizPromptBox f7) throws Exception {
		// 判断传入的对象是否为空，为空的话创建一个F7控件
		if (f7 == null) {
			f7 = new KDBizPromptBox();
		}

		if (uiObject instanceof STBillBaseFilterUI) {
			STBillBaseFilterUI ui = (STBillBaseFilterUI) uiObject;
			OrgUnitClientUtils.setBizOrgF7ByDelegation(f7, OrgType.Admin, ui
					.getMainBizOrgType(), (OrgUnitInfo) ui.getMainBizOrgF7()
					.getValue(), true);
		} else if (uiObject instanceof STBaseRptConditionUI) {
			STBaseRptConditionUI ui = (STBaseRptConditionUI) uiObject;
			OrgUnitClientUtils.setBizOrgF7ByDelegation(f7, OrgType.Admin, ui
					.getMainBizOrgType(), (OrgUnitInfo) ui.getMainBizOrgF7()
					.getValue(), true);
		} else if (uiObject instanceof CoreUI) {
			CoreUI ui = (CoreUI) uiObject;
			OrgUnitClientUtils.setBizOrgF7ByDelegation(f7, OrgType.Admin, ui
					.getMainType(), ui.getMainOrgInfo(), true);
		}
	}

	/**
	 * 描述： 获取行政组织F7查询对象信息
	 * 
	 * @param f7
	 * @return
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static AdminOrgUnitInfo getAdminOrgUnitByF7(KDBizPromptBox f7) {
		AdminOrgUnitInfo adminOrgUnit = new AdminOrgUnitInfo();

		Object o = f7.getData();
		if (!STQMUtils.isNull(o) && o instanceof AdminOrgUnitInfo) {
			adminOrgUnit = (AdminOrgUnitInfo) o;
		}
		return adminOrgUnit;
	}

	/**
	 * 
	 * 描述：根据往来户（供应商、客户、部门）ID从远程服务器获取往来户（供应商、客户、部门）ID、Number、Name信息
	 * 
	 * @param f7
	 * @param asstActId
	 *            往来户（供应商、客户、部门）ID
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 * @throws BOSException
	 */
	public static void setF7Value(KDBizPromptBox f7, String asstActId)
			throws BOSException {
		if (!StringUtils.isEmpty(asstActId) && f7 != null) {
			f7.setData(AsstActUtils.getAsstActInfoByRemote(asstActId));
		}
	}

	/**
	 * 
	 * 描述：设置F7对象ID、编码、名称
	 * 
	 * @param f7
	 *            F7对象
	 * @param asstActId
	 *            往来户（供应商、客户、部门）ID
	 * @param asstActNumber
	 *            往来户（供应商、客户、部门）编码
	 * @param asstActName
	 *            往来户（供应商、客户、部门）名称
	 * @author:daij 创建时间：2006-11-7
	 *              <p>
	 */
	public static void setF7Value(KDBizPromptBox f7, String asstActId,
			String asstActNumber, String asstActName) {
		f7.setData(AsstActUtils.getAsstActInfo(asstActId, asstActNumber,
				asstActName, SysContext.getSysContext().getLocale()));
	}
}
