package com.moneport.backend.controller.login;

import com.moneport.framework.dataObject.MapRequest;

import java.util.Map;

/**
 * @Project_Name : 스피드온2.0 고도화
 * @Package_Name : com.moneport.backend.controller.login
 * @Class_Name : LoginSvc.java
 * @Description : 로그인 서비스 인터페이스
 * @author : djmoon
 * @since 2025-04-13 오후 15:25
 * @version 1.0
 */
public interface LoginSvc {

    Map<String, Object> login(MapRequest param) throws Exception;

}
