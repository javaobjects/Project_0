/*
 * @(#)CurrencyExchangeRateUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 币别汇率处理工具.
 * 
 * @author daij date:2005-12-28
 *         <p>
 * @version EAS5.1
 */
public abstract class CurrencyExchangeRateUtils {

	// 默认的转换率
	public static final BigDecimal EXCHANGERATE_DEFAULT = STConstant.BIGDECIMAL_ONE;

	/**
	 * 币别汇率的常用属性Selectors
	 */
	public static SelectorItemCollection exchangeRateSelectors = null;

	/**
	 * 本位币的汇率信息
	 */
	private static ExchangeRateInfo baseExchangeRateInfo = null;

	static {
		// 汇率的常用属性Selectors
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

		// 本位币的汇率信息(汇率为1)
		baseExchangeRateInfo.setConvertRate(STConstant.BIGDECIMAL_ONE);
		// 折算方式
		ExchangeAuxInfo exchangeAux = new ExchangeAuxInfo();
		exchangeAux.setConvertMode(ConvertModeEnum.DIRECTEXCHANGERATE);
		// 汇率精度
		exchangeAux.setPrecision(STConstant.PRECISION_DEFAUL);
		baseExchangeRateInfo.setExchangeAux(exchangeAux);
	}

	public static final ExchangeRateInfo baseExchangeRateInfo() {
		return baseExchangeRateInfo;
	}

	/**
	 * 
	 * 描述：按指定财务组织和币别获取汇率
	 * 
	 * @param company
	 *            指定财务组织
	 * @param currency
	 *            指定币别
	 * @return ExchangeRateInfo 汇率信息
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2005-12-28
	 *              <p>
	 */
	public static ExchangeRateInfo getExchangeRate(CompanyOrgUnitInfo company,
			CurrencyInfo currency) throws EASBizException, BOSException {
		return getExchangeRate(null, company, currency, new java.util.Date());
	}

