package com.moneport.framework.global;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project_Name : moneport
 * @Package_Name : com.moneport.framework.global
 * @Class_Name : WebLogic.java
 * @Description : 컨트롤러 공용 로직
 * @author : djmoon
 * @since 2025-04-13 오전 10:20
 * @version 1.0
 */
@Slf4j
public class WebLogic {

    /**
     * <p>
     * 시스템 메세지 생성
     * </p>
     *
     * @since 2025-04-13 오전 10:21
     * @author djmoon
     ***********************************************************
     * @modi name :
     * @modi date :
     * @modi desc :
     */
    public Map<String, Object> makeAjaxSysMsg(Map<String, Object> jsonObj, String resultCd, String msg, String showYn) {
        Map<String, Object> sysMsg = new HashMap<>();

        sysMsg.put("sysCd", resultCd);
		sysMsg.put("sysMsg", msg);
		sysMsg.put("showYn", showYn);

        jsonObj.put("sysMessage", sysMsg);

        return jsonObj;
    }

}
