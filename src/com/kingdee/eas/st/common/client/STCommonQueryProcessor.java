/*
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.extendcontrols.KDBizComboBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.client.CommonQueryProcessor; //import com.kingdee.eas.basedata.mm.qm.client.SelectorListenerUtils_ST;
//import com.kingdee.eas.basedata.mm.qm.client.qiSchemeF7Utils.QISchemeF7Utils;
import com.kingdee.eas.basedata.mm.qm.utils.CSMF7Utils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgUnitRelation;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.StorageOrgUnitInfo;
import com.kingdee.eas.basedata.person.client.PersonF7UI;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.st.common.client.queryevent.AbstractQueryEvent;
import com.kingdee.eas.st.common.client.queryevent.AddressF7Event;
import com.kingdee.eas.st.common.client.queryevent.AsstActF7Event;
import com.kingdee.eas.st.common.client.queryevent.BoilerF7Event;
import com.kingdee.eas.st.common.client.queryevent.CustomerF7Event;
import com.kingdee.eas.st.common.client.queryevent.MaterialF7Event;
import com.kingdee.eas.st.common.client.queryevent.PersonF7Event;
import com.kingdee.eas.st.common.client.queryevent.ProductCheckTypeF7Event;
import com.kingdee.eas.st.common.client.queryevent.QIItemF7Event;
import com.kingdee.eas.st.common.client.queryevent.QISchemeF7Event;
import com.kingdee.eas.st.common.client.queryevent.QIStandardF7Event;
import com.kingdee.eas.st.common.client.queryevent.QiBizTypeF7Event;
import com.kingdee.eas.st.common.client.queryevent.SupplierF7Event;
import com.kingdee.eas.st.common.client.queryevent.WeighBillnumberF7Event;
import com.kingdee.util.StringUtils;

/**
 * ����:
 * 
 * @author daij date:2007-4-19
 *         <p>
 * @version EAS5.2
 */
public class STCommonQueryProcessor extends CommonQueryProcessor {

	private Component owner;

	/**
	 * �¼��б�.
	 */
	private Map events = new HashMap();

