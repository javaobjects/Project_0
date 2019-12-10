package com.kingdee.eas.st.common.listenerutils.listener.F7Relation.impl;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.st.common.listenerutils.base.BaseDataListener;
import com.kingdee.eas.util.client.MsgBox;

public class F7MoveTogetherListener extends BaseDataListener implements
		DataChangeListener {
	protected KDBizPromptBox sor = null;
	protected String[] properties = null;
	protected Object[] components = null;

	public F7MoveTogetherListener(KDBizPromptBox sor, String[] properties,
			Object[] components) {
		this.sor = sor;
		this.properties = properties;
		this.components = components;
	}

	public void dataChanged(DataChangeEvent obj) {
		if (properties.length != components.length) {
			MsgBox.showWarning("数组参数properties，components长度必须一致！");
			return;
		}
		try {
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof KDTextField) {// 单文本
					((KDTextField) components[i]).setText(UIRuleUtil
							.getString(UIRuleUtil.getProperty(
									(IObjectValue) obj.getNewValue(),
									properties[i])));
				} else if (components[i] instanceof KDTextArea) {// 多文本
					((KDTextArea) components[i]).setText(UIRuleUtil
							.getString(UIRuleUtil.getProperty(
									(IObjectValue) obj.getNewValue(),
									properties[i])));
				} else if (components[i] instanceof KDBizPromptBox) {// F7
					((KDBizPromptBox) components[i]).setData(UIRuleUtil
							.getProperty((IObjectValue) obj.getNewValue(),
									properties[i]));
				} else if (components[i] instanceof KDBizMultiLangBox) {
					((KDBizMultiLangBox) components[i])
							.setDefaultLangItemData(UIRuleUtil
									.getString(UIRuleUtil.getProperty(
											(IObjectValue) sor.getData(),
											properties[i])));
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public Object excute() {
		if (properties.length != components.length) {
			MsgBox.showWarning("数组参数properties，components长度必须一致！");
			return null;
		}
		try {
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof KDTextField) {// 单文本
					((KDTextField) components[i]).setText(UIRuleUtil
							.getString(UIRuleUtil
									.getProperty((IObjectValue) sor.getData(),
											properties[i])));
				} else if (components[i] instanceof KDTextArea) {// 多文本
					((KDTextArea) components[i]).setText(UIRuleUtil
							.getString(UIRuleUtil
									.getProperty((IObjectValue) sor.getData(),
											properties[i])));
				} else if (components[i] instanceof KDBizPromptBox) {// F7
					((KDBizPromptBox) components[i]).setData(UIRuleUtil
							.getProperty((IObjectValue) sor.getData(),
									properties[i]));
				} else if (components[i] instanceof KDBizMultiLangBox) {
					((KDBizMultiLangBox) components[i])
							.setDefaultLangItemData(UIRuleUtil
									.getString(UIRuleUtil.getProperty(
											(IObjectValue) sor.getData(),
											properties[i])));
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		sor.addDataChangeListener(this);
		return this;
	}
}
