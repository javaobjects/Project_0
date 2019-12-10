/*
 * @(#)F7ContextManager.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.client.MaterialSelectorFactory;
import com.kingdee.eas.basedata.mm.qm.utils.STClientUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.FrameWorkUtils;
import com.kingdee.eas.scm.common.ISCMGroupFacade;
import com.kingdee.eas.scm.common.SCMGroupFacadeFactory;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor; //import com.kingdee.eas.scm.common.client.SCMBillEditUI;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.scm.common.constants.QueryInfoConstants;
import com.kingdee.eas.scm.sm.pur.util.PurConstant;
import com.kingdee.eas.st.common.client.STBillBaseEditUI;
import com.kingdee.eas.st.common.client.STBillEditUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.util.StringUtils;

/**
 * 描述:管理物料客商的上下文，利用重用
 * 
 * @author paul date:2007-1-30
 *         <p>
 * @version EAS5.2.1
 */
public final class F7ContextManager {
	private int contextType = 0; // 0-Edit 1-Query

	// private List adaptorList = null;

	private int f7DisplayMode = 0;// 物料客商F7展现方式，默认树

	private Map f7Map = null;// 物料客商F7Map

	private Component owner;

	private OrgType mainOrgType;

	private Map diffTypeOrgs = null;// 不同主业务组织类型的F7

	private Map ctxChgF7Map = null;// 需要主业务组织切换的F7，如单据编号，销售组等。

	private Context mainOrgContext;

	// 需要设置主业务组织上下文的F7
	private KDBizPromptBox[] prmtNeedOrgF7s;

	/**
	 * 描述：构造函数
	 * 
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public F7ContextManager(Component owner, OrgType mainOrgType)
			throws EASBizException, BOSException {
		super();
		this.owner = owner;
		this.mainOrgType = mainOrgType;
		init();
	}

	/**
	 * 描述：构造函数
	 * 
	 * @author:aegeanmist 创建时间：2008-8-30
	 *                    <p>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public F7ContextManager(Component owner, int f7DisplayMode,
			OrgType mainOrgType) throws EASBizException, BOSException {
		super();
		this.owner = owner;
		this.mainOrgType = mainOrgType;
		this.f7DisplayMode = f7DisplayMode;
		f7Map = new HashMap();
		diffTypeOrgs = new HashMap();
		ctxChgF7Map = new HashMap();
	}

	/**
	 * 描述：供应商F7的Context构建，组织类型不同时，自己负责更新上下文的情况
	 * 
	 * @param bizSupplierBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 */
	public void registerBizSupplierF7(KDBizPromptBox bizSupplierBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg) {

		registerBizSupplierF7(bizSupplierBox, col, queryInfo, mainOrg,
				mainOrgType);
	}

	/**
	 * 描述：供应商F7的Context构建, 支持组织类型不同于主业务组织类型的F7上下文更新
	 * 
	 * @param bizSupplierBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @param isRelegable
	 *            -- 是否委托给Manager管理上下文，使用在和主业务组织类型不同时
	 * @author:paul 创建时间：2007-3-15
	 *              <p>
	 */
	public void registerBizSupplierF7(KDBizPromptBox bizSupplierBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType, boolean isRelegable) {

		registerBizSupplierF7(bizSupplierBox, col, queryInfo, mainOrg,
				mainOrgType);
		if (isRelegable)
			diffTypeOrgs.put(bizSupplierBox, mainOrgType);
	}

