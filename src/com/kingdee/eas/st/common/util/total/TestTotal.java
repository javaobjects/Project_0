/*
 * @(#)TestTotal.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.util.total;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingdee.bos.kscript.ParserException;
import com.kingdee.bos.kscript.runtime.Interpreter;
import com.kingdee.bos.kscript.runtime.InterpreterException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.SystemStatusInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.QIBizTypeInfo;

/**
 * 描述:
 * 
 * @author daij date:2007-12-6
 *         <p>
 * @version EAS5.4
 */
public class TestTotal {

	/**
	 * 描述：
	 * 
	 * @param args
	 * @author:daij 创建时间：2007-12-6
	 *              <p>
	 */
	public static void main(String[] args) {
		try {

			TotalStrategy strategy = new TotalStrategy();
			ITotalCaltulater caltulater = new TotalCaltulater(strategy);

			Map detail = new HashMap();
			strategy.putAnalyseString("material,patchNumber", "material");

			MaterialInfo m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAABwKgSmEQJ5/A="));
			m.setNumber("M001");
			m.setName("M001");

			detail.put("material", m);
			detail.put("patchNumber", "0001");

			detail.put("amount", new BigDecimal("1000.33"));
			detail.put("qty", new BigDecimal("5"));
			detail.put("sheaf", new Integer("5"));

			caltulater.groupBy(detail);
			detail.clear();

			m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAABwKgSmEQJ5/A="));
			m.setNumber("M001");
			m.setName("M001");

			detail.put("material", m);
			detail.put("patchNumber", "0002");

			detail.put("amount", new BigDecimal("1000.33"));
			detail.put("qty", new BigDecimal("5"));
			detail.put("sheaf", new Integer("5"));

			caltulater.groupBy(detail);
			detail.clear();

			m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAABwKgSmEQJ5/A="));
			m.setNumber("M001");
			m.setName("M001");

			detail.put("material", m);
			detail.put("patchNumber", "0001");

			detail.put("amount", new BigDecimal("1000.33"));
			detail.put("qty", new BigDecimal("100"));
			detail.put("sheaf", new Integer("5"));

			caltulater.groupBy(detail);
			detail.clear();

			m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAACwKgSmEQJ5/A="));
			m.setNumber("M002");
			m.setName("M002");

			caltulater.groupBy(detail);
			detail.clear();

			detail.put("material", m);
			detail.put("patchNumber", "0003");

			detail.put("amount", new BigDecimal("1000.33"));
			detail.put("qty", new BigDecimal("100"));
			detail.put("sheaf", new Integer("5"));

			caltulater.groupBy(detail);
			detail.clear();

			m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAABwKgSmEQJ5/A="));
			m.setNumber("M001");
			m.setName("M001");

			detail.put("material", m);
			detail.put("sourceBillId", "SO2007-12-5_0001");

			caltulater.leftJoin(detail);
			detail.clear();

			m = new MaterialInfo();
			m.setId(BOSUuid.read("ro4esAEWEADgAAACwKgSmEQJ5/A="));
			m.setNumber("M002");
			m.setName("M002");

			detail.put("material", m);
			detail.put("sourceBillId", "SO2007-12-6_0002");

			caltulater.leftJoin(detail);
			detail.clear();

			System.out.println("\r\n");
			System.out.println("\r\n");

			Iterator itor = caltulater.iterator();

			Object o = null;
			Iterator itorKey = null;
			StringBuffer names = new StringBuffer();
			StringBuffer values = new StringBuffer();
			while (itor.hasNext()) {
				detail = (Map) itor.next();

				itorKey = detail.keySet().iterator();
				while (itorKey.hasNext()) {
					o = itorKey.next();
					names.append(o.toString()).append(" || ");

					if (detail.get(o) != null) {
						values.append(detail.get(o).toString()).append(" || ");
					} else {
						values.append("null").append(" || ");
					}
				}
				System.out.println(names.toString() + "\r\n");
				System.out.println(values.toString() + "\r\n");

				names.setLength(0);
				values.setLength(0);
			}

			Map ls = new HashMap();

			ls.put("a", null);
			ls.put("b", "ssss");
			// ls.put("c",new Integer("3"));

			String s = "a|b|d";
			// String s = "(((a!= null) && (b!=null)) || c!= null )";

			// "([==]{2}+[\\s]*[null]{4}+)"
			Pattern pt = Pattern.compile("([\\s]*[^\\|])");

			// Pattern pt = Pattern.compile(
			// "([^\\&&|^\\||^\\(|^\\)]{1,})([\\s]*[!=]{2}+[\\s]*[null]{4}+)");
			// Pattern pt = Pattern.compile("([^\\&&|^\\||^\\(|^\\)]{1,})");
			Matcher matcher = pt.matcher(s);

			String v = null;
			while (matcher.find()) {
				v = matcher.group(1).trim().toString();

				System.out.println(v);

				v = matcher.group(2).trim().toString();

				System.out.println(v);
			}

			Interpreter itp = new Interpreter();
			Object result = itp.eval(s, ls);

			System.out.println(result.toString());

			ls.clear();

			ls.put("a", new BigDecimal("234123.231421"));
			ls.put("b", new Integer("2"));

			result = itp.eval("a + b", ls);

			System.out.println(result.toString());

			ls.clear();

			QIBizTypeInfo qi = new QIBizTypeInfo();

			qi.setId(BOSUuid.read("sOp0AwEREADgAAd/wKgStINzVbY="));

			ls.put("a", qi);

			qi = new QIBizTypeInfo();
			qi.setId(BOSUuid.read("sOp0AwEREADgAAeIwKgStINzVbY="));

			ls.put("b", qi);

			result = itp.eval(
					"a.getId().toString().equals(b.getId().toString())", ls);
			System.out.println(result.toString());

			System.out
					.println(BOSUuid.create(BOSObjectType.create("02A5514C")));

		} catch (InterpreterException e) {
			/* TODO 自动生成 catch 块 */
			e.printStackTrace();
		} catch (ParserException e) {
			/* TODO 自动生成 catch 块 */
			e.printStackTrace();
		} catch (Exception e) {
			/* TODO 自动生成 catch 块 */
			e.printStackTrace();
		}
	}

}
