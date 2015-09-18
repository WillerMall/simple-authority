package cn.webro.authorization;

import javax.servlet.http.HttpServletRequest;

/**
 * AuthHelper
 * 权限校验类
 * @author TuWei
 * @date 15/8/19
 */
public abstract class AuthValidator {
    /**
     * 校验权限
     * @return
     */
    public abstract boolean validatePermission(AuthManager manager, PermissionInfo permsInfo, HttpServletRequest request);
}
