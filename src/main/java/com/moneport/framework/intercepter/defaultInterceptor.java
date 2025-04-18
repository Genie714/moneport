package com.moneport.framework.intercepter;

import com.google.gson.Gson;
import com.moneport.framework.annotation.ValidCheck;
import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class defaultInterceptor implements HandlerInterceptor {

    private final Gson gson;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 핸들러가 메서드일 때만 처리
        if (!(handler instanceof HandlerMethod handlerMethod)) return true;

        // 메서드에 @ValidCheck가 붙어 있는지 확인
        ValidCheck validCheck = handlerMethod.getMethodAnnotation(ValidCheck.class);
        if (validCheck == null) return true;

        // 콘텐츠 타입 확인
        String contentType = request.getContentType();
        Map<String, Object> paramMap;

        if (contentType != null && contentType.contains("application/json")) {
            String json = new BufferedReader(request.getReader()).lines().collect(Collectors.joining());
            paramMap = gson.fromJson(json, Map.class);
        }  else {
            // ✅ Form 파라미터 처리
            paramMap = request.getParameterMap().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue()[0]
                    ));
        }

        for (String key : validCheck.required()) {
            Object value = paramMap.get(key);
            if (value == null || value.toString().isBlank()) {
                throw new AppException("REQ001", key + "는 필수 입력값입니다.");
            }
        }

        return true;
    }

}
