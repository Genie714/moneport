package com.moneport.backend.controller.login;

import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.global.WebLogic;
import com.moneport.framework.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class loginCtr extends WebLogic {

    private final LoginSvc loginSvc;
    private final JwtUtil jwtUtil;

    /**
     * <p>
     * 로그인
     * </p>
     *
     * @since 2025-04-13 오후 15:21
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @Parameters({ // 로그인 파라미터 설정
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "username", description = "회원 이름", required = true),
            @Parameter(name = "password", description = "회원 비밀번호", required = true),
    })
    public ResponseEntity<Map<String, Object>> login(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object
    
        try {
            // 로그인 시도
            Map<String, Object> result = loginSvc.login(param);

            if (result == null || "N".equals(result.get("LOGIN_YN"))) {
                makeAjaxSysMsg(jsonObj, "Fail", "회원 정보가 일치하지 않습니다.", "Y");
                return ResponseEntity.ok(jsonObj);
            }
            
            // Jwt 토큰 발행
            String token = jwtUtil.generateToken((String) result.get("username"));

            jsonObj.put("user", result);
            jsonObj.put("token", token);

            makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return ResponseEntity.ok(jsonObj);
    }

    @GetMapping("/secure")
    public ResponseEntity<?> secureEndpoint() {
        return ResponseEntity.ok(Map.of("msg", "인증된 사용자만 볼 수 있습니다."));
    }

}
