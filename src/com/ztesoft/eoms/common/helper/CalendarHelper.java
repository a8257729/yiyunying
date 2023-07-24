package com.ztesoft.eoms.common.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CalendarHelper {

    /**����Singleton���ģʽ�����е�Ψһʵ��*/
    private static CalendarHelper instance = new CalendarHelper();

    public static CalendarHelper getInstance() {
        return instance;
    }

    private static char DAY_DELIMITER = '-';
    public static int YEAR = Calendar.YEAR;
    public static int MONTH = Calendar.MONDAY;
    public static int DAY = Calendar.DAY_OF_MONTH;
    public static int HOUR = Calendar.HOUR_OF_DAY;
    public static int MINUTE = Calendar.MINUTE;
    public static int SECOND = Calendar.SECOND;
   

    /**
     * ȡ�õ�ǰ����
     * @return YYYY-MM-DD
     */
    public String getDate() {
        return getDateTime().substring(0, 10);
    }

    /**
     *  Date��ת��Ϊ�ַ���
     * @return YYYY-MM-DD
     */

    public String getDate(Date date) {
        return getDateTime(date).substring(0, 10);
    }

    /**
     * ȡ�õ�ǰʱ��
     * @return  HH:MM:SS
     */
    public String getTime() {
        return getDateTime().substring(11, 19);
    }

    /**
     * �õ���ǰ�����ڼ�������һΪ1��������Ϊ7
     * @return int
     */
    public int getCurWeekDay(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int ret = gc.get(GregorianCalendar.DAY_OF_WEEK);
        if (ret == 1) {
            ret = 7;
        }
        else {
            ret = ret - 1;
        }
        return ret;
    }

    /**
     * ȡ�õ�ǰ������ʱ��
     * @return YYYY-MM-DD HH:MM:DD
     */
    public String getDateTime() {
        return getDateTime(new GregorianCalendar());
    }

    /**
     * Date��ת��Ϊ�ַ���
     * @param date Date ����ʱ��
     * @return YYYY-MM-DD HH:MM:DD
     */
    public String getDateTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return getDateTime(calendar);
    }

    /**
     * ����������������ʱ��
     * @param calendar  ����
     * @return YYYY-MM-DD HH:MM:DD
     */
    private String getDateTime(Calendar calendar) {
        StringBuffer buf = new StringBuffer("");

        buf.append(calendar.get(calendar.YEAR));
        buf.append(DAY_DELIMITER);
        buf.append(calendar.get(calendar.MONTH) + 1 > 9 ? calendar.get(calendar.MONTH) + 1 + "" :
                   "0" + (calendar.get(calendar.MONTH) + 1));
        buf.append(DAY_DELIMITER);
        buf.append(calendar.get(calendar.DAY_OF_MONTH) > 9 ? calendar.get(calendar.DAY_OF_MONTH) + "" :
                   "0" + calendar.get(calendar.DAY_OF_MONTH));
        buf.append(" ");
        buf.append(calendar.get(calendar.HOUR_OF_DAY) > 9 ? calendar.get(calendar.HOUR_OF_DAY) + "" :
                   "0" + calendar.get(calendar.HOUR_OF_DAY));
        buf.append(":");
        buf.append(calendar.get(calendar.MINUTE) > 9 ? calendar.get(calendar.MINUTE) + "" :
                   "0" + calendar.get(calendar.MINUTE));
        buf.append(":");
        buf.append(calendar.get(calendar.SECOND) > 9 ? calendar.get(calendar.SECOND) + "" :
                   "0" + calendar.get(calendar.SECOND));
        return buf.toString();
    }

    /**
     * �����ꡢ�¡��շ�������ʱ��
     * @param year ��
     * @param month ��
     * @param year ��
     * @return YYYY-MM-DD
     */
    public String getDateTime(int year, int month, int day) {
        StringBuffer buf = new StringBuffer("");

        buf.append(year);
        buf.append(DAY_DELIMITER);
        buf.append(month > 9 ? month + "" : "0" + month);
        buf.append(DAY_DELIMITER);
        buf.append(day > 9 ? day + "" : "0" + day);
        return buf.toString();
    }

    /**
     * �����¡��շ�������ʱ��    
     * @param month ��
     * @param year ��
     * @return MM-DD
     */
    public String getDateTime(int month, int day) {
        StringBuffer buf = new StringBuffer("");       
        buf.append(month > 9 ? month + "" : "0" + month);
        buf.append(DAY_DELIMITER);
        buf.append(day > 9 ? day + "" : "0" + day);
        return buf.toString();
    }
    /**
     * * �����ꡢ�¡��ա�ʱ���֡��뷵������ʱ��
     * @param year ��
     * @param month ��
     * @param day ��
     * @param hour ʱ
     * @param minute ��
     * @param second ��
     * @return String YYYY-MM-DD HH:MM:SS
     */

    public String getDateTime(int year, int month, int day, int hour, int minute, int second) {
        StringBuffer buf = new StringBuffer("");
        buf.append(year);
        buf.append(DAY_DELIMITER);
        buf.append(month > 9 ? month + "" : "0" + month);
        buf.append(DAY_DELIMITER);
        buf.append(day > 9 ? day + "" : "0" + day);
        buf.append(" ");
        buf.append(hour > 9 ? hour + "" : "0" + hour);
        buf.append(":");
        buf.append(minute > 9 ? minute + "" : "0" + minute);
        buf.append(":");
        buf.append(second > 9 ? second + "" : "0" + second);
        return buf.toString();
    }

    /**
     * ��ָ���������а�ĳ��ʱ���������ָ������
     * @param datetime YYYY-MM-DD HH:MM:SS
     * @param type YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
     * @param step ���� ��������������
     * @return  �ı�������ʱ�� YYYY-MM-DD HH:MM:SS
     */
    public String getPreDateTime(String datetime, int type, int step) {

        Calendar calendar = new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)),
                                                  Integer.parseInt(datetime.substring(5, 7)) - 1,
                                                  Integer.parseInt(datetime.substring(8, 10)),
                                                  Integer.parseInt(datetime.substring(11, 13)),
                                                  Integer.parseInt(datetime.substring(14, 16)),
                                                  Integer.parseInt(datetime.substring(17, 19))
                                                  );
        calendar.add(type, step);
        return getDateTime(calendar);
    }

    /**
     * �����ַ�������ȡ����������
     * @param datetime String  YYYY-MM-DD HH:MM:SS
     * @return Calendar
     */
    public Calendar getCalendar(String datetime) {
        return new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)),
                                     Integer.parseInt(datetime.substring(5, 7)) - 1,
                                     Integer.parseInt(datetime.substring(8, 10)),
                                     Integer.parseInt(datetime.substring(11, 13)),
                                     Integer.parseInt(datetime.substring(14, 16)),
                                     Integer.parseInt(datetime.substring(17, 19))
                                     );
    }

    /**
     * �����ַ�������ȡ����������
     * @param datetime YYYY-MM-DD HH:MM:SS
     * @throws ParseException
     * @return Date
     */
    public Date getDateByFormat(String datetime,String pattern) throws ParseException {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.parse(datetime);
    }

    /**
     * ��ָ���������а�ĳ��ʱ���������ָ������
     * @param datetime YYYY-MM-DD HH:MM:SS
     * @param type YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
     * @param step ���� ��������������
     * @return  �ı�������ʱ�� YYYY-MM-DD
     */
    public String getPreDate(String date, int type, int step) {
        Calendar calendar = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
                                                  Integer.parseInt(date.substring(5, 7)) - 1,
                                                  Integer.parseInt(date.substring(8, 10)),
                                                  0,
                                                  0,
                                                  0
                                                  );
        calendar.add(type, step);
        return getDateTime(calendar).substring(0, 10);
    }

    /**
     * ȡ�õ�ǰ���ڵ�����
     * @return YYYYMMDD
     */

    public int getDateInt() {
        String date = getDate();
        return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10));
    }

    public String getDateTimeOfInt() {
        String date = getDateTime();
        return date.substring(0, 4) + date.substring(5, 7) +
            date.substring(8, 10) + date.substring(11, 13) + date.substring(14, 16) + date.substring(17, 19);
    }

    /**
     * ȡ�õ�ǰ���ڵ�����
     * @return YYYYMMDD
     */

    public int getDateTimeInt() {
        String date = getDate();
        return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10));
    }

    /**
     * ȡ��ָ��ʱ���ʱ���
     * @return long  ʱ���
     */
    public long getTimeStamp(String datetime) {
        return getCalendar(datetime).getTime().getTime();
    }

    /**
     * ȡ�õ�ǰ��ʱ���
     * @return long  ʱ���
     */
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * ȡ�õ�ǰʱ�������
     * @return HHMMSS
     */

    public int getTimeInt() {
        String date = getDateTime();
        return Integer.parseInt(date.substring(11, 13) + date.substring(14, 16) + date.substring(17, 19));
    }

    /**
     * �Ƚ�ʱ���С������ added from internet
     * @throws ParseException
     */
    public String compareTime(Date d1, Date d2) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyy-MM-dd");
