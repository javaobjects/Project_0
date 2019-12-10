package com.kingdee.eas.st.common;

import java.io.IOException;
import java.io.Serializable;

import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.util.marshal.Marshaller;
import com.kingdee.util.marshal.Unmarshaller;

public abstract class STBillBaseInfo extends AbstractSTBillBaseInfo implements
		Serializable {

	// �ж�����BOTP���õı���[��]���������û����õı���
	// colin_xu,2007/05/28
	private boolean isBotpCallSave = false;

	public STBillBaseInfo() {
		super();
		init();

	}

	protected STBillBaseInfo(String pkField) {
		super(pkField);
		init();
	}

	public String getLogInfo() {
		String retValue = "";
		if (getNumber() != null) {
			retValue = this.getNumber();
		}
		return retValue;
	}

	public boolean isBotpCallSave() {
		return isBotpCallSave;
	}

	public void setBotpCallSave(boolean isBotpCallSave) {
		this.isBotpCallSave = isBotpCallSave;
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.bos.dao.AbstractObjectValue#marshal(com.kingdee.util.marshal.Marshaller)
	 */
	public void marshal(Marshaller marshaller) throws IOException {
		super.marshal(marshaller);
		marshaller.writeBoolean(isBotpCallSave);
	}

	/**
	 * ������
	 * 
	 * @author:paul
	 * @see com.kingdee.bos.dao.AbstractObjectValue#unmarshal(com.kingdee.util.marshal.Unmarshaller)
	 */
	public void unmarshal(Unmarshaller unmarshaller) throws IOException,
			ClassNotFoundException {
		super.unmarshal(unmarshaller);
		isBotpCallSave = unmarshaller.readBoolean();
	}

	/**
	 * ����ʱִ��һЩ��ʼ������ �������ڹ��췽������õģ������趨Ϊprivate����ñ�Ū�ɶ�̬��ʽ��
	 * 
	 * @author xiaofeng_liu
	 * 
	 */
	private void init() {
		// add by xiaofeng_liu Ĭ��״̬Ϊ���������Գ�ȥxxxEditUI�е�һЩ����
		this.setBillStatus(BillBaseStatusEnum.ADD);
	}
}