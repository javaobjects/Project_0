package com.kingdee.eas.st.common.client.mainorgf7;

import java.util.ArrayList;
import java.util.Iterator;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;

/**
 * ���ڴ�������ҵ����֯��ص�F7����
 * 
 * @author zhiwei_wang
 * 
 */
public class MainOrgUnitF7Manager {
	private KDBizPromptBox mainOrgUnit;
	private OrgType orgType;
	private ArrayList processors = new ArrayList();
	private IUserChangeProcessor[] userProcessor = new IUserChangeProcessor[0];
	private DataChangeListener listener = null;

	private MainOrgUnitF7Manager(KDBizPromptBox mainOrgF7, OrgType orgType) {
		this(mainOrgF7, orgType, null);
	}

	private MainOrgUnitF7Manager(KDBizPromptBox mainOrgF7, OrgType orgType,
			IUserChangeProcessor[] userProcessor) {
		this.mainOrgUnit = mainOrgF7;
		this.orgType = orgType;
		this.userProcessor = userProcessor;

		listener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				if (STQMUtils.isDiffrent(e.getOldValue(), e.getNewValue())) {
					mainOrgChanged(MainOrgUnitF7Manager.this.mainOrgUnit,
							(OrgUnitInfo) e.getOldValue(), (OrgUnitInfo) e
									.getNewValue());
				}
			}
		};
		mainOrgUnit.addDataChangeListener(listener);

	}

	/**
	 * ��Ӵ�������Ĭ�ϻᴥ��change�¼�
	 * 
	 * @author zhiwei_wang
	 * @date 2009-2-12
	 * @param processor
	 */
	public void addF7(IF7Processor processor) {
		addF7(processor, true);
	}

	/**
	 * ��Ӵ�����
	 * 
	 * @author zhiwei_wang
	 * @date 2009-2-12
	 * @param processor
	 *            F7������
	 * @param isNeedChange
	 *            �Ƿ���Ҫ����change�¼���һ���ڽ����ʼ��ʱ��ҪΪtrue
	 */
	public void addF7(IF7Processor processor, boolean isNeedChange) {
		processors.add(processor);
		if (isNeedChange) {
			processor.mainOrgChanged(null,
					(OrgUnitInfo) mainOrgUnit.getValue(), orgType, false);
		}
	}

	public static MainOrgUnitF7Manager getInstance(KDBizPromptBox mainOrgF7,
			OrgType orgType) {
		MainOrgUnitF7Manager manager = new MainOrgUnitF7Manager(mainOrgF7,
				orgType);
		return manager;
	}

	public static MainOrgUnitF7Manager getInstance(KDBizPromptBox mainOrgF7,
			OrgType orgType, IUserChangeProcessor[] userProcessor) {
		MainOrgUnitF7Manager manager = new MainOrgUnitF7Manager(mainOrgF7,
				orgType, userProcessor);
		return manager;
	}

	private void mainOrgChanged(KDBizPromptBox mainOrgF7, OrgUnitInfo oldOrg,
			OrgUnitInfo newOrg) {
		if (userProcessor != null) {
			for (int i = 0; i < userProcessor.length; i++) {
				if (!userProcessor[i].beforeMainOrgUnitChanged(mainOrgF7,
						oldOrg, newOrg)) {
					// ���������ǰ����������ָ�ԭֵ���˳�������ִ�к�������
					mainOrgF7.removeDataChangeListener(listener);
					mainOrgF7.setValue(oldOrg);
					mainOrgUnit.addDataChangeListener(listener);
					return;
				}
			}
		}

		Iterator it = processors.iterator();
		while (it.hasNext()) {
			IF7Processor processor = (IF7Processor) it.next();
			// TODO ����Ⱦ�����
			if (oldOrg == null) { // ����ɵ���ҵ����֯Ϊ�գ���������F7��ֵ
				processor.mainOrgChanged(oldOrg, newOrg, orgType, false);
			} else {
				processor.mainOrgChanged(oldOrg, newOrg, orgType, true);
			}
		}

		if (userProcessor != null) {
			for (int i = 0; i < userProcessor.length; i++) {
				userProcessor[i].afterMainOrgUnitChanged(mainOrgF7, oldOrg,
						newOrg);
			}
		}
	}

	public void removeF7(IF7Processor processor) {
		processors.remove(processor);
	}
}
