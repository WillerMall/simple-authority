package cn.webro.authorization.config.support;

import cn.webro.authorization.config.ContextConfig;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.annotation.At;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * NutzContextConfig
 *
 * @author TuWei
 * @date 15/8/20
 */
public class NutzContextConfig implements ContextConfig {

    private String[] rootUrl;
    private Map<String, Method> urlMapping;

    public NutzContextConfig (NutConfig nutzConfig){
        if(nutzConfig == null){
            rootUrl = new String[0];
            urlMapping = new HashMap<String, Method>();
            return;
        }
        Class<?> mainController = nutzConfig.getMainModule();
        At mainAt = mainController.getAnnotation(At.class);
        this.rootUrl = mainAt == null ? new String[0] : mainAt.value();
        this.urlMapping = nutzConfig.getAtMap().getMethodMapping();
    }

    public String[] rootUrl() {
        return rootUrl;
    }

    public Map<String, Method> urlMapping() {
        return urlMapping;
    }
}
