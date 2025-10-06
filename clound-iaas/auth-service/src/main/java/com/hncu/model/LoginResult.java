package com.hncu.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author caimeisahng
 * @Date 2025/10/6 21:16
 * @Version 1.0
 * 登录统一结果对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登录统一结果对象")
public class LoginResult {
    @ApiModelProperty("令牌Token")
    private String accessToken;
    @ApiModelProperty("有效时长")
    private Long expireIn;
}
