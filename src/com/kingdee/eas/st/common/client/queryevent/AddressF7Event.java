/*
 * @(#)CustomerF7Event.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.eas.basedata.org.OrgType;

/**
 * ����:
 * 
 * @author daij date:2007-4-19
 *         <p>
 * @version EAS5.2
 */
public class AddressF7Event extends AbstractQueryEvent {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 830901695818588318L;

	/**
	 * ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.client.queryevent.AbstractQueryEvent#fireEvent()
	 */
	public void fireEvent() throws BOSException {
		getProcessor().processQueryEditor(this);
	}

}