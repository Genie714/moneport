package com.moneport.backend.config;

import com.moneport.framework.paramMapResolver.ParamMapResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ParamMapResolver paramMapResolver;

    public WebConfig(ParamMapResolver paramMapResolver) {
        this.paramMapResolver = paramMapResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(paramMapResolver);
    }
}
