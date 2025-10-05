package com.hncu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author caimeisahng
 * @Date 2025/10/4 20:08
 * @Version 1.0
 * Security安全框架识别的安全用户对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUser implements UserDetails {
    //商城后台管理系统用户的相关的属性
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 用户所在的商城Id
     */
    private Long shopId;

    /**
     * 系统类型
     */
    private String loginType;

    /**
     * 属性集合
     */
    /*private Set<String> perms = new HashSet<>();*/
    private Set<String> perms;



    //商城购物系统会员的相关属性

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginType+this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

    /**
     * 权限集合get方法的重写(处理set集合元素中存在类似prod:spec:update,prod:spec:info多个权限的情况)
     * @return
     */
    public Set<String> getPerms() {
        Set<String> finalPermsSet = new HashSet<>();
        perms.forEach(perm-> {
            //判断集合数据中是否存在，
            if (perm.contains(",")) {
                //包含说明一条set集合数据存在多个权限
                String[] realPerms = perm.split(",");
                for (String realPerm : realPerms) {
                    finalPermsSet.add(realPerm);
                }
            }else {
                //set集合中一条数据只包含一条权限
                finalPermsSet.add(perm);
            }
        });

        return finalPermsSet;
    }
}
