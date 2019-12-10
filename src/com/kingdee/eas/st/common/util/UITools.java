/*
 * 创建日期 2006-10-12
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.kingdee.eas.st.common.util;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.eas.st.common.progress.longtime.LongTimeDialog;

/**
 * main solve other problem beside the table TODO 类用途说明
 * 
 * @author idlity_wang 2007-4-6 11:07:14
 */
public class UITools {
	private static Logger logger = Logger.getLogger(UITools.class);

	public static LongTimeDialog getDialog(Component srcWin) {
		Window win = SwingUtilities.getWindowAncestor(srcWin);
		LongTimeDialog dialog = null;
		if (win instanceof Frame) {
			dialog = new LongTimeDialog((Frame) win);
		} else if (win instanceof Dialog) {
			dialog = new LongTimeDialog((Dialog) win);
		}
		return dialog;
	}

	public static void showObject(Object win) {
		if (win instanceof Frame) {
			((Frame) win).show();
		} else if (win instanceof Dialog) {
			((Dialog) win).show();
		}
	}

}
