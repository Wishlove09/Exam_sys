package edu.xmut.examsys.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author 朔风
 * @date 2023-12-11 19:05
 */
public class DateTimeUtils {

    /**
     * 判断日期区间
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return -1-未知，0-未开始，1-进行中，2-已结束，
     */
    public static int isRangeDate(Date startTime, Date endTime) {
        DateTime now = DateUtil.date();
        if (now.compareTo(startTime) >= 0 && now.compareTo(endTime) < 0) {
            return 1;
        } else if (now.compareTo(startTime) < 0) {
            return 0;
        } else if (now.compareTo(endTime) > 0) {
            return 2;
        }
        return -1;
    }

}
