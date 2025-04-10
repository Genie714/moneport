package com.moneport.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project_Name : moneport
 * @Package_Name : com.moneport.bizlogic.domain
 * @Class_Name : User.java
 * @Description : User(기본 테스트 용)
 * @author : djmoon
 * @since 2025-04-10 오전 11:10
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;

}
