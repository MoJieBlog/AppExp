package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-12
 */
public final class DateTimeUtils {

    /**
     * 时间转时间戳
     * @param timeStr
     * @param sdf
     * @return
     */
    public static long timeStr2timeLong(String timeStr,String sdf){
        long timeStamp = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        Date d;
        try {
            d = simpleDateFormat.parse(timeStr);
            timeStamp = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 时间戳转时间
     * @param timeLong
     * @param sdf
     * @return
     */
    public static String timeLong2timeStr(String timeLong, String sdf){
        long time = Long.parseLong(timeLong);
        if (timeLong.length() <= 10) {//如果返回的是秒，修改为毫秒单位
            time = time * 1000;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        String sd = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(time))));
        return sd;
    }
}
