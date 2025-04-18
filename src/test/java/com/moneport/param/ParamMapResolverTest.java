package com.moneport.param;

import com.moneport.backend.controller.test.TestController;
import com.moneport.framework.paramMapResolver.ParamMapResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = TestController.class)
@Import({ParamMapResolver.class})
public class ParamMapResolverTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("form Data 요청 정상 파싱")
    void formDataParsingTest() throws Exception {
        mockMvc.perform(post("/test/echo")
                        .with(csrf())
                        .with(user("TestHI"))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "moon")
                        .param("amount", "10000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("moon"))
                .andExpect(jsonPath("$.amount").value("10000"));
    }

    @Test
    @DisplayName("JSON 요청 정상 파싱")
    void jsonParsingTest() throws Exception {
        String json = """
            {
              "username": "moon",
              "amount": 10000
            }
            """;

        mockMvc.perform(post("/test/echo")
                        .with(csrf())
                        .with(user("TestHI"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("moon"))
                .andExpect(jsonPath("$.amount").value(10000));
    }

    @Test
    @DisplayName("빈 JSON 요청은 무시")
    void emptyJsonIgnore() throws Exception {
        mockMvc.perform(post("/test/echo")
                        .with(csrf())
                        .with(user("TestHI"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("   "))
                        .andExpect(jsonPath("$.username").doesNotExist())
                        .andExpect(jsonPath("$.amount").doesNotExist());
    }

    @Test
    @DisplayName("잘못된 JSON 파싱 실패 시 빈 응답 반환")
    void wrongJsonParsing() throws Exception {
        String badJson = "{ username: 'moon' ";  // 유효하지 않은 JSON

        mockMvc.perform(post("/test/echo")
                        .with(csrf())
                        .with(user("TestHI"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.username").doesNotExist())
                        .andExpect(jsonPath("$.amount").doesNotExist());
    }

}
