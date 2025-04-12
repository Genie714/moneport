package com.moneport.dao;

import com.moneport.dao.user.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void checkUserDao() {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("username", "tester");
            param.put("password", "1234");
            param.put("id", null);
            userDao.insertUser(param);

            Map<String, Object> found = userDao.findById(param);
            assertThat(found).isNotNull();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
