/*
 * @(#)STParamReader.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����: ST������ȡ��
 * 
 * ��ȡ������ʾ����
 * 
 * Client���ã� STParamReader.getParameter( null,STParamEntityView.isXXXViewInfo(
 * String companyId)).isXXX();
 * 
 * Server���ã� STParamReader.getParameter( ctx,STParamEntityView.isXXXViewInfo(
 * String companyId)).isXXX();
 * 
 * @author daij date:2006-11-28
 *         <p>
 * @version EAS5.2.0
 */
public class STParamReader implements Serializable, Cloneable {

	/**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = -4254283924540215822L;

	/**
	 * ��ȡ�Ĳ�����.
	 */
	private ParamItemCollection paramItems = null;

	/**
	 * ��ǰ�Ĳ������.
	 */
	private String paramNumber = null;

	/**
	 * ��ǰ�Ĳ���ֵ.
	 * 
	 * 1. ���ò���ƽ̨ͳһ�洢��ΪParamItemCollection. 2. �Լ��洢�����Ϊ�������ݽṹ.
	 */
	private Object paramVlaue = null;

	/**
	 * 
	 * ��������EntityViewInfo��ȡ����
	 * 
	 * @param ctx
	 *            ����������ģ���Ϊ�� => getRemoteInstance else getLocalInstance
	 * @param viewInfo
	 *            ��ȡ�����Ĺ�������
	 * @return STParamReader
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2006-11-28
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
	 * ��������ȡ��ǰ������Objectֵ.
	 * 
	 * @return Object ������������ֵ.
	 * @author:daij ����ʱ�䣺2006-11-28
	 *              <p>
	 */
	public Object value() {
		return paramVlaue;
	}

	/**
	 * 
	 * �������Ѳ���Object������ֵ����Ϊboolean.
	 * 
	 * @param paramValue
	 *            ������������ֵ.
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-11-28
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
	 * ����:@return ���� paramItems��
	 */
	public ParamItemCollection getParamItems() {

		return this.paramItems;
	}

	/**
	 * ����:����paramItems��ֵ��
	 * 
	 * @param paramItems
	 */
	public void setParamItems(ParamItemCollection paramItems) {
		this.paramItems = paramItems;

		// ����paramItems ����keyID.number �Ѳ�������д��STParamReader ��paramNumber,
		// ����ֲ����paramVlaue;
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

	// ��ÿ�������Ĳ������뷴д��������paramNumber, ����ֲ����paramVlaue ע��Ĭ��ֲ����.
	private void parsetIsAntiWriteparamVlaue() {
		parseSingleValueParamItem();
	}

	// ��ÿ�������Ĳ������뱣�漴��˲�������paramNumber, ����ֲ����paramVlaue ע��Ĭ��ֲ����.
	private void parsetIsAuditAfterSubmitparamVlaue() {
		parseSingleValueParamItem();
	}

	// ������ֵ����
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
	 * �����������Ƿ��ύ�����. ע�⣺ ��Ҫ�ṩĬ��ֲ����.
	 * 
	 * @return boolean ����ʱ�䣺2006-11-29
	 */
	public boolean IsAuditAfterSubmitparamVlaue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * 
	 * ������ ע�⣺ ��Ҫ�ṩĬ��ֲ����.
	 * 
	 * @return boolean ����ʱ�䣺2006-11-29
	 */
	public boolean isAntiWriteparamVlaue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * 
	 * ������ĳ��������Ƿ���Ҫһ��ȷ��.
	 * 
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-3-14
	 *              <p>
	 */
	public boolean isAffirmParamValue() {
		return readBoolean(this.paramVlaue);
	}

	/**
	 * @return ���� paramNumber��
	 */
	public String getParamNumber() {
		return paramNumber;
	}

	/**
	 * @return ���� paramVlaue��
	 */
	public Object getParamVlaue() {
		return paramVlaue;
	}

	/**
	 * @param paramNumber
	 *            Ҫ���õ� paramNumber��
	 */
	public void setParamNumber(String paramNumber) {
		this.paramNumber = paramNumber;
	}

	public void setParamVlaue(Object paramVlaue) {
		this.paramVlaue = paramVlaue;
	}
}
