/*
 * @(#)WriteBactActionTypeEnum.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.action;

/**
 * 描述: 反写操作发生时间的命名枚举. 注意：各业务系统发生反写操作的时点命名以此作为超类
 * 
 * @author daij date:2006-12-1
 *         <p>
 * @version EAS5.2.0
 */
public abstract class WriteBactActionTypeEnum {

	/**
	 * 保存时反写操作.
	 */
	public static final String SAVE_WRITEBACKACTION = "SaveWriteBackAction";

	/**
	 * 提交时反写操作.
	 */
	public static final String SUBMIT_WRITEBACKACTION = "SubmitWriteBackAction";

	/**
	 * 审核时反写操作.
	 */
	public static final String AUDIT_WRITEBACKACTION = "AuditWriteBackAction";

	/**
	 * 反审核时反写操作.
	 */
	public static final String UNAUDIT_WRITEBACKACTION = "UnAuditWriteBackAction";
}
