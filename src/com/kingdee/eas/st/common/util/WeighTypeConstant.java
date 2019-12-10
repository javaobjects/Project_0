package com.kingdee.eas.st.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 通用常量描述
 * 
 * @author Brina date:2006-11-29
 *         <p>
 * @version EAS5.2
 */
public abstract class WeighTypeConstant {

	/* 检斤类型编码 */
	public static final String WEIGH_TYPE_OTHERWEIGH = "STW000"; // 其他检斤编码

	public static final String WEIGH_TYPE_PURINCEPT = "STW001"; // 采购收货编码

	public static final String WEIGH_TYPE_PURSEND = "STW002"; // 采购退货编码

	public static final String WEIGH_TYPE_SALESEND = "STW003"; // 销售发货编码

	public static final String WEIGH_TYPE_SALEINCEPT = "STW004"; // 销售退回编码

	public static final String WEIGH_TYPE_IRONTRANS = "STW005"; // 铁水调拨编码

	public static final String WEIGH_TYPE_STEELTRANS = "STW006"; // 钢坯调拨编码

	public static final String WEIGH_TYPE_OTHERTRANS = "STW007"; // 其它调拨编码

	public static final String WEIGH_TYPE_MATERIALTRANS = "STW008"; // 大宗原燃料调拨编码

	public static final String WEIGH_TYPE_TEMPDEPOSITED = "STW009"; // 代储代存编码

	public static final String WEIGH_TYPE_CHECKUP = "STW010"; // 检验用检斤编码

	public static final String WEIGH_TYPE_FLOTSAMCALLBACK = "STW011"; // 废料回收检斤编码

	public static final String WEIGH_TYPE_COMPLETEINCOME = "STW012"; // 成品入库检斤编码

	// 补丁号:PT013466,提单号:R080416-078，增加两种检斤类型 王志伟
	public static final String WEIGH_TYPE_DELEGATEIN = "STW013"; //委外加工入库，发货单位应为
																	// “供应商”，
																	// 收货单位应为
																	// “行政组织”；
	public static final String WEIGH_TYPE_DELEGATEOUT = "STW014"; //委外加工出库，发货单位应为
																	// “行政组织”，
																	// 收货单位应为
																	// “供应商”；

	// add by fanmm at 2011-4-14 for 永辉项目
	public static final String WEIGH_TYPE_IRONTRANS_IN = "STW025"; // 库存调拨入库检斤
	public static final String WEIGH_TYPE_MATERIALTRANS_IN = "STW028"; // 内部交易入库检斤
	// add end 2011-4-14
	// add by fanmm at 2011-4-14 for 永辉项目
	public static final String WEIGH_TYPE_IRONTRANS_INID = "EGXnNx+DT7WJMXSth0S62Ogkqv4="; // 库存调拨入库检斤
	public static final String WEIGH_TYPE_MATERIALTRANS_INID = "SPQpkF1yQYa+fu0SqJOTiegkqv4="; // 内部交易入库检斤
	// add end 2011-4-14

	/* 检斤类型内码 */
	public static final String WEIGH_TYPE_PURINCEPTID = "p7jRMwEOEADgAADdwKgTQOgkqv4="; // 采购收货内码

	public static final String WEIGH_TYPE_PURSENDID = "p7jRMwEOEADgAAEWwKgTQOgkqv4="; // 采购退货内码

	public static final String WEIGH_TYPE_SALESENDID = "p7jRMwEOEADgAADiwKgTQOgkqv4="; // 销售发货内码

	public static final String WEIGH_TYPE_SALEINCEPTID = "p7jRMwEOEADgAAEbwKgTQOgkqv4="; // 销售退回内码

	public static final String WEIGH_TYPE_IRONTRANSID = "p7jRMwEOEADgAAECwKgTQOgkqv4="; // 铁水调拨内码

	public static final String WEIGH_TYPE_STEELTRANSID = "p7jRMwEOEADgAAEHwKgTQOgkqv4="; // 钢坯调拨内码

	public static final String WEIGH_TYPE_OTHERTRANSID = "p7jRMwEOEADgAAERwKgTQOgkqv4="; // 其它调拨内码

	public static final String WEIGH_TYPE_MATERIALTRANSID = "p7jRMwEOEADgAAEMwKgTQOgkqv4="; // 大宗原燃料调拨内码

	public static final String WEIGH_TYPE_TEMPDEPOSITEDID = "LUdeGwEPEADgAAVdwKgSFegkqv4="; // 代储代存内码

	public static final String WEIGH_TYPE_CHECKUPID = "LUdeGwEPEADgAAV9wKgSFegkqv4="; // 检验用检斤内码

	public static final String WEIGH_TYPE_FLOTSAMCALLBACKID = "/Ydx6AEREADgAACNwKgSqugkqv4=";// 废料回收检斤内码

