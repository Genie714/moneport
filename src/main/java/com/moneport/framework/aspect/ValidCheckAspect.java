package com.moneport.framework.aspect;

import com.moneport.framework.annotation.ValidCheck;
import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class ValidCheckAspect {

    @Around("@annotation(validCheck)")
    public Object validateParams(ProceedingJoinPoint joinPoint, ValidCheck validCheck) throws Throwable {
        Object[] args = joinPoint.getArgs();

        // MapRequest 찾기
        Map<String, Object> paramMap = Arrays.stream(args)
                .filter(arg -> arg instanceof MapRequest)
                .map(arg -> (MapRequest) arg)
                .findFirst()
                .orElseThrow(() -> new AppException("REQ999", "MapRequest 파라미터가 필요합니다."));

        // 필수값 확인
        for (String key : validCheck.required()) {
            Object value = paramMap.get(key);
            if (value == null || value.toString().isBlank()) {
                log.warn("[ValidCheck] 필수 파라미터 누락: {}", key);
                throw new AppException("REQ001", key + "는 필수 입력값입니다.");
            }
        }

        return joinPoint.proceed();
    }
}