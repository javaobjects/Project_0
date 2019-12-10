/*
 * @(#)STParamReader.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.param;

import java.io.Serializable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.param.ParamItemCollection;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.st.common.ISTParameterFacade;
import com.kingdee.eas.st.common.STParameterFacadeFactory;

/**
 * 描述: ST参数读取器
 * 
 * 读取器调用示例：
 * 
 * Client调用： STParamReader.getParameter( null,STParamEntityView.isXXXViewInfo(
 * String companyId)).isXXX();
 * 
 * Server调用： STParamReader.getParameter( ctx,STParamEntityView.isXXXViewInfo(
 * String companyId)).isXXX();
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public class STParamReader implements Serializable, Cloneable {

	/**
	 * <code>serialVersionUID</code> 的注释
	 */
	private static final long serialVersionUID = -4254283924540215822L;

	/**
	 * 获取的参数项.
	 */
	private ParamItemCollection paramItems = null;

	/**
	 * 当前的参数编号.
	 */
	private String paramNumber = null;

	/**
	 * 当前的参数值.
	 * 
	 * 1. 采用参数平台统一存储则为ParamItemCollection. 2. 自己存储则可能为其他数据结构.
	 */
	private Object paramVlaue = null;

	/**
	 * 
	 * 描述：按EntityViewInfo获取参数
	 * 
	 * @param ctx
	 *            服务的上下文，可为空 => getRemoteInstance else getLocalInstance
	 * @param viewInfo
	 *            获取参数的过滤条件
	 * @return STParamReader
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2006-11-28
	 *              <p>
	 */
	public static STParamReader getParameter(Context ctx, String paramNumber,
			EntityViewInfo viewInfo) throws BOSException, EASBizException {
		ISTParameterFacade ie = null;
		if (STQMUtils.isNull(ctx)) {
			ie = STParameterFacadeFactory.getRemoteInstance();
		} else {
			ie = STParameterFacadeFactory.getLocalInstance(ctx);
		}
		return ie.getParameter(paramNumber, viewInfo);
	}

	/**
	 * 
	 * 描述：读取当前参数的Object值.
	 * 
	 * @return Object 参数的弱类型值.
	 * @author:daij 创建时间：2006-11-28
	 *              <p>
	 */
	public Object value() {
		return paramVlaue;
	}

	/**
	 * 
	 * 描述：把参数Object弱类型值翻译为boolean.
	 * 
	 * @param paramValue
	 *            参数的弱类型值.
	 * @return boolean
	 * @author:daij 创建时间：2006-11-28
	 *              <p>
	 */
	public static boolean readBoolean(Object paramValue) {
		boolean v = false;

		if (STQMUtils.isNotNull(paramValue)) {
			v = (paramValue.toString().equalsIgnoreCase("true")) ? Boolean.TRUE
					.booleanValue() : Boolean.FALSE.booleanValue();
		}
		return v;
	}

	/**
	 * 描述:@return 返回 paramItems。
	 */
	public ParamItemCollection getParamItems() {

		return this.paramItems;
	}

	/**
	 * 描述:设置paramItems的值。
	 * 
	 * @param paramItems
	 */
	public void setParamItems(ParamItemCollection paramItems) {
		this.paramItems = paramItems;

		// 解析paramItems 根据keyID.number 把参数编码写到STParamReader 的paramNumber,
		// 参数植读到paramVlaue;
		parseParamItem();
	}

	protected void parseParamItem() {

		/*
		 * if
		 * (WeighTypeConstant.WEIGH_TYPE_PURINCEPT.equalsIgnoreCase(paramNumber)
		 * || WeighTypeConstant.WEIGH_TYPE_PURSEND.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_SALESEND.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_SALEINCEPT.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_IRONTRANS.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_STEELTRANS.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_OTHERTRANS.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_MATERIALTRANS.equalsIgnoreCase(paramNumber
		 * ) ||
		 * WeighTypeConstant.WEIGH_TYPE_TEMPDEPOSITED.equalsIgnoreCase(paramNumber
		 * ) ||
		 * WeighTypeConstant.WEIGH_TYPE_CHECKUP.equalsIgnoreCase(paramNumber) ||
		 * WeighTypeConstant
		 * .WEIGH_TYPE_FLOTSAMCALLBACK.equalsIgnoreCase(paramNumber) ||
		 * WeighTypeConstant
		 * .WEIGH_TYPE_COMPLETEINCOME.equalsIgnoreCase(paramNumber) ||
		 * WeighTypeConstant.WEIGH_TYPE_OTHERWEIGH.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_DELEGATEIN.equalsIgnoreCase(paramNumber)
		 * ||
		 * WeighTypeConstant.WEIGH_TYPE_DELEGATEOUT.equalsIgnoreCase(paramNumber
		 * )){
		 * 
		 * parsetIsAntiWriteparamVlaue(); }
		 */

		if (STQMUtils.isNotNull(paramNumber)
				&& paramNumber.indexOf("STSYS_IsAuditAfterSubmit") == 0) {
			parsetIsAuditAfterSubmitparamVlaue();
		}

		if (STQMUtils.isNotNull(paramNumber)
				&& paramNumber.indexOf("STSYS_IsAffirm") == 0) {
			parseSingleValueParamItem();
		}

		if (STQMUtils.isNotNull(paramNumber)
				&& paramNumber.equals("STSYS_ForCheckInWarehouse")) {
			parseSingleValueParamItem();
		}
	}

	// 按每个参数的参数编码反写参数读到paramNumber, 参数植读到paramVlaue 注意默认植处理.
	private void parsetIsAntiWriteparamVlaue() {
		parseSingleValueParamItem();
	}

	// 按每个参数的参数编码保存即审核参数读到paramNumber, 参数植读到paramVlaue 注意默认植处理.
	private void parsetIsAuditAfterSubmitparamVlaue() {
		parseSingleValueParamItem();
	}

	// 解析单值参数
	private void parseSingleValueParamItem() {
		if (STQMUtils.isNotNull(paramItems) && paramItems.size() >= 1) {

			ParamItemInfo paramItemInfo = paramItems.get(0);
			if (STQMUtils.isNotNull(paramItemInfo)) {
				this.paramVlaue = paramItemInfo.getValue();
			}
		}
	}

	/**
	 * 
	 * 描述：单据是否提交即审核. 注意： 需要提供默认植处理.
	 * 
	 * @return boolean 创建时间：2006-11-29
	 */
	public boolean IsAuditAfterSubmitparamVlaue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * 
	 * 描述： 注意： 需要提供默认植处理.
	 * 
	 * @return boolean 创建时间：2006-11-29
	 */
	public boolean isAntiWriteparamVlaue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * 
	 * 描述：某检斤类型是否需要一检确认.
	 * 
	 * @return boolean
	 * @author:daij 创建时间：2007-3-14
	 *              <p>
	 */
	public boolean isAffirmParamValue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * @return 返回 paramNumber。
	 */
	public String getParamNumber() {
		return paramNumber;
	}

	/**
	 * @return 返回 paramVlaue。
	 */
	public Object getParamVlaue() {
		return paramVlaue;
	}

	/**
	 * @param paramNumber
	 *            要设置的 paramNumber。
	 */
	public void setParamNumber(String paramNumber) {
		this.paramNumber = paramNumber;
	}

	public void setParamVlaue(Object paramVlaue) {
		this.paramVlaue = paramVlaue;
	}
}
