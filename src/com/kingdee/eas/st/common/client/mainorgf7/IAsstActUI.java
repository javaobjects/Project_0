package com.kingdee.eas.st.common.client.mainorgf7;

/**
 * 包含往来户的对象，在修改了类型后，触发asstActChanged事件，在该事件中重新设置往来户的F7
 * 
 * @author zhiwei_wang
 * 
 */
public interface IAsstActUI {
	/**
	 * 
	 * @param oldType
	 *            一个不可重复的长度为8的字符串标识业务对象（客户、供应商、部门）
	 * @param newType
	 *            一个不可重复的长度为8的字符串标识业务对象（客户、供应商、部门） 可以取下面三个值：
	 * 
	 *            STConstant.BOSTYPE_CUSTOMER = "BF0C040E";
	 * 
	 *            STConstant.BOSTYPE_SUPPLIER = "37C67DFC";
	 * 
	 *            STConstant.BOSTYPE_ADMINORGUNIT = "CCE7AED4";
	 */
	// 返回往来户类型
	public String getAsstActType();

	public void addAsstActChangeListener(AsstActChangeListener l);

	public void fireAsstActChange(String oldType, String newType);
}