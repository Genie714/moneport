package com.moneport.backend.controller.transaction;

import com.moneport.dao.transaction.TransactionDao;
import com.moneport.framework.dataObject.MapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionSvc {

    @Autowired
    private TransactionDao transactionDao;

    public void insertTransaction(MapRequest param) {
        transactionDao.insertTransaction(param);
    }

}