	/**
	 * 
	 * 描述：按指定财务组织，币别，日期获取汇率.
	 * 
	 * @param ctx
	 *            服务端上下文(Client端调用填写null)
	 * @param company
	 *            指定财务组织
	 * @param currency
	 *            指定币别
	 * @param date
	 *            指定日期
	 * @return ExchangeRateInfo 汇率信息
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2005-12-28
	 *              <p>
	 */
	public static ExchangeRateInfo getExchangeRate(Context ctx,
			CompanyOrgUnitInfo company, CurrencyInfo currency,
			java.util.Date date) throws EASBizException, BOSException {

		if (company == null)
			return baseExchangeRateInfo();

		// 填入默认的汇率信息（1）
		ExchangeRateInfo exchangeRate = baseExchangeRateInfo();
		// 检查来源币别
		if (currency != null && currency.getId() != null) {
			// 补充财务组织信息
			company = OrgUnitUtils.readFullCompanyOrgUnitInfo(null, company);
			// 查询汇率信息
			if (company.getBaseExchangeTable() != null
					&& company.getBaseCurrency() != null) {
				// 基本汇率表
				BOSUuid bt = company.getBaseExchangeTable().getId();
				// 本位币
				BOSUuid bc = company.getBaseCurrency().getId();
				// 币别不等于财务组织的本位币
				if ((bt != null && bc != null)
						&& (!currency.getId().toString().equals(bc.toString()))) {
					// 获取汇率
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
				// 基本汇率表为空，则认为没有外币业务
				if (bc != null
						&& !currency.getId().toString().equalsIgnoreCase(
								bc.toString())) {
					// TODO 如果给出的币别不同于财务组织的本位币则抛出异常?

				}
			}
		}
		return exchangeRate;
	}

	/**
	 * 
	 * 描述：获取包含常用字段的币别汇率信息.
	 * 
	 * @param ctx
	 *            服务端上下文
	 * @param rate
	 *            汇率
	 * @return ExchangeRateInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2006-7-27
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
	 * 描述：是否本位币汇率
	 * 
	 * @param rate
	 *            汇率
	 * @return boolean
	 * @author:daij 创建时间：2006-9-5
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
	 * 描述：是否本位币
	 * 
	 * @param company
	 *            财务组织
	 * @param currency
	 *            币别
	 * @return boolean
	 * @author:daij 创建时间：2006-9-5
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
	 * 描述：取财务组织上本位币的精度.
	 * 
	 * @param company
	 *            财务组织
	 * @return int 本位币的精度.
	 * @author:daij 创建时间：2006-7-31
	 *              <p>
	 */
	public static int currencyPrecision(CompanyOrgUnitInfo company) {
		// 默认2位精度.
		int scale = STConstant.PRECISION_DEFAUL;
		// 取财务组织上币别的精度.
		if (company != null && company.getBaseCurrency() != null) {
			scale = effectualPrecision(company.getBaseCurrency().getPrecision());
		}
		return scale;
	}

	/**
	 * 
	 * 描述：折算本位币值 注意: 结果取财务组织的本位币精度
	 * 
	 * @param amount
	 *            金额
	 * @param company
	 *            财务组织
	 * @param currency
	 *            币别
	 * @param exchangeRate
	 *            指定汇率
	 * @return BigDecimal 结果
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency,
			BigDecimal exchangeRate) throws BOSException, EASBizException {
		return toLocal(null, amount, company, currency, exchangeRate);
	}

	/**
	 * 
	 * 描述：折算本位币值 注意: 结果取财务组织的本位币精度
	 * 
	 * @param ctx
	 *            服务端上下文(Client端调用填null)
	 * @param amount
	 *            金额
	 * @param company
	 *            财务组织
	 * @param currency
	 *            币别
	 * @param exchangeRate
	 *            指定汇率
	 * @return BigDecimal 结果
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2007-11-14
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
		// 获取来源汇率信息
		ExchangeRateInfo rateInfo = getExchangeRate(ctx, company, currency,
				null);
		// 折算本位币的值
		if (rateInfo != null && !isBaseExchangeRate(rateInfo)) {
			rateInfo.setConvertRate(effectualExchangeRate(exchangeRate));
			result = convert(result, rateInfo, currencyPrecision(company));
		}
		return result;
	}

	/**
	 * 
	 * 描述：折算本位币值 注意: 结果取财务组织的本位币精度
	 * 
	 * @param amount
	 *            金额
	 * @param company
	 *            财务组织
	 * @param currency
	 *            币别
	 * @return BigDecimal 结果
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency)
			throws BOSException, EASBizException {
		return toLocal(null, amount, company, currency, null);
	}

	/**
	 * 
	 * 描述：折算本位币值 注意: 结果取财务组织的本位币精度
	 * 
	 * @param ctx
	 *            服务端上下文(Client端调用填null)
	 * @param amount
	 *            金额
	 * @param company
	 *            财务组织
	 * @param currency
	 *            币别
	 * @return BigDecimal 结果
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal toLocal(Context ctx, BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo currency)
			throws BOSException, EASBizException {
		return toLocal(ctx, amount, company, currency, null);
	}

	/**
	 * 
	 * 描述：2个币别间折算
	 * 
	 * @param amount
	 *            来源数值
	 * @param company
	 *            财务组织
	 * @param oldCurrency
	 *            来源币别
	 * @param currency
	 *            目标币别
	 * @return BigDecimal
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			CompanyOrgUnitInfo company, CurrencyInfo oldCurrency,
			CurrencyInfo currency) throws EASBizException, BOSException {
		return convert(null, amount, company, oldCurrency, currency);
	}

	/**
	 * 
	 * 描述：2个币别间折算
	 * 
	 * @param ctx
	 *            服务端上下文(Client端调用填null)
	 * @param amount
	 *            来源数值
	 * @param company
	 *            财务组织
	 * @param oldCurrency
	 *            来源币别
	 * @param currency
	 *            目标币别
	 * @return BigDecimal
	 * @throws EASBizException
	 * @throws BOSException
	 * @author:daij 创建时间：2007-11-14
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
	 * 描述：2个汇率间折算
	 * 
	 * @param numeric
	 *            数值
	 * @param oldRate
	 *            旧汇率信息
	 * @param rate
	 *            新汇率信息
	 * @param scale
	 *            指定计算后的精度
	 * @return BigDecimal
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			ExchangeRateInfo oldRate, ExchangeRateInfo rate, int scale) {
		// 按旧汇率折算原值为本位币值.
		BigDecimal localNumeric = convert(amount, oldRate,
				STConstant.PRECISION_DEFAUL);
		// 按新汇率折算本位币值为原值.
		BigDecimal result = SCMUtils.effectualNumeric(amount);
		if (rate != null) {
			ConvertModeEnum mode = rate.getConvertMode();
			if (mode != null) {
				if (mode.getValue() == ConvertModeEnum.DIRECTEXCHANGERATE_VALUE) {
					// 按直接汇率折算
					result = NumericUtils
							.effectualNumeric(localNumeric)
							.divide(
									effectualExchangeRate(rate.getConvertRate()),
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				} else if (mode.getValue() == ConvertModeEnum.INDIRECTEXCHANGERATE_VALUE) {
					// 按间接汇率折算
					result = NumericUtils
							.effectualNumeric(localNumeric)
							.multiply(
									effectualExchangeRate(rate.getConvertRate()))
							.setScale(effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				}
			} else {
				// 默认按直接汇率换算.
				result = NumericUtils.effectualNumeric(localNumeric).divide(
						effectualExchangeRate(rate.getConvertRate()),
						effectualPrecision(scale), BigDecimal.ROUND_HALF_UP);
			}
		}
		return result;
	}

	/**
	 * 
	 * 描述：金额转换为指定的汇率. 公式: Result = (amount * | / exchangeRate).scale 注意: 1.
	 * 如果没有折算方式(直接/间接)则按直接(*)处理 2. 如果汇率信息的换算率 <=0 则按默认的折算率: 1处理 3. 如果精度 <0
	 * 则按默认的精度: 2处理
	 * 
	 * @param amount
	 *            金额
	 * @param exchangeRate
	 *            汇率信息
	 * @param scale
	 *            精度
	 * @return BigDecimal 结果
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static BigDecimal convert(BigDecimal amount,
			ExchangeRateInfo exchangeRate, int scale) {

		BigDecimal result = NumericUtils.effectualNumeric(amount);
		if (exchangeRate != null) {
			ConvertModeEnum mode = exchangeRate.getConvertMode();
			if (mode != null) {
				if (mode.getValue() == ConvertModeEnum.DIRECTEXCHANGERATE_VALUE) {
					// 按直接汇率折算
					result = NumericUtils.effectualNumeric(amount)
							.multiply(
									effectualExchangeRate(exchangeRate
											.getConvertRate())).setScale(
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				} else if (mode.getValue() == ConvertModeEnum.INDIRECTEXCHANGERATE_VALUE) {
					// 按间接汇率折算
					result = NumericUtils.effectualNumeric(amount)
							.divide(
									effectualExchangeRate(exchangeRate
											.getConvertRate()),
									effectualPrecision(scale),
									BigDecimal.ROUND_HALF_UP);
				}
			} else {
				// 默认按直接汇率换算.
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
	 * 描述：有效的币别精度.
	 * 
	 * @param scale
	 *            精度值
	 * @return 合法的精度值.
	 * @author:daij 创建时间：2007-11-14
	 *              <p>
	 */
	public static int effectualPrecision(int scale) {
		return (scale >= 0) ? scale : STConstant.PRECISION_DEFAUL;
	}

	/**
	 * 
	 * 描述：有效的换算率值(包括汇率)
	 * 
	 * @param exchangeRate
	 *            原始的换算率值.
	 * @return 有效的换算率值.
	 * @author:daij 创建时间：2005-12-26
	 *              <p>
	 */
	public static BigDecimal effectualExchangeRate(BigDecimal exchangeRate) {
		return isDistrustExchangeRate(exchangeRate) ? exchangeRate
				: EXCHANGERATE_DEFAULT;
	}

	/**
	 * 
	 * 描述：判断换算率是否可以信任(包括汇率)
	 * 
	 * @param BigDecimal
	 *            换算率
	 * @return boolean 是否可信认
	 * @author:daij 创建时间：2005-12-25
	 *              <p>
	 */
	public static boolean isDistrustExchangeRate(BigDecimal exchangeRate) {
		return !((exchangeRate == null)
				|| (exchangeRate.compareTo(SysConstant.BIGZERO) == 0) || (exchangeRate
				.compareTo(SysConstant.BIGZERO) == -1));
	}

	/**
	 * 
	 * 描述：读出汇率.
	 * 
	 * @param info
	 *            汇率信息
	 * @return BigDecimal 汇率值
	 * @author:daij 创建时间：2005-12-28
	 *              <p>
	 */
	public static BigDecimal visitExchangeRate(ExchangeRateInfo info) {
		return (info == null) ? NumericUtils.effectualExchangeRate(null)
				: NumericUtils.effectualExchangeRate(info.getConvertRate());
	}

	/**
	 * 
	 * 描述：读出转换模式
	 * 
	 * @param info
	 *            汇率信息
	 * @return int 转换模式枚举值
	 * @author:daij 创建时间：2005-12-29
	 *              <p>
	 */
	public static int visitCovertMode(ExchangeRateInfo info) {
		return (info == null) ? ConvertModeEnum.DIRECTEXCHANGERATE_VALUE : info
				.getConvertMode().getValue();
	}

	/**
	 * 
	 * 描述：读出汇率的精度
	 * 
	 * @param info
	 * @return
	 * @author:daij 创建时间：2006-1-6
	 *              <p>
	 */
	public static int visitExchangeRatePrecision(ExchangeRateInfo info) {
		return (info == null) ? STConstant.PRECISION_DEFAUL : NumericUtils
				.effectualPrecision(info.getPrecision());
	}
}
