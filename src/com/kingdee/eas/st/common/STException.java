/**
 * output package name
 */
package com.kingdee.eas.st.common;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.eas.common.EASBizException;

/**
 * output class name
 */
public class STException extends EASBizException
{
    private static final String MAINCODE = "59";

    public static final NumericExceptionSubItem NULL_DATE_FIELD = new NumericExceptionSubItem("100", "NULL_DATE_FIELD");
    public static final NumericExceptionSubItem NULL_TEXT_FIELD = new NumericExceptionSubItem("101", "NULL_TEXT_FIELD");
    public static final NumericExceptionSubItem NULL_F7_FIELD = new NumericExceptionSubItem("102", "NULL_F7_FIELD");
    public static final NumericExceptionSubItem NULL_NUMERIC_FIELD = new NumericExceptionSubItem("103", "NULL_NUMERIC_FIELD");
    public static final NumericExceptionSubItem NULL_ENTRY_FIELD = new NumericExceptionSubItem("104", "NULL_ENTRY_FIELD");
    public static final NumericExceptionSubItem NULL_COMBO_FIELD = new NumericExceptionSubItem("105", "NULL_COMBO_FIELD");
    public static final NumericExceptionSubItem NULL_MULTITEXT_FIELD = new NumericExceptionSubItem("106", "NULL_MULTITEXT_FIELD");
    public static final NumericExceptionSubItem ADMINORGISNULL = new NumericExceptionSubItem("107", "ADMINORGISNULL");
    public static final NumericExceptionSubItem NULL_ZERO_ENTRY_FIELD = new NumericExceptionSubItem("108", "NULL_ZERO_ENTRY_FIELD");
    public static final NumericExceptionSubItem NULL_NUMBER = new NumericExceptionSubItem("200", "NULL_NUMBER");
    public static final NumericExceptionSubItem REFERENCE_CANT_UNAUDIT = new NumericExceptionSubItem("109", "REFERENCE_CANT_UNAUDIT");
    public static final NumericExceptionSubItem UPPER_HUNDRED_ENTRY_FIELD = new NumericExceptionSubItem("000", "UPPER_HUNDRED_ENTRY_FIELD");
    public static final NumericExceptionSubItem NOT_LESS_THAN_ZERO_ENTRY_FIELD = new NumericExceptionSubItem("201", "NOT_LESS_THAN_ZERO_ENTRY_FIELD");
    public static final NumericExceptionSubItem NUMBER_COMPARE = new NumericExceptionSubItem("202", "NUMBER_COMPARE");
    public static final NumericExceptionSubItem NUMBER_DUP = new NumericExceptionSubItem("301", "NUMBER_DUP");
    public static final NumericExceptionSubItem NAME_DUP = new NumericExceptionSubItem("302", "NAME_DUP");

    /**
     * construct function
     * @param NumericExceptionSubItem info
     * @param Throwable cause
     * @param Object[] params
     */
    public STException(NumericExceptionSubItem info, Throwable cause, Object[] params)
    {
        super(info, cause, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Object[] params
     */
    public STException(NumericExceptionSubItem info, Object[] params)
    {
        this(info, null, params);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info,Throwable cause
     */
    public STException(NumericExceptionSubItem info, Throwable cause)
    {
        this(info, cause, null);
    }

    /**
     * construct function
     * @param NumericExceptionSubItem info
     */
    public STException(NumericExceptionSubItem info)
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