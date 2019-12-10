package com.kingdee.eas.st.common.util;

import java.awt.Component;
import java.util.HashSet;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.cm.util.CSMF7Utils;
import com.kingdee.eas.basedata.framework.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;

/**
 * 左树右表工具调用类
 * 
 * @author Administrator
 * 
 */
public class LeftTreeRightTabUtils {

	/**
	 * 自定义分录QUERY左树右表
	 * 
	 * @param kdtEntry
	 *            分录名
	 * @param owner
	 * @param column
	 *            某列所需要展示的左树右表
	 * @param listQueryInfo
	 *            所定义列表的QUERY
	 * @param propName
	 *            连接KEY
	 * @param propertyOfBizOrg
	 *            过滤的业务组织对象
	 * @param queryInfo
	 *            所定义的QUERY
	 * @throws BOSException
	 */
	public static void customInitTreeTab(CtrlUnitInfo ctrlUnitInfo,
			KDTable kdtEntry, Object owner, String column,
			String listQueryInfo, String propName, String propertyOfBizOrg,
			String queryInfo) throws BOSException {
		KDBizPromptBox prmtMaterial = (KDBizPromptBox) kdtEntry.getColumn(
				column).getEditor().getComponent();
		GeneralKDPromptSelectorAdaptor selectorLisenterMaterial = new GeneralKDPromptSelectorAdaptor(
				prmtMaterial,
				"com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI",
				owner, MaterialGroupInfo.getBosType(), listQueryInfo, propName,
				null, queryInfo);
		FilterInfo bizFilterInfo = new FilterInfo();
		CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext()
				.getCurrentCtrlUnit();
		// 由于基础资料的权限分配有一个D类分配，可以分配到需要分配的组织，在这里这样取物料
		String ln = currentCtrlUnit.getLongNumber();
		String lnSecs[] = ln.split("!");
		int size = lnSecs.length;
		StringBuffer sb = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		// 拼接被分配的管理单元的条件，包括CUID与LONGNUMBER
		for (int i = 0; i < size - 1; i++) {
			if (i != 0)
				sb.append("!");
			sb.append(lnSecs[i]);
			if (i != 0)
				temp.append(",");
			temp.append("'" + sb.toString() + "'");
		}
		// 被分配的控制单元物料
		String sql = "select FDataBaseDID FAssignCUID from T_BD_DataBaseDAssign t1,T_ORG_CtrlUnit t2 where t1.FAssignCUID=t2.fid and (FAssignCUID='"
				+ currentCtrlUnit.getId().toString() + "' ";
		// 当前控制单元的物料
		if (size == 0) {
			sql += " or t2.flongnumber in (" + temp.toString() + ") ";
		}
		sql += ")";
		bizFilterInfo.getFilterItems().add(
				new FilterItemInfo("id", sql, CompareType.INNER));
		FilterInfo defaultFilterForControlTypeS4 = getDefaultFilterForControlTypeS4(
				currentCtrlUnit.getId().toString(), currentCtrlUnit
						.getLongNumber());
		bizFilterInfo.mergeFilter(defaultFilterForControlTypeS4, "or");
		EntityViewInfo materialView = new EntityViewInfo();
		materialView.setFilter(bizFilterInfo);
		prmtMaterial.setEntityViewInfo(materialView);
		prmtMaterial.setSelector(selectorLisenterMaterial);
		prmtMaterial.addSelectorListener(selectorLisenterMaterial);
	}

	public static void customInitListTab(CtrlUnitInfo ctrlUnitInfo,
			KDTable kdtEntry, Object owner, String column, String listQueryInfo)
			throws BOSException {
		KDBizPromptBox prmtMaterial = (KDBizPromptBox) kdtEntry.getColumn(
				column).getEditor().getComponent();
		prmtMaterial.setQueryInfo(listQueryInfo);
		FilterInfo bizFilterInfo = new FilterInfo();
		CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext()
				.getCurrentCtrlUnit();
		// 由于基础资料的权限分配有一个D类分配，可以分配到需要分配的组织，在这里这样取物料
		String ln = currentCtrlUnit.getLongNumber();
		String lnSecs[] = ln.split("!");
		int size = lnSecs.length;
		StringBuffer sb = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		// 拼接被分配的管理单元的条件，包括CUID与LONGNUMBER
		for (int i = 0; i < size - 1; i++) {
			if (i != 0)
				sb.append("!");
			sb.append(lnSecs[i]);
			if (i != 0)
				temp.append(",");
			temp.append("'" + sb.toString() + "'");
		}
		// 被分配的控制单元物料
		String sql = "select FDataBaseDID FAssignCUID from T_BD_DataBaseDAssign t1,T_ORG_CtrlUnit t2 where t1.FAssignCUID=t2.fid and (FAssignCUID='"
				+ currentCtrlUnit.getId().toString() + "' ";
		// 当前控制单元的物料
		if (size == 0) {
			sql += " or t2.flongnumber in (" + temp.toString() + ") ";
		}
		sql += ")";
		bizFilterInfo.getFilterItems().add(new FilterItemInfo("status", "1"));
		bizFilterInfo.getFilterItems().add(
				new FilterItemInfo("id", sql, CompareType.INNER));
		bizFilterInfo.getFilterItems()
				.add(
						new FilterItemInfo("CU.id", currentCtrlUnit.getId()
								.toString()));
		bizFilterInfo.setMaskString("#0 and (#1 or #2)");
		EntityViewInfo materialView = new EntityViewInfo();
		materialView.setFilter(bizFilterInfo);
		prmtMaterial.setEntityViewInfo(materialView);
	}

