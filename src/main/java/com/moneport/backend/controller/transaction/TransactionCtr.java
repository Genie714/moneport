package com.moneport.backend.controller.transaction;

import com.moneport.framework.annotation.ValidCheck;
import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.global.WebLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionCtr extends WebLogic {

    private final TransactionSvc transactionSvc;

    @Operation(summary = "수입/지출 등록")
    @Parameters({
            @Parameter(name = "param", hidden = true), // ezReq Swagger 파라미터 숨김처리
            @Parameter(name = "userId", description = "회원아이디", required = true),
            @Parameter(name = "type", description = "타입", required = true),
            @Parameter(name = "amount", description = "금액", required = true),
            @Parameter(name = "categoryId", description = "카테고리", required = true),
            @Parameter(name = "memo", description = "메모", required = true),
            @Parameter(name = "transDate", description = "작성날짜", required = true),
    })
    @ValidCheck(required = {"amount", "type", "userId"})
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        transactionSvc.insertTransaction(param);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "목록 조회")
    @Parameters({
            @Parameter(name = "param", hidden = true), // ezReq Swagger 파라미터 숨김처리
            @Parameter(name = "key", description = "회원아이디", required = true),
    })
    @ValidCheck(required = {"key"})
    @GetMapping
    public ResponseEntity<Map<String, Object>> schListTransactions(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        List<Map<String, Object>> list = transactionSvc.schListTransactions(param);
        jsonObj.put("result", list);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }
    
    @Operation(summary = "수입/지출 수정")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "key", description = "수입 지출 번호", required = true),
            @Parameter(name = "userId", description = "회원아이디", required = true),
            @Parameter(name = "type", description = "타입", required = true),
            @Parameter(name = "amount", description = "금액", required = true),
            @Parameter(name = "categoryId", description = "카테고리", required = true),
            @Parameter(name = "memo", description = "메모", required = true),
            @Parameter(name = "transDate", description = "작성날짜", required = true),
    })
    @ValidCheck(required = {"key"})
    @PutMapping("/{key}")
    public ResponseEntity<Map<String, Object>> updateTransaction(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        transactionSvc.updateTransaction(param);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

    @Operation(summary = "수입/지출 삭제")
    @Parameters({
            @Parameter(name = "param", hidden = true),
            @Parameter(name = "key", description = "수입 지출 번호", required = true),
    })
    @DeleteMapping("/{key}")
    public ResponseEntity<Map<String, Object>> deleteTransaction(MapRequest param) {
        Map<String, Object> jsonObj = new HashMap<>(); // return json object

        transactionSvc.deleteTransaction(param);

        makeAjaxSysMsg(jsonObj, "SUCCESS", "SUCCESS", "N");

        return ResponseEntity.ok(jsonObj);
    }

}
