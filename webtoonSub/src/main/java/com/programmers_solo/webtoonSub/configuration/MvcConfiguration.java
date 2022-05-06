package com.programmers_solo.webtoonSub.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .order(1)
                .excludePathPatterns("/webtoon/**", "/test/**", "/css/**", "/login", "/*.ico", "/error", "/logout", "/customer/**", "/files/**");
    }
}
