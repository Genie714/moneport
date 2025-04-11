package com.moneport.dao;

import com.moneport.backend.domain.User;
import org.apache.ibatis.annotations.Mapper;

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
    User findById(Long id) throws Exception;

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
    void insertUser(User user) throws Exception;

}
