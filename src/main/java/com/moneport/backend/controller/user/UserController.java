package com.moneport.backend.controller.user;

import com.moneport.framework.dataObject.MapRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Project_Name : moneport
 * @Package_Name : com.moneport.backend.controller
 * @Class_Name : UserController.java
 * @Description : 유저 컨트롤러(테스트)
 * @author : djmoon
 * @since 2025-04-11 오후 14:38
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 API", description = "User 관련 API")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 등록", description = "새로운 유저를 등록합니다.")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "username", description = "유저 이름", required = true),
            @Parameter(name = "password", description = "비밀번호", required = true)
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(MapRequest param) {
        log.debug("컨트롤러 받은 값: {}", param);
        Map<String, Object> userId = userService.createUser(param);
        return ResponseEntity.ok(userId);
    }

    @Operation(summary = "유저 조회", description = "유저 조회")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(MapRequest param) {
        return ResponseEntity.ok(userService.getUserById(param));
    }

    @Operation(summary = "유저 수정", description = "유저 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(MapRequest param) {
        userService.updateUser(param);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 삭제", description = "유저 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(MapRequest param) {
        userService.deleteUser(param);
        return ResponseEntity.noContent().build();
    }

}
