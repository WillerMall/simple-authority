package cn.webro.authorization.annotation;

import java.lang.annotation.*;

/**
 * Permission
 * 权限信息类
 *
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
     * 权限Id(如果该权限有父级权限Id, 那么该权限也继承了父级权限)
     * @return
     */
    int id() default 0;

    /**
     * 所属权限(父级权限)
     */
    int parentId() default 0;

    /**
     * 权限名称
     */
    String value();

    /**
     * 权限分组
     * @return
     */
    String group() default "";

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
     * 权限类型(比如 分组节点, 菜单, 按钮. ), 自己定义吧
     */
    int type() default 0;

    /**
     * 是否显示
     */
    boolean display() default true;

}
