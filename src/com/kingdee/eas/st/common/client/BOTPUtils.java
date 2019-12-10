/*
 * @(#)BOTPUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTMappingCollection;
import com.kingdee.bos.metadata.bot.BOTMappingFactory;
import com.kingdee.bos.metadata.bot.BOTMappingInfo;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.bot.DefineSysEnum;
import com.kingdee.bos.metadata.bot.IBOTMapping;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.biz.agent.DefaultMultiBillWorkAgent;
import com.kingdee.bos.workflow.biz.agent.IMultiBillWorkAgent;
import com.kingdee.eas.base.botp.BOTPException;
import com.kingdee.eas.base.btp.BTPException;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.btp.client.BTPMakeBillFromMultiDataListUI;
import com.kingdee.eas.base.dap.DAPException;
import com.kingdee.eas.base.uiframe.client.UINewTab;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.client.CoreBillEditUI;
import com.kingdee.eas.framework.client.CoreBillListUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.util.StringUtils;

/**
 * 描述:
 * 
 * @author daij date:2007-1-8
 *         <p>
 * @version EAS5.2
 */
public abstract class BOTPUtils {

	// private static final String TRANSMITCU = "TRANSMITCU";
	//	
	// /**
	// *
	// * 描述：
	// *
	// * @param destBillBOSTypeString
	// * @param sourceBills
	// * @param owner
	// * @throws Exception
	// * @author:daij
	// * 创建时间：2007-1-8 <p>
	// */
	// public static void pushCreateByFixSourceBosTypeAndSourceBills(
	// String destBillBOSTypeString,
	// CoreBillBaseCollection sourceBills,
	// IUIObject owner) throws Exception{
	//    	
	// if(StringUtils.isEmpty(destBillBOSTypeString)
	// || sourceBills == null
	// || owner == null)
	// return;
	//    	
	// //备份来源单据信息
	// CoreBillBaseCollection srcBillInfos =
	// (CoreBillBaseCollection)sourceBills.clone();
	//        
	// //获取BOTP映射关系.
	// BOTMappingInfo botMappingInfo =
	// BOTMappingFactory.getRemoteInstance().getMapping(
	// srcBillInfos,
	// destBillBOSTypeString,
	// DefineSysEnum.BTP);
	//    	
	// if(botMappingInfo != null &&
	// botMappingInfo.getId() != null){
	// //在下推生成时，在转换前回调XXXListUI的beforeTransform方法由业务单据加上适当的业务逻辑校验.
	// if(owner instanceof CoreBillListUI){
	// ((CoreBillListUI)owner).beforeTransform(srcBillInfos,
	// destBillBOSTypeString);
	// }
	//    		
	// //由BOTP转换引擎生成目标单据.
	// IBTPManager iBTPManager = BTPManagerFactory.getRemoteInstance();
	// BTPTransformResult btpResult = null;
	// btpResult = iBTPManager.transformForBotp(
	// srcBillInfos,
	// destBillBOSTypeString,
	// new ObjectUuidPK(botMappingInfo.getId().toString()));
	//            
	// IObjectCollection destBillCols = btpResult.getBills();
	// com.kingdee.bos.metadata.bot.BOTRelationCollection botRelationCols =
	// btpResult.getBOTRelationCollection();
	//            
	// //如果规则要求显示生成的目标单编辑界面.
	// if(botMappingInfo.isIsShowEditUI()){
	// //由目标单实体扩展属性定义EDITUI显示目标单供编辑.
	// String destBillEditUIClassName =
	// iBTPManager.getEntityObjectInfoExtendPro(
	// destBillBOSTypeString, "editUI");
	//                
	// //如果推出了多张单则需要TAB页签显示.
	// String openType = null;
	// Map map = new UIContext(owner);
	// if(owner != null &&
	// (owner.getUIWindow() instanceof UINewTab)){
	// openType = UIFactoryName.NEWTAB;
	// } else{
	// openType = UIFactoryName.MODEL;
	// }
	//                
	// //填写UIContext
	// map.put("srcBillID", srcBillInfos.get(0).getId().toString());
	// map.put("BOTPViewStatus", new Integer(1));
	// IUIWindow uiWindow = null;
	//                
	// if(destBillCols.size() > 1){
	// IIDList idList = RealModeIDList.getEmptyIDList();
	// for(int i = 0,count = destBillCols.size(); i < count; i++){
	// CoreBillBaseInfo destBillInfo =
	// (CoreBillBaseInfo)destBillCols.getObject(i);
	// iBTPManager.saveRelations(destBillInfo, botRelationCols);
	// idList.add(destBillInfo.getId().toString());
	// }
	//
	// idList.setCurrentIndex(0);
	// map.put("ID", idList.getCurrentID());
	// map.put("IDList", idList);
	// uiWindow = UIFactory.createUIFactory(openType).create(
	// destBillEditUIClassName,
	// map,
	// null, OprtState.EDIT);
	// } else{
	// map.put("InitDataObject", destBillCols.getObject(0));
	// uiWindow = UIFactory.createUIFactory(
	// openType).create(
	// destBillEditUIClassName,
	// map,
	// null,
	// OprtState.ADDNEW);
	//                    
	// if(uiWindow.getUIObject().getDataObject() == null ||
	// uiWindow.getUIObject().getDataObject().get("id") == null ||
	// !uiWindow.getUIObject().getDataObject().get("id").toString().equals(
	// destBillCols.getObject(0).get("id").toString())){
	// uiWindow.getUIObject().setDataObject(destBillCols.getObject(0));
	// uiWindow.getUIObject().loadFields();
	// }
	//((CoreBillEditUI)uiWindow.getUIObject()).setMakeRelations(botRelationCols)
	// ;
	// }
	// uiWindow.show();
	// } else{
	// //如果不需要显示目标单编辑界面则迭代暂存转换出所有的目标单.
	// for(int i = 0,count = destBillCols.size(); i < count; i++){
	// CoreBillBaseInfo destBillInfo =
	// (CoreBillBaseInfo)destBillCols.getObject(i);
	// iBTPManager.submitRelations(destBillInfo, botRelationCols);
	// }
	// }
	// }
	// }
	//        
	// public static void drawCreateFromFixSourceBosType(
	// String sourceBillBosTypeString,
	// String destBillBOSTypeString,
	// CoreBillBaseInfo sourceBill,
	// OrgUnitInfo orgUnit,
	// IUIObject owner,
	// Map userCustomFilter,
	// int btpCreateFromType) throws Exception {
	//    	
	// if(StringUtils.isEmpty(sourceBillBosTypeString)
	// || StringUtils.isEmpty(destBillBOSTypeString)
	// || owner == null)
	// return;
	//    	
	// CoreBillEditUI billEditUI = null;
	//    	
	// if(owner instanceof CoreBillEditUI){
	// billEditUI = (CoreBillEditUI)owner;
	// }
	//  		
	// Map uiContext = new UIContext(billEditUI);
	// uiContext.put("billEdit", billEditUI);
	// billEditUI.getUIContext().put(UIContext.BOTPUIPARAM,"botp");
	//  		
	// if (userCustomFilter != null) {
	// uiContext.put("BTPEDITPARAMETER", userCustomFilter);
	// }
	//
	// uiContext.put("destBillBOSTypeString", destBillBOSTypeString);
	//  		
	// //[第一步] 制作来源单据集合.
	// CoreBillBaseCollection srcBillCollection = null;
	// BOTMappingInfo botMappingInfo = getBOTPMappingInfoByBosTypeAndCU(
	// sourceBillBosTypeString,destBillBOSTypeString,orgUnit);
	//        
	// //如果没有指定单一源单则SHOW源单的ListUI供用户选择.
	// if(sourceBill == null){
	// //SHOW源单的ListUI供用户选择
	// IUIWindow window = showSourceBillListUI(
	// owner,
	// uiContext,
	// sourceBillBosTypeString,
	// destBillBOSTypeString,
	// orgUnit,
	// botMappingInfo);
	//  			
	// BTPMakeBillFromMultiDataListUI srcListUI = null;
	// if(window != null && window.getUIObject() instanceof
	// BTPMakeBillFromMultiDataListUI){
	// srcListUI = (BTPMakeBillFromMultiDataListUI)window.getUIObject();
	// }
	// //用户选择并确认进行转换.
	// if(!srcListUI.isCancel()){
	// srcBillCollection = srcListUI.getSrcBillCollection();
	// }
	// } else{
	// //如果指定了单一来源单据则不需要SHOW选择源单的ListUI.
	// srcBillCollection = new CoreBillBaseCollection();
	// srcBillCollection.add(sourceBill);
	// }
	//  		
	// //[第二步] 开始进行BOTP转换.
	// IObjectCollection destBillCols = null;
	// BOTRelationCollection botRelationCols = null;
	// IBTPManager iBTPManager = null;
	// if(srcBillCollection != null){
	//        	
	// //转换前的清理.
	// billEditUI.setMakeRelations(null);
	//            
	// if(botMappingInfo != null){
	// //检验规则是否适合所选单据
	// verifyBillAndBotMapping(
	// destBillBOSTypeString,
	// srcBillCollection,
	// new ObjectUuidPK(botMappingInfo.getId()));
	// iBTPManager = BTPManagerFactory.getRemoteInstance();
	// //通过校验就进行BOTP转换
	// BTPTransformResult btpResult =
	// BTPManagerFactory.getRemoteInstance().transformForBotp(
	// srcBillCollection,
	// destBillBOSTypeString,
	// new ObjectUuidPK(botMappingInfo.getId()));
	//                
	// //制作转换结果
	// destBillCols = btpResult.getBills();
	// botRelationCols = btpResult.getBOTRelationCollection();
	// }
	// }
	//        
	// //[第三步] 在目标单据EditUI转载转换后的结果.
	// if (destBillCols != null && destBillCols.size() > 0) {
	// if (destBillCols.size() > 1) {
	// IIDList idList = RealModeIDList.getEmptyIDList();
	// for (int i = 0, count = destBillCols.size(); i < count; i++) {
	// CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols
	// .getObject(i);
	// IMultiBillWorkAgent agent = new DefaultMultiBillWorkAgent();
	// agent.markBotpTransfer(destBillInfo, destBillInfo.getId()
	// .toString());
	// // 暂存
	// iBTPManager.saveRelations(destBillInfo, botRelationCols);
	// idList.add(destBillInfo.getId().toString());
	// }
	// // 暂存后调用editUI界面
	// idList.setCurrentIndex(0);
	// billEditUI.getUIContext().put(UIContext.ID,
	// idList.getCurrentID());
	// billEditUI.getUIContext().put(UIContext.IDLIST, idList);
	// billEditUI.setOprtState(EditUI.STATUS_EDIT);
	// billEditUI.getUIContext().put("BOTPViewStatus",
	// new Integer(1));
	// billEditUI.onLoad();
	// } else {
	// IObjectValue destBillInfo = destBillCols.getObject(0);
	// // 倪小兵要求增加下面两行
	// IMultiBillWorkAgent agent = new DefaultMultiBillWorkAgent();
	// agent.markBotpTransfer(destBillInfo, destBillInfo.get("id")
	// .toString());
	// billEditUI.setMakeRelations(botRelationCols);
	// billEditUI.getUIContext().put(UIContext.INIT_DATAOBJECT,
	// destBillInfo);
	// billEditUI.setOprtState(EditUI.STATUS_ADDNEW);
	// billEditUI.getUIContext().put("BOTPViewStatus",
	// new Integer(1));
	// billEditUI.onLoad();
	// }
	// } else {
	// if (destBillCols != null && destBillCols.size() == 0) {
	// throw new BTPException(BTPException.DESTBILLNULL);
	// }
	// }
	// }
	//    
	// /**
	// *
	// * 描述：
	// *
	// * @param sourceBillBosTypeString
	// * @param destBillBOSTypeString
	// * @param orgUnit
	// * @throws BOTPException
	// * @throws BOSException
	// * @author:daij
	// * 创建时间：2007-1-9 <p>
	// */
	// public static BOTMappingInfo getBOTPMappingInfoByBosTypeAndCU(
	// String sourceBillBosTypeString,
	// String destBillBOSTypeString,
	// OrgUnitInfo orgUnit) throws BOTPException, BOSException{
	// BOTMappingInfo botMappingInfo = null;
	//        
	// //从主业务组织提取其所在的CU用于获取BOTP规则.
	// if(orgUnit != null){
	// orgUnit = orgUnit.getCU();
	// }
	//        
	// Map transmitCtx = new HashMap();
	// transmitCtx.put(TRANSMITCU, orgUnit);
	// //获得BOTP规则信息
	// BOTMappingCollection botMappingCollection = null;
	// if(botMappingInfo == null){
	// IBOTMapping botMapping=BOTMappingFactory.getRemoteInstance();
	//
	//            
	// botMappingCollection =
	// botMapping.getMappingCollectionForSelectWithoutData(
	// sourceBillBosTypeString,
	// destBillBOSTypeString,
	// transmitCtx);
	//        	
	// if(botMappingCollection != null && botMappingCollection.size() > 0){
	// botMappingInfo = botMappingCollection.get(0);
	// }
	// }
	// return botMappingInfo;
	// }
	//    
	// /**
	// *
	// * 描述：SHOW来源单据的ListUI供用户选择用于转换的源单集合.
	// *
	// * @param sourceBillBosTypeString 来源单据的BOSType串,用于获取源单信息.
	// * @param destBillBOSTypeString 目标单据的BOSType串,用于获取BOTP规则.
	// * @param sourceBill 可以指定来源单据. 本方法仍然会对其进行校验是否符合规则要求.
	// * @param uiContext UI上下文
	// * @param orgUnit 获取规则时的过滤组织OU,需要包含CU信息用于获取规则时进行CU隔离.
	// * @param owner 显示ListUI的宿主.
	// * @param botMappingInfo 当前进行转换的BOTP规则,可为NULL则会根据条件到Sever端取一次规则.
	// * @throws EASBizException
	// * @throws Exception
	// * @author:daij
	// * 创建时间：2007-1-9 <p>
	// */
	// public static IUIWindow showSourceBillListUI(
	// IUIObject owner,
	// Map uiContext,
	// String sourceBillBosTypeString,
	// String destBillBOSTypeString,
	// OrgUnitInfo orgUnit,
	// BOTMappingInfo botMappingInfo) throws EASBizException, Exception{
	//    	
	// //如果没有规则映射则根据条件到Sever端取一次规则.
	// if(botMappingInfo == null){
	// botMappingInfo = getBOTPMappingInfoByBosTypeAndCU(
	// sourceBillBosTypeString,destBillBOSTypeString,orgUnit);
	// }
	//    	
	// IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	//        
	// //构建传递到来源单据列表(XXXListUI)的UIContext,包含在XXXEditUI构造的代码级过滤条件(放在UIContext的
	// "BTPEDITPARAMETER"关键字里).
	// HashMap uiCtx = new HashMap();
	//        
	// //填入来源单据XXXListUI的宿主.
	// uiCtx.put("Owner",owner);
	//        
	// //在XXXEditUI构造的代码级过滤条件(放在UIContext的"BTPEDITPARAMETER"关键字里).
	// if(uiContext.get("BTPEDITPARAMETER") != null){
	// uiCtx.put("BTPEDITPARAMETER", uiContext.get("BTPEDITPARAMETER"));
	// }
	//        
	// //从BOTP规则提取过滤来源单据的ViewInfo
	// EntityViewInfo botpView = getQueryViewInfoFromBotMapping(botMappingInfo);
	//        
	// //TODO 没有加OU过滤, 应该过滤出当前user有权限的组织制作的源单列表??
	// // mergeOrgFilterAndSetSrcBizBillFilter();
	//    	
	// //获取BOTP规则中的过滤条件.
	// uiCtx.put("BOTPFilter", botpView);
	//        
	// IUIWindow uiWin = uiFactory.create(
	// "com.kingdee.eas.base.btp.client.BTPMakeBillFromMultiDataListUI",uiCtx);
	//        
	// ((com.kingdee.eas.base.uiframe.client.UIModelDialog)uiWin).setResizable(
	// true);
	//        
	// BTPMakeBillFromMultiDataListUI srcListUI =
	// (BTPMakeBillFromMultiDataListUI)uiWin.getUIObject();
	//        
	// srcListUI.setSrcBosType(sourceBillBosTypeString);
	//        
	// uiWin.show();
	//        
	// return uiWin;
	// }
	//    
	// /**
	// * 判断当前规则是否适合所选单据
	// *
	// * @param srcBillCols
	// * @param botMappingPK
	// * @throws BOSException
	// * @throws BOTPException
	// * @throws DAPException
	// */
	// public static void verifyBillAndBotMapping(
	// String destBillBOSTypeString,
	// IObjectCollection srcBillCols,
	// IObjectPK botMappingPK) throws BOTPException, BOSException, DAPException
	// {
	//		
	// String botMappingIdStr = botMappingPK.toString();
	//		
	// boolean isSuitable = false;
	//		
	// IBOTMapping botMapping = BOTMappingFactory.getRemoteInstance();
	// BOTMappingCollection botMappingCols =
	// botMapping.getMappingCollectionForSelect(
	// (CoreBillBaseCollection)srcBillCols,
	// destBillBOSTypeString,
	// DefineSysEnum.BTP);
	//		
	// if(botMappingCols != null){
	// Iterator iter = botMappingCols.iterator();
	// while(iter.hasNext()){
	// BOTMappingInfo botMappingInfo = (BOTMappingInfo) iter.next();
	// if(botMappingInfo.getId().toString().equals(botMappingIdStr)){
	// isSuitable = true;
	// break;
	// }
	// }
	// }
	// if(!isSuitable){
	// throw new DAPException(
	// DAPException.BOTMAPPINGNOTSUITABLEFORBILLSELECTED,
	// new Object[]{""});
	// }
	// }
	//	
	// /**
	// *
	// * 描述：从BOTP规则中提取来源单据ListUI的Query 过滤EntityViewInfo
	// *
	// * @param botMappingInfo 当前的BOTP转换规则
	// * @return EntityViewInfo
	// * @throws ParserException
	// * @throws BOSException
	// * @author:daij
	// * 创建时间：2007-1-9 <p>
	// */
	// public static EntityViewInfo getQueryViewInfoFromBotMapping(
	// BOTMappingInfo botMappingInfo) throws ParserException, BOSException {
	//		
	// if(botMappingInfo == null || botMappingInfo.getExtRule() == null) return
	// null;
	//		
	// EntityViewInfo view = null;
	//		
	// //规则过滤条件
	// EntityViewInfo ruleView = null;
	// if(botMappingInfo.getExtRule().getFilter() != null &&
	// botMappingInfo.getExtRule().getFilter().length() > 0){
	// ruleView = new EntityViewInfo(
	// botMappingInfo.getExtRule().getFilter());
	// }
	//        
	// //数据过滤条件
	// EntityViewInfo dataView = null;
	// if(botMappingInfo.getExtRule().getDataFilter() != null &&
	// botMappingInfo.getExtRule().getDataFilter().length() > 0){
	// dataView = new
	// EntityViewInfo(botMappingInfo.getExtRule().getDataFilter());
	// }
	//        
	// //合并规则和数据过滤
	// view = mergeBOTPRuleAndDataFilter(ruleView, dataView);
	//        
	// return view;
	// }
	//
	// /**
	// *
	// * 描述：合并BOTP的数据和规则过滤Filter.
	// *
	// * @param filter 规则过滤
	// * @param dataFilter 数据过滤
	// * @return EntityViewInfo
	// * @throws BOSException
	// * @author:daij
	// * 创建时间：2007-1-9 <p>
	// */
	// public static EntityViewInfo mergeBOTPRuleAndDataFilter(
	// EntityViewInfo filter,
	// EntityViewInfo dataFilter) throws BOSException {
	//		
	// EntityViewInfo botpFilter = new EntityViewInfo();
	// if(filter == null){
	// botpFilter = dataFilter;
	// }
	// else if(dataFilter == null){
	// botpFilter = filter;
	// }
	// else{
	// //首先merge单据头filter
	// FilterInfo billFilter = null;
	// if(filter.getFilter() == null ||
	// filter.getFilter().getFilterItems().size() == 0){
	// billFilter = dataFilter.getFilter();
	// }
	// else if(dataFilter.getFilter() == null ||
	// dataFilter.getFilter().getFilterItems().size() == 0){
	// billFilter = filter.getFilter();
	// }
	// else{
	// billFilter = filter.getFilter();
	// billFilter.mergeFilter(dataFilter.getFilter(), "and");
	// }
	// if(billFilter == null){
	// billFilter = new FilterInfo();
	// }
	// botpFilter.setFilter(billFilter);
	// //merge单据分录entryFilter
	// FilterCollection entryFilterCols = null;
	// if(filter.getEntryFilters() == null && filter.getEntryFilters().size() ==
	// 0){
	// entryFilterCols = dataFilter.getEntryFilters();
	// }
	// else if(dataFilter.getEntryFilters() == null ||
	// dataFilter.getEntryFilters().size() == 0){
	// entryFilterCols = filter.getEntryFilters();
	// }
	// else{
	// entryFilterCols = new FilterCollection();
	// entryFilterCols.addObjectCollection(filter.getEntryFilters());
	// entryFilterCols.addObjectCollection(dataFilter.getEntryFilters());
	// }
	// if(entryFilterCols != null && entryFilterCols.size() > 0){
	// botpFilter.getEntryFilters().addObjectCollection(entryFilterCols);
	// }
	//
	// }
	// if(botpFilter == null){
	// botpFilter = new EntityViewInfo();
	// }
	// if(botpFilter.getFilter() == null){
	// //由于老宋没有处理,此处默认Filter不为空
	// FilterInfo defaultFilter = new FilterInfo();
	// botpFilter.setFilter(defaultFilter);
	// }
	// if(botpFilter.getEntryFilters() != null &&
	// botpFilter.getEntryFilters().size() > 0){
	// FilterCollection entryFilterCols = botpFilter.getEntryFilters();
	// FilterInfo billFilter = botpFilter.getFilter();
	// FilterInfo entryQueryFilter = new FilterInfo();
	// if(entryFilterCols != null && entryFilterCols.size() > 0){
	// //TODO 简单处理，entryName+filterName，完善的处理应该是根据query元数据设置
	// for(int i = 0, size = entryFilterCols.size(); i < size; i++){
	// FilterInfo entryFilterInfo = entryFilterCols.get(i);
	// if(entryFilterInfo != null && entryFilterInfo.getFilterItems().size() >
	// 0){
	// String entryName = entryFilterInfo.getEntryName();
	// for(int j = 0, count = entryFilterInfo.getFilterItems().size(); j <
	// count; j++){
	// entryQueryFilter.getFilterItems().add(new FilterItemInfo(entryName +'.' +
	// entryFilterInfo.getFilterItems().get(j).getPropertyName(),
	// entryFilterInfo.getFilterItems().get(j).getCompareValue(),
	// entryFilterInfo.getFilterItems().get(j).getCompareType()));
	// }
	//        				
	// //原来“拉”关联生成单据的构建规则过滤与数据过滤的算法有问题，没有设置MaskString的值，
	// //导致所有条件不管是or 还是and，都缺省设置为and，导致“拉”时过滤数据不正确。 ---by zfr
	// if(i == 0){
	// entryQueryFilter.setMaskString(entryFilterInfo.getMaskString());
	//            				
	// }
	// else {
	// entryQueryFilter.setMaskString(entryQueryFilter.getMaskString() + " and "
	// + entryFilterInfo.getMaskString());
	//        					
	// }
	//        				
	// }
	// }
	// }
	// if(entryQueryFilter.getFilterItems().size() > 0){
	// if(billFilter.getFilterItems().size() > 0){
	// billFilter.mergeFilter(entryQueryFilter, "and");
	// }
	// else{
	// billFilter = entryQueryFilter;
	// }
	// }
	// botpFilter.getEntryFilters().clear();
	// botpFilter.setFilter(billFilter);
	// }
	// return botpFilter;
	// }
	//	
	// private static void mergeOrgFilterAndSetSrcBizBillFilter(
	// EntityViewInfo view,
	// OrgUnitInfo ouInfo,
	// OrgType sourceBillMainBizOrgType,
	// String sourceBillMainBizOrgPropertyName) {
	//		
	// EntityViewInfo resultView = null;
	// if(view == null){
	// view = new EntityViewInfo();
	// view.setFilter(new FilterInfo());
	// }
	//    	
	// OrgType srcBillMainBizOrgType = OrgType.ControlUnit;
	// String srcBillMainBizOrgPropertyName = "CU";
	// if(sourceBillMainBizOrgType != null &&
	// !StringUtils.isEmpty(sourceBillMainBizOrgPropertyName)){
	// srcBillMainBizOrgType = sourceBillMainBizOrgType;
	// srcBillMainBizOrgPropertyName = sourceBillMainBizOrgPropertyName;
	// }
	//    	
	// //如果没有选择组织,则默认为当前业务组织
	// if(ouInfo == null){
	// ouInfo =
	// SysContext.getSysContext().getCurrentOrgUnit(srcBillMainBizOrgType);
	// }
	//    	
	// //补充组织过滤.
	// if(ouInfo != null && ouInfo.getId() != null){
	// FilterItemInfo ouFilterItemInfo = new FilterItemInfo(
	// srcBillMainBizOrgPropertyName + ".id", ouInfo.getId().toString());
	//    		
	// view.getFilter().getFilterItems().add(ouFilterItemInfo);
	//    		
	// if(!StringUtils.isEmpty(view.getFilter().getMaskString())){
	//    			
	// StringBuffer mask = new StringBuffer();
	// mask.append("(");
	// mask.append(resultView.getFilter().getMaskString());
	// mask.append(") and #");
	// mask.append(view.getFilter().getFilterItems().size() - 1);
	// view.getFilter().setMaskString(mask.toString());
	// }
	// }
	// }

