package com.kingdee.eas.st.common.util.param;

import com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation;
import java.util.HashMap;
import com.kingdee.bos.ctrl.data.modal.DefObj;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.eas.basedata.org.client.PositionPromptBox2;
import com.kingdee.eas.rpts.ctrlreport.ISqlF7Selector;
import com.kingdee.eas.rpts.ctrlreport.model.CtrlReportFinal;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import java.util.Iterator;

import com.kingdee.bos.ctrl.data.modal.DefObj;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.rpts.ctrlreport.model.CtrlReportUtil;
import com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation;
import com.kingdee.jdbc.rowset.IRowSet;

public class ParamRelation extends AbstractParamRelation {
	public ParamRelation() {
		super();
	}

	/**
	 * �������˷����������ʾ����ֵǰ���ϵ��ã������ֹ���Ԥ�������� <br>
	 * ���磬����ڹ��˷������в���Ϊ���ŵ�Ĭ��ֵ����������������������Ա�Ĺ�������,��������: <br>
	 * 1��ȡ����F7��ֵ����ǰ�仯��F7 <br>
	 * 2������Ŀ��F7 <br>
	 * 3��ͨ��setXXXfilter����setFilterString�ӿڰ�����F7��ֵ���õ�Ŀ��F7
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#loadSolutionAfter()
	 */
	public void loadSolutionAfter() throws Exception {
		// ��û���F7�ؼ�
		Object storageOrgUnitF7 = objectComponents.get("storageOrgUnitID");
		if (storageOrgUnitF7 == null) {
			return; // �������F7�ؼ�Ϊ�գ�ֱ�ӷ���
		}

		KDBizPromptBox storageOrgUnitF7Prompt = (KDBizPromptBox) storageOrgUnitF7;
		// ���ù�����
		FilterInfo filterInfo = getDefaultCUFilter(false);
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		entityViewInfo.setFilter(filterInfo);
		storageOrgUnitF7Prompt.setEntityViewInfo(entityViewInfo);
		storageOrgUnitF7Prompt.getQueryAgent()
				.setEntityViewInfo(entityViewInfo);
	}

	/**
	 * ��ǰѡ��Ŀؼ�ֵ�仯�����������ؼ���ֵ���ߴ����������(��ʱ��֧���ı����ֵ�仯�¼�)
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#dataChange(java.lang.String)
	 */
	public void dataChange(String paramName) throws Exception {
		if (paramName.equals("depName")) // ������ƾ����ڲ���������е�����
		{
		}
	}

	/**
	 * ���ȷ����ť��׼��ִ�й���֮ǰ���������ù���������ֵ<br>
	 * �������ĳ�����ڲ����룬Ĭ��Ϊ�������һ�� <br>
	 * 
	 * @see com.kingdee.eas.rpts.ctrlsqldesign.param.AbstractParamRelation#confirmFilterBefore()
	 */
	public boolean confirmFilterBefore() throws Exception {
		// �ж�ĳ�����������ϣ�����false��ȡ��ִ��
		String dateBegin = this.getComponentValue("dateBegin");
		String dateEnd = this.getComponentValue("dateEnd");

		if (dateBegin != null && dateEnd != null
				&& dateBegin.compareTo(dateEnd) > 0) {
			MsgBox.showInfo("��ʼ���ڲ��ܴ��ڽ�������");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * �����������Ƶ���֯����CU�����¼�CU�£���ǰ�û���Ȩ�޵�������֯����������֯ʵ����ϼ�������֯û�����赱ǰ�û�����ô��ִ
	 * �л��������ܳ�����ɢ������㣬�˻�������
	 * 
	 * @param isIgnore
	 *            �Ƿ����CU����
	 * @return
	 * @author:heng_wang ����ʱ�䣺2009-7-14
	 *                   <p>
	 */
	protected FilterInfo getDefaultCUFilter(boolean isIgnore) {
		FilterInfo filter = new FilterInfo();

		if (isIgnore) {
			return filter;
		}

		// ��ǰCU�����¼�CU
		StringBuffer oql = new StringBuffer();
		oql.setLength(0);
		oql
				.append(IFWEntityStruct.objectBase_CU)
				.append("In (")
				.append(
						"Select a.FId From T_org_baseUnit a, T_org_baseUnit b where (a.fnumber = b.fnumber ")
				.append(
						"Or SUBSTRING(a.FLongNumber,0,LENGTH(b.FLongNumber) + 1) = b.FLongNumber || '!') And b.FId ='")
				.append(getUICtrolUnit().getId().toString()).append("' ")
				.append("And a.FIsCU = 1").append(")");
		filter.getFilterItems().add(new FilterItemInfo(oql.toString()));

		// ��ǰ�û���Ȩ�޵�������֯������ʵ�������
		oql.setLength(0);
		oql
				.append(IFWEntityStruct.coreBase_ID)
				.append("In (")
				.append("SELECT DISTINCT BaseUnit.FID")
				.append("FROM T_PM_OrgRange OrgRange ")
				.append(
						"INNER JOIN T_ORG_BaseUnit BaseUnit ON OrgRange.FOrgID = BaseUnit.FID  ")
				.append("WHERE OrgRange.FUserID = '").append(
						SysContext.getSysContext().getCurrentUserInfo().getId()
								.toString()).append("' ").append(
						"AND BaseUnit.FIsSaleOrgUnit = 1 ").append(
						"AND OrgRange.FType = ").append(
						OrgRangeType.BIZ_ORG_TYPE_VALUE).append(
						"AND BaseUnit.FIsAssistantOrg = 0) ");

		filter.getFilterItems().add(new FilterItemInfo(oql.toString()));
		return filter;
	}

	public CtrlUnitInfo getUICtrolUnit() {
		return SysContext.getSysContext().getCurrentCtrlUnit();
	}
}
