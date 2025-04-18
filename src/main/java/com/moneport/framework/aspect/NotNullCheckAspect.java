package com.moneport.framework.aspect;

import com.moneport.framework.annotation.NotNullCheck;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class NotNullCheckAspect {

    @AfterReturning(pointcut = "@annotation(com.moneport.framework.annotation.NotNullCheck)", returning = "result")
    public void enforceNotNull(JoinPoint joinPoint, Object result) throws Throwable {
        if (result != null) return;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        NotNullCheck annotation = method.getAnnotation(NotNullCheck.class);

        String message = annotation.message();
        String code = "AOP001";
        Class<? extends RuntimeException> exClass = annotation.exception();

        throw exClass.getConstructor(String.class, String.class).newInstance(code, message);
    }
}
