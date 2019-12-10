/*
 * @(#)LongTimeDialog.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.st.common.progress.longtime;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDProgressBar;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * 描述:
 * 
 * @author ryanzhou date:2005-10-10
 *         <p>
 * @version EAS5.0
 */
public class LongTimeDialog extends KDDialog {
	private final static String RESOURCE = "com.kingdee.eas.st.common.STResource";

	private ILongTimeTask longTimeTask = null;

	private static final Color bottomBgColor = new Color(230, 230, 230);

	private Object result = null;

	private Window parent = null;

	public LongTimeDialog(Frame frame) {
		super(frame, EASResource.getString(RESOURCE, "longtimeDialogTitle"),
				true);
		parent = frame;
	}

	public LongTimeDialog(Dialog dialog) {
		super(dialog, EASResource.getString(RESOURCE, "longtimeDialogTitle"),
				true);
		parent = dialog;
	}

	public void setLongTimeTask(ILongTimeTask longTimeTask) {
		this.longTimeTask = longTimeTask;
	}

	public void setWaitMsg(String msg) {
		if (null != this.getContentPane().getComponent(1)
				&& this.getContentPane().getComponent(1) instanceof JLabel) {
			((JLabel) this.getContentPane().getComponent(1)).setText(msg);
		}
	}

	protected void dialogInit() {
		super.dialogInit();
		this.setResizable(false);

		this.setDefaultCloseOperation(KDDialog.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(null);
		final Dimension bgPanelDimension = new Dimension(363, 96);
		final int normalBottomHeight = 42;
		final int advanceBottomHeight = 202;
		contentPane.setPreferredSize(new Dimension(bgPanelDimension.width,
				bgPanelDimension.height + normalBottomHeight));
		contentPane.setBackground(new Color(0xffffff));

		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(null);
		pnlBottom.setBounds(0, bgPanelDimension.height, bgPanelDimension.width,
				advanceBottomHeight);
		contentPane.add(pnlBottom);
		pnlBottom.setBackground(bottomBgColor);
		Graphics2D g2d = (Graphics2D) pnlBottom.getGraphics();
		if (g2d != null) {
			GradientPaint gradient = new GradientPaint(0, 0,
					new Color(0xffffff), 363, 96, new Color(0xb4b4b4));
			g2d.setPaint(gradient);
		}

		KDSeparator sp = new KDSeparator();
		sp.setBounds(0, 0, bgPanelDimension.width, 2);
		pnlBottom.add(sp);

		String waitMsg = EASResource.getString(RESOURCE, "waitPlease");
		JLabel text = new JLabel(waitMsg);
		text.setBounds(42 + 2, 29, 279, 20);

		KDProgressBar progressBar = new KDProgressBar();
		progressBar.setStringPainted(false);
		progressBar.setIndeterminate(true);
		this.getContentPane().add(text, null);
		this.getContentPane().add(progressBar, null);
		progressBar.setBounds(new Rectangle(42, 50, 279, 12));
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void show() {
		SwingWorker longTimeTaskWorker = new SwingWorker() {
			public Object construct() {
				try {
					result = longTimeTask.exec();
					LongTimeDialog.this.dispose();
					return result;
				} catch (Exception e) {
					ExceptionHandler.handle(parent, e);
					LongTimeDialog.this.dispose();
					return null;// occur exception, bad state
				}
			}

			public void finished() {

				if (result != null) {
					try {
						Thread.sleep(500);
						LongTimeDialog.this.longTimeTask.afterExec(result);
					} catch (Exception e) {
						ExceptionHandler.handle(parent, e);
					}
				}
			}
		};

		longTimeTaskWorker.start();

		setCursor(this, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		super.show();
		setCursor(this, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	private void setCursor(Component cp, Cursor cursor) {
		cp.setCursor(cursor);
		if (cp instanceof Container) {
			Container cc = (Container) cp;
			Component curComponent;
			for (int i = 0, n = cc.getComponentCount(); i < n; i++) {
				curComponent = cc.getComponent(i);
				setCursor(curComponent, cursor);
			}
		}
	}

}
