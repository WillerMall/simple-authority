package cn.webro.authorization.nutz;

import cn.webro.authorization.annotation.Permission;
import cn.webro.authorization.AuthManager;
import cn.webro.authorization.AuthValidator;
import cn.webro.authorization.PermissionInfo;
import cn.webro.authorization.support.MethodUtils;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.ServerRedirectView;

import java.lang.reflect.Method;

/**
 * 基础权限过滤器
 * @author tuwei
 * @time   2012-9-13 上午10:54:04
 *
 */
public class PermissionFilter implements ActionFilter {

	Log log = Logs.get();
	AuthManager authManager;

	public View match(ActionContext actionContext) {
		if(authManager == null) {
			authManager = actionContext.getIoc().get(AuthManager.class);
		}
		Method actionMethod = actionContext.getMethod();
        //需要过滤权限
		if(actionMethod.getAnnotation(Permission.class) != null){
            //不过滤
			if(!authManager.isExcludeUrl(actionContext.getPath())){

				AuthValidator validator = authManager.getValidator();
				if(validator != null){
					PermissionInfo info = authManager.permissions().get(MethodUtils.getMethodIdentity(actionMethod));
                    //校验通过
                    if(validator.validatePermission(authManager, info, actionContext.getRequest())){
                        return null;
					}else{
                        //返回自定义视图
                        if(!Strings.isEmpty(info.getFailureUrl())){
                            return new ServerRedirectView(info.getFailureUrl());
                        }else {
                            if(info.getViewType() != Permission.VIEW_TYPE_NONE) {
                                if (info.getViewType() == Permission.VIEW_TYPE_HTML) {
                                    if (!Strings.isEmpty(authManager.getHtmlFailureUrl())) {
                                        return new ServerRedirectView(authManager.getHtmlFailureUrl());
                                    }
                                } else {
                                    if (!Strings.isEmpty(authManager.getJsonFailureUrl())) {
                                        return new ServerRedirectView(authManager.getJsonFailureUrl());
                                    }
                                }
                            }
                            return new HttpStatusView(403);
                        }
                    }
				}

			}
		}

		return null;
	}
}
