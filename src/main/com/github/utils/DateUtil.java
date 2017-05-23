package github.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日趋工具类
 *
 * @author Administrator
 */
public class DateUtil {

    private static Log logger = LogFactory.getLog(DateUtil.class);


    public static String formate(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转为字符串
     *
     * @param date    日期
     * @param pattern 规则
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "null";
        }
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd  HH:mm:ss";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转为日期
     *
     * @param date    字符串
     * @param pattern 规则
     * @return
     */
    public static Date format(String date, String pattern) {
        if (date == null || date.equals("") || date.equals("null")) {
            return new Date();
        }
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Date d = null;
        try {
            d = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            logger.info("string to date is error!!!");
        }
        return d;
    }

    public static Date format(String date) {
        return format(date, null);
    }

    public static Date addDay(Date endTime, int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(endTime);
        calendar.add(calendar.DATE, n);
        return calendar.getTime();
    }

    public static Date subDay(Date endTime, int n) {
        if(n <= 0){
            return endTime;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(endTime);
        calendar.set(calendar.DATE, calendar.get(calendar.DATE) - n);
        return calendar.getTime();
    }

    /**
     * 得到指定的时间格式
     * @param time  时间
     * @param pattern 指定格式
     */
    public static String formatStringTime(String time, String pattern) {
        if (time.length() < 9) {
            return "";
        }
        pattern = pattern.trim();
        StringBuffer sb = new StringBuffer();
        String space = pattern.replaceAll("[a-zA-Z]+", "");
        if(StringUtils.isNoneBlank(space)){
            space = space.substring(0, 1);
        }
        try {
            if (pattern.contains("yyyy")) {
                sb.append(time.substring(0, 4));
                sb.append(space);
            }
            if (pattern.contains("yy") && !pattern.contains("yyyy")) {
                sb.append(time.substring(2, 4));
                sb.append(space);
            }
            if (pattern.contains("MM")) {
                sb.append(time.substring(5, 7));
                sb.append(space);
            }
            if (pattern.contains("dd")) {
                sb.append(time.substring(8, 10));
            }
            if (time.length() >= 9) {
                if (pattern.contains("HH")) {
                    if (pattern.contains(" ")) {
                        sb.append(" ");
                    } else {
                        sb.append(space);
                    }
                    sb.append(time.substring(11, 13));
                }
                if (pattern.contains("mm")) {
                    if (pattern.contains(" ") && !sb.toString().contains(" ")) {
                        sb.append(" ");
                    } else {
                        sb.append(space);
                    }
                    sb.append(time.substring(14, 16));
                }
                if (pattern.contains("ss")) {
                    if (pattern.contains(" ") && !sb.toString().contains(" ")) {
                        sb.append(" ");
                    } else {
                        sb.append(space);
                    }
                    sb.append(time.substring(17, 19));
                }
            }
        } catch (Exception e) {
            logger.info("time format error");
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 指定时间的一天开始
     * @param beginTime 一天开始
     */
    public static long getBeginTime(String beginTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long begin = 0L;
        try {
            if (StringUtils.isNotBlank(beginTime)) {
                if (beginTime.equals("0")) {
                    return 0;
                }
                begin = format.parse(beginTime + " 00:00:00").getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return begin;
    }

    /**
     * 指定时间的一天结束
     * @param endTime 一天结束
     */
    public static long getEndTime(String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long begin = 0L;
        try {
            if (StringUtils.isNotBlank(endTime)) {
                begin = format.parse(endTime + " 23:59:59").getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return begin;
    }

    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return format.format(date);
    }
    public static String getDate2(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 通过秒数获取对应的时间
     * @param seconds 秒数
     * @return
     */
    public static String getTimeBySeconds(long seconds, String timeType){
        String result = "";
        try {
            if (timeType.equals("m")) {
                return Long.toString(seconds/60);
            }
            if (timeType.equals("h")) {
                return Double.toString(DecimalUtil.div(seconds, 3600, 1));
            }
            if (timeType.equals("d")) {
                return Double.toString(DecimalUtil.div(seconds, 86400, 1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 得到要求的开始时间
     *
     * @param days 从今天开始向前days天
     */
    public static String getBeginTime(int days) {
        if(days == -1){
            return "";
        }
        return format(subDay(new Date(), days), "yyyy-MM-dd");
    }

    /**
     * 获得要求的时间的long型
     * @param days
     * @return
     */
    public static long getLongBeginTime(int days) {
        if(days == -1){
            return 0;
        }
        return subDay(new Date(), days).getTime();
    }

    /**
     * 获取当天0点
     * @return
     */
    public static long getTodayStartTime(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 获取当天24点
     * @return
     */
    public static long getTodayEndTime(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    //秒数转化成时分秒
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static void main(String[] args) {
    }
}
