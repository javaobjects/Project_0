/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.mm.qm.STBillBaseCodingRuleVo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.st.common.STTreeBaseDataTreeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class STTreeBaseDataTreeEditUI extends AbstractSTTreeBaseDataTreeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(STTreeBaseDataTreeEditUI.class);

	/** 编码规则缓存 */
	protected STBillBaseCodingRuleVo codingRuleVo = new STBillBaseCodingRuleVo();

	/**
	 * output class constructor
	 */
	public STTreeBaseDataTreeEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 是否先必须检查编码。
	 * 
	 */
	protected boolean isNeedCheckNumberFirst() {
		if (this.codingRuleVo.isExist() && !this.codingRuleVo.isAddView()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.st.common.STTreeBaseDataTreeFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.st.common.STTreeBaseDataTreeInfo objectValue = new com.kingdee.eas.st.common.STTreeBaseDataTreeInfo();

		return objectValue;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		if (STATUS_ADDNEW.equals(super.getOprtState())) {
			refreshUIState();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		try {
			super.actionSubmit_actionPerformed(e);
		} catch (EASBizException ex) {
			ExceptionHandler.handle(this, ex);

			// 编码为空的异常，提示后定位焦点
			if (ex.getMainCode().equals("40") && ex.getSubCode().equals("104")) {
				numberInput.requestFocus();
			}
			// 名称重复的异常，提示后定位焦点
			if (ex.getMainCode().equals("86") && ex.getSubCode().equals("007")) {
				nameInput.requestFocus();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		refreshUIState();
	}

	// 清空复制时需要清空的字段
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		STTreeBaseDataTreeInfo info = (STTreeBaseDataTreeInfo) newData;
		info.setId(null);
		info.setNumber(null);
		info.setName(null);
	}

	public void setDataObject(IObjectValue dataObject) {
		super.setDataObject(dataObject);
		refreshUIState();
	}

	public void refreshUIState() {
		numberInput.setEnabled(editData.getId() == null);
	}

	/**
	 * 界面校验逻辑
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 如果需要对编码字段进行比较，则先比较
		if (isNeedCheckNumberFirst()) {
			Component txtNumber = dataBinder.getComponetByField("number");
			if (txtNumber instanceof KDTextField) {
				String str = ((KDTextField) txtNumber).getText();
				if (str == null || str.length() == 0) {
					txtNumber.requestFocus();
					MsgBox
							.showInfo(EASResource
									.getString("com.kingdee.eas.st.common.STResource.NULL_Number"));
					txtNumber.setEnabled(true);
					SysUtil.abort(); // 退出
				}
			}
		}

		// 名称必填
		Component txtName = dataBinder.getComponetByField("name");
		if (txtName instanceof KDBizMultiLangBox) {
			boolean isNull = STBaseDataClientUtils
					.isMultiLangBoxInputNameEmpty((KDBizMultiLangBox) txtName,
							editData, "name");
			if (isNull) {
				txtName.requestFocus();
				MsgBox
						.showInfo(EASResource
								.getString("com.kingdee.eas.st.common.STResource.NULL_NAME"));
				txtName.setEnabled(true);
				SysUtil.abort(); // 退出
			}
		}
	}

}