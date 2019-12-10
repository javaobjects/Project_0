/*
 * 创建日期 2006-12-3
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.kingdee.eas.st.common.app.botp.action;

import java.io.Serializable;

/**
 * 
 * 描述: 混合的反写操作Action.
 * 
 * @author daij date:2006-12-4
 *         <p>
 * @version EAS5.2.0
 */
public final class MixWriteBackAction extends AbstractAction implements
		Serializable {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -1864818507801807898L;

	/**
	 * WriteBackAction存根
	 */
	private WriteBackAction writeBackAction = null;

	/**
	 * NamingWriteBackAction存根
	 */
	private NamingWriteBackAction namingWriteBackAction = null;

	/**
	 * 
	 * 描述：构造函数
	 * 
	 * @param writeBackAction
	 * @param namingWriteBackAction
	 * @author:daij 创建时间：2006-12-4
	 *              <p>
	 */
	public MixWriteBackAction(WriteBackAction writeBackAction,
			NamingWriteBackAction namingWriteBackAction) {
		super();

		this.writeBackAction = writeBackAction;
		this.namingWriteBackAction = namingWriteBackAction;
	}

	/**
	 * 
	 * 描述：返回WriteBackAction存根.
	 * 
	 * @return WriteBackAction
	 * @author:daij 创建时间：2006-12-4
	 *              <p>
	 */
	public WriteBackAction writeBackAcion() {
		if (this.writeBackAction != null) {
			this.writeBackAction.putAll(this);
		}
		return this.writeBackAction;
	}

	/**
	 * 
	 * 描述：返回NamingWriteBackAction存根
	 * 
	 * @return NamingWriteBackAction
	 * @author:daij 创建时间：2006-12-4
	 *              <p>
	 */
	public NamingWriteBackAction namingWriteBackAcion() {
		if (this.namingWriteBackAction != null) {
			this.namingWriteBackAction.putAll(this);
		}
		return this.namingWriteBackAction;
	}
}