	/**
	 * ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.base.commonquery.client.IProcessor#process()
	 */
	public final void process() throws Exception {

		// ��ǰ�༭�Ĺ�������.
		String field = (String) get(CURRENT_FIELD_PROP);

		if (!StringUtils.isEmpty(field) && events.containsKey(field)) {
			Object o = events.get(field);
			if (o instanceof AbstractQueryEvent) {
				AbstractQueryEvent event = (AbstractQueryEvent) o;

				// ���õ�ǰ�ı༭��.
				event.setEditor(get(CURRENT_EDITOR));
				// ���ô�����
				event.setProcessor(this);
				// �����¼�����.
				event.fireEvent();
			}
		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param key
	 *            Query�е��ֶ�������
	 * @param event
	 *            �����Query�����¼�.
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void putQueryEvent(Object key, AbstractQueryEvent event) {
		events.put(key, event);
	}

	/**
	 * 
	 * ������
	 * 
	 * @param key
	 *            Query�е��ֶ�������
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void removeQueryEvent(Object key) {
		if (events.containsKey(key)) {
			events.remove(key);
		}
	}

	/**
	 * 
	 * ����������������F7����.
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(AsstActF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizComboBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// TODO ����������F7����.

		}
	}

	/**
	 * 
	 * ����������¯��F7����.
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(BoilerF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			setBoilerF7(f7);

		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(QIStandardF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// �ʼ��׼F7����.
			//SelectorListenerUtils_ST.attachPrmtF7UIWithQIStandardListUI(f7);//
			// ��ʱ����
		}
	}

	public void processQueryEditor(QISchemeF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// �ʼ췽��F7����.
			// QISchemeF7Utils.attachPrmtF7UI(f7);//��ʱ����
		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(QIItemF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// �ʼ���ĿF7����.
			// SelectorListenerUtils_ST.attachPrmtF7UIWithQIItemListUI(f7);
			// //��ʱ����

		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(MaterialF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();
			if (getOwner() instanceof Component) {
				// ����F7����.
				// colin update 2007-6-10 ��ֹ���˿�ѡ�����
				CSMF7Utils.setBizMaterialF7(f7, getOwner(), true, true);
			}
		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(CustomerF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// TODO �ͻ�F7����.
			if (e.getMainOrgUnit() != null) {
				OrgType orgType = e.getMainBizOrgType();
				OrgUnitInfo orgUnitInfo = null;
				OrgUnitInfo mainOrgInfo = e.getMainOrgUnit();
				if (mainOrgInfo instanceof StorageOrgUnitInfo) {

					try {
						IOrgUnitRelation iUnitRel = OrgUnitRelationFactory
								.getRemoteInstance();
						OrgUnitCollection orgCol = iUnitRel.getToUnit(
								mainOrgInfo.getId().toString(),
								OrgType.STORAGE_VALUE, OrgType.COMPANY_VALUE);
						if (orgCol != null && orgCol.size() > 0) {
							orgUnitInfo = (CompanyOrgUnitInfo) orgCol.get(0);
							orgType = OrgType.Company;
						}
					} catch (Exception e1) {
					}

				} else {
					orgUnitInfo = mainOrgInfo;
					if (mainOrgInfo instanceof SaleOrgUnitInfo) {
						orgType = OrgType.Sale;
					} else {
						orgType = OrgType.Company;
					}
				}

				CSMF7Utils.setBizCustomerTreeF7(f7, getOwner(), orgType,
						orgUnitInfo);
			}

		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(SupplierF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			// TODO ��Ӧ��F7����.
			if (e.getMainOrgUnit() != null) {
				OrgType orgType = e.getMainBizOrgType();
				OrgUnitInfo orgUnitInfo = null;
				OrgUnitInfo mainOrgInfo = e.getMainOrgUnit();
				if (mainOrgInfo instanceof StorageOrgUnitInfo) {

					try {
						IOrgUnitRelation iUnitRel = OrgUnitRelationFactory
								.getRemoteInstance();
						OrgUnitCollection orgCol = iUnitRel.getToUnit(
								mainOrgInfo.getId().toString(),
								OrgType.STORAGE_VALUE, OrgType.COMPANY_VALUE);
						if (orgCol != null && orgCol.size() > 0) {
							orgUnitInfo = (CompanyOrgUnitInfo) orgCol.get(0);
							orgType = OrgType.Company;
						}
					} catch (Exception e1) {
					}

				} else {
					orgUnitInfo = mainOrgInfo;
					if (mainOrgInfo instanceof SaleOrgUnitInfo) {
						orgType = OrgType.Sale;
					} else {
						orgType = OrgType.Company;
					}
				}

				CSMF7Utils.setBizSupplierTreeF7(f7, getOwner(), orgType,
						orgUnitInfo);
			}

		}
	}

	/**
	 * 
	 * ������
	 * 
	 * @param e
	 * @author:daij ����ʱ�䣺2007-4-19
	 *              <p>
	 */
	public void processQueryEditor(ProductCheckTypeF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			setBizProductCheckTypeF7(f7);

		}
	}

	private void setBizProductCheckTypeF7(KDBizPromptBox f7) {

		// ��Ʒ�������??
		String PRODUCT_GROUPID = "nUpGSwESEADgAACgwKgSzgXSzQw=";

		f7
				.setQueryInfo("com.kingdee.eas.basedata.master.auxacct.app.F7GeneralAsstActTypeQuery");

		// 过滤工序
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", "1", CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("group.id", PRODUCT_GROUPID,
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isLeaf", "0", CompareType.NOTEQUALS));

		viewInfo.setFilter(filter);
		f7.setEntityViewInfo(viewInfo);

		f7.setEditFormat("$number$");
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$number$");

	}

	private void setBoilerF7(KDBizPromptBox f7) {
		f7
				.setQueryInfo("com.kingdee.eas.st.produce.biz.app.F7BoilerNumberQuery");
		f7.setEditFormat("$number$");
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$number$");

	}

	public Component getOwner() {
		return owner;
	}

	public void setOwner(Component owner) {
		this.owner = owner;
	}

	public void processQueryEditor(QiBizTypeF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			setQiBizTypeF7(f7);

		}

	}

	private void setQiBizTypeF7(KDBizPromptBox f7) {
		// ��Ʒ�������??

		f7.setQueryInfo("com.kingdee.eas.basedata.mm.qm.app.QIBizTypeQuery");
		f7.setEditFormat("$name$");
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$name$");

	}

	// ����ۼ����ؼ����ﵥ��ѯ
	public void processQueryEditor(WeighBillnumberF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();

			setWeighBillnumberF7(f7);

		}

	}

	private void setWeighBillnumberF7(KDBizPromptBox f7) {
		// ��Ʒ�������??

		f7.setQueryInfo("com.kingdee.eas.st.weigh.app.WeighQuery");
		f7.setEditFormat("$number$");
		f7.setDisplayFormat("$number$");
		f7.setCommitFormat("$number$");

	}

	public void processQueryEditor(PersonF7Event e) {
		KDBizPromptBox f7 = null;
		if (e.getEditor() instanceof KDBizPromptBox) {
			f7 = (KDBizPromptBox) e.getEditor();
			setPersonF7(f7);
		}
	}

	private void setPersonF7(KDBizPromptBox box) {
		HashMap map = new HashMap();
		map.put(PersonF7UI.ALL_ADMIN, "YES");
		PersonPromptBox select = new PersonPromptBox(null, map);
		box.setSelector(select);
		select.setHasCUDefaultFilter(false);
		box.setHasCUDefaultFilter(false);
		box.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
	}

	public void processQueryEditor(AddressF7Event e) {
		KDBizPromptBox f7 = new KDBizPromptBox();
		setAddressF7(f7);
		e.setEditor(f7);

	}

	private void setAddressF7(KDBizPromptBox box) {
		box = new KDBizPromptBox();
		box.setQueryInfo("com.kingdee.eas.st.produce.biz.app.F7AddressQuery");
		box.setVisible(true);
		box.setEditable(true);
		box.setDisplayFormat("$name$");
		box.setEditFormat("$name$");
		box.setCommitFormat("$name$");
	}

}
