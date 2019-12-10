/*
 * @(#)BOTPUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:
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
	// * ������
	// *
	// * @param destBillBOSTypeString
	// * @param sourceBills
	// * @param owner
	// * @throws Exception
	// * @author:daij
	// * ����ʱ�䣺2007-1-8 <p>
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
	// //������Դ������Ϣ
	// CoreBillBaseCollection srcBillInfos =
	// (CoreBillBaseCollection)sourceBills.clone();
	//        
	// //��ȡBOTPӳ���ϵ.
	// BOTMappingInfo botMappingInfo =
	// BOTMappingFactory.getRemoteInstance().getMapping(
	// srcBillInfos,
	// destBillBOSTypeString,
	// DefineSysEnum.BTP);
	//    	
	// if(botMappingInfo != null &&
	// botMappingInfo.getId() != null){
	// //����������ʱ����ת��ǰ�ص�XXXListUI��beforeTransform������ҵ�񵥾ݼ����ʵ���ҵ���߼�У��.
	// if(owner instanceof CoreBillListUI){
	// ((CoreBillListUI)owner).beforeTransform(srcBillInfos,
	// destBillBOSTypeString);
	// }
	//    		
	// //��BOTPת����������Ŀ�굥��.
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
	// //�������Ҫ����ʾ���ɵ�Ŀ�굥�༭����.
	// if(botMappingInfo.isIsShowEditUI()){
	// //��Ŀ�굥ʵ����չ���Զ���EDITUI��ʾĿ�굥���༭.
	// String destBillEditUIClassName =
	// iBTPManager.getEntityObjectInfoExtendPro(
	// destBillBOSTypeString, "editUI");
	//                
	// //����Ƴ��˶��ŵ�����ҪTABҳǩ��ʾ.
	// String openType = null;
	// Map map = new UIContext(owner);
	// if(owner != null &&
	// (owner.getUIWindow() instanceof UINewTab)){
	// openType = UIFactoryName.NEWTAB;
	// } else{
	// openType = UIFactoryName.MODEL;
	// }
	//                
	// //��дUIContext
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
	// //�������Ҫ��ʾĿ�굥�༭����������ݴ�ת�������е�Ŀ�굥.
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
	// //[��һ��] ������Դ���ݼ���.
	// CoreBillBaseCollection srcBillCollection = null;
	// BOTMappingInfo botMappingInfo = getBOTPMappingInfoByBosTypeAndCU(
	// sourceBillBosTypeString,destBillBOSTypeString,orgUnit);
	//        
	// //���û��ָ����һԴ����SHOWԴ����ListUI���û�ѡ��.
	// if(sourceBill == null){
	// //SHOWԴ����ListUI���û�ѡ��
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
	// //�û�ѡ��ȷ�Ͻ���ת��.
	// if(!srcListUI.isCancel()){
	// srcBillCollection = srcListUI.getSrcBillCollection();
	// }
	// } else{
	// //���ָ���˵�һ��Դ��������ҪSHOWѡ��Դ����ListUI.
	// srcBillCollection = new CoreBillBaseCollection();
	// srcBillCollection.add(sourceBill);
	// }
	//  		
	// //[�ڶ���] ��ʼ����BOTPת��.
	// IObjectCollection destBillCols = null;
	// BOTRelationCollection botRelationCols = null;
	// IBTPManager iBTPManager = null;
	// if(srcBillCollection != null){
	//        	
	// //ת��ǰ������.
	// billEditUI.setMakeRelations(null);
	//            
	// if(botMappingInfo != null){
	// //��������Ƿ��ʺ���ѡ����
	// verifyBillAndBotMapping(
	// destBillBOSTypeString,
	// srcBillCollection,
	// new ObjectUuidPK(botMappingInfo.getId()));
	// iBTPManager = BTPManagerFactory.getRemoteInstance();
	// //ͨ��У��ͽ���BOTPת��
	// BTPTransformResult btpResult =
	// BTPManagerFactory.getRemoteInstance().transformForBotp(
	// srcBillCollection,
	// destBillBOSTypeString,
	// new ObjectUuidPK(botMappingInfo.getId()));
	//                
	// //����ת�����
	// destBillCols = btpResult.getBills();
	// botRelationCols = btpResult.getBOTRelationCollection();
	// }
	// }
	//        
	// //[������] ��Ŀ�굥��EditUIת��ת����Ľ��.
	// if (destBillCols != null && destBillCols.size() > 0) {
	// if (destBillCols.size() > 1) {
	// IIDList idList = RealModeIDList.getEmptyIDList();
	// for (int i = 0, count = destBillCols.size(); i < count; i++) {
	// CoreBillBaseInfo destBillInfo = (CoreBillBaseInfo) destBillCols
	// .getObject(i);
	// IMultiBillWorkAgent agent = new DefaultMultiBillWorkAgent();
	// agent.markBotpTransfer(destBillInfo, destBillInfo.getId()
	// .toString());
	// // �ݴ�
	// iBTPManager.saveRelations(destBillInfo, botRelationCols);
	// idList.add(destBillInfo.getId().toString());
	// }
	// // �ݴ�����editUI����
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
	// // ��С��Ҫ��������������
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
	// * ������
	// *
	// * @param sourceBillBosTypeString
	// * @param destBillBOSTypeString
	// * @param orgUnit
	// * @throws BOTPException
	// * @throws BOSException
	// * @author:daij
	// * ����ʱ�䣺2007-1-9 <p>
	// */
	// public static BOTMappingInfo getBOTPMappingInfoByBosTypeAndCU(
	// String sourceBillBosTypeString,
	// String destBillBOSTypeString,
	// OrgUnitInfo orgUnit) throws BOTPException, BOSException{
	// BOTMappingInfo botMappingInfo = null;
	//        
	// //����ҵ����֯��ȡ�����ڵ�CU���ڻ�ȡBOTP����.
	// if(orgUnit != null){
	// orgUnit = orgUnit.getCU();
	// }
	//        
	// Map transmitCtx = new HashMap();
	// transmitCtx.put(TRANSMITCU, orgUnit);
	// //���BOTP������Ϣ
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
	// * ������SHOW��Դ���ݵ�ListUI���û�ѡ������ת����Դ������.
	// *
	// * @param sourceBillBosTypeString ��Դ���ݵ�BOSType��,���ڻ�ȡԴ����Ϣ.
	// * @param destBillBOSTypeString Ŀ�굥�ݵ�BOSType��,���ڻ�ȡBOTP����.
	// * @param sourceBill ����ָ����Դ����. ��������Ȼ��������У���Ƿ���Ϲ���Ҫ��.
	// * @param uiContext UI������
	// * @param orgUnit ��ȡ����ʱ�Ĺ�����֯OU,��Ҫ����CU��Ϣ���ڻ�ȡ����ʱ����CU����.
	// * @param owner ��ʾListUI������.
	// * @param botMappingInfo ��ǰ����ת����BOTP����,��ΪNULL������������Sever��ȡһ�ι���.
	// * @throws EASBizException
	// * @throws Exception
	// * @author:daij
	// * ����ʱ�䣺2007-1-9 <p>
	// */
	// public static IUIWindow showSourceBillListUI(
	// IUIObject owner,
	// Map uiContext,
	// String sourceBillBosTypeString,
	// String destBillBOSTypeString,
	// OrgUnitInfo orgUnit,
	// BOTMappingInfo botMappingInfo) throws EASBizException, Exception{
	//    	
	// //���û�й���ӳ�������������Sever��ȡһ�ι���.
	// if(botMappingInfo == null){
	// botMappingInfo = getBOTPMappingInfoByBosTypeAndCU(
	// sourceBillBosTypeString,destBillBOSTypeString,orgUnit);
	// }
	//    	
	// IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	//        
	// //�������ݵ���Դ�����б�(XXXListUI)��UIContext,������XXXEditUI����Ĵ��뼶��������(����UIContext��
	// "BTPEDITPARAMETER"�ؼ�����).
	// HashMap uiCtx = new HashMap();
	//        
	// //������Դ����XXXListUI������.
	// uiCtx.put("Owner",owner);
	//        
	// //��XXXEditUI����Ĵ��뼶��������(����UIContext��"BTPEDITPARAMETER"�ؼ�����).
	// if(uiContext.get("BTPEDITPARAMETER") != null){
	// uiCtx.put("BTPEDITPARAMETER", uiContext.get("BTPEDITPARAMETER"));
	// }
	//        
	// //��BOTP������ȡ������Դ���ݵ�ViewInfo
	// EntityViewInfo botpView = getQueryViewInfoFromBotMapping(botMappingInfo);
	//        
	// //TODO û�м�OU����, Ӧ�ù��˳���ǰuser��Ȩ�޵���֯������Դ���б�??
	// // mergeOrgFilterAndSetSrcBizBillFilter();
	//    	
	// //��ȡBOTP�����еĹ�������.
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
	// * �жϵ�ǰ�����Ƿ��ʺ���ѡ����
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
	// * ��������BOTP��������ȡ��Դ����ListUI��Query ����EntityViewInfo
	// *
	// * @param botMappingInfo ��ǰ��BOTPת������
	// * @return EntityViewInfo
	// * @throws ParserException
	// * @throws BOSException
	// * @author:daij
	// * ����ʱ�䣺2007-1-9 <p>
	// */
	// public static EntityViewInfo getQueryViewInfoFromBotMapping(
	// BOTMappingInfo botMappingInfo) throws ParserException, BOSException {
	//		
	// if(botMappingInfo == null || botMappingInfo.getExtRule() == null) return
	// null;
	//		
	// EntityViewInfo view = null;
	//		
	// //�����������
	// EntityViewInfo ruleView = null;
	// if(botMappingInfo.getExtRule().getFilter() != null &&
	// botMappingInfo.getExtRule().getFilter().length() > 0){
	// ruleView = new EntityViewInfo(
	// botMappingInfo.getExtRule().getFilter());
	// }
	//        
	// //���ݹ�������
	// EntityViewInfo dataView = null;
	// if(botMappingInfo.getExtRule().getDataFilter() != null &&
	// botMappingInfo.getExtRule().getDataFilter().length() > 0){
	// dataView = new
	// EntityViewInfo(botMappingInfo.getExtRule().getDataFilter());
	// }
	//        
	// //�ϲ���������ݹ���
	// view = mergeBOTPRuleAndDataFilter(ruleView, dataView);
	//        
	// return view;
	// }
	//
	// /**
	// *
	// * �������ϲ�BOTP�����ݺ͹������Filter.
	// *
	// * @param filter �������
	// * @param dataFilter ���ݹ���
	// * @return EntityViewInfo
	// * @throws BOSException
	// * @author:daij
	// * ����ʱ�䣺2007-1-9 <p>
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
	// //����merge����ͷfilter
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
	// //merge���ݷ�¼entryFilter
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
	// //��������û�д���,�˴�Ĭ��Filter��Ϊ��
	// FilterInfo defaultFilter = new FilterInfo();
	// botpFilter.setFilter(defaultFilter);
	// }
	// if(botpFilter.getEntryFilters() != null &&
	// botpFilter.getEntryFilters().size() > 0){
	// FilterCollection entryFilterCols = botpFilter.getEntryFilters();
	// FilterInfo billFilter = botpFilter.getFilter();
	// FilterInfo entryQueryFilter = new FilterInfo();
	// if(entryFilterCols != null && entryFilterCols.size() > 0){
	// //TODO �򵥴���entryName+filterName�����ƵĴ���Ӧ���Ǹ���queryԪ��������
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
	// //ԭ���������������ɵ��ݵĹ���������������ݹ��˵��㷨�����⣬û������MaskString��ֵ��
	// //������������������or ����and����ȱʡ����Ϊand�����¡�����ʱ�������ݲ���ȷ�� ---by zfr
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
	// //���û��ѡ����֯,��Ĭ��Ϊ��ǰҵ����֯
	// if(ouInfo == null){
	// ouInfo =
	// SysContext.getSysContext().getCurrentOrgUnit(srcBillMainBizOrgType);
	// }
	//    	
	// //������֯����.
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
		* �ر�ע�⣬botpӦ������Ϊ���浥�ݣ�����ʾ����
		* @throws BOSException 
		* @throws BTPException 
		*/
		/**
		* @param ctx
		* @param botMappingID BOTP ID
		* @param srcBillIDs ��Դ����ID����
		* @param entriesNames ��¼�ֶ����������������û��Ӱ��
		* @param entriesKeys ��¼ID
		* @param srcBillBosType ��Դ����BOStype
		* @param destBillBosType Ŀ�굥��BOSTYPE
		* @param botpSelectors BOTP����
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
		//�Զ�����BOTPת��
		BTPTransformResult result = BTPManagerFactory.getLocalInstance(ctx).transformForBotp(
		srcBillIDs,
		entriesNames,
		entriesKeys,
		botpSelectors,
		destBillBosType,
		botMappingID,
		srcBillBosType);
		return result;
		//��ȡת�����ID�б�
		/*Iterator it = result.getBills().iterator();
		ArrayList allocationList = new ArrayList();
		while(it.hasNext()){
		//ѭ������Զ����ɵĵ���ID
		}*/
		}

		}

	
	

	
	



