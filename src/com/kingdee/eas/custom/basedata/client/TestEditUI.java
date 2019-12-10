/**
 * output package name
 */
package com.kingdee.eas.custom.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;

/**
 * Java类描述：
 * <p>Title: </p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * @author DZ_yanb
 * @date 2019-12-10    
 * @version 1.0
 */
public class TestEditUI extends AbstractTestEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TestEditUI.class);
    
    /**
     * output class constructor
     */
    public TestEditUI() throws Exception
    {
        super();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.custom.basedata.TestFactory.getRemoteInstance();
    }

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject) 
    {
        super.setDataObject(dataObject);
        if(STATUS_ADDNEW.equals(getOprtState())) {
            editData.put("treeid",(com.kingdee.eas.custom.basedata.TestTreeInfo)getUIContext().get(UIContext.PARENTNODE));
        }
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.custom.basedata.TestInfo objectValue = new com.kingdee.eas.custom.basedata.TestInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@以下代码为修改原有方法或新增方法@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void onLoad() throws Exception {
    	super.onLoad();

    	if(getOprtState().equals(STATUS_ADDNEW) || getOprtState().equals(STATUS_EDIT)){
    		btnCopy.setEnabled(false);
    		btnEdit.setEnabled(false);
    		btnRemove.setEnabled(false);
    		
    		btnFirst.setVisible(false);
    		btnLast.setVisible(false);
    		btnNext.setVisible(false);
    		btnPre.setVisible(false);
    		
    		btnCancel.setEnabled(false);
    		btnCancelCancel.setEnabled(false);
    	}
    }
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
    	boolean isEnabled = this.editData.isIsEnable();
        
      //判断是否启用
    	if(isEnabled){
    		btnRemove.setEnabled(false);
    		btnEdit.setEnabled(false);
    		
    		btnCancel.setEnabled(true);
    		btnCancelCancel.setEnabled(false);
    	}else{
    		btnEdit.setEnabled(true);
    		btnRemove.setEnabled(true);
    		
    		btnCancel.setEnabled(false);
    		btnCancelCancel.setEnabled(true);
    	}
    	
        super.loadFields();
        
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	//判断当前选中的节点是否为根节点
    	if(getUIContext().get("selectedTreeInfo")==null){
    		com.kingdee.eas.util.client.MsgBox.showInfo("不能在根节点下新增");
    		return;
    	} 
    	super.actionAddNew_actionPerformed(e);
    	btnCopy.setEnabled(false);
		btnEdit.setEnabled(false);
		btnRemove.setEnabled(false);
		
		btnFirst.setVisible(false);
		btnLast.setVisible(false);
		btnNext.setVisible(false);
		btnPre.setVisible(false);
		
		btnCancel.setEnabled(false);
		btnCancelCancel.setEnabled(false);
    }
    
    
    public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
    	super.actionSubmit_actionPerformed(arg0);
    	//设置提交后打开的窗口中隐藏部分按钮
    	if(getOprtState().equals(STATUS_ADDNEW)){
    		btnCopy.setEnabled(false);
    		btnEdit.setEnabled(false);
    		btnRemove.setEnabled(false);
    		
    		btnFirst.setVisible(false);
    		btnLast.setVisible(false);
    		btnNext.setVisible(false);
    		btnPre.setVisible(false);
    		
    		btnCancel.setEnabled(false);
    		btnCancelCancel.setEnabled(false);
    	}
    	
    }
    
    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    	//判断当前选中的节点是否为根节点
    	if(getUIContext().get("selectedTreeInfo")==null){
    		com.kingdee.eas.util.client.MsgBox.showInfo("不能在根节点下复制新增");
    		return;
    	} 
    	
    	//复制新增时，清除是否启用
    	this.editData.setIsLoaded(false);
        super.actionCopy_actionPerformed(e);
      
        btnCopy.setEnabled(false);
		btnEdit.setEnabled(false);
		btnRemove.setEnabled(false);
		
		btnFirst.setVisible(false);
		btnLast.setVisible(false);
		btnNext.setVisible(false);
		btnPre.setVisible(false);
		
		btnCancel.setEnabled(false);
		btnCancelCancel.setEnabled(false);
        
    }
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()==null){
    		com.kingdee.eas.util.client.MsgBox.showInfo("基础资料没有保存或不存在，无法修改！");
    		return;
    	}
    	
        super.actionEdit_actionPerformed(e);
        btnCopy.setEnabled(false);
		btnEdit.setEnabled(false);
		btnRemove.setEnabled(false);
		
		btnFirst.setVisible(false);
		btnLast.setVisible(false);
		btnNext.setVisible(false);
		btnPre.setVisible(false);
		
		btnCancel.setEnabled(false);
		btnCancelCancel.setEnabled(false);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	if(this.editData.getId()==null){
    		com.kingdee.eas.util.client.MsgBox.showInfo("基础资料没有保存或不存在，无法删除！");
    		return;
    	}
        super.actionRemove_actionPerformed(e);
    }

}