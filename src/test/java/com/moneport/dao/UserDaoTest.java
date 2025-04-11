package com.moneport.dao;

import com.moneport.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:file:./data/moneportdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "mybatis.mapper-locations=classpath:mapper/**/*.xml",
    "mybatis.type-aliases-package=com.moneport.domain"
})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void checkUserDao() {
        try {
            User user = new User(null, "tester", "1234");
            userDao.insertUser(user);

            User found = userDao.findById(user.getId());
            assertThat(found).isNotNull();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
