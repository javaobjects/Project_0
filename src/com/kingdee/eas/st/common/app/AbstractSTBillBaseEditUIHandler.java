/**
 * output package name
 */
package com.kingdee.eas.st.common.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSTBillBaseEditUIHandler extends com.kingdee.eas.framework.app.CoreBillEditUIHandler

{
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionLineCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionLineCopy(request,response,context);
	}
	protected void _handleActionLineCopy(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionClose(request,response,context);
	}
	protected void _handleActionClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnClose(request,response,context);
	}
	protected void _handleActionUnClose(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCloseEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCloseEntry(request,response,context);
	}
	protected void _handleActionCloseEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnCloseEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnCloseEntry(request,response,context);
	}
	protected void _handleActionUnCloseEntry(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionSubmitAndPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionSubmitAndPrint(request,response,context);
	}
	protected void _handleActionSubmitAndPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAuditAndPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAuditAndPrint(request,response,context);
	}
	protected void _handleActionAuditAndPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionCurrentStorage(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionCurrentStorage(request,response,context);
	}
	protected void _handleActionCurrentStorage(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionQueryByMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionQueryByMaterial(request,response,context);
	}
	protected void _handleActionQueryByMaterial(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}