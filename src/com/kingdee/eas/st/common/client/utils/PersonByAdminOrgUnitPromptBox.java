package com.kingdee.eas.st.common.client.utils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.SysUtil;

class PersonByAdminOrgUnitPromptBox extends PersonPromptBox {
	private KDPromptBox prmtAdminOrgUnit = null;
	private KDBizPromptBox prmtPerson = null;
	private IUIObject ui = null;

	public PersonByAdminOrgUnitPromptBox(IUIObject owner,
			KDPromptBox prmtAdminOrgUnit, KDBizPromptBox prmtPerson) {
		super(owner);
		this.prmtAdminOrgUnit = prmtAdminOrgUnit;
		this.prmtPerson = prmtPerson;
		this.ui = owner;
	}

	public void show() {
		beforeQuickQuery();
		super.show();

	}

	public void beforeQuickQuery() {
		AdminOrgUnitInfo adminOrgUnitInfo = null;
		Object o = prmtAdminOrgUnit.getValue();
		if (o instanceof AdminOrgUnitInfo) {
			adminOrgUnitInfo = (AdminOrgUnitInfo) o;
		}
		o = this.prmtPerson.getValue();

		if (STUtils.isNull(adminOrgUnitInfo)) {
			Object label = this.prmtAdminOrgUnit.getParent();
			KDLabelContainer kdlc = null;
			if (label instanceof KDLabelContainer) {
				kdlc = (KDLabelContainer) label;
				ui.handUIException(new STException(STException.ADMINORGISNULL,
						new String[] { kdlc.getBoundLabelText() }));
			}
			this.prmtPerson.setValue(null);
			this.prmtAdminOrgUnit.requestFocus();
			SysUtil.abort();
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("longNumber"));

		try {

			FullOrgUnitInfo fullOrgUnitInfo = FullOrgUnitFactory
					.getRemoteInstance().getFullOrgUnitInfo(
							new ObjectStringPK(adminOrgUnitInfo.getId()
									.toString()), sic);
			if (STUtils.isNotNull(fullOrgUnitInfo)) {
				// 生成行政组织的过滤条件(INNER类型)
				StringBuffer adminOrgUnitCondition = new StringBuffer();
				adminOrgUnitCondition
						.append("SELECT FPersonID FROM T_ORG_PositionMember a ")
						.append(
								"INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
						.append(
								"INNER JOIN T_ORG_BaseUnit f ON f.FId=b.FAdminOrgUnitID ")
						.append("WHERE f.FLongNumber LIKE '%").append(
								fullOrgUnitInfo.getLongNumber()).append("%'");
				// 过滤人员
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", adminOrgUnitCondition
								.toString(), CompareType.INNER));
				viewInfo.setFilter(filter);
				this.prmtPerson.setEntityViewInfo(viewInfo);
				this.setEntityViewInfo(viewInfo);
			}

		} catch (EASBizException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}

}