	/**
	 * 描述：供应商F7的Context构建
	 * 
	 * @param bizSupplierBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @author:paul 创建时间：2007-3-9
	 *              <p>
	 */
	public void registerBizSupplierF7(KDBizPromptBox bizSupplierBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType) {

		if (queryInfo != null) {
			bizSupplierBox.setQueryInfo(queryInfo);
		}
		if (contextType == 0) {
			bizSupplierBox.setEditable(true);
			bizSupplierBox.setEditFormat("$number$");
			bizSupplierBox.setDisplayFormat("$number$");
			bizSupplierBox.setCommitFormat("$number$;$name$;$mnemonicCode$");
		}
		// 供应商F7展现方式
		if (f7DisplayMode == 0) {
			//adaptorList.add(SCMGroupClientUtils.setBizSupplierF7(bizSupplierBox
			// ,
			// owner, mainOrgType, queryInfo));
			f7Map.put(bizSupplierBox, SCMGroupClientUtils.setBizSupplierF7(
					bizSupplierBox, owner, mainOrgType, queryInfo));
		} else {
			if (queryInfo == null) {
				bizSupplierBox.setQueryInfo(QueryInfoConstants
						.getStdSupplierQueryInfo(mainOrgType));
			}
			f7Map.put(bizSupplierBox, QueryInfoConstants
					.getSupplierQueryOrgId(mainOrgType));
			bizSupplierBox.setEditable(true);
		}

		// 核准状态
		if (contextType == 0) {
			SCMGroupClientUtils.setApproved4SupplierF7(bizSupplierBox,
					mainOrgType);
		}

		if (col != null) {
			col.setEditor(new KDTDefaultCellEditor(bizSupplierBox));
			// 设置显示编码格式
			ObjectValueRender avr = new ObjectValueRender();
			avr.setFormat(new BizDataFormat("$name$"));
			col.setRenderer(avr);
		}

		if (mainOrg != null) {
			bizSupplierBox.setCurrentMainBizOrgUnit(mainOrg, mainOrgType);
		}
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param contextType
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public F7ContextManager(Component owner, OrgType mainOrgType,
			int contextType) throws EASBizException, BOSException {
		this.owner = owner;
		this.contextType = contextType;
		this.mainOrgType = mainOrgType;
		init();
	}

	private void init() throws EASBizException, BOSException {
		f7DisplayMode = SCMGroupClientUtils.getF7DisplayMode(SysContext
				.getSysContext().getCurrentCtrlUnit().getId().toString());
		f7Map = new HashMap();
		diffTypeOrgs = new HashMap();
		ctxChgF7Map = new HashMap();
	}

	/**
	 * 描述：客户F7的Context构建，组织类型不同时，自己负责更新上下文的情况
	 * 
	 * @param bizCustomerBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 */
	public void registerBizCustomerF7(KDBizPromptBox bizCustomerBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg) {
		registerBizCustomerF7(bizCustomerBox, col, queryInfo, mainOrg,
				mainOrgType);
	}

	/**
	 * 描述：客户F7的Context构建, 支持组织类型不同于主业务组织类型的F7上下文更新
	 * 
	 * @param bizCustomerBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @param isRelegable
	 *            -- 是否委托给Manager管理上下文，使用在和主业务组织类型不同时
	 * @author:paul 创建时间：2007-3-15
	 *              <p>
	 */
	public void registerBizCustomerF7(KDBizPromptBox bizCustomerBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType, boolean isRelegable) {
		registerBizCustomerF7(bizCustomerBox, col, queryInfo, mainOrg,
				mainOrgType);
		if (isRelegable)
			diffTypeOrgs.put(bizCustomerBox, mainOrgType);
	}

	/**
	 * 描述：客户F7的Context构建
	 * 
	 * @param bizCustomerBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @author:paul 创建时间：2007-3-9
	 *              <p>
	 */
	public void registerBizCustomerF7(KDBizPromptBox bizCustomerBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType) {
		if (queryInfo != null) {
			bizCustomerBox.setQueryInfo(queryInfo);
		}
		if (contextType == 0) {
			bizCustomerBox.setEditFormat("$number$");
			bizCustomerBox.setDisplayFormat("$number$");
			bizCustomerBox.setCommitFormat("$number$;$name$;$mnemonicCode$");
		}
		// 客户F7展现方式
		if (f7DisplayMode == 0) {
			//adaptorList.add(SCMGroupClientUtils.setBizCustomerF7(bizCustomerBox
			// ,
			// owner, mainOrgType, queryInfo));
			f7Map.put(bizCustomerBox, SCMGroupClientUtils.setBizCustomerF7(
					bizCustomerBox, owner, mainOrgType, queryInfo));
		} else {
			if (queryInfo == null) {
				bizCustomerBox.setQueryInfo(QueryInfoConstants
						.getStdCustomerQueryInfo(mainOrgType));
			}
			f7Map.put(bizCustomerBox, QueryInfoConstants
					.getCustomerQueryOrgId(mainOrgType));
			bizCustomerBox.setEditable(true);

		}
		// 核准状态
		if (contextType == 0) {
			SCMGroupClientUtils.setApproved4CustomerF7(bizCustomerBox,
					mainOrgType);
		}

		if (col != null) {
			col.setEditor(new KDTDefaultCellEditor(bizCustomerBox));
			// 设置显示编码格式
			ObjectValueRender avr = new ObjectValueRender();
			avr.setFormat(new BizDataFormat("$name$"));
			col.setRenderer(avr);
		}
		if (mainOrg != null) {
			bizCustomerBox.setCurrentMainBizOrgUnit(mainOrg, mainOrgType);
		}
	}

	/**
	 * 描述：物料F7的Context构建，组织类型不同时，自己负责更新上下文的情况
	 * 
	 * @param bizMaterialBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param isMulSelect
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 */
	public void registerBizMaterialF7(KDBizPromptBox bizMaterialBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			boolean isMulSelect) {
		registerBizMaterialF7(bizMaterialBox, col, queryInfo, mainOrg,
				mainOrgType, isMulSelect);
	}

	/**
	 * 描述：物料F7的Context构建, 支持组织类型不同于主业务组织类型的F7上下文更新
	 * 
	 * @param bizMaterialBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @param isMulSelect
	 * @param isRelegable
	 *            -- 是否委托给Manager管理上下文，使用在和主业务组织类型不同时
	 * @author:paul 创建时间：2007-3-15
	 *              <p>
	 */
	public void registerBizMaterialF7(KDBizPromptBox bizMaterialBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType, boolean isMulSelect, boolean isRelegable) {
		registerBizMaterialF7(bizMaterialBox, col, queryInfo, mainOrg,
				mainOrgType, isMulSelect);
		if (isRelegable)
			diffTypeOrgs.put(bizMaterialBox, mainOrgType);
	}

	/**
	 * 描述：物料F7的Context构建
	 * 
	 * @param bizMaterialBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @param isMulSelect
	 * @author:paul 创建时间：2007-3-15
	 *              <p>
	 */
	public void registerBizMaterialF7(KDBizPromptBox bizMaterialBox,
			IColumn col, String queryInfo, OrgUnitInfo mainOrg,
			OrgType mainOrgType, boolean isMulSelect) {
		if (contextType == 0) {
			bizMaterialBox.setEditFormat("$number$");
			bizMaterialBox.setDisplayFormat("$number$");
			bizMaterialBox
					.setCommitFormat("$number$;$name$;$model$;$helpCode$");
		}

		// 物料F7展现方式
		if (f7DisplayMode == 0) {
			f7Map
					.put(bizMaterialBox, SCMGroupClientUtils.setBizMaterialF7(
							bizMaterialBox, owner, mainOrgType, isMulSelect,
							queryInfo));
		} else {
			if (queryInfo == null) {
				bizMaterialBox.setQueryInfo(QueryInfoConstants
						.getMaterialNoGroupQueryInfo(mainOrgType));
			} else {
				bizMaterialBox.setQueryInfo(queryInfo);
			}
			f7Map.put(bizMaterialBox, QueryInfoConstants
					.getMaterialQueryOrgId(mainOrgType));

			bizMaterialBox.setEditable(true);
		}

		// 物料F7多选
		bizMaterialBox.setEnabledMultiSelection(isMulSelect);

		// 增加二次开发的支持
		// mod by sky_lv 解决由于SelectorItemCollection被覆盖，导致基本单位名称显示不出来问题
		// 2008-11-08
		SelectorItemCollection selectors = bizMaterialBox
				.getSelectorCollection();
		if (selectors == null) {
			selectors = new SelectorItemCollection();
		}
		selectors.add(new SelectorItemInfo("*"));
		selectors.add(new SelectorItemInfo("baseUnit.id"));
		selectors.add(new SelectorItemInfo("baseUnit.number"));
		selectors.add(new SelectorItemInfo("baseUnit.name"));
		selectors.add(new SelectorItemInfo("assistUnit.id"));
		selectors.add(new SelectorItemInfo("assistUnit.number"));
		selectors.add(new SelectorItemInfo("assistUnit.name"));
		selectors.addObjectCollection(MaterialSelectorFactory.getSelectors());
		bizMaterialBox.setSelectorCollection(selectors);
		// end mod

		// 核准状态
		if (contextType == 0) {
			SCMGroupClientUtils.setApproved4MaterialF7(bizMaterialBox,
					mainOrgType);
		}

		if (contextType == 0 && col != null) {
			col.setEditor(new KDTDefaultCellEditor(bizMaterialBox));
			// 设置显示编码格式
			ObjectValueRender avr = new ObjectValueRender();
			avr.setFormat(new BizDataFormat("$number$"));
			col.setRenderer(avr);
		}

		if (mainOrg != null) {
			bizMaterialBox.setCurrentMainBizOrgUnit(mainOrg, mainOrgType);
		}

	}

	/**
	 * 描述：物料客商F7上下文更新
	 * 
	 * @author:paul 创建时间：2007-1-30
	 *              <p>
	 * @param needSetMainOrgContext
	 *            是否要设置主业务组织上下文
	 */
	public void changeF7Context(boolean needSetMainOrgContext) {
		// 物料客商F7展现方式
		if (f7DisplayMode == 0) {

			Iterator iter = f7Map.keySet().iterator();
			while (iter.hasNext()) {
				GeneralKDPromptSelectorAdaptor adaptor = (GeneralKDPromptSelectorAdaptor) f7Map
						.get(iter.next());
				if (needSetMainOrgContext)
					adaptor.setMainOrgContext(mainOrgContext);
				adaptor.isCurrentMainOrgChanged();
			}
		} else {
			Iterator iter = f7Map.keySet().iterator();
			while (iter.hasNext()) {
				KDBizPromptBox f7 = (KDBizPromptBox) iter.next();
				SCMGroupClientUtils.isCurrentMainOrgChanged(f7, (String) f7Map
						.get(f7), null);
			}
		}
	}

	/**
	 * 描述：F7上下文变化的新接口，可包括所有需要根据主业务组织上下文变化的F7
	 * 
	 * @param ids
	 * @author:paul 创建时间：2007-6-2
	 *              <p>
	 */
	public void changeF7Context(String ids) {
		changeF7Context(false); // 要调用，否则无法刷新FilterView

		changeContext4F7(ids);
	}

	/**
	 * 描述：F7上下文变化的新接口，直接设置F7mainOrgContext
	 * 
	 * @param ids
	 * @param collection
	 * @author:paul 创建时间：2007-8-29
	 *              <p>
	 */
	public void changeF7Context(String ids, OrgUnitCollection collection) {
		if (ids == null) {
			return;
		}
		// 初始化mainOrgContext
		initUIMainOrgContext(ids.indexOf(',') > -1 ? ids.substring(0, ids
				.indexOf(',')) : ids);

		// 更新F7上下文mainOrgContext
		if (prmtNeedOrgF7s != null && collection != null && mainOrgType != null) {
			for (int i = 0; i < prmtNeedOrgF7s.length; i++) {
				prmtNeedOrgF7s[i].setMainBizOrgs(collection, mainOrgType,
						mainOrgContext);
				// 设置D类资料的CU过滤 2007-9-6
				if (f7Map.containsKey(prmtNeedOrgF7s[i])) {
					prmtNeedOrgF7s[i]
							.setFilterInfoProducer(FilterInfoProducerFactory
									.getMultiOUs4DataFilterInfoProducer(
											StringUtils
													.arrayToString(
															prmtNeedOrgF7s[i]
																	.getQueryInfo()
																	.split(
																			"\\\\"),
															"."), collection));
				}
				prmtNeedOrgF7s[i].getQueryAgent().resetRuntimeEntityView();
			}
		}

		// 更新F7主业务组织，在组织类型和主业务组织类型不同时
		ISCMGroupFacade iScmGroup;
		try {
			iScmGroup = SCMGroupFacadeFactory.getRemoteInstance();
			HashMap orgsMap = new HashMap();
			Iterator iter = diffTypeOrgs.entrySet().iterator();
			OrgUnitCollection delegatedOrgs = null;
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				KDBizPromptBox f7 = (KDBizPromptBox) entry.getKey();
				OrgType orgType = (OrgType) entry.getValue();
				if (orgsMap.containsKey(orgType)) {
					delegatedOrgs = (OrgUnitCollection) orgsMap.get(orgType);
				} else if (collection != null) {
					delegatedOrgs = iScmGroup.obtainDelegatedOrgs(collection,
							mainOrgType, orgType);
					orgsMap.put(orgType, delegatedOrgs);
				}
				f7.setMainBizOrgs(delegatedOrgs, orgType);
				f7.getQueryAgent().resetRuntimeEntityView();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}

		changeF7Context(ids);
	}

	/**
	 * 描述：物料客商F7上下文更新, 增加的新接口，支持不同组织类型的F7，F7必须和主业务组织具有委托关系
	 * 
	 * @param uiContext
	 * @author:paul 创建时间：2007-3-15
	 *              <p>
	 */
	public void changeF7Context(Map uiContext) {
		if (uiContext == null) {
			changeF7Context(true);
			return;
		}

		// 更新F7主业务组织，在组织类型和主业务组织类型不同时
		Iterator iter = diffTypeOrgs.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			KDBizPromptBox f7 = (KDBizPromptBox) entry.getKey();
			OrgType orgType = (OrgType) entry.getValue();
			// 性能优化 F7传 mainOrgContext 2007-10-23
			// f7.setCurrentMainBizOrgUnit((OrgUnitInfo) uiContext.get(orgType),
			// orgType);
			f7.setCurrentMainBizOrgUnit((OrgUnitInfo) uiContext.get(orgType),
					orgType, mainOrgContext);
		}

		// f7DisplayMode物料客商F7展现方式
		iter = f7Map.keySet().iterator();
		while (iter.hasNext()) {
			KDBizPromptBox f7 = (KDBizPromptBox) iter.next();

			if (f7DisplayMode == 0) {
				GeneralKDPromptSelectorAdaptor adaptor = (GeneralKDPromptSelectorAdaptor) f7Map
						.get(f7);
				adaptor.setMainOrgContext(mainOrgContext);
				adaptor.isCurrentMainOrgChanged();
			} else {
				SCMGroupClientUtils.isCurrentMainOrgChanged(f7, (String) f7Map
						.get(f7), mainOrgContext);
			}
		}

		// 其它F7
		if (mainOrgContext != null) {
			OrgUnitInfo orgInfo = (OrgUnitInfo) this.mainOrgContext
					.get(this.mainOrgType);
			if (orgInfo != null)
				this.changeContext4F7(orgInfo.getId().toString());
		}
	}

