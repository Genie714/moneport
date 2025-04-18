package com.moneport.backend.dao.user;

import com.moneport.framework.dataObject.MapRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao {

    /**
     * <p>
     * 아이디 찾기(임시 테스트용)
     * </p>
     *
     * @since 2025-04-10 오전 11:14
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    Map<String, Object> findById(Map<String, Object> param) throws Exception;

    /**
     * <p>
     * 아이디 입력(임시 테스트용)
     * </p>
     *
     * @since 2025-04-10 오전 11:15
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    void insertUser(MapRequest param) throws Exception;

    void updateUser(MapRequest param) throws Exception;

    void deleteUser(MapRequest param) throws Exception;

    Map<String, Object> selectOneUser(MapRequest param);
}
