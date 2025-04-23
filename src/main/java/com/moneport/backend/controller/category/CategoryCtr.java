package com.moneport.backend.controller.category;

import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.global.WebLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryCtr extends WebLogic {

    private final CategorySvc categorySvc;

    @Parameters({
        @Parameter(name = "param", hidden = true),
        @Parameter(name = "userId", description = "회원 아이디", required = true)
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> list(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>();

        List<Map<String, Object>> list = categorySvc.listCategories(param);
        jsonObj.put("result", list);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");
        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "카테고리 등록")
    @Parameters({
        @Parameter(name = "param", hidden = true),
        @Parameter(name = "userId", description = "회원 아이디", required = true),
        @Parameter(name = "name", description = "카테고리 이름", required = true),
        @Parameter(name = "type", description = "카테고리 타입 (income/expense)", required = true)
    })
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>();

        categorySvc.insertCategory(param);
        makeAjaxSysMsg(jsonObj, "SUCCESS", "등록 완료", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "카테고리 수정")
    @Parameters({
        @Parameter(name = "param", hidden = true),
        @Parameter(name = "id", description = "카테고리 ID", required = true, in = ParameterIn.PATH),
        @Parameter(name = "userId", description = "회원 아이디", required = true),
        @Parameter(name = "name", description = "카테고리 이름", required = true),
        @Parameter(name = "type", description = "카테고리 타입 (income/expense)", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>();

        categorySvc.updateCategory(param);
        makeAjaxSysMsg(jsonObj, "SUCCESS", "수정 완료", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "카테고리 삭제")
    @Parameters({
        @Parameter(name = "param", hidden = true),
        @Parameter(name = "id", description = "카테고리 ID", required = true, in = ParameterIn.PATH),
        @Parameter(name = "userId", description = "회원 아이디", required = true)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>();

        categorySvc.deleteCategory(param);
        makeAjaxSysMsg(jsonObj, "SUCCESS", "삭제 완료", "N");

        return ResponseEntity.ok(jsonObj);
    }

}
