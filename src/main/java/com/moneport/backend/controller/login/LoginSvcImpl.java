package com.moneport.backend.controller.login;

import com.moneport.dao.user.UserDao;
import com.moneport.framework.dataObject.MapRequest;
import com.moneport.framework.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginSvcImpl implements LoginSvc {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> login(MapRequest param) throws Exception {

        Map<String, Object> user = userDao.selectOneUser(param);

        return user;
    }

}
