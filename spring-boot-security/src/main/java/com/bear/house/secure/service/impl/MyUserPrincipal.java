package com.bear.house.secure.service.impl;

import com.bear.house.secure.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

/**
 * 实现UserDetails接口，该接口包含了该用户的授权，状态等信息
 * @author WangTao
 *         Created at 18/6/4 下午6:18.
 */
public class MyUserPrincipal implements UserDetails {

    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }

    /**
     * 授权： 在这里我们可以进行授权
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 一般我们都不会是明文来获取或者保存我们的密码，所以spring-security给按照我们<br>
     *     设置的编码给我们请求的密码做匹配
     * @return
     */
    @Override
    public String getPassword() {
        // 我们数据库中按常理来说保存的是已经加密过的密码，所以我们不需要再次加密
        // return new BCryptPasswordEncoder().encode(user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
