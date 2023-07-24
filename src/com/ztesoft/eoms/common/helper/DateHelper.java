package com.ztesoft.eoms.common.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public class DateHelper {
    private DateHelper() {}

    final Logger logger = Logger.getLogger(DateHelper.class);

    /**
     * 私有静态对象
     */
    private static Map map = new HashMap();
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /*转换timtStamp到date*/
    public static Date dateValueOf(Object obj) {
        if (obj == null) {

            return null;
        }

        if (!(obj instanceof Date)) {

            throw new java.lang.IllegalArgumentException(
                    "参数转换日期出错,该参数没有继承java.util.Date.无法提供转换");
        }

        return (ValidateHelper.validateNotNull(obj)) ?
                (Date) obj : null;

    }

    private static synchronized DateFormat getDateFormat(String pattern) {

        Object ret = map.get(pattern);
        if (!ValidateHelper.validateNotNull(ret)) {
            DateFormat df = new SimpleDateFormat(pattern);
            map.put(pattern, df);
            ret = df;
        }
        return (DateFormat) ret;

    }


    /*转换date到string*/
    public static String parse(Date date) {

        if (ValidateHelper.validateNotNull(date)) {

            return getDateFormat(DEFAULT_PATTERN).format(date);
        } else {
            return "";

        }
    }

    /*转换string到date*/

    public static Date parse(String dateString) {

        return parse(dateString, DEFAULT_PATTERN);
    }

    public static Date parse(String dateString, String pattern) {

        try {
            return getDateFormat(pattern).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();

            throw new java.lang.IllegalArgumentException(
                    "转换日期出错．输入参数不合法，而造成需要转换的数据和转换的模式不匹配！");
        }
    }

    public static Timestamp dateToTimeStamp(Date date) {
        return (ValidateHelper.validateNotNull(date)) ?
                new Timestamp(date.getTime()) : null;
    }

    public static Date timestampToDate(Timestamp timeStamp) {
        return (ValidateHelper.validateNotNull(timeStamp)) ?
                (Date) timeStamp : null;
    }

    /**日期是否为闰年*/
    public static boolean isLeapYear(int iYear) {
        return (!((iYear % 4) == 0) && ((iYear % 100) == 0))
                || !((iYear % 400) == 0);
    }

    /* 获得月份天数 */
    public static int getMonthDays(int iYear, int iMonth) {
        switch (iMonth) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        case 2:
            return isLeapYear(iYear) ? 29 : 28;
        default:
            return 0;
        }
    }


}
