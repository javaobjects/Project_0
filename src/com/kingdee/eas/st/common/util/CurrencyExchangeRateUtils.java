/*
 * @(#)CurrencyExchangeRateUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import java.math.BigDecimal;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ConvertModeEnum;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.IExchangeRate;
import com.kingdee.eas.basedata.mm.qm.constant.STConstant;
import com.kingdee.eas.basedata.mm.qm.utils.NumericUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.scm.common.util.SCMUtils;

/**
 * 
 * ����: �ұ���ʴ�����.
 * 
 * @author daij date:2005-12-28
 *         <p>
 * @version EAS5.1
 */
public abstract class CurrencyExchangeRateUtils {

	// Ĭ�ϵ�ת����
	public static final BigDecimal EXCHANGERATE_DEFAULT = STConstant.BIGDECIMAL_ONE;

	/**
	 * �ұ���ʵĳ�������Selectors
	 */
	public static SelectorItemCollection exchangeRateSelectors = null;

	/**
	 * ��λ�ҵĻ�����Ϣ
	 */
	private static ExchangeRateInfo baseExchangeRateInfo = null;

	static {
		// ���ʵĳ�������Selectors
		exchangeRateSelectors = new SelectorItemCollection();
		exchangeRateSelectors.add(new SelectorItemInfo("id"));
		exchangeRateSelectors.add(new SelectorItemInfo("number"));
		exchangeRateSelectors.add(new SelectorItemInfo("name"));
		exchangeRateSelectors.add(new SelectorItemInfo("convertRate"));
		exchangeRateSelectors.add(new SelectorItemInfo(
				"exchangeAux.convertMode"));
		exchangeRateSelectors
				.add(new SelectorItemInfo("exchangeAux.precision"));

		baseExchangeRateInfo = new ExchangeRateInfo();

		// ��λ�ҵĻ�����Ϣ(����Ϊ1)
		baseExchangeRateInfo.setConvertRate(STConstant.BIGDECIMAL_ONE);
		// ���㷽ʽ
		ExchangeAuxInfo exchangeAux = new ExchangeAuxInfo();
		exchangeAux.setConvertMode(ConvertModeEnum.DIRECTEXCHANGERATE);
		// ���ʾ���
		exchangeAux.setPrecision(STConstant.PRECISION_DEFAUL);
		baseExchangeRateInfo.setExchangeAux(exchangeAux);
	}

	public static final ExchangeRateInfo baseExchangeRateInfo() {
		return baseExchangeRateInfo;
	}

	/**
	 * 
	 * ��������ָ��������֯�ͱұ��ȡ����
	 * 
	 * @param company
	 *            ָ��������֯
	 * @param currency
	 *            ָ���ұ�
	 * @return ExchangeRateInfo ������Ϣ
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2005-12-28
	 *              <p>
	 */
	public static ExchangeRateInfo getExchangeRate(CompanyOrgUnitInfo company,
			CurrencyInfo currency) throws EASBizException, BOSException {
		return getExchangeRate(null, company, currency, new java.util.Date());
	}

	/**
	 * 
	 * ��������ָ��������֯���ұ����ڻ�ȡ����.
	 * 
	 * @param ctx
	 *            �����������(Client�˵�����дnull)
	 * @param company
	 *            ָ��������֯
	 * @param currency
	 *            ָ���ұ�
	 * @param date
	 *            ָ������
	 * @return ExchangeRateInfo ������Ϣ
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2005-12-28
	 *              <p>
	 */
	public static ExchangeRateInfo getExchangeRate(Context ctx,
			CompanyOrgUnitInfo company, CurrencyInfo currency,
			java.util.Date date) throws EASBizException, BOSException {

		if (company == null)
			return baseExchangeRateInfo();

		// ����Ĭ�ϵĻ�����Ϣ��1��
		ExchangeRateInfo exchangeRate = baseExchangeRateInfo();
		// �����Դ�ұ�
		if (currency != null && currency.getId() != null) {
			// ���������֯��Ϣ
			company = OrgUnitUtils.readFullCompanyOrgUnitInfo(null, company);
			// ��ѯ������Ϣ
			if (company.getBaseExchangeTable() != null
					&& company.getBaseCurrency() != null) {
				// �������ʱ�
				BOSUuid bt = company.getBaseExchangeTable().getId();
				// ��λ��
				BOSUuid bc = company.getBaseCurrency().getId();
				// �ұ𲻵��ڲ�����֯�ı�λ��
				if ((bt != null && bc != null)
						&& (!currency.getId().toString().equals(bc.toString()))) {
					// ��ȡ����
					IExchangeRate ie = null;
					if (ctx == null) {
						ie = ExchangeRateFactory.getRemoteInstance();
					} else {
						ie = ExchangeRateFactory.getLocalInstance(ctx);
					}
					exchangeRate = ie.getExchangeRate(new ObjectUuidPK(bt),
							new ObjectUuidPK(currency.getId()),
							new ObjectUuidPK(bc), date);
				}
			} else if (company.getBaseExchangeTable() == null
					&& company.getBaseCurrency() != null) {
				BOSUuid bc = company.getBaseCurrency().getId();
				// �������ʱ�Ϊ�գ�����Ϊû�����ҵ��
				if (bc != null
						&& !currency.getId().toString().equalsIgnoreCase(
								bc.toString())) {
					// TODO ��������ıұ�ͬ�ڲ�����֯�ı�λ�����׳��쳣?

				}
			}
		}
		return exchangeRate;
	}

