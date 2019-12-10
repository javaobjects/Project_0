/*
 * @(#)OrgUnitClientUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.MultiOUs4OrgFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitDelegationFilterInfoProducer;
import com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.DelegationPartsEnum;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.IAdminOrgUnit;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.IPurchaseOrgUnit;
import com.kingdee.eas.basedata.org.IQualityOrgUnit;
import com.kingdee.eas.basedata.org.ISaleOrgUnit;
import com.kingdee.eas.basedata.org.IStorageOrgUnit;
import com.kingdee.eas.basedata.org.ITransportOrgUnit;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.QualityOrgUnitCollection;
import com.kingdee.eas.basedata.org.QualityOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitCollection;
import com.kingdee.eas.basedata.org.StorageOrgUnitFactory;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.org.TransportOrgUnitCollection;
import com.kingdee.eas.basedata.org.TransportOrgUnitFactory;
import com.kingdee.eas.basedata.org.client.f7.OrgF7QueryStringConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.scm.common.client.SCMGroupClientUtils;
import com.kingdee.eas.st.common.ISTBillBase;
import com.kingdee.eas.st.common.client.utils.SortUtil;
import com.kingdee.eas.st.common.util.OrgUnitUtils;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.util.StringUtils;

/**
 * ����: ��֯��Ԫ�ͻ��˹�����.
 * 
 * @author daij date:2007-1-10
 *         <p>
 * @version EAS5.2
 */
public abstract class OrgUnitClientUtils {

	public static Map orgF7Query = null;

	static {
		orgF7Query = new HashMap();

		orgF7Query.put(OrgType.Storage, OrgF7QueryStringConstants.STORAGE);

		orgF7Query.put(OrgType.Purchase, OrgF7QueryStringConstants.PURCHASE);

		orgF7Query.put(OrgType.Sale, OrgF7QueryStringConstants.SALE);

		orgF7Query.put(OrgType.Company, OrgF7QueryStringConstants.COMPANY);

		orgF7Query.put(OrgType.Admin, OrgF7QueryStringConstants.ADMIN);

		orgF7Query.put(OrgType.Quality,
				"com.kingdee.eas.st.common.app.QualityOrgUnitQuery");
	}

	public static void setF7ByDelegation(KDBizPromptBox f7,
			OrgUnitInfo mainOrg, int fromType, int toType) throws Exception {
		setF7ByDelegation(new KDBizPromptBox[] { f7 }, mainOrg, fromType,
				toType);
	}

	public static void setF7ByDelegation(KDBizPromptBox[] f7s,
			OrgUnitInfo mainOrg, int fromType, int toType) throws Exception {
		if (f7s == null || f7s.length == 0) {
			return;
		}
		if (mainOrg == null || mainOrg.getId() == null) {
			for (int i = 0, size = f7s.length; i < size; i++) {
				f7s[i].setValue(null);
			}
			return;
		}
		IOrgUnitRelation iUnitRel = OrgUnitRelationFactory.getRemoteInstance();
		OrgUnitCollection orgCol = iUnitRel.getToUnit(mainOrg.getId()
				.toString(), fromType, toType);
		// Map ls = new HashMap();
		// for(int i= 0; i< orgCol.size(); i++){
		// OrgUnitInfo toOrgInfo = (OrgUnitInfo)orgCol.get(i);
		// if(toOrgInfo == null || toOrgInfo.getId() == null){
		// continue;
		// }
		// ls.put(toOrgInfo.getId(),toOrgInfo);
		// }
		// û��ί�е���֯
		if (orgCol.size() == 0) {
			for (int j = 0, size = f7s.length; j < size; j++) {
				f7s[j].setValue(null);
			}
		} else {
			OrgUnitInfo org = orgCol.get(0);
			for (int j = 0, size = f7s.length; j < size; j++) {
				f7s[j].setValue(org);
			}
		}

		// û��ί�е���֯
		// if(orgCol.size() == 0){
		// Object o = null;
		// for(int j = 0, size = f7s.length; j < size; j++){
		// if(f7s[j] != null
		// && f7s[j].getValue() instanceof IObjectValue){
		// o =((IObjectValue)f7s[j].getValue()).get("id");
		// if(o instanceof BOSUuid){
		// if(!ls.containsKey((BOSUuid)o)){
		// f7s[j].setValue(null);
		// }
		// }
		// }
		// }
		// return;
		// }

		// if(f7s.length > 0){
		// Object o = null;
		// OrgUnitInfo org = orgCol.get(0);
		// for(int j = 0, size = f7s.length; j < size; j++){
		// if(f7s[j] != null
		// && f7s[j].getValue() instanceof IObjectValue){
		// o =((IObjectValue)f7s[j].getValue()).get("id");
		// if(o instanceof BOSUuid){
		// if(!ls.containsKey((BOSUuid)o)){
		// f7s[j].setValue(org);
		// }
		// }
		// }
		// }
		// }
	}

