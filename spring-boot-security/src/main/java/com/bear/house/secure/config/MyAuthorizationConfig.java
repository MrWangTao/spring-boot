package com.bear.house.secure.config;

import com.bear.house.secure.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
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
     * spring会话支持的Session注册表
     */
    @Autowired
    private SpringSessionBackedSessionRegistry springSessionBackedSessionRegistry;

    /**
     * 相当于xml中配置了一个<bean></bean> <br>
     * 访问决策管理器
     * @return
     */
    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new HttpAccessDecisionManager();
    }

    /**
     * 调用的安全性过滤的元信息
     * redis
     * @return
     */
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new RedisInvocationSecurityMetadataSource();
    }

    /**
     * session 信息过期策略
     * @return
     */
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new HttpSessionInfomationExpiredStrategy();
    }

    /**
     * http 认证入口点
     * @return
     */
    @Bean
    public HttpAuthenticationEntryPoint authenticationEntryPoint() {
        return new HttpAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        /*if (anonymous) {
            http.anonymous().disable();  // http匿名登录禁用
        }*/
        http.requestCache().requestCache(new NullRequestCache())  // 不缓存request
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(securityMetadataSource());
                        return o;
                    }
                })   // 后处理器
                .accessDecisionManager(accessDecisionManager())  // 添加自定义访问决策管理器
                .and().exceptionHandling()      // 异常处理
                .authenticationEntryPoint(authenticationEntryPoint())  // 添加自定义认证入口点
                .accessDeniedHandler(new HttpAccessDeniedHandler())     // 添加自定义访问拒绝处理器
                .and().csrf().disable();
        http.sessionManagement()
                .maximumSessions(1)
                // .maxSessionsPreventsLogin(true)  // 为true时，多次登录时抛出异常
                .sessionRegistry(springSessionBackedSessionRegistry)    // 注册自定义session管理
                .expiredSessionStrategy(sessionInformationExpiredStrategy());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // super.configure(web);
        WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
        ignoring.antMatchers("/error");
        // 在配置文件中动态获取
        /*if (Objects.nonNull(permitPages)) {
            if ("regex".equalsIgnoreCase(matcher)) {
                ignoredRequestConfigurer.regexMatchers(permitPages);
            } else if ("ant".equalsIgnoreCase(matcher)) {
                ignoredRequestConfigurer.antMatchers(permitPages);
            } else {
                ignoredRequestConfigurer.antMatchers(permitPages);
            }

        }*/
    }
}
