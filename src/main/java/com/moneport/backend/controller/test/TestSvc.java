package com.moneport.backend.controller.test;

import com.moneport.framework.annotation.NotNullCheck;
import com.moneport.framework.exception.AppException;
import org.springframework.stereotype.Service;

@Service
public class TestSvc {

    @NotNullCheck(message = "userId는 null일 수 없습니다", exception = AppException.class)
    public String getUserId(String input) {
        if ("notnull".equals(input)) {
            return "USER-123"; // 정상 케이스
        }
        return null; // 테스트용 null 반환
    }
}