		/**
		* 特别注意，botp应该设置为保存单据，不显示界面
		* @throws BOSException 
		* @throws BTPException 
		*/
		/**
		* @param ctx
		* @param botMappingID BOTP ID
		* @param srcBillIDs 来源单据ID数组
		* @param entriesNames 分录字段名，如果整单下推没有影响
		* @param entriesKeys 分录ID
		* @param srcBillBosType 来源单据BOStype
		* @param destBillBosType 目标单据BOSTYPE
		* @param botpSelectors BOTP过滤
		* @return BTPTransformResult
		* @throws BTPException
		* @throws BOSException
		*/
		public static BTPTransformResult autoBOTP(Context ctx,IObjectPK botMappingID,String[] srcBillIDs,
		String[] entriesNames,
		List entriesKeys,
		String srcBillBosType,
		String destBillBosType,
		SelectorItemCollection botpSelectors
		) throws BTPException, BOSException{
		//自动进行BOTP转换
		BTPTransformResult result = BTPManagerFactory.getLocalInstance(ctx).transformForBotp(
		srcBillIDs,
		entriesNames,
		entriesKeys,
		botpSelectors,
		destBillBosType,
		botMappingID,
		srcBillBosType);
		return result;
		//获取转换结果ID列表
		/*Iterator it = result.getBills().iterator();
		ArrayList allocationList = new ArrayList();
		while(it.hasNext()){
		//循环审核自动生成的单据ID
		}*/
		}

		}

	
	

	
	



