package com.moneport.annotation;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "testUser")
public class ValidCheckInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("필수 파라미터 누락 시 AppException 발생")
    void shouldThrowAppExceptionWhenRequiredParamMissing() throws Exception {
        MvcResult result = mockMvc.perform(post("/test/validate")
                        .contentType("application/x-www-form-urlencoded")
                        .param("type", "CASH")) // amount 빠짐
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("REQ001"))
                .andExpect(jsonPath("$.message").value("amount는 필수 입력값입니다."))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + json);
    }

    @Test
    @DisplayName("필수 파라미터 모두 존재 시 성공 응답")
    void shouldPassWhenAllParamsPresent() throws Exception {
        MvcResult result = mockMvc.perform(post("/test/validate")
                        .contentType("application/x-www-form-urlencoded")
                        .param("amount", "10000")
                        .param("type", "CARD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andReturn();

        String resultJson = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + resultJson);
    }

    @Test
    @DisplayName("JSON 요청에서 필수 파라미터 누락 시 AppException 발생")
    void shouldThrowAppExceptionOnJsonRequest() throws Exception {
        String json = """
            {
              "type": "TRANSFER"
            }
            """;

        MvcResult result = mockMvc.perform(post("/test/validate")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("REQ001"))
                .andExpect(jsonPath("$.message").value("amount는 필수 입력값입니다."))
                .andReturn();

        String resultJson = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + resultJson);
    }

    @Test
    @DisplayName("JSON 요청에서 모든 필수 파라미터가 있을 때 성공")
    void shouldPassWithValidJsonParams() throws Exception {
        String json = """
            {
              "amount": "20000",
              "type": "BANK"
            }
            """;

        MvcResult result = mockMvc.perform(post("/test/validate")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andReturn();

        String resultJson = result.getResponse().getContentAsString();
        System.out.println("응답 JSON: " + resultJson);
    }
}
