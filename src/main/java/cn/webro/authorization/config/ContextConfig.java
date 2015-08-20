package cn.webro.authorization.config;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * ContextConfig<br/>
 * 应用环境的配置信息
 * @author TuWei
 * @date 15/8/20
 */
public interface ContextConfig {

    /**
     * 所有Url的基准路径(比如MainModule的At)
     * @return
     */
    public String[] rootUrl();

    /**
     * Url与访问方法的映射
     * @return
     */
    public Map<String, Method> urlMapping();

}
