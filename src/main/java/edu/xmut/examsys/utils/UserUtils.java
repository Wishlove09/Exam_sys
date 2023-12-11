package edu.xmut.examsys.utils;

import javax.servlet.http.HttpServletRequest;

import static edu.xmut.examsys.constants.SystemConstant.AUTHORIZATION;

/**
 * @author 朔风
 * @date 2023-12-11 21:42
 */
public class UserUtils {
    public static Long getUserId(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String userId = new JwtTokenUtil().getUserId(token);
        return Long.parseLong(userId);
    }

    public static String getUserRealName(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        return new JwtTokenUtil().getRealName(token);
    }

}
