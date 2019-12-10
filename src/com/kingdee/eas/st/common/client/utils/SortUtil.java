/*
 * @(#)SortUtil.java
 *
 * ????????????????????????? 
 */
package com.kingdee.eas.st.common.client.utils;

import sun.misc.Compare;
import sun.misc.Sort;

import com.kingdee.eas.framework.DataBaseInfo;

/**
 * ????:
 * 
 * @author paul date:2007-4-23
 *         <p>
 * @version EAS5.3
 */
public final class SortUtil {

	/**
	 * ??????????????????????????
	 * 
	 * @param datas
	 * @return
	 * @author:paul ???????2006-10-25
	 *              <p>
	 */
	public static DataBaseInfo[] sortDataByNumber(DataBaseInfo[] datas,
			boolean isAsc) {
		if (datas == null || datas.length < 2) {
			return datas;
		}

		class DataCompare implements Compare {
			private boolean isAsc = true;

			/**
			 * ?????????
			 * 
			 * @author:paul ???????2006-10-25
			 *              <p>
			 */
			public DataCompare(boolean isAsc) {
				this.isAsc = isAsc;
			}

			/**
			 * ????
			 * 
			 * @author:paul
			 * @see sun.misc.Compare#doCompare(java.lang.Object,
			 *      java.lang.Object)
			 */
			public int doCompare(Object arg0, Object arg1) {
				return isAsc ? ((DataBaseInfo) arg0).getNumber().compareTo(
						((DataBaseInfo) arg1).getNumber())
						: ((DataBaseInfo) arg1).getNumber().compareTo(
								((DataBaseInfo) arg0).getNumber());
			}

		}

		Sort.quicksort(datas, new DataCompare(isAsc));
		return datas;
	}

	/**
	 * ?????Int?????String??????????? ?????????Int????
	 * 
	 * @param datas
	 * @param isAsc
	 * @return
	 * @author:paul ???????2007-1-4
	 *              <p>
	 */
	public static Object[] sortArrayByIntValue(Object[] datas, boolean isAsc) {
		if (datas == null || datas.length < 2) {
			return datas;
		}

		class DataCompare implements Compare {
			private boolean isAsc = true;

			/**
			 * ?????????
			 * 
			 * @author:paul ???????2006-10-25
			 *              <p>
			 */
			public DataCompare(boolean isAsc) {
				this.isAsc = isAsc;
			}

			/**
			 * ????
			 * 
			 * @author:paul
			 * @see sun.misc.Compare#doCompare(java.lang.Object,
			 *      java.lang.Object)
			 */
			public int doCompare(Object arg0, Object arg1) {
				if (arg0 instanceof Integer && arg1 instanceof Integer) {
					return isAsc ? ((Integer) arg0).compareTo((Integer) arg1)
							: ((Integer) arg1).compareTo((Integer) arg0);
				} else if (arg0 instanceof String && arg1 instanceof String) {
					return isAsc ? Integer.valueOf((String) arg0).compareTo(
							Integer.valueOf((String) arg1)) : Integer.valueOf(
							(String) arg0).compareTo(
							Integer.valueOf((String) arg1));
				} else {
					return 0;
				}

			}

		}

		Sort.quicksort(datas, new DataCompare(isAsc));
		return datas;
	}

}
