/*
 * @(#)XXXBillInfo.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.app.botp.sample;

import com.kingdee.eas.st.common.STBillBaseInfo;

/**
 * 描述:
 * 
 * @author daij date:2006-12-19
 *         <p>
 * @version EAS5.2.0
 */
public class XXXBillInfo extends STBillBaseInfo {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = 5445310432082933659L;

	/**
	 * 描述：构造函数
	 * 
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	public XXXBillInfo() {
		this("id");
	}

	/**
	 * 描述：构造函数
	 * 
	 * @param pkField
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	public XXXBillInfo(String pkField) {
		/* TODO 自动生成构造函数存根 */
		super(pkField);
		put("entries", new XXXBillEntryCollection());
	}

	public XXXBillEntryCollection getEntries() {
		return (XXXBillEntryCollection) get("entries");
	}
}
