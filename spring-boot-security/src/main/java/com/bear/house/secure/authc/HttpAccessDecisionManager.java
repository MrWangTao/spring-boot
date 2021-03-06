package com.bear.house.secure.authc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 通过实现访问决策管理器AccessDecisionManager接口，实现自己的决策管理器
 * @author WangTao
 *         Created at 18/6/6 下午3:16.
 */
public class HttpAccessDecisionManager implements AccessDecisionManager {

    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    @Value("${com.guns21.security.anonymous.disable:true}")
    private boolean anonymous;
    /**
     * 信息资源访问者
     */
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    /**
     * 判断configAttribute中找角色在authentication中是否存在，如果不存在throws 异常
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        /**
         *  1：如果url对应的角色列表为空，禁止访问
         */
        if (null == configAttributes || configAttributes.size() == 0) {
            throw new AccessDeniedException(messageSourceAccessor.getMessage("","没有访问权限"));
        }
        /**
         * 2.启用匿名访问时，检查角色列表是否包含匿名用户
         */
        if (!anonymous && configAttributes.stream().map(ca -> ca.getAttribute()).anyMatch(role -> role.equals(ROLE_ANONYMOUS))) {
            return;
        }

        /**
         * 3.没有授权用户，并且授权用户没有权限
         */
        if (null == authentication || authentication.getAuthorities() == null || authentication.getAuthorities().size() == 0) {
            throw new AccessDeniedException(messageSourceAccessor.getMessage("","没有访问权限"));
        }

        /**
         * 4.用户权限检查
         *
         */
        ConfigAttribute c;
        String needRole;
        for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(messageSourceAccessor.getMessage("","没有访问权限"));

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
