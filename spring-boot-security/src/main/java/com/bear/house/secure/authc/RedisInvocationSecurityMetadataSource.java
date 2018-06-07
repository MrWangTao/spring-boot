package com.bear.house.secure.authc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangTao
 *         Created at 18/6/6 下午3:30.
 */
public class RedisInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisInvocationSecurityMetadataSource.class);
    private String permissionRedisKey = "permission_redis_key";

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Map<String, List<String>>> template;
    @Value("${com.guns21.security.anonymous.disable:true}")
    private boolean anonymous;
    /**
     * 资源和角色的映射关系
     */
    /*@Autowired
    private ResourceRoleMapping resourceRoleMapping;*/
    //    private HashMap<String, Collection<ConfigAttribute>> map =null;

    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 获取访问权限的角色集合
     * 返回空集合是表示白名单，任何人都有权限
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttributes = new ArrayList<>();
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        //取redis中的数据
        // BoundHashOperations 来自spring-data-redis
        BoundHashOperations<String, String,  List<String>> ops = template.boundHashOps(permissionRedisKey);
        String requestURI = request.getRequestURI();
        List<String> roles = ops.get(requestURI); //TODO 添加过期时间
        if (ObjectUtils.isEmpty(roles)) {
            // 提供数据来源; 资源和角色的映射关系
            // roles = resourceRoleMapping.listRole(requestURI);
            ops.put(requestURI, roles);
        }

        /**
         * 如果对应的资源没有配置角色：
         * 1.返回空集合是白名单
         * 2.返回非空集合是黑名单,匿名访问时是黑名单
         */
        if (null == roles || roles.size() == 0) {
            LOGGER.warn("url {} hasn't roles", requestURI);
            if (anonymous) {
                return Collections.singleton(new SecurityConfig(ROLE_ANONYMOUS));
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        return roles.stream().map(r -> new SecurityConfig(r))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
