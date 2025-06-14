package com.moneport.jwt;

import com.moneport.framework.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("JWT가 유효하면 인증된 요청이 성공한다")
    public void testAuthenticatedEndpointWithJwt() throws Exception {
        // given
        String token = jwtUtil.generateToken("spiderman"); // or generateToken("spiderman") 등 username 포함

        // when + then
        mockMvc.perform(get("/api/auth/secure")  // JWT 인증이 필요한 API
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("인증된 사용자만 볼 수 있습니다."));
    }

}