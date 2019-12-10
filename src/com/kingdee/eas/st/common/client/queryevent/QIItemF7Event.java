/*
 * @(#)QIItemF7Event.java
 *
 * ������������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.client.queryevent;

import com.kingdee.bos.BOSException;

/**
 * ����:
 * 
 * @author daij date:2007-4-19
 *         <p>
 * @version EAS5.2
 */
public class QIItemF7Event extends AbstractQueryEvent {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -6505135592925386112L;

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