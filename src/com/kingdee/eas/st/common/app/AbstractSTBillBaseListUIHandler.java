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
public abstract class AbstractSTBillBaseListUIHandler extends com.kingdee.eas.framework.app.CoreBillListUIHandler

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
	public void handleActionMultiPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMultiPrint(request,response,context);
	}
	protected void _handleActionMultiPrint(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionMultiPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionMultiPrintPreview(request,response,context);
	}
	protected void _handleActionMultiPrintPreview(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}