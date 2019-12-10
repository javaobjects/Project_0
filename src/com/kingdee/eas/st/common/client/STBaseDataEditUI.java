package com.kingdee.eas.st.common.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.appframework.validator.Validator;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.st.common.app.AbstractSTBaseDataEditUIHandler;
import com.kingdee.eas.st.common.client.validator.STBaseDataCustomValidator;

public class STBaseDataEditUI extends AbstractSTBaseDataEditUI {

	public STBaseDataEditUI() throws Exception {
		super();
	}

	@Override
	public void setDataObject(IObjectValue dataObject) {
		this.editData = (com.kingdee.eas.framework.CoreBaseInfo) dataObject;
		super.setDataObject(dataObject);
	}

	@Override
	protected void initDataStatus() {
		super.initDataStatus();

		if (this.editData.get("isEnabled") != null
				&& this.editData.getBoolean("isEnabled")) {
			this.actionCancel.setEnabled(true);
			this.actionCancelCancel.setEnabled(false);

			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		} else if (this.editData.get("isEnabled") != null
				&& !this.editData.getBoolean("isEnabled")) {
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		} else {
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
	}

	@Override
	protected IObjectValue createNewData() {
		return null;
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	@Override
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		String orgPrptState = getOprtState();
		setOprtState("VIEW");
		try {
			super.actionCancel_actionPerformed(e);
		} catch (Exception ex) {
			setOprtState(orgPrptState);
			throw ex;
		}
	}

	@Override
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		String orgPrptState = getOprtState();
		setOprtState("VIEW");
		try {
			super.actionCancelCancel_actionPerformed(e);
		} catch (Exception ex) {
			setOprtState(orgPrptState);
			throw ex;
		}
	}

	@Override
	protected Validator getValidator() {
		return new STBaseDataCustomValidator(this, this.dataBinder);
	}

}
