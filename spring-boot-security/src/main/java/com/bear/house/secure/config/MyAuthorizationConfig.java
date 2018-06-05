package com.bear.house.secure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @Order(101) 加载执行顺序， 注意这里是授权，因为逻辑为先认证再授权，所以需要我们的认证顺序小于授权顺序
 * @author WangTao
 *         Created at 18/6/5 上午11:42.
 */
@Configuration
@Order(101)
public class MyAuthorizationConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要添加spring-session包
     */
    @Autowired
    private SpringSessionBackedSessionRegistry springSessionBackedSessionRegistry;

    /**
     * 相当于xml中配置了一个<bean></bean>
     * @return
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return null;
    }



}