	public void changeF7Context(KDBizPromptBox kdBizPromptBox) {
		if (f7DisplayMode == 0) {
			GeneralKDPromptSelectorAdaptor adaptor = (GeneralKDPromptSelectorAdaptor) f7Map
					.get(kdBizPromptBox);
			if (adaptor != null) {
				adaptor.isCurrentMainOrgChanged();
			}
		} else {
			String id = (String) f7Map.get(kdBizPromptBox);
			if (id != null) {
				SCMGroupClientUtils.isCurrentMainOrgChanged(kdBizPromptBox, id,
						null);
			}
		}
	}

	/**
	 * 描述：通用过滤F7的上下文切换
	 * 
	 * @param kdBizPromptBox
	 * @param collection
	 * @author:paul 创建时间：2007-12-10
	 *              <p>
	 */
	public void changeF7Context(KDBizPromptBox kdBizPromptBox,
			OrgUnitCollection collection) {
		if (f7DisplayMode == 0) {
			GeneralKDPromptSelectorAdaptor adaptor = (GeneralKDPromptSelectorAdaptor) f7Map
					.get(kdBizPromptBox);
			if (adaptor != null) {
				adaptor.isCurrentMainOrgChanged();
			}
		} else {
			String id = (String) f7Map.get(kdBizPromptBox);
			if (id != null) {
				SCMGroupClientUtils.isCurrentMainOrgChanged(kdBizPromptBox, id,
						null);
			}
		}
	}

