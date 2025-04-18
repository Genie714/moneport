package com.moneport.backend.controller.test;

import com.moneport.framework.dataObject.MapRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/echo")
    public ResponseEntity<?> echo(MapRequest param) {

        // 그대로 반환해서 바인딩된 값 확인
        return ResponseEntity.ok(param);
    }

}
