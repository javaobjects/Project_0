package com.kingdee.eas.st.common.client;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.extendcontrols.icons.ResourceManager;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.st.common.util.STUtils;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

public abstract class STPromptBox extends KDCommonPromptDialog {
	/**
	 * 
	 */
	protected FilterInfo filter = null;
	private static final long serialVersionUID = 1L;
	protected IUIWindow currOrgTreeDialog;
	HashMap ctx = new HashMap();

	public static String IS_SINGLE_SELECT = "IS_SINGLE_SELECT"; // 是否单选
	public static String IS_CANCELED = "IS_CANCELED"; // 是否取消

	protected IUIObject owner;

	public STPromptBox() {
	}

	public STPromptBox(IUIObject owner) {
		super();
		this.owner = owner;
	}

	public STPromptBox(IUIObject owner, FilterInfo filter) {
		this.owner = owner;
		this.filter = filter;
	}

	public STPromptBox(IUIObject owner, boolean isKeyProduct,
			boolean isKeyResource) {
		this.owner = owner;
	}

	/**
	 * 
	 * 描述： 设置缺省的过滤条件
	 * 
	 * @param filterInfo
	 * 
	 * @author brina 2007-03-13
	 */
	public void setDefaultFilterInfo(FilterInfo filterInfo) {
		this.filter = filterInfo;
	}

	/**
	 * 设置是否为单选
	 * 
	 * @param isSingle
	 */
	public void setIsSingleSelect(boolean isSingle) {
		ctx.put(IS_SINGLE_SELECT, Boolean.valueOf(isSingle));
	}

	/**
	 * 设置是否为单选
	 * 
	 * @param isSingle
	 */
	public void setIsCanceled(boolean isCancel) {
		ctx.put(IS_CANCELED, Boolean.valueOf(isCancel));
	}

	public void show() {
		if (ctx.get(IS_SINGLE_SELECT) == null) {
			ctx.put(IS_SINGLE_SELECT, Boolean.TRUE);
		}
		if (ctx.get(IS_CANCELED) == null) {
			ctx.put(IS_CANCELED, Boolean.TRUE);
		}
		ctx.put("Owner", ComponentUtil.getRootComponent((Component) owner));
		ctx.put("Filter", this.filter);

		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			currOrgTreeDialog = uiFactory.create(getListUIName(), ctx, null,
					OprtState.VIEW,
					com.kingdee.bos.ui.face.WinStyle.SHOW_TOOLBAR);

			ListUI stockDetailF7 = (ListUI) currOrgTreeDialog.getUIObject();
			((KDDialog) currOrgTreeDialog).removeAllToolBar();
			((KDDialog) currOrgTreeDialog).addToolBar(getInitToolBar());
			EntityViewInfo mainQuery = stockDetailF7.getMainQuery();
			FilterInfo mainFilter = mainQuery.getFilter();
			if (STUtils.isNotNull(this.filter)) {
				if (STUtils.isNotNull(mainFilter)) {
					mainFilter.mergeFilter(this.filter, "AND");
				} else {
					mainFilter = this.filter;
				}

				((ListUI) stockDetailF7).setFilterForQuery(this.filter);
			}
			mainQuery.setFilter(mainFilter);
			stockDetailF7.refreshList();
			currOrgTreeDialog.show();

		} catch (Exception e) {
			ExceptionHandler.handle(e);
		}
	}

	/**
	 * @author zhiwei_wang ListUI的全名，可以是用BOS设计自己的界面，注意一定要是从ListUI继承
	 *         例如：StockDetailF7UI.class.getName();
	 * @return
	 */
	public abstract String getListUIName();

	public abstract boolean isCanceled();

	public abstract Object getData();

	// public boolean isCanceled()
	// {
	// if(ctx.get(IS_CANCELED) == null){
	// ctx.put(IS_CANCELED, Boolean.TRUE);
	// return true;
	// }else{
	// return ((Boolean)ctx.get(IS_CANCELED)).booleanValue();
	// }
	// }

	public boolean isSingleSelect() {
		if (ctx.get(IS_SINGLE_SELECT) == null) {
			ctx.put(IS_SINGLE_SELECT, Boolean.TRUE);
			return true;
		} else {
			return ((Boolean) ctx.get(IS_SINGLE_SELECT)).booleanValue();
		}
	}

	/**
	 * @author zhiwei_wang 以下部分初始化工具条，工具条按钮调用原来ListUI上的事件。 2007-08-30
	 */
	private KDWorkButton btnFilter;

	private KDWorkButton btnRefresh;

	private KDWorkButton btnExit;

	protected KDToolBar getInitToolBar() {
		// toolbar
		KDToolBar tb = new KDToolBar(KDToolBar.NORTH);
		// this.addToolBar(tb);

		// 创建button
		btnRefresh = new KDWorkButton();
		btnFilter = new KDWorkButton();
		btnExit = new KDWorkButton();

		// 设置tooltip
		btnRefresh.setToolTipText(this.getMLS("Refresh", "Refresh"));
		btnFilter.setToolTipText(this.getMLS("Filter", "Filter"));
		btnExit.setToolTipText(EASResource.getString("exit"));

		// 设置 text
		btnRefresh.setText(this.getMLS("Refresh", "Refresh"));
		btnFilter.setText(this.getMLS("Filter", "Filter"));
		btnExit.setText(EASResource.getString("exit"));

		// 设置图标
		Icon refreshIcon = this.getMLIcon("refresh.gif");
		if (refreshIcon != null)
			btnRefresh.setIcon(refreshIcon);
		Icon filterIcon = this.getMLIcon("filter.gif");
		if (filterIcon != null)
			btnFilter.setIcon(filterIcon);
		btnExit.setIcon(EASResource.getIcon("imgTbtn_quit"));

		tb.add(btnFilter);
		tb.add(btnRefresh);
		tb.add(btnExit);

		// 注册按钮事件
		initControls();

		return tb;
	}

	// 从资源文件取得字串，以便支持多语言
	private String getMLS(String key, String defaultValue) {
		return LanguageManager.getLangMessage(key,
				"com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog",
				defaultValue);
	}

	// 从资源文件取得icon，以便支持多语言
	private Icon getMLIcon(String key) {
		// edit by fuxiuhu，获取图片资源时性能调整
		Image bi = ResourceManager.getBufferedImage(key);
		if (bi != null) {
			return new ImageIcon(bi);
		}

		return null;
	}

	protected void initControls() {
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					((ListUI) currOrgTreeDialog.getUIObject()).refreshList();
				} catch (Exception e1) {
					// TODO 自动生成 catch 块
					e1.printStackTrace();
				}
			}
		});

		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					((ListUI) currOrgTreeDialog.getUIObject())
							.actionQuery_actionPerformed(null);
				} catch (Exception e1) {
					// TODO 自动生成 catch 块
					e1.printStackTrace();
				}
			}
		});

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setIsCanceled(true);
				currOrgTreeDialog.getUIObject().destroyWindow();
			}
		});
	}

}
