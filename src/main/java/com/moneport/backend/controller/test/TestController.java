package com.moneport.backend.controller.test;

import com.moneport.framework.annotation.ValidCheck;
import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/echo")
    public ResponseEntity<?> echo(MapRequest param) {

        // 그대로 반환해서 바인딩된 값 확인
        return ResponseEntity.ok(param);
    }

    @GetMapping("/app-ex")
    public void throwAppException() {
        throw new AppException("TX001", "앱 예외 발생");
    }

    @GetMapping("/illegal-arg")
    public void throwIllegalArg() {
        throw new IllegalArgumentException("잘못된 인자");
    }

    @GetMapping("/generic")
    public void throwGeneric() {
        throw new RuntimeException("예상치 못한 오류");
    }

    @ValidCheck(required = {"amount", "type"})
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(MapRequest param) {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

}
