/**
 * output package name
 */
package com.kingdee.eas.st.common;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class STTreeException extends EASBizException
{
    private static final String MAINCODE = "86";

    public static final NumericExceptionSubItem HAS_ITEMS = new NumericExceptionSubItem("000", "HAS_ITEMS");
    public static final NumericExceptionSubItem ONLY_ADD_ITEM_LOWEST = new NumericExceptionSubItem("001", "ONLY_ADD_ITEM_LOWEST");
    public static final NumericExceptionSubItem CANNOT_AUDIT = new NumericExceptionSubItem("002", "CANNOT_AUDIT");
    public static final NumericExceptionSubItem CANNOT_UNAUDIT = new NumericExceptionSubItem("003", "CANNOT_UNAUDIT");
    public static final NumericExceptionSubItem CANNOT_REMOVE = new NumericExceptionSubItem("004", "CANNOT_REMOVE");
    public static final NumericExceptionSubItem CANNOT_EDIT = new NumericExceptionSubItem("005", "CANNOT_EDIT");
    public static final NumericExceptionSubItem CANNOT_ADDGROUP = new NumericExceptionSubItem("006", "CANNOT_ADDGROUP");
    public static final NumericExceptionSubItem DUPLICATE_NAME = new NumericExceptionSubItem("007", "DUPLICATE_NAME");
    public static final NumericExceptionSubItem CANNOT_MOVE_TO_ROOT = new NumericExceptionSubItem("008", "CANNOT_MOVE_TO_ROOT");
    public static final NumericExceptionSubItem CANNOT_SAVE = new NumericExceptionSubItem("009", "CANNOT_SAVE");
    public static final NumericExceptionSubItem CANNOT_MOVE_TO_NOT_LEAF = new NumericExceptionSubItem("010", "CANNOT_MOVE_TO_NOT_LEAF");
    public static final NumericExceptionSubItem CU_CANNOT_EDIT = new NumericExceptionSubItem("011", "CU_CANNOT_EDIT");
    public static final NumericExceptionSubItem CU_CANNOT_MOVE = new NumericExceptionSubItem("012", "CU_CANNOT_MOVE");
    public static final NumericExceptionSubItem CU_CANNOT_REMOVE = new NumericExceptionSubItem("013", "CU_CANNOT_REMOVE");
    public static final NumericExceptionSubItem CU_CANNOT_AUDIT = new NumericExceptionSubItem("014", "CU_CANNOT_AUDIT");
    public static final NumericExceptionSubItem CU_CANNOT_UNAUDIT = new NumericExceptionSubItem("015", "CU_CANNOT_UNAUDIT");
    public static final NumericExceptionSubItem CANNOT_ASSIGN = new NumericExceptionSubItem("016", "CANNOT_ASSIGN");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public STTreeException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public STTreeException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public STTreeException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public STTreeException(NumericExceptionSubItem info)
    {
        this(info, null, null);
    }

    /**
     * getMainCode function
     */
    public String getMainCode()
    {
        return MAINCODE;
    }
}