	/**
	 * 
	 * ��������ȡ���������ֶεıұ������Ϣ.
	 * 
	 * @param ctx
	 *            �����������
	 * @param rate
	 *            ����
	 * @return ExchangeRateInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2006-7-27
	 *              <p>
	 */
	public static ExchangeRateInfo fullExchangeRateInfo(Context ctx,
			ExchangeRateInfo rate) throws EASBizException, BOSException {
		if (rate == null || rate.getId() == null)
			return rate;

		IExchangeRate ie = null;
		if (ctx == null) {
			ie = ExchangeRateFactory.getRemoteInstance();
		} else {
			ie = ExchangeRateFactory.getLocalInstance(ctx);
		}
		return ie.getExchangeRateInfo(new ObjectStringPK(rate.getId()
				.toString()), exchangeRateSelectors);
	}

	/**
	 * 
	 * �������Ƿ�λ�һ���
	 * 
	 * @param rate
	 *            ����
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-9-5
	 *              <p>
	 */
	public static boolean isBaseExchangeRate(ExchangeRateInfo rate) {
		boolean isTrue = false;
		if (rate != null) {
			BigDecimal convertRate = rate.getConvertRate();
			if (!NumericUtils.equalsZero(convertRate)) {
				isTrue = (baseExchangeRateInfo().getConvertRate().compareTo(
						convertRate) == 0);
			}
		}
		return isTrue;
	}

	/**
	 * 
	 * �������Ƿ�λ��
	 * 
	 * @param company
	 *            ������֯
	 * @param currency
	 *            �ұ�
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-9-5
	 *              <p>
	 */
	public static boolean isBaseCurrency(CompanyOrgUnitInfo company,
			CurrencyInfo currency) {
		boolean isTrue = false;
		if (company != null && currency != null) {
			CurrencyInfo baseCurrency = company.getBaseCurrency();
			if (baseCurrency != null && baseCurrency.getId() != null
					&& currency.getId() != null) {
				isTrue = baseCurrency.getId().toString().equalsIgnoreCase(
						currency.getId().toString());
			}
		}
		return isTrue;
	}

	/**
	 * 
	 * ������ȡ������֯�ϱ�λ�ҵľ���.
	 * 
	 * @param company
	 *            ������֯
	 * @return int ��λ�ҵľ���.
	 * @author:daij ����ʱ�䣺2006-7-31
	 *              <p>
	 */
	public static int currencyPrecision(CompanyOrgUnitInfo company) {
		// Ĭ��2λ����.
		int scale = STConstant.PRECISION_DEFAUL;
		// ȡ������֯�ϱұ�ľ���.
		if (company != null && company.getBaseCurrency() != null) {
			scale = effectualPrecision(company.getBaseCurrency().getPrecision());
		}
		return scale;
	}

