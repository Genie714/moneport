package com.moneport.backend.controller.user;

import com.moneport.framework.dataObject.MapRequest;

import java.util.Map;

public interface UserService {

    Map<String, Object> createUser(MapRequest param);
    Map<String, Object> getUserById(MapRequest param);
    void updateUser(MapRequest param);
    void deleteUser(MapRequest param);

}
