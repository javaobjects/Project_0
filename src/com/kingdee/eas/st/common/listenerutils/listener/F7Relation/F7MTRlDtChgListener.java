package com.kingdee.eas.st.common.listenerutils.listener.F7Relation;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.st.common.listenerutils.tool.FilterUtils;

/**
 * F7数据改变监听，实现dest据sor按getFilterItemColletion返回值过滤<br />
 * 更奇怪的过滤请继承此类重载getFilterItemColletion方法<br />
 * 
 * @author hai_zhong
 * 
 */
public class F7MTRlDtChgListener extends BaseDataListener implements
		DataChangeListener {
	protected KDBizPromptBox sor = null;
	protected KDBizPromptBox dest = null;
	protected int fromType = -1;
	protected int toType = -1;
	protected int dir = 1;
	protected String fieldName = "";

	public void initParam(KDBizPromptBox sor, KDBizPromptBox dest) {
		initParam(sor, dest, -1, -1, 1);
	}

	public void initParam(KDBizPromptBox sor, KDBizPromptBox dest,
			int fromType, int toType, int dir) {
		initParam(sor, dest, fromType, toType, dir, "");
	}

	public void initParam(KDBizPromptBox sor, KDBizPromptBox dest,
			int fromType, int toType, int dir, String fieldName) {
		this.sor = sor;
		this.dest = dest;
		this.fromType = fromType;
		this.toType = toType;
		this.dir = dir;
		this.fieldName = fieldName;
	}

	protected void addFilter(FilterItemCollection fic) {
		for (int i = 0; i < fic.size(); i++) {
			FilterUtils.addFilter(dest, fic.get(i));
		}
	}

	protected void removeFilter(FilterItemCollection fic) {
		for (int i = 0; i < fic.size(); i++) {
			FilterUtils.removeFilter(dest, fic.get(i).getPropertyName());
		}
	}

	public FilterItemCollection getFilterItemCollection(Object v) {
		FilterItemCollection fic = new FilterItemCollection();
		try {
			if (null != v && v instanceof OrgUnitInfo) {
				FilterItemInfo fi = getFilterItemInfo((OrgUnitInfo) v,
						fromType, toType, dir, fieldName);// 委托关系过滤
				FilterItemInfo fi2 = null;// 当前登录用户的组织范围过滤
				UserInfo userInfo = SysContext.getSysContext()
						.getCurrentUserInfo();
				if (userInfo != null) {
					fi2 = getFilterForUserOrgPermission(userInfo,
							OrgRangeType.BIZ_ORG_TYPE, fieldName);
				}
				if (null != fi) {
					fic.add(fi);
				}
				if (null != fi2) {
					fic.add(fi2);
				}
			} else {
				fic.add(new FilterItemInfo("id", "", CompareType.EQUALS));
			}
			// fic.add(new FilterItemInfo("CU.id",SysContext.getSysContext().
			// getCurrentCtrlUnit().getId().toString(),CompareType.EQUALS));
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return fic;
	}

	public void dataChanged(DataChangeEvent e) {
		if (!getListenerManager().isEanbleListener())
			return;
		if (this.getListenerManager().isClearData()) {
			if (null != e.getOldValue()) {
				if (null != e.getNewValue()) {
					if (!((IObjectValue) e.getNewValue()).get("id").equals(
							((IObjectValue) e.getOldValue()).get("id")))
						dest.setValue(null);
				} else {
					dest.setValue(null);
				}
			} else if (null != e.getNewValue()) {
				dest.setValue(null);
			}
		}
		FilterItemCollection fic = getFilterItemCollection(e.getNewValue());

		if (null != fic && fic.size() > 0) {
			removeFilter(fic);
			addFilter(fic);
		}
	}

	public Object excute() {
		FilterItemCollection fic = getFilterItemCollection(sor.getData());
		if (null != fic && fic.size() > 0) {
			removeFilter(fic);
			addFilter(fic);
		}
		sor.addDataChangeListener(this);
		return this;
	}

	/**
	 * 取组织ou的委托组织或被委托组织过滤条件，用于F7过滤（形如:id in (...)）
	 * 
	 * @param srcOU
	 * @param srcType
	 * @param destType
	 * @param dir
	 *            (1:src委托于dest,2:src委托dest)
	 * @param filterColumn
	 *            过滤字段
	 * @return FilterItemInfo
	 * @throws ParserException
	 */
	public static FilterItemInfo getFilterItemInfo(OrgUnitInfo srcOU,
			int srcType, int destType, int dir, String filterColumn)
			throws ParserException {
		String selector = "";
		String condition = "";

		int fromType = -1;
		int toType = -1;

		if (dir == 1) {
			fromType = destType;
			toType = srcType;

			selector = "FFromUnitID";
			condition = "FToUnitID";
		} else {
			fromType = srcType;
			toType = destType;

			selector = "FToUnitID";
			condition = "FFromUnitID";
		}

		if (null == filterColumn || "".equals(filterColumn))
			filterColumn = "id";

		FilterItemInfo fi = null;
		if (null != srcOU) {
			String oql = "Where "
					+ filterColumn
					+ " in (select ur."
					+ selector
					+ " from T_ORG_UnitRelation"
					+ " ur left join T_ORG_TypeRelation tr on ur.ftyperelationid = tr.fid"
					+ " where ur." + condition + " = '"
					+ srcOU.getId().toString() + "' and tr.ffromtype = "
					+ fromType + " and tr.ftotype = " + toType + ")";
			EntityViewInfo evi = new EntityViewInfo(oql);

			if (evi.getFilter() != null
					&& evi.getFilter().getFilterItems() != null
					&& evi.getFilter().getFilterItems().get(0) != null) {
				fi = evi.getFilter().getFilterItems().get(0);
			}
		}
		return fi;
	}

	/**
	 * 获取登录用户的组织范围过滤条件
	 * 
	 * @param filterColumn
	 *            过滤字段
	 */
	public static FilterItemInfo getFilterForUserOrgPermission(
			UserInfo currUserInfo, OrgRangeType type, String filterColumn)
			throws ParserException {
		FilterItemInfo fi = null;
		String userId = null;
		if (null != filterColumn || "".equals(filterColumn))
			filterColumn = "id";
		if (null != currUserInfo) {
			userId = currUserInfo.getId().toString();
			String oql = " where " + filterColumn
					+ " in (select FOrgID from T_PM_OrgRange where FUserID='"
					+ userId + "' and FType='" + type.getValue() + "' )";
			EntityViewInfo evi = new EntityViewInfo(oql);
			if (evi.getFilter() != null
					&& evi.getFilter().getFilterItems() != null
					&& evi.getFilter().getFilterItems().get(0) != null) {
				fi = evi.getFilter().getFilterItems().get(0);
			}
		}
		return fi;
	}
}