	/**
	 * 
	 * ���������㱾λ��ֵ ע��: ���ȡ������֯�ı�λ�Ҿ���
	 * 
	 * @param amount
	 *            ���
	 * @param company
	 *            ������֯
	 * @param currency
	 *            �ұ�
	 * @param exchangeRate
	 *            ָ������
	 * @return BigDecimal ���
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency,
			BigDecimal exchangeRate) throws BOSException, EASBizException {
		return toLocal(null, amount, company, currency, exchangeRate);
	}

	/**
	 * 
	 * ���������㱾λ��ֵ ע��: ���ȡ������֯�ı�λ�Ҿ���
	 * 
	 * @param ctx
	 *            �����������(Client�˵�����null)
	 * @param amount
	 *            ���
	 * @param company
	 *            ������֯
	 * @param currency
	 *            �ұ�
	 * @param exchangeRate
	 *            ָ������
	 * @return BigDecimal ���
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(Context ctx, BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency,
			BigDecimal exchangeRate) throws BOSException, EASBizException {

		BigDecimal result = NumericUtils.effectualNumeric(amount);
		if (NumericUtils.equalsZero(amount) || currency == null
				|| currency.getId() == null || company == null) {
			return result;
		}
		// ��ȡ��Դ������Ϣ
		ExchangeRateInfo rateInfo = getExchangeRate(ctx, company, currency,
				null);
		// ���㱾λ�ҵ�ֵ
		if (rateInfo != null && !isBaseExchangeRate(rateInfo)) {
			rateInfo.setConvertRate(effectualExchangeRate(exchangeRate));
			result = convert(result, rateInfo, currencyPrecision(company));
		}
		return result;
	}

	/**
	 * 
	 * ���������㱾λ��ֵ ע��: ���ȡ������֯�ı�λ�Ҿ���
	 * 
	 * @param amount
	 *            ���
	 * @param company
	 *            ������֯
	 * @param currency
	 *            �ұ�
	 * @return BigDecimal ���
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency)
			throws BOSException, EASBizException {
		return toLocal(null, amount, company, currency, null);
	}

	/**
	 * 
	 * ���������㱾λ��ֵ ע��: ���ȡ������֯�ı�λ�Ҿ���
	 * 
	 * @param ctx
	 *            �����������(Client�˵�����null)
	 * @param amount
	 *            ���
	 * @param company
	 *            ������֯
	 * @param currency
	 *            �ұ�
	 * @return BigDecimal ���
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(Context ctx, BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency)
			throws BOSException, EASBizException {
		return toLocal(ctx, amount, company, currency, null);
	}

	/**
	 * 
	 * ������2���ұ������
	 * 
	 * @param amount
	 *            ��Դ��ֵ
	 * @param company
	 *            ������֯
	 * @param oldCurrency
	 *            ��Դ�ұ�
	 * @param currency
	 *            Ŀ��ұ�
	 * @return BigDecimal
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo oldCurrency,
			CurrencyInfo currency) throws EASBizException, BOSException {
		return convert(null, amount, company, oldCurrency, currency);
	}

	/**
	 * 
	 * ������2���ұ������
	 * 
	 * @param ctx
	 *            �����������(Client�˵�����null)
	 * @param amount
	 *            ��Դ��ֵ
	 * @param company
	 *            ������֯
	 * @param oldCurrency
	 *            ��Դ�ұ�
	 * @param currency
	 *            Ŀ��ұ�
	 * @return BigDecimal
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(Context ctx, BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo oldCurrency,
			CurrencyInfo currency) throws EASBizException, BOSException {

		BigDecimal result = NumericUtils.effectualNumeric(amount);
		if (currency != null) {
			result = convert(amount, getExchangeRate(ctx, company, oldCurrency,
					null), getExchangeRate(ctx, company, currency, null),
					effectualPrecision(currency.getPrecision()));
		}
		return result;
	}

	/**
	 * 
	 * ������2�����ʼ�����
	 * 
	 * @param numeric
	 *            ��ֵ
	 * @param oldRate
	 *            �ɻ�����Ϣ
	 * @param rate
	 *            �»�����Ϣ
	 * @param scale
	 *            ָ�������ľ���
	 * @return BigDecimal
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			ExchangeRateInfo oldRate, ExchangeRateInfo rate, int scale) {
		// ���ɻ�������ԭֵΪ��λ��ֵ.
		BigDecimal localNumeric = convert(amount, oldRate,
				STConstant.PRECISION_DEFAUL);
		// ���»������㱾λ��ֵΪԭֵ.
		BigDecimal result = SCMUtils.effectualNumeric(amount);
		if (rate != null) {
			ConvertModeEnum mode = rate.getConvertMode();
			if (mode != null) {
				if (mode.getValue() == ConvertModeEnum.DIRECTEXCHANGERATE_VALUE) {
					// ��ֱ�ӻ�������
					result = NumericUtils
							.effectualNumeric(localNumeric)
							.divide(
									effectualExchangeRate(rate.getConvertRate()),
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				} else if (mode.getValue() == ConvertModeEnum.INDIRECTEXCHANGERATE_VALUE) {
					// ����ӻ�������
					result = NumericUtils
							.effectualNumeric(localNumeric)
							.multiply(
									effectualExchangeRate(rate.getConvertRate()))
							.setScale(effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				}
			} else {
				// Ĭ�ϰ�ֱ�ӻ��ʻ���.
				result = NumericUtils.effectualNumeric(localNumeric).divide(
						effectualExchangeRate(rate.getConvertRate()),
						effectualPrecision(scale), BigDecimal.ROUND_HALF_UP);
			}
		}
		return result;
	}

	/**
	 * 
	 * ���������ת��Ϊָ���Ļ���. ��ʽ: Result = (amount * | / exchangeRate).scale ע��: 1.
	 * ���û�����㷽ʽ(ֱ��/���)��ֱ��(*)���� 2. ���������Ϣ�Ļ����� <=0 ��Ĭ�ϵ�������: 1���� 3. ������� <0
	 * ��Ĭ�ϵľ���: 2����
	 * 
	 * @param amount
	 *            ���
	 * @param exchangeRate
	 *            ������Ϣ
	 * @param scale
	 *            ����
	 * @return BigDecimal ���
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			ExchangeRateInfo exchangeRate, int scale) {

		BigDecimal result = NumericUtils.effectualNumeric(amount);
		if (exchangeRate != null) {
			ConvertModeEnum mode = exchangeRate.getConvertMode();
			if (mode != null) {
				if (mode.getValue() == ConvertModeEnum.DIRECTEXCHANGERATE_VALUE) {
					// ��ֱ�ӻ�������
					result = NumericUtils.effectualNumeric(amount)
							.multiply(
									effectualExchangeRate(exchangeRate
											.getConvertRate())).setScale(
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				} else if (mode.getValue() == ConvertModeEnum.INDIRECTEXCHANGERATE_VALUE) {
					// ����ӻ�������
					result = NumericUtils.effectualNumeric(amount)
							.divide(
									effectualExchangeRate(exchangeRate
											.getConvertRate()),
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				}
			} else {
				// Ĭ�ϰ�ֱ�ӻ��ʻ���.
				result = NumericUtils.effectualNumeric(amount).multiply(
						effectualExchangeRate(exchangeRate.getConvertRate()))
						.setScale(effectualPrecision(scale),
								BigDecimal.ROUND_HALF_UP);
			}
		}
		return result;
	}

	/**
	 * 
	 * ��������Ч�ıұ𾫶�.
	 * 
	 * @param scale
	 *            ����ֵ
	 * @return �Ϸ��ľ���ֵ.
	 * @author:daij ����ʱ�䣺2007-11-14
	 *              <p>
	 */
	public static int effectualPrecision(int scale) {
		return (scale >= 0) ? scale : STConstant.PRECISION_DEFAUL;
	}

