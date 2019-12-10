/*
 * @(#)CWriteBackDirector.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import java.util.HashMap;
import java.util.Map;

import com.kingdee.eas.st.common.app.botp.AbstractWriteBackDirector;
import com.kingdee.eas.st.common.app.botp.action.WriteBactActionTypeEnum;
import com.kingdee.eas.st.common.util.director.AssortDirector;

/**
 * ����:
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public class CWriteBackDirector extends AbstractWriteBackDirector {

	/**
	 * ���������캯��
	 * 
	 * @author:daij ����ʱ�䣺2006-12-1
	 *              <p>
	 */
	public CWriteBackDirector() {
		super();
	}

	/**
	 * ������
	 * 
	 * @author:daij
	 * @see com.kingdee.eas.st.common.util.director.IDirectable#setupDirector()
	 */
	public void setupDirector() {
		Map ps = new HashMap();

		ps.put(WriteBactActionTypeEnum.AUDIT_WRITEBACKACTION,
				new CAuditWriteBackProcessor());

		ps.put(WriteBactActionTypeEnum.SUBMIT_WRITEBACKACTION,
				new CSubmitWriteBackProcessor());

		this.director = new AssortDirector(ps);
	}

}