	public static final String WEIGH_TYPE_COMPLETEINCOMEID = "/gwjBgEREADgAAHIwKgSqugkqv4=";// 成品入库检斤内码

	public static final String WEIGH_TYPE_OTHERID = "Nzx/bAEaEADgAB18wKgSfOgkqv4="; // 其它检斤ID

	public static Map weighTypeNumberToId = null;

	public static Map weighTypeIdToNumber = null;

	static {

		weighTypeNumberToId = new HashMap();
		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS_IN,
				WEIGH_TYPE_IRONTRANS_INID); // 库存调拨入库检斤
		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS_IN,
				WEIGH_TYPE_MATERIALTRANS_INID); // 内部交易入库检斤

		weighTypeNumberToId.put(WEIGH_TYPE_PURINCEPT, WEIGH_TYPE_PURINCEPTID);

		weighTypeNumberToId.put(WEIGH_TYPE_PURSEND, WEIGH_TYPE_PURSENDID);

		weighTypeNumberToId.put(WEIGH_TYPE_SALESEND, WEIGH_TYPE_SALESENDID);

		weighTypeNumberToId.put(WEIGH_TYPE_SALEINCEPT, WEIGH_TYPE_SALEINCEPTID);

		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS, WEIGH_TYPE_IRONTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_STEELTRANS, WEIGH_TYPE_STEELTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_OTHERTRANS, WEIGH_TYPE_OTHERTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS,
				WEIGH_TYPE_MATERIALTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_TEMPDEPOSITED,
				WEIGH_TYPE_TEMPDEPOSITEDID);

		weighTypeNumberToId.put(WEIGH_TYPE_CHECKUP, WEIGH_TYPE_CHECKUPID);

		weighTypeNumberToId.put(WEIGH_TYPE_FLOTSAMCALLBACK,
				WEIGH_TYPE_FLOTSAMCALLBACKID);

		weighTypeNumberToId.put(WEIGH_TYPE_COMPLETEINCOME,
				WEIGH_TYPE_COMPLETEINCOMEID);

		weighTypeIdToNumber = new HashMap();

		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS_INID,
				WEIGH_TYPE_IRONTRANS_IN); // 库存调拨入库检斤
		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS_INID,
				WEIGH_TYPE_MATERIALTRANS_IN); // 内部交易入库检斤

		weighTypeIdToNumber.put(WEIGH_TYPE_PURINCEPTID, WEIGH_TYPE_PURINCEPT);

		weighTypeIdToNumber.put(WEIGH_TYPE_PURSENDID, WEIGH_TYPE_PURSEND);

		weighTypeIdToNumber.put(WEIGH_TYPE_SALESENDID, WEIGH_TYPE_SALESEND);

		weighTypeIdToNumber.put(WEIGH_TYPE_SALEINCEPTID, WEIGH_TYPE_SALEINCEPT);

		weighTypeIdToNumber.put(WEIGH_TYPE_IRONTRANSID, WEIGH_TYPE_IRONTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_STEELTRANSID, WEIGH_TYPE_STEELTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_OTHERTRANSID, WEIGH_TYPE_OTHERTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_MATERIALTRANSID,
				WEIGH_TYPE_MATERIALTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_TEMPDEPOSITEDID,
				WEIGH_TYPE_TEMPDEPOSITED);

		weighTypeIdToNumber.put(WEIGH_TYPE_CHECKUPID, WEIGH_TYPE_CHECKUP);

		weighTypeIdToNumber.put(WEIGH_TYPE_FLOTSAMCALLBACKID,
				WEIGH_TYPE_FLOTSAMCALLBACK);

		weighTypeIdToNumber.put(WEIGH_TYPE_COMPLETEINCOMEID,
				WEIGH_TYPE_COMPLETEINCOME);
	}

	/*
	 * --------------------------------------------------------------------------
	 * ----------------------------
	 */
	/* 业务资源关键字 */
	/*
	 * --------------------------------------------------------------------------
	 * ----------------------------
	 */
	// 物料F7查询
	public final static String MATERIAL_PATH = "com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery";
	// 多计量单位查询
	public final static String MULTIUNITF7_PATH = "com.kingdee.eas.basedata.master.material.app.F7MultiMeasureUnitQuery";
	// 发货地点F7查询
	public final static String CONSIGNPLACEF7_PATH = "com.kingdee.eas.st.weigh.app.F7ConsignPlaceQuery";
	// 收货地点F7查询
	public final static String ACCEPTPLACEF7_PATH = "com.kingdee.eas.st.weigh.app.F7AcceptPlaceQuery";
	// 运输单位F7查询
	public final static String TRAFFICORGF7_PATH = "com.kingdee.eas.st.weigh.app.F7TrafficOrgQuery";

}
