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
 * 用于处理与主业务组织相关的F7，如
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
	 * 添加处理器，默认会触发change事件
	 * 
	 * @author zhiwei_wang
	 * @date 2009-2-12
	 * @param processor
	 */
	public void addF7(IF7Processor processor) {
		addF7(processor, true);
	}

	/**
	 * 添加处理器
	 * 
	 * @author zhiwei_wang
	 * @date 2009-2-12
	 * @param processor
	 *            F7处理器
	 * @param isNeedChange
	 *            是否需要触发change事件，一般在界面初始化时需要为true
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
					// 如果不符合前置条件，则恢复原值并退出，不再执行后续操作
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
			// TODO 检查先决条件
			if (oldOrg == null) { // 如果旧的主业务组织为空，则不清除相关F7的值
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
