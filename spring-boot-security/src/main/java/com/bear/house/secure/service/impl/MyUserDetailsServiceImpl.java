package com.bear.house.secure.service.impl;

import com.bear.house.secure.entity.User;
import com.bear.house.secure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author WangTao
 *         Created at 18/6/4 下午3:51.
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 定义我们根据用户名成查询用户的方法
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByUserName(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("当前用户不存在");
        }
        return new MyUserPrincipal(userOptional.get());
    }

}
