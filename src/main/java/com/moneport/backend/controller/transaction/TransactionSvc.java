package com.moneport.backend.controller.transaction;

import com.moneport.backend.dao.transaction.TransactionDao;
import com.moneport.framework.annotation.NotNullCheck;
import com.moneport.framework.dataObject.MapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionSvc {

    @Autowired
    private TransactionDao transactionDao;

    public void insertTransaction(MapRequest param) {
        transactionDao.insertTransaction(param);
    }

    public List<Map<String, Object>> schListTransactions(MapRequest param) {
        return transactionDao.schListTransactions(param);
    }

}
