package com.moneport.backend.dao.transaction;

import com.moneport.framework.dataObject.MapRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionDao {

    void insertTransaction(MapRequest param);

    List<Map<String, Object>> schListTransactions(MapRequest param);

    int updateTransaction(MapRequest param);

    int deleteTransaction(MapRequest param);
}
