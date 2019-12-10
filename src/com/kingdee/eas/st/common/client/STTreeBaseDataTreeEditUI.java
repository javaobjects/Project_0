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

	/** ������򻺴� */
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
	 * �Ƿ��ȱ�������롣
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

			// ����Ϊ�յ��쳣����ʾ��λ����
			if (ex.getMainCode().equals("40") && ex.getSubCode().equals("104")) {
				numberInput.requestFocus();
			}
			// �����ظ����쳣����ʾ��λ����
			if (ex.getMainCode().equals("86") && ex.getSubCode().equals("007")) {
				nameInput.requestFocus();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		refreshUIState();
	}

	// ��ո���ʱ��Ҫ��յ��ֶ�
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
	 * ����У���߼�
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ҫ�Ա����ֶν��бȽϣ����ȱȽ�
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
					SysUtil.abort(); // �˳�
				}
			}
		}

		// ���Ʊ���
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
				SysUtil.abort(); // �˳�
			}
		}
	}

}