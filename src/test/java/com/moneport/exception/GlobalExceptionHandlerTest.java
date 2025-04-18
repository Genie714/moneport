package com.moneport.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("AppException 발생 시 400 에러와 함께 커스텀 코드/메시지를 반환한다")
    @WithMockUser(username = "testUser")
    @Test
    void shouldReturnBadRequest_whenAppExceptionThrown() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/app-ex"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("TX001"))
                .andExpect(jsonPath("$.message").value("앱 예외 발생"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + json);
    }

    @DisplayName("IllegalArgumentException 발생 시 400 에러와 표준 메시지를 반환한다")
    @WithMockUser(username = "testUser")
    @Test
    void shouldReturnBadRequest_whenIllegalArgumentThrown() throws Exception {
        MvcResult result = mockMvc.perform(get("/test/illegal-arg"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 인자"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + json);
    }

    @DisplayName("일반 예외(RuntimeException 등) 발생 시 500 에러와 상세 메시지를 포함한 응답을 반환한다")
    @WithMockUser(username = "testUser")
    @Test
    void shouldReturnInternalServerError_whenUnexpectedExceptionThrown() throws Exception {
         MvcResult result = mockMvc.perform(get("/test/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("서버 내부 오류가 발생했습니다."))
                .andExpect(jsonPath("$.detail").value("예상치 못한 오류"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + json);
    }

}
