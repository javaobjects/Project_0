package com.kingdee.eas.st.common.client;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.assistant.client.F7ProjectUI;

public class F7ProjectUI_ST extends F7ProjectUI {

	public F7ProjectUI_ST() throws Exception {
		super();
	}

	public EntityViewInfo getDefaultQuery() {
		EntityViewInfo evi = super.getDefaultQuery();
		FilterInfo filter = new FilterInfo();

		FilterItemCollection col = evi.getFilter().getFilterItems();
		for (int i = 0; i < col.size(); i++) {
			FilterItemInfo fi = col.get(i);
			if (!fi.getPropertyName().equals("isListItem")) {
				filter.getFilterItems().add(fi);
			}
		}

		evi.setFilter(filter);

		return evi;
	}

}