	/**
	 * 设置控制单元条件
	 * 
	 * @param currentCUID
	 * @param currentCULongNumber
	 * @return
	 */
	private static FilterInfo getDefaultFilterForControlTypeS4(
			String currentCUID, String currentCULongNumber) {
		FilterInfo fi = new FilterInfo();
		FilterItemCollection fic = fi.getFilterItems();
		fic.add(new FilterItemInfo("CU.id", currentCUID));
		// 以下作为当前控制单元的条件，所以不加longNumber处理
		// String ln = currentCULongNumber;
		// String lnSecs[] = ln.split("!");
		// int size = lnSecs.length;
		// HashSet lnUps = new HashSet();
		// StringBuffer sb = new StringBuffer();
		// for(int i = 0; i < size - 1; i++)
		// {
		// if(i != 0)
		// sb.append("!");
		// sb.append(lnSecs[i]);
		// lnUps.add(sb.toString());
		// }
		//
		// if(lnUps.size() != 0)
		// {
		// fic.add(new FilterItemInfo("CU.longNumber", lnUps,
		// CompareType.INCLUDE));
		// fi.setMaskString("#0 or #1");
		// }
		return fi;
	}

	/**
	 * 分录中的产品编码左树右表--标准产品内
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void standardBOSInitTreeTab(KDTable kdtEntry, String column,
			OrgType mainOrgType, KDBizPromptBox prmtMainOrgUnit,
			Component owner, Boolean isMulSelect) throws EASBizException,
			BOSException {
		IColumn col = kdtEntry.getColumn(column);
		OrgUnitInfo orgInfo = null;
		if (prmtMainOrgUnit.getValue() != null)
			orgInfo = (OrgUnitInfo) prmtMainOrgUnit.getValue();
		KDBizPromptBox kDBizPromptBoxMaterial = new KDBizPromptBox();
		// registerDealMultiMateiralInfo(kDBizPromptBoxMaterial, col.getKey());
		F7ContextManager f7Manager = new F7ContextManager(owner, mainOrgType);
		f7Manager.registerBizMaterialF7(kDBizPromptBoxMaterial, col, null,
				orgInfo, OrgType.Company, isMulSelect, true);
	}

	/**
	 * 单头设置左树右表
	 * 
	 * @param kdtEntry
	 *            分录名
	 * @param prmtMainOrgUnit
	 *            主业务组织
	 * @param column
	 */
	public static void headerInitTreeTab(KDBizPromptBox prmtF7Display,
			KDTable kdtEntry, KDBizPromptBox prmtMainOrgUnit, String column) {
		// 单头物料F7设置
		CSMF7Utils.setBizMaterialTreeF7(prmtF7Display, null, OrgType.Storage,
				(OrgUnitInfo) prmtMainOrgUnit.getData());

	}

	/**
	 * 普通左树右表分录设置
	 * 
	 * @param kdtEntry
	 * @param column
	 * @param prmtMainOrgUnit
	 */
	public static void EntryInitTreeTab(KDTable kdtEntry, String column,
			KDBizPromptBox prmtMainOrgUnit) {
		// 分录物料F7设置
		KDBizPromptBox prmtMaterial = (KDBizPromptBox) kdtEntry.getColumn(
				column).getEditor().getComponent();
		CSMF7Utils.setBizMaterialTreeF7(prmtMaterial, null, OrgType.Storage,
				(OrgUnitInfo) prmtMainOrgUnit.getData());
		ObjectValueRender kdtEntries_productNumber_OVR = new ObjectValueRender();
		kdtEntries_productNumber_OVR.setFormat(new BizDataFormat("$name$"));
		kdtEntry.getColumn(column).setRenderer(kdtEntries_productNumber_OVR);
	}
}
