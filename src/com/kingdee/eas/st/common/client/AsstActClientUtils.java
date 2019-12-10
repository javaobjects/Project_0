/*
 * @(#)AsstActUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
 * 
 * @author daij date:2006-11-7
 *         <p>
 * @version EAS5.2.0
 */
public abstract class AsstActClientUtils {
	/**
	 * 
	 * �������������������ͻ�����Ӧ�̡����ţ������ͣ�F7��ʾ��ͬ������
	 * 
	 * @param uiObject
	 * @param asstActTypeString
	 *            һ�������ظ��ĳ���Ϊ8���ַ�����ʶҵ����󣨿ͻ�����Ӧ�̡����ţ�
	 * @return
	 * @author:daij ����ʱ�䣺2006-11-7
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
	 * �������������������ͻ�����Ӧ�̡����ţ������ͣ�����F7����ʾ����
	 * 
	 * @param uiObject
	 * @param f7
	 *            F7��ʾ���󣨿ͻ�����Ӧ�̡����ţ�
	 * @param asstActTypeString
	 *            һ�������ظ��ĳ���Ϊ8���ַ�����ʶҵ����󣨿ͻ�����Ӧ�̡����ţ�
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * �������������������ͻ�����Ӧ�̡����ţ������ͣ�����F7����ʾ����
	 * 
	 * @param uiObject
	 * @param f7
	 *            F7��ʾ���󣨿ͻ�����Ӧ�̡����ţ�
	 * @param asstActTypeString
	 *            һ�������ظ��ĳ���Ϊ8���ַ�����ʶҵ����󣨿ͻ�����Ӧ�̡����ţ�
	 * @author:zhiwei_wang ����ʱ�䣺2006-11-7
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
	 * ��������ȡ����Ӧ�̡��ͻ������ţ�F7����
	 * 
	 * @param f7
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * ��������Ӧ�̶�Ӧ��F7����(����ѯʹ��)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * ��������ȡ��Ӧ��F7��ѯ������Ϣ
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina ����ʱ�䣺2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setSupplierF7(CoreUIObject uiObject, KDBizPromptBox f7)
			throws Exception {
		// �жϴ���Ķ����Ƿ�Ϊ�գ�Ϊ�յĻ�����һ��F7�ؼ�
		if (f7 == null)
			f7 = new KDBizPromptBox();

		// ��F7��ʾ����Ϣ����ʽ���г�ʼ��
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
																			// ��
																			// 1
																			// ��ʾ�ͻ�
																			// 2
																			// ��ʾ��Ӧ��
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
	 * ������ ��ȡ��Ӧ��F7��ѯ������Ϣ
	 * 
	 * @param f7
	 * @return
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * �������ͻ���Ӧ��F7����(����ѯʹ��)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * ��������ȡ�ͻ�F7��ѯ������Ϣ
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina ����ʱ�䣺2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setCustomerF7(CoreUIObject uiObject, KDBizPromptBox f7)
			throws Exception {
		// �жϴ���Ķ����Ƿ�Ϊ�գ�Ϊ�յĻ�����һ��F7�ؼ�
		if (f7 == null)
			f7 = new KDBizPromptBox();

		// ��F7��ʾ����Ϣ����ʽ���г�ʼ��
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
																			// ��
																			// 1
																			// ��ʾ�ͻ�
																			// 2
																			// ��ʾ��Ӧ��
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
	 * ������ ��ȡ�ͻ�F7��ѯ������Ϣ
	 * 
	 * @param f7
	 * @return
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * ������������֯��Ӧ��F7����(����ѯʹ��)
	 * 
	 * @param uiObject
	 * @return
	 * @author:brina ����ʱ�䣺2006-11-7
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
	 * ��������ȡ������֯F7��ѯ������Ϣ
	 * 
	 * @param uiObject
	 * @param f7
	 * @author:brina ����ʱ�䣺2006-11-7
	 *               <p>
	 * @throws Exception
	 */
	public static void setAdminOrgUnitF7(CoreUIObject uiObject,
			KDBizPromptBox f7) throws Exception {
		// �жϴ���Ķ����Ƿ�Ϊ�գ�Ϊ�յĻ�����һ��F7�ؼ�
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
	 * ������ ��ȡ������֯F7��ѯ������Ϣ
	 * 
	 * @param f7
	 * @return
	 * @author:daij ����ʱ�䣺2006-11-7
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
	 * ��������������������Ӧ�̡��ͻ������ţ�ID��Զ�̷�������ȡ����������Ӧ�̡��ͻ������ţ�ID��Number��Name��Ϣ
	 * 
	 * @param f7
	 * @param asstActId
	 *            ����������Ӧ�̡��ͻ������ţ�ID
	 * @author:daij ����ʱ�䣺2006-11-7
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
	 * ����������F7����ID�����롢����
	 * 
	 * @param f7
	 *            F7����
	 * @param asstActId
	 *            ����������Ӧ�̡��ͻ������ţ�ID
	 * @param asstActNumber
	 *            ����������Ӧ�̡��ͻ������ţ�����
	 * @param asstActName
	 *            ����������Ӧ�̡��ͻ������ţ�����
	 * @author:daij ����ʱ�䣺2006-11-7
	 *              <p>
	 */
	public static void setF7Value(KDBizPromptBox f7, String asstActId,
			String asstActNumber, String asstActName) {
		f7.setData(AsstActUtils.getAsstActInfo(asstActId, asstActNumber,
				asstActName, SysContext.getSysContext().getLocale()));
	}
}
