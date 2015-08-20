/**
 * NutzAuthConfig 配置文件
 */
var auth = {
    authManager : {
        singleton : true,
        type : "cn.webro.authorization.nutz.NutzIocAuthManager",
        fields : {
            validator : {
                type : "cn.webro.test.authority.DefaultAuthValidator"
            },
            excludeUrl : ["/main/t1/testMethod"], //需要排除的Url,不带后缀
            htmlFailureUrl : "/main/t1/authFailureHtml.do", //访问无权限跳转到的 HTML 页面地址
            jsonFailureUrl : "/main/t1/authFailureJson.do", //访问无权限跳转到的 JSON 页面地址
        }
    }
}