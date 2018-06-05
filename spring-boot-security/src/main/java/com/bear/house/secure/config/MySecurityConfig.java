package com.bear.house.secure.config;

import com.bear.house.secure.authc.MyAuthenticationSuccessHandler;
import com.bear.house.secure.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.*;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Configuration 标识为配置类, 如果不添加，则所有配置失效
 * @author WangTao
 * Created at 18/6/4 下午2:49.
 */
@Configuration
@Order(100)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsServiceImpl;

    /*@Autowired
    private DataSource dataSource;*/

    /**
     * 基于内存的用户权限管理信息<br/>
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth); // 注意这里是个坑， 需要去掉否则我们的配置失效
        // auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
        /**
         * 基于内存的认证设置: 只需要将对应的信息添加到这里就可以了
         */
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("bear").password("123456").roles("ADMIN", "USER")
                .and()
                .withUser("bee").password("123456").roles("USER");*/
        /**
         * 基于jdbc的认证设置
         */
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .withUser("hello").password("world").roles("USER");*/

        /**
         *
         * 设置认证数据提供者
         */
        auth.authenticationProvider(authenticationProvider()); // 需要配置自身的用户细节业务类
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 1：基于dao的验证数据提供者
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 2：设置自定义查询方法
        provider.setUserDetailsService(myUserDetailsServiceImpl);
        // 3：设置加解密方式, 注意一旦设置了加密方式，那么我们需要在我们实现UserDetails的getPassword()方法时，使用同样的加密方式
        //    但是一般我们自己存储的密码已经是加密完成了的，所以一般不需要进行再次进行加密
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 添加Http请求的配置和动态控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);  // 注意这里是个坑， 需要去掉否则我们的配置失效
        http.authorizeRequests()
                .antMatchers("/static/**", "/templates/**").permitAll()
                // .mvcMatchers("/hello").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new Filter() {
                    @Override
                    public void init(FilterConfig filterConfig) throws ServletException {

                    }

                    @Override
                    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                        /**
                         * 通用扩展验证，用于其他项目在引用时，自定义validation
                         */
                        filterChain.doFilter(request, response);
                    }

                    @Override
                    public void destroy() {

                    }
                }, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                // .loginPage("/login")   // 自定义我们自己的登录页面， 默认spring-boot-starter-security集成了默认的登录页面
                .failureUrl("/login?error")
                .successHandler(new MyAuthenticationSuccessHandler())
                // .successForwardUrl("/index")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("/")
                .invalidateHttpSession(true) // 使session无效
                .permitAll();
    }
}
