package com.hncu.constant;

/**
 * @Author caimeisahng
 * @Date 2025/9/22 5:13
 * @Version 1.0
 */
public enum BusinessEnum {

    OPERATION_FAIL(-1,"操作失败"),
    SERVER_INNER_ERROR(9999,"服务颞部异常"),

    UN_AUTHORIZATION(401,"未授权"),

    ACCESS_DENY_FAIL(403,"权限不足，请联系管理员")

    ;

    private Integer code;
    private String desc;

    BusinessEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
