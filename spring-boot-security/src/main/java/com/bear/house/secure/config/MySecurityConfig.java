package com.bear.house.secure.config;

import com.bear.house.secure.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Configuration 标识为配置类, 如果不添加，则所有配置失效
 * @author WangTao
 * Created at 18/6/4 下午2:49.
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsServiceImpl;

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
         * 基于内存的权限设置: 只需要将对应的信息添加到这里就可以了
         */
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("bear").password("123456").roles("ADMIN", "USER")
                .and()
                .withUser("bee").password("123456").roles("USER");*/

        /**
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
                .formLogin()
                // .loginPage("/login")   // 自定义我们自己的登录页面， 默认spring-boot-starter-security集成了默认的登录页面
                .failureUrl("/login?error")
                // .successForwardUrl("/index")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("/")
                .invalidateHttpSession(true) // 使session无效
                .permitAll();
    }
}
