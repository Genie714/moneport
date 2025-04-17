package com.moneport.backend.controller.transaction;

import com.moneport.framework.dataObject.MapRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionCtr {

    private final TransactionSvc transactionSvc;
    @Operation(summary = "수입/지출 등록")
    @Parameters({
            @Parameter(name = "param", hidden = true), // ezReq Swagger 파라미터 숨김처리
            @Parameter(name = "user_id", description = "회원아이디", required = true),
            @Parameter(name = "type", description = "타입", required = true),
            @Parameter(name = "amount", description = "금액", required = true),
            @Parameter(name = "category_id", description = "카테고리", required = true),
            @Parameter(name = "memo", description = "메모", required = true),
            @Parameter(name = "trans_date", description = "작성날짜", required = true),
    })
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(MapRequest param) {
        transactionSvc.insertTransaction(param);

        return ResponseEntity.ok(Map.of("result", "ok"));
    }
}
