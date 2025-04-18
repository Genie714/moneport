package com.moneport.annotation;

import com.moneport.backend.controller.test.TestSvc;
import com.moneport.framework.exception.AppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser(username = "testUser")
public class NotNullCheckAspectTest {

    @Autowired
    TestSvc testSvc;

    @Test
    @DisplayName("@NotNullCheck가 붙은 메서드가 null을 반환하면 예외가 발생한다")
    void shouldThrowExceptionWhenReturnValueIsNull() {
        AppException exception = assertThrows(AppException.class, () -> {
            testSvc.getUserId("test");
        });

        assertEquals("userId는 null일 수 없습니다", exception.getMessage());
    }

    @Test
    @DisplayName("@NotNullCheck가 붙은 메서드가 null이 아닌 값을 반환하면 정상 동작한다")
    void shouldPassWhenReturnValueIsNotNull() {
        String value = testSvc.getUserId("notnull"); // notnull 입력 시 null이 아닌 값 반환하도록 내부 조정 필요
        assertNotNull(value);
    }
}
