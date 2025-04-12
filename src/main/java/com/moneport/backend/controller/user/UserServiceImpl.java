package com.moneport.backend.controller.user;

import com.moneport.dao.user.UserDao;
import com.moneport.framework.dataObject.MapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    @Override
    public Map<String, Object> createUser(MapRequest param) {
        Map<String, Object> json = new HashMap<>();

        try {
            userDao.insertUser(param);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    @Override
    public Map<String, Object> getUserById(MapRequest param) {
        Map<String, Object> json = new HashMap<>();
        try {
            Map<String, Object> result = userDao.findById(param);
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(MapRequest param) {
        try {
            userDao.updateUser(param);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(MapRequest param) {
        try {
            userDao.deleteUser(param);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
