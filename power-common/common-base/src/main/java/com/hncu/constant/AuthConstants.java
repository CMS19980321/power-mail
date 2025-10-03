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


}
