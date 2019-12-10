package com.kingdee.eas.st.common;

/*
 * @(#)InvoicePrintDataSource.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
import java.math.BigDecimal;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.common.client.SysContext;

import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.scm.common.SCMBillBaseInfo;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.util.enums.Enum;
import com.kingdee.util.enums.IntEnum;

/**
 * 描述:发票打印数据源基类--目前只支持打印一个分录
 * 
 * @author ningyan-clive date:2005-9-8
 *         <p>
 * @version EAS5.0
 */
public abstract class STInvoicePrintDataSource implements BOSQueryDelegate {
	private String entriesTag = "entries"; // 实体的分录的属性名称，默认是entries，也可以是其他

	public void setEntryTag(String entryTag) {
		if (entryTag != null) {
			entriesTag = entryTag;
		}
	}

	protected static abstract class EnumUtil extends IntEnum {
		private EnumUtil(String name, int value) {
			super(name, value);
		}

		public static Enum getEnum(Class cls, Object val) {
			return IntEnum.getEnum(cls, Integer.parseInt(val.toString()));
		}
	}

	public static class ColDesc {
		private String colId;

		private String propName;// 如果propName不是单据头或分录的属性名，则不作处理

		private int dataType;// 属性的数据类型。实际上可以读取单据的元数据，这里为实现简单，增加该属性

		private Object extraInfo;// 详见下方属性数据类型的注释

		public ColDesc(String colId, String propName) {
			this(colId, propName, DT_ELSE, null);
		}

		public ColDesc(String colId, String propName, int dataType) {
			this(colId, propName, dataType, null);
		}

		public ColDesc(String colId, String propName, Object extraInfo) {
			this(colId, propName, DT_ELSE, extraInfo);
		}

		public ColDesc(String colId, String propName, int dataType,
				Object extraInfo) {
			super();

			this.colId = colId;
			this.propName = propName;
			this.dataType = dataType;
			this.extraInfo = extraInfo;
		}

		public String getColId() {
			return colId;
		}

		public int getDataType() {
			return dataType;
		}

		public Object getExtraInfo() {
			return extraInfo;
		}

		public String getPropName() {
			return propName;
		}
	}

	// 属性的数据类型
	public static final int DT_DECIMAL = 1;// 附加信息--格式串: null 或 "#" 或

	// "propM.propM_N" 或 "$"

	// null表示去掉小数点后结尾的0；
	// propM.propM_N表示单据的propM属性的propM_N属性的值，必须是Int类型，
	// 2、3两种情况都是要限制小数位数，且保留结尾的0；
	// $表示格式化为中文大写。
	public static final int DT_DATE = 2;

	public static final int DT_ENUM = 3;// 附加信息--枚举类的class对象

	public static final int DT_ELSE = 4;// 附加信息--格式串: "{ propM.propM_N } - {
	public static final int DT_BOOL = 5;
	// propX }"

	// 目前propM.propM_N与propX只支持无须格式化的数据类型，例如字符串、整型

	protected final AbstractObjectCollection billCol;

	private final DateFormat df;

	private final Properties decimalPro;

	private final Properties integerPro;

	private final Properties numberPro;

