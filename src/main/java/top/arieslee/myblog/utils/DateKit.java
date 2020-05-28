package top.arieslee.myblog.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateKit
 * @Description 日期工具类
 * @Author Aries
 * @Date 2019/5/14 10:39
 * @Version 1.0
 **/
public class DateKit {

    public static final int INTERVAL_DAY = 1;
    public static final int INTERVAL_WEEK = 2;
    public static final int INTERVAL_MONTH = 3;
    public static final int INTERVAL_YEAR = 4;
    public static final int INTERVAL_HOUR = 5;
    public static final int INTERVAL_MINUTE = 6;
    public static final int INTERVAL_SECOND = 7;


    /**
     * @return java.util.Date
     * @Description 将日期字符串解析为日期类对象
     * @Param [date 字符串, format 解析类型]
     **/
    public static Date dateParse(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * @return java.lang.String
     * @Description 格式化时间
     * @Param [unixTime 秒为单位的时间戳, ftm 指定格式]
     **/
    public static String formatDateByUnixTime(long unixTime, String ftm) {
        return formatDate(new Date(unixTime * 1000L), ftm);
    }

    /**
     * @return java.lang.String 返回格式后的时间
     * @Description 格式化时间
     * @Param [date, ftm]
     **/
    public static String formatDate(Date date, String ftm) {
        if (date != null && StringUtils.isNotBlank(ftm)) {
            SimpleDateFormat sdf = new SimpleDateFormat(ftm);
            return sdf.format(date);
        }
        return "";
    }

    public static Date dateFormat(String date, String dateFormat) {
        if(date == null) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            try {
                return format.parse(date);
            } catch (Exception ignored) {
            }

            return null;
        }
    }

    public static Date dateFormat(String date) {
        return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateFormat(Date date, String dateFormat) {
        if(date != null) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            if(date != null) {
                return format.format(date);
            }
        }

        return "";
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @Description 获取时间戳（秒为单位）
     **/
    public static int getUnixTimeByDate(Date date) {
        return (int) (date.getTime() / 1000L);
    }

    public static int getCurrentUnixTime(){
        return getUnixTimeByDate(new Date());
    }

    /**
     * @return java.util.Date
     * @Description 在原有时间上增加一段时间，这里待改进，没有考虑每月不一定为30天，每年不一定365天的情况
     * @Param [interval 增加幅度：年、月等, date 原时间, n 增加数量]
     **/
    public static Date dateAdd(int interval, Date date, int n) {
        Long time = date.getTime() / 1000L;
        switch (interval) {
            case 1:
                time += (long) (n * 86400);//天
                break;
            case 2:
                time += (long) (n * 604800);//星期
                break;
            case 3:
                time += (long) (n * 2678400);//月
                break;
            case 4:
                time += (long) (n * 31536000);//年
                break;
            case 5:
                time += (long) (n * 3600);//时
                break;
            case 6:
                time += (long) (n * 60);//分
                break;
            case 7:
                time += (long) n;//秒
        }
        Date result = new Date();
        result.setTime(time * 1000L);
        return result;
    }
}
