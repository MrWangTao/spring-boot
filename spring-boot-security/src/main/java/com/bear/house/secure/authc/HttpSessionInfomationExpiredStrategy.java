package com.bear.house.secure.authc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WangTao
 *         Created at 18/6/6 下午3:28.
 */
public class HttpSessionInfomationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        // ResponseUtils.writeResponse(response, Result.fail403(messageSourceAccessor.getMessage("", "已经被最新登录替代")));
    }
}