	/**
	 * 描述：注册计量单位F7，每个列只创建一个F7，根据物料不同设置不同过滤
	 * 
	 * @param table
	 * @param colNameMaterial
	 * @param colNameUnit
	 * @author:paul 创建时间：2006-11-24
	 *              <p>
	 */
	public void registerMeasureUnitF7(final KDTable table,
			final String colNameMaterial, final String colNameUnit) {
		// 对计量单位列增加F7

		if (table.getColumn(colNameUnit).getEditor() == null) {
			KDBizPromptBox bizUnitBox = new KDBizPromptBox();
			// bizUnitBox.setQueryInfo(PurConstant.MULTIUNITF7_PATH);
			bizUnitBox.setEditFormat("$number$");
			bizUnitBox.setDisplayFormat("$number$");
			bizUnitBox.setCommitFormat("$number$");
			bizUnitBox.setEditable(true);

			table.getColumn(colNameUnit).setEditor(
					new KDTDefaultCellEditor(bizUnitBox));
		}
		KDBizPromptBox bizUnitBox = (KDBizPromptBox) table.getColumn(
				colNameUnit).getEditor().getComponent();
		bizUnitBox.setQueryInfo(PurConstant.MULTIUNITF7_PATH);
		bizUnitBox
				.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
					public void dataChanged(
							com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
						try {
							SCMClientUtils
									.setMultiToMeasureUnit((KDBizPromptBox) e
											.getSource());
						} catch (Exception exc) {
							exc.printStackTrace();
						} finally {
						}
					}
				});

		table.addKDTActiveCellListener(new KDTActiveCellListener() {

			public void activeCellChanged(KDTActiveCellEvent e) {
				int rowIndex = table.getSelectManager().getActiveRowIndex();
				if (rowIndex >= 0) {
					int colIndex = table.getSelectManager()
							.getActiveColumnIndex();
					if (table.getColumn(colNameUnit).getColumnIndex() == colIndex) {
						// Object value = table.getCell(rowIndex,
						// colNameMaterial).getValue();
						// MaterialInfo materialInfo = (value instanceof
						// MaterialInfo) ?
						// (MaterialInfo) value : null;
						MaterialInfo materialInfo = (MaterialInfo) table
								.getCell(rowIndex, colNameMaterial).getValue();
						// 因行类型，可能无物料编码，所以计量单位的Query要跟着修改 paul 2007-9-18
						ICellEditor cellEditor = table.getColumn(colNameUnit)
								.getEditor();
						KDBizPromptBox bizUnitBox = null;
						if (cellEditor != null) {
							bizUnitBox = (KDBizPromptBox) cellEditor
									.getComponent();
						}
						if (bizUnitBox == null) {
							return;
						}
						if (materialInfo != null) {
							bizUnitBox
									.setQueryInfo(PurConstant.MULTIUNITF7_PATH);
							SCMClientUtils.setF7MeasureUnit(bizUnitBox,
									materialInfo.getId().toString());
						} else {
							// 没有物料
							bizUnitBox.setQueryInfo(PurConstant.BASEUNIT_PATH);
						}
					}
				}
			}

		});

	}

	/**
	 * 描述：注册辅助属性F7，每个列只创建一个F7，根据物料不同设置不同过滤
	 * 
	 * @param table
	 * @param colNameMaterial
	 * @param colNameAssitProperty
	 * @param editUI
	 * @author:paul 创建时间：2006-11-24
	 *              <p>
	 */
	public void registerAssistPropertyF7(final KDTable table,
			final String colNameMaterial, final String colNameAssitProperty,
			final STBillBaseEditUI editUI) {
		// 对辅助属性列增加F7

		if (table.getColumn(colNameAssitProperty).getEditor() == null) {
			KDBizPromptBox bizAssistPropertyBox = new KDBizPromptBox();
			// bizAssistPropertyBox.setQueryInfo(
			// "com.kingdee.eas.basedata.master.material.app.F7AsstAttrValueQuery"
			// );
			bizAssistPropertyBox.setEditFormat("$number$");
			bizAssistPropertyBox.setDisplayFormat("$name$");
			bizAssistPropertyBox.setCommitFormat("$number$");
			bizAssistPropertyBox.setEditable(true);

			table.getColumn(colNameAssitProperty).setEditor(
					new KDTDefaultCellEditor(bizAssistPropertyBox));
		}
		KDBizPromptBox bizAssistPropertyBox = (KDBizPromptBox) table.getColumn(
				colNameAssitProperty).getEditor().getComponent();
		bizAssistPropertyBox
				.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialAsstAttrValueQuery");

		table.addKDTActiveCellListener(new KDTActiveCellListener() {

			public void activeCellChanged(KDTActiveCellEvent e) {
				int rowIndex = table.getSelectManager().getActiveRowIndex();
				// 应该是大于等于0吧
				if (rowIndex >= 0) {
					int colIndex = table.getSelectManager()
							.getActiveColumnIndex();
					if (table.getColumn(colNameAssitProperty).getColumnIndex() == colIndex) {
						MaterialInfo materialInfo = (MaterialInfo) table
								.getCell(rowIndex, colNameMaterial).getValue();
						if (materialInfo != null) {
							String assistAttrTypeID = materialInfo
									.getAssistAttr() == null ? null
									: materialInfo.getAssistAttr().getId()
											.toString();
							if (assistAttrTypeID != null) {
								/**
								 * 暂时屏蔽
								 * STClientUtils.setF7MaterialAssistProperty(
								 * (KDBizPromptBox)
								 * table.getColumn(colNameAssitProperty
								 * ).getEditor().getComponent(),
								 * materialInfo.getId().toString());
								 **/
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 描述：注册仓库F7，每个列只创建一个F7，根据物料不同设置不同过滤
	 * 
	 * @param table
	 * @param colNameStorageOrgUnit
	 * @param colNameWarehouse
	 * @param editUI
	 * @author:paul 创建时间：2007-3-9
	 *              <p>
	 */
	public void registerWarehouseF7(final KDTable table,
			final String colNameStorageOrgUnit, final String colNameWarehouse) {
		// 对辅助属性列增加F7

		if (table.getColumn(colNameWarehouse).getEditor() == null) {
			KDBizPromptBox kDBizPromptBoxWarehouse = new KDBizPromptBox();
			kDBizPromptBoxWarehouse.setEditable(true);
			//kDBizPromptBoxWarehouse.setQueryInfo(QueryInfoConstants.WHAREHOUSE
			// );
			kDBizPromptBoxWarehouse.setDisplayFormat("$number$");
			kDBizPromptBoxWarehouse.setEditFormat("$number$");
			kDBizPromptBoxWarehouse.setCommitFormat("$number$");

			table.getColumn(colNameWarehouse).setEditor(
					new KDTDefaultCellEditor(kDBizPromptBoxWarehouse));
		}
		KDBizPromptBox kDBizPromptBoxWarehouse = (KDBizPromptBox) table
				.getColumn(colNameWarehouse).getEditor().getComponent();
		kDBizPromptBoxWarehouse.setQueryInfo(QueryInfoConstants.WHAREHOUSE);

		table.addKDTActiveCellListener(new KDTActiveCellListener() {

			public void activeCellChanged(KDTActiveCellEvent e) {
				int rowIndex = table.getSelectManager().getActiveRowIndex();
				// 应该是大于等于0吧
				if (rowIndex >= 0) {
					int colIndex = table.getSelectManager()
							.getActiveColumnIndex();
					if (table.getColumn(colNameWarehouse).getColumnIndex() == colIndex) {
						StorageOrgUnitInfo storageOrgUnitInfo = (StorageOrgUnitInfo) table
								.getRow(rowIndex)
								.getCell(colNameStorageOrgUnit).getValue();
						if (storageOrgUnitInfo != null) {
							try {
								((KDBizPromptBox) table.getColumn(
										colNameWarehouse).getEditor()
										.getComponent())
										.setEntityViewInfo(SCMClientUtils
												.getWarehouseFilter(
														storageOrgUnitInfo,
														SCMClientUtils.WHFILTER_ALL));
							} catch (EASBizException e1) {
								/* TODO 自动生成 catch 块 */
								e1.printStackTrace();
							} catch (BOSException e1) {
								/* TODO 自动生成 catch 块 */
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
	}

	/**
	 * @Description:渠道
	 * @param bizSupplierBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 *            Author: lxf98
	 * @Create DateTime:2008-7-5 上午08:57:11
	 */
	public void registerBizChannelF7(KDBizPromptBox bizChannelBox, IColumn col,
			String queryInfo, OrgUnitInfo mainOrg) {

		registerBizChannelF7(bizChannelBox, col, queryInfo, mainOrg,
				mainOrgType);
	}

	/**
	 * @Description:
	 * @param bizChannelBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 * @param isRelegable
	 *            Author: lxf98
	 * @Create DateTime:2008-7-5 上午08:59:27
	 */
	public void registerBizChannelF7(KDBizPromptBox bizChannelBox, IColumn col,
			String queryInfo, OrgUnitInfo mainOrg, OrgType mainOrgType,
			boolean isRelegable) {

		registerBizChannelF7(bizChannelBox, col, queryInfo, mainOrg,
				mainOrgType);
		if (isRelegable)
			diffTypeOrgs.put(bizChannelBox, mainOrgType);
	}

	/**
	 * @Description:
	 * @param bizChannelBox
	 * @param col
	 * @param queryInfo
	 * @param mainOrg
	 * @param mainOrgType
	 *            Author: lxf98
	 * @Create DateTime:2008-7-5 上午08:59:32
	 */
	public void registerBizChannelF7(KDBizPromptBox bizChannelBox, IColumn col,
			String queryInfo, OrgUnitInfo mainOrg, OrgType mainOrgType) {

		if (queryInfo != null) {
			bizChannelBox.setQueryInfo(queryInfo);
		}
		if (contextType == 0) {
			bizChannelBox.setEditable(true);
			bizChannelBox.setEditFormat("$number$");
			bizChannelBox.setDisplayFormat("$number$");
			bizChannelBox.setCommitFormat("$number$;$name$;$mnemonicCode$");
		}
		// 供应商F7展现方式
		if (f7DisplayMode == 0) {
			//adaptorList.add(SCMGroupClientUtils.setBizSupplierF7(bizSupplierBox
			// ,
			// owner, mainOrgType, queryInfo));
			f7Map.put(bizChannelBox, SCMGroupClientUtils.setBizSupplierF7(
					bizChannelBox, owner, mainOrgType, queryInfo));
		} else {
			if (queryInfo == null) {
				bizChannelBox.setQueryInfo(QueryInfoConstants
						.getStdSupplierQueryInfo(mainOrgType));
			}
			f7Map.put(bizChannelBox, QueryInfoConstants
					.getSupplierQueryOrgId(mainOrgType));
			bizChannelBox.setEditable(true);
		}

		// 核准状态
		if (contextType == 0) {
			SCMGroupClientUtils.setApproved4SupplierF7(bizChannelBox,
					mainOrgType);
		}

		if (col != null) {
			col.setEditor(new KDTDefaultCellEditor(bizChannelBox));
			// 设置显示编码格式
			ObjectValueRender avr = new ObjectValueRender();
			avr.setFormat(new BizDataFormat("$number$"));
			col.setRenderer(avr);
		}

		if (mainOrg != null) {
			bizChannelBox.setCurrentMainBizOrgUnit(mainOrg, mainOrgType);
		}
	}

	/**
	 * 描述：注册需要上下文根据主业务组织变化的F7，如单据编号、销售组等。
	 * 
	 * @param f7
	 * @param mainOrgProperty
	 * @author:paul 创建时间：2007-6-2
	 *              <p>
	 */
	public void registerContextChangeF7(KDBizPromptBox f7,
			String mainOrgProperty) {
		ctxChgF7Map.put(f7, mainOrgProperty);
	}

	/**
	 * 描述：更改F7的上下文，这些F7是通过registerContextChangeF7注册的
	 * 
	 * @param ids
	 * @author:paul 创建时间：2007-6-2
	 *              <p>
	 */
	private void changeContext4F7(String ids) {
		// 设置单据编号F7的组织过滤
		if (ctxChgF7Map != null && ctxChgF7Map.size() > 0) {
			Map viewMap = new HashMap();
			EntityViewInfo viewInfo = null;
			Iterator iter = ctxChgF7Map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Entry) iter.next();
				KDBizPromptBox f7 = (KDBizPromptBox) entry.getKey();
				String orgProperty = (String) entry.getValue();
				if (orgProperty != null) {

					if (viewMap.containsKey(orgProperty)) {
						viewInfo = (EntityViewInfo) viewMap.get(orgProperty);
					} else {
						viewInfo = new EntityViewInfo();
						viewInfo.setFilter(getFilterInfo(orgProperty, ids));
						viewMap.put(orgProperty, viewInfo);
					}

					f7.setEntityViewInfo(viewInfo);
				}
			}
		}
	}

	private FilterInfo getFilterInfo(String orgProperty, String ids) {
		FilterInfo filterInfo = new FilterInfo();

		if (ids != null) {
			if (ids.indexOf(',') == -1) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo(orgProperty, ids));
			} else {
				filterInfo.getFilterItems().add(
						new FilterItemInfo(orgProperty, ids, // SCMGroupClientUtils
																// .
																// getKeyIdList(
																// ids),
								CompareType.INCLUDE));
			}
		} else if (ids == null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("1", "0"));
		}

		return filterInfo;
	}

	/**
	 * 描述:设置mainOrgContext的值。
	 * 
	 * @param mainOrgContext
	 */
	public void setMainOrgContext(Context mainOrgContext) {
		this.mainOrgContext = mainOrgContext;
	}

	/**
	 * 
	 * 描述：委托F7的Context构建, 支持组织类型不同于主业务组织类型的F7上下文更新
	 * 
	 * @param bizOrgUnitBox
	 * @param col
	 * @param mainOrgType
	 * @param orgType
	 * @param isRelegable
	 *            -- 是否委托给Manager管理上下文，使用在和主业务组织类型不同时
	 * @author:paul 创建时间：2007-7-17
	 *              <p>
	 */
	public void registerBizOrgF7(KDBizPromptBox bizOrgUnitBox, IColumn col,
			OrgType mainOrgType, OrgType orgType, boolean isRelegable) {
		registerBizOrgF7(bizOrgUnitBox, col, null, mainOrgType, orgType);
		if (isRelegable)
			diffTypeOrgs.put(bizOrgUnitBox, mainOrgType);
	}

	/**
	 * 描述：委托组织F7的Context构建
	 * 
	 * @param bizOrgUnitBox
	 * @param col
	 * @param mainOrg
	 * @param mainOrgType
	 * @param orgType
	 * @author:paul 创建时间：2007-7-17
	 *              <p>
	 */
	public void registerBizOrgF7(KDBizPromptBox bizOrgUnitBox, IColumn col,
			OrgUnitInfo mainOrg, OrgType mainOrgType, OrgType orgType) {
		bizOrgUnitBox.setDisplayFormat("$name$");
		bizOrgUnitBox.setEditFormat("$number$");
		// 支持助记码 paul 2007-1-15
		bizOrgUnitBox.setCommitFormat("$number$;$code$");

		SCMGroupClientUtils.setBizOrgF7ByType(bizOrgUnitBox, orgType,
				mainOrgType, true);

		if (mainOrg != null) {
			bizOrgUnitBox.setCurrentMainBizOrgUnit(mainOrg, mainOrgType);
		}
		if (col != null) {
			col.setEditor(new KDTDefaultCellEditor(bizOrgUnitBox));
		}
		bizOrgUnitBox.setEditable(true);
	}

	/**
	 * 描述：初始化mainOrgContext
	 * 
	 * @param orgID
	 * @author:paul 创建时间：2007-8-29
	 *              <p>
	 */
	private void initUIMainOrgContext(String orgID) {
		if (mainOrgContext == null) {
			mainOrgContext = new Context();
		}
		try {
			FrameWorkUtils.switchOrg(mainOrgContext, orgID);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			// e.printStackTrace();
		}
	}

	/**
	 * 描述:设置prmtNeedOrgF7s的值。
	 * 
	 * @param prmtNeedOrgF7s
	 */
	public void setPrmtNeedOrgF7s(KDBizPromptBox[] prmtNeedOrgF7s) {
		this.prmtNeedOrgF7s = prmtNeedOrgF7s;
	}

	/**
	 * 
	 * 获取F7显示方式
	 * 
	 * @author xiaoping_li
	 * @date 2008-07-21
	 * @return F7显示方式
	 */
	public int getF7DisplayMode() {
		return f7DisplayMode;
	}

}
