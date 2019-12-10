package com.kingdee.eas.st.common.rpt.client;

import java.text.DateFormat;
import java.util.Date;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;

public class DateFormatter implements IValueFormatter {
	private int dateFormat = -1;

	public DateFormatter(int df) {
		this.dateFormat = df;
	}

	public Object format(Object value) {
		Date date = new Date();
		if (STQMUtils.isNotNull(value)) {
			String s = null;
			date = (Date) value;
			s = DateFormat.getDateInstance(this.dateFormat).format(date);
			return s;
		} else {
			return value;
		}
	}
}
