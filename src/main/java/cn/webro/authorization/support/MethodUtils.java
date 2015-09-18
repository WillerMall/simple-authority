package cn.webro.authorization.support;

import java.lang.reflect.Method;

/**
 * MethodUtils
 * 常用工具类
 * @author TuWei
 * @date 15/8/19
 */
public class MethodUtils {

    /**
     * 生成一个方法的唯一标识
     *
     * @param method 方法
     * @return 唯一标识
     */
    public static String getMethodIdentity(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append("@");
        sb.append(method.getName());
        Class<?>[] types = method.getParameterTypes();
        for (Class<?> clazz : types) {
            sb.append("@" + clazz.getName());
        }
        return sb.toString();
    }
}
