/**
 * output package name
 */
package com.kingdee.eas.st.common.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CopyLineNumInputUI extends AbstractCopyLineNumInputUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CopyLineNumInputUI.class);

	public int inputValue = -1;

	private String temp = null;

	public CopyLineNumInputUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		// ȷ��
		btnOk.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				beforeActionPerformed(e);
				try {
					btnConfirm_actionPerformed(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
					afterActionPerformed(e);
				}
			}
		});

		// ȡ��
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				beforeActionPerformed(e);
				try {
					btnAbort_actionPerformed(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
					afterActionPerformed(e);
				}
			}
		});

	}

	protected void btnAbort_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		this.uiWindow.close();
	}

	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		inputCopies();
	}

	private void inputCopies() {

		String o = txtInput.getText();

		// �������ݲ��䣬�˳�
		if (STUtils.isNotNull(temp) && temp.equals(o)) {
			return;
		}

		temp = o;

		if (!StringUtils.isEmpty(o)) {
			try {
				inputValue = Integer.parseInt(o);
			} catch (Exception e1) {
				txtInput.requestFocus();
				MsgBox.showInfo("�������������Ч������");
				return;
			}
		} else {
			txtInput.requestFocus();
			MsgBox.showInfo("�����������Ϊ�գ�");
			return;
		}

		this.getUIWindow().close();

	}

}