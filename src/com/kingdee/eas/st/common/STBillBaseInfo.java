package com.kingdee.eas.st.common;

import java.io.IOException;
import java.io.Serializable;

import com.kingdee.eas.scm.common.BillBaseStatusEnum;
import com.kingdee.util.marshal.Marshaller;
import com.kingdee.util.marshal.Unmarshaller;

public abstract class STBillBaseInfo extends AbstractSTBillBaseInfo implements
		Serializable {

	// 判断是由BOTP调用的保存[是]，或是由用户调用的保存
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
	 * 描述：
	 * 
	 * @author:paul
	 * @see com.kingdee.bos.dao.AbstractObjectValue#marshal(com.kingdee.util.marshal.Marshaller)
	 */
	public void marshal(Marshaller marshaller) throws IOException {
		super.marshal(marshaller);
		marshaller.writeBoolean(isBotpCallSave);
	}

	/**
	 * 描述：
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
	 * 构造时执行一些初始化动作 由于是在构造方法里调用的，所以设定为private，免得被弄成多态形式。
	 * 
	 * @author xiaofeng_liu
	 * 
	 */
	private void init() {
		// add by xiaofeng_liu 默认状态为新增。可以除去xxxEditUI中的一些代码
		this.setBillStatus(BillBaseStatusEnum.ADD);
	}
}