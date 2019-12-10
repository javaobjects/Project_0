/*
 * @(#)PersonUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.st.common.client.CommonPersonPromptBox;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.util.StringUtils;

public class PersonClientUtils {

	public static void setCommonPersonF7(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDBizPromptBox prmtPerson) {
		setCommonPersonF7(ui, prmtOrgUnit, prmtPerson, null);
	}

	public static void setCommonPersonF7(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable kdt,
			final String field) {
		setCommonPersonF7(ui, prmtOrgUnit, kdt, field, null);
	}

	public static void setCommonPersonF7(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDBizPromptBox prmtPerson,
			OrgType orgType) {

		if (null != orgType)
			setCommonPersonF7Prompt(ui, prmtOrgUnit, prmtPerson, orgType);
		else
			setPersonF7ByAdminOrgUnit(ui, prmtOrgUnit, prmtPerson);
	}

	public static void setCommonPersonF7(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable kdt,
			final String field, OrgType orgType) {
		if (null != orgType)
			setCommonPersonF7Prompt(ui, prmtOrgUnit, kdt, field, orgType);
		else
			setPersonF7PromptBoxByAdminOrgUnit(ui, prmtOrgUnit, kdt, field);
	}

	public static void setPersonF7(IUIObject ui,
			KDBizPromptBox prmtQualityOrgUnit, KDBizPromptBox prmtPerson) {

		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		PersonByQIUnitPromptBox select = new PersonByQIUnitPromptBox(ui,
				prmtQualityOrgUnit, prmtPerson);
		select.showAllChildren();
		select.setExpandAdmin(null);
		prmtPerson.setSelector(select);

	}

	/**
	 * add by hai_zhong
	 * 
	 * @param ui
	 * @param prmtOrgUnit
	 * @param prmtPerson
	 */
	public static void setCommonPersonF7Prompt(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDBizPromptBox prmtPerson,
			final OrgType orgType) {
		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		CommonPersonPromptBox select = new CommonPersonPromptBox(ui,
				prmtOrgUnit, prmtPerson, orgType);
		select.showAllChildren();
		select.setExpandAdmin(null);
		prmtPerson.setSelector(select);

	}

	/**
	 * add by hai_zhong
	 * 
	 * @param ui
	 * @param prmtOrgUnit
	 * @param kdt
	 * @param field
	 * @param orgType
	 */
	public static void setCommonPersonF7Prompt(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable kdt,
			final String field, OrgType orgType) {
		final KDBizPromptBox prmtPerson = new KDBizPromptBox();

		setCommonPersonF7Prompt(ui, prmtOrgUnit, prmtPerson, orgType);

		KDTDefaultCellEditor prmtPerson_CellEditor = new KDTDefaultCellEditor(
				prmtPerson);
		kdt.getColumn(field).setEditor(prmtPerson_CellEditor);
		ObjectValueRender prmtPerson_OVR = new ObjectValueRender();
		prmtPerson_OVR.setFormat(new BizDataFormat("$name$"));
		kdt.getColumn(field).setRenderer(prmtPerson_OVR);
	}

	// public static void setPersonF7TreeByAdminOrgUnit(final IUIObject ui,final
	// KDPromptBox prmtAdminOrgUnit,final KDBizPromptBox prmtPerson){
	public static void setPersonF7ByAdminOrgUnit(final IUIObject ui,
			final KDPromptBox prmtAdminOrgUnit, final KDBizPromptBox prmtPerson) {

		setPersonF7PromptBoxByAdminOrgUnit(ui, prmtAdminOrgUnit, prmtPerson);

		prmtAdminOrgUnit
				.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
					public void dataChanged(
							com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
						try {

							// ������Ա��ѡ������
							setPersonF7PromptBoxByAdminOrgUnit(ui,
									prmtAdminOrgUnit, prmtPerson);

							// �ж�prmtPerson�ڵ���Ա�Ƿ��������в��ţ����������վɵ�ֵ.
							AdminOrgUnitInfo adminOrgUnit = null;
							Object o = prmtAdminOrgUnit.getValue();
							if (o instanceof AdminOrgUnitInfo) {
								adminOrgUnit = (AdminOrgUnitInfo) o;
							} else {
								prmtPerson.setValue(null);
							}
							o = prmtPerson.getValue();
							if ((o instanceof PersonInfo)
									&& STUtils.isNotNull(adminOrgUnit)) {
								PersonInfo personInfo = (PersonInfo) o;
								if (PersonClientUtils.isPersonInOrg(personInfo,
										adminOrgUnit)) {
								} else {
									prmtPerson.setValue(null);
								}
							}
						} catch (Exception exc) {
						} finally {
						}
					}
				});

		prmtPerson.addCommitListener(new CommitListener() {
			public void willCommit(CommitEvent e) {
				Object f7 = prmtPerson.getSelector();
				if (f7 instanceof PersonByAdminOrgUnitPromptBox) {
					PersonByAdminOrgUnitPromptBox p = (PersonByAdminOrgUnitPromptBox) f7;
					p.beforeQuickQuery();
				}
			}
		});
	}

	public static void setPersonF7PromptBoxByAdminOrgUnit(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable kdt,
			final String field) {
		final KDBizPromptBox prmtPerson = new KDBizPromptBox();

		setPersonF7ByAdminOrgUnit(ui, prmtOrgUnit, prmtPerson);

		KDTDefaultCellEditor prmtPerson_CellEditor = new KDTDefaultCellEditor(
				prmtPerson);
		kdt.getColumn(field).setEditor(prmtPerson_CellEditor);
		ObjectValueRender prmtPerson_OVR = new ObjectValueRender();
		prmtPerson_OVR.setFormat(new BizDataFormat("$name$"));
		kdt.getColumn(field).setRenderer(prmtPerson_OVR);
	}

	/**
	 * add by hai_zhong
	 * 
	 * @param ui
	 * @param prmtQualityOrgUnit
	 * @param prmtPerson
	 */
	public static void setPersonF7ByQualityOrgUnit(IUIObject ui,
			KDBizPromptBox prmtQualityOrgUnit, KDBizPromptBox prmtPerson) {

		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");

		// add by hai_zhong[start]
		Map ctx = new HashMap();
		ctx.put("DEFAULT_SHOW_ALL", new Boolean(true));
		// [end]

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		PersonByQIUnitPromptBox select = new PersonByQIUnitPromptBox(ui,
				prmtQualityOrgUnit, prmtPerson, ctx);
		select.showAllChildren();
		select.setExpandAdmin(null);
		prmtPerson.setSelector(select);

	}

	/**
	 * 
	 * ������������Ա��ѡ��������Ҫ���벿��,���ݲ��Ź�����Ա��ѡ������
	 * 
	 * @param prmtAdminOrgUnit
	 * @param prmtPerson
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public static void setPersonF7ByAdminOrgUnit_Xu(
			AdminOrgUnitInfo adminOrgUnitInfo, final KDBizPromptBox prmtPerson) {

		// �����������֯Ϊ�գ�����
		if (STQMUtils.isNull(adminOrgUnitInfo)) {
			return;
		}

		// IDΪ�գ�����
		Object o = adminOrgUnitInfo.getId();
		if (STQMUtils.isNull(o)) {
			return;
		}
		String adminOrgUnitID = o.toString();

		// ����������֯�Ĺ�������(INNER����)
		StringBuffer adminOrgUnitCondition = new StringBuffer();
		adminOrgUnitCondition.append(
				"SELECT FPersonID FROM T_ORG_PositionMember a ").append(
				"INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
				.append("WHERE b.FAdminOrgUnitID ='").append(adminOrgUnitID)
				.append("'");

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		// ������Ա
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", adminOrgUnitCondition.toString(),
						CompareType.INNER));

		viewInfo.setFilter(filter);
		prmtPerson.setEntityViewInfo(viewInfo);

		prmtPerson.setEditFormat("$number$");
		// prmtPerson.setDisplayFormat("$number$");
		prmtPerson.setDisplayFormat("$name$");
		prmtPerson.setCommitFormat("$number$");

	}

	// public static void setPersonF7TreeByAdminOrgUnit(IUIObject
	// ui,AdminOrgUnitInfo adminOrgUnitInfo,KDBizPromptBox prmtPerson){
	public static void setPersonF7PromptBoxByAdminOrgUnit(IUIObject ui,
			KDPromptBox prmtAdminOrgUnit, KDBizPromptBox prmtPerson) {
		AdminOrgUnitInfo adminOrgUnitInfo = null;
		Object o = prmtAdminOrgUnit.getValue();
		if (o instanceof AdminOrgUnitInfo) {
			adminOrgUnitInfo = (AdminOrgUnitInfo) o;
		}
		if (STQMUtils.isNull(ui) || STQMUtils.isNull(prmtAdminOrgUnit)
				|| STQMUtils.isNull(prmtPerson)) {
			return;
		}

		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");
		if (StringUtils.isEmpty(prmtPerson.getQueryInfo())) {
			prmtPerson
					.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		}
		// map.put(PersonF7UI.ADMIN_ID, adminOrgUnitInfo.getId().toString());
		// map.put(PersonF7UI.EXPAND_ORG_MODEL, "2");
		PersonByAdminOrgUnitPromptBox select = new PersonByAdminOrgUnitPromptBox(
				ui, prmtAdminOrgUnit, prmtPerson);
		select.showAllChildren();
		select.setExpandAdmin(adminOrgUnitInfo == null ? null
				: adminOrgUnitInfo.getId().toString());
		prmtPerson.setSelector(select);
		// prmtPerson.setHasCUDefaultFilter(false);//Ĭ�ϲ�cu����
		//		
		// StringBuffer adminOrgUnitCondition = new StringBuffer();
		// adminOrgUnitCondition.append(
		// "SELECT FPersonID FROM T_ORG_PositionMember a ")
		// .append("INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
		//.append("WHERE b.FAdminOrgUnitID ='").append(adminOrgUnitInfo.getId().
		// toString()).append("'");
		// //������Ա
		// EntityViewInfo viewInfo = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new
		// FilterItemInfo("id",adminOrgUnitCondition.toString(),
		// CompareType.INNER));
		//
		// viewInfo.setFilter(filter);
		// prmtPerson.setEntityViewInfo(viewInfo);

	}

	/**
	 * 
	 * ������������Ա��ѡ��������Ҫ���벿�Ŷ�Ӧ��KDBizPromptBox,�������б仯ʱ���Զ�������Ա��ѡ������
	 * 
	 * @param prmtAdminOrgUnit
	 * @param prmtPerson
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public static void setPersonF7ByAdminOrgUnit_Xu(
			final KDPromptBox prmtAdminOrgUnit, final KDBizPromptBox prmtPerson) {

		// ���Ÿı�
		prmtAdminOrgUnit
				.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
					public void dataChanged(
							com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
						try {

							// ��ȡprmtAdminOrgUnit��ֵ
							AdminOrgUnitInfo adminOrgUnit = null;
							Object o = prmtAdminOrgUnit.getValue();
							if (o instanceof AdminOrgUnitInfo) {
								adminOrgUnit = (AdminOrgUnitInfo) o;
							}

							// ������Ա��ѡ������
							setPersonF7ByAdminOrgUnit_Xu(adminOrgUnit,
									prmtPerson);

						} catch (Exception exc) {

						} finally {
						}
					}
				});

	}

	/**
	 * 
	 * ����������ȱʡ��Ա(ȱʡ��ԱΪ��½�û�)
	 * 
	 * @param prmtPerson
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public static void setDefaultPerson(final KDBizPromptBox prmtPerson) {

		UserInfo userInfo = (com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
				.getSysContext().getCurrentUserInfo());
		setDefaultPerson(prmtPerson, userInfo);

	}

	/**
	 * 
	 * ����������ȱʡ��Ա
	 * 
	 * @param prmtPerson
	 * @param userInfo
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public static void setDefaultPerson(final KDBizPromptBox prmtPerson,
			final UserInfo userInfo) {

		prmtPerson.setValue(userInfo);

	}

	/**
	 * 
	 * ��������Ա�Ƿ���������֯
	 * 
	 * @param prmtPerson
	 * @param userInfo
	 * @author:colin_xu ����ʱ�䣺2007-5-31
	 *                  <p>
	 */
	public static boolean isPersonInOrg(PersonInfo person, OrgUnitInfo orgUnit) {
		if (person == null || orgUnit == null) {
			return false;
		}

		boolean isPersonInOrg = false;

		try {

			// ����HrOrgUnit
			IFullOrgUnit iFullOrgUnit = FullOrgUnitFactory.getRemoteInstance();
			// ����������֯�Ĺ�������(INNER����)
			StringBuffer adminOrgUnitCondition = new StringBuffer();
			adminOrgUnitCondition
					.append(
							"SELECT b.FAdminOrgUnitID FROM T_ORG_PositionMember a ")
					.append(
							"INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
					.append("WHERE FPersonID ='").append(
							person.getId().toString()).append("'");

			// OrgClientUtils.getAdminTree()
			// ������Ա
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", adminOrgUnitCondition.toString(),
							CompareType.INNER));
			filter.setMaskString("#0");
			viewInfo.setFilter(filter);
			FullOrgUnitCollection fullHrOrgUnitCollection = iFullOrgUnit
					.getFullOrgUnitCollection(viewInfo);

			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add(new SelectorItemInfo("id"));
			selectors.add(new SelectorItemInfo("longNumber"));

			FullOrgUnitInfo fullOrgUnitInfo = iFullOrgUnit.getFullOrgUnitInfo(
					new ObjectStringPK(orgUnit.getId().toString()), selectors);
			// HR��֯�Ƿ��ڵ�ǰ��֯���ϼ�(����ƽ��)
			if (fullHrOrgUnitCollection.get(0).getLongNumber().indexOf(
					fullOrgUnitInfo.getLongNumber()) >= 0) {
				isPersonInOrg = true;
			}
		} catch (Exception e) {
			// TODO
		}

		return isPersonInOrg;
	}

	/**
	 * add by hai_zhong
	 * 
	 * @param ui
	 * @param prmtOrgUnit
	 * @param kdt
	 * @param field
	 * @param orgType
	 */
	public static void setCommonPersonF7PromptMutil(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDTable kdt,
			final String field, OrgType orgType) {
		final KDBizPromptBox prmtPerson = new KDBizPromptBox();
		prmtPerson.setEnabledMultiSelection(true);

		setCommonPersonF7PromptMutil(ui, prmtOrgUnit, prmtPerson, orgType);

		KDTDefaultCellEditor prmtPerson_CellEditor = new KDTDefaultCellEditor(
				prmtPerson);
		kdt.getColumn(field).setEditor(prmtPerson_CellEditor);
		ObjectValueRender prmtPerson_OVR = new ObjectValueRender();
		prmtPerson_OVR.setFormat(new BizDataFormat("$number$"));
		kdt.getColumn(field).setRenderer(prmtPerson_OVR);
	}

	/**
	 * add by hai_zhong
	 * 
	 * @param ui
	 * @param prmtOrgUnit
	 * @param prmtPerson
	 */
	public static void setCommonPersonF7PromptMutil(final IUIObject ui,
			final KDBizPromptBox prmtOrgUnit, final KDBizPromptBox prmtPerson,
			final OrgType orgType) {
		prmtPerson.setEditable(true);
		prmtPerson.setEditFormat("$number$");
		prmtPerson.setCommitFormat("$number$;$idNum$");
		prmtPerson.setEnabledMultiSelection(true);

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		CommonPersonPromptBox select = new CommonPersonPromptBox(ui,
				prmtOrgUnit, prmtPerson, orgType);
		select.setIsSingleSelect(false);
		select.showAllChildren();
		select.setExpandAdmin(null);
		prmtPerson.setSelector(select);

	}
}
