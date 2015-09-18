package cn.webro.authorization;

import cn.webro.authorization.config.ContextConfig;
import org.nutz.lang.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AuthConfig
 * 权限信息配置类<p/>
 * 可以继承这个类自己实现配置
 *
 * @author TuWei
 * @date 15/8/19
 */
public abstract class AuthManager {

    /**
     * 所有权限信息(分好组后的)
     */
    protected static List<PermissionInfo> permsInfo;

    /**
     * 所有权限信息(未分组)
     */
    protected static Map<String, PermissionInfo> allPermissions;

    /**
     * 需要排除掉的url
     */
    protected Set<String> excludeUrl;

    /**
     * 访问无权限跳转到的 Html 页面页面地址
     */
    protected String htmlFailureUrl;

    /**
     * 访问无权限跳转到的 JSON 页面地址
     */
    protected String jsonFailureUrl;

    /**
     * 菜单
     */
    protected Map<String, String> menu;

    /**
     * 校验权限用的validator, 需要自己实现
     */
    protected AuthValidator validator;

    /**
     * 初始化权限配置
     */
    public abstract void initAuthorization(ContextConfig config);

    /**
     * 判断是不是包含某个需要排除的url
     *
     * @param oUrl
     * @return
     */
    public boolean isExcludeUrl(String oUrl) {
        if (Strings.isEmpty(oUrl)) {
            return false;
        }
        return excludeUrl.contains(oUrl);
    }

    /**
     * 返回一个只读的所有权限map<br/>
     * key: 访问方法的唯一标识<br/>
     * value: 权限信息
     *
     * @return 只读权限map
     */
    public static Map<String, PermissionInfo> permissions() {
        return Collections.unmodifiableMap(allPermissions);
    }

    /**
     * 返回一个只读的分好组(根据Module分组)后的所有权限List<br/>
     *
     * @return 只读权限List
     */
    public static List<PermissionInfo> groupedPermission() {
        return Collections.unmodifiableList(permsInfo);
    }

    public AuthValidator getValidator() {
        return validator;
    }

    public Set<String> getExcludeUrl() {
        return excludeUrl;
    }

    public String getHtmlFailureUrl() {
        return htmlFailureUrl;
    }

    public String getJsonFailureUrl() {
        return jsonFailureUrl;
    }

    public Map<String, String> getMenu() {
        return menu;
    }
}
