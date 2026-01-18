package com.hncu.vo;

import com.hncu.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author caimeisahng
 * @Date 2026/1/19 4:37
 * @Version 1.0
 * 菜单和操作权限对象
 */

@ApiModel("菜单和操作权限对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuAndAuth {

    @ApiModelProperty("菜单权限集合")
    private Set<SysMenu> menuList;
    @ApiModelProperty("操作权限集合")
    private Set<String> authorities;
}
