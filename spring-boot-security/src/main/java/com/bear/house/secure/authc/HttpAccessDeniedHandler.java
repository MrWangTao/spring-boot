package com.bear.house.secure.authc;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问拒绝处理器
 *
 * @author WangTao
 *         Created at 18/6/6 下午3:23.
 */
public class HttpAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        // ResponseUtils.writeResponse(response, Result.fail403(accessDeniedException.getMessage()));
    }

}
