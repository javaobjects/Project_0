package com.kingdee.eas.pa.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.management.LanguageCollection;
import com.kingdee.bos.metadata.management.LanguageInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupInfo;
import com.kingdee.eas.basedata.framework.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.basedata.master.account.AccountTableInfo;
import com.kingdee.eas.basedata.master.account.AccountType;
import com.kingdee.eas.basedata.master.account.AccountTypeInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.account.client.F7AccountViewUI;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.AuxAccountUtils;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupFactory;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeGroupInfo;
import com.kingdee.eas.basedata.master.cssp.client.F7CustomerSimpleSelector;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.client.F7BaseSelector;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.f7.NewAdminF7;
import com.kingdee.eas.basedata.org.client.f7.NewCompanyF7;
import com.kingdee.eas.basedata.org.client.f7.NewSaleF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.st.common.client.CommonPersonPromptBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class F7LeftTreeRightTable {
	private StackTraceElement ste = new Throwable().getStackTrace()[1];//可用于获取代码行号

	/**
	 * 2015-12-25 11:49:02 zl_lix
	 * 设置F7控件左树右表显示(不包含组织)，只针对标准的基础资料
	 * @param own	默认this
	 * @param bizPromptBox	F7控件
	 * @param F7Name	F7控件所选的基础资料的名称
	 * @param isEnableMulti	是否启用多选
	 */
	public static void setF7LeftTreeRightTable(Object own,KDBizPromptBox bizPromptBox,String F7Name,boolean isEnableMulti){
		String queryInfo = "";
		GeneralKDPromptSelectorAdaptor selectorLisenterCustomer = null;
		if(F7Name.equalsIgnoreCase("客户")){//客户  
			queryInfo = "com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery";
			selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(
					bizPromptBox,"com.kingdee.eas.basedata.master.cssp.client.F7CustomerTreeDetailListUI",
					own, null, queryInfo, "browseGroup.id", null, queryInfo);

		}else if(F7Name.equalsIgnoreCase("供应商")){//供应商  
			queryInfo = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQuery";  
			selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(
					bizPromptBox,"com.kingdee.eas.basedata.master.cssp.client.F7SupplierSimpleTreeDetailListUI",
					own, null, queryInfo, "browseGroup.id", null, queryInfo);
		}else if(F7Name.equalsIgnoreCase("银行帐户")){//银行帐户  
			queryInfo = "com.kingdee.eas.basedata.assistant.app.F7AccountBankQuery";  

		}else if(F7Name.equalsIgnoreCase("公司")){//公司  
			queryInfo = "com.kingdee.eas.basedata.org.app.CompanyQuery";  

		}else if(F7Name.equalsIgnoreCase("物料")){//物料  
			queryInfo = "com.kingdee.eas.basedata.master.material.app.F7MaterialQuery";
			selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(
					bizPromptBox,"com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI",
					own, MaterialGroupInfo.getBosType(), queryInfo, "materialGroup.id",null, queryInfo);

		}else if(F7Name.equalsIgnoreCase("成本中心")){//成本中心  
			queryInfo = "com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery4AsstAcct";  

		}else if(F7Name.equalsIgnoreCase("项目")){//项目  
			queryInfo = "com.kingdee.eas.basedata.assistant.app.F7ProjectQuery";  

		}else if(F7Name.equalsIgnoreCase("计量单位")){
			queryInfo = "com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery";
			selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(
					bizPromptBox,"com.kingdee.eas.basedata.assistant.client.F7MeasureUnitTreeDetailListUI",
					own, MeasureUnitGroupInfo.getBosType(), queryInfo, "measureUnitGroup.id",
					null, queryInfo);

		}else if(F7Name.equalsIgnoreCase("职员")){
			bizPromptBox.setEditable(true);
			bizPromptBox.setEditFormat("$number$");
			bizPromptBox.setCommitFormat("$number$;$idNum$");
			bizPromptBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
			
		}

		try {
			setBizCustomerF7(queryInfo, bizPromptBox, isEnableMulti, selectorLisenterCustomer);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2017-12-26 11:47:58 zl_lixin
	 * 设置科目左树右表
	 * @param bizPromptBox  F7控件
	 * @param compay		公司
	 * @param fi			过滤条件
	 */
	public static void setAccountViewLeftTree(KDBizPromptBox bizPromptBox,CompanyOrgUnitInfo compay,FilterInfo fi)throws Exception{
		bizPromptBox.setSelector(new AccountPromptBox(new F7AccountViewUI(),compay,fi));
	}
	
	/**
	 * 2017-12-26 11:47:58 zl_lixin
	 * 设置自定义核算项目左树右表
	 * @param bizPromptBox			F7控件
	 * @param groupId				自定义核算项目类型id
	 * @param own					当前界面（this）
	 * @throws Exception			
	 */
	public static void setGeneralAsstActTypeLR(KDBizPromptBox bizPromptBox,String groupNumber,CoreUIObject own)throws Exception{
		if(groupNumber!=null){
			GeneralAsstActTypeGroupCollection groupCollection = GeneralAsstActTypeGroupFactory.getRemoteInstance()
			.getGeneralAsstActTypeGroupCollection(new EntityViewInfo("where number = '"+groupNumber+"'"));
			
			if(groupCollection!=null && groupCollection.get(0)!=null){
				AuxAccountUtils.assignGeneralBizPromptBox(bizPromptBox,groupCollection.get(0).getId().toString(),false,own);  
			}
		}
	}

	/**
	 * 2015-12-25 11:41:09 zl_lix
	 * 设置左树右表显示
	 */
	private static void setBizCustomerF7(String queryInfo,KDBizPromptBox bizPromptBox,boolean isEnableMulti,GeneralKDPromptSelectorAdaptor selectorLisenterCustomer) throws EASBizException, BOSException {
		if(queryInfo.equals(""))
			return;

		if(bizPromptBox == null)
			return;

		if(selectorLisenterCustomer == null)
			return;

		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");

		//是否启用多选
		selectorLisenterCustomer.setIsMultiSelect(isEnableMulti);
		EntityViewInfo customerView = new EntityViewInfo();
		FilterInfo bizFilterInfo = new FilterInfo();
		customerView.setFilter(bizFilterInfo);
		bizPromptBox.setEntityViewInfo(customerView);
		bizPromptBox.setSelector(selectorLisenterCustomer);
		bizPromptBox.addSelectorListener(selectorLisenterCustomer);
	}

	/**
	 * 2015-12-25 11:49:02 zl_lix
	 * 设置F7控件左树右表显示(组织)
	 * @param own	默认this
	 * @param bizPromptBox	F7控件
	 * @param F7Name	F7控件所选的基础资料的名称
	 * @param isEnableMulti	是否启用多选
	 */
	public static void setF7OrgLeftTreeRightTable(Object own,KDBizPromptBox bizPromptBox,String F7Name,boolean isEnableMulti){
		if(F7Name.equalsIgnoreCase("行政组织")){
			NewAdminF7 f7 = new NewAdminF7();
			f7.setMultiSelect(isEnableMulti);

			//增加过滤：过滤出实体的财务组织  2016-3-10 19:35:19 zl_lix
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(new FilterItemInfo("unit.number",FilterPermissionsOrg.filterEntityOrg("行政组织"),CompareType.INCLUDE));
			f7.setOuterFilterInfo(fi);

			bizPromptBox.setSelector(f7);
			return;
		}else if(F7Name.equalsIgnoreCase("职员")){
			EmployeeMultiF7PromptBox f7 = new EmployeeMultiF7PromptBox();
			f7.setIsShowAllAdmin(true);
			bizPromptBox.setSelector(f7);  
			return;
		}else if(F7Name.equalsIgnoreCase("销售组织")){
			NewSaleF7 f7 = new NewSaleF7();
			f7.setMultiSelect(isEnableMulti);
			//增加过滤：过滤出实体的财务组织  2016-3-10 19:35:19 zl_lix
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(new FilterItemInfo("unit.number",FilterPermissionsOrg.filterEntityOrg("销售组织"),CompareType.INCLUDE));
			f7.setOuterFilterInfo(fi);


			bizPromptBox.setSelector(f7);
			return;
		}else if(F7Name.equalsIgnoreCase("财务组织")){
			NewCompanyF7 f7 = new NewCompanyF7();
			f7.setMultiSelect(isEnableMulti);

			//增加过滤：过滤出实体的财务组织  2016-3-10 19:35:19 zl_lix
			FilterInfo fi = new FilterInfo();
			fi.getFilterItems().add(new FilterItemInfo("unit.number",FilterPermissionsOrg.filterEntityOrg("财务组织"),CompareType.INCLUDE));
			f7.setOuterFilterInfo(fi);

			bizPromptBox.setSelector(f7);
			return;
		}

		bizPromptBox.setCommitFormat("$number$;$name$");  
		bizPromptBox.setEditFormat("$number$");  
		bizPromptBox.setDisplayFormat("$name$");  
		bizPromptBox.setEditable(true);  
	}


	/**
	 * 职员F7左树右表
	 */
	public static void setBizPersonF7(CoreUIObject own, KDBizPromptBox prmtcompanyOrg, KDBizPromptBox bizPersonBox, OrgType orgType) throws EASBizException, BOSException
	{
		bizPersonBox.setEditable(true);
		bizPersonBox.setEditFormat("$number$");
		bizPersonBox.setCommitFormat("$number$;$idNum$");
		bizPersonBox.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		CommonPersonPromptBox select = new CommonPersonPromptBox(own, prmtcompanyOrg, bizPersonBox, orgType);
		select.showUserAndCuRangeAdminOrg();
		select.setExpandAdmin(((CompanyOrgUnitInfo)prmtcompanyOrg.getValue()).getId().toString());
		bizPersonBox.setSelector(select);
	}

	/**
	 * 2016-3-26 10:49:54 zl_lix
	 * 通用版：设置F7为左树右表结构
	 * @param own			默认this
	 * @param bizPromptBox	F7控件
	 * @param F7Name
	 * @param isEnableMulti
	 */
	private static void  setF7LeftTreeRightTable(Object own,KDBizPromptBox bizPromptBox,String queryInfo,String listUI,boolean isEnableMulti){
		GeneralKDPromptSelectorAdaptor selectorLisenterCustomer = null;
		if(bizPromptBox==null){
			MsgBox.showInfo("在转换为左树右表时，F7控件不能为空");
			return;
		}

		if(queryInfo==null || "".equals(queryInfo)){
			MsgBox.showInfo(bizPromptBox.getName()+"在转换为左树右表时，找不到queryInfo");
			return;
		}

		if(listUI==null || "".equals(listUI)){
			MsgBox.showInfo(bizPromptBox.getName()+"在转换为左树右表时，找不到listUI");
			SysUtil.abort();
		}

		selectorLisenterCustomer = new GeneralKDPromptSelectorAdaptor(
				bizPromptBox,listUI,own, null, queryInfo, "browseGroup.id",null, queryInfo);
		bizPromptBox.setCommitFormat("$number$;$name$");
		bizPromptBox.setEditFormat("$number$");
		bizPromptBox.setDisplayFormat("$name$");

		//是否启用多选
		selectorLisenterCustomer.setIsMultiSelect(isEnableMulti);
		EntityViewInfo env = new EntityViewInfo();
		FilterInfo bizFilterInfo = new FilterInfo();
		env.setFilter(bizFilterInfo);
		bizPromptBox.setEntityViewInfo(env);
		bizPromptBox.setSelector(selectorLisenterCustomer);
		bizPromptBox.addSelectorListener(selectorLisenterCustomer);
	}

	/**
	 * zhuang
	 * 分录物料F7设置为左树右表
	 * @param owner UI
	 * @param kdtEntry 分录
	 * @param column 列名
	 * @param isEnableMulti 是否多选
	 */
	public static void setMaterialTreeForEntry(Object owner, KDTable kdtEntry,
			String column, boolean isEnableMulti) {
		String listQueryInfo = "com.kingdee.eas.basedata.master.material.app.F7MaterialQuery";
		final KDBizPromptBox prmtMaterial = new KDBizPromptBox();
		prmtMaterial.setEnabledMultiSelection(isEnableMulti);
		prmtMaterial.setCommitFormat("$number$;$name$");
		prmtMaterial.setEditFormat("$number$");
		prmtMaterial.setDisplayFormat("$number$");
		ObjectValueRender render = new ObjectValueRender();
		render.setFormat(new BizDataFormat("$name$"));
		kdtEntry.getColumn(column).setRenderer(render);
		GeneralKDPromptSelectorAdaptor selectorLisenterMaterial = new GeneralKDPromptSelectorAdaptor(
				prmtMaterial,
				"com.kingdee.eas.basedata.master.material.client.F7MaterialTreeListUI",
				owner, null, listQueryInfo, "materialGroup.id", null,
				listQueryInfo);
		selectorLisenterMaterial.setIsMultiSelect(isEnableMulti);
		EntityViewInfo materialView = new EntityViewInfo();
		FilterInfo bizFilterInfo = new FilterInfo();
		materialView.setFilter(bizFilterInfo);

		prmtMaterial.setEntityViewInfo(materialView);
		prmtMaterial.setSelector(selectorLisenterMaterial);
		prmtMaterial.addSelectorListener(selectorLisenterMaterial);

		KDTDefaultCellEditor kdtEntry_product_CellEditor = new KDTDefaultCellEditor(
				prmtMaterial);
		kdtEntry.getColumn(column).setEditor(kdtEntry_product_CellEditor);
	}
}
