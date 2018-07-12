package com.bear.house.mybatis.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author WangTao
 *         Created at 18/7/12 上午11:00.
 */
@Configuration
public class EnumHandlerAutoConfiguration {

    @PostConstruct
    public void init() throws ClassNotFoundException {
        // 1.创建类路径扫描候选组件提供者，不使用默认的过滤
        final ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        // 2.添加过滤格式
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*Enum")));
        // 3.获取在某个包下定义的类
        final Set<BeanDefinition> enumComponents = provider.findCandidateComponents("com.bear.house.mybatis.enums");
        // 4.从类定义实例中加载Class类
        for (BeanDefinition bean: enumComponents) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
        }
    }

}
