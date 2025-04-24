package com.moneport.backend.controller.user;

import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.global.WebLogic;
import com.moneport.framework.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
public class UserController extends WebLogic {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "유저 등록", description = "새로운 유저를 등록합니다.")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "username", description = "유저 이름", required = true),
            @Parameter(name = "password", description = "비밀번호", required = true)
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        //- 유저 생성
        userService.createUser(param);

        String token = jwtUtil.generateToken((String)param.get("username"));
        jsonObj.put("token", token);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "유저 조회", description = "유저 조회")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "id", description = "유저 아이디", required = true)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        //- 유저 조회
        jsonObj.put("user", userService.getUserById(param));

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "유저 수정", description = "유저 수정")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "id", description = "유저 아이디", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        //- 유저 수정
        userService.updateUser(param);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "유저 삭제", description = "유저 삭제")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "id", description = "유저 아이디", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        userService.deleteUser(param);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

}
