package cn.webro.authorization.annotation;

import java.lang.annotation.*;

/**
 * Permission
 * 权限信息类
 * @author TuWei
 * @date 15/8/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Permission {

    /**
     * 不返回视图
     */
    public final static int VIEW_TYPE_NONE = -1;

    /**
     * 方法返回HTML视图
     */
    public final static int VIEW_TYPE_HTML = 0;

    /**
     * 方法返回JSON 视图
     */
    public final static int VIEW_TYPE_JSON = 1;

    /**
     * 权限名称
     */
    String name();

    /**
     * 权限描述
     */
    String desc() default "";

    /**
     * 无权限跳转的页面(覆盖Config中的配置)
     */
    String failureUrl() default "";

    /**
     * 该方法返回的类型
     */
    int viewType() default VIEW_TYPE_HTML;

    /**
     * 所属类(父级栏目)
     */
    Class<?> parent() default Object.class;

    /**
     * 依赖地址(如果用户没有依赖的地址的权限,那么地址的权限用户也不回拥有)
     */
    String depends() default "";

//    /**
//     * 该权限是否为节点(只用来后台显示和分组)<br/>
//     * rootSection<br/>
//     * &nbsp;&nbsp;+<br/>
//     * &nbsp;&nbsp;+<br/>
//     * &nbsp;&nbsp;+--- subSection1<br/>
//     * &nbsp;&nbsp;+--- subSection2<br/>
//     * @return
//     */
//    boolean isNode() default false;
}
