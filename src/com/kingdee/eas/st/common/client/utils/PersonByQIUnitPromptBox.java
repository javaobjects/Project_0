package com.kingdee.eas.st.common.client.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.QualityOrgUnitInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.st.common.STException;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.SysUtil;

class PersonByQIUnitPromptBox extends PersonPromptBox {
	private KDPromptBox prmtQiOrgUnit = null;
	private KDBizPromptBox prmtPerson = null;
	private IUIObject ui = null;

	public PersonByQIUnitPromptBox(IUIObject owner, KDPromptBox prmtQiOrgUnit,
			KDBizPromptBox prmtPerson) {
		super(owner);
		this.prmtQiOrgUnit = prmtQiOrgUnit;
		this.prmtPerson = prmtPerson;
		this.ui = owner;
	}

	/**
	 * 带上下文(ctx)构造函数 add by hai_zhong
	 * 
	 * @param owner
	 * @param prmtQiOrgUnit
	 * @param prmtPerson
	 * @param hash
	 */
	public PersonByQIUnitPromptBox(IUIObject owner, KDPromptBox prmtQiOrgUnit,
			KDBizPromptBox prmtPerson, Map ctx) {
		super(owner, (HashMap) ctx);
		this.prmtQiOrgUnit = prmtQiOrgUnit;
		this.prmtPerson = prmtPerson;
		this.ui = owner;
	}

	public void show() {
		beforeQuickQuery();
		super.show();

	}

	public void beforeQuickQuery() {
		QualityOrgUnitInfo qualityOrgUnitInfo = null;
		Object o = prmtQiOrgUnit.getValue();
		if (o instanceof QualityOrgUnitInfo) {
			qualityOrgUnitInfo = (QualityOrgUnitInfo) o;
		}
		o = this.prmtPerson.getValue();

		if (STUtils.isNull(qualityOrgUnitInfo)) {
			Object label = this.prmtQiOrgUnit.getParent();
			KDLabelContainer kdlc = null;
			if (label instanceof KDLabelContainer) {
				kdlc = (KDLabelContainer) label;
				ui.handUIException(new STException(STException.ADMINORGISNULL,
						new String[] { kdlc.getBoundLabelText() }));
			}
			this.prmtPerson.setValue(null);
			this.prmtQiOrgUnit.requestFocus();
			SysUtil.abort();
		}

		FilterInfo info = null;
		try {
			info = MtOrgUtils.getOrgUnitRelationFilter(null, qualityOrgUnitInfo
					.getId().toString(), true, OrgType.Quality, OrgType.Admin,
					null, "id");

			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(info);
			// SelectorItemCollection sic = new SelectorItemCollection();
			// sic.add(new SelectorItemInfo("id"));
			// sic.add(new SelectorItemInfo("longNumber"));

			// AdminOrgUnitCollection coll =
			// AdminOrgUnitFactory.getRemoteInstance
			// ().getAdminOrgUnitCollection(view);
			CoreBaseCollection collection = FullOrgUnitFactory
					.getRemoteInstance().getCollection(view);
			Iterator it = collection.iterator();
			String orgIDs = "'ids'";

			while (it.hasNext()) {
				FullOrgUnitInfo fullOrgUnitInfo = (FullOrgUnitInfo) it.next();

				orgIDs = orgIDs + ",'" + fullOrgUnitInfo.getId().toString()
						+ "'";
			}

			if (STUtils.isNotNull(orgIDs)) {
				// 生成行政组织的过滤条件(INNER类型)
				StringBuffer adminOrgUnitCondition = new StringBuffer();
				adminOrgUnitCondition
						.append("SELECT FPersonID FROM T_ORG_PositionMember a ")
						.append(
								"INNER JOIN T_ORG_Position b ON a.FPositionID = b.FID ")
						.append(
								"INNER JOIN T_ORG_BaseUnit f ON f.FId=b.FAdminOrgUnitID ")
						.append("WHERE f.Fid in (").append(orgIDs).append(")");
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
