package edu.xmut.examsys.web.filter;

import com.alibaba.fastjson2.JSON;
import edu.xmut.examsys.constants.SystemConstant;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.R;
import fun.shuofeng.myspringmvc.filter.SecurityFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 朔风
 * @date 2023-11-30 11:24
 */
public class LoginFilter extends SecurityFilter {

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    private final Logger log = LoggerFactory.getLogger(LoginFilter.class);


    @Override
    public void doChain(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        // 从请求头获得jwt的token
        String token = request.getHeader(SystemConstant.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            // token不存在
            tokenNotExist(response);
            return;
        }
        // 判断token是否有效
        if (jwtTokenUtil.validateToken(token)) {
            String userId = jwtTokenUtil.getUserId(token);
            String realName = jwtTokenUtil.getRealName(token);
            log.info("当前登录的用户为：{}-{}", userId, realName);
            // 放行
            chain.doFilter(request, response);
            log.info("Token有效>>>>>>>>>>>>>>>>>放行");
        } else {
            // token已过期
            tokenIsExpired(response);
        }
    }

    private void tokenIsExpired(HttpServletResponse response) throws IOException {
        log.info("Token已失效，拦截请求");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(R.fail("token已失效")));
    }

    private void tokenNotExist(HttpServletResponse response) throws IOException {
        log.info("Token不存在，拦截请求");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(R.fail("用户未登录，token不存在").code(HttpServletResponse.SC_UNAUTHORIZED)));
    }


}
