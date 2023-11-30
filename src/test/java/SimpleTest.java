import cn.hutool.core.lang.generator.SnowflakeGenerator;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.SnowflakeUtils;
import io.jsonwebtoken.*;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 朔风
 * @date 2023-11-13 10:34
 */
public class SimpleTest {

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        String password = "123456";
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        messageDigest.update((password + "salt").getBytes(StandardCharsets.UTF_8));
        byte[] digest = messageDigest.digest();
        int i = 0;

        StringBuffer buf = new StringBuffer();
        for (byte b : digest) {
            i = b;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        // 将计算结果s转换为字符串


        System.out.println(buf);
    }

    @Test
    public void snowflake() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                System.out.println(SnowflakeUtils.nextId());
            }
        }).start();

        Thread.sleep(1000 * 2);

    }

    @Test
    public void snowflakeUtil() {
        byte b = (byte) 999;
        System.out.println(b);
    }


    @Test
    public void generateJWT() throws InterruptedException {

        // JWT头部分信息【Header】
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 载核【Payload】
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", "1234567890");
        payload.put("name", "John Doe");
        payload.put("admin", true);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 3);

        Thread.sleep(3 * 1000);

        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
        System.out.println(token);
    }


    @Test
    public void getInfoByJwt() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImV4cCI6MTcwMTMzMjMzMn0.7dZLbsI5vxtnYYkFKKhHbIbnd8JXMGJhCeMUqEkIkE8";

        JwsHeader header = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getHeader();
        System.out.println(header);


        Claims claims = Jwts
                .parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims);// {sub=1234567890, name=John Doe, admin=true, exp=1663297431}


        String signature = Jwts
                .parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getSignature();
        System.out.println(signature); // Ju5EzKBpUnuIRhDG1SU0NwMGsd9Jl_8YBcMM6PB2C20

    }

    @Test
    public void jwtUtilTest() throws InterruptedException {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        User user = new User();
        user.setId(2312114433L);
        user.setRealName("张三");

        String token = jwtTokenUtil.generateToken(user);
        System.out.println(token);

        Thread.sleep(7 * 1000);

        System.out.println(jwtTokenUtil.validateToken(token) ? "有效" : "无效");

        if (jwtTokenUtil.validateToken(token)) {
            System.out.println(jwtTokenUtil.getUserId(token));
            System.out.println(jwtTokenUtil.getRealName(token));
        }


    }


}
