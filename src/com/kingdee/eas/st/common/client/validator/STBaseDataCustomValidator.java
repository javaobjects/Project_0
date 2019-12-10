package com.kingdee.eas.st.common.client.validator;

import java.awt.Component;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.validator.Validator;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.PropertyValidateFailedException;
import com.kingdee.bos.ui.face.CoreUIObject;

public class STBaseDataCustomValidator extends Validator {

	public STBaseDataCustomValidator(CoreUIObject ui) {
		super(ui);
	}

	public STBaseDataCustomValidator(CoreUIObject ui, DataBinder binder) {
		super(ui, binder);
	}

	public void validate_Property_number() {
		KDTextField txtNumber = (KDTextField) this.binder
				.getComponetByField("number");

		if (null == txtNumber.getText()
				|| txtNumber.getText().trim().equals("")) {
			addError("number", new PropertyValidateFailedException("编码不能为空！"));
		}
	}

	public void validate_Property_name() {
		KDBizMultiLangBox txtName = (KDBizMultiLangBox) this.binder
				.getComponetByField("name");

		if (null == txtName.getDefaultLangItemData()
				|| txtName.getDefaultLangItemData().toString().trim()
						.equals("")) {
			addError("name", new PropertyValidateFailedException("名称不能为空！"));
		}
	}
}
