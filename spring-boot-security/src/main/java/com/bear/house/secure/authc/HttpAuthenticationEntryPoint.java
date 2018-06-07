package com.bear.house.secure.authc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证入口点<br>
 *
 * @Slf4j 注入日志
 * @author WangTao
 *         Created at 18/6/6 下午3:25.
 */
@Slf4j
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        log.warn("commence for url [{}] with exception {}", request.getRequestURI(), e.toString());
        log.debug("commence ", e);
        // ResponseUtils.writeResponse(response, Result.fail401(messageSourceAccessor.getMessage("","请登录")));
    }
}
