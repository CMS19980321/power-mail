package com.hncu.constant;

/**
 * @Author caimeisahng
 * @Date 2025/9/21 11:32
 * @Version 1.0
 */
public interface AuthConstants {
    /*
    * 请求头中的存放token值的前缀Key
    *
    * */

    String AUTHORIZATION = "authorization";

    /*
    * token值的前缀
    * */
    String BEARER = "bearer ";

    /*
    * token值存放在redis中的前缀
    * */
    String LOGIN_TOKEN_PREFIX = "login_token";

    /**
     * 登录url
     */

    String LOGIN_URL = "doLogin";

    /**
     * 登出Url
     */
    String LOGOUT_URL = "doLogout";

    /**
     * 登录类型
     */
    String LOGIN_TYPE = "loginType";

    /**
     * 登录类型值:商城后台管理系统用户
     */
    String SYS_USER_LOGIN = "sysUserLogin";

    /**
     * 登录类型值:商城用户购物系统用户
     */
    String MEMBER_LOGIN = "memberLogin";

    /**
     * token存活时长:秒
     * 在Java中，当你需要定义一个长整型（Long）字面量时，通常建议在数字后面加上一个大写的"L"或小写的"l"，
     * 以明确告诉编译器这个数字是一个长整型而不是默认的整型。这样做可以帮助避免编译错误，特别是在处理那些超出了整型范围的数字时。
     *
     * 例如：
     *
     * long number = 123456789012345L; // 正确
     * CopyInsert
     * 然而，如果你定义的数字在整型范围内，Java编译器会自动将整型字面量转换为长整型，因此不加"L"也可以编译通过：
     *
     * long number = 1234567890; // 也可以，编译器自动转换为long类型
     * CopyInsert
     * 但为了代码的可读性和避免潜在的混淆，建议在定义长整型字面量时加上"L
     */
    Long TOKEN_TIME = 14400L;


}