//        Date d1 = df.parse("2000-01-01");
//        Date d2 = df.parse("1999-12-31");
        String relation = null;
        if (d1.equals(d2)) {
            relation = "same";
        }
        else if (d1.before(d2)) {
            relation = "before";
        }
        else {
            relation = "after";
        }
        return relation;
    }

    /**
     * �����ꡢ�����ÿ���µ�����
     * @param year int
     * @param month int
     * @return int
     */
    public int getRealDays(int year, int month) {
        int[] daysInMonth = new int[] {
            31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (2 == month) {
            return ( (0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
        }
        else {
            return daysInMonth[month];
        }
    }

    /**
     * �����������ڼ��������
     * @param input YYYY-MM-DD
     */
    public long calculateDayInteval(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return diff / (1000 * 60 * 60 * 24);

    }

    /**
     * example
     * @param args
     */
    public static void main(String[] args) {
//    	  CalendarHelper CurrentDateTime = new CalendarHelper();
//        System.out.println(CurrentDateTime.getDateTime());
//        String str = CurrentDateTime.getDateTime();
//        System.out.println(getDateTimeOfInt());
//        System.out.println(CurrentDateTime.getDate());
//        System.out.println(CurrentDateTime.getTime());
//        System.out.println(CurrentDateTime.getDateInt());
//        System.out.println(CurrentDateTime.getTimeInt());
//        System.out.println(getCalendar(str).getTime().getTime());
//        System.out.println(CurrentDateTime.getPreDateTime(str, MINUTE, 100));
//        System.out.println(CalendarHelper.getInstance().getPreDateTime(CalendarHelper.getInstance().getDateTime(),MINUTE,190));
//        System.out.println(CurrentDateTime.getPreDateTime(str, SECOND, 100));
//        System.out.println(CurrentDateTime.getPreDateTime(str, MINUTE, 100));
//        System.out.println(CurrentDateTime.getPreDateTime(str, YEAR, 1));
//        System.out.println(CurrentDateTime.getPreDateTime(str, MONTH, 1));
//        System.out.println(CurrentDateTime.getPreDateTime(str, DAY, 10));
    	
          CalendarHelper currDateTime = CalendarHelper.getInstance();
          currDateTime.getDateTime();
          System.out.println(currDateTime.getPreDateTime(currDateTime.getDateTime(), CalendarHelper.DAY, 2));
//          try {
//			Date d = currDateTime.getDateByFormat(currDateTime.getPreDateTime(currDateTime.getDateTime(), CalendarHelper.DAY, 4));
//			System.out.println(d);
//			System.out.println(new Date());
//			System.out.println(currDateTime.getCurWeekDay(d));
//			System.out.println(currDateTime.getCurWeekDay(new Date()));
//          } catch (ParseException e) {			
//			e.printStackTrace();
//          }
         
        
    }

}
