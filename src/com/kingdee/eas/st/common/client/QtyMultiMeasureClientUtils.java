/*
 * @(#)QtyMultiMeasureClientUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.client;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

/**
 * 描述: 物料多计量客户端工具类.
 * 
 * @author daij date:2007-1-10
 *         <p>
 * @version EAS5.2
 */
public abstract class QtyMultiMeasureClientUtils {

	public final static String MULTIUNITF7_PATH = "com.kingdee.eas.basedata.master.material.app.F7MultiMeasureUnitQuery";

	/**
	 * 
	 * 描述：设置由物料多计量上的计量单位F7控件
	 * 
	 * @param bizBox
	 *            F7控件
	 * @param material
	 *            物料
	 * @author:daij 创建时间：2007-1-10
	 *              <p>
	 */
	public static void setF7MeasureUnitByMaterial(KDBizPromptBox bizBox,
			MaterialInfo material) {
		if (STQMUtils.isNotNull(material)
				&& STQMUtils.isNotNull(material.getId())) {

			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("material.id", material.getId()
							.toString(), CompareType.EQUALS));

			filterInfo.setMaskString("#0");
			viewInfo.setFilter(filterInfo);

			bizBox.setEntityViewInfo(viewInfo);
		}
	}

	/**
	 * @author zhiwei_wang
	 * @param bizUnitBox
	 * @param materialId
	 */
	public static void setF7TreeMeasureUnitByMaterial(
			KDBizPromptBox bizUnitBox, String materialId) {
		if (STQMUtils.isNull(bizUnitBox) || STQMUtils.isNull(materialId)) {
			return;
		}
		bizUnitBox.setQueryInfo(MULTIUNITF7_PATH);
		bizUnitBox.setEditFormat("$number$");
		bizUnitBox.setDisplayFormat("$name$");
		bizUnitBox.setCommitFormat("$number$");
		if (materialId != null) {
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("material.id", materialId,
							CompareType.EQUALS));
			filterInfo.setMaskString("#0");
			viewInfo.setFilter(filterInfo);

			bizUnitBox.setHasCUDefaultFilter(false);
			bizUnitBox.setEntityViewInfo(viewInfo);
		}
	}
}
