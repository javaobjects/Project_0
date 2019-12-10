package com.kingdee.eas.st.common.client;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;

public class CommonPersonPromptBox extends PersonPromptBox {
	private KDPromptBox prmtOrgUnit = null;
	private OrgType orgType = null;
	private KDBizPromptBox prmtPerson = null;
	private IUIObject ui = null;

	public CommonPersonPromptBox(IUIObject owner, KDPromptBox prmtOrgUnit,
			KDBizPromptBox prmtPerson, OrgType orgType) {
		super(owner);
		this.prmtOrgUnit = prmtOrgUnit;
		this.prmtPerson = prmtPerson;
		this.ui = owner;
		this.orgType = orgType;
	}

	public void show() {
		beforeQuickQuery();
		super.show();
	}

	public void beforeQuickQuery() {
		// 传入的库存组织为空，返回
		if (STQMUtils.isNull(prmtOrgUnit.getData())) {
			prmtPerson.setEntityViewInfo(null);
			return;
		}

		// ID为空，返回
		Object o = ((IObjectValue) prmtOrgUnit.getData()).get("id");
		if (STQMUtils.isNull(o)) {
			return;
		}
		String orgUnitID = o.toString();

		// 生成行政组织的过滤条件(INNER类型)
		StringBuffer adminOrgUnitCondition = new StringBuffer();
		adminOrgUnitCondition
				.append("SELECT FPersonID FROM T_ORG_PositionMember a ")
				.append("INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
				.append(
						"WHERE b.FAdminOrgUnitID in (select ur.FToUnitID from T_ORG_UnitRelation"
								+ " ur left join T_ORG_TypeRelation tr on ur.ftyperelationid = tr.fid"
								+ " where ur.FFromUnitID = '" + orgUnitID
								+ "' and tr.ffromtype = " + orgType.getValue()
								+ " and tr.ftotype = " + OrgType.ADMIN_VALUE
								+ ")");

		prmtPerson
				.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		// 过滤人员
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", adminOrgUnitCondition.toString(),
						CompareType.INNER));

		viewInfo.setFilter(filter);
		prmtPerson.setEntityViewInfo(viewInfo);
		this.setEntityViewInfo(viewInfo);
	}
}
