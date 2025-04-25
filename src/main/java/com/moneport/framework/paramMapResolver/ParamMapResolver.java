package com.moneport.framework.paramMapResolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moneport.framework.dataObject.MapRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Enumeration;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParamMapResolver implements HandlerMethodArgumentResolver {

    private final Gson gson;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MapRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MapRequest params = new MapRequest();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        //- form/formData 매핑
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

        //- json 매핑
        String contentType = request.getContentType();
        log.debug("Content-Type: {}", contentType);
        if (contentType != null && contentType.contains("application/json")) {
           StringBuilder jsonBuilder = new StringBuilder();
            String line;
            try (var reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }

            String json = jsonBuilder.toString();
            log.debug("Raw JSON Body: {}", json);
            if (!json.isBlank()) {
                try {
                    Map<String, Object> jsonMap = gson.fromJson(
                            json, new TypeToken<Map<String, Object>>() {}.getType()
                    );
                    log.debug("Parsed JSON Map: {}", jsonMap);

                    if (jsonMap != null) {
                        params.putAll(jsonMap);
                    }
                } catch (Exception e) {
                    log.warn("JSON 파싱 실패: {}", e.getMessage());
                }
            } else {
                log.warn("JSON 본문이 비어 있음 (request.getReader() 결과 없음)");
            }
        }

        //- path variable
        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables =
                (Map<String, String>) webRequest.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                        RequestAttributes.SCOPE_REQUEST
                );

        if (pathVariables != null) {
            params.putAll(pathVariables);
        }

        log.debug("최종 ParamMapResolver 결과: {}", params);
        return params;
    }

}
