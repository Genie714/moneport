package com.moneport.dao;

import com.moneport.backend.dao.user.UserDao;
import com.moneport.framework.dataObject.MapRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void checkUserDao() {
        try {
            MapRequest param = new MapRequest();
            param.put("username", "trantest");
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
