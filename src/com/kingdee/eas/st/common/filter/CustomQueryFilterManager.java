package com.kingdee.eas.st.common.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * �趨һЩ�ض����������Ĺ��˹�����������˵"����������ݲ鿴",�Ժ���ܻ������Ƶ���չ��
 * ���Խ������չElement��Ϊһ��List,setSomeFilterList(List);
 * Ȼ�����Manager��filterUi�б�ʹ��.ͳһ���ö�����湫�õĹ�����.
 * ��ο�"����������ݲ鿴"��������ʹ��:AuditedByCurrentUserFilterElement
 * 
 * @author xiaofeng_liu
 * 
 */
/*
 * TODO QueryFilterManager��ԭ��ʵ�����element��ʹ��һ��map���洢element�ģ�
 * ��û���ظ��������������л���ѯ������ʱ����Իָ�����ѯ�ؼ���״̬
 * ��������ӵ�someFilterMap��ӵ�elementʱ��key�ѱ�����super.element���key����ͻ��
 * ����ܼ��Ͻ����ͻ�Ļ��ơ�����ֻ����Ϊ�����ˡ�����˵������������ݲ鿴��ʹ�õ�key��"id",������element�Ͳ�������"id"��.
 */
public class CustomQueryFilterManager extends QueryFilterManager {
	/**
	 * ����Ԫ���б�,����������.
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
	 * ��δ������;���ݲ������� @author xiaofeng_liu 2007.11.20 ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#getRptParams()
	 */
	public RptParams getRptParams() {
		return super.getRptParams();
	}

	/*
	 * ��δ������;���ݲ������� @author xiaofeng_liu 2007.11.20 ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#setRptParams()
	 */
	public void setRptParams(RptParams rp) {
		super.setRptParams(rp);
	}

	/*
	 * �Ƿ�������ƹ�������?��ȷ�� TODO ���� Javadoc��
	 * 
	 * @see com.kingdee.eas.st.common.filter.QueryFilterManager#clearAll()
	 */
	public void clearAll() {
		super.clearAll();

	}
}
