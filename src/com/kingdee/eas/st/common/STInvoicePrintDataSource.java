package com.kingdee.eas.st.common;

/*
 * @(#)InvoicePrintDataSource.java
 *
 * �����������������޹�˾��Ȩ���� 
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
 * ����:��Ʊ��ӡ����Դ����--Ŀǰֻ֧�ִ�ӡһ����¼
 * 
 * @author ningyan-clive date:2005-9-8
 *         <p>
 * @version EAS5.0
 */
public abstract class STInvoicePrintDataSource implements BOSQueryDelegate {
	private String entriesTag = "entries"; // ʵ��ķ�¼���������ƣ�Ĭ����entries��Ҳ����������

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

		private String propName;// ���propName���ǵ���ͷ���¼������������������

		private int dataType;// ���Ե��������͡�ʵ���Ͽ��Զ�ȡ���ݵ�Ԫ���ݣ�����Ϊʵ�ּ򵥣����Ӹ�����

		private Object extraInfo;// ����·������������͵�ע��

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

	// ���Ե���������
	public static final int DT_DECIMAL = 1;// ������Ϣ--��ʽ��: null �� "#" ��

	// "propM.propM_N" �� "$"

	// null��ʾȥ��С������β��0��
	// propM.propM_N��ʾ���ݵ�propM���Ե�propM_N���Ե�ֵ��������Int���ͣ�
	// 2��3�����������Ҫ����С��λ�����ұ�����β��0��
	// $��ʾ��ʽ��Ϊ���Ĵ�д��
	public static final int DT_DATE = 2;

	public static final int DT_ENUM = 3;// ������Ϣ--ö�����class����

	public static final int DT_ELSE = 4;// ������Ϣ--��ʽ��: "{ propM.propM_N } - {
	public static final int DT_BOOL = 5;
	// propX }"

	// ĿǰpropM.propM_N��propXֻ֧�������ʽ�����������ͣ������ַ���������

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
		// ����ԭ����decimal�����⴦��ԭ���Ĵ����ǽ����ָ�ʽ����ǧ��λ�ö��Ÿ�����ַ����������Ӱ�����ϼ�
		// ��������ֻ�����Ӧ��decimalֵ����ʽ�������״�ģ�崦��
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
	 * �������������ʡ�����ϵ������ֵ�͵ĸ�ʽ��
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
	 * ���ڽ���͵ĸ�ʽ��
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
	 * ��ʽ��Ϊ���Ĵ�д
	 */
	protected String getChineseFormat(BigDecimal number) {
		if (number == null) {
			return "";
		}

		String nagative = "";
		boolean isNagative = false;
		if (number.toString().indexOf("-") >= 0) {
			nagative = "��";
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

		// �������ֵĴ���
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

		// С�����ֵĴ���
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

			String unDescriptable = "";// �޷��ñұ�λ����С��λ
			int index = -1;// ���һ������С��λ
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
				+ SCMClientUtils.getResource("Yuan")); // "*Ԫ"needed unit
		integerCurrencyUomb.setProperty("1", SCMClientUtils.getResource("Ten"));// "ʰ"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("2", SCMClientUtils
				.getResource("Hundred"));// "��");
		integerCurrencyUomb.setProperty("3", SCMClientUtils
				.getResource("Thousand"));// "Ǫ");
		integerCurrencyUomb.setProperty("4", "*"
				+ SCMClientUtils.getResource("Thousand"));// "*��");//needed unit
		integerCurrencyUomb.setProperty("5", SCMClientUtils.getResource("Ten"));// "ʰ"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("6", SCMClientUtils
				.getResource("Hundred"));// "��");
		integerCurrencyUomb.setProperty("7", SCMClientUtils
				.getResource("Thousand"));// "Ǫ");
		integerCurrencyUomb.setProperty("8", "*"
				+ SCMClientUtils.getResource("Yi"));// "*��");//needed unit
		integerCurrencyUomb.setProperty("9", SCMClientUtils.getResource("Ten"));// "ʰ"
																				// )
																				// ;
		integerCurrencyUomb.setProperty("10", SCMClientUtils
				.getResource("Hundred"));// "��");
		integerCurrencyUomb.setProperty("11", SCMClientUtils
				.getResource("Thousand"));// "Ǫ");
		integerCurrencyUomb
				.setProperty("12", SCMClientUtils.getResource("Wan"));// "��");

		return integerCurrencyUomb;
	}

	private static Properties getNumberMapping() {
		Properties numberMapping = new Properties();
		numberMapping.setProperty("0", SCMClientUtils.getResource("Zero"));//"��")
																			// ;
		numberMapping.setProperty("1", SCMClientUtils.getResource("One"));//"Ҽ")
																			// ;
		numberMapping.setProperty("2", SCMClientUtils.getResource("Two"));//"��")
																			// ;
		numberMapping.setProperty("3", SCMClientUtils.getResource("Three"));// "��"
																			// )
																			// ;
		numberMapping.setProperty("4", SCMClientUtils.getResource("Four"));//"��")
																			// ;
		numberMapping.setProperty("5", SCMClientUtils.getResource("Five"));//"��")
																			// ;
		numberMapping.setProperty("6", SCMClientUtils.getResource("Six"));//"½")
																			// ;
		numberMapping.setProperty("7", SCMClientUtils.getResource("Seven"));// "��"
																			// )
																			// ;
		numberMapping.setProperty("8", SCMClientUtils.getResource("Eight"));// "��"
																			// )
																			// ;
		numberMapping.setProperty("9", SCMClientUtils.getResource("Nine"));//"��")
																			// ;

		return numberMapping;
	}

	/**
	 * ��ʽ��ʱ��
	 */
	protected String formatDate(Date date) {
		return df.format(date);
	}

	/**
	 * ��ý����������Ϣ�� execute���ڷ��ؽ���и���һ�������С�����Ψһ��־��id����
	 */
	public abstract ColDesc[] getColDesc();

	/**
	 * ��������
	 */
	public void rowFilled(IRowSet rs, STBillBaseInfo bill, CoreBaseInfo entry) {
	}

}
