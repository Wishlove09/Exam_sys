import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

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
}
