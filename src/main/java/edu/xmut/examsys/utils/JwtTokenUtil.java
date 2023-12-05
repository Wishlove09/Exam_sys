package edu.xmut.examsys.utils;

import edu.xmut.examsys.bean.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
public class JwtTokenUtil {

    private final String secret = "eG11dA==";


    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.ID, user.getId());
        claims.put(Claims.SUBJECT, user.getRealName());
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getPayload(String token) {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /**
     * 生成token的过期时间 6小时
     */
    private Date generateExpirationDate() {
        long expiration = 6 * 60 * 60 * 1000L;
        // long expiration = 10 * 1000L;
        return new Date(System.currentTimeMillis() + expiration);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getRealName(String token) {
        String username;
        try {
            Claims claims = getPayload(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getUserId(String token) {
        Claims claims = getPayload(token);
        return claims.getId();
    }


    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     */
    public synchronized boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        try {
            getExpiredDate(token);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDate(String token) {
        Claims claims = getPayload(token);
        return claims.getExpiration();
    }


}