	/**
	 * 
	 * ��������Ч�Ļ�����ֵ(��������)
	 * 
	 * @param exchangeRate
	 *            ԭʼ�Ļ�����ֵ.
	 * @return ��Ч�Ļ�����ֵ.
	 * @author:daij ����ʱ�䣺2005-12-26
	 *              <p>
	 */
	public static BigDecimal effectualExchangeRate(BigDecimal exchangeRate) {
		return isDistrustExchangeRate(exchangeRate) ? exchangeRate
				: EXCHANGERATE_DEFAULT;
	}

	/**
	 * 
	 * �������жϻ������Ƿ��������(��������)
	 * 
	 * @param BigDecimal
	 *            ������
	 * @return boolean �Ƿ������
	 * @author:daij ����ʱ�䣺2005-12-25
	 *              <p>
	 */
	public static boolean isDistrustExchangeRate(BigDecimal exchangeRate) {
		return !((exchangeRate == null)
				|| (exchangeRate.compareTo(SysConstant.BIGZERO) == 0) || (exchangeRate
				.compareTo(SysConstant.BIGZERO) == -1));
	}

	/**
	 * 
	 * ��������������.
	 * 
	 * @param info
	 *            ������Ϣ
	 * @return BigDecimal ����ֵ
	 * @author:daij ����ʱ�䣺2005-12-28
	 *              <p>
	 */
	public static BigDecimal visitExchangeRate(ExchangeRateInfo info) {
		return (info == null) ? NumericUtils.effectualExchangeRate(null)
				: NumericUtils.effectualExchangeRate(info.getConvertRate());
	}

	/**
	 * 
	 * ����������ת��ģʽ
	 * 
	 * @param info
	 *            ������Ϣ
	 * @return int ת��ģʽö��ֵ
	 * @author:daij ����ʱ�䣺2005-12-29
	 *              <p>
	 */
	public static int visitCovertMode(ExchangeRateInfo info) {
		return (info == null) ? ConvertModeEnum.DIRECTEXCHANGERATE_VALUE : info
				.getConvertMode().getValue();
	}

	/**
	 * 
	 * �������������ʵľ���
	 * 
	 * @param info
	 * @return
	 * @author:daij ����ʱ�䣺2006-1-6
	 *              <p>
	 */
	public static int visitExchangeRatePrecision(ExchangeRateInfo info) {
		return (info == null) ? STConstant.PRECISION_DEFAUL : NumericUtils
				.effectualPrecision(info.getPrecision());
	}
}
