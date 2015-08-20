package cn.webro.authorization.nutz;

import cn.webro.authorization.AuthManager;
import cn.webro.authorization.annotation.Permission;
import cn.webro.authorization.config.ContextConfig;
import cn.webro.authorization.PermissionInfo;
import cn.webro.authorization.support.Utils;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 基于 nutz 的 默认配置实现<p/>
 *
 * @author TuWei
 * @date 15/8/19
 */
public class NutzIocAuthManager extends AuthManager {
    final Log log = Logs.get();

    private String[] rootUrl;
    /**
     * url 与方法的映射
     */
    private Map<String, Method> urlMapping;

    /**
     * 应用环境配置
     */
    private ContextConfig config;


    @Override
    public void initAuthorization(ContextConfig config) {
        this.config = config;
        this.rootUrl = config.rootUrl();
        this.permsInfo = new ArrayList<PermissionInfo>();
        this.urlMapping = config.urlMapping();
        this.allPermissions = new HashMap<String, PermissionInfo>();

        log.debug("初始化权限息配置...");
        if(validator == null){
            log.warn("未指定有效的AuthValidator, return!");
            return;
        }else {
            log.debugf("权限校验器: %s", this.validator);
        }
        log.debugf("排除过滤的Url: %s", excludeUrl);
        log.debugf("HTML 类型资源权限错误地址:%s", htmlFailureUrl);
        log.debugf("JSON 类型资源权限错误地址:%s", htmlFailureUrl);

        Map<Class<?>, PermissionInfo> permss = new HashMap<Class<?>, PermissionInfo>();
        Set<String> keySet = urlMapping.keySet();
        for(String url : keySet){
            Method m = urlMapping.get(url);
            Class<?> parentClass = m.getDeclaringClass();
            PermissionInfo pInfo = permss.get(parentClass);
            //如果map 里存在Class的PermissionInfo, 则不再new
            if(pInfo == null){
                pInfo = new PermissionInfo(parentClass);
                permss.put(parentClass, pInfo);
                permsInfo.add(pInfo);
            }
            //不需要管理权限
            if(m.getAnnotation(Permission.class) == null){
                continue;
            }
            //如果已经添加过该方法, 则不在添加(一个Method可能对应多个Url, 而PermissionInfo 保存了该方法的所有映射url)
            String identity = Utils.getMethodIdentity(m);
            if(allPermissions.get(identity) == null){
                PermissionInfo subInfo = buildPermissionInfo(m, parentClass, rootUrl);
                pInfo.getSubPermission().add(subInfo);
                allPermissions.put(identity, subInfo);
                log.debugf("找到权限信息: %s", subInfo);
            }
        }

        log.debug("权限信息初始化完毕!");
    }

    /**
     * 根据 Action 方法, 实例化一个PermissionInfo
     *
     * @param method action 方法
     */
    public PermissionInfo buildPermissionInfo(Method method,Class<?> parentClass, String[] rootUrl) {
        PermissionInfo permission = null;
        if (method != null) {
            permission = new PermissionInfo();
            permission.setMethod(method);
            permission.setMethodName(method.getName());
            permission.setUniqueId(Utils.getMethodIdentity(method));

            //Class<?> parentClass = method.getDeclaringClass();
            At pAt = parentClass.getAnnotation(At.class);
            At at = method.getAnnotation(At.class);
            //Permission pPerms = parentClass.getAnnotation(Permission.class);
            Permission perms = method.getAnnotation(Permission.class);

            if(Lang.isEmpty(rootUrl)){
                rootUrl = new String[]{""};
            }

            String[] parentUrl = pAt.value().length >= 1 ? pAt.value() : new String[]{""};
            String[] mUrl = at.value();
            if (mUrl.length == 0) {
                mUrl = new String[]{"/" + permission.getMethodName()};
            }

            for (String url : mUrl) {
                for (String pUrl : parentUrl){
                    for(String rUrl : rootUrl){
                        String mergedUrl = rUrl + pUrl + url;
                        permission.getUrls().add(mergedUrl);
                    }
                }
            }

            permission.setName(perms.name());
            permission.setDescription(perms.desc());
            permission.setViewType(perms.viewType());
            permission.setFailureUrl(perms.failureUrl());
        }
        return permission;
    }
}