	public STInvoicePrintDataSource(AbstractObjectCollection bills) {
		billCol = bills;
		df = DateFormat.getDateInstance(DateFormat.LONG, SysContext
				.getSysContext().getLocale());
		decimalPro = getDecimalUnitMapping();
		integerPro = getIntegerUnitMapping();
		numberPro = getNumberMapping();
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		DynamicRowSet drs = null;
		try {
			ColDesc[] desc = getColDesc();
			drs = new DynamicRowSet(desc.length + 1);
			ColInfo ciId = new ColInfo();
			ciId.colType = Types.VARCHAR;
			ciId.columnName = "id";
			drs.setColInfo(1, ciId);
			for (int i = 0; i < desc.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = desc[i].getColId();
				ci.nullable = 1;
				drs.setColInfo(i + 2, ci);
			}
			drs.beforeFirst();

			Iterator itrBill = billCol.iterator();
			int billCount = 0;
			while (itrBill.hasNext()) {
				STBillBaseInfo bill = (STBillBaseInfo) itrBill.next();
				if (bill.get(entriesTag) != null) {
					Iterator itrEntry = ((AbstractObjectCollection) bill
							.get(entriesTag)).iterator();
					while (itrEntry.hasNext()) {
						CoreBaseInfo entry = (CoreBaseInfo) itrEntry.next();

						drs.moveToInsertRow();
						clearRow(drs);
						drs.updateString("id", Integer.toString(billCount));
						for (int i = 0; i < desc.length; i++) {
							fillCol(drs, desc[i], bill, entry);
						}
						rowFilled(drs, bill, entry);
						drs.insertRow();
					}
				} else {
					drs.moveToInsertRow();
					clearRow(drs);
					drs.updateString("id", Integer.toString(billCount));
					for (int i = 0; i < desc.length; i++) {
						fillCol(drs, desc[i], bill, null);
					}
					rowFilled(drs, bill, null);
					drs.insertRow();
				}
				billCount++;
			}

			drs.beforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return drs;
	}

	public void fillCol(IRowSet rs, ColDesc d, STBillBaseInfo bill,
			CoreBaseInfo entry) throws Exception {
		Object prop = getProp(bill, entry, d.getPropName());
		if (prop == null) {
			return;
		}

		if (d.getDataType() == DT_DECIMAL) {
			BigDecimal dec = TypeConversionUtils.objToBigDecimal(prop);
			rs.updateBigDecimal(d.getColId(), dec);
			return;
		}
		String val = null;
		switch (d.getDataType()) {
		// 评蔽原来对decimal的特殊处理，原来的处理是将数字格式化成千分位用逗号隔离的字符串但是这个影响分组合计
		// 所以这里只传入对应的decimal值，格式化交给套打模板处理
		// nicklas.guan 06.10.28
		// case DT_DECIMAL:
		// BigDecimal dec = TypeConversionUtils.objToBigDecimal(prop);
		// if (d.getExtraInfo() == null)
		// {
		// val = formatNumber(dec);
		// }
		// else
		// {
		// String fmt = d.getExtraInfo().toString().trim();
		// if (fmt.startsWith("$"))
		// {
		// val = getChineseFormat(dec);
		// }
		// else if (Character.isDigit(fmt.charAt(0)))
		// {
		// val = formatNumber(dec, Integer.parseInt(fmt));
		// }
		// else
		// {
		// Object prop1 = getProp(bill, entry, fmt);
		// if (prop1 == null)
		// {
		// throw new Exception(
		// "The value of the property to be used as pattern should not be null!"
		// );
		// }
		// val = formatNumber(dec, Integer.parseInt(prop1.toString()));
		// }
		// }
		// break;
		case DT_DATE:
			val = formatDate(TypeConversionUtils.objToDate(prop));
			break;
		case DT_ENUM:
			val = EnumUtil.getEnum((Class) d.getExtraInfo(), prop).toString();
			break;
		case DT_BOOL:
			if (TypeConversionUtils.objToBoolean(prop)) {
				val = SCMClientUtils.getResource("Boolean_Yes");
			} else {
				val = SCMClientUtils.getResource("Boolean_No");
			}
			break;
		case DT_ELSE:
			if (d.getExtraInfo() == null) {
				val = prop.toString();
			} else {
				String prefix = d.getPropName() == null ? "" : d.getPropName()
						+ ".";
				String fmt = d.getExtraInfo().toString();
				StringBuffer buf = new StringBuffer();
				for (int i = 0, n = fmt.length(); i < n; i++) {
					char c = fmt.charAt(i);
					if (c == '{') {
						StringBuffer sb = new StringBuffer(prefix);
						while (++i < n) {
							char nc = fmt.charAt(i);
							if (nc == '}') {
								break;
							}
							sb.append(nc);
						}
						Object prop1 = getProp(bill, entry, sb.toString());
						if (prop1 != null) {
							buf.append(prop1.toString());
						}
					} else {
						buf.append(c);
					}
				}
				val = buf.toString();
			}
			break;
		default:
			throw new Exception("Unsupported data type!");
		}

		rs.updateString(d.getColId(), val);
	}

	protected Object getProp(STBillBaseInfo bill, CoreBaseInfo entry, String pn)
			throws Exception {
		CoreBaseInfo obj = bill;
		pn = pn.trim();
		if (pn.startsWith(entriesTag + ".")) {
			pn = pn.substring(entriesTag.length() + 1);
			obj = entry;
		}

		String[] path = pn.split("\\.");
		for (int i = 0; i < path.length - 1; i++) {
			Object p = obj.get(path[i].trim());
			if (p == null) {
				return null;
			} else if (p instanceof CoreBaseInfo) {
				obj = (STBillBaseInfo) p;
			} else {
				throw new Exception(
						"The property in the path should be instance of CoreBaseInfo or its subclasses!");
			}
		}

		return obj.get(path[path.length - 1].trim());
	}

	protected static void clearRow(IRowSet rs) throws Exception {
		for (int i = 1, n = rs.getRowSetMetaData().getColumnCount(); i <= n; i++) {
			rs.updateString(i, null);
		}
	}

	/**
	 * 用于数量、汇率、换算系数等数值型的格式化
	 * 
	 * @param val
	 * @return
	 */
	protected String formatNumber(BigDecimal val) {
		if (val == null) {
			return "";
		}
		return withCommaAndTrim(val, val.scale());
	}

	private static String withCommaAndTrim(BigDecimal val, int scale) {
		String pattern = "#,##0.##########";
		if (scale > 10) {
			StringBuffer buf = new StringBuffer(pattern);
			for (int i = 10; i < scale; i++) {
				buf.append("#");
			}
			pattern = buf.toString();
		}

		return new DecimalFormat(pattern).format(val);
	}

	/**
	 * 用于金额型的格式化
	 * 
	 * @param val
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	protected String formatNumber(BigDecimal val, int scale) {
		if (val == null) {
			return "";
		}
		return withComma(val.setScale(scale, BigDecimal.ROUND_HALF_UP), scale);
	}

	private static String withComma(BigDecimal val, int scale) {
		String pattern = null;
		switch (scale) {
		case 0:
			pattern = "#,##0";
			break;
		case 1:
			pattern = "#,##0.0";
			break;
		case 2:
			pattern = "#,##0.00";
			break;
		case 3:
			pattern = "#,##0.000";
			break;
		case 4:
			pattern = "#,##0.0000";
			break;
		default:
			StringBuffer buf = new StringBuffer("#,##0.00000");
			for (int i = 5; i < scale; i++) {
				buf.append("0");
			}
			pattern = buf.toString();
		}

		return new DecimalFormat(pattern).format(val);
	}

	/**
	 * 格式化为中文大写
	 */
	protected String getChineseFormat(BigDecimal number) {
		if (number == null) {
			return "";
		}

		String nagative = "";
		boolean isNagative = false;
		if (number.toString().indexOf("-") >= 0) {
			nagative = "负";
			isNagative = true;
		}

		String valTemp = number.toString();
		if (isNagative) {
			valTemp = valTemp.replace('-', ' ').trim();
		}

		int dotIndex = valTemp.indexOf(".");
		String integerStr = "";
		String integerTempStr = null;
		String decimalStr = "";
		String decimalTempStr = null;
		if (dotIndex == -1) {
			integerTempStr = valTemp;
		} else {
			integerTempStr = valTemp.substring(0, dotIndex);
			decimalTempStr = valTemp.substring(dotIndex + 1);
		}

		// 整数部分的处理
		if (integerTempStr != null) // 1234
		{
			int integerLen = integerTempStr.length() - 1;
			boolean zeroAttached = false;// Jacket: remove zeroes
			for (int i = integerLen; i >= 0; i--) {
				String index = String.valueOf(i);
				String str = String.valueOf(integerTempStr.charAt(integerLen
						- i));
				String numStr = numberPro.getProperty(str);
				String uombStr = integerPro.getProperty(index);
				boolean needed = false;
				if (uombStr.startsWith("*"))// Jacket: needed unit
				{
					needed = true;
					uombStr = uombStr.substring(1);
				}

				if ("0".equals(str)) {
					if (zeroAttached) {
						numStr = "";
						if (needed) {
							zeroAttached = false;
							integerStr = integerStr.substring(0, integerStr
									.length() - 1);
						} else {
							uombStr = "";
						}
					} else {
						if (!needed) {
							zeroAttached = true;
							uombStr = "";
						} else {
							zeroAttached = false;
							numStr = "";
						}
					}
				} else {
					zeroAttached = false;
				}

				integerStr += numStr + uombStr;
			}
		}

		boolean integerEmpty = integerStr.length() < 2 ? true : false;
		if (integerEmpty) {
			integerStr = "";
		}

		boolean decimalEmpty = true;

		// 小数部分的处理
		if (decimalTempStr != null) {
			int decimalLen = decimalTempStr.length();
			int n = decimalPro.size();
			n = decimalLen > n ? n : decimalLen;
			boolean zeroAttached = false;// Jacket: remove zeroes
			boolean ignoreLeadingZero = true;
			for (int j = 0; j < n; j++) {
				String index = String.valueOf(j);
				String str = String.valueOf(decimalTempStr.charAt(j));
				String numStr = numberPro.getProperty(str);
				String uombStr = decimalPro.getProperty(index);

				if ("0".equals(str)) {
					if (zeroAttached) {
						numStr = "";
						uombStr = "";
						if (j == n - 1) {
							decimalStr = decimalStr.substring(0, decimalStr
									.length() - 1);
						}
					} else {
						if (j < n - 1 && !ignoreLeadingZero) {
							zeroAttached = true;
						} else {
							numStr = "";
						}
						uombStr = "";
					}
				} else {
					zeroAttached = false;
					ignoreLeadingZero = false;
				}

				decimalStr += numStr + uombStr;
			}

			decimalEmpty = decimalStr.length() < 2 ? true : false;
			if (decimalEmpty) {
				decimalStr = "";
			}

			String unDescriptable = "";// 无法用币别单位表达的小数位
			int index = -1;// 最后一个非零小数位
			for (int i = n; i < decimalLen; i++) {
				char c = decimalTempStr.charAt(i);
				String numStr = numberPro.getProperty(String.valueOf(c));
				unDescriptable += numStr;
				if ('0' != c) {
					index = i;
				}
			}
			if (index >= 0) {
				decimalEmpty = false;
				if ('0' == decimalTempStr.charAt(n - 1)) {
					decimalStr += SCMClientUtils.getResource("Zero")
							+ decimalPro.getProperty(String.valueOf(n - 1));
				}

				decimalStr += unDescriptable.substring(0, index - n + 1);
			}
		}

		if (integerEmpty && decimalEmpty) {
			integerStr = SCMClientUtils.getResource("ZeroYuan");
		}

		return nagative + integerStr + decimalStr
				+ SCMClientUtils.getResource("Zheng");
	}

	private static Properties getDecimalUnitMapping() {
		Properties decimalCurrencyUomb = new Properties();
		decimalCurrencyUomb
				.setProperty("0", SCMClientUtils.getResource("Jiao"));
		decimalCurrencyUomb
				.setProperty("1", SCMClientUtils.getResource("Cent"));
		decimalCurrencyUomb.setProperty("2", SCMClientUtils.getResource("Li"));

		return decimalCurrencyUomb;
	}

	private static Properties getIntegerUnitMapping() {
		Properties integerCurrencyUomb = new Properties();
		integerCurrencyUomb.setProperty("0", "*"
				+ SCMClientUtils.getResource("Yuan")); // "*元"needed unit
		integerCurrencyUomb.setProperty("1", SCMClientUtils.getResource("Ten"));// "拾"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("2", SCMClientUtils
				.getResource("Hundred"));// "佰");
		integerCurrencyUomb.setProperty("3", SCMClientUtils
				.getResource("Thousand"));// "仟");
		integerCurrencyUomb.setProperty("4", "*"
				+ SCMClientUtils.getResource("Thousand"));// "*万");//needed unit
		integerCurrencyUomb.setProperty("5", SCMClientUtils.getResource("Ten"));// "拾"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("6", SCMClientUtils
				.getResource("Hundred"));// "佰");
		integerCurrencyUomb.setProperty("7", SCMClientUtils
				.getResource("Thousand"));// "仟");
		integerCurrencyUomb.setProperty("8", "*"
				+ SCMClientUtils.getResource("Yi"));// "*亿");//needed unit
		integerCurrencyUomb.setProperty("9", SCMClientUtils.getResource("Ten"));// "拾"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("10", SCMClientUtils
				.getResource("Hundred"));// "佰");
		integerCurrencyUomb.setProperty("11", SCMClientUtils
				.getResource("Thousand"));// "仟");
		integerCurrencyUomb
				.setProperty("12", SCMClientUtils.getResource("Wan"));// "万");

		return integerCurrencyUomb;
	}

	private static Properties getNumberMapping() {
		Properties numberMapping = new Properties();
		numberMapping.setProperty("0", SCMClientUtils.getResource("Zero"));//"零")
																			// ;
		numberMapping.setProperty("1", SCMClientUtils.getResource("One"));//"壹")
																			// ;
		numberMapping.setProperty("2", SCMClientUtils.getResource("Two"));//"贰")
																			// ;
		numberMapping.setProperty("3", SCMClientUtils.getResource("Three"));// "叁"
																			// )
																			// ;
		numberMapping.setProperty("4", SCMClientUtils.getResource("Four"));//"肆")
																			// ;
		numberMapping.setProperty("5", SCMClientUtils.getResource("Five"));//"伍")
																			// ;
		numberMapping.setProperty("6", SCMClientUtils.getResource("Six"));//"陆")
																			// ;
		numberMapping.setProperty("7", SCMClientUtils.getResource("Seven"));// "柒"
																			// )
																			// ;
		numberMapping.setProperty("8", SCMClientUtils.getResource("Eight"));// "捌"
																			// )
																			// ;
		numberMapping.setProperty("9", SCMClientUtils.getResource("Nine"));//"玖")
																			// ;

		return numberMapping;
	}

	/**
	 * 格式化时间
	 */
	protected String formatDate(Date date) {
		return df.format(date);
	}

	/**
	 * 获得结果集的列信息。 execute将在返回结果中附加一个固有列“单据唯一标志（id）”
	 */
	public abstract ColDesc[] getColDesc();

	/**
	 * 行填充完毕
	 */
	public void rowFilled(IRowSet rs, STBillBaseInfo bill, CoreBaseInfo entry) {
	}

}
