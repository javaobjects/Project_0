/*
 * @STCurrentStorageUtils.java
 * 
 * 金蝶国际软件集团有限公司版权所有
 */
package com.kingdee.eas.st.common.util;

/**
 * 描述: 钢铁即时库存查询常量定义。<描述> <br>
 * 
 * @author: angus_yang date: 2008-5-22
 * 
 *          修改人：<修改人> <br>
 *          修改时间：<修改时间> <br>
 *          修改描述：<修改描述> <br>
 * @version EAS 6.0
 */
public final class STCurrentStorageUtils {
	// 库存组织--StorageOrgUnitInfo
	public static final String QUERY_STORAGEORGUNIT = "STORAGEORGUNIT";

	// 库存组织的过滤条件
	public static final String QUERY_STORAGE_FILTER = "STORAGE_FILTER";

	// 物料--MaterialInfo
	public static final String QUERY_MATERIAL = "MATERIAL";

	// 仓库--WarehouseInfo
	public static final String QUERY_WAREHOUSE = "WAREHOUSE";

	// 库位--LocationInfo
	public static final String QUERY_LOCATION = "LOCATION";

	// //批号--String
	// public static final String QUERY_BLOCKNUMBER= "BLOCKNUMBER";
	//	
	// //熔炼号--String
	// public static final String QUERY_SMELTINGNUMBER = "SMELTINGNUMBER";
	// 批次--String
	public static final String QUERY_LOT = "lot";
	// 辅助属性--String
	public static final String QUERY_AssistProperty = "assistProperty";
}
