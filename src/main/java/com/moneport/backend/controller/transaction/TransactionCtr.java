package com.moneport.backend.controller.transaction;

import com.moneport.framework.annotation.ValidCheck;
import com.moneport.framework.dataObject.MapRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionCtr {

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
        transactionSvc.insertTransaction(param);

        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @Operation(summary = "목록 조회")
    @Parameters({
            @Parameter(name = "param", hidden = true), // ezReq Swagger 파라미터 숨김처리
            @Parameter(name = "key", description = "회원아이디", required = true),
    })
    @ValidCheck(required = {"key"})
    @GetMapping
    public ResponseEntity<Map<String, Object>> schListTransactions(MapRequest param) {
        List<Map<String, Object>> list = transactionSvc.schListTransactions(param);

        return ResponseEntity.ok(Map.of("result", list));
    }

}
