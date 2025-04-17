package com.moneport.dao.transaction;

import com.moneport.framework.dataObject.MapRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionDao {

    void insertTransaction(MapRequest param);

}
