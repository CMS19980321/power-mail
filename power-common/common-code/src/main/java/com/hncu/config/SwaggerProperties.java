package com.hncu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author caimeisahng
 * @Date 2025/10/26 20:30
 * @Version 1.0
 * Swagger配置属性对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "swagger3")
public class SwaggerProperties {
    /**
     * 描述生成文档的包名
     */
    private  String basePackage;

    /**
     * 作者名称
     */
    private String name;

    /**
     * 主页
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 标题
     */
    private String title;

    /**
     *描述
     */
    private String description;

    /**
     * 授权信息
     */
    private String license;

    /**
     * 授权的url
     */
    private String licenseUrl;

    /**
     * 服务的团队
     */
    private String termsOFServiceUrl;

    /**
     * 版本
     */
    private String version;


}
