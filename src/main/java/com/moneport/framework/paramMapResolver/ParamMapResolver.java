package com.moneport.framework.paramMapResolver;

import com.google.gson.Gson;
import com.moneport.framework.dataObject.MapRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Enumeration;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ParamMapResolver implements HandlerMethodArgumentResolver {

    private final Gson gson;

    public ParamMapResolver(Gson gson) {
        this.gson = gson;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MapRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MapRequest params = new MapRequest();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            String[] values = request.getParameterValues(key);

            if (values == null) {
                params.put(key, "");
            } else if (values.length > 1) {
                params.put(key, values);
            } else {
                params.put(key, values[0]);
            }
        }

        return params;

        /*
        String json = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        params.putAll(gson.fromJson(json, MapRequest.class));
        return params;

         */
    }

}
