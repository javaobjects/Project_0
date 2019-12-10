package com.kingdee.eas.st.common.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * 设定一些特定过滤条件的过滤管理器，比如说"以审批人身份查看",以后可能还有类似的扩展。
 * 可以将多个扩展Element作为一个List,setSomeFilterList(List);
 * 然后这个Manager在filterUi中被使用.统一设置多个界面公用的过滤器.
 * 请参考"以审批人身份查看"过滤器的使用:AuditedByCurrentUserFilterElement
 * 
 * @author xiaofeng_liu
 * 
 */
/*
 * TODO QueryFilterManager的原本实现里的element是使用一个map来存储element的，
 * 故没有重复，而且这样在切换查询方案的时候可以恢复各查询控件的状态
 * 但这里外加的someFilterMap添加的element时的key难保不合super.element里的key不冲突。
 * 最好能加上解除冲突的机制。否则只能认为避免了。比如说”以审批人身份查看“使用的key是"id",那其它element就不可再用"id"了.
 */
public class CustomQueryFilterManager extends QueryFilterManager {
	/**
	 * 过滤元素列表,可以增减的.
	 */
	private Map CustomeFilterMap = new HashMap();

	public void addCustomeFilterElement(FilterElement fe) {
		if (fe == null) {
			return;
		}
		this.CustomeFilterMap.put(fe.getId(), fe);
	}

	public void removeCustomeFilterElement(String fid) {
		this.CustomeFilterMap.remove(fid);
	}

	public CustomQueryFilterManager() {
		super();
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filterInfo = super.getFilterInfo();
		if (filterInfo == null) {
			filterInfo = new FilterInfo();
		}
		Iterator ite = this.CustomeFilterMap.keySet().iterator();
		while (ite.hasNext()) {
			Object o = ite.next();
			o = this.CustomeFilterMap.get(o);
			if (o instanceof FilterElement) {
				FilterElement fe = (FilterElement) o;
				FilterInfo fi = fe.getFilterInfo();
				if (fi != null) {
					try {
						filterInfo.mergeFilter(fi, "AND");
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filterInfo;
	}

	public void setData(EntityViewInfo entityViewInfo) {
		super.setData(entityViewInfo);
		Iterator ite = this.CustomeFilterMap.keySet().iterator();
		while (ite.hasNext()) {
			Object o = ite.next();
			o = this.CustomeFilterMap.get(o);
			if (o instanceof FilterElement) {
				FilterElement fe = (FilterElement) o;
				fe.setData(entityViewInfo);
			}
		}
	}

	public boolean verify() {
		Iterator ite = this.CustomeFilterMap.keySet().iterator();
		while (ite.hasNext()) {
			Object o = ite.next();
			o = this.CustomeFilterMap.get(o);
			if (o instanceof FilterElement) {
				FilterElement fe = (FilterElement) o;
				if (!fe.verify()) {
					return false;
				}
			}
		}
		return super.verify();
	}

	/*
	 * 尚未发现用途，暂不作更改 @author xiaofeng_liu 2007.11.20 （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#getRptParams()
	 */
	public RptParams getRptParams() {
		return super.getRptParams();
	}

	/*
	 * 尚未发现用途，暂不作更改 @author xiaofeng_liu 2007.11.20 （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#setRptParams()
	 */
	public void setRptParams(RptParams rp) {
		super.setRptParams(rp);
	}

	/*
	 * 是否清楚定制过滤条件?待确定 TODO （非 Javadoc）
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#clearAll()
	 */
	public void clearAll() {
		super.clearAll();

	}
}
