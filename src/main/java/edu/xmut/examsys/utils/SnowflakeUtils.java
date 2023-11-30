package edu.xmut.examsys.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

import java.net.InetAddress;

/**
 * 雪花生成器
 *
 * @author 朔风
 * @date 2023-11-29 20:27
 */
public class SnowflakeUtils {


    private static Snowflake snowflake;

    static {
        long workedId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31;
        snowflake = IdUtil.createSnowflake(workedId, 1);
    }

    public synchronized static long nextId() {
        return snowflake.nextId();
    }

    public synchronized static String nextIdStr() {
        return snowflake.nextIdStr();
    }

}
