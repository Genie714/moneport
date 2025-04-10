package com.moneport.mapper;

import com.moneport.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Project_Name : 스피드온2.0 고도화
 * @Package_Name : com.moneport.mapper
 * @Class_Name : UserDaoTest.java
 * @Description : 테스트 작성 테스트
 * @author : djmoon
 * @since 2025-04-10 오전 11:33
 * @version 1.0
 */
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void insertAndFindById() {
        try {
            // given
            User user = new User(null, "testuser", "1234");
            userDao.insert(user);

            // when
            User result = userDao.findById(user.getId());

            // then
            assertThat(result).isNotNull();
            assertThat(result.getUsername()).isEqualTo("testuser");
            assertThat(result.getPassword()).isEqualTo("1234");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