	/**
	 * 
	 * ������Ϊ��֯����F7���ӡ�������֯������.
	 * 
	 * @param bizBox
	 *            bizBox��һ��ѡ����֯��F7
	 * @author:daij ����ʱ�䣺2007-1-9
	 *              <p>
	 */
	public static void setOrgF7IsEntity(KDBizPromptBox bizBox) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		if (STQMUtils.isNotNull(bizBox)
				&& STQMUtils.isNotNull(bizBox.getEntityViewInfo())) {
			viewInfo = bizBox.getEntityViewInfo();
		}

		FilterInfo filterInfo = new FilterInfo();
		if (STQMUtils.isNull(viewInfo.getFilter())) {
			viewInfo.setFilter(filterInfo);
		}
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isBizUnit", Boolean.TRUE,
						CompareType.EQUALS));
		bizBox.setEntityViewInfo(viewInfo);
	}

	/**
	 * 
	 * ������������ҵ����֯F7 ע��: ���˳���ǰ�û���֯��Χ�ڵ���֯��Ԫ.
	 * 
	 * @param bizOrgUnitBox
	 * @param orgType
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static void setMainBizOrgF7(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType) {
		setMainBizOrgF7(bizOrgUnitBox, orgType, null);
	}

	/**
	 * 
	 * ������������ҵ����֯F7 ע��: ���˳���ǰ�û���֯��Χ�ھ߱�ĳ������Ȩ�޵���֯��Ԫ.
	 * 
	 * @param bizOrgUnitBox
	 *            F7�ؼ�
	 * @param orgType
	 *            ��ҵ����֯����
	 * @param permissionItemKeyString
	 *            Ȩ����ؼ��ִ�
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static void setMainBizOrgF7(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, String[] permissionItemKeyString) {

		// ��ȡ������֯���͵�ר��F7 Producer(Ĭ���ǰ�User��֯��Χ����)
		OrgUnitFilterInfoProducer iProducer = (OrgUnitFilterInfoProducer) FilterInfoProducerFactory
				.getOrgUnitFilterInfoProducer(orgType);
		if (iProducer.getModel() != null) {
			// TODO ֻ��ʾʵ����֯
			iProducer.getModel().setShowVirtual(false);

			// ����Ȩ�������.
			if (STQMUtils.isNotNull(permissionItemKeyString)
					&& permissionItemKeyString.length > 0) {

				iProducer.getModel().setPermissionItem(
						permissionItemKeyString[0]);
			}
		}
		bizOrgUnitBox.setFilterInfoProducer(iProducer);

		// Name��ʾ
		bizOrgUnitBox.setDisplayFormat("$name$");
		// Number�༭
		bizOrgUnitBox.setEditFormat("$number$");
		// ֧��������
		bizOrgUnitBox.setCommitFormat("$number$;$code$");
	}

	/**
	 * 
	 * ����������ҵ����֯F7 ע��: ����ҵ��ҵ����֯����ҵ����֯��ί�й�ϵ����F7
	 * 
	 * @param bizOrgUnitBox
	 *            F7�ؼ�.
	 * @param orgType
	 *            �����õ�ҵ����֯����
	 * @param mainOrgType
	 *            ��ҵ����֯����
	 * @param isSingleMainOrg
	 *            �Ƿ���ҵ����֯.
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static void setBizOrgF7ByDelegation(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, OrgType mainOrgType, OrgUnitInfo mainOrgUnitInfo,
			boolean isSingleMainOrg) {
		if (STQMUtils.isNull(bizOrgUnitBox) || STQMUtils.isNull(bizOrgUnitBox)
				|| STQMUtils.isNull(mainOrgType)) {
			return;
		}

		// ����֯���ͻ�ȡF7Query
		if (orgF7Query.containsKey(orgType)) {
			// Name��ʾ
			bizOrgUnitBox.setDisplayFormat("$name$");
			// Number�༭
			bizOrgUnitBox.setEditFormat("$number$");
			// ֧��������
			bizOrgUnitBox.setCommitFormat("$number$;$code$");
			// bizOrgUnitBox.setSelector(null);
			bizOrgUnitBox.setEntityViewInfo(null);
			// F7��ѯQuery��
			bizOrgUnitBox.setQueryInfo(orgF7Query.get(orgType).toString());

			// ���ø���֯��������ҵ����֯��ί�й�ϵ����
			bizOrgUnitBox.setFilterInfoProducer(getOrgUnitProducer(orgType,
					mainOrgType, isSingleMainOrg));

			// ���˳��뵱ǰ����ҵ����֯�߱�ί�й�ϵ��ҵ����֯.
			if (STQMUtils.isNotNull(mainOrgUnitInfo)) {
				bizOrgUnitBox.setCurrentMainBizOrgUnit(mainOrgUnitInfo,
						mainOrgType);
			}
		}
	}

	/**
	 * 
	 * ����������ҵ����֯F7 ע��: ����ҵ��ҵ����֯����ҵ����֯�ķ���ί�й�ϵ����F7
	 * 
	 * @param bizOrgUnitBox
	 *            F7�ؼ�.
	 * @param orgType
	 *            �����õ�ҵ����֯����
	 * @param mainOrgType
	 *            ��ҵ����֯����
	 * @param isSingleMainOrg
	 *            �Ƿ���ҵ����֯.
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static void setBizOrgF7ByFromUnit(KDBizPromptBox bizOrgUnitBox,
			OrgType orgType, OrgType mainOrgType, OrgUnitInfo mainOrgUnitInfo,
			boolean isSingleMainOrg) {
		if (STQMUtils.isNull(bizOrgUnitBox) || STQMUtils.isNull(bizOrgUnitBox)
				|| STQMUtils.isNull(mainOrgType)) {
			return;
		}

		// ����֯���ͻ�ȡF7Query
		if (orgF7Query.containsKey(orgType)) {
			// Name��ʾ
			bizOrgUnitBox.setDisplayFormat("$name$");
			// Number�༭
			bizOrgUnitBox.setEditFormat("$number$");
			// ֧��������
			bizOrgUnitBox.setCommitFormat("$number$;$code$");
			bizOrgUnitBox.setSelector(null);
			bizOrgUnitBox.setEntityViewInfo(null);
			// F7��ѯQuery��
			bizOrgUnitBox.setQueryInfo(orgF7Query.get(orgType).toString());

			// ���ø���֯��������ҵ����֯��ί�й�ϵ����
			bizOrgUnitBox.setFilterInfoProducer(getFromOrgUnitProducer(orgType,
					mainOrgType, isSingleMainOrg));

			// ���˳��뵱ǰ����ҵ����֯�߱�ί�й�ϵ��ҵ����֯.
			if (STQMUtils.isNotNull(mainOrgUnitInfo)) {
				bizOrgUnitBox.setCurrentMainBizOrgUnit(mainOrgUnitInfo,
						mainOrgType);
			}
		}
	}

	/**
	 * 
	 * ����������F7����֯����ί�й�ϵ��������
	 * 
	 * @param orgType
	 *            ��֯����
	 * @param mainOrgType
	 *            ��ҵ����֯����
	 * @param isSingleMainOrg
	 *            �Ƿ�һ��ҵ����֯.
	 * @return IFilterInfoProducer
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static IFilterInfoProducer getOrgUnitProducer(OrgType orgType,
			OrgType mainOrgType, boolean isSingleMainOrg) {
		if (isSingleMainOrg) {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getOrgUnitDelegationFilterInfoProducer(OrgUnitUtils
							.getDelegationDirection(orgType, mainOrgType),
							orgType);
			OrgUnitDelegationFilterInfoProducer producer = (OrgUnitDelegationFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		} else {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getMultiOUs4OrgFilterInfoProducer(null, mainOrgType,
							OrgUnitUtils.getDelegationDirection(orgType,
									mainOrgType), orgType);
			MultiOUs4OrgFilterInfoProducer producer = (MultiOUs4OrgFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		}

	}

	/**
	 * 
	 * ����������F7����֯����ί�й�ϵ��������
	 * 
	 * @param orgType
	 *            ��֯����
	 * @param mainOrgType
	 *            ��ҵ����֯����
	 * @param isSingleMainOrg
	 *            �Ƿ�һ��ҵ����֯.
	 * @return IFilterInfoProducer
	 * @author:daij ����ʱ�䣺2007-1-26
	 *              <p>
	 */
	public static IFilterInfoProducer getFromOrgUnitProducer(OrgType orgType,
			OrgType mainOrgType, boolean isSingleMainOrg) {
		if (isSingleMainOrg) {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getOrgUnitDelegationFilterInfoProducer(
							DelegationPartsEnum.THE_FROM_UNIT, orgType);
			OrgUnitDelegationFilterInfoProducer producer = (OrgUnitDelegationFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		} else {
			IFilterInfoProducer iProducer = FilterInfoProducerFactory
					.getMultiOUs4OrgFilterInfoProducer(null, mainOrgType,
							DelegationPartsEnum.THE_FROM_UNIT, orgType);
			MultiOUs4OrgFilterInfoProducer producer = (MultiOUs4OrgFilterInfoProducer) iProducer;
			producer.getModel().setDoNotUseUserOrgRangeAsFilter();
			return producer;
		}

	}

	/**
	 * 
	 * ������ȡ��ǰ�û���Ȩ�޵�Ĭ��ҵ��������֯. ȡ������: 1. ָ���û�,Ĭ��Ϊ��ǰ�û�. 2. ָ��ҵ����֯����,��ȡʵ����֯ 3.
	 * ͨ��Ȩ����ؼ���ָ���û���Ȩ�޵���֯��Χ. 4. ����1,2,3��������֯�ж������֯��������ȡtop1.
	 * 
	 * @param orgType
	 *            ��֯����
	 * @param permissionItem
	 *            Ȩ����
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-9-28
	 *              <p>
	 */
	public static OrgUnitInfo getDefaultBizOrgUnitInfo(OrgType orgType,
			String permissionItem) throws EASBizException, BOSException {
		return getDefaultBizOrgUnitInfo(null, orgType, permissionItem);
	}

	/**
	 * 
	 * ������ȡ�û���Ȩ�޵�Ĭ��ҵ��������֯. ȡ������: 1. ָ���û�,Ĭ��Ϊ��ǰ�û�. 2. ָ��ҵ����֯����,��ȡʵ����֯ 3.
	 * ͨ��Ȩ����ؼ���ָ���û���Ȩ�޵���֯��Χ. 4. ����1,2,3��������֯�ж������֯��������ȡtop1.
	 * 
	 * @param user
	 *            �û�, ����ΪNULL��ȡ��ǰ�û�
	 * @param orgType
	 *            ��֯����
	 * @param permissionItem
	 *            Ȩ����
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-9-28
	 *              <p>
	 */
	public static OrgUnitInfo getDefaultBizOrgUnitInfo(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {

		OrgUnitInfo orgUnit = null;
		if (STUtils.isNotNull(orgType) && !StringUtils.isEmpty(permissionItem)) {
			// û��ָ���û���ȡ��ǰĬ�ϵ��û�.
			if (STUtils.isNull(user)) {
				user = SysContext.getSysContext().getCurrentUserInfo();
			}

			if (STUtils.isNotNull(user)) {
				// ���û���ȡ��ĳ���Ȩ�޵�ҵ��������֯����(��������)
				FullOrgUnitCollection result = PermissionFactory
						.getRemoteInstance().getAuthorizedOrg(
								new ObjectUuidPK(user.getId()), orgType, null,
								permissionItem);
				// ��ҵ��������֯�����й��˳�ʵ����֯
				OrgUnitInfo[] mainOrgs = getOrgUnitInfosByType(result, orgType);
				if (STUtils.isNotNull(mainOrgs) && mainOrgs.length > 0) {
					// ��ҵ��ʵ����֯���������
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
					// ����top 1
					orgUnit = mainOrgs[0];
				}
			}
		}
		return orgUnit;
	}

	/**
	 * ������� ������ȡ�û���Ȩ�޵�Ĭ��ҵ��������֯. ȡ������: 1. ָ���û�,Ĭ��Ϊ��ǰ�û�. 2. ָ��ҵ����֯����,��ȡʵ����֯ 3.
	 * ͨ��Ȩ����ؼ���ָ���û���Ȩ�޵���֯��Χ.
	 * 
	 * 
	 * @param user
	 *            �û�, ����ΪNULL��ȡ��ǰ�û�
	 * @param orgType
	 *            ��֯����
	 * @param permissionItem
	 *            Ȩ����
	 * @return OrgUnitInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:guof ����ʱ�䣺2009-8-28
	 *              <p>
	 */
	public static OrgUnitInfo[] getBizOrgUnitInfo(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {

		OrgUnitInfo orgUnit[] = null;
		if (STUtils.isNotNull(orgType) && !StringUtils.isEmpty(permissionItem)) {
			// û��ָ���û���ȡ��ǰĬ�ϵ��û�.
			if (STUtils.isNull(user)) {
				user = SysContext.getSysContext().getCurrentUserInfo();
			}

			if (STUtils.isNotNull(user)) {
				// ���û���ȡ��ĳ���Ȩ�޵�ҵ��������֯����(��������)
				FullOrgUnitCollection result = PermissionFactory
						.getRemoteInstance().getAuthorizedOrg(
								new ObjectUuidPK(user.getId()), orgType, null,
								permissionItem);
				// ��ҵ��������֯�����й��˳�ʵ����֯
				OrgUnitInfo[] mainOrgs = getOrgUnitInfosByType(result, orgType);
				if (STUtils.isNotNull(mainOrgs) && mainOrgs.length > 0) {
					// ��ҵ��ʵ����֯���������
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
					// ����top 1
					orgUnit = mainOrgs;
				}
			}
		}
		return orgUnit;
	}

	/**
	 * ������ȡ�û���ǰCU����Ȩ�޵�Ĭ��ҵ��������֯��Ĭ��ֵΪ��ǰ��½��ҵ����֯. ȡ������: 1. ָ���û�,Ĭ��Ϊ��ǰ�û�. 2.
	 * ָ��ҵ����֯����,��ȡʵ����֯ 3. ͨ��Ȩ����ؼ���ָ���û���Ȩ�޵���֯��Χ.
	 * 
	 * @param user
	 * @param orgType
	 * @param permissionItem
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author jiwei_xiao
	 */
	public static OrgUnitInfo getDefaultOrgUnitInCU(UserInfo user,
			OrgType orgType, String permissionItem) throws EASBizException,
			BOSException {
		OrgUnitInfo[] orgUnit = null;
		orgUnit = OrgUnitClientUtils.getBizOrgUnitInfo(user, orgType,
				permissionItem);
		StorageOrgUnitInfo orgUnitInfo = SysContext.getSysContext()
				.getCurrentStorageUnit();
		if (orgUnit != null) {
			if (orgUnitInfo != null) {
				for (int i = 0; i < orgUnit.length; i++) {
					if (orgUnit[i].getId().toString().equals(
							orgUnitInfo.getId().toString())) {
						return orgUnit[i];
					}
				}
			}
			OrgUnitInfo currentBizOrg = (OrgUnitInfo) SysContext
					.getSysContext().getCurrentCtrlUnit();
			if (currentBizOrg != null) {
				for (int i = 0; i < orgUnit.length; i++) {
					if (orgUnit[i].getCU().getId().toString().equals(
							currentBizOrg.getId().toString())) {
						return orgUnit[i];
					}
				}
			}
		}
		return null;
	}

	/**
	 * ����ʵ����֯
	 * 
	 * @param userInfo
	 * @param orgUnitInfo
	 * @return
	 */
	public static OrgUnitInfo getBizOrgUnit(UserInfo userInfo, OrgType orgType,
			String permissionItem, OrgUnitInfo orgUnitInfo) {

		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// �����ʵ�壬ֱ�ӷ���
		if (STQMUtils.isNotNull(orgUnitInfo)) {
			if (orgUnitInfo.getBoolean("isBizUnit")) {
				bizSaleOrgUnitInfo = orgUnitInfo;
				return bizSaleOrgUnitInfo;
			}
		}

		// ����ʵ��������֯
		try {
			OrgUnitInfo[] mainOrgs = null;
			// TODO �ܷ��SCM���ã�����
			FullOrgUnitCollection collection = SCMGroupClientUtils
					.getAuthOrgByPermItem(new ObjectStringPK(userInfo.getId()
							.toString()), orgType, permissionItem);
			mainOrgs = getOrgUnitInfosByType(collection, orgType);
			if (mainOrgs == null) {
				mainOrgs = new OrgUnitInfo[] {};
				return null;
			}
			if (mainOrgs.length > 0) {
				mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(mainOrgs,
						true);
			}
			bizSaleOrgUnitInfo = mainOrgs[0];
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

		return bizSaleOrgUnitInfo;
	}

	public static OrgUnitInfo getBizOrgUnit(ICoreBase coreBase,
			UserInfo userInfo, OrgType orgType, String permissionItem,
			OrgUnitInfo orgUnitInfo) {

		// if (STQMUtils.isNull(orgUnitInfo)) {
		// return null;
		// }
		OrgUnitInfo bizSaleOrgUnitInfo = null;

		// �����ʵ�壬ֱ�ӷ���
		if (STQMUtils.isNotNull(orgUnitInfo)
				&& orgUnitInfo.getBoolean("isBizUnit")) {
			bizSaleOrgUnitInfo = orgUnitInfo;
		} else {
			// ����ʵ��������֯
			try {
				OrgUnitInfo[] mainOrgs = null;
				if (coreBase instanceof ISTBillBase) {
					try {

						ISTBillBase iSTBillBase = (ISTBillBase) coreBase;

						mainOrgs = iSTBillBase
								.getBizOrgUnit(new ObjectStringPK(userInfo
										.getId().toString()), orgType,
										permissionItem);

					} catch (Exception e) {
						ExceptionHandler.handle(e);
					}
				}

				if (mainOrgs == null) {
					mainOrgs = new OrgUnitInfo[] {};
					return null;
				}
				if (mainOrgs.length > 0) {
					mainOrgs = (OrgUnitInfo[]) SortUtil.sortDataByNumber(
							mainOrgs, true);
				}
				bizSaleOrgUnitInfo = mainOrgs[0];
			} catch (Exception e) {
				ExceptionHandler.handle(e);
			}
		}

		return bizSaleOrgUnitInfo;
	}

	public static OrgUnitInfo getBizOrgUnitInfo(ICoreBase coreBase,
			String addNewPermItemName, OrgType orgType, Context mainOrgContext) {

		if (StringUtils.isEmpty(addNewPermItemName)) {
			return null;
		}

		OrgUnitInfo orgUnitInfo = null;
		if (STQMUtils.isNotNull(mainOrgContext)) {
			orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);
		}
		if (orgUnitInfo == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit(orgType);
		}

		OrgUnitInfo retOrgUnitInfo = null;

		// �����Ȩ��ʵ����֯
		retOrgUnitInfo = getBizOrgUnit(coreBase, (UserInfo) (SysContext
				.getSysContext().getCurrentUserInfo()), orgType,
				addNewPermItemName, orgUnitInfo);

		return retOrgUnitInfo;

	}

	public static OrgUnitInfo getBizOrgUnitInfo(String addNewPermItemName,
			OrgType orgType, Context mainOrgContext) {

		if (STQMUtils.isNull(addNewPermItemName)
				|| STQMUtils.isNull(mainOrgContext)) {
			return null;
		}

		OrgUnitInfo orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);

		OrgUnitInfo retOrgUnitInfo = null;

		// �����Ȩ��ʵ����֯
		retOrgUnitInfo = getBizOrgUnit((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()), orgType, addNewPermItemName,
				orgUnitInfo);

		return retOrgUnitInfo;

	}

	public static OrgUnitInfo getBizOrgUnitInfo(String addNewPermItemName,
			OrgType orgType, OrgUnitInfo orgUnitInfo) {

		// ||
		// STQMUtils.isNull(orgUnitInfo)

		if (STQMUtils.isNull(addNewPermItemName)) {
			return null;
		}

		// OrgUnitInfo orgUnitInfo = (OrgUnitInfo) mainOrgContext.get(orgType);

		OrgUnitInfo retOrgUnitInfo = null;

		// �����Ȩ��ʵ����֯
		retOrgUnitInfo = getBizOrgUnit((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()), orgType, addNewPermItemName,
				orgUnitInfo);

		return retOrgUnitInfo;

	}

	/**
	 * ������������֯���ϣ��õ�ĳ���͵�ʵ����֯����
	 * 
	 * @param orgs
	 * @param orgType
	 * @return
	 * @author:paul ����ʱ�䣺2006-10-23
	 *              <p>
	 */
	public static OrgUnitInfo[] getOrgUnitInfosByType(
			FullOrgUnitCollection orgs, OrgType orgType) {
		OrgUnitInfo[] results = null;

		if (orgs == null || orgs.size() == 0) {
			return null;
		}

		LinkedHashSet keys = new LinkedHashSet();
		for (int i = 0; i < orgs.size(); i++) {
			keys.add(orgs.get(i).getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", keys, CompareType.INCLUDE));
		// ������֯������ʵ��֮��
		if (!orgType.equals(OrgType.Admin)) {
			filter.getFilterItems().add(
					new FilterItemInfo("isBizUnit", Boolean.valueOf(true),
							CompareType.EQUALS));
		}
		view.setFilter(filter);
		try {
			switch (orgType.getValue()) {
			case 0: // Admin
			{
				IAdminOrgUnit iAdmin = AdminOrgUnitFactory.getRemoteInstance();

				AdminOrgUnitCollection collection = iAdmin
						.getAdminOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}

			case 1: // Company
			{
				ICompanyOrgUnit iCompany = CompanyOrgUnitFactory
						.getRemoteInstance();
				CompanyOrgUnitCollection collection = iCompany
						.getCompanyOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 2: // Sale
			{
				ISaleOrgUnit iSale = SaleOrgUnitFactory.getRemoteInstance();
				SaleOrgUnitCollection collection = iSale
						.getSaleOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 3: // Purchase
			{
				IPurchaseOrgUnit iPurchase = PurchaseOrgUnitFactory
						.getRemoteInstance();
				PurchaseOrgUnitCollection collection = iPurchase
						.getPurchaseOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 4: // Storage
			{
				IStorageOrgUnit iStorage = StorageOrgUnitFactory
						.getRemoteInstance();
				StorageOrgUnitCollection collection = iStorage
						.getStorageOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 32: // Quality
			{
				IQualityOrgUnit iQuality = QualityOrgUnitFactory
						.getRemoteInstance();
				QualityOrgUnitCollection collection = iQuality
						.getQualityOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			case 24: // Transport
			{
				ITransportOrgUnit iTransport = TransportOrgUnitFactory
						.getRemoteInstance();
				TransportOrgUnitCollection collection = iTransport
						.getTransportOrgUnitCollection(view);
				if (collection != null && collection.size() > 0) {
					results = new OrgUnitInfo[collection.size()];
					for (int i = 0; i < collection.size(); i++) {
						results[i] = collection.get(i);
					}
				}

				return results;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
