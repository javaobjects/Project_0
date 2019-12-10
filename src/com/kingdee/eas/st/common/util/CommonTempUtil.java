package com.kingdee.eas.st.common.util;

import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.material.MaterialInventoryCollection;
import com.kingdee.eas.basedata.master.material.MaterialInventoryFactory;

public class CommonTempUtil {

	/**
	 * 根据物料和库存组织查看物料是否启用批次管理
	 * 
	 * @param materialID
	 * @param storageID
	 * @return
	 */
	public static boolean getLotStateByMaterial(String materialID,
			String storageID) {
		if (materialID == null || storageID == null || "".equals(materialID)
				|| "".equals(storageID)) {
			return false;
		}
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("material.id", materialID,
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit", storageID, CompareType.EQUALS));
		entityViewInfo.setFilter(filter);
		try {
			MaterialInventoryCollection collection = MaterialInventoryFactory
					.getRemoteInstance().getMaterialInventoryCollection(
							entityViewInfo);
			if (collection.size() > 0) {
				return collection.get(0).isIsLotNumber();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * 根据物料和库存组织查看物料是否启用批次管理
	 * 
	 * @param materialID
	 * @param storageID
	 * @return
	 */
	public static boolean getLotStateByMaterial(Context ctx, String materialID,
			String storageID) {
		if (materialID == null || storageID == null || "".equals(materialID)
				|| "".equals(storageID)) {
			return false;
		}
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("material.id", materialID,
						CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit", storageID, CompareType.EQUALS));
		entityViewInfo.setFilter(filter);
		try {
			MaterialInventoryCollection collection = MaterialInventoryFactory
					.getLocalInstance(ctx).getMaterialInventoryCollection(
							entityViewInfo);
			if (collection.size() > 0) {
				return collection.get(0).isIsLotNumber();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}
