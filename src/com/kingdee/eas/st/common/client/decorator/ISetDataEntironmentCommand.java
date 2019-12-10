/*
 * @(#)ISetDataEntironmentCommand.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client.decorator;

/**
 * 描述: 装载DataEntironment命令 职责: 1.在IUIDirector组合中至顶而下传达此命令.
 * 把统一的DataEntironment下达到具体的修饰Builder中. 2.DataEntironment包括:
 * UIContext,editData,UIObject.
 * 
 * @author daij date:2006-11-8
 *         <p>
 * @version EAS5.2.0
 */
public interface ISetDataEntironmentCommand {
	/**
	 * 
	 * 描述：装载DataEntironment.
	 * 
	 * @param dataEntironment
	 *            修饰的数据包.
	 * @author:daij 创建时间：2006-11-8
	 *              <p>
	 */
	public abstract void loadDataEntironment(DataEntironment dataEntironment);
}
